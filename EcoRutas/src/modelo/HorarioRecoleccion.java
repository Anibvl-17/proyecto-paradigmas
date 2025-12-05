package modelo;

public class HorarioRecoleccion {
    private int id;
    private String sector;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String tipoResiduo;
    
    //Constructor
    public HorarioRecoleccion(int id, String sector, String diaSemana, String horaInicio, String horaFin, String tipoResiduo) {
        this.setId(id);
        this.setSector(sector);
        this.setDiaSemana(diaSemana);
        this.setHoraInicio(horaInicio);
        this.setHoraFin(horaFin);
        this.setTipoResiduo(tipoResiduo);
    }
    
    //Getters
    public int getId() {
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

    public String getTipoResiduo() {
        return tipoResiduo;
    }
    
    //Setters
    public void setId(int id) {
        if (id < 1){
            throw new IllegalArgumentException("Error: El ID no puede ser menor a 1");
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
        String[] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};

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

    public void setTipoResiduo(String tipoResiduo) {
        if (tipoResiduo == null || tipoResiduo.isEmpty()){
            throw new IllegalArgumentException("Error: El tipo de residuo no puede estar vacío."); 
        }
        
        if (!tipoResiduo.equals("Orgánico") && !tipoResiduo.equals("General")) {
            throw new IllegalArgumentException("Error: El tipo de residuo debe ser General u Orgánico");
        }
        
        this.tipoResiduo = tipoResiduo;
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
             + "\nTipo Residuo: " + tipoResiduo
             + "\n-----------------------------------------";
    }
}
