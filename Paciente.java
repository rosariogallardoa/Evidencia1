package com.codigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id_paciente;
    private String status;
    private String nombreCompleto;
    private String perfil;
    private String edad;
    private String correo;
    private String telefono;
    private String mensajeError;

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int valor) {
        this.id_paciente = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String valor) {
        this.status = valor;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String valor) {
        this.nombreCompleto = valor;
    }

    public String getPerfil(){ return perfil;}

    public void setPerfil(String valor){ perfil = valor;}

    public String getEdad() {
        return edad;
    }

    public void setEdad(String valor) {
        this.edad = valor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String valor) {
        this.correo = valor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String valor) {
        this.telefono = valor;
    }

    public String getMensajeError(){ return mensajeError;}
    public void setMensajeError(String valor){ mensajeError = valor;}


    public Paciente(){

    }

    public Paciente(int id_pacienteN, String statusN, String nombreCompletoN, String perfilN, String edadN, String correoN, String telefonoN){
        this.id_paciente = id_pacienteN;
        this.status = statusN;
        this.nombreCompleto = nombreCompletoN;
        this.perfil = perfilN;
        this.edad = edadN;
        this.correo = correoN;
        this.telefono = telefonoN;
    }

    public Paciente(String[] valores){
        int[] numerosEnteros = new int[1];
        Utilerias.esNumero(valores[0], numerosEnteros);
        int valorId = numerosEnteros[0];
        this.id_paciente = valorId;
        this.status = valores[1];
        this.nombreCompleto = valores[2];
        this.perfil = valores[3];
        this.edad = valores[4];
        this.correo = valores[5];
        this.telefono = valores[6];
    }

    @Override
    public String toString() {
        String cadena = "";
        cadena = String.format("%d|%s|%s|%s|%s|%s|%s", id_paciente,status, nombreCompleto,perfil, edad, correo, telefono);
        return cadena;
    }

    public RespuestaProceso validaPaciente(){
        RespuestaProceso respuesta;

        if(this.nombreCompleto.trim() == ""){
            respuesta = new RespuestaProceso(0,"El nombre no es válido");
            return respuesta;
        }

        int[] numeros = new int[1];
        Utilerias.esNumero(this.edad,numeros);
        if(numeros[0] < 1){
            respuesta = new RespuestaProceso(0,"La edad no es válido");
            return respuesta;
        }

        if(!this.edad.matches("[0-9]*")){
            respuesta = new RespuestaProceso(0,"La edad no debe contener letras");
            return respuesta;
        }

        if(this.correo == ""){
            respuesta = new RespuestaProceso(0,"EL correo no es válido");
            return respuesta;
        }

        if(this.telefono == "" || this.telefono.length()!=10){
            respuesta = new RespuestaProceso(0,"El telefono no es válido");
            return respuesta;
        }

        if(!this.telefono.matches("[0-9]*")){
            respuesta = new RespuestaProceso(0,"El telefono no debe contener letras");
            return respuesta;
        }

        respuesta = new RespuestaProceso(1,"");
        return respuesta;
    }

    public List<Paciente> listaPacientes(){
        List<Paciente> listaEntidad = new ArrayList<Paciente>();
        //BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            // inicializa variables de apoyo
            mensajeError = "";

            //validando si existe el archivo
            Utilerias.rutaArchivo = Paths.get(Utilerias.pacientesRutaArchivo);
            File file = new File(Utilerias.pacientesRutaArchivo);
            if(!Files.exists(Utilerias.rutaArchivo)) {
                file.createNewFile();
                mensajeError = "El archivo no existe";
                //System.out.println("El archivo no existe");
                return listaEntidad;
            }

            bufferedReader = new BufferedReader(new FileReader(Utilerias.pacientesRutaArchivo));

            //lectura de archivo
            String linea;
            int contador = 0;
            while ((linea = bufferedReader.readLine()) != null) {
                contador++;
                String[] elem = linea.split("\\|");
                //String numero, nombre;

                //valida que se tengan los campos
                int longitud = elem.length;
                if(elem.length!=7){
                    mensajeError += String.format("Linea %d con error(1):%s%s",contador,linea, Utilerias.saltoLinea);
                    continue;
                }

                if(elem[0].trim() == ""){
                    mensajeError += String.format("Linea %d con error(2):%s%s",contador,linea, Utilerias.saltoLinea);
                    continue;
                }

                if(elem[1].trim() == ""){
                    mensajeError += String.format("Linea %d con error(3):%s%s",contador,linea, Utilerias.saltoLinea);
                    continue;
                }

                Paciente d = new Paciente(elem);
                listaEntidad.add(d);
            }
        }
        catch(IOException ex){
            System.out.println("Hubo un error al leer: " + ex.getMessage());
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing in read: " + e.getMessage());
            }

        }
        return  listaEntidad;
    }

    public int obtieneId(List<Paciente> lista)  {
        //List<Doctor> lista = new ArrayList<Doctor>();
        int idCreado = -1;
        try {
            //lista = listaDoctores();
            if (lista == null || lista.size() == 0) {
                idCreado = 1;
            }
            else {
                //obtener el maxino valor del id
                //se usa palabra clave Optional para que acepte valores nulos en caso de no tener datos
                //Doctor maxAge = lista
                //        .stream()
                //        .max(Comparator.comparing(Doctor::getId_doctor));
                //       //.max(Comparator.comparing(v->v));
                //       .orElseThrow(Exception::new);

                int valor=-1;
                int valorMax=-1;
                int indice = 0;
                int indiceMax = -1;
                for(Paciente elem: lista){

                    valor = elem.getId_paciente();
                    if(valor >= valorMax){
                        valorMax = valor;
                        indiceMax = indice;
                    }

                    indice += 1;

                }

                //obtener id
                Paciente maxAge = lista.get(indiceMax);
                //aumentar en 1 el Id
                if (maxAge == null) {
                    //hubo un error
                    idCreado = -1;
                }
                //else if(maxAge.()){
                //    idCreado = 1;
                //}
                else {
                    idCreado = maxAge.getId_paciente() + 1;
                }
            }
        } finally {

        }
        return idCreado;
    }

    public Paciente obtienePacientePorId(int idEntidad){
        Paciente entidadEntontrada = new Paciente();
        //BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            // inicializa variables de apoyo
            entidadEntontrada.mensajeError = "";

            //validando si existe el archivo
            Utilerias.rutaArchivo = Paths.get(Utilerias.pacientesRutaArchivo);
            if(!Files.exists(Utilerias.rutaArchivo)) {
                entidadEntontrada.mensajeError = "El archivo no existe";
                //System.out.println("El archivo no existe");
                return entidadEntontrada;
            }

            bufferedReader = new BufferedReader(new FileReader(Utilerias.pacientesRutaArchivo));

            //lectura de archivo
            String linea;
            String idEntidadCadena ="";
            boolean existe = false;
            int contador = 0;
            while ((linea = bufferedReader.readLine()) != null) {
                contador++;
                String[] elem = linea.split("\\|");
                //String numero, nombre;

                //valida que se tengan los campos
                if(elem.length!=7){
                    entidadEntontrada.mensajeError += String.format("Linea %d con error(1):%s%s",contador,linea, Utilerias.saltoLinea);
                    continue;
                }

                idEntidadCadena = elem[0].trim();
                if(idEntidadCadena.equals(Integer.toString(idEntidad))){
                    entidadEntontrada = new Paciente(elem);
                    entidadEntontrada.mensajeError = "";
                    existe = true;
                    break;
                }
            }

            if(!existe){
                entidadEntontrada.mensajeError = String.format("No existe el Paciente %d" ,idEntidad);
            }
        }
        catch(IOException ex){
            entidadEntontrada.mensajeError = String.format("Hubo un error al leer archivo: " + ex.getMessage());
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                entidadEntontrada.mensajeError = String.format("Hubo un error al leer archivo. IOException catched while closing in read:" + e.getMessage());
            }

        }
        return entidadEntontrada;
    }


}
