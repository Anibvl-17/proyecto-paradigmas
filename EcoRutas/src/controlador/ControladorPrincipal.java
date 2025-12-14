package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorPrincipal {
    private VistaPrincipal vistaPrincipal;
    private GestorPuntoReciclaje gestorPuntos;
    
    private VistaMensajes vistaMensajes;
    
    public ControladorPrincipal(GestorPuntoReciclaje gestorPuntos, VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.gestorPuntos = gestorPuntos;
        vistaMensajes = new VistaMensajes();
    }
    
    public void iniciar() {
        // pack() y setLocationRelativeTo(null) permiten centrar la vista.
        vistaPrincipal.pack();
        vistaPrincipal.setLocationRelativeTo(null);
        
        vistaPrincipal.setVisible(true);
        
        vistaPrincipal.getBtnAccesoFuncionario().addActionListener(e -> mostrarAccesoFuncionarios());
        vistaPrincipal.getBtnHorarios().addActionListener(e -> mostrarHorarios());
        
        vistaPrincipal.getBtnSolicitud().addActionListener(e -> mostrarVistaSolicitud());
        vistaPrincipal.getBtnBuscarPunto().addActionListener(e -> mostrarContenedoresPorIdPunto());
        vistaPrincipal.getBtnActualizar().addActionListener(e -> listarPuntos());
        
        listarPuntos();
    }
    
    public void mostrarVistaSolicitud() {
        VistaNuevaSolicitud vista = new VistaNuevaSolicitud();
        GestorSolicitudes modelo = new GestorSolicitudes();
        ControladorNuevaSolicitud controlador = new ControladorNuevaSolicitud(modelo, vista);
        controlador.iniciar();
    }
    
    private void mostrarHorarios() {
        VistaHorarioRec vista = new VistaHorarioRec();
        GestorHorarioRecoleccion modelo = new GestorHorarioRecoleccion();
        ControladorHorarios controlador = new ControladorHorarios(modelo, vista);
        controlador.iniciar();
    }
    
    private void mostrarAccesoFuncionarios() {
        VistaAccesoFuncionario vista = new VistaAccesoFuncionario();
        ControladorAcceso controlador = new ControladorAcceso(vista);
        controlador.iniciar();
    }
    
    private void mostrarContenedoresPorIdPunto() {
        try {
            int idPunto = Integer.parseInt(vistaPrincipal.getTxtIDPunto().getText());
            
            GestorContenedor gestorContenedor = new GestorContenedor(idPunto);
            VistaContenedores vistaContenedores = new VistaContenedores();
            ControladorContenedor controladorContenedor = new ControladorContenedor(gestorContenedor, vistaContenedores);
            
            controladorContenedor.iniciar();
            
        } catch (NumberFormatException e) {
            vistaMensajes.mostrarError(null, "Error: El ID del punto de reciclaje debe ser un número.");
        }
    }
    
    private void listarPuntos() {
        try {
            gestorPuntos.cargarArchivo();
            
            DefaultTableModel m = (DefaultTableModel) vistaPrincipal.getTablaPuntos().getModel();
            m.setNumRows(0);
            
            for (PuntoReciclaje p : gestorPuntos.ListarPuntos()) {
                m.addRow(new Object[] {p.getId(), p.getNombre(), p.getDireccion(), p.getSector(), p.isDisponible()} );
            }
        } catch (FileNotFoundException e) {
            // Archivo no encontrado, significa que no se han guardado puntos
        } catch (IOException e) {
            vistaMensajes.mostrarError(null, "Error: No se pudo cargar los puntos de reciclaje");
        }
    }
}
