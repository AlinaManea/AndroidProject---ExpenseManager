package ro.ase.grupa1094.Clase;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import ro.ase.grupa1094.Categorie;

@Entity(tableName = "Cheltuieli", foreignKeys = @ForeignKey(entity = Utilizator.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))
public class Cheltuiala implements Serializable {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Categorie categorie;
    private String descriere;
    private double valoare;
    private long userId;

    public Cheltuiala(Categorie categorie, String descriere, double valoare) {
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

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return "Cheltuiala -> " +
                "categorie: " + categorie +
                ", descriere: " + descriere + '\'' +
                ", valoare: " + valoare;
    }
}
