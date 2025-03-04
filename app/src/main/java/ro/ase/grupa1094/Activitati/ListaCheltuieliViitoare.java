package ro.ase.grupa1094.Activitati;

import static ro.ase.grupa1094.FirebaseServiceListaCumparaturiViit.firebaseService;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Callback;
import ro.ase.grupa1094.Clase.CheltuialaViitoare;
import ro.ase.grupa1094.Clase.Necesitate;
import ro.ase.grupa1094.FirebaseServiceListaCumparaturiViit;
import ro.ase.grupa1094.FirebaseServiceTranz;
import ro.ase.grupa1094.R;

public class ListaCheltuieliViitoare extends AppCompatActivity {
   ListView lvCheltuieliViitoare;
   EditText etDenumireProdus;
   EditText etMagazin;
   EditText etPret;
   RadioGroup rgNecesitate;
   FloatingActionButton fabSave2;
   FloatingActionButton fabDelete2;
   Button btnGolireDate2;
   private FirebaseServiceListaCumparaturiViit firebaseService;
   List<CheltuialaViitoare> cheltuieli=new ArrayList<>();
    private int indexCheltuialaSelectata = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        getSupportActionBar().setTitle("FinApp");
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        setContentView(R.layout.activity_lista_cheltuieli_viitoare);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        initComponente();
        firebaseService = FirebaseServiceListaCumparaturiViit.getInstance();
        firebaseService.addPlanificariListener(dataChangeCallback());
    }

    private Callback<List<CheltuialaViitoare>> dataChangeCallback() {
        return rezultat -> {
            cheltuieli.clear();
            cheltuieli.addAll(rezultat);
            ArrayAdapter<CheltuialaViitoare> adapter = (ArrayAdapter<CheltuialaViitoare>) lvCheltuieliViitoare.getAdapter();
            adapter.notifyDataSetChanged();
            golireText();
        };
    }

    private void initComponente() {
        etDenumireProdus = findViewById(R.id.etDenumireProdus);
        etMagazin = findViewById(R.id.etMagazin);
        etPret = findViewById(R.id.etPret);
        rgNecesitate = findViewById(R.id.rgNecesitate);
        fabSave2 = findViewById(R.id.fabSave2);
        fabDelete2 = findViewById(R.id.fabDelete2);
        btnGolireDate2 = findViewById(R.id.btnGolireDate2);
        lvCheltuieliViitoare = findViewById(R.id.lvCheltuieliViitoare);

        ArrayAdapter<CheltuialaViitoare> adapterCheltuieli = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cheltuieli);
        lvCheltuieliViitoare.setAdapter(adapterCheltuieli);

        btnGolireDate2.setOnClickListener(golireDateEventListener());
        fabSave2.setOnClickListener(saveEventListener());
        fabDelete2.setOnClickListener(deleteEventListener());

        lvCheltuieliViitoare.setOnItemClickListener(cheltuialaSelectataEventListener());
    }

    private AdapterView.OnItemClickListener cheltuialaSelectataEventListener() {
        return (adapterView, view, position, param) -> {
            indexCheltuialaSelectata = position;
            CheltuialaViitoare cheltuiala = cheltuieli.get(position);

            etDenumireProdus.setText(cheltuiala.getDenumireProdus());
            etMagazin.setText(cheltuiala.getMagazin());
            etPret.setText(String.valueOf(cheltuiala.getPret()));

            if (cheltuiala.getEsteNecesar() == Necesitate.DA) {
                RadioButton radioDa = findViewById(R.id.rbDA);
                if (radioDa != null) {
                    radioDa.setChecked(true);
                }
            } else {
                RadioButton radioNu = findViewById(R.id.rbNU);
                if (radioNu != null) {
                    radioNu.setChecked(true);
                }
            }
        };
    }

    private View.OnClickListener deleteEventListener() {
        return view -> {
            if (indexCheltuialaSelectata != -1) {
                firebaseService.delete(cheltuieli.get(indexCheltuialaSelectata));
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return view -> {
            if (validare()) {
                if (indexCheltuialaSelectata == -1) {
                    CheltuialaViitoare cheltuiala = actualizareCheltuialaFromUI(null);
                    firebaseService.insert(cheltuiala);
                } else {
                    CheltuialaViitoare cheltuiala = actualizareCheltuialaFromUI(cheltuieli.get(indexCheltuialaSelectata).getId());
                    firebaseService.update(cheltuiala);
                }
            }
        };
    }

    private CheltuialaViitoare actualizareCheltuialaFromUI(String id) {
        CheltuialaViitoare cheltuiala = new CheltuialaViitoare();
        cheltuiala.setId(id);
        cheltuiala.setDenumireProdus(etDenumireProdus.getText().toString());
        cheltuiala.setMagazin(etMagazin.getText().toString());
        cheltuiala.setPret(Float.valueOf(etPret.getText().toString()));

        int selectedId = rgNecesitate.getCheckedRadioButtonId();
        cheltuiala.setEsteNecesar(selectedId == R.id.rgNecesitate? Necesitate.DA : Necesitate.NU);

        return cheltuiala;
    }

    private boolean validare() {
        if (etDenumireProdus.getText() == null || etDenumireProdus.getText().toString().isEmpty() ||
                etMagazin.getText() == null || etMagazin.getText().toString().isEmpty() ||
                etPret.getText() == null || etPret.getText().toString().isEmpty()) {
            Toast.makeText(this, "Toate câmpurile sunt obligatorii!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener golireDateEventListener() {
        return view -> golireText();
    }

    private void golireText() {
        etDenumireProdus.setText(null);
        etMagazin.setText(null);
        etPret.setText(null);
        rgNecesitate.clearCheck();
        indexCheltuialaSelectata = -1;
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


        if (item.getItemId() == R.id.idTranzactii) {


            Intent intent4 = new Intent(this, ActivitateTranzactii.class);
            startActivity(intent4);
            return true;
        }


        if (item.getItemId() == R.id.idAdaugare) {
            Intent intent2 = new Intent(this, ActivitateAdaugareVenChelt.class);
            startActivity(intent2);
            return true;
        }
        if (item.getItemId() == R.id.idDespreNoi) {


            Intent intent3 = new Intent(this, ActivitateSetari.class);
            startActivity(intent3);
            return true;
        }

        if (item.getItemId() == R.id.idTranzFirebase) {

            Intent intent5 = new Intent(this, ActivTranzactieFirebase.class);
            startActivity(intent5);
            return true;
        }

        if (item.getItemId() == R.id.idChelt) {
            Intent intent6 = new Intent(this, ListaCheltuieliViitoare.class);
            startActivity(intent6);
            return true;
        }
        return true;

    }
}