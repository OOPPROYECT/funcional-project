package service;

import exception.InventarioVacioException;
import exception.ProductoNoExisteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import model.Producto;

public class InventarioService {

    private static final String INVENTARIO_FILE = "inventario.dat";
    private HashMap<String, Producto> productos;

    public InventarioService() {
        productos = cargarInventario();
        if (productos == null) {
            productos = new HashMap<>();
        }
    }

    public boolean agregarProducto(Producto producto) {
        String clave = producto.getNombre().trim().toLowerCase();
        if (productos.containsKey(clave)) {
            return false;
        }
        productos.put(clave, producto);
        guardarInventario();
        return true;
    }

    public void eliminarProducto(String nombre) throws ProductoNoExisteException {
        String clave = nombre.trim().toLowerCase();
        if (!productos.containsKey(clave)) {
            throw new ProductoNoExisteException("El producto no existe.");
        }
        productos.remove(clave);
        guardarInventario();
    }

    public void modificarProducto(String nombre, double nuevoPrecio, int nuevoStock)
            throws ProductoNoExisteException {
        Producto producto = buscarProducto(nombre);
        if (producto == null) {
            throw new ProductoNoExisteException("El producto no existe.");
        }
        producto.setPrecio(nuevoPrecio);
        producto.setStock(nuevoStock);
        guardarInventario();
    }

    public void mostrarProductos() throws InventarioVacioException {
        if (productos.isEmpty()) {
            throw new InventarioVacioException("El inventario está vacío.");
        }
        for (Producto p : productos.values()) {
            System.out.println(p);
        }
    }

    public Producto buscarProducto(String nombre) {
        return productos.get(nombre.trim().toLowerCase());
    }

    public int generarNuevoId() {
        return productos.values().stream()
                .mapToInt(Producto::getId)
                .max()
                .orElse(0) + 1;
    }

    private void guardarInventario() {
        try (FileOutputStream fos = new FileOutputStream(INVENTARIO_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el inventario: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Producto> cargarInventario() {
        File archivo = new File(INVENTARIO_FILE);
        if (!archivo.exists()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (HashMap<String, Producto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar el inventario: " + e.getMessage());
            return null;
        }
    }

}
