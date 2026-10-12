import java.time.LocalDate;

public class ProductoAlimenticio extends Producto {

    private LocalDate fechaCaducidad;

    public ProductoAlimenticio(String nombre, double precio, int stock, String categoria, LocalDate fechaCaducidad) {
        super(nombre, precio, stock, categoria);
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    @Override
    public String getDetalle() {
        return "Caduca: " + fechaCaducidad;
    }
}
