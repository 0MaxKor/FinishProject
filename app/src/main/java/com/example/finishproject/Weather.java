package com.example.finishproject;

import java.util.ArrayList;

public class Weather {
    public String message;
    public String cod;
    public double count;
     ArrayList < Object > list = new ArrayList< Object >();

    public Weather(String message, String cod, double count) {
        this.message = message;
        this.cod = cod;
        this.count = count;
    }


    public String getMessage() {
        return message;
    }

    public String getCod() {
        return cod;
    }

    public double getCount() {
        return count;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setCount(float count) {
        this.count = count;
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
}
