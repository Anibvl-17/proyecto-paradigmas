package vista;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class VistaMensajes {
    public void mostrarInfo(JDialog parent, String mensaje) {
        JOptionPane.showMessageDialog(
                parent, 
                mensaje, 
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void mostrarError(JDialog parent, String mensaje) {
        JOptionPane.showMessageDialog(
                parent, 
                mensaje, 
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
