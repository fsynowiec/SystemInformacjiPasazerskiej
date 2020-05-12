package systeminformacjipasazerskiej.model;

import java.util.ArrayList;

public class Kurs {
    private int idKursu;
    private int idPociagu;
    private ArrayList<Postoj> listaPostojow = new ArrayList<>();
    private int czasPrzejazdu;

    public int getIdKursu() {
        return idKursu;
    }

    public void setIdKursu(int idKursu) {
        this.idKursu = idKursu;
    }

    public int getIdPociagu() {
        return idPociagu;
    }

    public void setIdPociagu(int idPociagu) {
        this.idPociagu = idPociagu;
    }

    public ArrayList<Postoj> getListaPostojow() {
        return listaPostojow;
    }

    public void setListaPostojow(ArrayList<Postoj> listaPostojow) {
        this.listaPostojow = listaPostojow;
    }

    public int getCzasPrzejazdu() {
        return czasPrzejazdu;
    }

    public void setCzasPrzejazdu(int czasPrzejazdu) {
        this.czasPrzejazdu = czasPrzejazdu;
    }

    // TODO: print actual results
    @Override
    public String toString() {
        return "Kurs{" +
                "idKursu=" + idKursu +
                ", idPociagu=" + idPociagu +
                '}';
    }
}
