/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Joan
 */
public abstract class Persona {
    
    protected int codigo;
    protected String nombre;

    public Persona(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Métodos básicos
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método abstracto opcional
    public abstract void mostrarInfo();
}
