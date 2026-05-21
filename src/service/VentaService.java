package service;

import exception.StockInsuficienteException;
import model.Producto;

public class VentaService {

    public void venderProducto(Producto producto, int cantidad)
            throws StockInsuficienteException {

        if (cantidad > producto.getStock()) {

            throw new StockInsuficienteException(
                    "Stock insuficiente para realizar la venta."
            );

        }

        producto.reducirStock(cantidad);

        double total = producto.getPrecio() * cantidad;

        System.out.println("Venta realizada.");
        System.out.println("Total: $" + total);

    }

}
