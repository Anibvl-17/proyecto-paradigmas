package controlador;

import modelo.*;
import vista.*;

public class ControladorAcceso {

    VistaAccesoFuncionario vista;

    private String rolInspector = "inspector";
    private String claveInspector = "Inspect0r2025";

    private String rolEncargado = "encargado";
    private String claveEncargado = "Encargad02025";

    public ControladorAcceso(VistaAccesoFuncionario vista) {
        this.vista = vista;
    }

    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);

        vista.getBtnAcceder().addActionListener(e -> verificarAcceso());
    }

    // Verifica la contraseña y despliega la vista de acuerdo al rol selecccionado
    private void verificarAcceso() {
        String rol = vista.getRadioBtnInspector().isSelected() ? rolInspector : rolEncargado;
        String contrasenia = vista.getTxtContrasenia().getText();

        if (rol.equals(rolInspector) && contrasenia.equals(claveInspector)) {
            vista.setVisible(false);
            mostrarVistaInspector();
        } else if (rol.equals(rolEncargado) && contrasenia.equals(claveEncargado)) {
            vista.setVisible(false);
            mostrarVistaEncargado();
        } else {
            new VistaMensajes().mostrarError(null, "Contraseña incorrecta");
        }
    }

    public void mostrarVistaEncargado() {
        GestorHorarioRecoleccion modeloGestorHorario = new GestorHorarioRecoleccion();
        VistaGestionHorarioRec vistaGestorHorario = new VistaGestionHorarioRec();
        ControladorGestorHorarios controladorGestorHorarios = new ControladorGestorHorarios(modeloGestorHorario, vistaGestorHorario);

        controladorGestorHorarios.iniciar();
    }

    public void mostrarVistaInspector() {
        GestorPuntoReciclaje modeloGestorPuntos = new GestorPuntoReciclaje();
        VistaGestionPuntos vistaGestorPuntos = new VistaGestionPuntos();
        ControladorPuntoReciclaje controladorGestorPuntos = new ControladorPuntoReciclaje(modeloGestorPuntos, vistaGestorPuntos);

        controladorGestorPuntos.iniciar();
    }
}
