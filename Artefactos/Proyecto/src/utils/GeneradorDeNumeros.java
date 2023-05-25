package utils;

import java.util.Random;

//GeneradorDeNumeroses una clase que nos ayuda a generar los numeros aleatorios de los numeros del usuario, y los numeros de las cuentas
public class GeneradorDeNumeros {

    //Este metodo genera los numeros de forma aleatoria
    public static String generarNumero16Diitos(){
        Random random = new Random();
        long numero = random.nextLong() % 9000000000000000L + 1000000000000000L;
        if(numero < 0)
            return String.valueOf(numero*(-1));
        
        return String.valueOf(numero);
    }
}
