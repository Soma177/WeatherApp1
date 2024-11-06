package com.example.weatherapp1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.weatherapp1.api.WeatherService;
import com.example.weatherapp1.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "b1ccdde6d34d4f63b3a41713243010";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";

    private TextView locationTextView, temperatureTextView, conditionTextView;
    private ImageView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        locationTextView = findViewById(R.id.locationTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        conditionTextView = findViewById(R.id.conditionTextView);
        weatherIcon = findViewById(R.id.weatherIcon);

        // Настройка Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);

        // Выполнение запроса текущей погоды
        getCurrentWeather(weatherService, "Karaganda");
    }

    private void getCurrentWeather(WeatherService weatherService, String location) {
        weatherService.getCurrentWeather(API_KEY, location, "no").enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    updateUI(weather);
                    Log.d("WeatherAPI", "JSON Response: " + new Gson().toJson(response.body()));
                }
                else {
                    // Если ответ неудачный, показываем сообщение об ошибке
                    Log.d("WeatherAPI", "Error: Response unsuccessful or empty body");
                    locationTextView.setText("City not found");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Обработка ошибки
                locationTextView.setText("Error: " + t.getMessage());
            }
        });
    }

    private void updateUI(WeatherResponse weather) {
        // Устанавливаем текстовые значения
        locationTextView.setText(weather.location.name + ", " + weather.location.country);
        temperatureTextView.setText(weather.current.temp_c + "°C");
        conditionTextView.setText(weather.current.condition.text);

        // Загрузка иконки погоды с помощью Glide
        String iconUrl = "http:" + weather.current.condition.icon;
        Glide.with(this).load(iconUrl).into(weatherIcon);
    }
}
