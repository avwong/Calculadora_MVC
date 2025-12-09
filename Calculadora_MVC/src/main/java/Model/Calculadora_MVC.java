/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Model;

import View.Pantalla;
import Controller.Controlador;

public class Calculadora_MVC {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Calculadora modelo = new Calculadora();
            Pantalla vista = new Pantalla();
            Controlador controller = new Controlador(modelo, vista);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
            vista.requestFocus();
        });
    }
}
