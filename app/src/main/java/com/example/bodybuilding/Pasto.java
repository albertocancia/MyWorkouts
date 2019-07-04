package com.example.bodybuilding;

public class Pasto {
    private String nome;
    private boolean active;
    Pasto(String nome, boolean active){
        this.nome = nome;
        this.active = active;
    }

    public String getName(){
        return nome;
    }
    public boolean getActive(){
        return active;
    }
}
