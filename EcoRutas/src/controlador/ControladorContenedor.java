package controlador;

import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ControladorContenedor {
    private GestorContenedor modelo;
    private VistaContenedores vista;
    
    private VistaMensajes vistaMensajes;
    
    public ControladorContenedor(GestorContenedor modelo, VistaContenedores vista) {
        this.modelo = modelo;
        this.vista = vista;
        vistaMensajes = new VistaMensajes();
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        listarContenedores();
        
        vista.getLabelId().setText(String.valueOf(modelo.getIdPunto()));
        
        vista.setVisible(true);
    }
    
    private void listarContenedores() {
        try {
            modelo.cargarArchivo();
            
            DefaultTableModel m = (DefaultTableModel) vista.getTablaContenedores().getModel();
            m.setNumRows(0);
            
            for (Contenedor c : modelo.listarContenedores()) {
                m.addRow(new Object[]{c.getTipo(), c.getEstado(), c.getColor(), c.getCapacidadMaxima(), c.getCapacidadActual()});
            }
            
        } catch (IOException e) {
            vistaMensajes.mostrarError("Error: No se pudo cargar los contenedores");
        }
    }
    
}
