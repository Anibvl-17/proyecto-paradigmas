package controlador;

import vista.*;
import modelo.*;

public class ControladorPrincipal {
    private VistaPrincipal vistaPrincipal;
    private GestorPuntoReciclaje gestorPuntos;
    
    public ControladorPrincipal(GestorPuntoReciclaje gestorPuntos, VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.gestorPuntos = gestorPuntos;
    }
    
    public void iniciar() {
        // pack() y setLocationRelativeTo(null) permiten centrar la vista.
        vistaPrincipal.pack();
        vistaPrincipal.setLocationRelativeTo(null);
        
        vistaPrincipal.setVisible(true);
        
        vistaPrincipal.getBtnAccesoFuncionario().addActionListener(e -> mostrarAccesoFuncionarios());
        vistaPrincipal.getBtnHorarios().addActionListener(e -> mostrarHorarios());
        
        vistaPrincipal.getBtnSolicitud().addActionListener(e -> mostrarVistaSolicitud());
    }
    
    public void mostrarVistaSolicitud() {
        VistaNuevaSolicitud vista = new VistaNuevaSolicitud();
        GestorSolicitudes modelo = new GestorSolicitudes();
        ControladorNuevaSolicitud controlador = new ControladorNuevaSolicitud(modelo, vista);
        controlador.iniciar();
    }
    
    private void mostrarHorarios() {
        VistaHorarioRec vista = new VistaHorarioRec();
        vista.setVisible(true);
    }
    
    private void mostrarAccesoFuncionarios() {
        VistaAccesoFuncionario vista = new VistaAccesoFuncionario();
        ControladorAcceso controlador = new ControladorAcceso(vista);
        controlador.iniciar();
    }
}
