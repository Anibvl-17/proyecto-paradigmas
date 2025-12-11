package controlador;

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
            modelo.agregarSolicitud(new Solicitud(calcularId(), nombre, mensaje, tipoSolicitud));
            vistaMensajes.mostrarInfo(null, "Solicitud creada exitosamente");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
    }
    
    private void limpiarFormulario() {
        // Obtiene el primer item del combo box
        Object primerItem = vista.getComboBoxTipo().getItemAt(0);
        
        // Selecciona el primer item de los combo box
        vista.getComboBoxTipo().getModel().setSelectedItem(primerItem);
        
        // Limpia el texto de los textfields
        vista.getTxtNombre().setText("");
        vista.getTxtMensaje().setText("");
        vista.getTxtNombre().requestFocus();
    }
    
    // Calcula la id de la solicitud segun la última solicitud de la lista.
    private int calcularId() {
        int cantidadHorarios = modelo.listarSolicitudes().size();

        if (cantidadHorarios == 0) {
            return 1;
        }

        return modelo.listarSolicitudes().get(cantidadHorarios - 1).getId() + 1;
    }
}
