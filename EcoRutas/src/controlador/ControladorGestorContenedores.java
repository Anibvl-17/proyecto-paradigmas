package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorGestorContenedores {
    private VistaGestionContenedores vista;
    private GestorContenedor modelo;
    
    private VistaMensajes vistaMensajes;

    public ControladorGestorContenedores(GestorContenedor modelo, VistaGestionContenedores vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        // Actualiza el texto descriptivo con el ID del punto
        vista.getLabelIdPunto().setText(String.valueOf(modelo.getIdPunto()));
        
        vista.setVisible(true);
        
        // Botones para crear, actualizar y eliminar contenedores
        vista.getBtnAgregar().addActionListener(e -> agregarContenedor());
        vista.getBtnActualizar().addActionListener(e -> actualizarContenedor());
        vista.getBtnEliminar().addActionListener(e -> eliminarContenedor());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
        
        cargarContenedores();
    }
    
    private void limpiarFormulario() {
        // Selecciona el primer item de los combo box
        vista.getComboBoxTipo().setSelectedIndex(0);
        vista.getComboBoxEstado().setSelectedIndex(0);
        vista.getComboBoxColor().setSelectedIndex(0);
        
        // Limpia el texto de los textfields
        vista.getTxtCapacidadMaxima().setText("");
        vista.getTxtCapacidadActual().setText("");
    }
    
    private void agregarContenedor() {
        int id = calcularId();
        String tipo = (String) vista.getComboBoxTipo().getSelectedItem();
        String estado = (String) vista.getComboBoxEstado().getSelectedItem();
        String color = (String) vista.getComboBoxColor().getSelectedItem();
        
        try {
            int capacidadMaxima = Integer.parseInt(vista.getTxtCapacidadMaxima().getText().trim());
            int capacidadActual = Integer.parseInt(vista.getTxtCapacidadActual().getText().trim());
            
            modelo.agregarContenedor(new Contenedor(id, tipo, capacidadMaxima, capacidadActual, estado, color));
            listarContenedores();
            archivarContenedores();
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: La capacidad máxima y actual deben ser números.");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
    }

    private void actualizarContenedor() {
        int id = obtenerId();
        
        // Si el id es -1, ya se mostró un mensaje de error y no continua con la operacion
        if (id == -1) return;
        
        String tipo = (String) vista.getComboBoxTipo().getSelectedItem();
        String estado = (String) vista.getComboBoxEstado().getSelectedItem();
        String color = (String) vista.getComboBoxColor().getSelectedItem();
        
        try {
            int capacidadMaxima = Integer.parseInt(vista.getTxtCapacidadMaxima().getText().trim());
            int capacidadActual = Integer.parseInt(vista.getTxtCapacidadActual().getText().trim());
            
            if (!modelo.actualizarContenedorPorId(id, new Contenedor(id, tipo, capacidadMaxima, capacidadActual, estado, color))) {
                vistaMensajes.mostrarError(null, "Error: El contenedor con ID " + id + " no existe.");
                return;
            }
            
            listarContenedores();
            archivarContenedores();
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: La capacidad máxima y actual deben ser números.");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }

        vistaMensajes.mostrarInfo(null, "Contenedor con ID " + id + " actualizado exitosamente");
        listarContenedores();
        archivarContenedores();
    }

    private void eliminarContenedor() {
        int id = obtenerId();

        if (!modelo.eliminarContenedorPorId(id)) {
            vistaMensajes.mostrarError(null, "Error: El contenedor con ID " + id + " no existe.");
            return;
        }

        vistaMensajes.mostrarInfo(null, "El contenedor se eliminó exitosamente.");
        listarContenedores();
        archivarContenedores();
    }

    private void listarContenedores() {
        DefaultTableModel m = (DefaultTableModel) vista.getTablaContenedores().getModel();
        m.setNumRows(0);

        for (Contenedor c : modelo.listarContenedores()) {
            m.addRow(new Object[]{c.getId(), c.getTipo(), c.getEstado(), c.getColor(), c.getCapacidadMaxima(), c.getCapacidadActual()});
        }
    }

    // Calcula la id de los puntos de acuerdo al ultimo punto de la lista.
    private int calcularId() {
        int cantidadPuntos = modelo.listarContenedores().size();

        if (cantidadPuntos == 0) {
            return 1;
        }

        return modelo.listarContenedores().get(cantidadPuntos - 1).getId() + 1;
    }

    // Obtiene el id del text field txtId
    // Se usa para evitar escribir varias veces el mismo codigo
    private int obtenerId() {
        try {
            int id = Integer.parseInt(vista.getTxtId().getText().trim());
            
            if (id < 1) throw new NumberFormatException();
            
            return id;
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: El id debe ser un número positivo");
            return -1;
        }
    }

    private void archivarContenedores() {
        try {
            modelo.archivar();
        } catch (IOException ex) {
            vistaMensajes.mostrarError(null, "Error: No se pudo guardar los contenedores");

        }
    }

    private void cargarContenedores() {
        try {
            modelo.cargarArchivo();
            listarContenedores();
        } catch (FileNotFoundException e) {
            // No pasa nada, no se han guardado contenedores antes en este caso.
        } catch (IOException ex) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar los contenedores");
        }
    }
}
