package decorator;

import interfaze.CuentaBancaria;

//CuentaDecorador es nuestra clase abstracta del patron decorator que nos ayuda a que se extienda 
//los metodos de la interfaz CuentaBancaria a nuestras clases decoradoras
public abstract class CuentaDecorador implements CuentaBancaria{
    protected CuentaBancaria cuentaDecorada;

    public CuentaDecorador (CuentaBancaria cuentaDecorada){
        this.cuentaDecorada = cuentaDecorada;
    }

    @Override
    public void retirar(int saldo){
        this.cuentaDecorada.retirar(saldo);
    }
}
