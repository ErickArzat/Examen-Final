package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Equalizer es una clase que nos ayuda a validar cadenas que son iguales
public class Equalizer {

    
    //este metodo nos ayuda a validar cadenas que son iguales
    public static boolean isMatch(String cadena, Pattern patron) {
        Matcher mat = patron.matcher(cadena);
        return mat.matches();
    }

}
