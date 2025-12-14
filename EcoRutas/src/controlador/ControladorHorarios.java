package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorHorarios {
    VistaHorarioRec vista;
    GestorHorarioRecoleccion modelo;
    
    VistaMensajes vistaMensajes;
    
    public ControladorHorarios(GestorHorarioRecoleccion modelo, VistaHorarioRec vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
        
        cargarHorarios();
    }
    
    public void listarHorarios() {
        DefaultTableModel m = (DefaultTableModel) vista.getTablaHorarios().getModel();
        m.setNumRows(0);
        
        for (HorarioRecoleccion h : modelo.listarHorarios()) {
            m.addRow(new Object[] {h.getSector(), h.getDiaSemana(), h.getHoraInicio(), h.getHoraFin(), h.getTipoResiduo()});
        }
    }
    
    public void cargarHorarios() {
        try {
            modelo.cargarArchivo();
            listarHorarios();
        } catch (FileNotFoundException e) {
            // No hay horarios guardados
        } catch (IOException e) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar los horarios");
        }
    }
    
    // Pendiente
    // - Filtrar por sector (y por tipo?)
    // - Listar todos
}
