package ro.ase.grupa1094.Clase;

import java.io.Serializable;

public class Inregistrare implements Serializable {
    private String id;
    private String nume;
    private String prenume;
    private String telefon;
    private String data;
    private String numeUtiliz;
    private String mail;
    private String parola;

    public Inregistrare(String nume, String prenume, String telefon, String data, String numeUtiliz, String mail, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.data = data;
        this.numeUtiliz=numeUtiliz;
        this.mail = mail;
        this.parola = parola;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
    public String getNumeUtiliz() {return numeUtiliz;}

    public void setNumeUtiliz(String numeUtiliz) {this.numeUtiliz = numeUtiliz;}
    @Override
    public String toString() {
        return "Inregistrare{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", telefon='" + telefon + '\'' +
                ", data='" + data + '\'' +
                ", numeUtiliz='" + numeUtiliz + '\'' +
                ", mail='" + mail + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }

}
