/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import persistencia.ReporteDAO;

/**
 *
 * @author Angie
 */
public class Reporte {   
    ReporteDAO dao = new ReporteDAO();
    
    public void generarReporteVentas(List<Transaccion> transacciones){
        int ventasDia = 0;
        int ventasMes = 0;
        int nTransacciones = 0;
        String masVendidos = "";
        float total = 0;
        LocalDate hoy = LocalDate.now();
        
        HashMap<String, Integer> productosVendidos = new HashMap<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getFecha().isEqual(hoy)){
                ventasDia++;
            }
            if (transaccion.getFecha().getMonth() == hoy.getMonth() && transaccion.getFecha().getYear() == hoy.getYear()){
                ventasMes++;
            }
            if (transaccion instanceof Venta){
                Venta venta = (Venta) transaccion;
                Producto producto = venta.getProducto();
                String nombre = producto.getNombre();
                productosVendidos.putIfAbsent(nombre, 0);
                int cantidad = productosVendidos.get(nombre);
                productosVendidos.put(nombre, cantidad + 1);
            }
            total += transaccion.getMonto();
            nTransacciones++;
        }
        
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(productosVendidos.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        int contador = 0;
        for (Map.Entry<String, Integer> entry : lista) {
            if (contador >= 4){ break;}
            masVendidos += ("   "+entry.getKey() + ": " + entry.getValue()+"\n");
            contador++;
        }
        
        
        String reporte = "=== REPORTE DE VENTAS ===\n\n"
                + "Ventas del dia: "+ventasDia+"\n"
                + "Ventas del mes: "+ventasMes+"\n"
                + "Productos mas vendidos: \n"+masVendidos+"\n"
                + "Cantidad de transacciones: "+nTransacciones+"\n"
                + "Ingresos totales: "+total+"\n";
        
        
        dao.guardarReporte("reporte_ventas.txt", reporte);
    }
    public void generarReporteClientes(List<Cliente> clientes){
    
    }
}
