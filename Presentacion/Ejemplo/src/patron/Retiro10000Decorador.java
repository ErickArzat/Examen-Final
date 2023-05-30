package patron;

import interfaze.CuentaBancaria;

public class Retiro10000Decorador extends CuentaDecorador {

    public Retiro10000Decorador(CuentaBancaria cuentaDecorada) {
        super(cuentaDecorada);
    }

    @Override
    public void retirar(int saldo) {
    if(saldo < 10000){
            System.out.println("retiro exitoso");
            cuentaDecorada.retirar(saldo);
        }else
            System.out.println("No puedes hacer retiros mayores a 10,000 pesos");
    }

    @Override
    public void agregarSaldo(int saldo) {
        this.cuentaDecorada.agregarSaldo(saldo);
    }

    @Override
    public int getSaldo() {
        return cuentaDecorada.getSaldo();
    }

    
    
    
}
