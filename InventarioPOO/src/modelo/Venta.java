/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author LINUL
 */
public class Venta extends Transaccion{
    private Producto producto;
    private Cliente cliente;
    private int cantidad;

    public Venta(Producto producto, Cliente cliente, int cantidad, int codigo, Date fecha, float monto) {
        super(codigo, fecha, monto);
        this.producto = producto;
        this.cliente = cliente;
        this.cantidad = cantidad;
        setTipo("venta");
    }

    @Override
    public String toString() {
        return "Transaccion{" + "codigo=" + getCodigo() + ", fecha=" + imprimirFecha(getFecha()) + ", monto=" + getMonto() + ", tipo=" + getTipo() + ", Cliente=" + cliente.getNombre() + ", producto=" + producto.getNombre() + ", cantidad=" + cantidad + '}';
    }
    
    
    
    

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    
}
