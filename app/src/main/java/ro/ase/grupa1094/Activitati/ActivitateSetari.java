package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ro.ase.grupa1094.Clase.Feedback;
import ro.ase.grupa1094.Activitati.FeedbackTrimis;
import ro.ase.grupa1094.R;

public class ActivitateSetari extends AppCompatActivity {

    CheckBox checkBoxNotificari;
    EditText textFeedback;
    Button btnFeedback;
    Button btnLogout;
    private ArrayList<Feedback> feedbackList = new ArrayList<>();
    private ActivityResultLauncher<Intent> feedbackLauncher;
    SharedPreferences sharedPreferences;
    Button btnViewFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activitate3);
        getSupportActionBar().setTitle("FinApp");

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        checkBoxNotificari = findViewById(R.id.checkBoxNotificari);
        btnFeedback = findViewById(R.id.btnFeedback);
        btnLogout = findViewById(R.id.btnLogout);

        RadioGroup rgValuta = findViewById(R.id.rgValuta);
        RadioButton rbRON = findViewById(R.id.rgRON);
        RadioButton rbEURO = findViewById(R.id.rgEURO);

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String savedCurrency = sharedPreferences.getString("valuta", "RON");

        if (savedCurrency.equals("RON")) {
            rbRON.setChecked(true);
        } else if (savedCurrency.equals("EURO")) {
            rbEURO.setChecked(true);
        }

        rgValuta.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (checkedId == R.id.rgRON) {
                editor.putString("valuta", "RON");
                Toast.makeText(this, "Ai selectat RON!", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.rgEURO) {
                editor.putString("valuta", "EURO");
                Toast.makeText(this, "Ai selectat EURO!", Toast.LENGTH_SHORT).show();
            }
            editor.apply();
        });



        btnViewFeedback=findViewById(R.id.btnVizualizareFeedback);
        btnViewFeedback.setOnClickListener(v->
        {
            Intent intentVwFb=new Intent(getApplicationContext(), ActivitateFeedback.class);
            startActivity(intentVwFb);
        });

        feedbackLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent != null) {
                    List<Feedback> receivedFeedbackList = (ArrayList<Feedback>) intent.getSerializableExtra("feedbackList");

                }
            }
        });


        boolean notificariActive = sharedPreferences.getBoolean("notificariActive", false);
        checkBoxNotificari.setChecked(notificariActive);


        checkBoxNotificari.setOnClickListener(view -> {
            boolean isChecked = checkBoxNotificari.isChecked();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("notificariActive", isChecked);
            editor.apply();
            Toast.makeText(ActivitateSetari.this, isChecked ? "Notificările sunt deschise!" : "Notificările sunt închise!", Toast.LENGTH_LONG).show();
        });

        EditText textFeedback=findViewById(R.id.etFeedback);
        EditText ratingg=findViewById(R.id.etRating);

        // Trimitere feedback
        btnFeedback.setOnClickListener(view -> {
            String mesaj = textFeedback.getText().toString();
            int rating = Integer.parseInt(ratingg.getText().toString());

            if (!mesaj.isEmpty()) {
                String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                Feedback feedback = new Feedback(mesaj, formattedDate,rating);
                feedbackList.add(feedback);

                //
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("feedback", mesaj);
                editor.putFloat("rating", rating);
                editor.apply();


                Intent intent = new Intent(ActivitateSetari.this, FeedbackTrimis.class);
                intent.putExtra("feedbackList", feedbackList);
                feedbackLauncher.launch(intent);
            } else {
                Toast.makeText(ActivitateSetari.this, "Nu ați completat câmpul de feedback!", Toast.LENGTH_SHORT).show();
            }
        });


        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(ActivitateSetari.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

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
