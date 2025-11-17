/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    
    public void registrarCategoria(Categoria c){
        categorias.add(c);
    }
    
    public void editarNombreCategoria(String nombreAntes, String nombreDespues){
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equals(nombreAntes)){
                categoria.setNombre(nombreDespues);
                return;
            }
        }
    }

    public void eliminarCategoriasSinUso() {
        for (int i = categorias.size() - 1; i >= 0; i--) {
            if (categorias.get(i).getProductos().isEmpty()) {
                categorias.remove(i);
            }
        }
    }
    
    public String listarCategorias(){
        String lista = "";
        for (Categoria categoria : categorias) {
            lista += categoria.toString()+"\n";
        }
        return lista;
    }
    
    public void registrarProveedor(Proveedor proveedor){
        proveedores.add(proveedor);
    }
    
    public void eliminarProveedor(int codigo){
        for (int i = proveedores.size() - 1; i >= 0; i--) {
            if (proveedores.get(i).getCodigo() == codigo){
                proveedores.remove(i);
                return;
            }
        }
    }
    
    public String listarProveedores(){
        String lista = "";
        for (Proveedor proveedor : proveedores) {
            lista += proveedor.toString()+"\n";
        }
        return lista;
    }

    public void registrarCliente(Cliente cliente){
        clientes.add(cliente);
    }
    
    public void EliminarCliente(int codigo){
        for (int i = clientes.size() - 1; i >= 0; i--) {
            if (clientes.get(i).getCodigo() == codigo){
                clientes.remove(i);
                return;
            }
        }
    }
    
    public List<Cliente> buscarClientePorNombre(String nombre){
        List<Cliente> lista = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equals(nombre)){
                lista.add(cliente);
            }
        }
        return lista;
    }
    
    public Cliente buscarClientePorCodigo(int codigo){
        for (Cliente cliente : clientes) {
            if(cliente.getCodigo() == codigo){
                return cliente;
            }
        }
        return null;
    }
    
    public String listarClientes(){
        String lista = "";
        for (Cliente cliente : clientes) {
            lista+=cliente.toString()+"\n";
        }
        return lista;
    }
    
    public String listarClientesConDeudas(){
        String listaString = "";
        List<Cliente> listaList = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equals("deuda")){
                Deuda deuda = (Deuda) transaccion;
                if (!deuda.isPagado()){
                    if (!listaList.contains(deuda.getCliente())){
                        listaList.add(deuda.getCliente());
                    }
                }
            }
        }
        for (Cliente cliente : listaList) {
            listaString += cliente.toString()+"\n";
        }
        return listaString;
    }
    
    public String listarTransaccionesCliente(int codigo){
        String lista = "";
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equals("deuda")){
                Deuda deuda = (Deuda) transaccion;
                if (deuda.getCliente().getCodigo() == codigo){
                    lista += deuda.toString()+"\n";
                }
            }else if(transaccion.getTipo().equals("venta")){
                Venta venta = (Venta) transaccion;
                if (venta.getCliente().getCodigo() == codigo){
                    lista += venta.toString()+"\n";
                }
            }
        }
        return lista;
    }
    
    public void registrarTransaccion(Transaccion transaccion){
        transacciones.add(transaccion);
    }
    
    public List<Transaccion> buscarTransaccionesPorFecha(int dia, int mes, int anio) {
        List<Transaccion> resultado = new ArrayList<>();
        LocalDate fechaBuscada = LocalDate.of(anio, mes, dia);

        for (Transaccion transaccion : transacciones) {
            LocalDate fechaTransaccion = transaccion.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (fechaTransaccion.equals(fechaBuscada)) {
                resultado.add(transaccion);
            }
        }

        return resultado;
    }
    
    public List<Transaccion> buscarTransaccionesPorTipo(String tipo){
        List<Transaccion> resultado = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equals(tipo)){
                resultado.add(transaccion);
            }
        }
        return resultado;
    }
    
    public String listarTransacciones(){
        String lista = "";
        for (Transaccion transaccion : transacciones) {
            lista += transaccion.toString()+"\n";
        }
        return lista;
    }

    public float calcularIngresos(){
        float ingresos = 0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equals("deuda") || transaccion.getTipo().equals("venta")){
                ingresos += transaccion.getMonto();
            }
        }
        return ingresos;
    }
    
    public float calcularEgresos(){
        float egresos = 0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equals("pago")){
                egresos += transaccion.getMonto();
            }
        }
        return egresos;
    }
    
    public void marcarDeudaComoPagada(int codigo){
        for (Transaccion transaccion : transacciones) {
            if(transaccion.getCodigo() == codigo){
                if(transaccion instanceof Deuda deuda){
                    deuda.marcarComoPagado();
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
