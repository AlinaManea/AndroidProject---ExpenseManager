package ro.ase.grupa1094.Clase;

public class CheltuialaViitoare {
    private String id;
    String denumireProdus;
    String magazin;
    Float pret;
    Necesitate esteNecesar;

    public CheltuialaViitoare() {
    }


    @Override
    public String toString() {
        return "CheltuialaViitoare{" +
                "denumireProdus='" + denumireProdus + '\'' +
                ", magazin='" + magazin + '\'' +
                ", pret=" + pret +
                ", esteNecesar=" + esteNecesar +
                '}';
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public String getMagazin() {
        return magazin;
    }

    public void setMagazin(String magazin) {
        this.magazin = magazin;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public Necesitate getEsteNecesar() {
        return esteNecesar;
    }

    public void setEsteNecesar(Necesitate esteNecesar) {
        this.esteNecesar = esteNecesar;
    }


    public CheltuialaViitoare(String denumireProdus, String magazin, Float pret, Necesitate esteNecesar) {
        this.denumireProdus = denumireProdus;
        this.magazin = magazin;
        this.pret = pret;
        this.esteNecesar = esteNecesar;
    }

}
