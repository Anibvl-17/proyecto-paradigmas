package modelo;

public class Contenedor {
    private String id;
    private String tipo;
    private int capacidadMaxima;
    private int capacidadActual;
    private String  estado;
    private String color;
    
    //Constructor
    public Contenedor(String id, String tipo, int capacidadMaxima, int capacidadActual, String estado, String color) {
        this.setId(id);
        this.setTipo(tipo);
        this.setCapacidadMaxima(capacidadMaxima);
        this.setCapacidadActual(capacidadActual);
        this.setEstado(estado);
        this.setColor(color);
    }
    
    //Getters
    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public String getEstado() {
        return estado;
    }

    public String getColor() {
        return color;
    }
    
    //Setters
    public void setId(String id) {
        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("Error: La id no puede estar vacia.");
        }
        this.id = id;
    }

    public void setTipo(String tipo) {
        if(!tipo.equalsIgnoreCase("Papel y carton") && !tipo.equalsIgnoreCase("Plastico") && !tipo.equalsIgnoreCase("Vidrio") && !tipo.equalsIgnoreCase("Organico")){
            throw new IllegalArgumentException("Error: Solo puede escoger tipo entre (Papel y carton, Plastico, Vidrio, Organico).");
        }
        this.tipo = tipo;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        if(capacidadMaxima < 0){
            throw new IllegalArgumentException("Error: Capcidad máxima no puede contener números negativos.");
        }
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setCapacidadActual(int capacidadActual) {
        if(capacidadActual < 0){
            throw new IllegalArgumentException("Error: Capacidad actual no puede contener números negativos.");
        }
        this.capacidadActual = capacidadActual;
    }

    public void setEstado(String estado) {
        if(!estado.equalsIgnoreCase("Disponible") && !estado.equalsIgnoreCase("Lleno") && !estado.equalsIgnoreCase("Malo")){
            throw new IllegalArgumentException("Error: Solo puede escoger estado entre (Disponible, Lleno, Malo).");
        }
        this.estado = estado;
    }

    public void setColor(String color) {
        if(!color.equalsIgnoreCase("Verde")&& !color.equalsIgnoreCase("Azul") && !color.equalsIgnoreCase("Amarillo") && !color.equalsIgnoreCase("Cafe")){
            throw new IllegalArgumentException("Error: Solo puede escoger color entre (Verde, Azul, Amarillo, Cafe).");
        }
        this.color = color;
    }
    
    //Metodo
    public boolean estaLleno() {
        return capacidadActual == capacidadMaxima;
    }
    
    @Override
    public String toString() {
        return "\n==== TipoContenedor ====="
                + "\nID:" + id 
                + "\nTipo:" + tipo 
                + "\nCapacidad Maxima:" + capacidadMaxima 
                + "\nCapacidad Actual:" + capacidadActual
                + "\nEstado:" + estado 
                + "\nColor:" + color
                + "\nEsta Lleno:" + (estaLleno() ? "Sí" : "No")
                + "-----------------------------------------";
    }
    
}
