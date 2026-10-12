import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Curso curso = new Curso("Programacion");

        System.out.println("===== REGISTRO ACADEMICO =====");
        int n = leerEntero("Cuantos estudiantes desea registrar? ", 1, 100);
        for (int i = 1; i <= n; i++) {
            System.out.println("\n--- Estudiante " + i + " de " + n + " ---");
            registrarEstudiante(curso);
        }

        curso.mostrarEstadisticas();

        int opcion = 0;
        while (opcion != 7) {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Registrar otro estudiante");
            System.out.println("2. Ver estadisticas del curso");
            System.out.println("3. Ver listado de estudiantes");
            System.out.println("4. Buscar estudiante");
            System.out.println("5. Modificar una nota");
            System.out.println("6. Guardar reporte en archivo");
            System.out.println("7. Salir");
            opcion = leerEntero("Elija una opcion: ", 1, 7);

            switch (opcion) {
                case 1:
                    registrarEstudiante(curso);
                    break;
                case 2:
                    curso.mostrarEstadisticas();
                    break;
                case 3:
                    curso.mostrarListado();
                    break;
                case 4:
                    System.out.print("Nombre del estudiante: ");
                    Estudiante e = curso.buscarEstudiante(sc.nextLine().trim());
                    if (e == null) {
                        System.out.println("Estudiante no encontrado.");
                    } else {
                        e.mostrarInfo();
                    }
                    break;
                case 5:
                    modificarNota(curso);
                    break;
                case 6:
                    if (curso.guardarReporte("reporte_curso.txt")) {
                        System.out.println("Reporte guardado en reporte_curso.txt");
                    }
                    break;
                case 7:
                    System.out.println("Programa finalizado.");
                    break;
            }
        }
    }

    static void registrarEstudiante(Curso curso) {
        String nombre;
        while (true) {
            System.out.print("Nombre del estudiante: ");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");
            } else if (curso.buscarEstudiante(nombre) != null) {
                System.out.println("Ya existe un estudiante con ese nombre.");
            } else {
                break;
            }
        }

        int faltas = leerEntero("Numero de faltas: ", 0, 1000);
        Estudiante est = new Estudiante(nombre, faltas);

        int cantidad = leerEntero("Cuantas asignaturas tiene? ", 1, 20);
        for (int i = 1; i <= cantidad; i++) {
            System.out.print("Nombre de la asignatura " + i + ": ");
            String nombreAsig = sc.nextLine().trim();
            if (nombreAsig.isEmpty()) {
                nombreAsig = "Asignatura " + i;
            }
            double nota = leerNota("Nota de " + nombreAsig + " (0 a 10): ");
            est.agregarAsignatura(new Asignatura(nombreAsig, nota));
        }

        curso.agregarEstudiante(est);
        System.out.println("Estudiante registrado.");
    }

    static void modificarNota(Curso curso) {
        System.out.print("Nombre del estudiante: ");
        String nombre = sc.nextLine().trim();
        Estudiante e = curso.buscarEstudiante(nombre);
        if (e == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }
        System.out.print("Nombre de la asignatura: ");
        String asignatura = sc.nextLine().trim();
        if (e.buscarAsignatura(asignatura) == null) {
            System.out.println("Asignatura no encontrada.");
            return;
        }
        double nueva = leerNota("Nueva nota (0 a 10): ");
        if (curso.modificarNota(nombre, asignatura, nueva)) {
            System.out.println("Nota modificada.");
        }
    }

    static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Ingrese un numero entre " + min + " y " + max + ".");
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    static double leerNota(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(sc.nextLine().trim());
                if (valor >= 0 && valor <= 10) {
                    return valor;
                }
                System.out.println("La nota debe estar entre 0 y 10.");
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}
