package ro.ase.grupa1094.Activitati;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Adaptere.CheltuialaAdapter;
import ro.ase.grupa1094.Adaptere.VenitAdapter;
import ro.ase.grupa1094.Categorie;
import ro.ase.grupa1094.Categoriee;
import ro.ase.grupa1094.Clase.Cheltuiala;
import ro.ase.grupa1094.Clase.Venit;
import ro.ase.grupa1094.FinAppDatabase;
import ro.ase.grupa1094.HttpsManager;
import ro.ase.grupa1094.Parsere.CheltuialaParser;
import ro.ase.grupa1094.Parsere.VenitParser;
import ro.ase.grupa1094.R;

public class ActivitateTranzactii extends AppCompatActivity {

    private static final String urlCheltuieli = "https://jsonkeeper.com/b/ARXE";
    private static final String urlVenit="https://jsonkeeper.com/b/1QB0";
    ListView venituriListView;
    ListView cheltuieliListView;
    List<Venit> venituri=new ArrayList<>();
    List<Cheltuiala> cheltuieli=new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;
    private int pozitieVenInList;
    private int pozitieCheltInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitate_tranzactii);

        getSupportActionBar().setTitle("FinApp");
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        venituriListView = findViewById(R.id.listaVenituri);
        cheltuieliListView = findViewById(R.id.listaCheltuieli);

                //SharedPreferences
