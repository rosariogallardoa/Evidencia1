package com.codigo;

public class RespuestaProceso {

    private int accion;
    private String mensaje;

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public RespuestaProceso(){}

    public RespuestaProceso(int accionN, String mensajeN){
        this.accion = accionN;
        this.mensaje = mensajeN;
    }

}
