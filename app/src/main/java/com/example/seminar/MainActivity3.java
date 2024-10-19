package com.example.seminar;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        Bundle bundle = getIntent().getExtras();

        System.out.println(123);

        if(bundle != null) {
            String mesaj = bundle.getString("mesaj");
            int valoare1 = bundle.getInt("val1");
            int valoare2 = bundle.getInt("val2");

            String mesajComplet = "Mesaj: " + mesaj + "\nValoarea 1: " + valoare1 + "\nValoarea 2:" + valoare2;
            Toast.makeText(this, mesajComplet, Toast.LENGTH_LONG).show();
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}