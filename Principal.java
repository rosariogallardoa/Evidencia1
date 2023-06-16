package com.ventanas;

import com.codigo.Utilerias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {

    private JMenuBar menuBar;
    private JMenu archivo;
    private JMenuItem pacientesM;
    private JMenuItem doctoresM;
    private JMenuItem citasM;
    private JMenuItem salirM;
    private JPanel panel;

    public Principal(String perfil){
        super(Utilerias.tituloAplicacion);

        setContentPane(panel);
        menuBar = new JMenuBar();
        archivo = new JMenu("Principal");

        switch (perfil){
            case "Administrador":
                pacientesM = new JMenuItem("Pacientes");
                doctoresM = new JMenuItem("Doctores");
                citasM = new JMenuItem("Citas");
                archivo.add(pacientesM);
                archivo.add(doctoresM);
                archivo.add(citasM);
                break;
            case "Paciente","Doctor":
                citasM = new JMenuItem("Citas");
                archivo.add(citasM);
                break;

            default:
                break;

        }

        salirM = new JMenuItem("Salir");

        menuBar.add(archivo);
        archivo.add(salirM);

        setJMenuBar(menuBar);

        doctoresM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                int height = pantalla.height;
                int width = pantalla.width;

                //llamando el login
                JFrame inicio = new DoctoresVentana();
                inicio.setSize((width/2), (height/2));
                inicio.setLocationRelativeTo(null);
                inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                inicio.setVisible(true);


            }
        });
        salirM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
    }

}
