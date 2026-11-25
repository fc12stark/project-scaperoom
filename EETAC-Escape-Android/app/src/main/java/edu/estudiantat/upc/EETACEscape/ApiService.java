package edu.estudiantat.upc.EETACEscape;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
    @POST("users")
    Call<User> register(@Body User user);

    @POST("sessions")
    Call<User> login(@Body LoginRequest request);

    @GET("items")
    Call<List<Item>> getShopItems();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("users/{id}/inventory")
    Call<List<InventoryItem>> getInventory(@Path("id") String id);

    @POST("users/{id}/inventory")
    Call<InventoryItem> buyItem(@Path("id") String id, @Body InventoryRequest request);

    @DELETE("users/{id}/inventory/{itemId}")
    Call<InventoryItem> returnItem(@Path("id") String id, @Path("itemId") String itemId);

    @PUT("users/{id}/level")
    Call<User> updateLevel(@Path("id") String id, @Body LevelRequest request);

    @POST("games")
    Call<GameSession> createGame(@Body GameRequest request);
}
