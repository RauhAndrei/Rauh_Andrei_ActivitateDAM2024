package com.example.seminar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Avion> avioane = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        avioane.add(new Avion("marca", "model", 123, 3143.5f, true));

        Button toListaAvioaneBtn = findViewById(R.id.toListaAvioaneBtn);
        toListaAvioaneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ListaAvioane.class);
                it.putParcelableArrayListExtra("avioane", (ArrayList<? extends Parcelable>) avioane);
                startActivity(it);
            }
        });

        Button toFormularBtn = findViewById(R.id.toAdAvionFormularBtn);
        toFormularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), AdaugareAvion.class);
                startActivityForResult(it, 403);
            }
        });

        Button toImaginiAvioaneBtn = findViewById(R.id.toImaginiAvioane);
        toImaginiAvioaneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ImaginiAvioane.class);
                startActivity(it);
            }
        });

        Button toAccuWeatherBtn = findViewById(R.id.toAccuWeatherBtn);
        toAccuWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), AccuWeatherActivity.class);
                startActivity(it);
            }
        });

        Button toAviaoneFavoriteBtn = findViewById(R.id.toAviaoneFavorite);
        toAviaoneFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), AvioaneFavorite.class);
                startActivity(it);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 403) {
            if (resultCode == RESULT_OK) {
                Avion avion = data.getParcelableExtra("avion");
                avioane.add(avion);
            }
        }
    }
}