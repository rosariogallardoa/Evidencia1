import com.ventanas.acceso;

import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //calculando el tamaño de la pantalla para que se muestre al centro
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                int height = pantalla.height;
                int width = pantalla.width;

                //llamando el login
                JFrame inicio = new acceso();
                inicio.setContentPane(((acceso) inicio).getPanel());
                inicio.setSize((width/2)-100, (height/2)-100);
                inicio.setLocationRelativeTo(null);
                //inicio.setDefaultCloseOperation(0);
                inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                inicio.setVisible(true);
            }
        });
    }
}