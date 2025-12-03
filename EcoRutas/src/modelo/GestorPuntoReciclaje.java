package modelo;

import java.util.ArrayList;

public class GestorPuntoReciclaje {
    private ArrayList<PuntoReciclaje> puntos;
    
    //Contructor
    public GestorPuntoReciclaje() {
        puntos = new ArrayList<>();
    }
    
    //Metodos
    public ArrayList<PuntoReciclaje> ListarPuntos() {
        return puntos;
    }
    
    public PuntoReciclaje buscarPuntoPorId(String id){
        for (PuntoReciclaje punto : puntos) {
            if(punto.getId().equals(id)){
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
    
    public boolean eliminarPuntoPorId(String id){
        PuntoReciclaje punto = buscarPuntoPorId(id);
        if(punto == null) return false;
        
        puntos.remove(punto);
        return true;
    }
    
    public boolean actualizarPuntoPorId(String id, PuntoReciclaje nuevosDatos){
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
}
