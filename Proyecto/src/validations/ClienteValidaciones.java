package validations;

import java.util.regex.Pattern;

import errors.ClienteException;
import utils.Equalizer;

//ClienteValidaciones es una clase que nos ayuda a validar los datos de un cliente
public class ClienteValidaciones {
    
    //Este metodo recibe como parametro el nombre del usuario, y nos ayuda a validar si contiene caracteres especiales
    public static boolean validarNombre(String nombre) throws ClienteException {
        Pattern patronNombre = Pattern.compile("[a-zA-Z ]+");

        if (!Equalizer.isMatch(nombre, patronNombre)) {
            throw new ClienteException("Error, hay caracteres invalidos en nombre del cliente");
        }

        return true;
    }

    //Este metodo recibe como parametro el curp del usuario, y nos ayuda a validar si el curp tiene una extension de 18 caracteres
    public static boolean validarCurp(String curp) throws ClienteException {
        Pattern patronCurp = Pattern.compile("[a-zA-Z0-9]{18}");
        
        if (!Equalizer.isMatch(curp, patronCurp)) {
            throw new ClienteException(
                    "Error, el curp del cliente es erroneo. Verifique que solo contenga 16 valores numericos");
        }

        return true; 
    }
}
