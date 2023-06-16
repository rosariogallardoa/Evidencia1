package com.ventanas;

import com.codigo.Doctor;
import com.codigo.Paciente;
import com.codigo.RespuestaProceso;
import com.codigo.Utilerias;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PacienteVentana extends  JFrame {
    private JPanel panel1;
    private JLabel lblId_Paciente;
    private JLabel lblStatus;
    private JTextField txtNombreCompleto;
    private JTextField txtEdad;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JLabel lblError;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JButton btnGuardar;
    private JButton btnSalir;


    public JPanel getPanel(){
        return panel1;
    }
    public void setUsuario(String valor){ lblUsuario.setText(valor);}
    public void setContrasenia(String valor){ lblContrasenia.setText(valor);}
    public void setLblIdPaciente(int valor){ lblId_Paciente.setText(Integer.toString(valor));}


    public PacienteVentana() {
        super(Utilerias.tituloAplicacion);
        lblUsuario.setVisible(false);
        lblContrasenia.setVisible(false);
        muestraError(false,"");

        //Limpiar el texto de los id y status al cargar la ventana
        MuestraIdPaciente_Status("","activo");

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardar();
                } catch (Exception ex) {
                    muestraError(true, "Guardar. Ex:"+ ex.getMessage());
                }
            }
        });
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void muestraPaciente(int idEntidad){
        Paciente entidadBuscar = new Paciente();
        entidadBuscar = entidadBuscar.obtienePacientePorId(idEntidad);
        if(!entidadBuscar.getMensajeError().equals("")){
            muestraError(true,entidadBuscar.getMensajeError() );
            return;
        }
        pintaInformacion(entidadBuscar);
    }

    private void MuestraIdPaciente_Status(String consecutivo, String estatus){
        lblId_Paciente.setText(consecutivo);
        lblStatus.setText(estatus);
    }

    private void guardar() throws Exception {
        int idPaciente;
        List<Paciente> listaPaciente = new ArrayList<Paciente>();
        RespuestaProceso respuesta = new RespuestaProceso();
        Paciente pac = new Paciente();
        pac.setStatus("activo");
        pac.setNombreCompleto(txtNombreCompleto.getText());
        pac.setPerfil("Paciente");
        pac.setEdad(txtEdad.getText());
        pac.setCorreo(txtCorreo.getText().trim());
        pac.setTelefono(txtTelefono.getText().trim());

        //valida campos
        respuesta = pac.validaPaciente();
        if(respuesta.getAccion() == 0){
            muestraError(true, respuesta.getMensaje());
            return;
        }

        //obtiene y asignar id
        listaPaciente = pac.listaPacientes();
        idPaciente = pac.obtieneId(listaPaciente);
        if(idPaciente == -1){
            muestraError(true, "Hubo un error al obtener IdPaciente");
            return;
        }
        if(listaPaciente == null){
            listaPaciente = new ArrayList<Paciente>();
        }
        pac.setId_paciente(idPaciente);
        listaPaciente.add(pac);

        //guardar campos
        String cadena = pac.toString();
        Utilerias.guardarListaPaciente(Utilerias.pacientesRutaArchivo, listaPaciente,true, true);
        if(Utilerias.mensajeError != ""){
            muestraError(true, Utilerias.mensajeError);
            return;
        }

        //guardando usuario
        //Usuario u = new Usuario(lblUsuario.getText(), lblContrasenia.getText(), "Doctor","activo");
        String cadenaUsuario = String.format("%s|%s|%s|%s|%d", lblUsuario.getText(), lblContrasenia.getText(), "Paciente","activo", idPaciente);
        Utilerias.guardar(Utilerias.usuariosRutaArchivo,cadenaUsuario,true, false);

        lblId_Paciente.setText(Integer.toString(idPaciente));
        lblStatus.setText("activo");
        muestraError(true, "Proceso Exitoso");
    }

    private void muestraError(boolean ver, String cadena){
        lblError.setVisible(ver);
        lblError.setText(cadena);
    }

    private void limpiaPantalla(){

    }

    private void pintaInformacion(Paciente paciente){
        lblId_Paciente.setText(String.format("%d", paciente.getId_paciente()));
        lblStatus.setText(paciente.getStatus());
        txtNombreCompleto.setText(paciente.getNombreCompleto());
        txtEdad.setText(paciente.getEdad());
        txtCorreo.setText(paciente.getCorreo());
        txtTelefono.setText(paciente.getTelefono());
    }

}
