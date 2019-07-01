package com.example.bodybuilding;

import java.util.List;

public class Scheda {
    private String uid;
    private String giorno;
    private List<String> esercizi;

    public Scheda(){}

    public Scheda(String uid, String giorno, List<String> esercizi){
        this.uid = uid;
        this.giorno = giorno;
        this.esercizi = esercizi;
    }

    public String getUid() {
        return uid;
    }

    public String getGiorno() {
        return giorno;
    }

    public List<String> getEsercizi() {
        return esercizi;
    }
}
