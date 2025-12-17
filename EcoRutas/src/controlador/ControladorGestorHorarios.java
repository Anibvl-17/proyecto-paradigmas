package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorGestorHorarios {

    private VistaGestionHorarioRec vista;
    private GestorHorarioRecoleccion modelo;
    
    private VistaMensajes vistaMensajes;
    
    private boolean mostrarTipoOrganico;
    private boolean mostrarTipoGeneral;
    private boolean mostrarSectorUrbano;
    private boolean mostrarSectorRural;
    private boolean mostrarSectorIndustrial;

    public ControladorGestorHorarios(GestorHorarioRecoleccion modelo, VistaGestionHorarioRec vista) {
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
        
        // Asignacion de funciones a botones
        vista.getBtnCrear().addActionListener(e -> agregarHorario());
        vista.getBtnActualizar().addActionListener( e -> actualizarHorario());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
        vista.getBtnEliminar().addActionListener(e -> eliminarHorario());
        
        // Alterna los filtros de tipo de recoleccion
        vista.getCbTipoGeneral().addActionListener(e -> alternarTipoGeneral());
        vista.getCbTipoOrganico().addActionListener(e -> alternarTipoOrganico());
       
        // Alterna los filtros de sector
        vista.getCbSectorRural().addActionListener(e -> alternarSectorRural());
        vista.getCbSectorUrbano().addActionListener(e -> alternarSectorUrbano());
        vista.getCbSectorIndustrial().addActionListener(e -> alternarSectorIndustrial());
        
        vista.getTxtHoraInicio().requestFocus();

        cargarHorarios();
    }

    private void agregarHorario() {
        int id = calcularId();
        String sector = (String) vista.getComboBoxSector().getSelectedItem();
        String diaSemana = (String) vista.getComboBoxDia().getSelectedItem();
        String tipoResiduo = (String) vista.getComboBoxTipoResiduo().getSelectedItem();

        try {
            int horaInicio = Integer.parseInt(vista.getTxtHoraInicio().getText());
            int horaFin = Integer.parseInt(vista.getTxtHoraFin().getText());
            HorarioRecoleccion h = new HorarioRecoleccion(id, sector, diaSemana, horaInicio, horaFin, tipoResiduo);

            modelo.agregarHorario(h);
            listarHorarios();
            archivarHorarios();
            limpiarFormulario();
            vistaMensajes.mostrarInfo("Horario agregado exitosamente");
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError("La hora de inicio y hora de fin deben ser números");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(e.getMessage());
        }
    }
    
    private void actualizarHorario() {
        int id = obtenerId();
        
        // Si el id es -1, ya se mostró un mensaje de error y no continua con la operacion
        if (id == -1) return;
        
        HorarioRecoleccion horarioActual = modelo.buscarPorId(id);
        
        if (horarioActual == null) {
            vistaMensajes.mostrarError("Error: El horario con ID " + id + " no existe");
            return;
        }
        
        String sector = (String) vista.getComboBoxSector().getSelectedItem();
        String diaSemana = (String) vista.getComboBoxDia().getSelectedItem();
        String tipoResiduo = (String) vista.getComboBoxTipoResiduo().getSelectedItem();
        
        try {
            int horaInicio = Integer.parseInt(vista.getTxtHoraInicio().getText().trim());
            int horaFin = Integer.parseInt(vista.getTxtHoraFin().getText().trim());
            
            modelo.actualizarHorarioPorId(id, new HorarioRecoleccion(id, sector, diaSemana, horaInicio, horaFin, tipoResiduo));
            archivarHorarios();
            listarHorarios();
            limpiarFormulario();
            vistaMensajes.mostrarInfo("Horario actualizado exitosamente");
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError("La hora de inicio y hora de fin deben ser números");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(e.getMessage());
        }
    }
    
    private void alternarTipoGeneral() {
        mostrarTipoGeneral = !mostrarTipoGeneral;
        listarHorarios();
    }
    
    private void alternarTipoOrganico() {
        mostrarTipoOrganico = !mostrarTipoOrganico;
        listarHorarios();
    }
    
    private void alternarSectorRural() {
        mostrarSectorRural = !mostrarSectorRural;
        listarHorarios();
    }
    
    private void alternarSectorUrbano() {
        mostrarSectorUrbano = !mostrarSectorUrbano;
        listarHorarios();
    }
    
    private void alternarSectorIndustrial() {
        mostrarSectorIndustrial = !mostrarSectorIndustrial;
        listarHorarios();
    }

    private void listarHorarios() {
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
            
            m.addRow(new Object[]{
                h.getId(),
                h.getSector(),
                h.getDiaSemana(),
                h.getHoraInicio(),
                h.getHoraFin(),
                h.getTipoResiduo()
            });
        }
    }
    
    private void limpiarFormulario() {
        // Selecciona el primer item de los combo box
        vista.getComboBoxSector().setSelectedIndex(0);
        vista.getComboBoxDia().setSelectedIndex(0);
        vista.getComboBoxTipoResiduo().setSelectedIndex(0);
        
        // Limpia el texto de los textfields
        vista.getTxtHoraInicio().setText("");
        vista.getTxtHoraFin().setText("");
        
        vista.getTxtHoraInicio().requestFocus();
    }

    // Calcula la id de los horarios de acuerdo al ultimo horario de la lista.
    private int calcularId() {
        int cantidadHorarios = modelo.listarHorarios().size();

        if (cantidadHorarios == 0) {
            return 1;
        }

        return modelo.listarHorarios().get(cantidadHorarios - 1).getId() + 1;
    }
    
    // Obtiene el id del text field txtId
    // Se usa para evitar escribir varias veces el mismo codigo
    private int obtenerId() {
        try {
            int id = Integer.parseInt(vista.getTxtId().getText().trim());
            
            if (id < 1) throw new NumberFormatException();

            return id;
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError("Error: El ID debe ser un número positivo");
            return -1;
        }
    }
    
    private void eliminarHorario() {
        int id = obtenerId();
        
        // Si el id es -1, ya se mostró un mensaje de error y no continua con la operacion
        if (id == -1) return;
        
        if (!modelo.eliminarHorarioPorId(id)) {
            vistaMensajes.mostrarError("No existen horarios con id " + id);
            return;
        }

        listarHorarios(); // Actualiza la lista automaticamente
        archivarHorarios(); 
        vista.getTxtId().setText("");
        vistaMensajes.mostrarInfo("Horario eliminado exitosamente");
    }
    
    private void cargarHorarios() {
        try {
            modelo.cargarArchivo();
            listarHorarios();
        } catch (FileNotFoundException e) {
            // No se ha creado el archivo, por lo tanto no hay horarios
        } catch (IOException e) {
            vistaMensajes.mostrarError("Error: No se pudo cargar los horarios de recolección");
        }
    }
    
    private void archivarHorarios() {
        try {
            modelo.archivar();
        } catch (IOException e) {
            vistaMensajes.mostrarError("Error: No se pudo guardar los horarios de recolección");
        }
    }
}
