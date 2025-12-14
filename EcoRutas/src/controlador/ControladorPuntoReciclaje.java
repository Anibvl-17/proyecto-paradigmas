package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ControladorPuntoReciclaje {

    VistaGestionPuntos vista;
    GestorPuntoReciclaje modelo;

    VistaMensajes vistaMensajes;

    public ControladorPuntoReciclaje(GestorPuntoReciclaje modelo, VistaGestionPuntos vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
    }

    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);

        vista.setVisible(true);

        vista.getBtnGestionContenedores().addActionListener(e -> mostrarGestionContenedores());

        // Botones para crear, actualizar y eliminar puntos
        vista.getBtnCrear().addActionListener(e -> agregarPunto());
        vista.getBtnActualizar().addActionListener(e -> actualizarPunto());
        vista.getBtnEliminar().addActionListener(e -> eliminarPunto());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
        
        cargarPuntos();
    }
    
    private void limpiarFormulario() {
        vista.getComboBoxSector().setSelectedIndex(0);
        vista.getTxtDireccion().setText("");
        vista.getTxtNombre().setText("");
        
        vista.getTxtNombre().requestFocus();
    }

    private void mostrarGestionContenedores() {
        int id = obtenerId();

        if (id == -1) {
            return;
        }

        if (modelo.buscarPuntoPorId(id) == null) {
            vistaMensajes.mostrarError(null, "Error: El punto con ID " + id + " no existe.");
            return;
        }

        GestorContenedor modeloContenedor = new GestorContenedor(id);
        VistaGestionContenedores vistaGestionContenedores = new VistaGestionContenedores();
        ControladorGestorContenedores controladorGestorContenedores = new ControladorGestorContenedores(modeloContenedor, vistaGestionContenedores);
        controladorGestorContenedores.iniciar();
    }

    private void agregarPunto() {
        int id = calcularId();
        String nombre = vista.getTxtNombre().getText();
        String direccion = vista.getTxtDireccion().getText();
        String sector = (String) vista.getComboBoxSector().getSelectedItem();
        boolean disponible = true;
        GestorContenedor gestorContenedor = new GestorContenedor(id);
        try {
            modelo.agregarPunto(new PuntoReciclaje(id, nombre, direccion, sector, disponible, gestorContenedor));
            listarPuntos();
            archivarPuntos();
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
    }

    private void actualizarPunto() {
        int id = obtenerId();
        
        // Si el id es -1, el mensajes ya se mostró en la función obtenerId()
        if (id < 1) return;
        
        String nombre = vista.getTxtNombre().getText();
        String direccion = vista.getTxtDireccion().getText();
        String sector = (String) vista.getComboBoxSector().getSelectedItem();
        boolean disponible = true;
        GestorContenedor gestorContenedor = new GestorContenedor(id);
        
        try {
            if (!modelo.actualizarPuntoPorId(id, new PuntoReciclaje(id, nombre, direccion, sector, disponible, gestorContenedor))) {
                vistaMensajes.mostrarError(null, "Error: El punto con ID " + id + " no existe.");
                return;
            }

            vistaMensajes.mostrarInfo(null, "Punto con ID " + id + " actualizado exitosamente");
            listarPuntos();
            archivarPuntos();
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
        

    }

    private void eliminarPunto() {
        int id = obtenerId();

        if (!modelo.eliminarPuntoPorId(id)) {
            // Si el id es -1, el mensajes ya se mostró en la función obtenerId()
            if (id == -1) return;
            
            vistaMensajes.mostrarError(null, "Error: El punto con ID " + id + " no existe.");
            return;
        }

        vistaMensajes.mostrarInfo(null, "El punto se eliminó exitosamente.");
        listarPuntos();
        archivarPuntos();
    }

    private void listarPuntos() {
        DefaultTableModel m = (DefaultTableModel) vista.getTabla().getModel();
        m.setNumRows(0);

        for (PuntoReciclaje p : modelo.ListarPuntos()) {
            m.addRow(new Object[]{p.getId(), p.getNombre(), p.getDireccion(), p.getSector(), p.isDisponible() ? "Si" : "No"});
        }
    }

    // Calcula la id de los puntos de acuerdo al ultimo punto de la lista.
    private int calcularId() {
        int cantidadPuntos = modelo.ListarPuntos().size();

        if (cantidadPuntos == 0) {
            return 1;
        }

        return modelo.ListarPuntos().get(cantidadPuntos - 1).getId() + 1;
    }

    // Obtiene el id del text field txtId
    // Se usa para evitar escribir varias veces el mismo codigo
    private int obtenerId() {
        try {
            int id = Integer.parseInt(vista.getTxtId().getText());
            
            if (id < 1) throw new NumberFormatException();
            
            return id;
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: El id debe ser un número positivo");
            return -1;
        }
    }

    private void archivarPuntos() {
        try {
            modelo.archivar();
        } catch (IOException ex) {
            vistaMensajes.mostrarError(null, "Error: No se pudo guardar los puntos");

        }
    }

    private void cargarPuntos() {
        try {
            modelo.cargarArchivo();
            listarPuntos();
        } catch (FileNotFoundException e) {
            // No pasa nada, no se han guardado puntos antes en este caso.
        } catch (IOException ex) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar los puntos");
        }
    }
}
