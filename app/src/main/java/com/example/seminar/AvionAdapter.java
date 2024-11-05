package com.example.seminar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class AvionAdapter extends BaseAdapter {
    private List<Avion> avioane = null;
    private Context ctx;
    private int resursaLayout;

    public AvionAdapter(List<Avion> avioane, Context ctx, int resursaLayout) {
        this.avioane = avioane;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return avioane.size();
    }

    @Override
    public Object getItem(int i) {
        return avioane.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resursaLayout, viewGroup, false);

        TextView marcaTv = v.findViewById(R.id.marcaTv);
        TextView modelTv = v.findViewById(R.id.modelTV);
        TextView pasageriTv = v.findViewById(R.id.nrPasageriET);
        TextView greutateTv = v.findViewById(R.id.greutateET);
        CheckBox motorinaCb = v.findViewById(R.id.motorinaCB);

        Avion avion = (Avion)getItem(i);

        marcaTv.setText(avion.getMarca());
        modelTv.setText(avion.getModel());
        pasageriTv.setText(avion.getNrMaxPasageri());
        greutateTv.setText(String.valueOf((avion.getGreutate())));
        motorinaCb.setChecked(avion.isAreMotorina());

        return v;
    }


}
