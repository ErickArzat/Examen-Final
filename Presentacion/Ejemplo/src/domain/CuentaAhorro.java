package domain;

import interfaze.CuentaBancaria;

public class CuentaAhorro implements CuentaBancaria{
    private int saldo;

    public CuentaAhorro(int saldo){
        this.saldo = saldo;
    }

    @Override
    public void retirar(int saldo) {
        System.out.println("retiro exitoso 2");
        this.saldo -= saldo;
        
    }


    @Override
    public void agregarSaldo(int saldo) {
        this.saldo += saldo;
    }
        
    public int getSaldo(){
        return this.saldo;
    }

    //getters and setters+
}
