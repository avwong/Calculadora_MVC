/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

public class Calculadora {
    
    private double valorActual = 0.0;
    private final StringBuilder entrada = new StringBuilder();
    private String operadorPendiente = "";
    private boolean mostrandoBinario = false;
    private boolean mostrandoPrimo = false;
    private boolean errorActivo = false;

    private final Memoria memoria;
    private final Bitacora bitacora;

    //CONSTRUCTOR
    public Calculadora() {
        this.memoria = new Memoria();
        this.bitacora = new Bitacora();
    }

    
    public String getTextoPantalla() {
        //si escribe o hay mensaje de error, se muestra lo que escribe
        if (entrada.length() > 0) {
            return entrada.toString();
        }
        //si no, se muestra el valor actual
        return doubleAString(valorActual);
    }

    //metodo para que se muestren los valores como string
    private String doubleAString(double numero) {
        //para que no se muestre con decimal .0
        if (Math.floor(numero) == numero) {
            return String.valueOf((long) numero);
        }
        return String.valueOf(numero);
    }

    //para saber si esta mostrando el binario o primo
    public boolean estaEnModoEspecial() {
        return mostrandoBinario || mostrandoPrimo;
    }

    //

    public void ingresarCaracter(char c) {
        //si hubo error, al escribir se reinicia
        if (errorActivo) limpiarTodo();
        //esperar hasta que se presione C para borrar y escribir nuevo valor
        if (estaEnModoEspecial()) return;

        //validaciones decimal
        if (c == '.') {
            if (entrada.indexOf(".") != -1) return; //no permite dos puntos
            
            if (entrada.length() == 0) {
                entrada.append("0"); //si se empieza con . decimal se pone un 0 de entero
            }
            
            entrada.append('.');
            
        //validaciones numeros
        } else if (Character.isDigit(c)) {
            
            //quita 0s incecesarios al inicio
            if (entrada.length() == 1 && entrada.charAt(0) == '0' && c != '.') {
                entrada.setLength(0);
            }
            entrada.append(c);
        }
    }

