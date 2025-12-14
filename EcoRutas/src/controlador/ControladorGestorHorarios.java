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

    public ControladorGestorHorarios(GestorHorarioRecoleccion modelo, VistaGestionHorarioRec vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
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

        cargarHorario();

        vista.getBtnActualizar().addActionListener(e -> actualizarHorario());
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
            vistaMensajes.mostrarInfo(null, "Horario agregado exitosamente");
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "La hora de inicio y hora de fin deben ser números");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
    }
    
    private void actualizarHorario() {
        int id = obtenerId();
        
        // Si el id es -1, ya se mostró un mensaje de error y no continua con la operacion
        if (id == -1) return;
        
        HorarioRecoleccion horarioActual = modelo.buscarPorId(id);
        
        if (horarioActual == null) {
            vistaMensajes.mostrarError(null, "Error: El horario con ID " + id + " no existe");
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
            vistaMensajes.mostrarInfo(null, "Horario actualizado exitosamente");
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "La hora de inicio y hora de fin deben ser números");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
    }

    private void listarHorarios() {
        DefaultTableModel m = (DefaultTableModel) vista.getTablaHorarios().getModel();
        m.setNumRows(0);

        for (HorarioRecoleccion horario : modelo.listarHorarios()) {
            m.addRow(new Object[]{
                horario.getId(),
                horario.getSector(),
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraFin(),
                horario.getTipoResiduo()
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
            modelo.eliminarHorarioPorId(id);
            listarHorarios(); // Actualiza la lista automaticamente
            archivarHorario();
            vistaMensajes.mostrarInfo(null, "Horario eliminado exitosamente");

            return id;
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: El ID debe ser un número positivo");
            return -1;
        }
    }
    
    private void eliminarHorario() {
        int id = obtenerId();
        
        // Si el id es -1, ya se mostró un mensaje de error y no continua con la operacion
        if (id == -1) return;
        
        if (!modelo.eliminarHorarioPorId(id)) {
            vistaMensajes.mostrarError(null, "No existen horarios con id " + id);
            return;
        }

        modelo.eliminarHorarioPorId(id);
        listarHorarios(); // Actualiza la lista automaticamente
        archivarHorarios(); 
        vista.getTxtId().setText("");
        vistaMensajes.mostrarInfo(null, "Horario eliminado exitosamente");
    }
    
    private void cargarHorarios() {
        try {
            modelo.cargarArchivo();
            listarHorarios();
        } catch (FileNotFoundException e) {
            // No se ha creado el archivo, por lo tanto no hay horarios
        } catch (IOException e) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar los horarios de recolección");
        }
    }
    
    private void archivarHorarios() {
        try {
            modelo.archivar();
        } catch (IOException e) {
            vistaMensajes.mostrarError(null, "Error: No se pudo guardar los horarios de recolección");
        }
    }
    
    private void archivarHorario() {
        try {
            modelo.archivar();
        } catch (IOException ex) {
            vistaMensajes.mostrarError(null, "Error: No se pudo guardar los Horarios");

        }
    }

    private void cargarHorario() {
        try {
            modelo.cargarArchivo();
            listarHorarios();
        } catch (FileNotFoundException e) {
        } catch (IOException ex) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar los Horarios");
        }
    }
    
    // Pendientes:
    // - Filtrar
}
