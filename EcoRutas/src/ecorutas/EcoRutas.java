package ecorutas;

import vista.*;
import modelo.*;
import controlador.*;

public class EcoRutas {

    public static void main(String[] args) {
        GestorPuntoReciclaje modelo = new GestorPuntoReciclaje();
        VistaPrincipal vista = new VistaPrincipal();
        ControladorPrincipal controlador = new ControladorPrincipal(modelo, vista);
        
        controlador.iniciar();
    }
    
}
