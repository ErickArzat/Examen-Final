package utils;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import domain.Client;
import interfaze.CuentaBancaria;

//ReporteCliente es una clase que nos ayuda a generar el reporte de todos los clientes
public class ReporteCliente {

    //Este metodo recibe como parametro todos los clientes que se encuentran registrados en la base de datos junto con sus cuentas
    public void generarReporte(ArrayList<Client> clientes) {
        Document documento = new Document();

        try {
            FileOutputStream archivoPdf = new FileOutputStream("ReporteDeClientes.pdf");
            PdfWriter.getInstance(documento, archivoPdf).setInitialLeading(20);
            documento.open();
            Paragraph titulo = new Paragraph("Reporte de Clientes y Cuentas");
            titulo.setAlignment(1);
            documento.add(titulo);
            documento.add(new Paragraph("\n"));
            
            for (Client cliente : clientes) {
                documento.add(new LineSeparator());
                documento.add( new Paragraph("No. Cliente: " + cliente.getId() + "               Nombre: " + cliente.getNombre() + "\n"));
                documento.add(new Paragraph("\n")); 
                documento.add(new LineSeparator());
                documento.add(new Paragraph("Cuentas")); 
                documento.add(obtenerDatosCuentasCliente(cliente.getListaCuentas()));  
                documento.add(new LineSeparator());
            }

            documento.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Este metodo recibe como parametro un ArrayList de Cuentas Bancarias de un cliente, y nos ayuda a escribir en el archivo los datos de todas sus cuentas
    private Paragraph obtenerDatosCuentasCliente(ArrayList<CuentaBancaria> cuentasCliente) {
        Paragraph datosCuenta = new Paragraph();       
        datosCuenta.setSpacingBefore(10); 
        datosCuenta.setSpacingAfter(20);

        for (CuentaBancaria cuenta : cuentasCliente) {
            datosCuenta.add("No. cuenta: " + cuenta.getNumeroCuenta() + "               Saldo: $" + cuenta.getBalance() + "\n");
        }

        return datosCuenta;

    }
}