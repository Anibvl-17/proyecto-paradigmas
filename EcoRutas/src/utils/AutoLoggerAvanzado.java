package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.InetAddress;

public class AutoLoggerAvanzado {
    private static final String LOG_DIR = "log";
    private static final String AUDIT_DIR = "auditoria";
    private static final String LOG_FILE = LOG_DIR + "/registro.txt";
    private static final String AUDIT_FILE = AUDIT_DIR + "/actividad.txt";

    static {
        try {
            crearCarpeta(LOG_DIR);
            crearCarpeta(AUDIT_DIR);
            inicializarArchivo(LOG_FILE);
            inicializarArchivo(AUDIT_FILE);
            log("=== Inicio del programa ===");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearCarpeta(String nombre) {
        File f = new File(nombre);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    private static void inicializarArchivo(String ruta) throws IOException {
        File f = new File(ruta);
        if (!f.exists()) {
            f.createNewFile();
        }
    }

    public static void log(String mensaje) {
        escribir(LOG_FILE, mensaje);
        escribir(AUDIT_FILE, mensaje);
    }

    private static void escribir(String archivo, String mensaje) {
        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            String ip = obtenerIP();
            
            bw.write("[" + fecha + "] [IP=" + ip + "] " + mensaje);
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String obtenerIP() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            return "IP_DESCONOCIDA";
        }
    }
}

