package com.example.finishproject;

import java.util.ArrayList;

public class Translator {
    Data data;

    public Translator(Data dataObject) {
        data = dataObject;
    }


    class Data {
        ArrayList <text> translations = new ArrayList();

    }
    class text{
        String translatedText;
    }
}

