package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorHorarioRecoleccion {
    private ArrayList<HorarioRecoleccion> horarios;
    private String nombreArchivo;
    
    //Constructor
    public GestorHorarioRecoleccion() {
        horarios = new ArrayList<>();
        nombreArchivo = "horarios.txt";
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
    
    public void archivar() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (HorarioRecoleccion h : horarios) {
                bw.write(h.getId() + ";" + h.getSector() + ";" + h.getDiaSemana() + ";" + h.getHoraInicio() + ";" + h.getHoraFin() + ";" + h.getTipoResiduo());
                bw.newLine();
            }
            bw.close();
        }
    }
    
    public void cargarArchivo() throws FileNotFoundException, IOException {
        horarios.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while((linea = br.readLine()) != null) {
                String partes[] = linea.split(";");
                horarios.add(new HorarioRecoleccion(
                        Integer.parseInt(partes[0]), partes[1], partes[2], Integer.parseInt(partes[3]), Integer.parseInt(partes[4]), partes[5]
                ));
            }
            br.close();
        }
    }
}
