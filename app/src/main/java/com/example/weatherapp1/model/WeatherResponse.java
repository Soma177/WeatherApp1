package com.example.weatherapp1.model;

public class WeatherResponse {
    public Location location;
    public Current current;

    public class Location {
        public String name;
        public String region;
        public String country;
    }

    public class Current {
        public double temp_c;  // Температура в градусах Цельсия
        public Condition condition;

        public class Condition {
            public String text;  // Описание погоды
            public String icon;  // URL иконки погоды
        }
    }
}
