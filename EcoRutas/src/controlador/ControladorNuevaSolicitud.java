package controlador;

import modelo.GestorSolicitudes;
import vista.VistaNuevaSolicitud;

public class ControladorNuevaSolicitud {
    GestorSolicitudes modelo;
    VistaNuevaSolicitud vista;
    
    public ControladorNuevaSolicitud(GestorSolicitudes modelo, VistaNuevaSolicitud vista) {
        this.vista = vista;
        this.modelo = modelo;
    }
    
    public void iniciar() {
        vista.setVisible(true);
        
        // Definir botones con sus respectivas funciones
    }
    
    // Agregar funciones
}
