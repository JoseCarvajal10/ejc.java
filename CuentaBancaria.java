import java.util.ArrayList;

/**
 * Cuenta bancaria con historial de las ultimas 5 transacciones.
 */
public class CuentaBancaria extends Cuenta {

    private static final int MAX_MOVIMIENTOS = 5;

    private ArrayList<String> movimientos;
    private boolean bloqueada;

    public CuentaBancaria(String titular, double saldoInicial, String pin) {
        super(titular, saldoInicial, pin);
        this.movimientos = new ArrayList<>();
        this.bloqueada = false;
    }

    // Cuanto dinero se puede sacar como maximo (las cuentas corrientes lo cambian)
    protected double getSaldoDisponible() {
        return getSaldo();
    }

    @Override
    public boolean depositar(double monto) {
        if (monto <= 0) {
            return false;
        }
        setSaldo(getSaldo() + monto);
        registrarMovimiento(String.format("Deposito: +$%.2f", monto));
        return true;
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0 || monto > getSaldoDisponible()) {
            return false;
        }
        setSaldo(getSaldo() - monto);
        registrarMovimiento(String.format("Retiro: -$%.2f", monto));
        return true;
    }

    public boolean transferir(CuentaBancaria cuentaDestino, double monto) {
        if (cuentaDestino == null || cuentaDestino == this) {
            return false;
        }
        if (monto <= 0 || monto > getSaldoDisponible()) {
            return false;
        }
        setSaldo(getSaldo() - monto);
        registrarMovimiento(String.format("Transferencia a %s: -$%.2f", cuentaDestino.getTitular(), monto));
        cuentaDestino.recibirTransferencia(monto, getTitular());
        return true;
    }

    public void recibirTransferencia(double monto, String origen) {
        setSaldo(getSaldo() + monto);
        registrarMovimiento(String.format("Transferencia de %s: +$%.2f", origen, monto));
    }

    protected void registrarMovimiento(String movimiento) {
        movimientos.add(movimiento);
        if (movimientos.size() > MAX_MOVIMIENTOS) {
            movimientos.remove(0);
        }
    }

    public ArrayList<String> getMovimientos() {
        return new ArrayList<>(movimientos);
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void bloquear() {
        this.bloqueada = true;
    }

    public String getTipo() {
        return "Cuenta bancaria";
    }
}
