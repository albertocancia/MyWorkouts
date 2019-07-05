package com.example.bodybuilding;

public class Esercizio {
    private String nome;
    private String categoria;
    private boolean selected;
    private int serie;
    private int rep;
    private int peso;

    public Esercizio(String nome, int serie, int rep, int peso){
        this.nome = nome;
        this.rep = rep;
        this.serie = serie;
        this.peso = peso;
    }
    public Esercizio(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
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
    public int getPeso(){
        return peso;
    }

    public void setRep(int a){
        rep = a;
    }
    public void setSerie(int a){
        serie = a;
    }

    public String toString(){
        String s;
        s = nome+"_"+serie+"_"+rep+"_"+peso;
        return s;
    }
}
