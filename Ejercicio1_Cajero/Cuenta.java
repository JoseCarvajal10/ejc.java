/**
 * Clase abstracta base para todos los tipos de cuenta.
 */
public abstract class Cuenta {

    private String titular;
    private double saldo;
    private String pin;

    public Cuenta(String titular, double saldoInicial, String pin) {
        this.titular = titular;
        this.saldo = saldoInicial;
        this.pin = pin;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double consultarSaldo() {
        return saldo;
    }

    public boolean validarPin(String pinIngresado) {
        return pin.equals(pinIngresado);
    }

    public abstract boolean depositar(double monto);

    public abstract boolean retirar(double monto);
}
