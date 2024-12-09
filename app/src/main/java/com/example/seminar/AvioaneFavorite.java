package com.example.seminar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Map;

public class AvioaneFavorite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avioane_favorite);

        SharedPreferences sp = getSharedPreferences("obiecteFavorite", MODE_PRIVATE);
        Map<String, ?> allEntries = sp.getAll();

        ArrayList<String> listaAviaoneFavorite = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            listaAviaoneFavorite.add(entry.getValue().toString());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listaAviaoneFavorite);

        ListView avioaneFavoriteLv = findViewById(R.id.avioaneFavoriteLv);
        avioaneFavoriteLv.setAdapter(adapter);

    }
}
