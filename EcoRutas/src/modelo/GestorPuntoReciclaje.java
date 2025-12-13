package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorPuntoReciclaje {
    private ArrayList<PuntoReciclaje> puntos;
    
    private String nombreArchivoPuntos;
    
    //Contructor
    public GestorPuntoReciclaje() {
        puntos = new ArrayList<>();
        nombreArchivoPuntos = "puntos_reciclaje.txt";
    }
    
    //Metodos
    public ArrayList<PuntoReciclaje> ListarPuntos() {
        return puntos;
    }
    
    public PuntoReciclaje buscarPuntoPorId(int id){
        for (PuntoReciclaje punto : puntos) {
            if(punto.getId() == id){
                return punto;
            }
        }
        return null;
    }
    
    public boolean agregarPunto(PuntoReciclaje punto){
        if(buscarPuntoPorId(punto.getId()) != null){
            return false;
        }
        
        puntos.add(punto);
        return true;
    }
    
    public boolean eliminarPuntoPorId(int id){
        PuntoReciclaje punto = buscarPuntoPorId(id);
        if(punto == null) return false;
        
        puntos.remove(punto);
        return true;
    }
    
    public boolean actualizarPuntoPorId(int id, PuntoReciclaje nuevosDatos){
        PuntoReciclaje punto = buscarPuntoPorId(id);
        if(punto == null) return false;
        
        punto.setNombre(nuevosDatos.getNombre());
        punto.setDireccion(nuevosDatos.getDireccion());
        punto.setSector(nuevosDatos.getSector());
        punto.setDisponible(nuevosDatos.isDisponible());
        punto.setContenedores(nuevosDatos.getContenedores());

        return true;
    }
    
    public  PuntoReciclaje buscarPorDireccion(String direccion) {
        for (PuntoReciclaje punto : puntos) {
            if(punto.getDireccion().equalsIgnoreCase(direccion)){
                return punto;
            }
        }
        return null;
    }

    public PuntoReciclaje buscarPuntosDisponibles() {
        for (PuntoReciclaje punto : puntos) {
            if(punto.isDisponible()){
                return punto;
            }
        }
        return null;
    }

    public int totalContenedoresPorDireccion(String direccion) {
        PuntoReciclaje punto = buscarPorDireccion(direccion);
        if(punto == null) return -1;
        
        return punto.totalContenedores();
    }
        
    public void archivar() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivoPuntos))) {
            for (PuntoReciclaje p : puntos) {
                // No se guarda el gestor de contenedores aqui, ya que después se busca por el ID (el nombre del archivo tiene el ID)
                // Cada GestorContenedores tiene un ID de un punto de reciclaje.
                bw.write(p.getId() + ";" + p.getNombre() + ";" + p.getDireccion() + ";" + p.getSector() + ";" + p.isDisponible());
                bw.newLine();
            }
            bw.close();
        }
    }
    
    public void cargarArchivo() throws FileNotFoundException, IOException {
        puntos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivoPuntos))) {
            String linea;
            while((linea = br.readLine()) != null) {
                String partes[] = linea.split(";");
                puntos.add(
                    new PuntoReciclaje(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3],
                        Boolean.parseBoolean(partes[4]),
                        cargarContenedoresPorIDPunto(Integer.parseInt(partes[0]))
                    )
                );
            }
            br.close();
        }
    }
    
    // Obtiene la lista de contenedores de un determinado punto (segun id)
    public GestorContenedor cargarContenedoresPorIDPunto(int idPunto) throws IOException {
        GestorContenedor gestor = new GestorContenedor(idPunto);
        gestor.cargarArchivo();
        return gestor;
    }
}
