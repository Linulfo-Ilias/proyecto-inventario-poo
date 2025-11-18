/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.ventanas.clientes;

import controlador.SistemaController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Producto;
import vista.dialogos.DialogoError;
import vista.dialogos.DialogoInfo;
import vista.ventanas.principales.VentanaLogin;

/**
 *
 * @author Angie
 */
public class FormularioCliente extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormularioCliente.class.getName());

    private SistemaController controller;
    private JPanel panelCategorias;
    
    /**
     * Creates new form FormularioCliente
     */
    public FormularioCliente(SistemaController controller) {
        this.controller = controller;
        initComponents();
        
        panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
        JScrollPane scrollPrincipal = new JScrollPane(panelCategorias);
        scrollPrincipal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        cargarCategorias();
    }
    
    private void cargarCategorias(){
        panelCategorias.removeAll();
        
        for (Categoria categoria : controller.getInventario().getCategorias()) {
            // Creamos un panel por cada categoría
            JPanel panelCategoria = new JPanel();
            panelCategoria.setLayout(new BorderLayout());
            panelCategoria.setBorder(BorderFactory.createTitledBorder(categoria.getNombre()));

            // Creamos la tabla para esta categoría
            JTable tabla = new JTable();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Precio");

            for (Producto p : categoria.getProductos()) {
                modelo.addRow(new Object[]{p.getNombre(), p.getCantidad(), p.getPrecio()});
            }

            tabla.setModel(modelo);
            tabla.setFillsViewportHeight(true);
            tabla.setShowGrid(true);

            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) { // solo una vez por cambio
                        // Recorremos todas las tablas de todas las categorías
                        for (Component compCat : panelCategorias.getComponents()) {
                            if (!(compCat instanceof JPanel)) continue;
                            JPanel panelCat = (JPanel) compCat;

                            for (Component innerComp : panelCat.getComponents()) {
                                if (!(innerComp instanceof JScrollPane)) continue;
                                JScrollPane scroll = (JScrollPane) innerComp;
                                JTable t = (JTable) scroll.getViewport().getView();

                                // Deseleccionamos todas las demás tablas excepto la que generó el evento
                                if (t != tabla) {
                                    t.clearSelection();
                                }
                            }
                        }
                    }
                }
            });
            
            JScrollPane scrollTabla = new JScrollPane(tabla);
            scrollTabla.setPreferredSize(new Dimension(400, Math.min(150, categoria.getProductos().size() * 25 + 20)));

            panelCategoria.add(scrollTabla, BorderLayout.CENTER);

            panelCategorias.add(panelCategoria);
            panelCategorias.add(Box.createRigidArea(new Dimension(0, 10))); // espacio entre categorías
        }
        
        panelCategorias.revalidate();
        panelCategorias.repaint();
    }
    
    private void comprarProducto() {
        try {
            int cantidad = ((Number) jFormattedTextField1.getValue()).intValue();
            if (cantidad <= 0) {
                new DialogoError(this, true, "Ingrese una cantidad válida.").setVisible(true);
                return;
            }

            // Recorremos los paneles de categorías para encontrar la tabla seleccionada
            for (Component comp : panelCategorias.getComponents()) {
                if (!(comp instanceof JPanel)) continue;
                JPanel panelCat = (JPanel) comp;

                for (Component innerComp : panelCat.getComponents()) {
                    if (!(innerComp instanceof JScrollPane)) continue;
                    JScrollPane scroll = (JScrollPane) innerComp;
                    JTable tabla = (JTable) scroll.getViewport().getView();

                    int fila = tabla.getSelectedRow();
                    if (fila >= 0) {
                        // Obtenemos la categoría actual
                        String nombreCategoria = ((javax.swing.border.TitledBorder) panelCat.getBorder()).getTitle();
                        Categoria categoria = controller.getInventario().getCategoriaPorNombre(nombreCategoria);

                        // Obtenemos el producto seleccionado
                        Producto producto = categoria.getProductos().get(fila);

                        // Verificamos stock
                        if (producto.getCantidad() < cantidad) {
                            new DialogoError(this, true, "No hay suficiente stock").setVisible(true);
                            return;
                        }

                        // Obtenemos el nombre del cliente
                        String nombreCliente = jLabel2.getText();
                        Cliente cliente = controller.buscarCliente(nombreCliente);
                        if (cliente == null) {
                            new DialogoError(this, true, "Cliente no encontrado").setVisible(true);
                            return;
                        }

                        // Realizamos la compra
                        controller.comprarProducto(cliente.getCodigo(), producto.getCodigo(), cantidad);

                        new DialogoInfo(this, true, "Compra realizada con éxito!").setVisible(true);

                        // Recargamos las tablas para reflejar los cambios
                        cargarCategorias();
                        return;
                    }
                }
            }

            // Si llegamos aquí, no se seleccionó nada
            new DialogoError(this, true, "Seleccione un producto para comprar").setVisible(true);

        } catch (Exception ex) {
            new DialogoError(this, true, "Ocurrió un error: " + ex.getMessage()).setVisible(true);
            ex.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("comprar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("nombre del cliente");

        jButton2.setText("cerrar sesión");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setName("panelCategorias"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jLabel3.setText("cantidad");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 254, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1))))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        comprarProducto();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new VentanaLogin(controller).setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
