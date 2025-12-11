package controlador;

import modelo.*;
import vista.*;

public class ControladorPuntoReciclaje {
    VistaGestionPuntos vista;
    GestorPuntoReciclaje modelo;
    
    public ControladorPuntoReciclaje(GestorPuntoReciclaje modelo, VistaGestionPuntos vista) {
        this.vista = vista;
        this.modelo = modelo;
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
        
        // Asignar funciones a botones
    }
    
    // Desarrollar funciones
}
