package modelo;

import java.util.ArrayList;

public class GestorContenedor {
    private ArrayList<Contenedor> contenedores;
    
    //Constructor
    public GestorContenedor() {
        contenedores = new ArrayList<>();
    }
   
    //Metodos
    public ArrayList<Contenedor> listarContenedores() {
        return contenedores;
    }
    
    public boolean agregarContenedor(Contenedor t){
        if(buscarContenedorPorId(t.getId()) != null){
            return false;
        }
        contenedores.add(t);
        return true;
    }
    
    public  Contenedor buscarContenedorPorId(String id){
        for (Contenedor contenedor : contenedores) {
            if(contenedor.getId().equalsIgnoreCase(id))
                return contenedor;
        }
        return null;
    }
    
    public boolean eliminarContenedorPorId(String id){
        Contenedor contenedor = buscarContenedorPorId(id);
        if(contenedor == null) return false;
        contenedores.remove(contenedor);
        return true;
    }

    public boolean actualizarContenedorPorId(String id, Contenedor nuevosDatos){
        Contenedor contenedor = buscarContenedorPorId(id);
        if(contenedor == null) return false;
        
        contenedor.setTipo(nuevosDatos.getTipo());
        contenedor.setCapacidadMaxima(nuevosDatos.getCapacidadMaxima());
        contenedor.setCapacidadActual(nuevosDatos.getCapacidadActual());
        contenedor.setEstado(nuevosDatos.getEstado());
        contenedor.setColor(nuevosDatos.getColor());

        
        return true;
    }
    
    public int capacidadDisponibleporTipo(String tipo, int umbral){
        Contenedor contenedor = buscarPorTipo(tipo);
        if(contenedor == null) return -1;
        
        return contenedor.getCapacidadMaxima() - contenedor.getCapacidadActual();
    }

    public Contenedor buscarPorTipo(String tipo){
        for (Contenedor contenedor : contenedores) {
            if(contenedor.getTipo().equalsIgnoreCase(tipo)){
                return contenedor;
            }
        }
        return null;
    }
}
