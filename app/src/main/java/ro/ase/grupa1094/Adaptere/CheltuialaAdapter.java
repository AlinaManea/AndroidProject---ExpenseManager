package ro.ase.grupa1094.Adaptere;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import ro.ase.grupa1094.Clase.Cheltuiala;
import ro.ase.grupa1094.R;

public class CheltuialaAdapter extends ArrayAdapter<Cheltuiala> {

    private Context context;
    private int layoutId;
    private List<Cheltuiala> cheltuialaList;
    private LayoutInflater inflater;


    public CheltuialaAdapter(@NonNull Context context, int resource, @NonNull List<Cheltuiala> cheltuialaList, LayoutInflater inflater) {
        super(context, resource, cheltuialaList);
        this.context=context;
        this.layoutId=resource;
        this.cheltuialaList=cheltuialaList;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=inflater.inflate(layoutId,parent,false);
        Cheltuiala cheltuiala=cheltuialaList.get(position);


        TextView tvCategorieC= view.findViewById(R.id.tvCategorieC);
        TextView tvDescriereC=view.findViewById(R.id.tvDescriereC);
        TextView tvValoareC=view.findViewById(R.id.tvValoareC);
        TextView tvAtentie=view.findViewById(R.id.tvAtentie);


        tvCategorieC.setText(String.valueOf(cheltuiala.getCategorie()));
        tvDescriereC.setText(cheltuiala.getDescriere());
        tvValoareC.setText(String.valueOf(cheltuiala.getValoare()));


        //filtrari
        if (cheltuiala.getValoare() > 1000) {
            tvValoareC.setTextColor(Color.RED);
            tvAtentie.setText("Atenție: cheltuială mare!");
            tvAtentie.setVisibility(View.VISIBLE);
        } else {
            tvValoareC.setTextColor(Color.BLACK);
            tvAtentie.setVisibility(View.GONE);
        }


        String formattedValoare = String.format(Locale.getDefault(), "%.2f", cheltuiala.getValoare());
        tvValoareC.setText(formattedValoare);

        return view;
    }
}
