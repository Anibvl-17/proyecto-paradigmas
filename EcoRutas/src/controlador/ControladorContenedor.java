package controlador;

import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ControladorContenedor {
    private GestorContenedor modelo;
    private VistaContenedores vista;
    
    private VistaMensajes vistaMensajes;
    
    private String tipoVisible;
    private int umbralCapacidadMinima;
    
    public ControladorContenedor(GestorContenedor modelo, VistaContenedores vista) {
        this.modelo = modelo;
        this.vista = vista;
        vistaMensajes = new VistaMensajes();
        tipoVisible = "todos";
        umbralCapacidadMinima = 0; // el limite maximo es 25 mil litros
    }
    
    public void iniciar() {
        vista.pack();
        vista.setLocationRelativeTo(null);
        
        listarContenedores();
        
        vista.getLabelId().setText(String.valueOf(modelo.getIdPunto()));
        vista.getBtnFiltrar().addActionListener(e -> filtrar());
        
        vista.setVisible(true);
        
        vista.getTxtUmbralCapacidad().requestFocus();
    }
    
    private void filtrar() {
        tipoVisible = (String) vista.getComboBoxTipo().getSelectedItem();
        
        try {
            String strUmbral = vista.getTxtUmbralCapacidad().getText().trim();
            
            if (!strUmbral.isBlank()) {
                umbralCapacidadMinima = Integer.parseInt(strUmbral);
                if (umbralCapacidadMinima < 0 || umbralCapacidadMinima > 25000) throw new NumberFormatException();
            } else {
                umbralCapacidadMinima = 0;
            }
            
            listarContenedores();
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError("Error: El umbral debe ser entre 0 y 25000");
        }
    }
    
    private void listarContenedores() {
        try {
            modelo.cargarArchivo();
            
            DefaultTableModel m = (DefaultTableModel) vista.getTablaContenedores().getModel();
            m.setNumRows(0);
            
            for (Contenedor c : modelo.listarContenedores()) {
                if (!tipoVisible.equalsIgnoreCase("todos") && !tipoVisible.equalsIgnoreCase(c.getTipo())) continue;
                if (umbralCapacidadMinima > c.getCapacidadMaxima() - c.getCapacidadActual()) continue;
                
                m.addRow(new Object[]{c.getTipo(), c.getEstado(), c.getColor(), c.getCapacidadMaxima(), c.getCapacidadMaxima() - c.getCapacidadActual()});
            }
            
        } catch (IOException e) {
            vistaMensajes.mostrarError("Error: No se pudo cargar los contenedores");
        }
    }
    
}
