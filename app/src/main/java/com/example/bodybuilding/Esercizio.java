package com.example.bodybuilding;

public class Esercizio {
    private String nome;
    private String categoria;
    private boolean selected;
    private int serie;
    private int rep;

    public Esercizio(String nome, int serie, int rep){
        this.nome = nome;
        this.rep = rep;
        this.serie = serie;
    }
    public Esercizio(String nome) {
        this.nome = nome;
    }

    public String getName(){
        return nome;
    }

    public String getCategoria(){
        return categoria;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSerie(){
        return serie;
    }
    public int getRep(){
        return rep;
    }
    public void setRep(int a){
        rep = a;
    }
    public void setSerie(int a){
        serie = a;
    }

    public String toString(){
        String s;
        s = nome+"_"+serie+"_"+rep;
        return s;
    }
}
