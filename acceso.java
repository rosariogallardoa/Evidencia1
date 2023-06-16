package com.ventanas;

import com.codigo.RespuestaProceso;
import com.codigo.Usuario;
import com.codigo.Utilerias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class acceso extends  JFrame {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField txtContrasenia;
    private JComboBox comboPerfiles;
    private JButton btnIngresar;
    private JButton btnSalir;
    private JLabel lblError;
    private JLabel lblPerfil;
    private JLabel lblIdEntidad;
    private JLabel lblPerfilEntidad;
    private List<Usuario> listaUsuarios;


    public JPanel getPanel(){
        return panel1;
    }
    public List<Usuario> getListaUsuarios() { return listaUsuarios;}

    public acceso() {
        //agregando el titulo a la aplicacion
        super(Utilerias.tituloAplicacion);
        ocultaEtiquetasId();
        llenaPerfiles();
        muestraPerfil(false);
        muestraError(false, "");

        //agregando listeners para las acciones
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario u = new Usuario();
                u.setUsuario(txtUsuario.getText());
                u.setContrasenia(txtContrasenia.getText());
                //u.setPerfil(comboPerfiles.getSelectedItem().toString());
                //u.setStatus("activo");//por defualt solo accesan los usuarios activos
                //u.mensajeError = "";

                //se quita el caracter | ya que es el que separa la info
                u.eliminaCaracteresEspeciales();

                listaUsuarios = u.listaUsuarios();
                if(u.mensajeError != ""){
                    muestraError(true, "Ingresar. Lista Usuarios. " + u.mensajeError);
                    return;
                }

                RespuestaProceso validacion = u.validaUsuarioLista(listaUsuarios,u);
                String perfilRecuperado = u.getPerfil();
                if(validacion.getAccion() == -1){
                    //la contrasenia no coincide
                    muestraError(true, validacion.getMensaje());
                    return;
                }
                else if(validacion.getAccion() == 0){
                    //permite el registro pues el usuario no existe o no hay usuarios registrados
                    muestraError(true, validacion.getMensaje());
                    // muestra perfil
                    muestraPerfil(true);
                    return;
                }
                else {
                    //existe el usuario y sus datos de acceso son las correctas
                    muestraError(false, "");
                    String valorEntidad = Integer.toString(u.getIdEntidad());
                    //asignaEtiquetasId(valorEntidad,perfilRecuperado);
                    switch (perfilRecuperado){
                        case "Doctor":
                            muestraVentanaDoctor(Integer.valueOf(valorEntidad));
                            limpiaPantalla();
                            break;
                        case "Paciente":
                            muestraVentanaPaciente(Integer.valueOf(valorEntidad));
                            limpiaPantalla();
                            break;
                        default:
                            break;
                    }

                }

            }
        });
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        comboPerfiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (comboPerfiles.getSelectedItem().toString()){
                    case "Paciente":
                        muestraError(false, "");
                        muestraVentanaPaciente(-1);
                        limpiaPantalla();
                        break;
                    case "Doctor":
                        muestraError(false, "");
                        muestraVentanaDoctor(-1);
                        limpiaPantalla();
                        break;
                    default:
                        muestraError(true, "Seleccion default");
                        break;
                }
            }
        });
    }


    private void llenaPerfiles(){
        comboPerfiles.addItem("Selecciona opcion");
        comboPerfiles.addItem("Paciente");
        comboPerfiles.addItem("Doctor");
        comboPerfiles.addItem("Administrador");
    }

    private void ocultaEtiquetasId(){
        lblIdEntidad.setVisible(false);
        lblPerfilEntidad.setVisible(false);
    }

    private void asignaEtiquetasId(String idEntidad, String perfilEntidad){
        lblIdEntidad.setText(idEntidad);
        lblPerfilEntidad.setText(perfilEntidad);
    }

    private void muestraPerfil(boolean ver){
        comboPerfiles.setVisible(ver);
        lblPerfil.setVisible(ver);
    }

    private void muestraError(boolean ver, String mensaje){
        lblError.setVisible(ver);
        lblError.setText(mensaje);
    }

    private void limpiaPantalla(){
        txtUsuario.setText("");
        txtContrasenia.setText("");
        comboPerfiles.setSelectedIndex(0);
        muestraPerfil(false);
    }

    private void muestraVentanaDoctor(int idEntidad){
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        //llamando el login
        DoctoresVentana doctor = new DoctoresVentana();
        doctor.setContentPane(doctor.getPanel());
        doctor.setSize((width/2), (height/2));
        doctor.setLocationRelativeTo(null);
        doctor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doctor.setVisible(true);
        //pasando la info del usuario
        doctor.setUsuario(txtUsuario.getText());
        doctor.setContrasenia(txtContrasenia.getText());
        if(idEntidad>0) {
            doctor.muestraDoctor(idEntidad);
        }
    }

    private void muestraVentanaPaciente(int idEntidad){
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        //llamando el login
        PacienteVentana paciente = new PacienteVentana();
        paciente.setContentPane(paciente.getPanel());
        paciente.setSize((width/2), (height/2));
        paciente.setLocationRelativeTo(null);
        paciente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paciente.setVisible(true);
        //pasando la info del usuario
        String usu = txtUsuario.getText();
        String contra = txtContrasenia.getText();
        paciente.setUsuario(usu);
        paciente.setContrasenia(contra);
        if(idEntidad>0) {
            paciente.muestraPaciente(idEntidad);
        }
    }
}
