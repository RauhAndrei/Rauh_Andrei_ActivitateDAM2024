package com.example.seminar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class ListaAvioane extends AppCompatActivity {
    private AppDataBase dataBase;
    private List<Avion> avioane = null;
    private int idModificat = 0;
    private AvionAdapter adapter = null;

    //Incarcam aviaonele in lista din baza de date
    private void loadAvioaneFromDatabase() {
        new Thread(() -> {
            avioane = dataBase.avionDao().getAllAvioane();

            runOnUiThread(() -> {
                if (avioane == null || avioane.isEmpty()) {
                    Toast.makeText(this, "Nu există avioane salvate!", Toast.LENGTH_LONG).show();
                    avioane = new ArrayList<>();
                }
                ListView avioaneLv = findViewById(R.id.avioaneLV);
                adapter = new AvionAdapter(avioane, this, R.layout.row_item);
                avioaneLv.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_avioane);

        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "avioane.db").build();
        loadAvioaneFromDatabase();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();
        avioane = it.getParcelableArrayListExtra("avioane");
        ListView avioaneLv = findViewById(R.id.avioaneLV);

        adapter = new AvionAdapter(avioane, getApplicationContext(), R.layout.row_item);
        avioaneLv.setAdapter(adapter);

        //am crash cand apas
        avioaneLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentModifica = new Intent(getApplicationContext(), AdaugareAvion.class);
                intentModifica.putExtra("avion", avioane.get(i));
                idModificat = i;
                startActivityForResult(intentModifica, 200);
                Toast.makeText(getApplicationContext(), avioane.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //Stergem avionul din baza de date
        avioaneLv.setOnItemLongClickListener((parent, view, i, l) -> {
//            Avion avionDeSters = avioane.get(i);
//            avioane.remove(i);
//            new Thread(() -> dataBase.avionDao().delete(avionDeSters)).start();
//            adapter.notifyDataSetChanged();

            SharedPreferences sp = getSharedPreferences("obiecteFavorite", MODE_PRIVATE);//dc nu exista il creeaza
            SharedPreferences.Editor editor = sp.edit();

            editor.putString(avioane.get(i).getKey(), avioane.get(i).toString());
            editor.commit();
            return true;
        });

    }

    //????????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                Avion avionModificat = data.getParcelableExtra("avion");
                avioane.set(idModificat, avionModificat);
                new Thread(() -> dataBase.avionDao().insert(avionModificat)).start();
                adapter.notifyDataSetChanged();
            }
        }
    }
    //????????????????
}