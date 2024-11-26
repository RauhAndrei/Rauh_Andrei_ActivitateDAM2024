package com.example.seminar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImagineAdapter extends BaseAdapter  {
    private List<ImagineDomeniu> imagini = null;
    private Context ctx;
    private int resursaLayout;

    public ImagineAdapter(List<ImagineDomeniu> imagini, Context ctx, int resursaLayout) {
        this.imagini = imagini;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return imagini.size();
    }

    @Override
    public Object getItem(int i) {
        return imagini.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resursaLayout, viewGroup, false);

        ImagineDomeniu imagine = (ImagineDomeniu)getItem(i);
        ImageView imagineIv = v.findViewById(R.id.avionIv);
        TextView descriereTv = v.findViewById(R.id.numeImagineTv);

        imagineIv.setImageBitmap(imagine.getImagine());
        descriereTv.setText(imagine.getTextAfisat());
        return v;
    }
}
