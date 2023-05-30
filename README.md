# Examen-Final

# Patrones de diseño

Los patrones de diseño que se implementaron en el proyecto fueron:

- DAO (Data Access Object)
- singleton
- Decorator

## Diagrama de clase

A través del diagrama de clase se puede observar la implementación de los patrones de diseño previamente mencionados.

- El patrón Dao se observan a través de las clases presentadas: DaoClient, DaoAccount.
- El patrón Singleton se ve reflejado en mi clase ConectionSingleton.
- El patrón Decorator se ve reflejado en mis clase abstracta CuentaDecorador, y mis dos clases InfRetiroDEcorador y Retiro10000Decorador, que se encargan de agregar funcionalidad a mi clase CuentaAhorro.

## Importancia de los patrones de diseño

### DAO

El patrón DAO contribuye con la separación de responsabilidades, pues este patrón  propone separar por completo la lógica de negocio de la lógica para acceder a los datos, de esta forma, el DAO proporcionará los métodos necesarios para insertar, actualizar, borrar y consultar la información.
 Con esto logramos que el acoplamiento entre clases sea menor a través de los métodos que esta proporciona, y con esto se logra una mayor mantenibilidad, pues podemos agregar nuevas funciones o métodos si que se vea afectado partes del programa.
 
### singleton

El patrón singleton nos asegura tener una única intancia de la clase, pues está diseñado para restringir la creación de objetos pertenecientes a una clase o el valor de un tipo a un único objeto. 
 Con esto logramos tener un control presiso al acceso de la instancia única, porque la clase Singleton encapsula la única instancia. Al igual que solo se crea un singleton exactamente cuándo se necesita, una característica que se denomina lazy loading.
 
### Decorator

La utilidad principal del patrón Decorator es la de dotar de funcionalidades dinámicamente a objetos mediante composición. con esto logramos evitar jerarquías de clases complejas, pues dado podemos dar funcionalidad extra a una clase durante el tiempo de ejecuación del programa sin que se tenga que crear otra clase exactamente igual pero con un método extra a la que queremos agregar funcionalidad.
 Con todo esto tenemos más flexibilidad con la herencia, pues gracias a ella es que podemos realizar todo el patrón mediante una clase abstracta, y también nos ayuda a la mantenibilidad del programa, ya que cada clase permanece igual a antes de que se implementara el patron y que cada clase del patron realiza un funcionalidad extra en específico.
 
Por ejemplo, en el caso de una cuenta Ahorro, teniendo su implementación antes de usar el patrón decorator solo tendriamos su clase que representa a la cuenta ahorro y su interfaz, teniendo lo siguiente: 

 ```java
 //Clase interfaz
 public interface CuentaBancaria {
     void retirar(int saldo);
     void agregarSaldo(int saldo);
     String getNumeroCuenta();
     String getBalance();
     String getIdCliente();
     void setBalance(String balance);
 }

 //Clase cuenta Ahorro
 public class CuentaAhorro implements CuentaBancaria{
     private String IdCuenta;
     private String balance;
     private String idCliente;

     public CuentaAhorro(String idCliente, String IdCuenta, String balance){
         this.IdCuenta = IdCuenta;
         this.balance = balance;
         this.idCliente = idCliente;
     }

     @Override
     public String getIdCliente() {
         return this.idCliente;
     }

     public void setIdCliente(String id){
         this.idCliente = id;
     }

     @Override
     public void retirar(int saldo) {
         String.valueOf(Double.parseDouble(this.balance) -  saldo);

     }

     @Override
     public void agregarSaldo(int saldo) {
         String.valueOf(Double.parseDouble(this.balance ) + saldo);
     }

     @Override
     public String getNumeroCuenta() {
         return this.IdCuenta;
     }

     @Override
     public String getBalance() {
         return this.balance;
     }

     @Override
     public void setBalance(String balance) {
         this.balance = balance;
     }

 }

Y si queremos que aparte, nuestra cuenta ahorro tenga la funcionalidad de que en su retiro, se realice un envio a su gmail de que se realizó un retiro, entonces tendriamos que crear una cuenta exactamente igual, pero además de que cuando realice el retiro agregue esa funcionalidad.
 Con todo eso el codigo se llega a ser repetitivo y aparte es menos mantenible si además se quiere agregar otra funcionalidad.
 
Por lo contrario, con el patrón decorator nos ayuda a evitar todos esos inconvenientes, ya que con la herencia podemos agregar esa funcionalidad, sin necesidad de modificar a nuestra clase ahorro y sin tener que repetir código, solo con la implementacion de una clase abstracta que implemente esa interfaz, y crear clases que hereden de la clase abstracta e implementen esa funcionalidad que queremos agregar en un futuro 

 ```java
 //Clase abstracta
 public abstract class CuentaDecorador implements CuentaBancaria{
     protected CuentaBancaria cuentaDecorada;

     public CuentaDecorador (CuentaBancaria cuentaDecorada){
         this.cuentaDecorada = cuentaDecorada;
     }

     @Override
     public void retirar(int saldo){
         this.cuentaDecorada.retirar(saldo);
     }
 }

 //Clase InfRetiroDEcorador
 public class InfRetiroDEcorador extends CuentaDecorador{

     private static String emailFrom = "erickarzat11@gmail.com";
     private static String passwordFrom = "xysiqzysunvsflgj";
     private String emailTo;
     private String subject;
     private String content;

     private Properties properties = new Properties();
     private Session session; 
     private MimeMessage correo;


     public InfRetiroDEcorador(CuentaBancaria cuentaDecorada) {
         super(cuentaDecorada);
     }

     @Override
     public void retirar(int saldo) {
         System.out.println("creando email");
         this.cuentaDecorada.retirar(saldo);
         createEmail();
         sendEmail();
     }

     //Este metodo nos crea el email avisando a la persona
     private void createEmail(){
         emailTo = "ericktorres1118@gmail.com";
         subject = "Informacion de su cuenta ahorro";
         content = "";

         properties.put("mail.smtp.host", "smtp.gmail.com");
         properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
         properties.setProperty("mail.smtp.starttls.enable", "true");
         properties.setProperty("mail.smtp.port", "587");
         properties.setProperty("mail.smtp.user", emailFrom);
         properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
         properties.setProperty("mail.smtp.auth", "true");

         session = Session.getDefaultInstance(properties);


         try {

             correo = new MimeMessage(session);
             correo.setFrom(new InternetAddress(emailFrom));
             correo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
             correo.setSubject(subject);
             correo.setText(content, "ISO-8859-1", "html");

         } catch (MessagingException e) {

         }
     }

     //Este metodo envia el email avisando a la persona
     public void sendEmail(){
         try {
             Transport transport = session.getTransport("smtp");
             transport.connect(emailFrom, passwordFrom);
             transport.sendMessage(correo, correo.getRecipients(Message.RecipientType.TO));
             transport.close();
             System.out.println("correo enviado");
         } catch (NoSuchProviderException e) {
             e.printStackTrace();
         } catch (MessagingException e) {
             e.printStackTrace();
         }
     }

   
Un ejemplo a la hora de crear una cuenta ahorro podria ser la siguiente: 

 public class Main {
     public static void main(String[] args) throws Exception {

         CuentaBancaria cuenta = new InfRetiroDEcorador(new CuentaAhorro());

         cuenta.retirar(1000);

     }
 }

Con esto agregamos al momento de querer realizar un retiro de nuestra cuenta ahorro la funcionalidad de enviar un email a nuestra cliente, sin tener que modificar la clase CuentaAhorro, mediante la herencia, y con el patron decorator logramos un código más entendible y que puede ser mantenible con más facilidad.
