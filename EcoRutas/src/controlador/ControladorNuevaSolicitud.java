package controlador;

import java.io.IOException;
import modelo.*;
import vista.*;

public class ControladorNuevaSolicitud {
    GestorSolicitudes modelo;
    VistaNuevaSolicitud vista;
    
    VistaMensajes vistaMensajes;
    
    public ControladorNuevaSolicitud(GestorSolicitudes modelo, VistaNuevaSolicitud vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
        
        vista.getBtnCrear().addActionListener(e -> agregarSolicitud());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
    }
    
    public void agregarSolicitud() {
        String nombre = vista.getTxtNombre().getText();
        String tipoSolicitud = (String) vista.getComboBoxTipo().getSelectedItem();
        String mensaje = vista.getTxtMensaje().getText();
        
        try {
            modelo.cargarArchivo(); // Carga las solicitudes previas para no sobreescribirlas
            modelo.agregarSolicitud(new Solicitud(calcularId(), nombre, mensaje, tipoSolicitud));
            modelo.archivar();
            limpiarFormulario();
            vistaMensajes.mostrarInfo("Solicitud creada exitosamente");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(e.getMessage());
        } catch (IOException ex) {
            vistaMensajes.mostrarError("Error al guardar solicitud.");
        } 
    }
    
    private void limpiarFormulario() {
        // Selecciona el primer item de los combo box
        vista.getComboBoxTipo().setSelectedIndex(0);
        
        // Limpia el texto de los textfields
        vista.getTxtNombre().setText("");
        vista.getTxtMensaje().setText("");
        vista.getTxtNombre().requestFocus();
    }
    
    // Calcula la id de la solicitud segun la última solicitud de la lista.
    private int calcularId() {
        int cantidadSolicitudes = modelo.listarSolicitudes().size();

        if (cantidadSolicitudes == 0) {
            return 1;
        }

        return modelo.listarSolicitudes().get(cantidadSolicitudes - 1).getId() + 1;
    }
}
