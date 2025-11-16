/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author josue
 */
public class Inventario {
    private List<Categoria> categorias;
    private List<Cliente> clientes;
    private List<Proveedor> proveedores;
    private List<Transaccion> transacciones;

    public Inventario() {
        categorias=new ArrayList<>();
        clientes=new ArrayList<>();
        proveedores=new ArrayList<>();
        transacciones=new ArrayList<>();
    }
    
    
    
    

    /**
     * @return the categorias
     */
    public List<Categoria> getCategorias() {
        return categorias;
    }

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    /**
     * @return the clientes
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * @return the proveedores
     */
    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    /**
     * @param proveedores the proveedores to set
     */
    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    /**
     * @return the transacciones
     */
    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    /**
     * @param transacciones the transacciones to set
     */
    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
    
    
    
    
}
