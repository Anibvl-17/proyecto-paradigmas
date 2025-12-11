package controlador;

import vista.*;
import modelo.*;

public class ControladorVerHorarios {
    VistaHorarioRec vista;
    GestorHorarioRecoleccion modelo;
    
    public ControladorVerHorarios(GestorHorarioRecoleccion modelo, VistaHorarioRec vista) {
        this.vista = vista;
        this.modelo = modelo;
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
    }
}
