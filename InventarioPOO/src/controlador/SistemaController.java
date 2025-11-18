/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.*;
import persistencia.*;
import vista.dialogos.DialogoError;
import vista.ventanas.principales.PanelDeControl;
import vista.ventanas.principales.VentanaLogin;

/**
 *
 * @author Angie
 */
public class SistemaController {
    private Inventario inventario;
    private GestorPersistencia gestor;
    private Reporte reporte;
    private BackupManager manager;
    
    public SistemaController() {
        try {
            gestor = new GestorPersistencia();
            reporte = new Reporte();
            //inventario = gestor.cargarInventario("data/inventario.json");
            manager = new BackupManager();
            
            
            
            new VentanaLogin(this, getProductos()).setVisible(true);
        } catch (Exception e) {
            new DialogoError(null, true, "ERROR: no se pudo ejecutar el programa.").setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        
        SistemaController sistema = new SistemaController();
        //new VentanaLogin(sistema, inventario).setVisible(true);
    }
    
    public boolean verificarCredenciales(String usuario, String contraseña){
        if (usuario.isEmpty() || contraseña.isEmpty()) return false;
        return true;
    }
    
    public Cliente Logear(String usuario, String contraseña) {
        if (!verificarCredenciales(usuario, contraseña)) return null;

        for (Cliente cliente : inventario.getClientes()) {
            if(usuario.equals(cliente.getNombre()) && contraseña.equals(cliente.getContraseña())) {
                return cliente;
            }
        }
        return null;
    }
    
    public boolean iniciarAdmin(String usuario, String contraseña){
        if (usuario.equals("XxPersonaxX_YT") && contraseña.equals("18112025")){
            new PanelDeControl(this).setVisible(true);
            return true;}
        return false;
    }
    
    public List<Producto> getProductos() {
        return inventario.getProductos();
    }
    public List<Cliente> getClientes() {
        return inventario.getClientes();
    }
    public List<Venta> getVentas(){
        List<Venta> ventas = new ArrayList<>();
        for (Transaccion transaccion : inventario.getTransacciones()) {
            if (transaccion instanceof Venta){
                Venta venta = (Venta) transaccion;
                ventas.add(venta);
            }
        }
        return ventas;
    }
    
    public int getComprasTotales(Cliente c){
        int contador = 0;
        for (Transaccion transaccion : inventario.getTransacciones()) {
            if (transaccion instanceof Venta){
                Venta venta = (Venta) transaccion;
                if (c.equals(venta.getCliente())){
                    contador++;
                }
            }
        }
        return contador;
    }
    
    public void eliminarProducto(int codigo) {
        inventario.eliminarProducto(codigo);
    }
    
    public void eliminarCliente(int codigo){
        inventario.eliminarCliente(codigo);
    }
    
    public void editarProducto(Producto p, String nuevoNombre, float precio, int cantidad) {
        p.setNombre(nuevoNombre);
        p.setPrecio(precio);
        p.setCantidad(cantidad);
    }
    
    public String agregarProducto(String nombreCategoria,String nombre, int cantidad, int precio) {
        return inventario.registrarProducto(nombreCategoria, nombre, cantidad, precio);
    }
    
    public void agregarCliente(String nombre,String contraseña) {
        inventario.registrarCliente(nombre, contraseña);
    }
    
    public Cliente buscarCliente(String nombre){
        return inventario.buscarClientePorNombre(nombre).get(0);
    }
    
    public void guardarCambios() {
        try {
            gestor.guardarInventario(inventario, "data/inventario.json");
        } catch (Exception e) {
            new DialogoError(null, true, "ERROR guardando inventario").setVisible(true);
        }
    }
    
    public void registrarUsuario(String nombre, String contraseña){
        inventario.registrarCliente(nombre, contraseña);
    }
    
    public void generarReporteVentas(){
        reporte.generarReporteVentas(inventario.getTransacciones());
    }
    
    public List<BackupManager.Backup> listarBackups(){
        return manager.listarBackups();
    }
    
    public void crearBackup(){
        manager.crearBackup(inventario);
    }
    
    public void eliminarBackup(String nombreArchivo){
        manager.eliminarBackup(nombreArchivo);
    }
    
    public Inventario restaurarBackup(String nombreArchivo){
        this.inventario = manager.restaurarBackup(nombreArchivo);
    }
    
    public void comprarProducto(int codCliente, int codProducto, int cantidad){
        inventario.registrarVenta(codCliente, codProducto, cantidad);
    }
    
    /**
     * @return the inventario
     */
    public Inventario getInventario() {
        return inventario;
    }
    
    
}
