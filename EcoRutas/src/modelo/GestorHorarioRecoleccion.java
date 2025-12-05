package modelo;

import java.util.ArrayList;

public class GestorHorarioRecoleccion {
    private ArrayList<HorarioRecoleccion> horarios;
    
    //Constructor
    public GestorHorarioRecoleccion() {
        horarios = new ArrayList<>();
    }
    
    //Metodos
    public ArrayList<HorarioRecoleccion> listarHorarios() {
        return horarios;
    }
    
    public HorarioRecoleccion buscarPorId(int id) {
        for (HorarioRecoleccion h : horarios) {
            if(h.getId() == id) {
                return h;
            }
        }
        return null;
    }
    
    public boolean agregarHorario(HorarioRecoleccion h) {
        if(buscarPorId(h.getId()) != null){
            return false;
        }
        horarios.add(h);
        return true;
    }
    
    public boolean eliminarHorarioPorId(int id) {
        HorarioRecoleccion h = buscarPorId(id);
        if(h == null) return false;

        horarios.remove(h);
        return true;
    }
    
    public boolean actualizarHorarioPorId(int id, HorarioRecoleccion nuevosDatos) {
        HorarioRecoleccion original = buscarPorId(id);
        if(original == null) return false;

        original.setSector(nuevosDatos.getSector());
        original.setDiaSemana(nuevosDatos.getDiaSemana());
        original.setHoraInicio(nuevosDatos.getHoraInicio());
        original.setHoraFin(nuevosDatos.getHoraFin());
        original.setTipoResiduo(nuevosDatos.getTipoResiduo());

        return true;
    }
    
    public HorarioRecoleccion buscarHorarioPorSector(String sector) {
        for (HorarioRecoleccion horario : horarios) {
            if(horario.getSector().equalsIgnoreCase(sector)){
                return horario;
            }
        }
        return null;
    }
    
    public HorarioRecoleccion buscarHorarioPorDia(String dia){
        for(HorarioRecoleccion horario : horarios){
            if(horario.getDiaSemana().equalsIgnoreCase(dia)){
                 return horario;
            }
        }
        return null;  
    }
}
