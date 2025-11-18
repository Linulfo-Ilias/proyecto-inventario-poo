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

    // ================= Constructor =================
    public SistemaController() {
        try {
            gestor = new GestorPersistencia();
            reporte = new Reporte();
            manager = new BackupManager();
            new VentanaLogin(this).setVisible(true);
        } catch (Exception e) {
            new DialogoError(null, true, "ERROR: no se pudo ejecutar el programa.").setVisible(true);
        }
    }

    // ================= Main =================
    public static void main(String[] args) {
        new SistemaController();
    }

    // ================= Login =================
    public boolean verificarCredenciales(String usuario, String contraseña) {
        if (usuario.isEmpty() || contraseña.isEmpty()) return false;
        return true;
    }

    public Cliente Logear(String usuario, String contraseña) {
        if (!verificarCredenciales(usuario, contraseña)) return null;

        for (Cliente cliente : inventario.getClientes()) {
            if (usuario.equals(cliente.getNombre()) && contraseña.equals(cliente.getContraseña())) {
                return cliente;
            }
        }
        return null;
    }

    public boolean iniciarAdmin(String usuario, String contraseña) {
        if (usuario.equals("XxFrijolesxX_YT") && contraseña.equals("18112025Red")) {
            new PanelDeControl(this).setVisible(true);
            return true;
        }
        return false;
    }

    // ================= Getters =================
    public List<Producto> getProductos() {
        return inventario.getProductos();
    }

    public List<Cliente> getClientes() {
        return inventario.getClientes();
    }

    public List<Venta> getVentas() {
        List<Venta> ventas = new ArrayList<>();
        for (Transaccion transaccion : inventario.getTransacciones()) {
            if (transaccion instanceof Venta) {
                ventas.add((Venta) transaccion);
            }
        }
        return ventas;
    }

    public int getComprasTotales(Cliente c) {
        int contador = 0;
        for (Transaccion transaccion : inventario.getTransacciones()) {
            if (transaccion instanceof Venta) {
                Venta venta = (Venta) transaccion;
                if (c.equals(venta.getCliente())) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public Inventario getInventario() {
        return inventario;
    }

    // ================= Productos =================
    public boolean eliminarProducto(int codigo) {
        try {
            inventario.eliminarProducto(codigo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editarProducto(Producto p, String nuevoNombre, float precio, int cantidad) {
        try {
            p.setNombre(nuevoNombre);
            p.setPrecio(precio);
            p.setCantidad(cantidad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean agregarProducto(String nombreCategoria, String nombre, int cantidad, int precio) {
        try {
            inventario.registrarProducto(nombreCategoria, nombre, cantidad, precio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= Clientes =================
    public boolean eliminarCliente(int codigo) {
        try {
            inventario.eliminarCliente(codigo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean agregarCliente(String nombre, String contraseña) {
        try {
            inventario.registrarCliente(nombre, contraseña);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cliente buscarCliente(String nombre) {
        try {
            return inventario.buscarClientePorNombre(nombre).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    // ================= Persistencia =================
    public boolean guardarCambios() {
        try {
            gestor.guardarInventario(inventario, "data/inventario.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= Reportes =================
    public boolean generarReporteVentas() {
        try {
            reporte.generarReporteVentas(inventario.getTransacciones());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= Backups =================
    public List<BackupManager.Backup> listarBackups() {
        return manager.listarBackups();
    }

    public boolean crearBackup() {
        try {
            manager.crearBackup(inventario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarBackup(String nombreArchivo) {
        try {
            manager.eliminarBackup(nombreArchivo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean restaurarBackup(String nombreArchivo) {
        try {
            this.inventario = manager.restaurarBackup(nombreArchivo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= Compras =================
    public boolean comprarProducto(int codCliente, int codProducto, int cantidad) {
        try {
            inventario.registrarVenta(codCliente, codProducto, cantidad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= Registrar Usuario =================
    public boolean registrarUsuario(String nombre, String contraseña) {
        try {
            inventario.registrarCliente(nombre, contraseña);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
