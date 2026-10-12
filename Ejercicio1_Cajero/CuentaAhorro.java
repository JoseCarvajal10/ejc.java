/**
 * Cuenta de ahorro: paga un pequeno interes sobre cada deposito.
 */
public class CuentaAhorro extends CuentaBancaria {

    private static final double TASA_INTERES = 0.01; // 1%

    public CuentaAhorro(String titular, double saldoInicial, String pin) {
        super(titular, saldoInicial, pin);
    }

    @Override
    public boolean depositar(double monto) {
        if (!super.depositar(monto)) {
            return false;
        }
        double interes = monto * TASA_INTERES;
        setSaldo(getSaldo() + interes);
        registrarMovimiento(String.format("Interes: +$%.2f", interes));
        return true;
    }

    @Override
    public String getTipo() {
        return "Cuenta de ahorro";
    }
}
