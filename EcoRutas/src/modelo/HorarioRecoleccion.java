package modelo;

public class HorarioRecoleccion {
    private int id;
    private String sector;
    private String diaSemana;
    private int horaInicio;
    private int horaFin;
    private String tipoResiduo;
    
    //Constructor
    public HorarioRecoleccion(int id, String sector, String diaSemana, int horaInicio, int horaFin, String tipoResiduo) {
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

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraFin() {
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

    // Se debe validar horario entre 8 am y 20 pm (rango de 8 a 20)
    public void setHoraInicio(int horaInicio) {
        if (horaInicio < 8 || horaInicio > 20){
            throw new IllegalArgumentException("Error: Hora de inicio debe ser entre 8 y 20 horas");
        }
        this.horaInicio = horaInicio;
    }

    // Se debe validar que la hora fin debe ser mayor a la hora inicio, y cumplir dentro del rango
    public void setHoraFin(int horaFin) {
        if (horaFin <= horaInicio){
            throw new IllegalArgumentException("Error: Hora de fin debe ser mayor que hora de inicio");
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
