package com.example.seminar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.INotificationSideChannel;
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
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccuWeatherActivity extends AppCompatActivity {

    private static final String API_KEY = "wMBA7gjZW1uNoaaBqVzMNDfyuMdTVp5B";
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.myLooper());

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.durata_zile, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spDurata.setAdapter(adapter);

        findViewById(R.id.cautaCodOrasBtn).setOnClickListener(v -> {
            String oras = ((EditText) findViewById(R.id.orasEt)).getText().toString();
            fetchCityKey(oras, spDurata);
        });
    }

   private void fetchCityKey(String oras, Spinner spDurata) {
        String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q=" + oras;
        executor.execute(() -> {
            try {
                String cityKey = new JSONArray(fetchData(url)).getJSONObject(0).getString("Key");
                fetchWeather(cityKey, Integer.parseInt(spDurata.getSelectedItem().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
   }

   private void fetchWeather(String cityKey, int durata) {
        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/" + (durata == 1 ? "1day/" : "5day/") + cityKey + "?apikey=" + API_KEY + "&metric=true";
        executor.execute(() -> {
            try {
                JSONArray forecasts = new JSONObject(fetchData(url)).getJSONArray("DailyForecasts");
                StringBuilder rezultat = new StringBuilder();

                for (int i = 0; i < forecasts.length(); i++) {
                    JSONObject forecast = forecasts.getJSONObject(i);
                    rezultat.append("Data: ").append(forecast.getString("Date"))
                            .append("\nMin: ").append(forecast.getJSONObject("Temperature").getJSONObject("Minimum").getString("Value"))
                            .append("\nMin: ").append(forecast.getJSONObject("Temperature").getJSONObject("Minimum").getString("Value"))
                            .append("\n\n");
                }
                handler.post(() -> ((TextView) findViewById(R.id.idKeyTv)).setText(rezultat));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
   }

    private String fetchData(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String linie;
            StringBuilder response = new StringBuilder();
            while ((linie = reader.readLine()) != null) response.append(linie);
            return response.toString();
        } finally {
            con.disconnect();
        }

    }


}
