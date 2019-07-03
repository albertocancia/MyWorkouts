package com.example.bodybuilding;


public class User {
    private String nome;
    private int peso;
    private int altezza;

    User(String nome, int peso, int altezza){
        this.nome = nome;
        this.peso = peso;
        this.altezza = altezza;
    }


    public void setAltezza(int altezza){
        this.altezza = altezza;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }
    public int getAltezza(){
        return this.altezza;
    }

    public int getPeso(){
        return this.peso;
    }

    public String getNome(){
        return this.nome;
    }

}
