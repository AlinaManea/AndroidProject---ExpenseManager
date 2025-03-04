package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ro.ase.grupa1094.Clase.Utilizator;
import ro.ase.grupa1094.DAO.UtilizatorDAO;
import ro.ase.grupa1094.FinAppDatabase;
import ro.ase.grupa1094.R;

public class MainActivity extends AppCompatActivity {
    EditText etUtilizator;
    EditText etParola;
    CheckBox checkBoxParola;
    Button btnLogin;
    Button btnContNou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("FinApp");
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        etUtilizator = findViewById(R.id.etUtilizator);
        etParola = findViewById(R.id.etParola);
        checkBoxParola = findViewById(R.id.cbxParola);
        btnLogin = findViewById(R.id.btnLogin);
        btnContNou = findViewById(R.id.btnContNou);

        FinAppDatabase db = FinAppDatabase.getInstance(getApplicationContext());
        UtilizatorDAO utilizatorDAO = db.getUtilizatorDAO();

        btnLogin.setOnClickListener(v -> {
            String numeUtilizator = etUtilizator.getText().toString();
            String parola = etParola.getText().toString();


            if (numeUtilizator.isEmpty() || parola.isEmpty()) {
                Toast.makeText(MainActivity.this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show();
            } else {

                new Thread(() -> {
                    Utilizator utilizatorAutentificat = utilizatorDAO.getUtilizatorByCredentials(numeUtilizator, parola);

                    runOnUiThread(() -> {
                        if (utilizatorAutentificat != null) {
                            Toast.makeText(MainActivity.this, "Bună, " + numeUtilizator + "!", Toast.LENGTH_LONG).show();


                            SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong("userId", utilizatorAutentificat.getId());
                            editor.apply();

                            long token=sharedPreferences.getLong("userId", -1);

                            Intent intent = new Intent(MainActivity.this, ActivitateTranzactii.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Utilizator sau parolă incorecte!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
        });

        btnContNou.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreareContNou.class);
            startActivity(intent);
        });
    }
}





