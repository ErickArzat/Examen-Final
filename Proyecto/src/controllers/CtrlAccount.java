package controllers;

import java.util.ArrayList;

import daos.DaoAccount;
import daos.DaoClient;
import decorator.InfRetiroDEcorador;
import decorator.Retiro10000Decorador;
import domain.Account;
import domain.Client;
import domain.CuentaAhorro;
import errors.ClienteException;
import errors.CuentaException;
import interfaze.CuentaBancaria;
import utils.GeneradorDeNumeros;
import validations.BalanceValidation;

//CtrlClient es una clase que nos ayuda con todo el funcionamiento de la parte de las cuentas en nuestro sistema
public class CtrlAccount {
    DaoClient daoClient = new DaoClient();
    DaoAccount daoAccount = new DaoAccount();

    //Este metodo nos ayuda a agregar una cuenta a un cliente nuevo en nuestra base de datos, validando si ya existe el curp en 
    //nuestro sistema
    public void agregarAccountClient(String curp) throws ClienteException{
        if(daoClient.existCurp(curp)){
            Client cl = daoClient.obtenerClient(curp);

            Account ac = new Account(cl.getId(), GeneradorDeNumeros.generarNumero16Diitos(), "0");
            daoAccount.agregarAccount(ac);
        }else
            throw new  ClienteException("Error, cliente no encontrado");
    }


    //Este metodo nos ayuda a agregar una cuenta a un cliente, validando si ya existe el curp en 
    //nuestro sistema
    public void agregarAccountSavingClient(String curp, String retiro10000Dec, String infRetiroDec) throws ClienteException{
        if(daoClient.existCurp(curp)){
            Client cl = daoClient.obtenerClient(curp);

            CuentaBancaria ac = new CuentaAhorro(cl.getId(), GeneradorDeNumeros.generarNumero16Diitos(), "0");
            daoAccount.agregarSavingAccount(ac, retiro10000Dec,infRetiroDec);
        }else
            throw new  ClienteException("Error, cliente no encontrado");
    }

    //Este metodo nos ayuda a obtener una cuenta de acuerdo a su numero de cuenta
    public Account getCuenta(String idCuenta) throws CuentaException{
        Account account = daoAccount.getAccount(idCuenta);

        if (account == null)
            throw new CuentaException("Error, cuenta no encontrada");

        System.out.println("Cuenta encontrada");
        return account;
    }

    //Este metodo nos ayuda a obtener todas las cuentas de un cliente de acuerdo con su id
    public ArrayList<CuentaBancaria> getCuentas(String idCliente) throws CuentaException{
        ArrayList<CuentaBancaria> accounts = daoAccount.getAccounts(idCliente);

        if (accounts == null)
            throw new CuentaException("Error, cuenta no encontrada");
            
        System.out.println("Cuenta encontrada");
        return accounts;
    }

    //Este metodo nos ayuda a modificar el balance de una cuenta de acuerdo al deposito que se desea realizar
    public void modificarDepositoBalanceCuenta(String idCuenta, String saldo) throws CuentaException{
        if(!BalanceValidation.validarSaldo(saldo))
            throw new CuentaException("Error, el saldo a agregar debe ser mayor a cero");

        Account account = daoAccount.getAccount(idCuenta);
        account.setBalance(String.valueOf(Double.parseDouble(account.getBalance()) + Double.parseDouble(saldo)));
        daoAccount.modificarBalanceCuenta(account);
    }

    //Este metodo nos ayuda a modificar el balance de una cuenta de acuerdo al retiro que se desea realizar
    public void modificarRetiroBalanceCuenta(String idCuenta, String saldo) throws CuentaException{
        if(!BalanceValidation.validarSaldo(saldo))
            throw new CuentaException("Error, el saldo a agregar debe ser mayor a cero");

        Account account = daoAccount.getAccount(idCuenta);
        account.setBalance(String.valueOf(Double.parseDouble(account.getBalance()) - Double.parseDouble(saldo)));
        daoAccount.modificarBalanceCuenta(account);
    }

    //Este metodo nos ayuda a modificar el balance de una cuenta ahorro de acuerdo al deposito que se desea realizar
    public void modificarDepositoBalanceCuentaAhorro(String idCuenta, String saldo) throws CuentaException{
        if(!BalanceValidation.validarSaldo(saldo))
            throw new CuentaException("Error, el saldo a agregar debe ser mayor a cero");

        CuentaAhorro account = daoAccount.getSavingAccount(idCuenta);
        CuentaBancaria cuenta = getDecoratsSavingAccount(account);

        cuenta.agregarSaldo(Integer.parseInt(saldo));
        daoAccount.modificarBalanceCuentaAhorro(cuenta);
    }

    //Este metodo nos ayuda a modificar el balance de una cuenta ahorro de acuerdo al retiro que se desea realizar
    public void modificarRetiroBalanceCuentaAhorro(String idCuenta, String saldo) throws CuentaException{
        if(!BalanceValidation.validarSaldo(saldo))
            throw new CuentaException("Error, el saldo a agregar debe ser mayor a cero");

        CuentaAhorro account = daoAccount.getSavingAccount(idCuenta);
        CuentaBancaria cuenta = getDecoratsSavingAccount(account);

        cuenta.retirar(Integer.parseInt(saldo));
        daoAccount.modificarBalanceCuentaAhorro(cuenta);
    }

    //Este metodo elimina una cuenta de nuestra base de datos
    public void eliminarCuenta(String idCuenta) throws CuentaException{
        Account account = daoAccount.getAccount(idCuenta);

        if(BalanceValidation.validarSaldo(account))
            throw new CuentaException("Error, la cuenta no puede tener dinero ingresado si quiere eliminar la cuenta");

        daoAccount.eliminarCuenta(account.getNumeroCuenta());
    }

    //Este metodo elimina una cuenta ahorro de nuestra base de datos
    public void eliminarCuentaAhorro(String idCuenta) throws CuentaException{
        CuentaAhorro account = daoAccount.getSavingAccount(idCuenta);

        if(BalanceValidation.validarSaldo(account))
            throw new CuentaException("Error, la cuenta no puede tener dinero ingresado si quiere eliminar la cuenta");

        daoAccount.eliminarCuentaAhorro(account.getNumeroCuenta());
    }
    
    //Este motodo nos ayuda a asignarle las funcionalidades extra a nuestra cuenta ahorro, mediante las clases decoradoras
    public CuentaBancaria getDecoratsSavingAccount(CuentaAhorro account){
        CuentaBancaria cuenta = null;

        ArrayList<String> list = daoAccount.getDecoratsSavingAccount(account.getNumeroCuenta());

        String infRetiroDec = list.get(0);
        String retiro10000Dec = list.get(1);

        if(infRetiroDec == "1" && retiro10000Dec == "1"){
            cuenta = new Retiro10000Decorador(new InfRetiroDEcorador(account));
        }else if(infRetiroDec == "1" && retiro10000Dec == "0"){
            cuenta = new InfRetiroDEcorador(account);
        }else if(infRetiroDec == "0" && retiro10000Dec == "1")
            cuenta = new Retiro10000Decorador(account);

        return cuenta;
    }
}
