package com.example.seminar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AdaugareAvion extends AppCompatActivity {

    private AppDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adaugare_avion);

        //Creează o instanță a bazei de date Room (avioane.db) care va fi folosită pentru stocarea informațiilor despre avioane.
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "avioane.db").build();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button adAvionBtn = findViewById(R.id.adAvionBtn) ;
        adAvionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMarca = findViewById(R.id.marcaEt);
                EditText etModel = findViewById(R.id.modelEt);
                EditText etNrPasageri = findViewById(R.id.nrPasageriEt);
                EditText etGreutate = findViewById(R.id.greutateEt);
                CheckBox motorinaCb = findViewById(R.id.motorinaCb);

                String marca = etMarca.getText().toString();
                String model = etModel.getText().toString();
                int nrPasageri = Integer.parseInt(etNrPasageri.getText().toString());
                Float greutate = Float.parseFloat(etGreutate.getText().toString());
                boolean motorina = ((CheckBox)findViewById(R.id.motorinaCb)).isChecked();

                Avion avion = new Avion(marca, model, nrPasageri, greutate, motorina);

                //operațiile lente(accesul la o bază de date / lucrul cu rețele) trebuie executate în afara UI thread-ului pentru a preveni blocarea aplicației. Dacă ar fi rulat direct pe firul principal, aplicația ar deveni nefuncțională
                new Thread(() -> {
                    try {
                        FileOutputStream file;
                        file = openFileOutput("obiecte.txt", MODE_PRIVATE);
                        OutputStreamWriter output = new OutputStreamWriter(file);
                        BufferedWriter writer = new BufferedWriter(output);
                        writer.write(avion.toString());
                        writer.close();
                        output.close();
                        file.close();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    dataBase.avionDao().insert(avion);
                    //Firul principal este singurul care poate actualiza interfața utilizator (UI). Pentru a afișa mesajul Toast, trebuie să folosim metoda runOnUiThread
                    runOnUiThread(() -> Toast.makeText(AdaugareAvion.this, "Avion salvat cu succes!", Toast.LENGTH_LONG).show());
                }).start();

                Intent it = new Intent();
                it.putExtra("avion", avion);
                Toast.makeText(AdaugareAvion.this, avion.toString(), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, it);

                SharedPreferences sp = getSharedPreferences("obiecteAvioane", MODE_PRIVATE);

                finish();
            }
        });

        //?????????????????(n am inteles dc folosim acest cod)
        Intent it = getIntent();
        if (it.hasExtra("avion")) {
            Avion avion = it.getParcelableExtra("avion");

            TextView marcaTv = findViewById(R.id.marcaTV);
            TextView modelTv = findViewById(R.id.modelTV);
            TextView pasageriTv = findViewById(R.id.nrPasageriTV);
            TextView greutateTv = findViewById(R.id.greutateTV);
            CheckBox motorinaCb = findViewById(R.id.motorinaCB);

            marcaTv.setText(avion.getMarca());
            modelTv.setText(avion.getModel());
            pasageriTv.setText(avion.getNrMaxPasageri());
            greutateTv.setText(String.valueOf((avion.getGreutate())));
            motorinaCb.setChecked(avion.isAreMotorina());
        }
        //????????????????
    }



}