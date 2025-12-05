package controlador;

import vista.*;
import modelo.*;

public class ControladorPrincipal {
    private VistaPrincipal vistaPrincipal;
    private GestorPuntoReciclaje gestorPuntos;
    
    private String rolInspector = "inspector";
    private String claveInspector = "Inspect0r2025";
    
    private String rolEncargado = "encargado";
    private String claveEncargado = "Encargad02025";
    
    public ControladorPrincipal(GestorPuntoReciclaje gestorPuntos, VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.gestorPuntos = gestorPuntos;
    }
    
    public void iniciar() {
        vistaPrincipal.setVisible(true);
        
        vistaPrincipal.getBtnAccesoFuncionario().addActionListener(e -> mostrarAccesoFuncionarios());
    }
    
    private void mostrarAccesoFuncionarios() {
        VistaAccesoFuncionario vista = new VistaAccesoFuncionario();
        vista.setVisible(true);
        
        vista.getBtnAcceder().addActionListener(e -> verificarAcceso(vista));
    }
    
    // Verifica la contraseña y despliega la vista de acuerdo al rol selecccionado
    private void verificarAcceso(VistaAccesoFuncionario vistaAcceso) {
       String rol = vistaAcceso.getRadioBtnInspector().isSelected() ? rolInspector : rolEncargado;
       String contrasenia = vistaAcceso.getTxtContrasenia().getText();
       
       if (rol.equals(rolInspector) && contrasenia.equals(claveInspector)) {
           new VistaMensajes().mostrarInfo(null, "Acceso correcto a inspector.");
       } else if (rol.equals(rolEncargado) && contrasenia.equals(claveEncargado)) {
           vistaAcceso.setVisible(false);
           
           GestorHorarioRecoleccion modeloGestionHorario = new GestorHorarioRecoleccion();
           VistaGestionHorarioRec vistaGestionHorario = new VistaGestionHorarioRec();
           ControladorHorarios controladorHorarios = new ControladorHorarios(modeloGestionHorario, vistaGestionHorario);
           
           controladorHorarios.iniciar();
       } else {
           new VistaMensajes().mostrarError(null, "Contraseña incorrecta");
       }
   }
}
