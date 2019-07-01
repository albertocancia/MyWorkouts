package com.example.bodybuilding;

import java.util.List;

public class Scheda {
    private String Uid;
    private String Giorno;
    List<String> Esercizi;

    public Scheda(String Uid, String Giorno, List<String> Esercizi){
        this.Uid = Uid;
        this.Giorno = Giorno;
        this.Esercizi = Esercizi;
    }

    public String getUid() {
        return Uid;
    }

    public String getGiorno() {
        return Giorno;
    }

    public List<String> getEsercizi() {
        return Esercizi;
    }
}
