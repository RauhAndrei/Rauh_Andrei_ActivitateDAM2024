package com.example.seminar;
import android.content.Intent;
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

                Intent it = new Intent();
                it.putExtra("avion", avion);
                Toast.makeText(AdaugareAvion.this, avion.toString(), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, it);
                finish();
            }
        });

        //?????????????????
        Intent it = getIntent();
        if (it.hasExtra("avion")) {
            Avion avion = it.getParcelableExtra("avion");
            //?????????????????
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

    }



}