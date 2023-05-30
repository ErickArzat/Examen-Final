package errors;

//ClienteException es una clase creada para lanzar una exception perzonalizada sobre los clientes
public class ClienteException extends Exception{
    public ClienteException(String message){
        super(message);
    }
}
