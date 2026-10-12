import java.util.ArrayList;

/**
 * Inventario centralizado: el carrito consulta aqui el stock.
 */
public class Inventario {

    private ArrayList<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public int getCantidadProductos() {
        return productos.size();
    }

    public Producto getProducto(int indice) {
        if (indice < 0 || indice >= productos.size()) {
            return null;
        }
        return productos.get(indice);
    }

    public boolean hayStock(Producto p, int cantidad) {
        return p.hayStock(cantidad);
    }

    public void disminuirStock(Producto p, int cantidad) {
        p.disminuirStock(cantidad);
    }

    public ArrayList<String> getCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Producto p : productos) {
            if (!categorias.contains(p.getCategoria())) {
                categorias.add(p.getCategoria());
            }
        }
        return categorias;
    }

    // Muestra todo el catalogo o solo una categoria (si categoria es null)
    public void mostrarCatalogo(String categoria) {
        System.out.println("\n===== CATALOGO =====");
        boolean hayAlguno = false;
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (categoria == null || p.getCategoria().equalsIgnoreCase(categoria)) {
                String detalle = p.getDetalle().isEmpty() ? "" : " | " + p.getDetalle();
                System.out.printf("%d. %-12s $%8.2f | Stock: %-3d | %s%s%n",
                        i + 1, p.getNombre(), p.getPrecio(), p.getStock(), p.getCategoria(), detalle);
                hayAlguno = true;
            }
        }
        if (!hayAlguno) {
            System.out.println("No hay productos en esa categoria.");
        }
    }
}
