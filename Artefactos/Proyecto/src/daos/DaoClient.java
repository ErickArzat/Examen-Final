package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import domain.Client;
import sql.ConectionSingleton;

//DaoClient es nuestra clase del patron DAO que nos sirve como intermediario entre los controladores y la conexion a la base de datos, pues este ejecuta todas
//las consultas necesarias
public class DaoClient {
    
    //Este metodo nos ayuda a agregar un cliete en su respectiva tabla
    public void agregarClient(Client client){
        String consulta = "INSERT INTO clientes (id_usuario, nombre, curp, activo) VALUES (?,?,?,?)";
        try {
            Connection conexion = ConectionSingleton.getConnection();
            PreparedStatement ps = conexion.prepareStatement(consulta);

            ps.setString(1, client.getId());
            ps.setString(2, client.getNombre());
            ps.setString(3, client.getCurp());
            ps.setString(4, String.valueOf(1));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Guardado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Registro No Guardado");
        }
        
    }

    //Este metodo nos ayuda a obtener un cliete de su respectiva tabla
    public Client obtenerClient(String curp){
        ResultSet rs;
        String consulta = " SELECT id_usuario, nombre, curp FROM clientes WHERE curp = ?";

        try {
            Connection conexion = ConectionSingleton.getConnection();
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, curp);
            rs = ps.executeQuery();
            
            if(rs.next()){
                Client cl = new Client(rs.getString("id_usuario"), rs.getString("nombre"), curp);
                return cl;
            }
        } catch (Exception e) {
            
        }
        return null;
    }

    //Este metodo nos ayuda a obtener a todos los clietes registrados en nuestra base de datos
    public ArrayList<Client> obtenerClients(){
        ResultSet rs;
        String consulta = " SELECT id_usuario, nombre, curp FROM clientes";
        ArrayList<Client> listaClientes = new ArrayList<Client>();
        try {
            Connection conexion = ConectionSingleton.getConnection();
            PreparedStatement ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Client cl = new Client(rs.getString("id_usuario"), rs.getString("nombre"), rs.getString("curp"));
                listaClientes.add(cl);
            }

            return listaClientes;
        } catch (Exception e) {
            
        }
        return listaClientes;
    }

    //Este metodo nos ayuda a modificar el nombre de un cliete en su respectiva tabla
    public void modificarCliente(String curp, String nombre){
        String consulta = "UPDATE clientes SET nombre = ? WHERE curp = ? AND activo = 1";

        try {
            Connection conexion = ConectionSingleton.getConnection();    
            PreparedStatement ps = conexion.prepareStatement(consulta);
            
            ps.setString(1, nombre);
            ps.setString(2, curp);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Modificado");
        } catch (Exception e) {

        }
    }

    //Este metodo nos ayuda a eliminar un cliete en su respectiva tabla
    public void eliminarCliente(String curp){
        String consulta = "UPDATE clientes SET activo = 0 WHERE curp = ?";

        try {
            Connection conexion = ConectionSingleton.getConnection();    
            PreparedStatement ps = conexion.prepareStatement(consulta);
            
            ps.setString(1, curp);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (Exception e) {

        }
    }

    //Este metodo nos ayuda a verificar si el curp ya se encuentra registrado en nuestra base de datos
    public boolean existCurp(String curp){
        ResultSet rs;
        String consulta = " SELECT * FROM clientes WHERE curp = ? AND activo = 1";

        try {
            Connection conexion = ConectionSingleton.getConnection();
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, curp);
            rs = ps.executeQuery();
            
            if(rs.next())
                return true;
            
        } catch (Exception e) {
        }
    return false;
    }
}
