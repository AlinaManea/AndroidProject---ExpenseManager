package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Callback;
import ro.ase.grupa1094.Clase.Tranzactie;
import ro.ase.grupa1094.FirebaseServiceTranz;
import ro.ase.grupa1094.R;
import ro.ase.grupa1094.Tip;

public class ActivTranzactieFirebase extends AppCompatActivity {
    private Spinner spnCategorie;
    private EditText etDescriere;
    private EditText etValoare;
    private Button btnGolireDate;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabDelete;
    private ListView lvTranzactii;
    private List<Tranzactie> tranzactii = new ArrayList<>();
    private FirebaseServiceTranz firebaseService;
    private int indexTranzactieSelectata = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activ_tranzactie_firebase);

        getSupportActionBar().setTitle("FinApp");

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        initComponente();
        firebaseService = FirebaseServiceTranz.getInstance();
         firebaseService.addTranzactiiListener(dataChangeCallback());
    }

    private Callback<List<Tranzactie>> dataChangeCallback() {
        return rezultat -> {
            tranzactii.clear();
            tranzactii.addAll(rezultat);
            ArrayAdapter<Tranzactie> adapter = (ArrayAdapter<Tranzactie>) lvTranzactii.getAdapter();
            adapter.notifyDataSetChanged();
            golireText();
        };
    }

    private void initComponente() {
        spnCategorie = findViewById(R.id.spnCategroie);
        etDescriere = findViewById(R.id.etDescriere);
        etValoare = findViewById(R.id.etValoare);
        btnGolireDate = findViewById(R.id.btnGolireDate);
        fabSave = findViewById(R.id.fabSave);
        fabDelete = findViewById(R.id.fabDelete);
        lvTranzactii = findViewById(R.id.lvTranzactii);


        ArrayAdapter<CharSequence> adapterCategorie = ArrayAdapter.createFromResource(this,
                R.array.tranzactii, android.R.layout.simple_spinner_dropdown_item);
        spnCategorie.setAdapter(adapterCategorie);

        ArrayAdapter<Tranzactie> adapterTranzactii = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tranzactii);
        lvTranzactii.setAdapter(adapterTranzactii);

        btnGolireDate.setOnClickListener(golireDateEventListener());
        fabSave.setOnClickListener(saveEventListener());
        fabDelete.setOnClickListener(deleteEventListener());

        lvTranzactii.setOnItemClickListener(tranzactieSelectataEventListener());
    }

    private AdapterView.OnItemClickListener tranzactieSelectataEventListener() {
        return (adapterView, view, position, param) -> {
            indexTranzactieSelectata = position;
            Tranzactie tranzactie = tranzactii.get(position);

            String categorie = tranzactie.getTip().name();
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spnCategorie.getAdapter();
            int indexCategorie = adapter.getPosition(categorie);
            spnCategorie.setSelection(indexCategorie);
            etDescriere.setText(tranzactie.getDescriere());
            etValoare.setText(String.valueOf(tranzactie.getValoare()));
        };
    }

    private View.OnClickListener deleteEventListener() {
        return view -> {
            if (indexTranzactieSelectata != -1) {
                firebaseService.delete(tranzactii.get(indexTranzactieSelectata));
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return view -> {
            if (validare()) {
                if (indexTranzactieSelectata == -1) {
                    Tranzactie tranzactie = actualizareTranzactieFromUI(null);
                    firebaseService.insert(tranzactie);
                } else {
                    Tranzactie tranzactie = actualizareTranzactieFromUI(tranzactii.get(indexTranzactieSelectata).getId());
                    firebaseService.update(tranzactie);
                }
            }
        };
    }

    private Tranzactie actualizareTranzactieFromUI(String id) {
        Tranzactie tranzactie = new Tranzactie();
        tranzactie.setId(id);
        String categorie = spnCategorie.getSelectedItem().toString();
        tranzactie.setTip(Tip.valueOf(categorie.toUpperCase()));
        tranzactie.setDescriere(etDescriere.getText().toString());
        tranzactie.setValoare(Integer.valueOf(etValoare.getText().toString()));
        return tranzactie;
    }

    private boolean validare() {
        if (etDescriere.getText() == null || etDescriere.getText().toString().isEmpty() ||
                etValoare.getText() == null || etValoare.getText().toString().isEmpty()) {
            Toast.makeText(this, "Validarea nu a trecut", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener golireDateEventListener() {
        return view -> golireText();
    }

    private void golireText() {
        spnCategorie.setSelection(0);
        etDescriere.setText(null);
        etValoare.setText(null);
        indexTranzactieSelectata = -1;
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
