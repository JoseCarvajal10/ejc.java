import java.util.ArrayList;

public class Estudiante {

    private String nombre;
    private ArrayList<Asignatura> asignaturas;
    private double promedio;
    private int faltas;

    public Estudiante(String nombre, int faltas) {
        this.nombre = nombre;
        this.faltas = faltas;
        this.asignaturas = new ArrayList<>();
        this.promedio = 0;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
        calcularPromedio();
    }

    public double calcularPromedio() {
        if (asignaturas.isEmpty()) {
            promedio = 0;
            return promedio;
        }
        double suma = 0;
        for (Asignatura a : asignaturas) {
            suma += a.getNotaFinal();
        }
        promedio = suma / asignaturas.size();
        return promedio;
    }

    // Aprueba si el promedio es >= 6 y tiene menos de 10 faltas
    public boolean aprobo() {
        return promedio >= 6 && faltas < 10;
    }

    public Asignatura buscarAsignatura(String nombreAsignatura) {
        for (Asignatura a : asignaturas) {
            if (a.getNombre().equalsIgnoreCase(nombreAsignatura)) {
                return a;
            }
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public int getFaltas() {
        return faltas;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void mostrarInfo() {
        System.out.println("Estudiante: " + nombre + " | Faltas: " + faltas);
        for (Asignatura a : asignaturas) {
            System.out.printf("   - %s: %.2f%n", a.getNombre(), a.getNotaFinal());
        }
        System.out.printf("   Promedio: %.2f (%s)%n", promedio, aprobo() ? "APROBO" : "REPROBO");
    }
}
