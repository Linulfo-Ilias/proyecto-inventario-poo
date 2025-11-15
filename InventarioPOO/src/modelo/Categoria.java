/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LINUL
 */

public class Categoria {

    // ATRIBUTOS
    private String nombre;
    private List<Producto> productos;

    // CONSTRUCTOR VACÍO (IMPORTANTE)
    public Categoria() {
        this.productos = new ArrayList<>();
    }

    // CONSTRUCTOR QUE RECIBE SOLO EL NOMBRE
    public Categoria(String nombre) {
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }



    // MÉTODOS LÓGICOS (Turno 2)

    // Agregar producto a la lista
    public void agregarProducto(Producto p) {
        if (p != null) {
            this.productos.add(p);
        }
    }

    // Buscar por código (devuelve 1 producto o null)
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    // Buscar por nombre (devuelve lista)
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "Categoria: " + nombre + " (Productos: " + productos.size() + ")";
    }
    
    // MÉTODOS GETTER Y SETTER
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    
}

