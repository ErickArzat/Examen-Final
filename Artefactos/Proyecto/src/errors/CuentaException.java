package errors;

//CuentaException es una clase creada para lanzar una exception perzonalizada sobre las cuentas
public class CuentaException extends Exception{
    public CuentaException(String message){
        super(message);
    }
}
