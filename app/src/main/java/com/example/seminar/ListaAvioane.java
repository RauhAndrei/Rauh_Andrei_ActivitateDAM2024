package com.example.seminar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ListaAvioane extends AppCompatActivity {

    private int idModificat = 0;
    private AvionAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_avioane);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();
        List<Avion> avioane = it.getParcelableArrayListExtra("avioane");
        ListView avioaneLv = findViewById(R.id.avioaneLV);


//        ArrayAdapter<Avion> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, avioane);
//        avioaneLv.setAdapter(adapter);
        adapter = new AvionAdapter(avioane, getApplicationContext(), R.layout.row_item);
        avioaneLv.setAdapter(adapter);

        avioaneLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intentModifica = new Intent(getApplicationContext(), AdaugareAvion.class);
                intentModifica.putExtra("avion", avioane.get(i));
                idModificat = 1;
                startActivityForResult(intentModifica, 209);
                Toast.makeText(ListaAvioane.this, avioane.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        avioaneLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                avioane.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        

    }
}