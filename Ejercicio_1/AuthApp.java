import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;

public class AuthApp {

    public static void main(String[] args) {
        String username = "";
        String filename = "";
        boolean finished = false;
        boolean validUsername = false;
        boolean validFileName = false;

        Logger logger = Logger.getLogger("AuthApp");
        FileHandler fh;

        try {
            // Configuramos el logger y el filehandler para crear el archivo .log
            fh = new FileHandler("AuthApp.log");
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // Eliminar el ConsoleHandler (salida de logs por consola)
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                rootLogger.removeHandler(handlers[0]);
            }

        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Error de seguridad al crear el archivo .log");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error de entrada/salida al crear el archivo .log");
            System.out.println(e.getMessage());
        }

        System.out.println("BIENVENIDO A AUTHAPP:");
        logger.log(Level.INFO, "Iniciando AuthApp");

        // Bucle para solicitar el nombre de usuario
        while (!validUsername) {
            logger.log(Level.INFO, "Esperando nombre de usuario");
            System.out.println("\nIngrese su nombre de usuario:");
            Scanner scanner = new Scanner(System.in);
            username = scanner.nextLine();
            if (validateUsername(username)) {
                logger.log(Level.INFO, "Usuario " + username + " ha ingresado");
                validUsername = true;
            } else {
                logger.log(Level.WARNING, "El usuario ha ingresado un nombre de usuario inválido");
                System.out.println("El nombre de usuario debe estar formado por 8 letras minúsculas");
            }
        }

        // Bucle con la lógica principal de AuthApp
        while (!finished) {

            // Bucle para solicitar el nombre de archivo
            while (!validFileName) {
                logger.log(Level.INFO, "Esperando nombre de archivo");
                System.out.println("Ingrese el nombre de un archivo:");
                Scanner scanner = new Scanner(System.in);
                filename = scanner.nextLine();
                if (validateFileName(filename)) {
                    logger.log(Level.INFO, "Usuario: " + username + " ha ingresado el nombre de archivo: " + filename);
                    validFileName = true;
                } else {
                    logger.log(Level.WARNING, "El usuario: " + " ha ingresado un nombre de archivo inválido");
                    System.out.println("\nEl nombre de archivo debe estar formado por 1 a 8 caracteres alfanuméricos,\n " +
                            "seguido de un punto y 3 caracteres alfanuméricos");
                }
            }

            // Llamamos a la función contenidoFichero para leer el contenido del archivo
            try {
                fileContent(filename);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al leer el archivo");
                System.out.println(e.getMessage());
            }

            // Preguntamos al usuario si desea salir de AuthApp
            System.out.println("\n¿Desea salir de AuthApp? (S/N)");
            Scanner scanner = new Scanner(System.in);
            String exit = scanner.nextLine();
            if (exit.equals("S") || exit.equals("s")) {
                logger.log(Level.INFO, "El usuario: " + username + " ha salido de AuthApp");
                finished = true;
            } else if (exit.equals("N") || exit.equals("n")) {
                logger.log(Level.INFO, "El usuario: " + username + " ha decidido continuar en AuthApp");
                validUsername = false;
                validFileName = false;
            } else {
                logger.log(Level.WARNING, "El usuario: " + " ha ingresado una opción inválida");
                System.out.println("\nOpción inválida");
            }

        }
    }

    // Función para validar el nombre de usuario
    public static boolean validateUsername(String username) {
        if (username.matches("^[a-z]{8}$")) {
            System.out.println("\nBienvenido " + username);
            return true;
        } else {
            return false;
        }
    }

    // Función para validar el nombre de un archivo
    public static boolean validateFileName(String filename) {
        if (filename.matches("^[a-zA-Z0-9]{1,8}.[a-zA-Z0-9]{3}$")) {
            System.out.println("\nNombre de archivo válido");
            return true;
        } else {
            return false;
        }
    }

    // Función para leer el contenido de un archivo que se encuentre en la carpeta raíz del proyecto
    public static void fileContent(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            System.out.println("Contenido del archivo:\n");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
            System.out.println("\n");
            System.out.println("Fin del archivo");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Archivo no encontrado");
        }
    }
}