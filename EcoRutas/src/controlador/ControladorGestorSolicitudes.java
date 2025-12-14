package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorGestorSolicitudes {
    GestorSolicitudes modelo;
    VistaGestionSolicitudes vista;
    
    // Se utilizan para filtrar por tipo de solicitud
    boolean sugerenciasVisibles;
    boolean reclamosVisibles;
    
    VistaMensajes vistaMensajes;
    
    public ControladorGestorSolicitudes(GestorSolicitudes modelo, VistaGestionSolicitudes vista) {
        this.modelo = modelo;
        this.vista = vista;
        vistaMensajes = new VistaMensajes();
        sugerenciasVisibles = true;
        reclamosVisibles = true;
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
        
        vista.getBtnEliminar().addActionListener(e -> eliminarSolicitud());
        vista.getCheckboxReclamos().addActionListener(e -> alternarFiltroReclamos());
        vista.getCheckboxSugerencias().addActionListener(e -> alternarFiltroSugerencias());
        
        cargarSolicitudes();
    }
    
    private void alternarFiltroReclamos() {
        reclamosVisibles = !reclamosVisibles;
        listarSolicitudes();
    }
    
    private void alternarFiltroSugerencias() {
        sugerenciasVisibles = !sugerenciasVisibles;
        listarSolicitudes();
    }
    
    private void eliminarSolicitud() {
        try {
            int id = Integer.parseInt(vista.getTxtId().getText().trim());
            
            if (id < 1) throw new NumberFormatException();
            
            if (!modelo.eliminarSolicitudPorId(id)) {
                vistaMensajes.mostrarError(null, "Error: La solicitud con ID " + id + " no existe");
            }
            
            archivarSolicitudes();
            listarSolicitudes();
            vista.getTxtId().setText("");
            vistaMensajes.mostrarInfo(null, "Solicitud eliminada exitosamente");
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: El ID debe ser un número positivo");
        }
    }
    
    private void listarSolicitudes() {
        DefaultTableModel m = (DefaultTableModel) vista.getTablaSolicitudes().getModel();
        m.setNumRows(0);
        
        for (Solicitud s : modelo.listarSolicitudes()) {
            // Filtra las solicitudes según las opciones seleccionadas en la vista
            if (s.getTipoSolicitud().equalsIgnoreCase("reclamo") && !reclamosVisibles) continue;
            if (s.getTipoSolicitud().equalsIgnoreCase("sugerencia") && !sugerenciasVisibles) continue;
            
            m.addRow(new Object[] {s.getId(), s.getNombreSolicitante(), s.getMensaje(), s.getTipoSolicitud()});
        }
    }
    
    private void cargarSolicitudes() {
        try {
            modelo.cargarArchivo();
            listarSolicitudes();
        } catch (FileNotFoundException e) {
            // No se han guardado solicitudes, no hay nada que cargar
        } catch (IOException e) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar las solicitudes");
        }
    }
    
    private void archivarSolicitudes() {
        try {
            modelo.archivar();
        } catch (IOException e) {
            vistaMensajes.mostrarError(null, "Error: No se pudo guardar las solicitudes");
        }
    }
}
