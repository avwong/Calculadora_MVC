/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Bitacora {
    
    private final String nombreArchivo;

    //CONSTRUCTOR
    public Bitacora() {
        this.nombreArchivo = "Bitacora.txt";
    }

    public void escribirLinea(String linea) {
        try {
            //agregar linea para escribir
            FileManager.writeFile(nombreArchivo, linea + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String leerTodo() {
        try {
            return FileManager.readFile(nombreArchivo);
        } catch (FileNotFoundException e) {
            //si no existe, no se retorna nada
            return "";
        } catch (IOException e) {
            return "Error leyendo " + nombreArchivo;
        }
    }

    public void borrar() {
        try {
            FileManager.clearFile(nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
