package interfaze;

//CuentaBancaria es nuestra clase interface que nos ayuda a extender los metodos que puede realizar una cuenta bancaria
public interface CuentaBancaria {
    void retirar(int saldo);
    void agregarSaldo(int saldo);
    String getNumeroCuenta();
    String getBalance();
    String getIdCliente();
    void setBalance(String balance);
}
