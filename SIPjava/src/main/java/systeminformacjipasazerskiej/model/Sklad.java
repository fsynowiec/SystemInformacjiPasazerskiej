package systeminformacjipasazerskiej.model;

import java.util.ArrayList;

public class Sklad {
    private int idSkladu;
    private boolean czyPrzesylki;
    private ArrayList<Wagon> listaWagonow;
    private ArrayList<Integer> idWagonow;
    private ArrayList<Integer> liczbaWagonow;

    public int getIdSkladu() {
        return idSkladu;
    }

    public void setIdSkladu(int idSkladu) {
        this.idSkladu = idSkladu;
    }

    public boolean isCzyPrzesylki() {
        return czyPrzesylki;
    }

    public void setCzyPrzesylki(boolean czyPrzesylki) {
        this.czyPrzesylki = czyPrzesylki;
    }

    public ArrayList<Wagon> getListaWagonow() {
        return listaWagonow;
    }

    public void setListaWagonow(ArrayList<Wagon> listaWagonow) {
        this.listaWagonow = listaWagonow;
    }

    public ArrayList<Integer> getIdWagonow() {
        return idWagonow;
    }

    public void setIdWagonow(ArrayList<Integer> idWagonow) {
        this.idWagonow = idWagonow;
    }

    public ArrayList<Integer> getLiczbaWagonow() {
        return liczbaWagonow;
    }

    public void setLiczbaWagonow(ArrayList<Integer> liczbaWagonow) {
        this.liczbaWagonow = liczbaWagonow;
    }

}