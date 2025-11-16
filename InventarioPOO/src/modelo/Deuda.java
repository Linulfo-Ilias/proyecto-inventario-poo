/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author LINUL
 */
public class Deuda extends Transaccion{
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private boolean pagado;

    public Deuda(Cliente cliente, Producto producto, int cantidad, boolean pagado, int codigo, Date fecha, float monto) {
        super(codigo, fecha, monto);
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.pagado = pagado;
        setTipo("deuda");
    }
    
    private String mostrarPagado(){
        return pagado ? "si": "no";
    }

    @Override
    public String toString() {
        return "Transaccion{" + "codigo=" + getCodigo() + ", fecha=" + getFecha() + ", monto=" + getMonto() + ", tipo=" + getTipo() + ", Cliente=" + cliente.getNombre() + ", producto=" + producto.getNombre() + ", cantidad=" + cantidad + ", pagado"+ mostrarPagado() + '}';
    }
    
    public void marcarComoPagado(){
        setPagado(true);
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

    /**
     * @return the pagado
     */
    public boolean isPagado() {
        return pagado;
    }

    /**
     * @param pagado the pagado to set
     */
    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
    
}