//                SharedPreferences sharedPreferencesNotif = getSharedPreferences("SetariUtilizator", MODE_PRIVATE);
//                boolean notificariActive = sharedPreferencesNotif.getBoolean("notificariActive", false);
//                String feedback = sharedPreferencesNotif.getString("feedback", "Niciun feedback oferit încă.");
//
//
//                if (notificariActive) {
//                    Toast.makeText(this, "Notificările sunt active", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Notificările sunt dezactivate", Toast.LENGTH_SHORT).show();
//                }
//
//                Toast.makeText(this, "Ultimul feedback: " + feedback, Toast.LENGTH_LONG).show();

        FloatingActionButton fabOpen = findViewById(R.id.fabOpen);
        fabOpen.setOnClickListener(v -> {
            Intent intentAdaugare = new Intent(getApplicationContext(), ActivitateAdaugareVenChelt.class);
            launcher.launch(intentAdaugare);
        });




        Button btnSoldCalcul = findViewById(R.id.btnCalculSold);
        btnSoldCalcul.setOnClickListener(v ->
        {   double venituriTotale = 0;
            double cheltuieliTotale = 0;

            for (Venit venit : venituri) {
                venituriTotale += venit.getValoare();}

            for (Cheltuiala cheltuiala : cheltuieli) {
                cheltuieliTotale += cheltuiala.getValoare();}

            double sold = venituriTotale - cheltuieliTotale;

            double procentCheltuieli = 0.0;
            if (venituriTotale != 0) {
                procentCheltuieli = (cheltuieliTotale / venituriTotale) * 100;
            }
            else {
            procentCheltuieli = 0.0;
        }
            Intent intent = new Intent(getApplicationContext(), CalculSoldUtilizator.class);
           intent.putExtra("venTotale", venituriTotale);
           intent.putExtra("cheltTotale", cheltuieliTotale);
           intent.putExtra("sold", sold);
           intent.putExtra("procentCheltuieli", procentCheltuieli);
           startActivity(intent);
        });

        initComponente();
        incarcareCheltuieliDinRetea();
        incarcareVenituriDinRetea();

        FinAppDatabase database = FinAppDatabase.getInstance(getApplicationContext());

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId == -1) {
            Toast.makeText(getApplicationContext(), "Nu există un utilizator logat!", Toast.LENGTH_SHORT).show();
        } else {
            venituri = database.getVenitDAO().getVenituriByUserId(userId);
            VenitAdapter venituriAdapter = new VenitAdapter(getApplicationContext(), R.layout.view_venit, venituri, getLayoutInflater());
            venituriListView.setAdapter(venituriAdapter);

            cheltuieli = database.getCheltuialaDAO().getCheltuieliByUserId(userId);
            CheltuialaAdapter cheltuieliAdapter = new CheltuialaAdapter(getApplicationContext(), R.layout.view_cheltuiala, cheltuieli, getLayoutInflater());
            cheltuieliListView.setAdapter(cheltuieliAdapter);
        }

        //EDITARE
        venituriListView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            pozitieVenInList = position;
            Intent intent = new Intent(getApplicationContext(), ActivitateAdaugareVenChelt.class);
            intent.putExtra("editVenit", venituri.get(pozitieVenInList));
            launcher.launch(intent);
            return true;
        });

        cheltuieliListView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            pozitieCheltInList = position;
            Intent intent = new Intent(getApplicationContext(), ActivitateAdaugareVenChelt.class);
            intent.putExtra("editCheltuiala", cheltuieli.get(pozitieCheltInList));
            launcher.launch(intent);
            return true;
        });

        //INSERT - prin activitatea de adaugare
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent data = result.getData();

                if (data.hasExtra("venitAdaugat")) {
                    Venit venitAdaugat = (Venit) data.getSerializableExtra("venitAdaugat");
                    if (!venituri.contains(venitAdaugat)) {
                        venituri.add(venitAdaugat);
                        database.getVenitDAO().insertVenit(venitAdaugat);

                        VenitAdapter venituriAdapter = (VenitAdapter) venituriListView.getAdapter();
                        venituriAdapter.notifyDataSetChanged();
                    }
                } else if (data.hasExtra("editVenit")) {
                    Venit venit = (Venit) data.getSerializableExtra("editVenit");
                    if (venit != null) {
                        Venit editedVenit = venituri.get(pozitieVenInList);
                        editedVenit.setCategorie(venit.getCategorie());
                        editedVenit.setDescriere(venit.getDescriere());
                        editedVenit.setValoare(venit.getValoare());

                        venituri.set(pozitieVenInList, editedVenit);

                        database.getVenitDAO().updateVenit(editedVenit.getId(), editedVenit.getCategorie(), editedVenit.getDescriere(), editedVenit.getValoare());

                    }
                    VenitAdapter venituriAdapter = (VenitAdapter) venituriListView.getAdapter();
                    venituriAdapter.notifyDataSetChanged();
                }

                if (data.hasExtra("cheltuialaAdaugata")) {
                    Cheltuiala cheltuialaAdaugata = (Cheltuiala) data.getSerializableExtra("cheltuialaAdaugata");
                    if (!cheltuieli.contains(cheltuialaAdaugata)) {
                        cheltuieli.add(cheltuialaAdaugata);
                        database.getCheltuialaDAO().insertCheltuiala(cheltuialaAdaugata);

                        CheltuialaAdapter cheltuialaAdapter = (CheltuialaAdapter) cheltuieliListView.getAdapter();
                        cheltuialaAdapter.notifyDataSetChanged();
                    }
                } else if (data.hasExtra("editCheltuiala")) {
                    Cheltuiala cheltuiala = (Cheltuiala) data.getSerializableExtra("editCheltuiala");

                    if (cheltuiala != null) {
                        Cheltuiala editedCheltuiala = cheltuieli.get(pozitieCheltInList);
                        editedCheltuiala.setCategorie(cheltuiala.getCategorie());
                        editedCheltuiala.setDescriere(cheltuiala.getDescriere());
                        editedCheltuiala.setValoare(cheltuiala.getValoare());

                        cheltuieli.set(pozitieCheltInList, editedCheltuiala);

                        database.getCheltuialaDAO().updateCheltuiala(editedCheltuiala.getId(), editedCheltuiala.getCategorie(), editedCheltuiala.getDescriere(), editedCheltuiala.getValoare());
                    }
                    CheltuialaAdapter cheltuialaAdapter = (CheltuialaAdapter) cheltuieliListView.getAdapter();
                    cheltuialaAdapter.notifyDataSetChanged();
                }
            }
        });

        //STERGERE
        venituriListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Venit venitSelectat = venituri.get(position);
            venituri.remove(venitSelectat);
            database.getVenitDAO().deleteVenitById(venitSelectat.getId());

            VenitAdapter venituriAdapter = (VenitAdapter) venituriListView.getAdapter();
            venituriAdapter.notifyDataSetChanged();
        });

        cheltuieliListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Cheltuiala cheltuialaSelectata = cheltuieli.get(position);
            cheltuieli.remove(cheltuialaSelectata);
            database.getCheltuialaDAO().deleteCheltuialaById(cheltuialaSelectata.getId());

            CheltuialaAdapter cheltuialaAdapter = (CheltuialaAdapter) cheltuieliListView.getAdapter();
            cheltuialaAdapter.notifyDataSetChanged();
        });
    }

     //PRELUARE DIN RETEA
    private void incarcareCheltuieliDinRetea() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpsManager httpsManager = new HttpsManager(urlCheltuieli);
                String rezultat = httpsManager.procesare();
                new Handler(getMainLooper()).post(() -> {
                    preluareCheltuieliDinJson(rezultat);
                });
            }
        };
        thread.start();
    }
    private void incarcareVenituriDinRetea() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpsManager httpsManager = new HttpsManager(urlVenit);
                String rezultat = httpsManager.procesare();
                new Handler(getMainLooper()).post(() -> {
                    preluareVenituriDinJson(rezultat);
                });
            }
        };
        thread.start();
    }

    private void preluareCheltuieliDinJson(String json) {
        cheltuieli.addAll(CheltuialaParser.parsareJson(json));
        CheltuialaAdapter cheltuieliAdapter = (CheltuialaAdapter) cheltuieliListView.getAdapter();
        cheltuieliAdapter.notifyDataSetChanged();
    }


    private void preluareVenituriDinJson(String json) {
        venituri.addAll(VenitParser.parsareJson(json));
        VenitAdapter venituriAdapter = (VenitAdapter) venituriListView.getAdapter();
        venituriAdapter.notifyDataSetChanged();
    }


    private void initComponente() {
        CheltuialaAdapter cheltuieliAdapter=new CheltuialaAdapter(getApplicationContext(), R.layout.view_cheltuiala, cheltuieli,getLayoutInflater());
        cheltuieliListView.setAdapter(cheltuieliAdapter);


        VenitAdapter venituriAdapter=new VenitAdapter(getApplicationContext(), R.layout.view_venit, venituri,getLayoutInflater());
        venituriListView.setAdapter(venituriAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
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


        if (item.getItemId() == R.id.idSold) {


            Intent intent4= new Intent(this, CalculSoldUtilizator.class);
            startActivity(intent4);
            return true;
        }

        if (item.getItemId() == R.id.idTranzFirebase) {


            Intent intent5 = new Intent(this, ActivTranzactieFirebase.class);
            startActivity(intent5);
            return true;
        }

        if (item.getItemId() == R.id.idChelt) {


            Intent intent5 = new Intent(this, ListaCheltuieliViitoare.class);
            startActivity(intent5);
            return true;
        }
        return true;
    }}