    //convierte la entrada de string a double
    private Double getEntradaComoDouble() {
        if (errorActivo) return null;
        if (entrada.length() == 0) return null;
        try {
            return Double.parseDouble(entrada.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    //Operaciones

    public void limpiarTodo() {
        entrada.setLength(0);
        valorActual = 0.0;
        operadorPendiente = "";
        mostrandoBinario = false;
        mostrandoPrimo = false;
        errorActivo = false;
    }

    public void limpiarModoEspecial() {
        limpiarTodo();
    }

    public void presionarOperador(String op) {
        if (estaEnModoEspecial() || errorActivo) return;

        Double valorEntrada = getEntradaComoDouble();

        //si esta esperando el segundo numero
        if (!operadorPendiente.isEmpty() && valorEntrada != null) {
            double antes = valorActual;
            double despues = aplicarOperacion(valorActual, valorEntrada, operadorPendiente);
            if (errorActivo) return;
            logOperacionBasica(antes, operadorPendiente, valorEntrada, despues);
            valorActual = despues;
            entrada.setLength(0);
            
        //recibe el primer numero
        } else if (valorEntrada != null && operadorPendiente.isEmpty()) {
            valorActual = valorEntrada;
            entrada.setLength(0);
        }

        operadorPendiente = op;
    }

    public void presionarIgual() {
        if (estaEnModoEspecial() || errorActivo) return;

        Double valorEntrada = getEntradaComoDouble();

        //si no hay operacion, se devuelve el mismo numero
        if (operadorPendiente.isEmpty()) {
            if (valorEntrada != null) {
                valorActual = valorEntrada;
                entrada.setLength(0);
                logIgualSolo(valorActual);
            }
        
        //hay opoeracion
        } else {
            if (valorEntrada == null) return;
            double antes = valorActual;
            double despues = aplicarOperacion(valorActual, valorEntrada, operadorPendiente);
            if (errorActivo) return;
            logOperacionBasica(antes, operadorPendiente, valorEntrada, despues);
            valorActual = despues;
            entrada.setLength(0);
            operadorPendiente = "";
        }
    }

    private double aplicarOperacion(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                //error matematico
                if (b == 0) {
                    setError("Error: Division entre 0");
                    return valorActual;
                }
                return a / b;
            default: return b;
        }
    }

    //METODOS PARA MEMORIA

    //M+
    public void memoriaAgregar() {
        if (estaEnModoEspecial() || errorActivo) return;

        Double valorEntrada = getEntradaComoDouble();
        
        //funciona aunq no haya nada de entrada en este momento, usa el uktimo valor creado
        if (valorEntrada == null) {
            valorEntrada = valorActual;
        }
        //por si sigue habiendo error, no hace nada
        if (valorEntrada == null) return;

        //agrega el numero a la memoria
        memoria.agregar(valorEntrada);
        
        //escribe en la bitacora
        logMemoriaMas(valorEntrada, memoria.obtenerValores());
    }

    public void memoriaPromedio() {
        if (estaEnModoEspecial() || errorActivo) return;
        if (memoria.estaVacia()) return; //no hay de que sacar promedio

        //obtiene valores en memoria
        ArrayList<Double> lista = memoria.obtenerValores();
        double prom = memoria.promedio();
        valorActual = prom;
        entrada.setLength(0);
        //guarda el promedio en memoria
        logMemoriaPromedio(lista, prom);
    }

    //OPERACIONES ESPECIALES

    public void convertirBinario() {
        if (estaEnModoEspecial() || errorActivo) return;

        Double valorEntrada = getEntradaComoDouble();
        
        //usar el ultimo valor
        if (valorEntrada == null) {
            valorEntrada = valorActual;
        }
        //x si hay error
        if (valorEntrada == null) return;

        //le quita los decimales
        long n = Math.round(valorEntrada);
        String bin = Long.toBinaryString(n);
        //se guarda en bitacora
        logBinario(valorEntrada, bin);

        //actualiza lo que muestra la pantalla
        entrada.setLength(0);
        entrada.append(bin);
        mostrandoBinario = true;
        mostrandoPrimo = false;
    }

    public void verificarPrimo() {
        if (estaEnModoEspecial() || errorActivo) return;

        Double valorEntrada = getEntradaComoDouble();
        
        //usar el ultimo valor
        if (valorEntrada == null) {
            valorEntrada = valorActual;
        }
        //x si hay error
        if (valorEntrada == null) return;

        //lo hace entero
        long n = Math.round(valorEntrada);
        boolean esPrimo = esPrimo(n);
        //guarda en bitacora
        logPrimo(valorEntrada, esPrimo);

        //actualiza lo que muestra en pantalla
        entrada.setLength(0);
        entrada.append(esPrimo ? "true" : "false");
        mostrandoPrimo = true;
        mostrandoBinario = false;
    }

    private boolean esPrimo(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0) return false;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    //METODOS DE BITACORA / LOG
    //guarda en la bitacora

    private void logOperacionBasica(double a, String op, double b, double r) {
        String linea = doubleAString(a) + " " + op + " " + doubleAString(b) + " = " + doubleAString(r);
        bitacora.escribirLinea(linea);
    }

    private void logIgualSolo(double v) {
        String s = doubleAString(v);
        String linea = s + " = " + s;
        bitacora.escribirLinea(linea);
    }

    //M+
    private void logMemoriaMas(double valor, ArrayList<Double> mem) {
        StringBuilder sb = new StringBuilder();
        sb.append("M+ ").append(doubleAString(valor)).append(" >");
        for (Double d : mem) {
            sb.append(" ").append(doubleAString(d));
        }
        bitacora.escribirLinea(sb.toString());
    }

    private void logMemoriaPromedio(ArrayList<Double> mem, double prom) {
        StringBuilder sb = new StringBuilder();
        sb.append("Avg");
        for (Double d : mem) {
            sb.append(" ").append(doubleAString(d));
        }
        sb.append(" = ").append(doubleAString(prom));
        bitacora.escribirLinea(sb.toString());
    }

    private void logBinario(double valor, String bin) {
        String linea = "Bin " + doubleAString(valor) + " " + bin;
        bitacora.escribirLinea(linea);
    }

    private void logPrimo(double valor, boolean primo) {
        String linea = "Primo " + doubleAString(valor) + " " + primo;
        bitacora.escribirLinea(linea);
    }

    public String leerBitacora() {
        return bitacora.leerTodo();
    }

    public void borrarBitacora() {
        bitacora.borrar();
    }

    private void setError(String mensaje) {
        entrada.setLength(0);
        entrada.append(mensaje);
        errorActivo = true;
        operadorPendiente = "";
    }
}
