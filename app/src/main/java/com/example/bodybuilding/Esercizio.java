package com.example.bodybuilding;

public class Esercizio {
    private String nome;
    private String categoria;

    public Esercizio(String nome, String categoria){
        this.nome = nome;
        this.categoria = categoria;
    }

    public String getNome(){
        return nome;
    }

    public String getCategoria(){
        return categoria;
    }
}
