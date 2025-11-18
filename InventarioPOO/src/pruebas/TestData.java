package pruebas;

import controlador.SistemaController;
import modelo.Cliente;
import vista.ventanas.principales.VentanaLogin;

public class TestData {

    public static void poblarSistema(SistemaController controller) {

        // ===== Crear categorías y productos =====
        controller.agregarProducto("Bebidas", "Agua", 50, 1000);
        controller.agregarProducto("Bebidas", "Jugo", 30, 2500);
        controller.agregarProducto("Snacks", "Papas fritas", 20, 3000);
        controller.agregarProducto("Snacks", "Galletas", 40, 1500);

        // ===== Crear clientes =====
        controller.agregarCliente("Andres", "1234");
        controller.agregarCliente("Maria", "abcd");
        controller.agregarCliente("Ricardo", "superSecreta");
        controller.agregarCliente("Linulfo", "contraseña");
        controller.agregarCliente("Juan", "9999");
        controller.agregarCliente("Carlos", "amoPOO100");
        controller.agregarCliente("Ruben", "juniorTuPapa");

        // ===== Registrar ventas =====
        Cliente andres = controller.buscarCliente("Andres");
        Cliente maria = controller.buscarCliente("Maria");

        controller.comprarProducto(andres.getCodigo(), 1, 2); // 2 unidades de Agua
        controller.comprarProducto(maria.getCodigo(), 3, 1);  // 1 unidad Papas fritas
        controller.comprarProducto(andres.getCodigo(), 4, 5); // 5 unidades Galletas

        // ===== Crear backups =====
        controller.crearBackup();
    }

    public static void main(String[] args) {
        // 1️⃣ Crear el controller
        SistemaController sistema = new SistemaController();

        // 2️⃣ Poblamos el sistema con productos y clientes
        poblarSistema(sistema);

        // 3️⃣ Abrimos la ventana de login con el controller ya poblado
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaLogin(sistema).setVisible(true);
        });

        // 4️⃣ Guardar cambios finales en JSON (opcional)
        sistema.guardarCambios();

        System.out.println("Sistema poblado para pruebas ✅");
    }
}
