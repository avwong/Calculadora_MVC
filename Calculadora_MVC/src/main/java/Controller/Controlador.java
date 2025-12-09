/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Calculadora;
import View.Pantalla;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Controlador implements ActionListener{
    
    private final Calculadora modelo;
    private final Pantalla vista;
    private boolean bitacoraLimpiada = false;
    
    //CONSTRUCTOR

    public Controlador(Calculadora modelo, Pantalla vista) {
        this.modelo = modelo;
        this.vista = vista;
        suscripciones();
        configurarKeyListener();
        configurarCierre();
        actualizarDisplay();
        vista.requestFocusInWindow();
    }
    
    public void suscripciones(){
        vista.btnAdd.addActionListener(this);
        vista.btnSub.addActionListener(this);
        vista.btnMul.addActionListener(this);
        vista.btnDiv.addActionListener(this);
        vista.btnIgual.addActionListener(this);
        vista.btnC.addActionListener(this);
        vista.btnDot.addActionListener(this);
        vista.btnBin.addActionListener(this);
        vista.btnPrimo.addActionListener(this);
        vista.btnAvg.addActionListener(this);
        vista.btnMPlus.addActionListener(this);
        vista.btnData.addActionListener(this);
        vista.btn0.addActionListener(this);
        vista.btn1.addActionListener(this);
        vista.btn2.addActionListener(this);
        vista.btn3.addActionListener(this);
        vista.btn4.addActionListener(this);
        vista.btn5.addActionListener(this);
        vista.btn6.addActionListener(this);
        vista.btn7.addActionListener(this);
        vista.btn8.addActionListener(this);
        vista.btn9.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.btn0) { 
            manejarCaracter('0'); 
            return; 
        }
        if (src == vista.btn1) { 
            manejarCaracter('1'); 
            return; 
        }
        if (src == vista.btn2) { 
            manejarCaracter('2'); 
            return; 
        }
        if (src == vista.btn3) { 
            manejarCaracter('3'); 
            return; }
        if (src == vista.btn4) { 
            manejarCaracter('4'); 
            return; }
        if (src == vista.btn5) { 
            manejarCaracter('5'); 
            return; }
        if (src == vista.btn6) { 
            manejarCaracter('6'); 
            return; 
        }
        if (src == vista.btn7) { 
            manejarCaracter('7'); 
            return; 
        }
        if (src == vista.btn8) { 
            manejarCaracter('8'); 
            return; 
        }
        if (src == vista.btn9) { 
            manejarCaracter('9'); 
            return; 
        }
        
        if (src == vista.btnDot) { 
            manejarCaracter('.'); 
            return; 
        }
        
        if (src == vista.btnAdd) { 
            manejarOperador("+"); 
            return; 
        }
        if (src == vista.btnSub) { 
            manejarOperador("-"); 
            return; 
        }
        if (src == vista.btnMul) { 
            manejarOperador("*"); 
            return; 
        }
        if (src == vista.btnDiv) { 
            manejarOperador("/"); 
            return; 
        }

        if (src == vista.btnIgual) { 
            manejarIgual(); 
            return; 
        }
        if (src == vista.btnC) { 
            limpiar(); 
            return; 
        }

        if (src == vista.btnBin) { 
            modelo.convertirBinario(); 
            actualizarDisplay(); 
            return; 
        }
        if (src == vista.btnPrimo) { 
            modelo.verificarPrimo(); 
            actualizarDisplay(); 
            return; 
        }
        if (src == vista.btnAvg) { 
            modelo.memoriaPromedio(); 
            actualizarDisplay(); 
            return; 
        }
        if (src == vista.btnMPlus) { 
            modelo.memoriaAgregar(); 
            actualizarDisplay(); 
            return; 
        }
        if (src == vista.btnData) { 
            mostrarBitacora(); 
        }
    }

    private void manejarCaracter(char c) {
        modelo.ingresarCaracter(c);
        actualizarDisplay();
    }

    private void manejarOperador(String op) {
        modelo.presionarOperador(op);
        actualizarDisplay();
    }

    private void manejarIgual() {
        modelo.presionarIgual();
        actualizarDisplay();
    }

    private void limpiar() {
        modelo.limpiarTodo();
        actualizarDisplay();
    }

    private void actualizarDisplay() {
        vista.txfDisplay.setText(modelo.getTextoPantalla());
    }

    private void mostrarBitacora() {
        String contenido = modelo.leerBitacora();
        if (contenido == null || contenido.isEmpty()) {
            contenido = "Bitacora vacia.";
        }

        JTextArea area = new JTextArea(contenido, 20, 40);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(area);
        JOptionPane.showMessageDialog(vista, scroll, "Bitacora", JOptionPane.INFORMATION_MESSAGE);
    }

    private void configurarKeyListener() {
        KeyEventDispatcher despachador = e -> {
            if (e.getID() == KeyEvent.KEY_TYPED) {
                procesarTecla(e.getKeyChar());
            } else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                manejarIgual();
            }
            return false; //no consumir
        };

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(despachador);
    }

    private void configurarCierre() {
        vista.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                limpiarBitacora();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                limpiarBitacora();
            }
        });
    }

    private void limpiarBitacora() {
        if (bitacoraLimpiada) return;
        modelo.borrarBitacora();
        bitacoraLimpiada = true;
    }

    private void procesarTecla(char c) {
        switch (c) {
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                manejarCaracter(c);
                break;
            case '.':
                manejarCaracter('.');
                break;
            case '+': case '-': case '*': case '/':
                manejarOperador(String.valueOf(c));
                break;
            case '=':
                manejarIgual();
                break;
            case 'c': case 'C':
                limpiar();
                break;
            default:
                break;
        }
    }

}
