import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static final String ARCHIVO = "agenda.txt";

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.cargarDesdeArchivo(ARCHIVO);

        System.out.println("===== AGENDA DE CONTACTOS =====");

        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Registrar contacto");
            System.out.println("2. Buscar contacto");
            System.out.println("3. Listar todos los contactos");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Mostrar contactos por grupo");
            System.out.println("6. Guardar y salir");
            opcion = leerEntero("Elija una opcion: ", 1, 6);

            switch (opcion) {
                case 1:
                    registrar(agenda);
                    break;
                case 2:
                    buscar(agenda);
                    break;
                case 3:
                    agenda.mostrarTodos();
                    break;
                case 4:
                    System.out.print("Nombre del contacto a eliminar: ");
                    if (agenda.eliminarContacto(sc.nextLine().trim())) {
                        System.out.println("Contacto eliminado.");
                    } else {
                        System.out.println("No se encontro ese contacto.");
                    }
                    break;
                case 5:
                    System.out.print("Grupo (Familia, Trabajo, Amigos...): ");
                    agenda.mostrarContactosPorGrupo(sc.nextLine().trim());
                    break;
                case 6:
                    agenda.guardarEnArchivo(ARCHIVO);
                    System.out.println("Agenda guardada. Hasta pronto.");
                    break;
            }
        }
    }

    static void registrar(Agenda agenda) {
        System.out.print("Nombre: ");
        String nombre = limpiar(sc.nextLine());
        System.out.print("Telefono: ");
        String telefono = limpiar(sc.nextLine());
        System.out.print("Email: ");
        String email = limpiar(sc.nextLine());
        System.out.print("Grupo (Familia, Trabajo, Amigos...): ");
        String grupo = limpiar(sc.nextLine());
        if (grupo.isEmpty()) {
            grupo = "Otros";
        }

        if (agenda.agregarContacto(new Contacto(nombre, telefono, email, grupo))) {
            System.out.println("Contacto registrado.");
        }
    }

    static void buscar(Agenda agenda) {
        System.out.print("Texto a buscar en el nombre: ");
        ArrayList<Contacto> resultado = agenda.buscarContacto(sc.nextLine().trim());
        if (resultado.isEmpty()) {
            System.out.println("No se encontraron contactos.");
            return;
        }
        System.out.println("Se encontraron " + resultado.size() + " contacto(s):");
        for (Contacto c : resultado) {
            c.mostrarInfo();
        }
    }

    // Quita espacios y el caracter ';' para no danar el archivo
    static String limpiar(String texto) {
        return texto.trim().replace(";", ",");
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
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }
}
