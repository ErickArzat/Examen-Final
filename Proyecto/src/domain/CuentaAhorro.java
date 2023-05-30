package domain;

import interfaze.CuentaBancaria;

//CuentaAhorro es nuestra clase modelo de una cuenta ahorro que tiene el id del cliente a quien corresponde, un numero de cuenta y su balance,
//e implementa la interfaz CuentaBancaria
public class CuentaAhorro implements CuentaBancaria{
    private String IdCuenta;
    private String balance;
    private String idCliente;

    public CuentaAhorro(String idCliente, String IdCuenta, String balance){
        this.IdCuenta = IdCuenta;
        this.balance = balance;
        this.idCliente = idCliente;
    }

    @Override
    public String getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(String id){
        this.idCliente = id;
    }

    @Override
    public void retirar(int saldo) {
        String.valueOf(Double.parseDouble(this.balance) -  saldo);
        
    }

    @Override
    public void agregarSaldo(int saldo) {
        String.valueOf(Double.parseDouble(this.balance ) + saldo);
    }

    @Override
    public String getNumeroCuenta() {
        return this.IdCuenta;
    }

    @Override
    public String getBalance() {
        return this.balance;
    }

    @Override
    public void setBalance(String balance) {
        this.balance = balance;
    }

}
