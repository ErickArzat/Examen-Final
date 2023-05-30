package domain;

import java.util.ArrayList;

import interfaze.CuentaBancaria;

//Client es nuestra clase modelo de un cliente, que tiene un nombre, un id, un curp y una lista de cuentas
public class Client {
    private String nombre;
    private String id;
    private String curp;
    private ArrayList<CuentaBancaria> listaCuentas;

    public Client(String id, String nombre,String curp){
        this.id = id;
        this.nombre = nombre;
        this.curp = curp;
    }

    public Client(String id, String nombre,String curp, ArrayList<CuentaBancaria> listaCuentas){
        this.id = id;
        this.nombre = nombre;
        this.curp = curp;
        this.listaCuentas = listaCuentas;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }
    public ArrayList<CuentaBancaria> getListaCuentas(){
        return this.listaCuentas;
    }

    public void setListaCuentas(ArrayList<CuentaBancaria> cuentas){
        this.listaCuentas = cuentas;
    }

    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
}
