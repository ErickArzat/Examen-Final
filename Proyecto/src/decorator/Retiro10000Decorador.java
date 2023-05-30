package decorator;

import errors.RetirorErrorException;
import interfaze.CuentaBancaria;

//Retiro10000Decorador es nuestra clase que forma parte del patron decorator, y nos ayuda a que no se pueda retirar mas de 10,000 pesos
public class Retiro10000Decorador extends CuentaDecorador {

    public Retiro10000Decorador(CuentaBancaria cuentaDecorada) {
        super(cuentaDecorada);
    }

    //Este metodo nos ayuda a agregarle esa funcionalidad al metodo de retirar
    @Override
    public void retirar(int saldo) {
    if(saldo < 10000){
            cuentaDecorada.retirar(saldo);
        } else
        try {
            throw new RetirorErrorException("No puedes hacer retiros mayores a 10,000 pesos");
        } catch (RetirorErrorException e) {
        }
    }

    //Este metodo delega la responsabilidad 
    @Override
    public void agregarSaldo(int saldo) {
        this.cuentaDecorada.agregarSaldo(saldo);
    }

    //Este metodo delega la responsabilidad
    @Override
    public String getNumeroCuenta() {
        return cuentaDecorada.getNumeroCuenta();
    }
    
    //Este metodo delega la responsabilidad
    @Override
    public String getBalance() {
        return cuentaDecorada.getBalance();
    }

    //Este metodo delega la responsabilidad
    @Override
    public void setBalance(String balance) {
        this.cuentaDecorada.setBalance(balance);
    }

    //Este metodo delega la responsabilidad
    @Override
    public String getIdCliente() {
        return cuentaDecorada.getIdCliente();
    }
}
