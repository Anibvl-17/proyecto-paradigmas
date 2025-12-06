package modelo;

public class PuntoReciclaje {
    private String id;
    private String nombre;
    private String direccion;
    private String sector;
    private boolean disponible;
    private GestorContenedor contenedores;
    
    //Constructor
    public PuntoReciclaje(String id, String nombre, String direccion, String sector, boolean disponible, GestorContenedor contenedores) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDireccion(direccion);
        this.setSector(sector);
        this.setDisponible(disponible);
        this.setContenedores(contenedores);
    }
    
    //Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getSector() {
        return sector;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public GestorContenedor getContenedores() {
        return contenedores;
    }
    
    //Setters
    public void setId(String id) {
        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("Error: La id no puede estar vacia.");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("Error: El nombre no puede estar vacio.");
        }
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        if(direccion == null || direccion.isEmpty()){
            throw new IllegalArgumentException("Error: La direccion no puede estar vacia.");
        }
        this.direccion = direccion;
    }

    public void setSector(String sector) {
        if (!sector.equalsIgnoreCase("Urbano") && !sector.equalsIgnoreCase("Rural") && !sector.equalsIgnoreCase("Industrial")) {       
        throw new IllegalArgumentException("Error: Solo puede escoger sector entre (Urbano, Rural, Industrial).");
        }
        this.sector = sector;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setContenedores(GestorContenedor contenedores) {
        if (contenedores == null) {
        throw new IllegalArgumentException("Error: El punto de reciclaje debe tener un gestor de contenedores valido.");
        }
        this.contenedores = contenedores;
    }
    
    //Metodos
    public int contenedoresDisponibles() {
    int count = 0;
    for (Contenedor contenedor : contenedores.listarContenedores()) {
        if (!contenedor.getEstado().equalsIgnoreCase("Malo")) {
            count++;
        }
    }
    return count;
    }
    
    public int totalContenedores(){
        return contenedores.listarContenedores().size();
    }
    
    @Override
    public String toString() {
        return "\n==== Punto de Reciclaje ===="
             + "\nID: " + id
             + "\nNombre: " + nombre
             + "\nDirección: " + direccion
             + "\nSector: " + sector
             + "\nDisponible: " + (disponible ? "Sí" : "No")
             + "\nTotal Contenedores: " + totalContenedores()
             + "\nContenedores Disponibles:" + contenedoresDisponibles()
             + "\n-----------------------------------------";
    }
}
