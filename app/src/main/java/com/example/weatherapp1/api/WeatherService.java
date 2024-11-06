package com.example.weatherapp1.api;

import com.example.weatherapp1.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    // Получение текущей погоды
    @GET("current.json")
    Call<WeatherResponse> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("aqi") String airQuality // для включения данных по качеству воздуха (можно отключить "no")
    );

    // Получение прогноза на 7 дней
    @GET("forecast.json")
    Call<WeatherResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days
    );
}
