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

import ro.ase.grupa1094.Categoriee;
import ro.ase.grupa1094.R;
import ro.ase.grupa1094.Clase.Venit;

public class VenitAdapter extends ArrayAdapter<Venit> {
private Context context;
    private int layoutId;
    private List<Venit> venitList;
    private LayoutInflater inflater;


    public VenitAdapter(@NonNull Context context, int resource, @NonNull List<Venit> venitList, LayoutInflater inflater) {
        super(context, resource, venitList);
        this.context=context;
        this.layoutId=resource;
        this.venitList=venitList;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view=inflater.inflate(layoutId,parent,false);
       Venit venit=venitList.get(position);


       TextView tvCategorieV= view.findViewById(R.id.tvCategorieV);
       TextView tvDescriereV=view.findViewById(R.id.tvDescriereV);
       TextView tvValoareV=view.findViewById(R.id.tvValoareV);


       tvCategorieV.setText(String.valueOf(venit.getCategorie()));
       tvDescriereV.setText(venit.getDescriere());
       tvValoareV.setText(String.valueOf(venit.getValoare()));

       if(venit.getCategorie()== Categoriee.Chirie)
           tvCategorieV.setTextColor(Color.RED);

        String formattedValoare = String.format(Locale.getDefault(), "%.2f", venit.getValoare());
        tvValoareV.setText(formattedValoare);

        return view;
    }
}
