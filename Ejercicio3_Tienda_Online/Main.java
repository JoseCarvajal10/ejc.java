import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Inventario inventario = cargarCatalogo();

        System.out.println("===== TIENDA ONLINE =====");
        Cliente cliente = iniciarSesion();
        Carrito carrito = new Carrito(cliente, inventario);
        System.out.println("\nBienvenido/a, " + cliente.getNombre());

        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Ver catalogo completo");
            System.out.println("2. Filtrar catalogo por categoria");
            System.out.println("3. Agregar producto al carrito");
            System.out.println("4. Ver carrito");
            System.out.println("5. Pagar y ver factura");
            opcion = leerEntero("Elija una opcion: ", 1, 5);

            switch (opcion) {
                case 1:
                    inventario.mostrarCatalogo(null);
                    break;
                case 2:
                    filtrarPorCategoria(inventario);
                    break;
                case 3:
                    agregarAlCarrito(inventario, carrito);
                    break;
                case 4:
                    carrito.mostrarCarrito();
                    break;
                case 5:
                    if (carrito.estaVacio()) {
                        System.out.println("No agrego productos al carrito. Hasta pronto.");
                    } else {
                        carrito.mostrarFactura();
                        System.out.println("Gracias por su compra!");
                    }
                    break;
            }
        }
    }

    static Inventario cargarCatalogo() {
        Inventario inv = new Inventario();
        inv.agregarProducto(new ProductoElectronico("Laptop", 1500, 5, "Tecnologia", 24));
        inv.agregarProducto(new ProductoElectronico("Monitor", 300, 8, "Tecnologia", 12));
        inv.agregarProducto(new ProductoElectronico("Mouse", 25, 20, "Tecnologia", 6));
        inv.agregarProducto(new ProductoAlimenticio("Arroz 1kg", 3.50, 50, "Alimentos", LocalDate.now().plusMonths(8)));
        inv.agregarProducto(new ProductoAlimenticio("Leche 1L", 2.20, 30, "Alimentos", LocalDate.now().plusWeeks(2)));
        inv.agregarProducto(new Producto("Cuaderno", 4, 40, "Papeleria"));
        inv.agregarProducto(new Producto("Lapiz", 0.80, 100, "Papeleria"));
        return inv;
    }

    // "Login" simulado con el email del cliente
    static Cliente iniciarSesion() {
        String email;
        while (true) {
            System.out.print("Ingrese su email para iniciar: ");
            email = sc.nextLine().trim();
            if (email.contains("@") && email.contains(".")) {
                break;
            }
            System.out.println("Email invalido.");
        }
        String nombre;
        do {
            System.out.print("Ingrese su nombre: ");
            nombre = sc.nextLine().trim();
        } while (nombre.isEmpty());
        return new Cliente(nombre, email);
    }

    static void filtrarPorCategoria(Inventario inventario) {
        ArrayList<String> categorias = inventario.getCategorias();
        System.out.println("Categorias disponibles:");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ". " + categorias.get(i));
        }
        int opcion = leerEntero("Elija una categoria: ", 1, categorias.size());
        inventario.mostrarCatalogo(categorias.get(opcion - 1));
    }

    static void agregarAlCarrito(Inventario inventario, Carrito carrito) {
        inventario.mostrarCatalogo(null);
        int numero = leerEntero("Numero del producto: ", 1, inventario.getCantidadProductos());
        Producto producto = inventario.getProducto(numero - 1);
        int cantidad = leerEntero("Cantidad: ", 1, 1000);

        if (carrito.agregarProducto(producto, cantidad)) {
            System.out.println("Agregado: " + cantidad + " x " + producto.getNombre());
        } else {
            System.out.println("Stock insuficiente. Disponible: " + producto.getStock());
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
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }
}
