package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import domain.Account;
import domain.CuentaAhorro;
import interfaze.CuentaBancaria;
import sql.ConectionSingleton;

//DaoAccount es nuestra clase del patron DAO que nos sirve como intermediario entre los controladores y la conexion a la base de datos, pues este ejecuta todas
//las consultas necesarias
public class DaoAccount {

    //Este metodo nos ayuda a agregar una cuenta en su respectiva tabla
    public void agregarAccount(Account account){
        String consulta = "INSERT INTO cuentas (no_cuenta, balance, usuario_id, activo) VALUES (?,?,?,?)";
        try {
            Connection conexion = ConectionSingleton.getConnection();
            PreparedStatement ps = conexion.prepareStatement(consulta);

            ps.setString(1, account.getNumeroCuenta());
            ps.setString(2, account.getBalance());
            ps.setString(3, account.getIdCliente());
            ps.setString(4, String.valueOf(1));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Guardado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Registro No Guardado" + e.getMessage());
        }
    }

    //Este metodo nos ayuda a agregar una cuenta ahorro en su respectiva tabla
    public void agregarSavingAccount(CuentaBancaria account, String retiro10000Dec, String infRetiroDec){
        String consulta = "INSERT INTO cuentasAhorros (no_cuenta, balance, usuario_id, infRetiroDec, retiro10000Dec, activo) VALUES (?,?,?,?,?,?)";
        try {
            Connection conexion = ConectionSingleton.getConnection();
            PreparedStatement ps = conexion.prepareStatement(consulta);

            ps.setString(1, account.getNumeroCuenta());
            ps.setString(2, account.getBalance());
            ps.setString(3, account.getIdCliente());
            ps.setString(4, retiro10000Dec);
            ps.setString(5, infRetiroDec);
            ps.setString(6, String.valueOf(1));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Guardado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Registro No Guardado" + e.getMessage());
        }
    }

    //Este metodo nos ayuda a obtener una cuenta ahorro de acuerdo a su numero de cuenta
    public CuentaAhorro getSavingAccount(String idCuenta){
        CuentaAhorro ac = null;
        ResultSet rs;
        String consulta = "SELECT no_cuenta, balance, usuario_id FROM cuentasAhorros WHERE no_cuenta = ? AND activo = 1";
        
        try {

            Connection conexion = ConectionSingleton.getConnection();    
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, idCuenta);
            rs = ps.executeQuery();

            while(rs.next())
                ac = new CuentaAhorro(rs.getString("usuario_id"), rs.getString("no_cuenta"), rs.getString("balance"));
                
            return ac;
        } catch (Exception e) {
        }

        return ac;
    }

    //Este metodo nos ayuda a obtener todas las cuentas ahorro de acuerdo al id del cliente
    public ArrayList<CuentaBancaria> getSavingAccounts(String idCliente){
        ArrayList<CuentaBancaria> listAccounts = new ArrayList<CuentaBancaria>();
        
        ResultSet rs;
        String consulta = "SELECT no_cuenta, balance, usuario_id FROM cuentasAhorros WHERE usuario_id = ? AND activo = 1";
        
        try {

            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, idCliente);
            rs = ps.executeQuery();

            while(rs.next()){
                Account ac = new Account(rs.getString("usuario_id"), rs.getString("no_cuenta"), rs.getString("balance"));
                listAccounts.add(ac);
            }
            return listAccounts;
        } catch (Exception e) {
        }

        return listAccounts;
    }

    //Este metodo nos ayuda a obtener todas las cuentas ahorro de acuerdo al id del cliente
    public ArrayList<String> getDecoratsSavingAccount(String idCuenta){
        ArrayList<String> list = null;
        ResultSet rs;
        String consulta = "SELECT infRetiroDec, retiro10000Dec FROM cuentasAhorros WHERE no_cuenta = ? AND activo = 1";
        
        try {

            Connection conexion = ConectionSingleton.getConnection();  
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, idCuenta);
            rs = ps.executeQuery();

            while(rs.next())
                list.add(rs.getString("infRetiroDec"));
                list.add(rs.getString("retiro10000Dec"));
                
            return list;
        } catch (Exception e) {
        }

        return list;
    }

    //Este metodo nos ayuda a obtener una cuenta de acuerdo a su numero de cuenta
    public Account getAccount(String idCuenta){
        Account ac = null;
        ResultSet rs;
        String consulta = "SELECT no_cuenta, balance, usuario_id FROM cuentas WHERE no_cuenta = ? AND activo = 1";
        
        try {

            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, idCuenta);
            rs = ps.executeQuery();

            while(rs.next())
                ac = new Account(rs.getString("usuario_id"), rs.getString("no_cuenta"), rs.getString("balance"));
                
            return ac;
        } catch (Exception e) {
        }

        return ac;
    }

    //Este metodo nos ayuda a obtener todas las cuentas de acuerdo al id del cliente
    public ArrayList<CuentaBancaria> getAccounts(String idCliente){
        ArrayList<CuentaBancaria> listAccounts = getSavingAccounts(idCliente);

        ResultSet rs;
        String consulta = "SELECT no_cuenta, balance, usuario_id FROM cuentas WHERE usuario_id = ? AND activo = 1";
        
        try {

            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, idCliente);
            rs = ps.executeQuery();

            while(rs.next()){
                Account ac = new Account(rs.getString("usuario_id"), rs.getString("no_cuenta"), rs.getString("balance"));
                listAccounts.add(ac);
            }
            return listAccounts;
        } catch (Exception e) {
        }

        return listAccounts;
    }

    //Este metodo nos ayuda a modificar el balance de una cuenta, recibiendo como parametro la cuenta
    public void modificarBalanceCuenta(Account account){

        String consulta = "UPDATE cuentas SET balance = ? WHERE no_cuenta = ?";

        try {
            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            
            ps.setString(1, account.getBalance());
            ps.setString(2, account.getNumeroCuenta());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Modificado");
        } catch (Exception e) {

        }
        
    }

    //Este metodo nos ayuda a modificar el balance de una cuenta ahorro, recibiendo como parametro la cuenta
    public void modificarBalanceCuentaAhorro(CuentaBancaria account){

        String consulta = "UPDATE cuentasAhorros SET balance = ? WHERE no_cuenta = ?";

        try {
            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            
            ps.setString(1, account.getBalance());
            ps.setString(2, account.getNumeroCuenta());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Modificado");
        } catch (Exception e) {

        }
        
    }

    //Este metodo nos ayuda a elimanar una cuenta de nuestra base de datos, recibiendo como parametro el numero de cuenta
    public void eliminarCuenta(String idCuenta){
        String consulta = "UPDATE cuentas SET activo = 0 WHERE no_cuenta = ?";

        try {
            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            
            ps.setString(1, idCuenta);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (Exception e) {

        }
    }

    //Este metodo nos ayuda a elimanar una cuenta ahorro de nuestra base de datos, recibiendo como parametro el numero de cuenta
    public void eliminarCuentaAhorro(String idCuenta){
        String consulta = "UPDATE cuentasAhorros SET activo = 0 WHERE no_cuenta = ?";

        try {
            Connection conexion = ConectionSingleton.getConnection();   
            PreparedStatement ps = conexion.prepareStatement(consulta);
            
            ps.setString(1, idCuenta);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (Exception e) {

        }
    }
    
}
