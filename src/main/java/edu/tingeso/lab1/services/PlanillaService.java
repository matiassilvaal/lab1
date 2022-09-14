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

@SuppressWarnings("deprecation")
@Service
public class PlanillaService {

    @Autowired
    PlanillaRepository planillaRepository;
    @Autowired
    DataRepository dataRepository;

    public ArrayList<PlanillaEntity> obtenerPlanilla(){
        return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
    }

    public Integer ingresarJustificativo(Date fecha, String rut){

        DataEntity info = dataRepository.findByRutAndFecha(fecha,rut);
        switch(calcularSiJustificativo(info)){
            case -1:
                return -1;
            case 0:
                return 0;
            case 1:
                dataRepository.updateJustificativo(rut);
                return 1;
        }
        return 0;
    }
    public Integer calcularSiJustificativo(DataEntity info){
        if(info.getJustificado()){
            return -1;
        }
        if(info.getHora().compareTo(Time.valueOf("09:10:00")) >= 0){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Integer ingresarHorasExtras(Date fecha, String rut){
        DataEntity info = dataRepository.findByRutAndFecha(fecha,rut);
        if(info == null){
            return -2;
        }
        if(info.getHorasExtras() > 0){
            return -1;
        }
        Integer calculo = calcularSiHorasExtras(info);
        if(calculo != 0){
            dataRepository.updateHorasExtras(rut,calculo);
            return 1;
        }
        else{
            return 0;
        }
    }
    public Integer calcularSiHorasExtras(DataEntity info){
        if(info.getHorasExtras() > 0){
            return -1;
        }
        int diferencia = info.getHora().getHours()-Time.valueOf("18:00:00").getHours();
        return Math.max(diferencia, 0);
    }
}
