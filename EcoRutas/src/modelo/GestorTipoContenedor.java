package modelo;

import java.util.ArrayList;
import utils.AutoLoggerAvanzado;

public class GestorTipoContenedor {
    private ArrayList<TipoContenedor> contenedores;
    
    //Constructor
    public GestorTipoContenedor() {
        contenedores = new ArrayList<>();
    }
    
    //Getters
    public ArrayList<TipoContenedor> getContenedores() {
        return contenedores;
    }
    
    //Metodos
    public void agregarContenedor(TipoContenedor t){
        contenedores.add(t);
        AutoLoggerAvanzado.log("Contenedor agregado: " + t.getId() + "|" + t.getTipo() + "|" + t.getCapacidadMaxima() + "|" + t.getCapacidadActual() + "|" + t.getEstado() + "|" + t.getColor());            
    }
    
    public  TipoContenedor buscarContenedorPorId(String id){
        for (TipoContenedor contenedor : contenedores) {
            if(contenedor.getId().equalsIgnoreCase(id))
                return contenedor;
        }
        return null;
    }
    
    public boolean eliminarContenedor(String id){
        TipoContenedor contenedor = buscarContenedorPorId(id);
        if(contenedor == null) return false;
        contenedores.remove(contenedor);
        return true;
    }
    
    //Falta
    public void listarContenedores(){
        
    }

    public void actualizarContenedor(){
        
    }
    
    public void buscarPorEstado(){
        
    }

    public void buscarPorTipo(){
        
    }
}
