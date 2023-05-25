package errors;

//RetirorErrorException es una clase creada para lanzar una exception perzonalizada sobre los retiros
public class RetirorErrorException extends Exception{
    public RetirorErrorException(String message){
        super(message);
    }
}
