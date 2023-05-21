package com.example.finishproject;

import java.util.ArrayList;
import java.util.Collection;

public class Weather {
    public String message;
    public String cod;
    public double count;
    ArrayList<General> list;

    public Weather(String message, String cod, double count, ArrayList<General> list) {
        this.message = message;
        this.cod = cod;
        this.count = count;
        this.list = list;
        list.add(new General());


    }


    @Override
    public String toString() {
        return "Weather{" +
                "message='" + message + '\'' +
                ", cod='" + cod + '\'' +
                ", count=" + count +
                ", list=" + list +
                '}';
    }


    class General {
        int id;
        String name;
        Coord coord;
        Main main;
        long dt;
        wind wind;
        sys sys;
        String rain;
        String snow;
        clouds clouds;
        ArrayList<weatherr>weather;

        @Override
        public String toString() {
            return "General{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", coord=" + coord +
                    ", main=" + main +
                    ", dt=" + dt +
                    ", wind=" + wind +
                    ", sys=" + sys +
                    ", rain='" + rain + '\'' +
                    ", snow='" + snow + '\'' +
                    ", clouds=" + clouds +
                    ", weather=" + weather +
                    '}';
        }


        class Coord {
        double lat;
        double lon;


    }


    class Main {
        double temp;
        double feels_like;
        double temp_min;
        double temp_max;
        double pressure;
        double humidity;
        double sea_level;
        double grnd_level;

    }


    class wind {
        double speed;
        int deg;

    }


    class sys {
        String country;

    }


    class clouds {
        int all;

    }







        class weatherr{
            int id;
            String main;
            String description;
            String icon;


        }


}
}
