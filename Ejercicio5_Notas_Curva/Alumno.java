public class Alumno {

    private static final double MAX_NOTA = 10.0;
    private static final double NOTA_APROBACION = 6.0;

    private String nombre;
    private double notaParcial1;   // 30%
    private double notaParcial2;   // 30%
    private double examenFinal;    // 40%
    private double notaOriginal;
    private double notaFinal;

    public Alumno(String nombre, double notaParcial1, double notaParcial2, double examenFinal) {
        this.nombre = nombre;
        this.notaParcial1 = notaParcial1;
        this.notaParcial2 = notaParcial2;
        this.examenFinal = examenFinal;
        calcularNotaOriginal();
        this.notaFinal = notaOriginal;
    }

    // Aplica las ponderaciones: 30% + 30% + 40%
    public double calcularNotaOriginal() {
        notaOriginal = notaParcial1 * 0.30 + notaParcial2 * 0.30 + examenFinal * 0.40;
        return notaOriginal;
    }

    // Suma puntos a la nota original sin pasar de 10
    public void aplicarCurva(double puntos) {
        notaFinal = Math.min(MAX_NOTA, notaOriginal + puntos);
    }

    // Aumenta la nota original en un porcentaje (ej. 10% de 5.0 es +0.5), sin pasar de 10
    public void aplicarCurvaPorcentual(double porcentaje) {
        double incremento = notaOriginal * porcentaje / 100.0;
        notaFinal = Math.min(MAX_NOTA, notaOriginal + incremento);
    }

    public boolean estaAprobado() {
        return notaFinal >= NOTA_APROBACION;
    }

    public boolean aproboSinCurva() {
        return notaOriginal >= NOTA_APROBACION;
    }

    public String getNombre() {
        return nombre;
    }

    public double getNotaOriginal() {
        return notaOriginal;
    }

    public double getNotaFinal() {
        return notaFinal;
    }
}
