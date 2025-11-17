/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import modelo.Inventario;

/**
 *
 * @author Angie
 */
public class GestorPersistencia {
    
    private final Gson gson;
    
    public GestorPersistencia(){
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public void guardarInventario(Inventario inventario, String ruta) throws  Exception{
        try (FileWriter writer = new FileWriter(ruta)){
            gson.toJson(inventario, writer);
        }
    }
    
    public Inventario cargarInventario(String ruta) throws Exception{
        try (FileReader reader = new FileReader(ruta)){
            return gson.fromJson(reader, Inventario.class);
        }
    }
}
