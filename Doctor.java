package com.codigo;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Doctor {

    private int id_doctor;
    private String status;
    private String nombreCompleto;
    private String perfil;
    private String especialidad;
    private String cedulaProfesional;
    private String consultorio;
    private String turnosHorarios;
    private String servicios;
    private String mensajeError;

    public int getId_doctor(){ return id_doctor;}
    public String getStatus(){ return status;}
    public String getNombreCompleto(){ return nombreCompleto;}
    public String getPerfil(){ return perfil;}
    public String getEspecialidad(){ return especialidad;}
    public String getCedulaProfesional(){ return cedulaProfesional;}
    public String getConsultorio(){ return consultorio;}
    public String getTurnosHorarios(){ return turnosHorarios;}
    public String getServicios(){ return servicios;}
    public String getMensajeError(){ return mensajeError;}


    public void setIdDoctor(int valor){ id_doctor = valor;}
    public void setStatus(String valor){ status = valor;}
    public void setNombreCompleto(String valor){ nombreCompleto = valor;}
    public void setPerfil(String valor){ perfil = valor;}
    public void setEspecialidad(String valor){ especialidad = valor;}
    public void setCedulaProfesional(String valor){ cedulaProfesional = valor;}
    public void setConsultorio(String valor){ consultorio = valor;}
    public void setTurnosHorarios(String valor){ turnosHorarios = valor;}
    public void setServicios(String valor){ servicios = valor;}
    public void setMensajeError(String valor){ mensajeError = valor;}


    public Doctor(){

    }

    public Doctor(int id_doctorD, String statusD, String nombreCompletoD, String perfilD, String especialidadD, String cedulaProfesionalD, String consultorioD, String turnosHorariosD, String serviciosD){
        this.id_doctor = id_doctorD;
        this.status = statusD;
        this.nombreCompleto = nombreCompletoD;
        this.perfil = perfilD;
        this.especialidad = especialidadD;
        this.cedulaProfesional = cedulaProfesionalD;
        this.consultorio = consultorioD;
        this.turnosHorarios = turnosHorariosD;
        this.servicios = serviciosD;
    }

    public Doctor(String[] valores){
        int[] numerosEnteros = new int[1];
        Utilerias.esNumero(valores[0], numerosEnteros);
        int valorId = numerosEnteros[0];
        this.id_doctor = valorId;
        this.status = valores[1];
        this.nombreCompleto = valores[2];
        this.perfil = valores[3];
        this.especialidad = valores[4];
        this.cedulaProfesional = valores[5];
        this.consultorio = valores[6];
        this.turnosHorarios = valores[7];
        this.servicios = valores[8];

    }

    @Override
    public String toString() {
        String cadena = "";
        cadena = String.format("%d|%s|%s|%s|%s|%s|%s|%s|%s", id_doctor,status, nombreCompleto, perfil, especialidad, cedulaProfesional, consultorio,turnosHorarios, servicios);
        return cadena;
    }

    public RespuestaProceso validaDoctor(){
        RespuestaProceso respuesta;

        if(this.nombreCompleto.trim() == ""){
            respuesta = new RespuestaProceso(0,"El nombre no es válido");
            return respuesta;
        }

        if(this.especialidad == ""){
            respuesta = new RespuestaProceso(0,"La especialidad no es válido");
            return respuesta;
        }

        if(this.cedulaProfesional == ""){
            respuesta = new RespuestaProceso(0,"La cédula profesional no es válido");
            return respuesta;
        }

        if(this.consultorio == ""){
            respuesta = new RespuestaProceso(0,"El consultorio no es válido");
            return respuesta;
        }

        if(this.turnosHorarios == ""){
            respuesta = new RespuestaProceso(0,"El turno/horario no es válido");
            return respuesta;
        }

        if(this.servicios == ""){
            respuesta = new RespuestaProceso(0,"El servicio no es válido");
            return respuesta;
        }

        respuesta = new RespuestaProceso(1,"");
        return respuesta;
    }

    public List<Doctor> listaDoctores(){
        List<Doctor> listaDoctores = new ArrayList<Doctor>();
        //BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            // inicializa variables de apoyo
            mensajeError = "";

            //validando si existe el archivo
            Utilerias.rutaArchivo = Paths.get(Utilerias.doctoresRutaArchivo);
            File file = new File(Utilerias.pacientesRutaArchivo);
            if(!Files.exists(Utilerias.rutaArchivo)) {
                file.createNewFile();
                mensajeError = "El archivo no existe";
                //System.out.println("El archivo no existe");
                return listaDoctores;
            }

            bufferedReader = new BufferedReader(new FileReader(Utilerias.doctoresRutaArchivo));

            //lectura de archivo
            String linea;
            int contador = 0;
            while ((linea = bufferedReader.readLine()) != null) {
                contador++;
                String[] elem = linea.split("\\|");
                //String numero, nombre;

                //valida que se tengan los campos
                int longitud = elem.length;
                if(elem.length!=9){
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

                Doctor d = new Doctor(elem);
                listaDoctores.add(d);
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
        return  listaDoctores;
    }

    public int obtieneId(List<Doctor> lista)  {
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
                for(Doctor elem: lista){

                    valor = elem.getId_doctor();
                    if(valor >= valorMax){
                        valorMax = valor;
                        indiceMax = indice;
                    }

                    indice += 1;

                }

                //obtener id
                Doctor maxAge = lista.get(indiceMax);
                //aumentar en 1 el Id
                if (maxAge == null) {
                    //hubo un error
                    idCreado = -1;
                }
                //else if(maxAge.()){
                //    idCreado = 1;
                //}
                else {
                    idCreado = maxAge.getId_doctor() + 1;
                }
            }
        } finally {

        }
        return idCreado;
    }

    public Doctor obtieneDoctorPorId(int idEntidad){
        Doctor  doctorEntontrado = new Doctor();
        //BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            // inicializa variables de apoyo
            doctorEntontrado.mensajeError = "";

            //validando si existe el archivo
            Utilerias.rutaArchivo = Paths.get(Utilerias.doctoresRutaArchivo);
            if(!Files.exists(Utilerias.rutaArchivo)) {
                doctorEntontrado.mensajeError = "El archivo no existe";
                //System.out.println("El archivo no existe");
                return doctorEntontrado;
            }

            bufferedReader = new BufferedReader(new FileReader(Utilerias.doctoresRutaArchivo));

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
                if(elem.length!=9){
                    doctorEntontrado.mensajeError += String.format("Linea %d con error(1):%s%s",contador,linea, Utilerias.saltoLinea);
                    continue;
                }

                idEntidadCadena = elem[0].trim();
                if(idEntidadCadena.equals(Integer.toString(idEntidad))){
                    doctorEntontrado = new Doctor(elem);
                    doctorEntontrado.mensajeError = "";
                    existe = true;
                    break;
                }
            }

            if(!existe){
                doctorEntontrado.mensajeError = String.format("No existe el Doctor %d" ,idEntidad);
            }
        }
        catch(IOException ex){
            doctorEntontrado.mensajeError = String.format("Hubo un error al leer archivo: " + ex.getMessage());
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                doctorEntontrado.mensajeError = String.format("Hubo un error al leer archivo. IOException catched while closing in read:" + e.getMessage());
            }

        }
        return  doctorEntontrado;
    }

}
