package domain;

import interfaze.CuentaBancaria;

//Account es nuestra clase modelo de una cuenta que tiene el id del cliente a quien corresponde, un numero de cuenta y su balance,
//e implementa la interfaz CuentaBancaria
public class Account implements CuentaBancaria{
    private String idCliente;
    private String numeroCuenta;
    private String balance;

    public Account(String idCliente, String numeroCuenta, String balance){
        this.idCliente = idCliente;
        this.numeroCuenta = numeroCuenta;
        this.balance = balance;
    }
    public Account(){
    }

    public String getIdCliente(){
        return this.idCliente;
    }

    public void setIdCliente(String id){
        this.idCliente = id;
    }

    public String getNumeroCuenta(){
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta){
        this.numeroCuenta = numeroCuenta;
    }

    public String getBalance(){
        return this.balance;
    }

    public void setBalance(String balance){
        this.balance = balance;
    }
    @Override
    public void retirar(int saldo) {
        String.valueOf(Double.parseDouble(this.balance ) - saldo);
    }
    @Override
    public void agregarSaldo(int saldo) {
        String.valueOf(Double.parseDouble(this.balance ) + saldo);
    }

}
