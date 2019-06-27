package com.example.bodybuilding;


public class User {
    private static final String TAG = "MyActivity";
    private String email;
    private String psw;
    private int peso;
    private int altezza;

    User(String email, String psw, int peso, int altezza){
        this.email = email;
        this.psw = psw;
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
        return this.altezza;
    }

}
