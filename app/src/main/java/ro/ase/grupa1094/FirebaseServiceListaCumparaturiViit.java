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

import ro.ase.grupa1094.Clase.CheltuialaViitoare;

public class FirebaseServiceListaCumparaturiViit {
    private final DatabaseReference reference;
    public static FirebaseServiceListaCumparaturiViit firebaseService;

    private FirebaseServiceListaCumparaturiViit() {
        reference = FirebaseDatabase.getInstance().getReference("cheltuieliViitoare");
    }

    public static FirebaseServiceListaCumparaturiViit getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseServiceListaCumparaturiViit.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseServiceListaCumparaturiViit();
                }
            }
        }
        return firebaseService;
    }

    public void insert(CheltuialaViitoare cheltuiala) {
        if (cheltuiala == null || cheltuiala.getId() != null) {
            return;
        }
        String id = reference.push().getKey();
        cheltuiala.setId(id);
        reference.child(cheltuiala.getId()).setValue(cheltuiala);
    }

    public void update(CheltuialaViitoare cheltuiala) {
        if (cheltuiala == null || cheltuiala.getId() == null) {
            return;
        }
        reference.child(cheltuiala.getId()).setValue(cheltuiala);
    }

    public void delete(CheltuialaViitoare cheltuiala) {
        if (cheltuiala == null || cheltuiala.getId() == null) {
            return;
        }
        reference.child(cheltuiala.getId()).removeValue();
    }

    public void addPlanificariListener(Callback<List<CheltuialaViitoare>> callback) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CheltuialaViitoare> cheltuieli = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CheltuialaViitoare cheltuiala = data.getValue(CheltuialaViitoare.class);
                    if (cheltuiala != null) {
                        cheltuieli.add(cheltuiala);
                    }
                }
                callback.runOnUI(cheltuieli);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Cheltuiala nu poate fi accesata!");
            }
        });
    }
}
