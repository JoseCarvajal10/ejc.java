import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Curso {

    private String nombre;
    private ArrayList<Estudiante> estudiantes;

    public Curso(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante e) {
        estudiantes.add(e);
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public int getCantidadEstudiantes() {
        return estudiantes.size();
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0;
        }
        double suma = 0;
        for (Estudiante e : estudiantes) {
            suma += e.getPromedio();
        }
        return suma / estudiantes.size();
    }

    public int contarAprobados() {
        int cuenta = 0;
        for (Estudiante e : estudiantes) {
            if (e.aprobo()) {
                cuenta++;
            }
        }
        return cuenta;
    }

    public int contarReprobados() {
        return estudiantes.size() - contarAprobados();
    }

    public Estudiante getMejorEstudiante() {
        if (estudiantes.isEmpty()) {
            return null;
        }
        Estudiante mejor = estudiantes.get(0);
        for (Estudiante e : estudiantes) {
            if (e.getPromedio() > mejor.getPromedio()) {
                mejor = e;
            }
        }
        return mejor;
    }

    public Estudiante getPeorEstudiante() {
        if (estudiantes.isEmpty()) {
            return null;
        }
        Estudiante peor = estudiantes.get(0);
        for (Estudiante e : estudiantes) {
            if (e.getPromedio() < peor.getPromedio()) {
                peor = e;
            }
        }
        return peor;
    }

    public Estudiante buscarEstudiante(String nombreEstudiante) {
        for (Estudiante e : estudiantes) {
            if (e.getNombre().equalsIgnoreCase(nombreEstudiante)) {
                return e;
            }
        }
        return null;
    }

    public boolean modificarNota(String nombreEstudiante, String nombreAsignatura, double nuevaNota) {
        Estudiante e = buscarEstudiante(nombreEstudiante);
        if (e == null) {
            return false;
        }
        Asignatura a = e.buscarAsignatura(nombreAsignatura);
        if (a == null) {
            return false;
        }
        a.setNotaFinal(nuevaNota);
        e.calcularPromedio();
        return true;
    }

    public void mostrarEstadisticas() {
        System.out.println("\n===== ESTADISTICAS DE " + nombre.toUpperCase() + " =====");
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.printf("Promedio general del grupo: %.2f%n", calcularPromedioGeneral());
        System.out.println("Aprobados: " + contarAprobados());
        System.out.println("Reprobados: " + contarReprobados());
        Estudiante mejor = getMejorEstudiante();
        Estudiante peor = getPeorEstudiante();
        System.out.printf("Mayor promedio: %s (%.2f)%n", mejor.getNombre(), mejor.getPromedio());
        System.out.printf("Menor promedio: %s (%.2f)%n", peor.getNombre(), peor.getPromedio());
    }

    public void mostrarListado() {
        System.out.println("\n===== LISTADO DE ESTUDIANTES =====");
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        for (Estudiante e : estudiantes) {
            e.mostrarInfo();
        }
    }

    public boolean guardarReporte(String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("REPORTE DEL CURSO: " + nombre);
            pw.println("==============================");
            pw.println();
            pw.println("LISTADO DE ESTUDIANTES");
            for (Estudiante e : estudiantes) {
                pw.printf("- %s | Promedio: %.2f | Faltas: %d | %s%n",
                        e.getNombre(), e.getPromedio(), e.getFaltas(),
                        e.aprobo() ? "Aprobo" : "Reprobo");
            }
            pw.println();
            pw.println("ESTADISTICAS GENERALES");
            pw.printf("Promedio general: %.2f%n", calcularPromedioGeneral());
            pw.println("Aprobados: " + contarAprobados());
            pw.println("Reprobados: " + contarReprobados());
            if (!estudiantes.isEmpty()) {
                pw.printf("Mayor promedio: %s (%.2f)%n", getMejorEstudiante().getNombre(), getMejorEstudiante().getPromedio());
                pw.printf("Menor promedio: %s (%.2f)%n", getPeorEstudiante().getNombre(), getPeorEstudiante().getPromedio());
            }
            return true;
        } catch (IOException ex) {
            System.out.println("Error al guardar el reporte: " + ex.getMessage());
            return false;
        }
    }
}
