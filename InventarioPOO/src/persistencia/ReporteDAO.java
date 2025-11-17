/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Angie
 */
public class ReporteDAO {
    public void guardarReporte(String nombreArchivo, String contenido) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

