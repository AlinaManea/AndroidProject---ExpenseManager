package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.ase.grupa1094.Categorie;
import ro.ase.grupa1094.Categoriee;
import ro.ase.grupa1094.Clase.Cheltuiala;
import ro.ase.grupa1094.Clase.Venit;
import ro.ase.grupa1094.FinAppDatabase;
import ro.ase.grupa1094.R;

public class ActivitateAdaugareVenChelt extends AppCompatActivity {
enum CategorieVen{
    Salariu, Chirie, Bonus, Investitie, Economii
}

enum CategorieCh{
    Alimente, Chirie, Utilitati, Transport, Sanatate, Educatie, Divertisment
}
    private boolean isEditingVenit = false;
    private boolean isEditingChelt = false;
    Spinner spnVen;
    Spinner spnCh;
    EditText etDescriereVen;
    EditText etValoareVen;
    EditText etDescriereCh;
    EditText etValoareCh;
    Button btnVen;
    Button btnCh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activitate2);
        getSupportActionBar().setTitle("FinApp");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVen=findViewById(R.id.btnSaveVenit);
        btnCh=findViewById(R.id.btnSaveChelt);
        etDescriereVen=findViewById(R.id.etDescriereVen);
        etDescriereCh=findViewById(R.id.etDescriereCh);
        etValoareVen=findViewById(R.id.etValoareVen);
        etValoareCh=findViewById(R.id.etValoareCh);
        spnVen=findViewById(R.id.spnVen);
        spnCh=findViewById(R.id.spnChelt);

        SharedPreferences sharedPref = getSharedPreferences("User", MODE_PRIVATE);
        long userId = sharedPref.getLong("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Eroare: Utilizator neautentificat!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
//        Intent editIntent = getIntent();
//        if (editIntent.hasExtra("editVenit")) {
//            Venit editVenit = (Venit) editIntent.getSerializableExtra("editVenit");
//            Toast.makeText(this, editVenit.toString(), Toast.LENGTH_SHORT).show();
//
//        }
//
//
//        if (editIntent.hasExtra("editCheltuiala")) {
//            Cheltuiala editCheltuiala = (Cheltuiala) editIntent.getSerializableExtra("editCheltuiala");
//            Toast.makeText(this, editCheltuiala.toString(), Toast.LENGTH_SHORT).show();
//        }

        //dropdown categorie la venituri
        String[] valoriCategVen = new String[CategorieVen.values().length];
        int nrValori = 0;
        for (CategorieVen c : CategorieVen.values()) {
            valoriCategVen[nrValori++] = c.toString();
        }
        ArrayAdapter<String> genAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valoriCategVen);
        spnVen.setAdapter(genAdapter);

        //dropdown categorie la cheltuieli
        String[] valoriCategCh = new String[CategorieCh.values().length];
        int nrValori2 = 0;
        for (CategorieCh c : CategorieCh.values()) {
            valoriCategCh[nrValori2++] = c.toString();
        }
        ArrayAdapter<String> genAdapter2 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valoriCategCh);
        spnCh.setAdapter(genAdapter2);


        Intent editIntent = getIntent();
        if(editIntent.hasExtra("editVenit"))
        {
            isEditingVenit=true;
            Venit editVenit=(Venit) editIntent.getSerializableExtra("editVenit");
            ArrayAdapter<String> editedAdapter = (ArrayAdapter<String>) spnVen.getAdapter();
            for (int i = 0; i < editedAdapter.getCount(); i++) {
                if (editVenit.getCategorie().toString().equals(editedAdapter.getItem(i))) {
                    spnVen.setSelection(i);
                }
            }
            etDescriereVen.setText(editVenit.getDescriere());
            etValoareVen.setText(String.valueOf(editVenit.getValoare()));
        }


        if(editIntent.hasExtra("editCheltuiala"))
        {
            isEditingChelt=true;
            Cheltuiala editCheltuiala=(Cheltuiala) editIntent.getSerializableExtra("editCheltuiala");
            ArrayAdapter<String> editedAdapter = (ArrayAdapter<String>) spnCh.getAdapter();
            for (int i = 0; i < editedAdapter.getCount(); i++) {
                if (editCheltuiala.getCategorie().toString().equals(editedAdapter.getItem(i))) {
                    spnCh.setSelection(i);
                }
            }
            etDescriereCh.setText(editCheltuiala.getDescriere());
            etValoareCh.setText(String.valueOf(editCheltuiala.getValoare()));
        }


        //Obiect pt clasa Venit
        btnVen.setOnClickListener(view->
        {
            Categoriee categorieVen=Categoriee.valueOf(spnVen.getSelectedItem().toString());
            String  descriereVen =etDescriereVen.getText().toString();
            float valoareVen=Float.parseFloat(etValoareVen.getText().toString());


            Venit venit=new Venit(categorieVen,descriereVen,valoareVen);
            venit.setUserId(userId);

            Intent intent = getIntent();

            if(isEditingVenit){
                intent.putExtra("editVenit",  venit);
                isEditingVenit=false;}
            else {
                intent.putExtra("venitAdaugat", venit);
            }
          setResult(RESULT_OK, intent);
          finish();
        });

        //Obiect pt clasa Cheltuiala
        btnCh.setOnClickListener(view->
        {
            Categorie categorieCh=Categorie.valueOf(spnCh.getSelectedItem().toString());
            String  descriereCh =etDescriereCh.getText().toString();
            float valoareCh=Float.parseFloat(etValoareCh.getText().toString());


            Cheltuiala cheltuiala=new Cheltuiala(categorieCh,descriereCh,valoareCh);
            cheltuiala.setUserId(userId);

            Toast.makeText(this, cheltuiala.toString(), Toast.LENGTH_SHORT).show();

            Intent intent = getIntent();

            if(isEditingChelt){
                intent.putExtra("editCheltuiala",  cheltuiala);
                isEditingChelt=false;}
            else {
               intent.putExtra("cheltuialaAdaugata", cheltuiala);
            }

            setResult(RESULT_OK, intent);
            finish();

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.idLogIn) {
            Intent intentLogIn = new Intent(this, MainActivity.class);
            startActivity(intentLogIn);
            return true;
        }

        if(item.getItemId() == R.id.idTranzactii){
            Intent intent4=new Intent(this, ActivitateTranzactii.class);
            startActivity(intent4);
            return true;
        }

        if (item.getItemId() == R.id.idAdaugare) {
            Intent intent2= new Intent(this, ActivitateAdaugareVenChelt.class);
            startActivity(intent2);
            return true;
        }

        if (item.getItemId() == R.id.idDespreNoi) {
            Intent intent3= new Intent(this, ActivitateSetari.class);
            startActivity(intent3);
            return true;
        }

        if (item.getItemId() == R.id.idSold) {


            Intent intent4= new Intent(this, CalculSoldUtilizator.class);
            startActivity(intent4);
            return true;
        }

        if (item.getItemId() == R.id.idTranzFirebase) {


            Intent intent5= new Intent(this, ActivTranzactieFirebase.class);
            startActivity(intent5);
            return true;
        }


        if (item.getItemId() == R.id.idChelt) {


            Intent intent5= new Intent(this, ListaCheltuieliViitoare.class);
            startActivity(intent5);
            return true;
        }
        return true;
    }


    }