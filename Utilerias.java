package com.codigo;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Utilerias {

    public static String contactosRutaArchivo = "com/codigo/datos/contactos.txt"; //cuando se corre desde IDE
    public static String usuariosRutaArchivo = "src/com/codigo/datos/usuarios.txt"; //cuando se corre desde IDE
    public static String doctoresRutaArchivo = "src/com/codigo/datos/doctores.txt"; //cuando se corre desde IDE
    public static String pacientesRutaArchivo = "src/com/codigo/datos/pacientes.txt";
    public static String citasRutaArchivo = "src/com/codigo/datos/citas.txt";
    public static String logRutaArchivo = "com/codigo/datos/log.txt"; //cuando se corre desde IDE
    public static Path rutaArchivo; // = Paths.get(contactosRutaArchivo);
    public static String mensajeError = "";
    public static String saltoLinea = "\n";


    public static String tituloAplicacion = "Administrador de Citas ROSARIO";
    public static HashMap<String,Object> contactos = new HashMap<>();
    public static HashMap<String,Object> doctores = new HashMap<>();
    public static HashMap<String,Object> pacientes = new HashMap<>();

    public static String guardar(String ruta, String cadena, boolean agregaSaltoLinea, boolean eliminaArchivo){
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean procesoOk = false;

        try {
            String data = "";
            mensajeError = "";

            File file = new File(ruta);

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                if(eliminaArchivo){
                    file.delete();
                    file.createNewFile();
                }
            }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            //for (String cve : contactos.keySet())
            //{
            //System.out.println(cve + " - " + contactos.get(cve));
            if (agregaSaltoLinea) {
                data = cadena + saltoLinea;
            }
            //System.out.println("data: " + data);
            // flag true, indica adjuntar información al archivo.
            bw.write(data);
            //}

            System.out.println("Proceso exitoso!");

            procesoOk = true;
        } catch (IOException ex) {
            System.out.println("IOException catched while closing1: " + ex.getMessage());
            mensajeError = "IOException catched while closing1: " + ex.getMessage();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                System.out.println("IOException catched while closing2: " + e.getMessage());
                mensajeError = "IOException catched while closing2: " + e.getMessage();
            }
        }
        return mensajeError;
    }

    public static String guardarHashMap( boolean agregaSaltoLinea, boolean eliminaArchivo){
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean procesoOk = false;

        try {
            String data = "";
            mensajeError = "";

            File file = new File(contactosRutaArchivo);

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                if(eliminaArchivo){
                    file.delete();
                    file.createNewFile();
                }
            }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            for (String cve : contactos.keySet())
            {
            System.out.println(cve + " - " + contactos.get(cve));
            if (agregaSaltoLinea) {
                data = cve + " - " + contactos.get(cve) + saltoLinea;
            }
            System.out.println("data: " + data);
             //flag true, indica adjuntar información al archivo.
            bw.write(data);
            }

            System.out.println("Proceso exitoso!");

            procesoOk = true;
        } catch (IOException ex) {
            System.out.println("IOException catched while closing1: " + ex.getMessage());
            mensajeError = "IOException catched while closing1: " + ex.getMessage();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                System.out.println("IOException catched while closing2: " + e.getMessage());
                mensajeError = "IOException catched while closing2: " + e.getMessage();
            }
        }
        return mensajeError;
    }

    public static String guardarListaDoctor(String ruta, List<Doctor> lista, boolean agregaSaltoLinea, boolean eliminaArchivo){
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean procesoOk = false;

        try {
            String data = "";
            mensajeError = "";

            File file = new File(ruta);

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                if(eliminaArchivo){
                    file.delete();
                    file.createNewFile();
                }
            }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            for (Doctor cve : lista)
            {
                //System.out.println(cve + " - " + contactos.get(cve));
                if (agregaSaltoLinea) {
                    data = cve.toString() + saltoLinea;
                }
                //System.out.println("data: " + data);
                //flag true, indica adjuntar información al archivo.
                bw.write(data);
            }

            //System.out.println("Proceso exitoso!");

            procesoOk = true;
        } catch (IOException ex) {
            System.out.println("IOException catched while closing1: " + ex.getMessage());
            mensajeError = "IOException catched while closing1: " + ex.getMessage();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                System.out.println("IOException catched while closing2: " + e.getMessage());
                mensajeError = "IOException catched while closing2: " + e.getMessage();
            }
        }
        return mensajeError;
    }
    public static boolean esNumero(String cadena, int[] valorConvertido){
        try{
            Integer number = Integer.valueOf(cadena);
            valorConvertido[0] = number;
            return true;
        }
        catch (NumberFormatException ex){
            valorConvertido[0] = -1;
            return false;
        }
    }

    public static String guardarListaPaciente(String ruta, List<Paciente> lista, boolean agregaSaltoLinea, boolean eliminaArchivo){
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean procesoOk = false;

        try {
            String data = "";
            mensajeError = "";

            File file = new File(ruta);

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                if(eliminaArchivo){
                    file.delete();
                    file.createNewFile();
                }
            }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            for (Paciente cve : lista)
            {
                //System.out.println(cve + " - " + contactos.get(cve));
                if (agregaSaltoLinea) {
                    data = cve.toString() + saltoLinea;
                }
                //System.out.println("data: " + data);
                //flag true, indica adjuntar información al archivo.
                bw.write(data);
            }

            //System.out.println("Proceso exitoso!");

            procesoOk = true;
        } catch (IOException ex) {
            System.out.println("IOException catched while closing1: " + ex.getMessage());
            mensajeError = "IOException catched while closing1: " + ex.getMessage();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                System.out.println("IOException catched while closing2: " + e.getMessage());
                mensajeError = "IOException catched while closing2: " + e.getMessage();
            }
        }
        return mensajeError;
    }
}
