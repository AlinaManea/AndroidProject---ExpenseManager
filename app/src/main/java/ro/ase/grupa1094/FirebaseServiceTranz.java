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

import ro.ase.grupa1094.Clase.Tranzactie;

public class FirebaseServiceTranz {
    private final DatabaseReference reference;
    private static FirebaseServiceTranz firebaseService;

    private FirebaseServiceTranz() {
        reference = FirebaseDatabase.getInstance().getReference("tranzactii");
    }

    public static FirebaseServiceTranz getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseServiceTranz.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseServiceTranz();
                }
            }
        }
        return firebaseService;
    }

    public void insert(Tranzactie tranzactie) {
        if (tranzactie == null || tranzactie.getId() != null) {
            return;
        }
        String id = reference.push().getKey();
        tranzactie.setId(id);
        reference.child(tranzactie.getId()).setValue(tranzactie);
    }


    public void update(Tranzactie tranzactie) {
        if (tranzactie == null || tranzactie.getId() == null) {
            return;
        }
        reference.child(tranzactie.getId()).setValue(tranzactie);
    }


    public void delete(Tranzactie tranzactie) {
        if (tranzactie == null || tranzactie.getId() == null) {
            return;
        }
        reference.child(tranzactie.getId()).removeValue();
    }


    public void addTranzactiiListener(Callback<List<Tranzactie>> callback) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Tranzactie> tranzactii = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Tranzactie tranzactie = data.getValue(Tranzactie.class);
                    if (tranzactie != null) {
                        tranzactii.add(tranzactie);
                    }
                }
                callback.runOnUI(tranzactii);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Eroare la încărcarea tranzacțiilor!");
            }
        });
    }
}
