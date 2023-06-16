package com.codigo;

import jdk.jshell.execution.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {

    private HashMap<String, Usuario> usuarios;
    private String usuario;
    private String contrasenia;
    private String perfil;
    private String status;
    private int idEntidad;


    public HashMap getUsuarios(){
        return usuarios;
    }
    public String getUsuario(){
        return usuario;
    };
    public String getContrasenia(){
        return contrasenia;
    };
    public String getPerfil(){
        return perfil;
    };
    public String getStatus(){
        return status;
    };
    public int getIdEntidad(){
        return idEntidad;
    };

    public void setUsuario(String valor){
        usuario = valor;
    };
    public void setContrasenia(String valor){
        contrasenia = valor;
    };
    public void setPerfil(String valor){
        perfil = valor;
    };
    public void setStatus(String valor){
        status = valor;
    };
    public void setIdEntidad(int valor){
        idEntidad = valor;
    };

public String mensajeError;

    public Usuario(){

    }

    public Usuario(String usuarioN, String contraseniaN, String perfilN, String statusN, String mensajeN, int idEntidadN){
        this.usuario = usuarioN;
        this.contrasenia = contraseniaN;
        this.perfil = perfilN;
        this.status = statusN;
        this.mensajeError = mensajeN;
        this.idEntidad = idEntidadN;
    }

    @Override
    public String toString() {
        String cadena = "";
        cadena = String.format("%s|%s|%s|%s|%d", usuario,contrasenia, perfil, status,idEntidad);
        return cadena;
    }

    public List<Usuario> listaUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        //BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            // inicializa variables de apoyo
            mensajeError = "";

            //validando si existe el archivo
            Utilerias.rutaArchivo = Paths.get(Utilerias.usuariosRutaArchivo);
            if(!Files.exists(Utilerias.rutaArchivo)) {
                mensajeError = "El archivo no existe";
                //System.out.println("El archivo no existe");
                return null;
            }

            bufferedReader = new BufferedReader(new FileReader(Utilerias.usuariosRutaArchivo));

            //lectura de archivo
            String linea;
            int contador = 0;
            while ((linea = bufferedReader.readLine()) != null) {
                contador++;
                String[] elem = linea.split("\\|");
                //String numero, nombre;

                //valida que se tengan numero y nombre
                int longitud = elem.length;
                if(elem.length!=5 && contador==1){
                    continue;
                }
                if(elem.length!=5 && contador!=1){
                    mensajeError += String.format("Linea %d con error(1):%s%s",contador,linea, Utilerias.saltoLinea);
                    break;
                }

                if(elem[0].trim() == ""){
                    mensajeError += String.format("Linea %d con error(2):%s%s",contador,linea, Utilerias.saltoLinea);
                    break;
                }

                if(elem[1].trim() == ""){
                    mensajeError += String.format("Linea %d con error(3):%s%s",contador,linea, Utilerias.saltoLinea);
                    break;
                }

                int numId = Integer.valueOf(elem[4].trim());
                Usuario u = new Usuario(elem[0].trim(), elem[1].trim(), elem[2].trim(), elem[3].trim(),"",numId);

                //usuarios.put(elem[0].trim(),u);
                listaUsuarios.add(u);
                //System.out.println(line);
                //bufferedWriter.write(line + "\n");
            }

            ////valida si encontro errores en la lectura del archivo que termine y rgrese false
            //if(mensajeError.trim()!=""){
            //    return ;
            //}

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
        return  listaUsuarios;
    }

    public String validaUsuario(){
        if(usuarios == null || usuarios.size()== 0){
            return "No hay usuarios";
        }

        if(!usuarios.containsKey(usuario)){
            return "El usuario o contrasenia no son correctos(1)";
        }

        Usuario usu = usuarios.get(usuario);
        String contra = usu.getContrasenia().trim();
        if( contra != contrasenia.trim()){
            return "El usuario o contrasenia no son correctos(2)";
        }

        if(usu.perfil != perfil){
            return "El usuario o contrasenia no son correctos(3)";
        }

        return "";
    }

    public RespuestaProceso validaUsuarioLista(List<Usuario> listaUsuarios,Usuario usuarioProporcionado){
        //List<Usuario> listaTemp = new ArrayList<Usuario>();
        //listaTemp.add(usuarioProporcionado);
        boolean existe = false;
        RespuestaProceso respuesta;

        if(listaUsuarios == null || listaUsuarios.size()== 0){
            respuesta = new RespuestaProceso(0,"No hay usuarios");
            return respuesta;
        }
/*
        for(Usuario elem: listaUsuarios){
            if(!elem.getUsuario().equals(usuarioProporcionado.getUsuario()) ){
                return "El usuario o contrasenia no son correctos(1)";
            }
            if(!elem.getContrasenia().equals(usuarioProporcionado.getContrasenia())){
                return "El usuario o contrasenia no son correctos(2)";
            }
            //if(!elem.getPerfil().equals(usuarioProporcionado.getPerfil())){
            //    return "El usuario o contrasenia no son correctos(3)";
            //}
        }

 */
        for(Usuario elem: listaUsuarios){
            if(!elem.getUsuario().equals(usuarioProporcionado.getUsuario()) ){
                continue;
            }
            existe = true; //indicaando que se encontro el usuario en la lista

            if(!elem.getContrasenia().equals(usuarioProporcionado.getContrasenia())){
                respuesta = new RespuestaProceso(-1,"El usuario o contrasenia no son correctos");
                return respuesta;
            }

            usuarioProporcionado.setPerfil(elem.getPerfil());
            usuarioProporcionado.setIdEntidad(elem.getIdEntidad());
            break;
            //if(!elem.getPerfil().equals(usuarioProporcionado.getPerfil())){
            //    return "El usuario o contrasenia no son correctos(3)";
            //}
        }

        if(!existe){
            respuesta = new RespuestaProceso(0,"El usuario no existe");
            return respuesta;
        }

        respuesta = new RespuestaProceso(1,"");
        return respuesta;
    }

    public void eliminaCaracteresEspeciales(){
        this.usuario = this.usuario.replace("|","");
        this.contrasenia = this.contrasenia.replace("|","");
    }


}
