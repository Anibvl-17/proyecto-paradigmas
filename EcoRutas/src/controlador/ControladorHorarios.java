package controlador;

import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorHorarios {

    private VistaGestionHorarioRec vista;
    private GestorHorarioRecoleccion modelo;
    
    private VistaMensajes vistaMensajes;

    public ControladorHorarios(GestorHorarioRecoleccion modelo, VistaGestionHorarioRec vista) {
        this.vista = vista;
        this.modelo = modelo;
        vistaMensajes = new VistaMensajes();
    }

    public void iniciar() {
        vista.setVisible(true);

        // Asignacion de funciones a botones
        vista.getBtnCrear().addActionListener(e -> agregarHorario());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
        vista.getBtnEliminar().addActionListener(e -> eliminarHorario());
    }

    private void agregarHorario() {
        int id = calcularId();
        String sector = (String) vista.getComboBoxSector().getSelectedItem();
        String diaSemana = (String) vista.getComboBoxDia().getSelectedItem();
        String horaInicio = vista.getTxtHoraInicio().getText();
        String horaFin = vista.getTxtHoraFin().getText();
        String tipoResiduo = (String) vista.getComboBoxTipoResiduo().getSelectedItem();

        try {
            HorarioRecoleccion h = new HorarioRecoleccion(id, sector, diaSemana, horaInicio, horaFin, tipoResiduo);

            modelo.agregarHorario(h);
            listarHorarios();
        } catch (IllegalArgumentException e) {
            new VistaMensajes().mostrarError(null, e.getMessage());
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
        // Obtiene el primer item de los combo box
        Object primerItemSector = vista.getComboBoxSector().getItemAt(0);
        Object primerItemDia = vista.getComboBoxDia().getItemAt(0);
        Object primerItemTipoResiduo = vista.getComboBoxTipoResiduo().getItemAt(0);
        
        // Selecciona el primer item de los combo box
        vista.getComboBoxSector().getModel().setSelectedItem(primerItemSector);
        vista.getComboBoxDia().getModel().setSelectedItem(primerItemDia);
        vista.getComboBoxTipoResiduo().getModel().setSelectedItem(primerItemTipoResiduo);
        
        // Limpia el texto de los textfields
        vista.getTxtHoraInicio().setText("");
        vista.getTxtHoraFin().setText("");
    }

    // Calcula la id de los horarios de acuerdo al ultimo horario de la lista.
    private int calcularId() {
        int cantidadHorarios = modelo.listarHorarios().size();

        if (cantidadHorarios == 0) {
            return 1;
        }

        return modelo.listarHorarios().get(cantidadHorarios - 1).getId() + 1;
    }
    
    private void eliminarHorario() {
        try {
            int id = Integer.parseInt(vista.getTxtId().getText());
            
            if (!modelo.eliminarHorarioPorId(id)) {
                vistaMensajes.mostrarError(null, "No existen horarios con id " + id);
                return;
            }
            
            modelo.eliminarHorarioPorId(id);
            listarHorarios(); // Actualiza la lista automaticamente
            vistaMensajes.mostrarInfo(null, "Horario eliminado exitosamente");
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: El ID debe ser un número");
        } catch (IllegalArgumentException e) {
            vistaMensajes.mostrarError(null, e.getMessage());
        }
    }
    
    // Pendientes:
    // - Eliminar
    // - Editar
    // - Filtrar
    // - Cargar desde archivo
    // - Guardar desde archivo
}
