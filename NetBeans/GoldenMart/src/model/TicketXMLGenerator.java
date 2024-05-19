
package model;
/**
 *
 * @author Javs
 * 
 */

import javax.xml.stream.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TicketXMLGenerator {
    public int contadorArchivos = 0;

    public String generarXML(List<Producto> productosVendidos, float cantidadPagada, float cambio, String ultimosCuatroDigitosTarjeta, boolean esEfectivo) {
        StringWriter stringWriter = new StringWriter();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer;
        try {
            writer = outputFactory.createXMLStreamWriter(stringWriter);
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }

        try {
            writer.writeStartDocument();
            writer.writeStartElement("Ticket");
            escribirDetallesTicket(writer, productosVendidos, cantidadPagada, cambio, ultimosCuatroDigitosTarjeta, esEfectivo);
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }

        return stringWriter.toString();
    }

    public void escribirDetallesTicket(XMLStreamWriter writer, List<Producto> productosVendidos, float cantidadPagada, float cambio, String ultimosCuatroDigitosTarjeta, boolean esEfectivo) throws XMLStreamException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        writer.writeStartElement("DetallesGenerales");
        writer.writeStartElement("Fecha");
        writer.writeCharacters(dateFormat.format(new java.util.Date()));
        writer.writeEndElement();

        writer.writeStartElement("Hora");
        writer.writeCharacters(java.time.LocalTime.now().toString());
        writer.writeEndElement();

        writer.writeStartElement("TipoPago");
        if (esEfectivo) {
            writer.writeCharacters("Efectivo");
        } else {
            writer.writeCharacters("Tarjeta");
        }
        writer.writeEndElement();

        writer.writeStartElement("ProductosVendidos");
        for (Producto producto : productosVendidos) {
            writer.writeStartElement("Producto");
            writer.writeStartElement("Nombre");
            writer.writeCharacters(producto.getNombre());
            writer.writeEndElement();
            writer.writeStartElement("Precio");
            writer.writeCharacters(Float.toString(producto.getPrecio()));
            writer.writeEndElement();
            writer.writeEndElement();
        }
        writer.writeEndElement();

        if (esEfectivo) {
            writer.writeStartElement("CantidadPagada");
            writer.writeCharacters(Float.toString(cantidadPagada));
            writer.writeEndElement();

            writer.writeStartElement("Cambio");
            writer.writeCharacters(Float.toString(cambio));
            writer.writeEndElement();
        } else {
            writer.writeStartElement("UltimosCuatroDigitosTarjeta");
            writer.writeCharacters(ultimosCuatroDigitosTarjeta);
            writer.writeEndElement();
        }

        writer.writeEndElement(); // Cerrar DetallesGenerales
    }
    
    public void generarYGuardarXML(String xml) {
        String directorio = "C:/Users/CARLOS/Documents/Javs/IS2/GoldenMart/Golden-Mart/Tickets/";
        File carpetaTickets = new File(directorio);
        if (!carpetaTickets.exists()) {
            carpetaTickets.mkdirs();
        }

        String nombreBaseArchivo = "ticket";
        String nombreArchivoXML = nombreBaseArchivo + "_" + contadorArchivos + ".xml";
        String nombreArchivoXSL = nombreBaseArchivo + ".xsl"; // Mismo nombre base que el XML pero con extensión .xsl

        contadorArchivos++;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio + nombreArchivoXML))) {
            writer.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Aquí puedes generar dinámicamente el contenido del archivo XSLT
        String contenidoXSL = generarContenidoXSL();

        // Guardar el archivo XSLT en la misma ubicación que el XML
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio + nombreArchivoXSL))) {
            writer.write(contenidoXSL);
           // System.out.println("Archivo XSLT generado correctamente en: " + directorio + nombreArchivoXSL);
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Error al generar el archivo XSLT.");
        }

        // Transformar el XML en PDF utilizando XSLT
        try {
            Source xmlSource = new StreamSource(new StringReader(xml));
            Source xsltSource = new StreamSource(new File(directorio + nombreArchivoXSL)); // Utilizar el mismo directorio y nombre base pero con extensión .xsl
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xsltSource);

            // Preparar el resultado, en este caso un archivo PDF
            Result result = new StreamResult(new File(directorio + nombreArchivoXML.replace(".xml", ".pdf")));

            // Realizar la transformación
            transformer.transform(xmlSource, result);

            //System.out.println("Transformación XML a PDF completada con éxito.");
        } catch (TransformerException e) {
            e.printStackTrace();
            //System.out.println("Error durante la transformación XML a PDF: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error durante la generación del PDF.");
        }
    }
// Método para generar el contenido del archivo XSLT
    public String generarContenidoXSL() {
        // Aquí puedes escribir el contenido del archivo XSLT
        // Por ejemplo:
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n");
        sb.append("    <xsl:output method=\"text\"/>\n");
        sb.append("    <xsl:template match=\"/\">\n");
        sb.append("        <xsl:apply-templates/>\n");
        sb.append("    </xsl:template>\n");
        // Aquí puedes agregar más plantillas XSLT según sea necesario
        sb.append("</xsl:stylesheet>\n");
        return sb.toString();
    }

}


    

