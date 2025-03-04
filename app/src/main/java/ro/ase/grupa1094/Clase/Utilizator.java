package ro.ase.grupa1094.Clase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Utilizatori")
public class Utilizator implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nume;
    private String parola;


    public Utilizator(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
