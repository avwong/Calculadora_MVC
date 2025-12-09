/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

public class Memoria {
    
    //la lista de los numeros guardados en memoria tiene un maximo de 10
    private static final int TAMANO = 10;
    //lista donde se guardan los valores
    private final ArrayList<Double> numeros = new ArrayList<>();

    //agrega numero a la lista
    public void agregar(double numero) {
        //si no hay mas campo, elimina el primero
        if (numeros.size() == TAMANO) {
            numeros.removeFirst();
        }
        numeros.addLast(numero);
    }

    public boolean estaVacia() {
        return numeros.isEmpty();
    }

    //retorna la lista con los numeros
    public ArrayList<Double> obtenerValores() {
        return new ArrayList<>(numeros);
    }

    public double promedio() {
        if (numeros.isEmpty()) return 0.0;
        
        double suma = 0;
        
        for (Double numero : numeros) {
            suma += numero;
        }
        return suma / numeros.size();
    }
}
