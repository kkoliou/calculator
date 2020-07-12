package com.android.calculator;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyExchangeService {
    @GET("http://data.fixer.io/api/latest?access_key=cf2cec699bac6a877687217edc984666")
    Call<CurrencyExchange> loadCurrencyData();
}
