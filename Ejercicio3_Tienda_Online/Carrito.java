import java.util.ArrayList;

public class Carrito {

    private static final double MONTO_DESCUENTO = 1000;
    private static final double PORCENTAJE_DESCUENTO = 0.10;

    private Cliente cliente;
    private Inventario inventario;
    private ArrayList<ItemCarrito> items;

    public Carrito(Cliente cliente, Inventario inventario) {
        this.cliente = cliente;
        this.inventario = inventario;
        this.items = new ArrayList<>();
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        // El stock se valida y se descuenta a traves del inventario
        if (!inventario.hayStock(producto, cantidad)) {
            return false;
        }
        inventario.disminuirStock(producto, cantidad);

        for (ItemCarrito item : items) {
            if (item.getProducto() == producto) {
                item.sumarCantidad(cantidad);
                return true;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
        return true;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    public double calcularDescuento() {
        double subtotal = calcularSubtotal();
        if (subtotal > MONTO_DESCUENTO) {
            return subtotal * PORCENTAJE_DESCUENTO;
        }
        return 0;
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public void mostrarCarrito() {
        System.out.println("\n===== CARRITO DE " + cliente.getNombre().toUpperCase() + " =====");
        if (items.isEmpty()) {
            System.out.println("El carrito esta vacio.");
            return;
        }
        for (ItemCarrito item : items) {
            System.out.printf("%-12s x%-3d $%9.2f%n",
                    item.getProducto().getNombre(), item.getCantidad(), item.getSubtotal());
        }
        System.out.printf("Subtotal parcial: $%.2f%n", calcularSubtotal());
    }

    public void mostrarFactura() {
        System.out.println("\n==============================");
        System.out.println("          FACTURA");
        System.out.println("==============================");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Email:   " + cliente.getEmail());
        System.out.println("------------------------------");
        for (ItemCarrito item : items) {
            System.out.printf("%-12s x%-3d $%9.2f%n",
                    item.getProducto().getNombre(), item.getCantidad(), item.getSubtotal());
        }
        System.out.println("------------------------------");
        System.out.printf("Subtotal:  $%9.2f%n", calcularSubtotal());
        System.out.printf("Descuento: $%9.2f%n", calcularDescuento());
        System.out.printf("TOTAL:     $%9.2f%n", calcularTotal());
        System.out.println("==============================");
    }
}
