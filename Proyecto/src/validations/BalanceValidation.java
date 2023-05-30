package validations;

import java.util.ArrayList;
import interfaze.CuentaBancaria;


//BalanceValidation Es una clase que nos ayuda con la validacion del balance a la hora de realizar algun retiro o se desea eliminar un cliente
public class BalanceValidation {

    //Este metodo recibe como parametro el saldo, y nos ayuda para validar directamente el saldo
    public static boolean validarSaldo(String saldo) {
        return Double.parseDouble(saldo) > 0; 
    }

    //Este metodo recibe como parametro una cuenta, nos ayuda a la hora de querer eliminar una cuenta
    public static boolean validarSaldo(CuentaBancaria cuentaCliente) {
        return Double.parseDouble(cuentaCliente.getBalance()) > 0; 
    }

    //Este metodo recibe como parametro un ArrayList de cuentas, nos ayuda a la hora de querer eliminar a un usuarior, validamos todas sus cuentas
    public static boolean validarSaldo (ArrayList<CuentaBancaria> cuentasCliente) {
        for (CuentaBancaria cuentaCliente : cuentasCliente) {
            if (Double.parseDouble(cuentaCliente.getBalance()) > 0) {
                return true; 
            }
        }
        return false; 
    }
}
