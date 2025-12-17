package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorSolicitudes {
    private ArrayList<Solicitud> solicitudes;
    private String nombreArchivo;
    
    //Constructor
    public GestorSolicitudes() {
        solicitudes = new ArrayList<>();
        nombreArchivo = "solicitudes.txt";
    }
   
    //Metodos
    public ArrayList<Solicitud> listarSolicitudes() {
        return solicitudes;
    }
    
    public Solicitud buscarSolicitudPorId(int id){
        for (Solicitud s : solicitudes) {
            if(s.getId() == id)
                return s;
        }
        return null;
    }
    
    public boolean agregarSolicitud(Solicitud s){
        if(buscarSolicitudPorId(s.getId()) != null){
            return false;
        }
        
        solicitudes.add(s);
        return true;
    }
    
    public boolean eliminarSolicitudPorId(int id){
        Solicitud s = buscarSolicitudPorId(id);
        if(s == null) return false;
        
        solicitudes.remove(s);
        return true;
    }

    public ArrayList<Solicitud> buscarPorTipo(String tipo){
        ArrayList<Solicitud> array = new ArrayList<>();
        for (Solicitud s : solicitudes) {
            if(s.getTipoSolicitud().equalsIgnoreCase(tipo)){
                array.add(s);
            }
        }
        return array;
    }
    
    public void archivar() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Solicitud s : solicitudes) {
                bw.write(s.getId() + ";" + s.getNombreSolicitante() + ";" + s.getMensaje() + ";" + s.getTipoSolicitud());
                bw.newLine();
            }
            bw.close();
        }
    }

    public void cargarArchivo() throws FileNotFoundException, IOException {
        // Verifica si el archivo de solicitudes existe
        // Se agregó para evitar error de que no encuentra el archivo al crear
        // solicitudes cuando no existe ninguna solicitud (no hay archivo)
        if (!new File(nombreArchivo).exists()) return;
        
        solicitudes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while((linea = br.readLine()) != null) {
                String partes[] = linea.split(";");
                solicitudes.add(new Solicitud(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3]));
            }
            br.close();
        }
    }
}