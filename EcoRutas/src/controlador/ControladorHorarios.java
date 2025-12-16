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
    
    boolean mostrarTipoOrganico;
    boolean mostrarTipoGeneral;
    boolean mostrarSectorUrbano;
    boolean mostrarSectorRural;
    boolean mostrarSectorIndustrial;
    
    public ControladorHorarios(GestorHorarioRecoleccion modelo, VistaHorarioRec vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
        
        mostrarTipoOrganico = true;
        mostrarTipoGeneral = true;
        mostrarSectorUrbano = true;
        mostrarSectorRural = true;
        mostrarSectorIndustrial = true;
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        vista.setVisible(true);
        
        // Alterna los filtros de tipo de recoleccion
        vista.getCbTipoGeneral().addActionListener(e -> alternarTipoGeneral());
        vista.getCbTipoOrganico().addActionListener(e -> alternarTipoOrganico());
       
        // Alterna los filtros de sector
        vista.getCbSectorRural().addActionListener(e -> alternarSectorRural());
        vista.getCbSectorUrbano().addActionListener(e -> alternarSectorUrbano());
        vista.getCbSectorIndustrial().addActionListener(e -> alternarSectorIndustrial());
        
        cargarHorarios();
    }
    
    public void alternarTipoGeneral() {
        mostrarTipoGeneral = !mostrarTipoGeneral;
        listarHorarios();
    }
    
    public void alternarTipoOrganico() {
        mostrarTipoOrganico = !mostrarTipoOrganico;
        listarHorarios();
    }
    
    public void alternarSectorRural() {
        mostrarSectorRural = !mostrarSectorRural;
        listarHorarios();
    }
    
    public void alternarSectorUrbano() {
        mostrarSectorUrbano = !mostrarSectorUrbano;
        listarHorarios();
    }
    
    public void alternarSectorIndustrial() {
        mostrarSectorIndustrial = !mostrarSectorIndustrial;
        listarHorarios();
    }
    
    public void listarHorarios() {
        DefaultTableModel m = (DefaultTableModel) vista.getTablaHorarios().getModel();
        m.setNumRows(0);
        
        for (HorarioRecoleccion h : modelo.listarHorarios()) {
            // Filtra los tipos de recoleccion segun los filtros seleccionados
            if (!mostrarTipoGeneral && h.getTipoResiduo().equalsIgnoreCase("general")) continue;
            if (!mostrarTipoOrganico && h.getTipoResiduo().equalsIgnoreCase("orgánico")) continue;
            
            // Filtra los sectores de acuerdo a los filtros seleccionados de sector
            if (!mostrarSectorRural && h.getSector().equalsIgnoreCase("rural")) continue;
            if (!mostrarSectorUrbano && h.getSector().equalsIgnoreCase("urbano")) continue;
            if (!mostrarSectorIndustrial && h.getSector().equalsIgnoreCase("industrial")) continue;
            
            
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
            vistaMensajes.mostrarError("Error: No se pudo cargar los horarios");
        }
    }
}
