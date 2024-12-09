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

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://www.bucataras.ro/uploads/modules/news/52258/656x440_prajitura-alba-ca-zapada-268448.jpg");
        linkuriImagini.add("https://www.delicii.com/wp-content/uploads/2024/05/MDP_0639-scaled.jpg");
        linkuriImagini.add("https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQQPG52Tehp40qqSjTVHFh-29grZQTjpzJkfVu8ucxNcvktaRDOXpD14KyBrkLhstWvrgOpMrv2h-0quoGnEGRvTa8P6DdF-DIQzv_MHKfCOGt23cgB8jd-&usqp=CAc");
        linkuriImagini.add("https://www.jocooks.com/wp-content/uploads/2018/11/cheesecake-1-22.jpg");
        linkuriImagini.add("https://jamilacuisine.ro/wp-content/uploads/2016/01/Negrese-glazurate-500x478.jpg");

        Executor executor = Executors.newSingleThreadExecutor();
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
                        imagineDomeniuList.add(new ImagineDomeniu("Alba ca Zapada",imagini.get(0),"https://www.google.com/imgres?q=alba%20ca%20zapada%20prajitura&imgurl=https%3A%2F%2Fwww.bucataras.ro%2Fuploads%2Fmodules%2Fnews%2F52258%2Fprajitura-alba-ca-zapada-268448.jpg&imgrefurl=https%3A%2F%2Fwww.bucataras.ro%2Fretete%2Fprajitura-alba-ca-zapada-desertul-copilariei-52258.html&docid=BiDBe7mXZ0oYhM&tbnid=yseaNo3Wgb5dmM&vet=12ahUKEwinttbe6uiJAxV8gf0HHYrTJ-4QM3oECFIQAA..i&w=2160&h=1620&hcb=2&ved=2ahUKEwinttbe6uiJAxV8gf0HHYrTJ-4QM3oECFIQAA"));
                        imagineDomeniuList.add(new ImagineDomeniu("Tiramisu",imagini.get(1),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.delicii.com%2Ftiramisu-reteta-clasica%2F&psig=AOvVaw0ls9pAfzKaSaifN0t_lPGF&ust=1732120137220000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKjy5Mvo6IkDFQAAAAAdAAAAABAE"));
                        imagineDomeniuList.add(new ImagineDomeniu("Carrot cake",imagini.get(2),"https://www.google.com/aclk?sa=l&ai=DChcSEwiGuNGB6eiJAxVvo4MHHWLSEXcYABABGgJlZg&co=1&ase=2&gclid=Cj0KCQiAi_G5BhDXARIsAN5SX7oKxKLVLp4Jg4dkt5NQ9par3hiatGg6dq6f-6VP0ZM0Oha4s4_ZUcIaAk-bEALw_wcB&sig=AOD64_0o2TtBuZ_1EOjTs66GPM0H3NTi2A&ctype=5&q=&nis=4&ved=2ahUKEwiAscuB6eiJAxX0gf0HHey3MUwQ9aACKAB6BAgBEBE&adurl="));
                        imagineDomeniuList.add(new ImagineDomeniu("Cheese cake",imagini.get(3),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.jocooks.com%2Frecipes%2Fcheesecake-recipe%2F&psig=AOvVaw0HyyoQp0KzyX9OzRX5ifkV&ust=1732037733374000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLCVwcC15okDFQAAAAAdAAAAABAE"));
                        imagineDomeniuList.add(new ImagineDomeniu("Negresa",imagini.get(4),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fjamilacuisine.ro%2Fnegrese-glazurate-reteta-video%2F&psig=AOvVaw0dupDEOsAo8YuXG-3cgvXx&ust=1732037744294000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMiA4MW15okDFQAAAAAdAAAAABAE"));

                        ListView listView = findViewById(R.id.list_view);
                        ImagineAdapter adapter = new ImagineAdapter(imagineDomeniuList, getApplicationContext(), R.layout.activity_imagini_avioane);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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