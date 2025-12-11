package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorContenedor {
    private ArrayList<Contenedor> contenedores;
    private String nombreArchivo;
    
    // Agregamos id del punto de reciclaje
    private int idPunto;
    
    //Constructor
    public GestorContenedor(int idPunto) {
        contenedores = new ArrayList<>();
        this.idPunto = idPunto;
        nombreArchivo = idPunto + "_" + "contenedores.txt";
    }

    public int getIdPunto() {
        return idPunto;
    }
   
    //Metodos
    public ArrayList<Contenedor> listarContenedores() {
        return contenedores;
    }
    
    public  Contenedor buscarContenedorPorId(String id){
        for (Contenedor contenedor : contenedores) {
            if(contenedor.getId().equals(id))
                return contenedor;
        }
        return null;
    }
    
    public boolean agregarContenedor(Contenedor t){
        if(buscarContenedorPorId(t.getId()) != null){
            return false;
        }
        
        contenedores.add(t);
        return true;
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
    
    public void archivar() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Contenedor c : contenedores) {
                bw.write(c.getId() + ";" + c.getTipo() + ";" + c.getCapacidadMaxima() + ";" + c.getCapacidadActual() + ";" + c.getEstado() + ";" + c.getColor());
                bw.newLine();
            }
            bw.close();
        }
    }
    
    // Guarda los contenedores, el archivo incluye el id del punto de reciclaje
    // para luego poder recuperar la lista de cada punto
    public void cargarArchivo() throws FileNotFoundException, IOException {
        contenedores.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while((linea = br.readLine()) != null) {
                String partes[] = linea.split(";");
                contenedores.add(new Contenedor(partes[0], partes[1], Integer.parseInt(partes[2]), Integer.parseInt(partes[3]), partes[4], partes[5]));
            }
            br.close();
        }
    }
}