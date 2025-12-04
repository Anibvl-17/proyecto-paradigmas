package modelo;

public class HorarioRecoleccion {
    private String id;
    private String sector;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String recolectorId; //Aca mejor llamar al modelo Usuario
    
    //Constructor
    public HorarioRecoleccion(String id, String sector, String diaSemana, String horaInicio, String horaFin, String recolectorId) {
        this.setId(id);
        this.setSector(sector);
        this.setDiaSemana(diaSemana);
        this.setHoraInicio(horaInicio);
        this.setHoraFin(horaFin);
        this.setRecolectorId(recolectorId);
    }
    
    //Getters
    public String getId() {
        return id;
    }

    public String getSector() {
        return sector;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getRecolectorId() {
        return recolectorId;
    }
    
    //Setters
    public void setId(String id) {
        if (id == null || id.isEmpty()){
            throw new IllegalArgumentException("Error: El ID no puede estar vacío.");
        }
        this.id = id;
    }

    public void setSector(String sector) {
        if (!sector.equalsIgnoreCase("Urbano") && !sector.equalsIgnoreCase("Rural") && !sector.equalsIgnoreCase("Industrial")) {
            throw new IllegalArgumentException("Error: Sector solo puede ser (Urbano, Rural, Industrial).");
        }
        this.sector = sector;
    }

    public void setDiaSemana(String diaSemana) {
        String[] dias = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};

        boolean valido = false;
        for (String dia : dias) {
            if (dia.equalsIgnoreCase(diaSemana)) {
                valido = true;
                break;
            }
        }
        if (!valido){
            throw new IllegalArgumentException("Error: Día inválido. Use Lunes a Domingo.");
        }

        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(String horaInicio) {
        if (horaInicio == null || horaInicio.isEmpty()){
            throw new IllegalArgumentException("Error: Hora de inicio no puede estar vacía.");
        }
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        if (horaFin == null || horaFin.isEmpty()){
            throw new IllegalArgumentException("Error: Hora de fin no puede estar vacía.");
        }
        this.horaFin = horaFin;
    }

    public void setRecolectorId(String recolectorId) {
        if (recolectorId == null || recolectorId.isEmpty()){
            throw new IllegalArgumentException("Error: El ID del recolector no puede estar vacío."); 
        }
        this.recolectorId = recolectorId;
    }
    
    //Metodos
    @Override
    public String toString() {
        return "\n==== Horario de Recolección ===="
             + "\nID: " + id
             + "\nSector: " + sector
             + "\nDía Semana: " + diaSemana
             + "\nHora Inicio: " + horaInicio
             + "\nHora Fin: " + horaFin
             + "\nRecolector ID: " + recolectorId
             + "\n-----------------------------------------";
    }
}
