package ro.ase.grupa1094.Clase;

import android.text.method.TransformationMethod;

import java.io.Serializable;

import ro.ase.grupa1094.Tip;

public class Tranzactie  implements Serializable {
    private String id;
    private Tip tip;
    private String descriere;
    private int valoare;

    public Tranzactie()
    {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Tranzactie(Tip tip, String descriere, int valoare) {
        this.tip = tip;
        this.descriere = descriere;
        this.valoare = valoare;
    }
    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "tip=" + tip +
                ", descriere='" + descriere + '\'' +
                ", valoare=" + valoare +
                '}';
    }
}
