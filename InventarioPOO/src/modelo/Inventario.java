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
    
    public void registrarProducto(Categoria c, Producto p){
        for (Categoria categoria : categorias) {
            if(c==categoria){
                categoria.getProductos().add(p);
                System.out.println("Producto registrado...");
            }
        }
    }
    
    public void eliminarProducto(int codigo){
        for (Categoria categoria : categorias) {
            List<Producto> productos= categoria.getProductos();
            for (int i = 0; i < productos.size(); i++) {
                if(productos.get(i).getCodigo()==codigo){
                    productos.remove(i);
                    System.out.println("Producto eliminado...");
                    return;
                }
            }
        }
        System.out.println("No se encontro un producto con ese codigo...");
    }
    
    public Producto buscarPorCodigo(int codigo) {
        for (Categoria categoria : categorias) {
             for (Producto p : categoria.getProductos()) {
                 if (p.getCodigo() == codigo) {
                     return p;  // Lo encontró
                 }
             }
        }
        return null;
    }
    
    public Producto buscarPorNombre(String nombre){
        for (Categoria categoria : categorias) {
            for (Producto p : categoria.getProductos()) {
                if(p.getNombre().equals(nombre)){
                    return p;
                }
            }
        }
        return null;
    }
    
    public void listarProductos() {
        for (Categoria categoria : categorias) {
            System.out.println("Categoría: " + categoria.getNombre());
            for (Producto p : categoria.getProductos()) {
                System.out.println("  - " + p);
            }
            System.out.println();
        }
    }
    
    public void listarPorCategoria(String namecategoria){
        for (Categoria categoria : categorias) {
            if(categoria.getNombre().equals(namecategoria)){
                System.out.println("Categoria: "+namecategoria);
                for (Producto p : categoria.getProductos()){
                    System.out.println(" - "+p);
                }
                return;
            }
            
        }
        System.out.println("No existe la categoria: "+namecategoria);
    }
    
    public void listarBajaCantidad(int cantidad){
        System.out.println("Productos con cantidad menor o igual a "+cantidad+": ");
        for (Categoria categoria : categorias) {
            for (Producto p : categoria.getProductos()) {
                if(p.getCantidad()<=cantidad){
                    System.out.println("Categoria: "+categoria.getNombre()+" | Producto: "+p.getNombre()+" | Cantidad: "+p.getCantidad());
                }
            }
        }
    }
    
    public void mostrarMasCaroYMasBarato() {
        Producto masCaro = null;
        Producto masBarato = null;
        for (Categoria categoria : categorias) {
            for (Producto p : categoria.getProductos()) {
                if (masCaro == null || p.getPrecio() > masCaro.getPrecio()) {
                    masCaro = p;
                }
                if (masBarato == null || p.getPrecio() < masBarato.getPrecio()) {
                    masBarato = p;
                }
            }
        }
        if (masCaro != null) {
            System.out.println("Producto más caro: "+masCaro.getNombre()+" | Precio: "+masCaro.getPrecio());
        }
        if (masBarato != null) {
            System.out.println("Producto más barato: "+masBarato.getNombre()+" | Precio: "+masBarato.getPrecio());
        }
    }
    
    public void clonarProducto(int codigo) {
        for (Categoria categoria : categorias) {
            for(Producto p : categoria.getProductos()){
                if(p.getCodigo()==codigo){
                    Producto copia = p.clone();
                    if (copia != null) {
                        categoria.getProductos().add(copia);
                        System.out.println("Producto clonado...");
                    } else {
                        System.out.println("No se pudo clonar el producto...");
                    }
                    return;
                }
            }
        }
        System.out.println("No se encontro un producto con ese codigo...");
    }
    
    public float calcValorTotalInventario(){
        float total=0;
        for (Categoria categoria : categorias) {
            for(Producto p : categoria.getProductos()){
                float valorproducto=p.getPrecio()*p.getCantidad();
                total+=valorproducto;
            }
        }
        return total;
    }
    
    public void venderProducto(int codigo, int cantidad){
        for (Categoria categoria : categorias) {
            for(Producto p : categoria.getProductos()){
                if(p.getCodigo()==codigo){
                    if(p.getCantidad()>=cantidad){
                        p.restar(cantidad);
                    }
                }
            }
        }
    }
    
    public void comprarProducto(int codigo, int cantidad){
        for (Categoria categoria : categorias) {
            for(Producto p : categoria.getProductos()){
                if(p.getCodigo()==codigo){
                    p.sumar(cantidad);
                }
            }
        }
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
