package ro.ase.grupa1094.Adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ro.ase.grupa1094.R;
import ro.ase.grupa1094.Clase.Tranzactie;

public class SoldAdapter extends ArrayAdapter<Tranzactie> {
    private Context context;
    private int layoutId;
    private List<Tranzactie> tranzactieList;
    private LayoutInflater inflater;


    public SoldAdapter(@NonNull Context context, int resource, @NonNull List<Tranzactie> tranzactieList, LayoutInflater inflater) {
        super(context, resource, tranzactieList);
        this.context=context;
        this.layoutId=resource;
        this.tranzactieList=tranzactieList;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=inflater.inflate(layoutId,parent,false);
        Tranzactie tranzactie =tranzactieList.get(position);


       TextView tvTip=view.findViewById(R.id.tvTip);
        TextView tvDescriere=view.findViewById(R.id.tvDescriereT);
        TextView tvValoare=view.findViewById(R.id.tvValoareT);


        tvTip.setText(String.valueOf(tranzactie.getTip()));
        tvDescriere.setText(tranzactie.getDescriere());
        tvValoare.setText(String.valueOf(tranzactie.getValoare()));


        return view;
    }
}
