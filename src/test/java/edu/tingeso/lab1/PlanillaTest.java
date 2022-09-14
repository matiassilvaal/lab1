package edu.tingeso.lab1;
import edu.tingeso.lab1.entities.DataEntity;

import edu.tingeso.lab1.services.PlanillaService;
import org.junit.jupiter.api.Test;


import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanillaTest {
    PlanillaService planillaService = new PlanillaService();
    DataEntity dataEntity = new DataEntity();
    @Test
    public void calcularSiJustificativoTest_Exists(){
        dataEntity.setJustificado(true);
        assertEquals(planillaService.calcularSiJustificativo(dataEntity),-1);
    }
    @Test
    public void calcularSiJustificativoTest_NotExists(){
        dataEntity.setJustificado(false);
        dataEntity.setHora(Time.valueOf("09:00:00"));
        assertEquals(planillaService.calcularSiJustificativo(dataEntity),0);
    }
    @Test
    public void calcularSiJustificativoTest_NotExistsAndGreaterThan70(){
        dataEntity.setJustificado(false);
        dataEntity.setHora(Time.valueOf("09:30:00"));
        assertEquals(planillaService.calcularSiJustificativo(dataEntity),1);
    }
    @Test
    public void calcularSiHorasExtrasTest_AlreadyExists(){
        dataEntity.setHorasExtras(4);
        assertEquals(planillaService.calcularSiHorasExtras(dataEntity),-1);
    }
    @Test
    public void calcularSiHorasExtrasTest_Exists(){
        dataEntity.setHorasExtras(0);
        dataEntity.setHora(Time.valueOf("19:00:00"));
        assertEquals(planillaService.calcularSiHorasExtras(dataEntity),1);
    }
    @Test
    public void calcularSiHorasExtrasTest_NotExists(){
        dataEntity.setHorasExtras(0);
        dataEntity.setHora(Time.valueOf("18:00:00"));
        assertEquals(planillaService.calcularSiHorasExtras(dataEntity),0);
    }
}
