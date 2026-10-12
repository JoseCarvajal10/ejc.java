/**
 * Cuenta corriente: permite quedar en saldo negativo hasta un limite de sobregiro.
 */
public class CuentaCorriente extends CuentaBancaria {

    private double limiteSobregiro;

    public CuentaCorriente(String titular, double saldoInicial, String pin, double limiteSobregiro) {
        super(titular, saldoInicial, pin);
        this.limiteSobregiro = limiteSobregiro;
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    @Override
    protected double getSaldoDisponible() {
        return getSaldo() + limiteSobregiro;
    }

    @Override
    public String getTipo() {
        return "Cuenta corriente";
    }
}
