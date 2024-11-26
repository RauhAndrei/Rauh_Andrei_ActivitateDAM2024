package com.example.seminar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {

    String link = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=kwW8r66aQBLf66jEhTxEvMX5WTwe5ZGJ&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText orasEt = findViewById(R.id.orasEt);
        String oras = orasEt.getText().toString();

        Button apiBtn = findViewById(R.id.apiBtn);
        apiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link += oras;

            }
        });

        // pornim un fir paralel de execuție
        Executor executor = Executors.newSingleThreadExecutor();
        // pentru întoarcere la firul principal folosim un handler
        Handler handler = new Handler(Looper.myLooper());

        StringBuilder builder;
//        JSONArray vector = new JSONArray(builder.to)
        executor.execute(new Runnable() {
            StringBuilder builder = new StringBuilder();
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            builder.append(line);
                        }
                        bufferedReader.close();
                        reader.close();
                    }
                    connection.disconnect();

                } catch (IOException e) {
                    Log.e("API_ERROR", "Error fetching data", e);
                }
            }
        });
    }
}
