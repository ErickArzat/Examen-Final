import domain.CuentaAhorro;
import interfaze.CuentaBancaria;
import patron.InfRetiroDEcorador;
import patron.Retiro10000Decorador;

public class App {
    public static void main(String[] args) throws Exception {
        
        
        CuentaBancaria cuenta = new InfRetiroDEcorador(new CuentaAhorro(10000));

        cuenta.retirar(1000);
        

        CuentaBancaria cuenta2 = new Retiro10000Decorador(new InfRetiroDEcorador(new CuentaAhorro(10000)));

        cuenta2.retirar(1000);
    }
}
