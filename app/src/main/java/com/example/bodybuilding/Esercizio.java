package com.example.bodybuilding;

public class Esercizio {
    private String nome;
    private String categoria;
    private boolean selected;

    public Esercizio(String nome){
        this.nome = nome;
        //this.categoria = categoria;
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
}
