package com.android.calculator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CurrencyFrag extends Fragment {

    private TextView input;
    private TextView output;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnClear;
    private Button btnDel;
    private Button btnDot;
    String[] currencies;
    HashMap<String, Double> ratesMap;
    private String baseCur;
    private String convCur;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.currency_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Enable internet connection to get the last currency rates", Toast.LENGTH_LONG).show();
        }

        initConvButtons();
        btnListeners();

        currencies = new String[]{"AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD",
                "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC",
                "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP",
                "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD",
                "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR",
                "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MVR", "MWK", "MXN", "MYR", "MZN",
                "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD",
                "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT",
                "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD",
                "XDR", "XOF", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL"};

        ratesMap = new HashMap<>();

        new JsonTask().execute("http://data.fixer.io/api/latest?access_key=cf2cec699bac6a877687217edc984666");

        spinners();

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {
                    try {
                        double amountForConv = Double.parseDouble(input.getText().toString());
                        double rate2 = ratesMap.get(convCur);
                        if (baseCur != "EUR") {
                            double rate1 = ratesMap.get(baseCur);
                            double amountToEur = amountForConv * 1 / rate1;
                            double converted = amountToEur * rate2;
                            output.setText(String.valueOf(converted));
                        } else {
                            double converted = amountForConv * rate2;
                            output.setText(String.valueOf(converted));
                        }
                    } catch (NullPointerException e) {
                        Log.e("hashmap","nullpointerexception");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {}

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.e("Response: ", "> " + line);
                }

                JSONObject obj = new JSONObject(buffer.toString());
                JSONObject rates = obj.getJSONObject("rates");

                for (int i = 0; i < currencies.length; i++) {
                    double value = rates.getDouble(currencies[i]);
                    ratesMap.put(currencies[i],value);
                    Log.e("currencies", currencies[i] + ": " + value);
                }

                return buffer.toString();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {}
    }

    public void spinners() {

        final Spinner fromSpin = getView().findViewById(R.id.spFrom);
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, currencies);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpin.setAdapter(adapterFrom);

        final Spinner toSpin = getView().findViewById(R.id.spTo);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, currencies);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin.setAdapter(adapterTo);

        fromSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                baseCur = fromSpin.getSelectedItem().toString();
                input.setText("0");
                output.setText("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        toSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convCur = toSpin.getSelectedItem().toString();
                input.setText("0");
                output.setText("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void initConvButtons() {
        input = getView().findViewById(R.id.fromTV);
        output = getView().findViewById(R.id.toTV);
        btn0 = getView().findViewById(R.id.btn0Cur);
        btn1 = getView().findViewById(R.id.btn1Cur);
        btn2 = getView().findViewById(R.id.btn2Cur);
        btn3 = getView().findViewById(R.id.btn3Cur);
        btn4 = getView().findViewById(R.id.btn4Cur);
        btn5 = getView().findViewById(R.id.btn5Cur);
        btn6 = getView().findViewById(R.id.btn6Cur);
        btn7 = getView().findViewById(R.id.btn7Cur);
        btn8 = getView().findViewById(R.id.btn8Cur);
        btn9 = getView().findViewById(R.id.btn9Cur);
        btnClear = getView().findViewById(R.id.btnCCur);
        btnDel = getView().findViewById(R.id.btnDelCur);
        btnDot = getView().findViewById(R.id.btnDotCur);
    }

    public void btnListeners() {
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().equals("0") && input.getText().toString().length() >= 1) {
                    input.setText(input.getText().toString() + "0");
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = startsWithZero();
                input.setText(str + "9");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("0");
                output.setText("0");
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = input.getText().toString();
                if (str.length() > 1) {
                    str = str.substring(0, str.length() - 1);
                    input.setText(str);
                } else {
                    input.setText("0");
                    output.setText("0");
                }
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().contains(".")) {
                    input.setText(input.getText().toString() + ".");
                }
            }
        });
    }

    public String startsWithZero() {
        String str = input.getText().toString();

        if (str.length() == 1) {
            if (str.charAt(0) == '0') {
                str = "";
            }
        }
        return str;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
