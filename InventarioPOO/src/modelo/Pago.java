/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author LINUL
 */
public class Pago extends Transaccion{
    private Proveedor proveedor;

    public Pago(Proveedor proveedor, int codigo, LocalDate fecha, float monto) {
        super(codigo, fecha, monto);
        this.proveedor = proveedor;
        setTipo("pago");
    }

    @Override
    public String toString() {
        return "Transaccion{" + "codigo=" + getCodigo() + ", fecha=" + imprimirFecha() + ", monto=" + getMonto() + ", tipo=" + getTipo() + ", proveedor=" + proveedor.getNombre() + '}';
    }

    
    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }


    
}

