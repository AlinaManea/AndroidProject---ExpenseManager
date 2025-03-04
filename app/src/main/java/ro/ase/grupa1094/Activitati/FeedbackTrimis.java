package ro.ase.grupa1094.Activitati;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Adaptere.FeedbackAdapter;
import ro.ase.grupa1094.Clase.Feedback;
import ro.ase.grupa1094.R;

public class FeedbackTrimis extends AppCompatActivity {

    Button btnInapoi;
    private ListView listViewFeedback;
    private FeedbackAdapter feedbackAdapter;
    private ArrayList<Feedback> feedbackList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activitate_feedback);

        getSupportActionBar().setTitle("FinApp");

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#516aa6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        feedbackList = (ArrayList<Feedback>) getIntent().getSerializableExtra("feedbackList");


        listViewFeedback = findViewById(R.id.listViewFeedback);
        feedbackAdapter = new FeedbackAdapter(this, R.layout.view_feedback, feedbackList);
        listViewFeedback.setAdapter(feedbackAdapter);


        btnInapoi = findViewById(R.id.btnInapoiSetari);

        btnInapoi.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("feedbackList",  feedbackList);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
