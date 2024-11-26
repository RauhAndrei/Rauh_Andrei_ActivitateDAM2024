package com.example.seminar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImaginiAvioane extends AppCompatActivity {
    private List<ImagineDomeniu> imagineDomeniuList = null;
    private List<Bitmap> imagini = null;
    private List<String> linkuriImagini = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagini = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini_avioane);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        linkuriImagini.add("https://images.unsplash.com/photo-1541316735011-8f6c9d6c4f30?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZpb258ZW58MHx8MHx8fDA%3D");
        linkuriImagini.add("https://images.unsplash.com/photo-1541316735011-8f6c9d6c4f30?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZpb258ZW58MHx8MHx8fDA%3D");
        linkuriImagini.add("https://images.unsplash.com/photo-1541316735011-8f6c9d6c4f30?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZpb258ZW58MHx8MHx8fDA%3D");
        linkuriImagini.add("https://images.unsplash.com/photo-1541316735011-8f6c9d6c4f30?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZpb258ZW58MHx8MHx8fDA%3D");
        linkuriImagini.add("https://images.unsplash.com/photo-1541316735011-8f6c9d6c4f30?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YXZpb258ZW58MHx8MHx8fDA%3D");

        //pornim un fir paralel de executie
        Executor executor = Executors.newSingleThreadExecutor();
        //pentru intoarcere la firul principal folosim un handler
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(String link:linkuriImagini){
                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        InputStream is = con.getInputStream();
                        imagini.add(BitmapFactory.decodeStream(is));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imagineDomeniuList = new ArrayList<>();
                        imagineDomeniuList.add(new ImagineDomeniu("Alba ca Zapada",imagini.get(0),"https://media.istockphoto.com/id/1414160809/fr/vectoriel/ic%C3%B4ne-davion-pictogramme-de-vol-davion-transport-voyage-symbolique.jpg?s=612x612&w=0&k=20&c=k60eqmc_ZMSgSrwfyfL93KPmqEhIMZ2cR640WVV6b_k="));
                        imagineDomeniuList.add(new ImagineDomeniu("Alba ca Zapada",imagini.get(1),"https://media.istockphoto.com/id/1414160809/fr/vectoriel/ic%C3%B4ne-davion-pictogramme-de-vol-davion-transport-voyage-symbolique.jpg?s=612x612&w=0&k=20&c=k60eqmc_ZMSgSrwfyfL93KPmqEhIMZ2cR640WVV6b_k="));
                        imagineDomeniuList.add(new ImagineDomeniu("Alba ca Zapada",imagini.get(2),"https://media.istockphoto.com/id/1414160809/fr/vectoriel/ic%C3%B4ne-davion-pictogramme-de-vol-davion-transport-voyage-symbolique.jpg?s=612x612&w=0&k=20&c=k60eqmc_ZMSgSrwfyfL93KPmqEhIMZ2cR640WVV6b_k="));
                        imagineDomeniuList.add(new ImagineDomeniu("Alba ca Zapada",imagini.get(3),"https://media.istockphoto.com/id/1414160809/fr/vectoriel/ic%C3%B4ne-davion-pictogramme-de-vol-davion-transport-voyage-symbolique.jpg?s=612x612&w=0&k=20&c=k60eqmc_ZMSgSrwfyfL93KPmqEhIMZ2cR640WVV6b_k="));
                        imagineDomeniuList.add(new ImagineDomeniu("Alba ca Zapada",imagini.get(4),"https://media.istockphoto.com/id/1414160809/fr/vectoriel/ic%C3%B4ne-davion-pictogramme-de-vol-davion-transport-voyage-symbolique.jpg?s=612x612&w=0&k=20&c=k60eqmc_ZMSgSrwfyfL93KPmqEhIMZ2cR640WVV6b_k="));

                        ListView lv = findViewById(R.id.imaginiLv);
                        ImagineAdapter adapter = new ImagineAdapter(imagineDomeniuList, getApplicationContext(), R.layout.activity_imagini_avioane);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
                                it.putExtra("link",imagineDomeniuList.get(i).getLink());
                                startActivity(it);
                            }
                        });
                    }
                });
            }
        });
    }



}