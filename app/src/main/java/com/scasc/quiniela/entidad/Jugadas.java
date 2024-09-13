package com.scasc.quiniela.entidad;

import androidx.annotation.NonNull;

public class Jugadas {
    private int posicion; /// del 1 - 14
    private int monto; /// desde 500 hasta 100.000
    private int tipo; /// 3,2,1
    private int id;
    private int numero;

    public Jugadas(int _id,int _posicion,int _monto,int _tipo,int _numero){
        this.id=_id;
        this.posicion=_posicion;
        this.monto=_monto;
        this.tipo=_tipo;
        this.numero=_numero;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNumero(){return this.numero;}

    @NonNull
    @Override
    public String toString(){
        //{"posicion":"1","numero":"321","monto":"5441","tipo":"3"}
        return "{\"posicion\":\""+posicion+"\",\"numero\":\""+numero+"\",\"monto\":\""+monto+"\",\"tipo\":\""+tipo+"\"}";
    }

}
