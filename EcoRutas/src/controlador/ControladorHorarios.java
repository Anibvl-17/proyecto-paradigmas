package controlador;

import vista.*;
import modelo.*;

public class ControladorHorarios {
    VistaHorarioRec vista;
    GestorHorarioRecoleccion modelo;
    
    public ControladorHorarios(GestorHorarioRecoleccion modelo, VistaHorarioRec vista) {
        this.vista = vista;
        this.modelo = modelo;
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
    }
}
