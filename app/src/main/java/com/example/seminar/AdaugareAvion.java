package com.example.seminar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugareAvion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adaugare_avion);
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
                String marca = etMarca.getText().toString();

                EditText etModel = findViewById(R.id.modelEt);
                String model = etModel.getText().toString();

                EditText etNrPasageri = findViewById(R.id.nrPasageriEt);
                int nrPasageri = Integer.parseInt(etNrPasageri.getText().toString());

                EditText etGreutate = findViewById(R.id.greutateEt);
                Float greutate = Float.parseFloat(etGreutate.getText().toString());

                CheckBox motorinaCb = findViewById(R.id.motorinaCb);
                boolean motorina = ((CheckBox)findViewById(R.id.motorinaCb)).isChecked();

                Avion avion = new Avion(marca, model, nrPasageri, greutate, motorina);

                Intent it = new Intent();
                it.putExtra("avion", avion);
                Toast.makeText(AdaugareAvion.this, avion.toString(), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, it);
                finish();
            }
        });

    }



}