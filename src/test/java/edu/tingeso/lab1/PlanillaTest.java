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
    void calcularSiJustificativoTest_Exists(){
        dataEntity.setJustificado(true);
        assertEquals(-1, planillaService.calcularSiJustificativo(dataEntity));
    }
    @Test
    void calcularSiJustificativoTest_NotExists(){
        dataEntity.setJustificado(false);
        dataEntity.setHora(Time.valueOf("09:00:00"));
        assertEquals(0, planillaService.calcularSiJustificativo(dataEntity));
    }
    @Test
    void calcularSiJustificativoTest_NotExistsAndGreaterThan70(){
        dataEntity.setJustificado(false);
        dataEntity.setHora(Time.valueOf("09:30:00"));
        assertEquals(1, planillaService.calcularSiJustificativo(dataEntity));
    }
    @Test
    void calcularSiHorasExtrasTest_AlreadyExists(){
        dataEntity.setHorasExtras(4);
        assertEquals(-1, planillaService.calcularSiHorasExtras(dataEntity));
    }
    @Test
    void calcularSiHorasExtrasTest_Exists(){
        dataEntity.setHorasExtras(0);
        dataEntity.setHora(Time.valueOf("19:00:00"));
        assertEquals(1, planillaService.calcularSiHorasExtras(dataEntity));
    }
    @Test
    void calcularSiHorasExtrasTest_NotExists(){
        dataEntity.setHorasExtras(0);
        dataEntity.setHora(Time.valueOf("18:00:00"));
        assertEquals(0, planillaService.calcularSiHorasExtras(dataEntity));
    }
}
