package vista;

import javax.swing.JOptionPane;

public class VistaMensajes {
    public void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(
                null, 
                mensaje, 
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                null, 
                mensaje, 
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
