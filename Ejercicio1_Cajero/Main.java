import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Banco banco = new Banco();
        banco.agregarCuenta(new CuentaAhorro("Carlos", 500, "1234"));
        banco.agregarCuenta(new CuentaCorriente("Maria", 1000, "4321", 300));
        banco.agregarCuenta(new CuentaBancaria("Pedro", 200, "1111"));

        System.out.println("===== CAJERO AUTOMATICO =====");

        // 1. Identificar la cuenta por el titular
        CuentaBancaria cuenta = null;
        while (cuenta == null) {
            System.out.print("Ingrese el titular (o escriba 'salir'): ");
            String titular = sc.nextLine().trim();
            if (titular.equalsIgnoreCase("salir")) {
                System.out.println("Hasta pronto.");
                return;
            }
            cuenta = banco.buscarCuenta(titular);
            if (cuenta == null) {
                System.out.println("No existe una cuenta con ese titular.");
            }
        }

        if (cuenta.isBloqueada()) {
            System.out.println("Esta cuenta esta bloqueada.");
            return;
        }

        // 2. Validar el PIN (maximo 3 intentos)
        int intentos = 0;
        boolean acceso = false;
        while (intentos < 3 && !acceso) {
            System.out.print("Ingrese su PIN: ");
            String pin = sc.nextLine().trim();
            if (banco.validarPin(cuenta.getTitular(), pin)) {
                acceso = true;
            } else {
                intentos++;
                System.out.println("PIN incorrecto. Intentos restantes: " + (3 - intentos));
            }
        }

        if (!acceso) {
            cuenta.bloquear();
            System.out.println("Cuenta bloqueada por exceder los intentos permitidos.");
            return;
        }

        System.out.println("\nBienvenido/a, " + cuenta.getTitular() + " (" + cuenta.getTipo() + ")");
        mostrarMenu(banco, cuenta);
    }

    static void mostrarMenu(Banco banco, CuentaBancaria cuenta) {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Transferir dinero");
            System.out.println("5. Consultar movimientos");
            System.out.println("6. Salir");
            System.out.print("Elija una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida, ingrese un numero del 1 al 6.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.printf("Su saldo actual es: $%.2f%n", cuenta.consultarSaldo());
                    break;
                case 2:
                    double retiro = leerMonto("Monto a retirar: ");
                    if (cuenta.retirar(retiro)) {
                        System.out.printf("Retiro exitoso. Nuevo saldo: $%.2f%n", cuenta.consultarSaldo());
                    } else {
                        System.out.println("Fondos insuficientes para retirar ese monto.");
                    }
                    break;
                case 3:
                    double deposito = leerMonto("Monto a depositar: ");
                    if (cuenta.depositar(deposito)) {
                        System.out.printf("Deposito exitoso. Nuevo saldo: $%.2f%n", cuenta.consultarSaldo());
                    }
                    break;
                case 4:
                    transferir(banco, cuenta);
                    break;
                case 5:
                    mostrarMovimientos(cuenta);
                    break;
                case 6:
                    System.out.println("Gracias por usar el cajero. Hasta pronto.");
                    break;
                default:
                    System.out.println("Opcion invalida, ingrese un numero del 1 al 6.");
            }
        }
    }

    static void transferir(Banco banco, CuentaBancaria cuenta) {
        System.out.print("Titular de la cuenta destino: ");
        String nombreDestino = sc.nextLine().trim();
        CuentaBancaria destino = banco.buscarCuenta(nombreDestino);

        if (destino == null) {
            System.out.println("La cuenta destino no existe.");
            return;
        }
        if (destino == cuenta) {
            System.out.println("No puede transferirse a su propia cuenta.");
            return;
        }

        double monto = leerMonto("Monto a transferir: ");
        if (cuenta.transferir(destino, monto)) {
            System.out.printf("Transferencia exitosa a %s. Nuevo saldo: $%.2f%n",
                    destino.getTitular(), cuenta.consultarSaldo());
        } else {
            System.out.println("Fondos insuficientes para transferir ese monto.");
        }
    }

    static void mostrarMovimientos(CuentaBancaria cuenta) {
        ArrayList<String> movimientos = cuenta.getMovimientos();
        if (movimientos.isEmpty()) {
            System.out.println("Aun no hay movimientos registrados.");
            return;
        }
        System.out.println("Ultimos movimientos:");
        for (int i = 0; i < movimientos.size(); i++) {
            System.out.println((i + 1) + ". " + movimientos.get(i));
        }
    }

    // Pide un monto positivo y repite hasta que sea valido
    static double leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double monto = Double.parseDouble(sc.nextLine().trim());
                if (monto > 0) {
                    return monto;
                }
                System.out.println("El monto debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}
