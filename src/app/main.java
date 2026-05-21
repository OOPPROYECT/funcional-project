package app;

import exception.InventarioVacioException;
import exception.ProductoNoExisteException;
import model.Producto;
import service.InventarioService;
import util.Consola;

public class Main {

    public static void main(String[] args) {
        InventarioService inventario = new InventarioService();
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción:");

            switch (opcion) {
                case 1:
                    agregarProducto(inventario);
                    break;
                case 2:
                    eliminarProducto(inventario);
                    break;
                case 3:
                    modificarProducto(inventario);
                    break;
                case 4:
                    mostrarInventario(inventario);
                    break;
                case 5:
                    salir = true;
                    System.out.println("Cerrando el sistema. Los datos del inventario se conservarán.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
            System.out.println();
        }
    }

    private static void mostrarMenu() {
        System.out.println("===== MENÚ DE INVENTARIO =====");
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Modificar producto");
        System.out.println("4. Mostrar inventario");
        System.out.println("5. Salir");
    }

    private static void agregarProducto(InventarioService inventario) {
        String nombre = leerTexto("Ingrese el nombre del producto:");
        double precio = leerDouble("Ingrese el precio del producto:");
        int stock = leerEntero("Ingrese la cantidad en stock:");

        Producto producto = new Producto(inventario.generarNuevoId(), nombre, precio, stock);
        if (inventario.agregarProducto(producto)) {
            System.out.println("Producto agregado correctamente.");
        } else {
            System.out.println("El producto ya existe. Use modificar para actualizarlo.");
        }
    }

    private static void eliminarProducto(InventarioService inventario) {
        String nombre = leerTexto("Ingrese el nombre del producto a eliminar:");
        try {
            inventario.eliminarProducto(nombre);
            System.out.println("Producto eliminado correctamente.");
        } catch (ProductoNoExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void modificarProducto(InventarioService inventario) {
        String nombre = leerTexto("Ingrese el nombre del producto a modificar:");
        try {
            double nuevoPrecio = leerDouble("Ingrese el nuevo precio:");
            int nuevoStock = leerEntero("Ingrese el nuevo stock:");
            inventario.modificarProducto(nombre, nuevoPrecio, nuevoStock);
            System.out.println("Producto modificado correctamente.");
        } catch (ProductoNoExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarInventario(InventarioService inventario) {
        try {
            System.out.println("===== INVENTARIO =====");
            inventario.mostrarProductos();
        } catch (InventarioVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            String valor = Consola.sc.nextLine();
            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numérico entero. Intente de nuevo.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            String valor = Consola.sc.nextLine();
            try {
                return Double.parseDouble(valor.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numérico válido. Intente de nuevo.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            String valor = Consola.sc.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("El nombre no puede estar vacío. Ingrese un valor válido.");
        }
    }

}
