public class ProductoElectronico extends Producto {

    private int mesesGarantia;

    public ProductoElectronico(String nombre, double precio, int stock, String categoria, int mesesGarantia) {
        super(nombre, precio, stock, categoria);
        this.mesesGarantia = mesesGarantia;
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    @Override
    public String getDetalle() {
        return "Garantia: " + mesesGarantia + " meses";
    }
}
