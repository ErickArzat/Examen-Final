package controllers;

import java.util.ArrayList;

import daos.DaoAccount;
import daos.DaoClient;
import domain.Account;
import domain.Client;
import errors.ClienteException;
import errors.CuentaException;
import interfaze.CuentaBancaria;
import utils.GeneradorDeNumeros;
import utils.ReporteCliente;
import validations.BalanceValidation;
import validations.ClienteValidaciones;

//CtrlClient es una clase que nos ayuda con todo el funcionamiento de la parte de los clientas en nuestro sistema
public class CtrlClient {
    private DaoClient daoCliente = new DaoClient();
    private DaoAccount daoCuenta = new DaoAccount();
    ReporteCliente reporteCliente = new ReporteCliente(); 
    
    //Este metodo nos ayuda a agregar un nuevo cliente en nuestra base de datos, validando si ya existe el curp en 
    //nuestro sistema o si los datos del cliente esten bien registrados
    public void agregarClient(String nombre,String curp) throws ClienteException{
        ClienteValidaciones.validarNombre(nombre); 
        ClienteValidaciones.validarCurp(curp);

        if (daoCliente.existCurp(curp))
            throw new ClienteException("Error, ya existe el curp en un cliente");
            
        String id = GeneradorDeNumeros.generarNumero16Diitos();
        Client cl = new Client(id, nombre, curp);

        daoCliente.agregarClient(cl);

        Account ac = new Account(id, GeneradorDeNumeros.generarNumero16Diitos(), "0");
        daoCuenta.agregarAccount(ac);

    }

    //Este metodo nos ayuda a obtener un cliente de nuestra bese de datos 
    public Client obtenerCliente(String curp) throws ClienteException{
        ClienteValidaciones.validarCurp(curp);
        Client cliente = daoCliente.obtenerClient(curp);

        if(cliente == null)
            throw new  ClienteException("Error, cliente no encontrado");

        cliente.setListaCuentas(daoCuenta.getAccounts(cliente.getId()));

        return cliente;
    }

    //Este metodo nos ayuda a obtener a todos los clientes de nuestra bese de datos 
    public ArrayList<Client> obtenerClientes() throws ClienteException{

        ArrayList<Client> clientes = daoCliente.obtenerClients();

        if(clientes == null)
            throw new  ClienteException("Error, cliente no encontrado");

        for(Client cliente: clientes)
            cliente.setListaCuentas(daoCuenta.getAccounts(cliente.getId()));
        
        return clientes;
    }

    //Este metodo nos ayuda a modificar el nombre de un cliente de nuestra bese de datos 
    public void modificarCliente(String curp, String nombre) throws ClienteException{
        ClienteValidaciones.validarNombre(nombre); 
        ClienteValidaciones.validarCurp(curp);

        if (!daoCliente.existCurp(curp))
            throw new ClienteException("Error, no existe el curp en la base de datos");
        
        daoCliente.modificarCliente(curp, nombre);
    }

    //Este metodo nos ayuda a eliminar a un cliente de nuestra bese de datos 
    public void eliminarCliente(String curp) throws ClienteException, CuentaException{
        ClienteValidaciones.validarCurp(curp);
        if (!daoCliente.existCurp(curp))
            throw new ClienteException("Error, no existe el curp en la base de datos");

        Client cl = daoCliente.obtenerClient(curp);
        ArrayList<CuentaBancaria> accounts = daoCuenta.getAccounts(cl.getId());

        if(BalanceValidation.validarSaldo(accounts))
            throw new CuentaException("No se pudo borrar al cliente, al menos unas de sus cuentas tiene dinero ingresado");
        
        for (CuentaBancaria cuentaCliente : accounts){
            daoCuenta.eliminarCuenta(cuentaCliente.getNumeroCuenta());
        }

        daoCliente.eliminarCliente(curp);
    }

    //Este metodo nos ayuda a generar el reporte de todos los clientes
    public void generarReporte() throws ClienteException{
        reporteCliente.generarReporte(obtenerClientes());
    }
}
