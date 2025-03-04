package ro.ase.grupa1094;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Clase.Inregistrare;

public class FirebaseServiceUtilizator {
    private final DatabaseReference reference;
    private static FirebaseServiceUtilizator firebaseServiceUtilizator;


    private FirebaseServiceUtilizator() {
        reference = FirebaseDatabase.getInstance().getReference("utilizatori");
    }

    public static FirebaseServiceUtilizator getInstance() {
        if (firebaseServiceUtilizator == null) {
            synchronized (FirebaseServiceUtilizator.class) {
                if (firebaseServiceUtilizator == null) {
                    firebaseServiceUtilizator = new FirebaseServiceUtilizator();
                }
            }
        }
        return firebaseServiceUtilizator;
    }

    public void insert(Inregistrare utilizator) {
        if (utilizator == null) {
            return;
        }

        String id = reference.push().getKey();
        utilizator.setId(id);
        reference.child(id).setValue(utilizator);
    }

    public void update(Inregistrare utilizator) {
        if (utilizator == null || utilizator.getId() == null) {
            return;
        }
        reference.child(utilizator.getId()).setValue(utilizator);
    }

    public void delete(Inregistrare utilizator) {
        if (utilizator == null || utilizator.getId() == null) {
            return;
        }
        reference.child(utilizator.getId()).removeValue();

    }

    public void addUtilizatoriListener(Callback<List<Inregistrare>> callback) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Inregistrare> utilizatori = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Inregistrare utilizator = data.getValue(Inregistrare.class);
                    if (utilizator != null) {
                        utilizatori.add(utilizator);
                    }
                }
                callback.runOnUI(utilizatori);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Eroare la încărcarea utilizatorilor!");
            }
        });
    }
}
