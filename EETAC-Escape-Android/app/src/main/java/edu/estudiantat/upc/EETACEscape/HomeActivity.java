package edu.estudiantat.upc.EETACEscape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ApiService api;
    private String userId;
    private String username;
    private TextView textUser;
    private TextView textResponses;
    private Spinner spinnerLevels;
    private EditText editItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        userId = prefs.getString("userId", "");
        username = prefs.getString("username", "");
        if (userId.equals("")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        api = RetrofitClient.getApiService();
        textUser = findViewById(R.id.textUser);
        textResponses = findViewById(R.id.textResponses);
        spinnerLevels = findViewById(R.id.spinnerLevels);

        String[] levels = {"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevels.setAdapter(adapter);
        editItemId = findViewById(R.id.editItemId);

        findViewById(R.id.buttonListShop).setOnClickListener(v -> listShop());
        findViewById(R.id.buttonBuyItem).setOnClickListener(v -> buyItem());
        findViewById(R.id.buttonReturnItem).setOnClickListener(v -> returnItem());
        findViewById(R.id.buttonSetLevel).setOnClickListener(v -> setLevel());
        findViewById(R.id.buttonInventory).setOnClickListener(v -> showInventory());
        findViewById(R.id.buttonNewGame).setOnClickListener(v -> newGame());
        findViewById(R.id.buttonLogout).setOnClickListener(v -> logout());

        loadUser();
    }

    private void loadUser() {
        api.getUser(userId).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    textUser.setText("Usuari: " + username + "\nECTS: " + user.getEcts()
                            + "\nNivell actual: " + user.getNivellActual());
                }
                showResponse("GET usuari", response);
            }

            public void onFailure(Call<User> call, Throwable t) {
                showFailure("GET usuari", t);
            }
        });
    }

    private void listShop() {
        api.getShopItems().enqueue(new Callback<List<Item>>() {
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                showResponse("GET tienda", response);
            }

            public void onFailure(Call<List<Item>> call, Throwable t) {
                showFailure("GET tienda", t);
            }
        });
    }

    private void buyItem() {
        String itemId = editItemId.getText().toString();
        if (itemId.equals("")) {
            String oldText = textResponses.getText().toString();
            textResponses.setText("ItemId obligatori\n\n" + oldText);
            return;
        }
        api.buyItem(userId, new InventoryRequest(itemId, 1)).enqueue(new Callback<InventoryItem>() {
            public void onResponse(Call<InventoryItem> call, Response<InventoryItem> response) {
                showResponse("POST comprar", response);
                loadUser();
                showInventory();
            }

            public void onFailure(Call<InventoryItem> call, Throwable t) {
                showFailure("POST comprar", t);
            }
        });
    }

    private void returnItem() {
        String itemId = editItemId.getText().toString();
        if (itemId.equals("")) {
            String oldText = textResponses.getText().toString();
            textResponses.setText("ItemId obligatori\n\n" + oldText);
            return;
        }
        api.returnItem(userId, itemId).enqueue(new Callback<InventoryItem>() {
            public void onResponse(Call<InventoryItem> call, Response<InventoryItem> response) {
                showResponse("DELETE retornar", response);
                loadUser();
                showInventory();
            }

            public void onFailure(Call<InventoryItem> call, Throwable t) {
                showFailure("DELETE retornar", t);
            }
        });
    }

    private void setLevel() {
        int ordre = Integer.parseInt(spinnerLevels.getSelectedItem().toString());
        api.updateLevel(userId, new LevelRequest(ordre)).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                showResponse("PUT nivell " + ordre, response);
                if (response.isSuccessful()) {
                    loadUser();
                }
            }

            public void onFailure(Call<User> call, Throwable t) {
                showFailure("PUT nivell", t);
            }
        });
    }

    private void showInventory() {
        api.getInventory(userId).enqueue(new Callback<List<InventoryItem>>() {
            public void onResponse(Call<List<InventoryItem>> call, Response<List<InventoryItem>> response) {
                showResponse("GET inventari", response);
            }

            public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                showFailure("GET inventari", t);
            }
        });
    }

    private void newGame() {
        api.createGame(new GameRequest(userId)).enqueue(new Callback<GameSession>() {
            public void onResponse(Call<GameSession> call, Response<GameSession> response) {
                showResponse("POST partida", response);
            }

            public void onFailure(Call<GameSession> call, Throwable t) {
                showFailure("POST partida", t);
            }
        });
    }

    private void showResponse(String label, Response<?> response) {
        String body;
        if (response.isSuccessful()) {
            body = new Gson().toJson(response.body());
        } else {
            try {
                body = response.errorBody().string();
            } catch (IOException e) {
                body = e.getMessage();
            }
        }
        String oldText = textResponses.getText().toString();
        textResponses.setText(label + " HTTP " + response.code() + "\n" + body + "\n\n" + oldText);
    }

    private void showFailure(String label, Throwable t) {
        String oldText = textResponses.getText().toString();
        textResponses.setText(label + " error: " + t.getMessage() + "\n\n" + oldText);
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
