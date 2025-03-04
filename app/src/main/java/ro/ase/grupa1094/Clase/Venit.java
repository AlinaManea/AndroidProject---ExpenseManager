package ro.ase.grupa1094.Clase;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ro.ase.grupa1094.Categoriee;

@Entity(tableName = "Venituri", foreignKeys = @ForeignKey(entity = Utilizator.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))
public class Venit implements Serializable {
    public Venit() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Categoriee categorie;
    private String descriere;
    private double valoare;
    private long userId;



    public Venit(Categoriee categorie, String descriere, double valoare) {
        this.categorie = categorie;
        this.descriere = descriere;
        this.valoare = valoare;

    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Categoriee getCategorie() {
        return categorie;
    }

    public void setCategorie(Categoriee categorie) {
        this.categorie = categorie;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return "Venit -> " +
                "categorie: " + categorie +
                ", descriere:'" + descriere + '\'' +
                ", valoare: " + valoare;
    }
}
