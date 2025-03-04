package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.ase.grupa1094.Clase.Inregistrare;
import ro.ase.grupa1094.Clase.Utilizator;
import ro.ase.grupa1094.DAO.UtilizatorDAO;
import ro.ase.grupa1094.FinAppDatabase;
import ro.ase.grupa1094.FirebaseServiceUtilizator;
import ro.ase.grupa1094.R;

public class CreareContNou extends AppCompatActivity {

    EditText nume;
    EditText prenume;
    EditText telf;
    EditText data;
    EditText mail;
    EditText numeUtiliz;
    EditText parola;
    Button btninregistrare;
    Button btnInapoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creare_cont_nou);

        getSupportActionBar().setTitle("FinApp");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        nume = findViewById(R.id.etnume);
        prenume = findViewById(R.id.etprenume);
        telf = findViewById(R.id.etTelefon);
        data = findViewById(R.id.etDataNasterii);
        mail = findViewById(R.id.etMail);
        numeUtiliz = findViewById(R.id.etNumeUtiliz);
        parola = findViewById(R.id.etparola);
        btninregistrare = findViewById(R.id.btnInreg);
        btnInapoi = findViewById(R.id.btnInapoi);


        btninregistrare.setOnClickListener(v -> {
            String numee = nume.getText().toString();
            String prenumeUtilizator = prenume.getText().toString();
            String telefon = telf.getText().toString();
            String dataNast = data.getText().toString();
            String email = mail.getText().toString();
            String numeUtilizator = numeUtiliz.getText().toString();
            String parolaUtilizator = parola.getText().toString();

            if (!email.contains("@")) {
                Toast.makeText(CreareContNou.this, "Introduceți un email valid!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (telefon.length() != 10 || !telefon.matches("\\d+")) {
                Toast.makeText(CreareContNou.this, "Introduceți un număr de telefon valid!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (numee.isEmpty() || prenumeUtilizator.isEmpty() || parolaUtilizator.isEmpty() ||
                    telefon.isEmpty() || dataNast.isEmpty() || email.isEmpty() || numeUtilizator.isEmpty()) {
                Toast.makeText(CreareContNou.this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show();
            } else {
                Inregistrare utilizInreg = new Inregistrare(numee, prenumeUtilizator, telefon, dataNast, numeUtilizator, email, parolaUtilizator);
                FirebaseServiceUtilizator.getInstance().insert(utilizInreg);

                new Thread(() -> {
                    FinAppDatabase db = FinAppDatabase.getInstance(getApplicationContext());
                    UtilizatorDAO utilizatorDAO = db.getUtilizatorDAO();


                    Utilizator utilizatorRoom = new Utilizator(numeUtilizator, parolaUtilizator);
                    utilizatorDAO.insertUtilizator(utilizatorRoom);

                    runOnUiThread(() -> {
                        Toast.makeText(CreareContNou.this, "Bun venit, " + numeUtilizator + "!", Toast.LENGTH_LONG).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("userId", utilizatorRoom.getId());
                        editor.apply();

                        Intent intent = new Intent(CreareContNou.this, MainActivity.class);
                        intent.putExtra("UtilizatorInregistrat", utilizInreg);
                        startActivity(intent);
                    });
                }).start();
            }
        });

        btnInapoi.setOnClickListener(view -> {
            Intent intent = new Intent(CreareContNou.this, MainActivity.class);
            startActivity(intent);
        });
    }
}




