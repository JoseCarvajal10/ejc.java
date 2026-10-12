import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        sc.useLocale(Locale.US);

        System.out.println("===== SISTEMA DE NOTAS CON CURVA =====");
        int n = leerEntero("Cuantos alumnos desea ingresar? ", 1, 200);

        ArrayList<Alumno> alumnos = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.println("\n--- Alumno " + i + " de " + n + " ---");
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            double p1 = leerNota("Nota parcial 1 (30%): ");
            double p2 = leerNota("Nota parcial 2 (30%): ");
            double ex = leerNota("Examen final (40%): ");
            alumnos.add(new Alumno(nombre, p1, p2, ex));
        }

        // Promedio del curso antes de la curva
        double promedio = promedioOriginal(alumnos);
        System.out.printf("%nPromedio del curso (sin curva): %.2f%n", promedio);

        boolean seAplicoCurva = false;
        if (promedio < 6) {
            System.out.println("El promedio es menor a 6, se aplicara una curva.");
            seAplicoCurva = aplicarCurva(alumnos);
        } else {
            System.out.println("El promedio es 6 o mas, no hace falta curva.");
        }

        mostrarListado(alumnos);
        mostrarEstadisticas(alumnos, seAplicoCurva);
    }

    static boolean aplicarCurva(ArrayList<Alumno> alumnos) {
        System.out.println("\nTipo de curva:");
        System.out.println("1. Puntos fijos");
        System.out.println("2. Porcentual");
        System.out.println("3. Basada en el mejor alumno (10 - nota mas alta)");
        int tipo = leerEntero("Elija una opcion: ", 1, 3);

        switch (tipo) {
            case 1:
                double puntos = leerNota("Puntos a sumar a todos (ejemplo 2): ");
                for (Alumno a : alumnos) {
                    a.aplicarCurva(puntos);
                }
                System.out.println("Curva aplicada: +" + puntos + " puntos.");
                break;
            case 2:
                double porcentaje = leerDecimal("Porcentaje de aumento (ejemplo 10): ", 0, 100);
                for (Alumno a : alumnos) {
                    a.aplicarCurvaPorcentual(porcentaje);
                }
                System.out.println("Curva aplicada: +" + porcentaje + "%.");
                break;
            case 3:
                double mayor = 0;
                for (Alumno a : alumnos) {
                    if (a.getNotaOriginal() > mayor) {
                        mayor = a.getNotaOriginal();
                    }
                }
                double curva = 10 - mayor;
                for (Alumno a : alumnos) {
                    a.aplicarCurva(curva);
                }
                System.out.printf("Nota mas alta: %.2f -> curva de +%.2f puntos.%n", mayor, curva);
                break;
        }
        return true;
    }

    static double promedioOriginal(ArrayList<Alumno> alumnos) {
        double suma = 0;
        for (Alumno a : alumnos) {
            suma += a.getNotaOriginal();
        }
        return suma / alumnos.size();
    }

    static void mostrarListado(ArrayList<Alumno> alumnos) {
        System.out.println("\n===== LISTADO DE ALUMNOS =====");
        System.out.printf("%-15s %-10s %-10s %s%n", "Nombre", "Original", "Final", "Estado");
        for (Alumno a : alumnos) {
            System.out.printf("%-15s %-10.2f %-10.2f %s%n", a.getNombre(), a.getNotaOriginal(),
                    a.getNotaFinal(), a.estaAprobado() ? "APROBO" : "REPROBO");
        }
    }

    static void mostrarEstadisticas(ArrayList<Alumno> alumnos, boolean seAplicoCurva) {
        double suma = 0;
        for (Alumno a : alumnos) {
            suma += a.getNotaFinal();
        }
        double media = suma / alumnos.size();

        // Desviacion estandar de las notas finales
        double sumaCuadrados = 0;
        for (Alumno a : alumnos) {
            sumaCuadrados += Math.pow(a.getNotaFinal() - media, 2);
        }
        double desviacion = Math.sqrt(sumaCuadrados / alumnos.size());

        // Alumnos que pasaron de reprobado a aprobado gracias a la curva
        int rescatados = 0;
        for (Alumno a : alumnos) {
            if (!a.aproboSinCurva() && a.estaAprobado()) {
                rescatados++;
            }
        }

        System.out.println("\n===== ESTADISTICAS =====");
        System.out.printf("%s del curso: %.2f%n", seAplicoCurva ? "Nueva media" : "Media", media);
        System.out.printf("Desviacion estandar de las notas finales: %.2f%n", desviacion);
        System.out.println("Alumnos que pasaron de reprobado a aprobado por la curva: " + rescatados);
    }

    // Lee un entero; si el usuario escribe texto vuelve a pedirlo (InputMismatchException)
    static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine();
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Ingrese un numero entre " + min + " y " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida, escriba un numero.");
                sc.nextLine();
            }
        }
    }

    static double leerDecimal(String mensaje, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = sc.nextDouble();
                sc.nextLine();
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Ingrese un valor entre " + min + " y " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida, escriba un numero.");
                sc.nextLine();
            }
        }
    }

    static double leerNota(String mensaje) {
        return leerDecimal(mensaje, 0, 10);
    }
}
