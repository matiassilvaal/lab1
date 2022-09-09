package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.entities.PlanillaEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import edu.tingeso.lab1.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

@Service
public class PlanillaService {
    @Autowired
    PlanillaRepository planillaRepository;
    @Autowired
    DataRepository dataRepository;

    public ArrayList<PlanillaEntity> obtenerPlanilla(){
        return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
    }

    public boolean ingresarJustificativo(Date fecha, String rut){
        DataEntity info = dataRepository.findByRutAndFecha(fecha,rut);
        if(info.getHora().compareTo(Time.valueOf("09:10:00")) >= 0){
            dataRepository.updateJustificativo(rut);
            return true;
        }
        else{
            return false;
        }
    }

}
