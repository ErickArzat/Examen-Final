package patron;

import interfaze.CuentaBancaria;

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
