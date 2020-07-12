package com.android.calculator;

import java.util.HashMap;

public class CurrencyExchange {

    private String base;
    private String date;
    private HashMap<String, Double> rates;

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }
}
