package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ro.ase.grupa1094.R;

public class CalculSoldUtilizator extends AppCompatActivity {
  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_sold_utilizator);

        getSupportActionBar().setTitle("FinApp");
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        double sold = 0;
        double procentCheltuieli = 0;
        double venTotale = 0;
        double cheltTotale = 0;

        sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String valuta = sharedPreferences.getString("valuta", "RON");

        Intent intent = getIntent();
        if (intent.hasExtra("sold")) {
            sold = intent.getDoubleExtra("sold", 0);
        }
        if (intent.hasExtra("procentCheltuieli")) {
            procentCheltuieli = intent.getDoubleExtra("procentCheltuieli", 0);
        }
        if (intent.hasExtra("venTotale")) {
            venTotale = intent.getDoubleExtra("venTotale", 0);
        }
        if (intent.hasExtra("cheltTotale")) {
            cheltTotale = intent.getDoubleExtra("cheltTotale", 0);
        }

        String procentFormatat = String.format("%.2f", procentCheltuieli);

        TextView tvSold = findViewById(R.id.tvSold);
        tvSold.setText("Soldul curent: " + sold + " " + valuta);

        TextView tvVenituri = findViewById(R.id.tvVenit);
        tvVenituri.setText("Total venituri: " + venTotale + " " + valuta);

        TextView tvCheltuieli = findViewById(R.id.tvCheltuiala);
        tvCheltuieli.setText("Total cheltuieli: " + cheltTotale + " " + valuta);

        ProgressBar pbProgresCheltuieli = findViewById(R.id.pbProgresCheltuieli);
        pbProgresCheltuieli.setProgress((int) procentCheltuieli);

        TextView tvProcentCheltuieli = findViewById(R.id.tvProcentCheltuieli);
        tvProcentCheltuieli.setText("Cheltuieli: " + procentFormatat + "% din venituri");

        Button btnInapoi = findViewById(R.id.btnInapoiDeLaSold);
        btnInapoi.setOnClickListener(v -> {
            Intent intentInapoi = new Intent(getApplicationContext(), ActivitateTranzactii.class);
            startActivity(intentInapoi);
        });

    }
}
