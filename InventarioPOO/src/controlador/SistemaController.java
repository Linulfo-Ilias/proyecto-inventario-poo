/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.time.LocalDate;
import modelo.*;
import persistencia.*;
import vista.dialogos.DialogoError;
import vista.ventanas.principales.VentanaPrincipal;

/**
 *
 * @author Angie
 */
public class SistemaController {
    private Inventario inventario;
    private GestorPersistencia gestor;
    private Reporte reporte;
    
    private VentanaPrincipal ventanaPrincipal;
    
    public SistemaController(){
        try {
            gestor = new GestorPersistencia();
            reporte = new Reporte();
            inventario = gestor.cargarInventario("data/inventario.json");
        
            VentanaPrincipal = new VentanaPrincipal(this);
            ventanaPrincipal.setVisible(true)
        } catch (Exception e) {
            new DialogoError(VentanaPrincipal, true, "ERROR: no se pudo cargar el inventario", e).setVisible(true);
        }
    }
    
    public void registrarCliente(String nombre){
        getInventario().registrarCliente(nombre);
    }
    
    public void registrarVenta(Producto producto, Cliente cliente, int cantidad, int codigo, float monto){
        Venta venta = new Venta(producto, cliente, cantidad, codigo, LocalDate.now(), monto);
        getInventario().registrarTransaccion(venta);
    }
    
    public void guardarDatos(){
        try {
            gestor.guardarInventario(inventario, "data/inventario.json");
        } catch (Exception e) {
            new DialogoError(this).setVisible(true);
        }
        
    }
    
    public void generarReporteVentas(){
        reporte.generarReporteVentas(inventario.getTransacciones());
    }
    
    /**
     * @return the inventario
     */
    public Inventario getInventario() {
        return inventario;
    }
    
    
}
