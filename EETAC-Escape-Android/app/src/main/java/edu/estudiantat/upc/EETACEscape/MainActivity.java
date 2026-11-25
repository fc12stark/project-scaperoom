package edu.estudiantat.upc.EETACEscape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;
    private EditText editEmail;
    private ProgressBar progressBar;
    private TextView textStatus;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        progressBar = findViewById(R.id.progressBar);
        textStatus = findViewById(R.id.textStatus);
        api = RetrofitClient.getApiService();

        findViewById(R.id.buttonLogin).setOnClickListener(v -> login());
        findViewById(R.id.buttonRegister).setOnClickListener(v -> register());
    }

    private void login() {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        if (username.equals("") || password.equals("")) {
            textStatus.setText("username i password obligatoris");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        api.login(new LoginRequest(username, password)).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                showResponse("Login", response);
                if (response.isSuccessful() && response.body() != null) {
                    goHome(response.body());
                }
            }

            public void onFailure(Call<User> call, Throwable t) {
                showFailure("Login", t);
            }
        });
    }

    private void register() {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        String email = editEmail.getText().toString();
        if (username.equals("") || password.equals("")) {
            textStatus.setText("username i password obligatoris");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        api.register(new User(username, password, email)).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                showResponse("Registre", response);
                if (response.isSuccessful() && response.body() != null) {
                    goHome(response.body());
                }
            }

            public void onFailure(Call<User> call, Throwable t) {
                showFailure("Registre", t);
            }
        });
    }

    private void goHome(User user) {
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", user.getId());
        editor.putString("username", user.getUsername());
        editor.apply();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userId", user.getId());
        intent.putExtra("username", user.getUsername());
        startActivity(intent);
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
        textStatus.setText(label + " HTTP " + response.code() + "\n" + body);
        progressBar.setVisibility(View.GONE);
    }

    private void showFailure(String label, Throwable t) {
        textStatus.setText(label + " error: " + t.getMessage());
        progressBar.setVisibility(View.GONE);
    }
}
