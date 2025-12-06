package modelo;

public class Solicitud {
    private int id;
    private String nombreSolicitante;
    private String mensaje;
    private String tipoSolicitud;

    public Solicitud(int id, String nombreSolicitante, String mensaje, String tipoSolicitud) {
        this.setId(id);
        this.setNombreSolicitante(nombreSolicitante);
        this.setMensaje(mensaje);
        this.setTipoSolicitud(tipoSolicitud);
    }

    public int getId() {
        return id;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setId(int id) {
        if (id < 1)
            throw new IllegalArgumentException("El ID de la solicitud debe ser mayor o igual a 1");
        
        this.id = id;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.isBlank())
            throw new IllegalArgumentException("El nombre del solicitante no puede estar vacio");
        
        if (nombreSolicitante.length() < 2)
            throw new IllegalArgumentException("El nombre del solicitante debe tener al menos 2 caracteres");
        
        this.nombreSolicitante = nombreSolicitante;
    }

    public void setMensaje(String mensaje) {
        if (mensaje == null || mensaje.isBlank())
            throw new IllegalArgumentException("El mensaje no puede estar vacio");
        
        if (mensaje.length() < 8)
            throw new IllegalArgumentException("El mensaje debe tener al menos 8 caracteres");
        
        this.mensaje = mensaje;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        if (tipoSolicitud == null || tipoSolicitud.isBlank())
            throw new IllegalArgumentException("El tipo de solicitud no puede estar vacio");
        
        if (!tipoSolicitud.equalsIgnoreCase("reclamo") && !tipoSolicitud.equalsIgnoreCase("sugerencia"))
            throw new IllegalArgumentException("El tipo de solicitud debe ser Reclamo o Sugerencia");
            
        
        this.tipoSolicitud = tipoSolicitud;
    }
    
    
}
