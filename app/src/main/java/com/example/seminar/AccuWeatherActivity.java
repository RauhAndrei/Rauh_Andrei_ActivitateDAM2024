package com.example.seminar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccuWeatherActivity extends AppCompatActivity {

    String apiKey = "aYsIaZVUYUiMVG33MIXQv5C1ZvSiBnEG";
    String cheieOras;
    String min;
    String max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accu_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spDurata = findViewById(R.id.idSpinnerDurata);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.durata_zile, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spDurata.setAdapter(adapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        Button cautaCodOrasBtn = findViewById(R.id.cautaCodOrasBtn);
        cautaCodOrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText orasEt = findViewById(R.id.orasEt);
                String oras = orasEt.getText().toString();

                String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apiKey + "&q=" + oras;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try {
                            URL apiURL = new URL(url);
                            con = (HttpURLConnection) apiURL.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder response = new StringBuilder();
                            String line;

                            while((line = reader.readLine())!=null) {
                                response.append(line);
                            }
                            reader.close();
                            con.disconnect();

                            JSONArray vector = new JSONArray(response.toString());
                            JSONObject obiect = vector.getJSONObject(0);
                            cheieOras = obiect.getString("Key");

                            String durataStr = spDurata.getSelectedItem().toString();
                            int durata = Integer.parseInt(durataStr);

                            TextView orasTV = findViewById(R.id.idKeyTv);

                            String urlWeather = null;
                            if (durata==1){
                                urlWeather = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + cheieOras
                                        + "?apikey=" + apiKey + "&metric=true";
                        }
                            else {
                                urlWeather = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + cheieOras
                                        + "?apikey=" + apiKey + "&metric=true";
                            }
                            apiURL = new URL(urlWeather);
                            con = (HttpURLConnection) apiURL.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();

                            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            response = new StringBuilder();
                            while((line = reader.readLine()) != null){
                                response.append(line);
                            }

                            reader.close();
                            con.disconnect();

                            JSONObject object = new JSONObject(response.toString());
                            JSONArray dailyForecasts = object.getJSONArray("DailyForecasts");
                            StringBuilder rezultat = new StringBuilder();

                            for (int i = 0; i < dailyForecasts.length(); i++) {
                                JSONObject forecast = dailyForecasts.getJSONObject(i);
                                JSONObject temperature = forecast.getJSONObject("Temperature");
                                String min = temperature.getJSONObject("Minimum").getString("Value");
                                String max = temperature.getJSONObject("Maximum").getString("Value");
                                String data = forecast.getString("Date");

                                rezultat.append("Data: ").append(data)
                                        .append("\nMin: ").append(min)
                                        .append("\nMax: ").append(max)
                                        .append("\n\n");
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    TextView tvCod = findViewById(R.id.idKeyTv);
                                    tvCod.setText("Cheie oras:" + cheieOras + "\n" + rezultat.toString());
                                }
                            });

                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

            }
        });
    }
}
