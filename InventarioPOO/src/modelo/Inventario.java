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
    private int contadorCodigosCliente;
    private int contadorCodigosTransaccion;
    private int contadorCodigosProveedor;
    private int contadorCodigosProducto;

    public Inventario() {
        categorias=new ArrayList<>();
        clientes=new ArrayList<>();
        proveedores=new ArrayList<>();
        transacciones=new ArrayList<>();
    }

    public int generarCodigoCliente(){
        return contadorCodigosCliente++;
    }
    
    public int generarCodigoTransaccion(){
        return contadorCodigosTransaccion++;
    }
    
    public int generarCodigoProveedor(){
        return contadorCodigosProveedor++;
    }
    
    public int generarCodigoProducto(){
        return contadorCodigosProducto++;
    }
    
    public void registrarProducto(String nombreCategoria, String nombre, int cantidad, float precio){
        for (Categoria categoria : categorias) {
            if(categoria.getNombre().equalsIgnoreCase(nombreCategoria)){
                Producto producto = new Producto(generarCodigoProducto(), nombre, cantidad, precio);
                categoria.getProductos().add(producto);
            }
        }
    }
    
    public void eliminarProducto(int codigo){
        for (Categoria categoria : categorias) {
            List<Producto> productos= categoria.getProductos();
            for (int i = productos.size()-1; i >= 0; i--) {
                if(productos.get(i).getCodigo()==codigo){
                    productos.remove(i);
                    return;
                }
            }
        }
    }
    
    public Producto buscarProductoPorCodigo(int codigo) {
        for (Categoria categoria : categorias) {
             for (Producto p : categoria.getProductos()) {
                 if (p.getCodigo() == codigo) {
                     return p;
                 }
             }
        }
        return null;
    }
    
    public List<Producto> buscarProductoPorNombre(String nombre){
        List<Producto> lista = new ArrayList<>();
        for (Categoria categoria : categorias) {
            for (Producto p : categoria.getProductos()) {
                if(p.getNombre().equalsIgnoreCase(nombre)){
                    lista.add(p);
                }
            }
        }
        return lista;
    }
    
    public String listarProductos() {
        String lista = "";
        for (Categoria categoria : categorias) {
            lista += categoria.toString()+"\n";
            for (Producto p : categoria.getProductos()) {
                lista += "\t"+p.toString()+"\n";
            }
        }
        return lista;
    }
    
    public String listarProductosPorCategoria(String nombreCategoria){
        String lista = "";
        for (Categoria categoria : categorias) {
            if(categoria.getNombre().equalsIgnoreCase(nombreCategoria)){
                lista += categoria.toString()+"\n";
                for (Producto p : categoria.getProductos()){
                    lista += "\t"+p.toString()+"\n";
                }
                return lista;
            }
            
        }
        return "no se encontro la categoria";
    }
    
    public String listarBajaCantidad(int cantidad){
        String lista = "";
        for (Categoria categoria : categorias) {
            for (Producto p : categoria.getProductos()) {
                if(p.getCantidad()<=cantidad){
                    lista += p.toString()+"\n";
                }
            }
        }
        return lista;
    }
    
    public String mostrarMasCaroYMasBarato() {
        Producto masCaro = null;
        Producto masBarato = null;
        String lista = "";
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
        if (masCaro == null){
            lista = "No hay productos";
        } else {
            lista += "Mas caro:\n"+
                    masCaro.toString()+
                    "\nMas Barato:\n"+
                    masBarato.toString();
        }
        return lista;
    }
    
    public void clonarProducto(int codigo) {
        for (Categoria categoria : categorias) {
            for(Producto p : categoria.getProductos()){
                if(p.getCodigo()==codigo){
                    Producto copia = p.clone();
                    if (copia != null) {
                        categoria.getProductos().add(copia);
                    } 
                    return;
                }
            }
        }

    }
    
    public float calcularValorTotalInventario(){
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
                        return;
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
                    return;
                }
            }
        }
    }  
    
 
    public void registrarCategoria(String nombre){
        Categoria categoria = new Categoria(nombre);
        categorias.add(categoria);
    }
    
    public void editarNombreCategoria(String nombreAntes, String nombreDespues){
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombreAntes)){
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
    
    public void registrarProveedor(String nombre){
        Proveedor proveedor = new Proveedor(generarCodigoProveedor(), nombre);
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

    public void registrarCliente(String nombre){
        Cliente cliente = new Cliente(generarCodigoCliente(), nombre);
        clientes.add(cliente);
    }
    
    public void eliminarCliente(int codigo){
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
            if (cliente.getNombre().equalsIgnoreCase(nombre)){
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
            if (transaccion.getTipo().equalsIgnoreCase("deuda")){
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
            if (transaccion.getTipo().equalsIgnoreCase("deuda")){
                Deuda deuda = (Deuda) transaccion;
                if (deuda.getCliente().getCodigo() == codigo){
                    lista += deuda.toString()+"\n";
                }
            }else if(transaccion.getTipo().equalsIgnoreCase("venta")){
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
    
    public void registrarDeuda(int codigoCliente, int codigoProducto, int cantidad){
        Cliente clienteARegistrar = null;
        Producto productoARegistrar = null;
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == codigoCliente){
                clienteARegistrar = cliente;
                break;
            }
        }
        for (Categoria categoria : categorias) {
            for (Producto producto : categoria.getProductos()){
                if (producto.getCodigo() == codigoProducto){
                    productoARegistrar = producto;
                    break;
                }
            }
            if (productoARegistrar != null) break;
        }
        Deuda deuda = new Deuda(clienteARegistrar,
                productoARegistrar, 
                cantidad, 
                (generarCodigoTransaccion()), 
                LocalDate.now(), 
                (cantidad*productoARegistrar.getPrecio()));
        
        transacciones.add(deuda);
    }
    
    public void registrarVenta(int codigoCliente, int codigoProducto, int cantidad){
        Cliente clienteARegistrar = null;
        Producto productoARegistrar = null;
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == codigoCliente){
                clienteARegistrar = cliente;
                break;
            }
        }
        for (Categoria categoria : categorias) {
            for (Producto producto : categoria.getProductos()){
                if (producto.getCodigo() == codigoProducto){
                    productoARegistrar = producto;
                    break;
                }
            }
            if (productoARegistrar != null) break;
        }
        Venta venta = new Venta(
                productoARegistrar,
                clienteARegistrar, 
                cantidad, 
                (generarCodigoTransaccion()), 
                LocalDate.now(), 
                (cantidad*productoARegistrar.getPrecio()));
        
        transacciones.add(venta);
    }
    
    public void registrarPago(int codigoProveedor, float monto){
        Proveedor proveedorARegistrar = null;
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getCodigo() == codigoProveedor){
                proveedorARegistrar = proveedor;
                break;
            }  
        }
        Pago pago = new Pago(proveedorARegistrar,
                (generarCodigoTransaccion()),
                LocalDate.now(), 
                monto);
        
        transacciones.add(pago);
    }
    
    public List<Transaccion> buscarTransaccionesPorFecha(int dia, int mes, int anio) {
        List<Transaccion> resultado = new ArrayList<>();
        LocalDate fechaBuscada = LocalDate.of(anio, mes, dia);

        for (Transaccion transaccion : transacciones) {
            if (transaccion.getFecha().equals(fechaBuscada)) {
                resultado.add(transaccion);
            }
        }

        return resultado;
    }
    
    public List<Transaccion> buscarTransaccionesPorTipo(String tipo){
        List<Transaccion> resultado = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equalsIgnoreCase(tipo)){
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
            if (transaccion.getTipo().equalsIgnoreCase("deuda") || transaccion.getTipo().equalsIgnoreCase("venta")){
                ingresos += transaccion.getMonto();
            }
        }
        return ingresos;
    }
    
    public float calcularEgresos(){
        float egresos = 0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equalsIgnoreCase("pago")){
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

    /**
     * @return the contadorCodigosCliente
     */
    public int getContadorCodigosCliente() {
        return contadorCodigosCliente;
    }

    /**
     * @param contadorCodigosCliente the contadorCodigosCliente to set
     */
    public void setContadorCodigosCliente(int contadorCodigosCliente) {
        this.contadorCodigosCliente = contadorCodigosCliente;
    }

    /**
     * @return the contadorCodigosTransaccion
     */
    public int getContadorCodigosTransaccion() {
        return contadorCodigosTransaccion;
    }

    /**
     * @param contadorCodigosTransaccion the contadorCodigosTransaccion to set
     */
    public void setContadorCodigosTransaccion(int contadorCodigosTransaccion) {
        this.contadorCodigosTransaccion = contadorCodigosTransaccion;
    }

    /**
     * @return the contadorCodigosProveedor
     */
    public int getContadorCodigosProveedor() {
        return contadorCodigosProveedor;
    }

    /**
     * @param contadorCodigosProveedor the contadorCodigosProveedor to set
     */
    public void setContadorCodigosProveedor(int contadorCodigosProveedor) {
        this.contadorCodigosProveedor = contadorCodigosProveedor;
    }

    /**
     * @return the contadorCodigosProducto
     */
    public int getContadorCodigosProducto() {
        return contadorCodigosProducto;
    }

    /**
     * @param contadorCodigosProducto the contadorCodigosProducto to set
     */
    public void setContadorCodigosProducto(int contadorCodigosProducto) {
        this.contadorCodigosProducto = contadorCodigosProducto;
    }
    
    
    
    
}
