import java.util.ArrayList;

/**
 * Banco: guarda las cuentas y permite buscarlas y validar el PIN.
 */
public class Banco {

    private ArrayList<CuentaBancaria> cuentas;

    public Banco() {
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

    public CuentaBancaria buscarCuenta(String titular) {
        for (CuentaBancaria c : cuentas) {
            if (c.getTitular().equalsIgnoreCase(titular)) {
                return c;
            }
        }
        return null;
    }

    public boolean validarPin(String titular, String pin) {
        CuentaBancaria cuenta = buscarCuenta(titular);
        return cuenta != null && cuenta.validarPin(pin);
    }
}
