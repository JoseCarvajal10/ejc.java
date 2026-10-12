import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Agenda {

    private static final int LONGITUD_TELEFONO = 10;

    private ArrayList<Contacto> contactos;

    public Agenda() {
        this.contactos = new ArrayList<>();
    }

    public boolean agregarContacto(Contacto c) {
        if (c.getNombre().isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return false;
        }
        if (!telefonoValido(c.getTelefono())) {
            System.out.println("Telefono invalido: debe tener solo numeros y " + LONGITUD_TELEFONO + " digitos.");
            return false;
        }
        if (!c.getEmail().contains("@") || !c.getEmail().contains(".")) {
            System.out.println("Email invalido: debe contener '@' y '.'.");
            return false;
        }
        for (Contacto existente : contactos) {
            if (existente.getNombre().equalsIgnoreCase(c.getNombre())
                    || existente.getTelefono().equals(c.getTelefono())) {
                System.out.println("Ya existe un contacto con ese nombre o telefono.");
                return false;
            }
        }
        contactos.add(c);
        return true;
    }

    private boolean telefonoValido(String telefono) {
        if (telefono.length() != LONGITUD_TELEFONO) {
            return false;
        }
        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Busqueda parcial: devuelve todos los contactos cuyo nombre contiene el texto
    public ArrayList<Contacto> buscarContacto(String nombre) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public void mostrarTodos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda esta vacia.");
            return;
        }
        for (Contacto c : contactos) {
            c.mostrarInfo();
        }
    }

    public boolean eliminarContacto(String nombre) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getNombre().equalsIgnoreCase(nombre)) {
                contactos.remove(i);
                return true;
            }
        }
        return false;
    }

    public void mostrarContactosPorGrupo(String grupo) {
        boolean hayAlguno = false;
        for (Contacto c : contactos) {
            if (c.getGrupo().equalsIgnoreCase(grupo)) {
                c.mostrarInfo();
                hayAlguno = true;
            }
        }
        if (!hayAlguno) {
            System.out.println("No hay contactos en el grupo " + grupo + ".");
        }
    }

    // Guarda un contacto por linea: nombre;telefono;email;grupo
    public void guardarEnArchivo(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Contacto c : contactos) {
                pw.println(c.getNombre() + ";" + c.getTelefono() + ";" + c.getEmail() + ";" + c.getGrupo());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    public void cargarDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    contactos.add(new Contacto(partes[0], partes[1], partes[2], partes[3]));
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe todavia, se empieza con la agenda vacia
        }
    }
}
