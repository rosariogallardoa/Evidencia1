package com.ventanas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codigo.Doctor;
import com.codigo.RespuestaProceso;
import  com.codigo.Utilerias;

public class DoctoresVentana extends  JFrame {
    private JPanel panel;
    private JLabel lblE_IdDoctor;
    private JLabel lblIdDoctor;
    private JLabel lblE_Status;
    private JLabel lblStatus;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtConsultorio;
    private JButton btnGuardar;
    private JButton btnSalir;
    private JLabel lblError;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JTextField txtServicios;
    private JComboBox comboEspecialidad;
    private JComboBox comboTurno;
    private JComboBox comboHorario;
    private JLabel lblTurnosHorarios;
    private JButton btnAgregar;

    private HashMap<String,String> turnos;
    private int[] listaHorarios;

    public JPanel getPanel(){
        return panel;
    }
    public void setUsuario(String valor){ lblUsuario.setText(valor);}
    public void setContrasenia(String valor){ lblContrasenia.setText(valor);}
    public void setLblIdDoctor(int valor){ lblIdDoctor.setText(Integer.toString(valor));}

    public DoctoresVentana() {
        //agregando el titulo a la aplicacion
        super(Utilerias.tituloAplicacion);
        lblUsuario.setVisible(false);
        lblContrasenia.setVisible(false);
        lblTurnosHorarios.setText("");
        listaHorarios = new int[14];

        muestraError(false,"");
        llenaEspecialidad();
        llenaTurno();

        //Limpiar el texto de los id y status al cargar la ventana
        MuestraIdDoctor_Status("","activo");

        //agregando listeners para las acciones
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardar();
                } catch (Exception ex) {
                    muestraError(true, "Guardar. Ex:"+ ex.getMessage());
                    //throw new RuntimeException(ex);
                }
            }
        });
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
dispose();
            }
        });

        comboTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboTurno.getSelectedIndex() ==0){
                    muestraError(true, "Selecione una opción válida");
                    return;
                }
                llenaHorario();
            }
        });
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //agregaHorario();
            }
        });
    }

    public void muestraDoctor(int idEntidad){
        Doctor entidadBuscar = new Doctor();
        entidadBuscar = entidadBuscar.obtieneDoctorPorId(idEntidad);
        if(!entidadBuscar.getMensajeError().equals("")){
            muestraError(true,entidadBuscar.getMensajeError() );
            return;
        }
        pintaInformacion(entidadBuscar);
    }

    private void llenaEspecialidad(){

        comboEspecialidad.addItem("Selecciona opcion");
        comboEspecialidad.addItem("Medico Familiar");
        comboEspecialidad.addItem("Ginecologia");
        comboEspecialidad.addItem("Cardiologia");
        comboEspecialidad.addItem("Neumologia");
        comboEspecialidad.addItem("Ortopedia");
    }

    private void llenaTurno(){

        comboTurno.addItem("Selecciona opcion");
        comboTurno.addItem("Matutino");
        comboTurno.addItem("Vespertino");

    }

    private void llenaHorario(){
        String cadena = "";
        int inicio, fin;

        if(comboHorario.getItemCount()>0){
            comboHorario.removeAllItems();
        }

        comboHorario.addItem("Selecciona opcion");

        if(comboTurno.getSelectedItem().toString().contains("Matutino")) {
            inicio = 8;
            fin = 14;
            for(int i=inicio; i<fin;i++)
            {
                cadena = String.format("%d:00-%d:00",i,i+1);
                comboHorario.addItem(cadena);
            }
        }
        else if(comboTurno.getSelectedItem().toString().contains("Vespertino")) {
            inicio = 14;
            fin = 19;
            for(int i=inicio; i<fin;i++)
            {
                cadena = String.format("%d:00-%d:00",i,i+1);
                comboHorario.addItem(cadena);
            }
        }
    }

    private void MuestraIdDoctor_Status(String consecutivo, String estatus){
        lblIdDoctor.setText(consecutivo.toString());
        lblStatus.setText(estatus);
    }

    private void guardar() throws Exception {
        int idDoctor;
        List<Doctor> listaDoctor = new ArrayList<Doctor>();
        RespuestaProceso respuesta = new RespuestaProceso();
        Doctor doc = new Doctor();
        doc.setStatus("activo");
        doc.setNombreCompleto(txtNombre.getText());
        doc.setPerfil("Doctor");
        doc.setEspecialidad(comboEspecialidad.getSelectedItem().toString());
        doc.setCedulaProfesional(txtCedula.getText().trim());
        doc.setConsultorio(txtConsultorio.getText().trim());
        doc.setTurnosHorarios("NA");
        doc.setServicios(txtServicios.getText());

        //valida campos
        respuesta = doc.validaDoctor();
        if(respuesta.getAccion() == 0){
            muestraError(true, respuesta.getMensaje());
            return;
        }

        //obtiene y asignar id
        listaDoctor = doc.listaDoctores();
        idDoctor = doc.obtieneId(listaDoctor);
        if(idDoctor == -1){
            muestraError(true, "Hubo un error al obtener IdDoctor");
            return;
        }
        if(listaDoctor == null){
            listaDoctor = new ArrayList<Doctor>();
        }
        doc.setIdDoctor(idDoctor);
        listaDoctor.add(doc);

        //guardar campos
        String cadena = doc.toString();
        Utilerias.guardarListaDoctor(Utilerias.doctoresRutaArchivo, listaDoctor,true, true);
        if(Utilerias.mensajeError != ""){
            muestraError(true, Utilerias.mensajeError);
            return;
        }

        //guardando usuario
        //Usuario u = new Usuario(lblUsuario.getText(), lblContrasenia.getText(), "Doctor","activo");
        String cadenaUsuario = String.format("%s|%s|%s|%s|%d", lblUsuario.getText(), lblContrasenia.getText(), "Doctor","activo",idDoctor);
        Utilerias.guardar(Utilerias.usuariosRutaArchivo,cadenaUsuario,true, false);

        lblIdDoctor.setText(Integer.toString(idDoctor));
        lblStatus.setText("activo");
        muestraError(true, "Proceso Exitoso");
    }

    private void muestraError(boolean ver, String cadena){
        lblError.setVisible(ver);
        lblError.setText(cadena);
    }

    private void limpiaPantalla(){

    }

    private void pintaInformacion(Doctor doctor){
        lblIdDoctor.setText(String.format("%d", doctor.getId_doctor()));
        lblStatus.setText(doctor.getStatus());
        txtNombre.setText(doctor.getNombreCompleto());
        comboEspecialidad.setSelectedItem(doctor.getEspecialidad());
        txtCedula.setText(doctor.getCedulaProfesional());
        txtConsultorio.setText(doctor.getConsultorio());
        txtServicios.setText(doctor.getServicios());
    }

    private void agregaHorario(){
        String cadena = "", valores = "";
        String primero = "";
        turnos = new HashMap<String,String>();

        primero = comboHorario.getSelectedItem().toString().substring(0,1);
        cadena = String.format("%s,%s*",comboTurno.getSelectedItem().toString(),comboHorario.getSelectedItem().toString());

        if(!turnos.containsKey(primero)){
            turnos.put(primero,cadena);
        }

        /*
        for(Map.Entry m: turnos.entrySet()){
            valores = m.getValue(m.getKey()).toString();
        }

        lblTurnosHorarios.setText(valores);
         */
    }
}
