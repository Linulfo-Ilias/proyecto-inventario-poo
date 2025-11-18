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
    private int contadorCodigosCliente = 1;
    private int contadorCodigosTransaccion = 1;
    private int contadorCodigosProveedor = 1;
    private int contadorCodigosProducto = 1;

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
    
    public String registrarProducto(String nombreCategoria, String nombre, int cantidad, float precio){
        if (nombreCategoria == null || nombreCategoria.isBlank()) {
            return "Error: el nombre de la categoría no puede estar vacío.";
        }
        if (nombre == null || nombre.isBlank()) {
            return "Error: el nombre del producto no puede estar vacío.";
        }
        if (cantidad < 0) {
            return "Error: la cantidad del producto no puede ser negativa.";
        }
        if (precio < 0) {
            return "Error: el precio del producto no puede ser negativo.";
        }
        for (Categoria categoria : categorias) {
            if(categoria.getNombre().equalsIgnoreCase(nombreCategoria)){
                Producto producto = new Producto(generarCodigoProducto(), nombre, cantidad, precio);
                categoria.agregarProducto(producto);
                return "Producto Agregado Correctamente.";
            }
        }
        return "Error: no se encontro la categoria.";
    }
    
    public String eliminarProducto(int codigo){
        for (Categoria categoria : categorias) {
            List<Producto> productos= categoria.getProductos();
            for (int i = productos.size()-1; i >= 0; i--) {
                if(productos.get(i).getCodigo()==codigo){
                    productos.remove(i);
                    return "Producto Eliminado Correctamente.";
                }
            }
        }
        return "Error: el producto no ha sido encontrado";
    }
    
    //Importante: usar el null para imprimir un error desde fuera
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
    
    //Importante: usar el null para imprimir un error desde fuera
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
        return "".equals(lista) ? "No Hay Productos Registrados." : lista;
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
        return "".equals(lista) ? "No hay productos con cantidad menor o igual a "+cantidad : lista;
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
    
    public String clonarProducto(int codigo) {
        for (Categoria categoria : categorias) {
            for(Producto p : categoria.getProductos()){
                if(p.getCodigo()==codigo){
                    Producto copia = p.clone();
                    if (copia == null){
                         return "Error: no se pudo clonar el producto.";                       
                    }
                    copia.setCodigo(generarCodigoProducto());
                    if (copia != null) {
                        categoria.agregarProducto(copia);
                    } 
                    return "Producto copiado correctamente";
                }
            }
        }
        return "Producto no encontrado";

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
    
    
    public String actualizarStockProducto(int codigo, int cantidad){
        Producto p = buscarProductoPorCodigo(codigo);
        if (p == null){
            return "Error: producto no encontrado.";
        }
        if (cantidad < 0){
            return "Error: cantidad invalida.";
        }
        p.setCantidad(cantidad);
        return "Cantidad Actualizada Correctamente.";
    }  
    
 
    public String registrarCategoria(String nombre){
        if (nombre == null || nombre.isBlank() ){
            return "Error: el nombre de la categoria no puede estar vacio.";
        }
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombre)){
                return "Error: la categoria ya existe.";
            }
        }
        Categoria categoria = new Categoria(nombre);
        categorias.add(categoria);
        return "Categoria Añadida Exitosamente.";
        
    }
    
    public String editarNombreCategoria(String nombreAntes, String nombreDespues){
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombreAntes)){
                categoria.setNombre(nombreDespues);
                return "Nombre de la Categoria correctamente Actualizado.";
            }
        }
        return "Error: no se encontro categoria";
    }

    public String eliminarCategoriasSinUso() {
        for (int i = categorias.size() - 1; i >= 0; i--) {
            if (categorias.get(i).getProductos().isEmpty()) {
                categorias.remove(i);
            }
        }
        return "Categorias Sin Uso Eliminadas Exitosamente.";
    }
    
    public String listarCategorias(){
        String lista = "";
        for (Categoria categoria : categorias) {
            lista += categoria.toString()+"\n";
        }
        return "".equals(lista) ? "Error: no hay categorias registradas." : lista;
    }
    
    public String registrarProveedor(String nombre){
        if (nombre == null || nombre.isBlank()){
            return "Error: el nombre del proveedor no puede estar vacio";
        }
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getNombre().equalsIgnoreCase(nombre)){
                return "Error: el proveedor ya existe.";
            }
        }
        Proveedor proveedor = new Proveedor(generarCodigoProveedor(), nombre);
        proveedores.add(proveedor);
        return "Proveedor Registrado Con Exito.";
    }
    
    public String eliminarProveedor(int codigo){
        for (int i = proveedores.size() - 1; i >= 0; i--) {
            if (proveedores.get(i).getCodigo() == codigo){
                proveedores.remove(i);
                return "Proveedor Eliminado Exitosamente.";
            }
        }
        return "Error: no se encontro el proveedor";
    }
    
    public String listarProveedores(){
        String lista = "";
        for (Proveedor proveedor : proveedores) {
            lista += proveedor.toString()+"\n";
        }
        return "".equals(lista) ? "Error: no hay proveedores registrados" : lista;
    }

    public boolean registrarCliente(String nombre, String contraseña){
        if (nombre == null || nombre.isBlank()){
            return false;
        }
        Cliente cliente = new Cliente(generarCodigoCliente(), nombre, contraseña);
        clientes.add(cliente);
        return true;
    }
    
    public String eliminarCliente(int codigo){
        Cliente cliente = buscarClientePorCodigo(codigo);
        if (cliente == null){
            return "Error: no se encontro";
        }
        clientes.remove(cliente);
        return "Cliente Eliminado Correctamente";
    }
    
    
    //Importante: la lista se imprime desde fuera y el error tambien
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
        return "".equals(lista) ? "Error: no hay clientes registrados" : lista;
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
        return "".equals(listaString) ? "Error: no hay clientes con deudas" : listaString;
    }
    
    public String listarTransaccionesCliente(int codigo){
        Cliente cliente = buscarClientePorCodigo(codigo);
        if (cliente == null){
            return "Error: no existe el cliente";
        }
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
        return "".equals(lista) ? "Error: el cliente no tiene transacciones registradas" : lista;
    }
    
    public void registrarTransaccion(Transaccion transaccion){
        transacciones.add(transaccion);
    }
    
    public String registrarDeuda(int codigoCliente, int codigoProducto, int cantidad){
        Cliente cliente = buscarClientePorCodigo(codigoCliente);
        Producto producto = buscarProductoPorCodigo(codigoProducto);
        if (producto == null){
            return "Error: producto no encontrado.";
        }
        if (cliente == null){
            return "Error: cliente no encontrado.";
        }
        if (cantidad <= 0){
            return "Error: cantidad no valida.";
        }
        if (producto.getCantidad() < cantidad){
            return "Error: stock insuficiente.\nActual: "+producto.getCantidad();
        }
        producto.restar(cantidad);

        Deuda deuda = new Deuda(cliente,
                producto, 
                cantidad, 
                (generarCodigoTransaccion()), 
                LocalDate.now(), 
                (cantidad*producto.getPrecio()));
        
        transacciones.add(deuda);
        return "Deuda Registrada Correctamente.";
    }
    
    public String registrarVenta(int codigoCliente, int codigoProducto, int cantidad){
        Cliente cliente = buscarClientePorCodigo(codigoCliente);
        Producto producto = buscarProductoPorCodigo(codigoProducto);
        if (producto == null){
            return "Error: producto no encontrado.";
        }
        if (cliente == null){
            return "Error: cliente no encontrado.";
        }
        if (cantidad <= 0){
            return "Error: cantidad no valida.";
        }
        
        if (producto.getCantidad() < cantidad){
            return "Error: stock insuficiente.\nActual: "+producto.getCantidad();
        }
        producto.restar(cantidad);

        Venta venta = new Venta(
                producto,
                cliente, 
                cantidad, 
                (generarCodigoTransaccion()), 
                LocalDate.now(), 
                (cantidad*producto.getPrecio()));
        
        transacciones.add(venta);
        return "Venta Registrada Correctamente.";
    }
    
    public String registrarPago(int codigoProveedor, float monto){
        Proveedor proveedorARegistrar = null;
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getCodigo() == codigoProveedor){
                proveedorARegistrar = proveedor;
                break;
            }  
        }
        if (proveedorARegistrar == null){
            return "Error: no se encontro el proveedor";
        }
        Pago pago = new Pago(proveedorARegistrar,
                (generarCodigoTransaccion()),
                LocalDate.now(), 
                monto);
        
        transacciones.add(pago);
        return "Pago Registrado Correctamente.";
    }
    
    //Importante: la lista se imprime desde fuera y el error tambien
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
    
    //Importante: la lista se imprime desde fuera y el error tambien
    public List<Transaccion> buscarTransaccionesPorTipo(String tipo){
        List<Transaccion> resultado = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipo().equalsIgnoreCase(tipo)){
                resultado.add(transaccion);
            }
        }
        return resultado;
    }
    
    public Transaccion buscarTransaccionPorCodigo(int codigo){
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getCodigo() == codigo){
                return transaccion;
            }
        }
        return null;
    }
    
    public String listarTransacciones(){
        String lista = "";
        for (Transaccion transaccion : transacciones) {
            lista += transaccion.toString()+"\n";
        }
        return "".equals(lista) ? "Error: no hay transacciones registradas" : lista;
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
    
    public String marcarDeudaComoPagada(int codigo){
        Transaccion transaccion = buscarTransaccionPorCodigo(codigo);
        if (transaccion == null){
            return "Error: la transaccion no se ha encontrado";
        }
        if (transaccion.getTipo().equalsIgnoreCase("deuda")){
            Deuda deuda = (Deuda) transaccion;
            deuda.marcarComoPagado();
            return "Deuda marcada como pagada.";
        } else {
            return "Error: esta transaccion no es una deuda.";
        }
    }
    
    
    public List<Producto> getProductos(){ // devuelve TODOS los productos
        List<Producto> productosTotales = new ArrayList<>();
        for (Categoria categoria : categorias) {
            List<Producto> productos = categoria.getProductos();
            for (Producto producto : productos) {
                productosTotales.add(producto);
            }
        }
        return productosTotales;
    }
    
    public Categoria getCategoriaPorNombre(String nombre){
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equals(nombre)){return categoria;}
        }
        return null;
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
