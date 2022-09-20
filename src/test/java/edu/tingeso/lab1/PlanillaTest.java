package edu.tingeso.lab1;
import edu.tingeso.lab1.entities.DataEntity;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.repositories.EmpleadoRepository;
import edu.tingeso.lab1.repositories.PlanillaRepository;
import edu.tingeso.lab1.services.EmpleadoService;
import edu.tingeso.lab1.services.PlanillaService;
import org.junit.jupiter.api.Test;
import java.sql.Time;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import edu.tingeso.lab1.repositories.DataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.tingeso.lab1.services.DataService;
import java.sql.Date;

@ExtendWith(MockitoExtension.class)
class PlanillaTest {
    @Mock private PlanillaRepository planillaRepository;
    @Mock private DataRepository dataRepository;
    @Mock private EmpleadoRepository empleadoRepository;
    PlanillaService planillaService;
    DataService dataService;
    EmpleadoService empleadoService;
    DataEntity dataEntity = new DataEntity();
    EmpleadoEntity empleadoEntity = new EmpleadoEntity();
    @BeforeEach void setUp() {
        this.dataService = new DataService(dataRepository);
        this.empleadoService = new EmpleadoService(empleadoRepository);
        this.planillaService = new PlanillaService(planillaRepository, dataService, empleadoService);

    }
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

    @Test
    void ingresarJustificativoTest(){
        dataEntity.setJustificado(false);
        dataEntity.setHora(Time.valueOf("09:30:00"));
        planillaService.ingresarJustificativo(Date.valueOf("2021-05-01"), "12345678-9");
        dataService.ingresarJustificativo("12345678-9",Date.valueOf("2021-05-01"));
        verify(dataRepository).updateJustificativo("12345678-9", Date.valueOf("2021-05-01"));
    }

    @Test
    void ingresarHorasExtrasTest(){
        dataEntity.setHorasExtras(0);
        dataEntity.setHora(Time.valueOf("19:00:00"));
        planillaService.ingresarHorasExtras(Date.valueOf("2021-05-01"), "12345678-9");
        dataService.ingresarHorasExtras("12345678-9",Date.valueOf("2021-05-01"),1);
        verify(dataRepository).updateHorasExtras("12345678-9", Date.valueOf("2021-05-01"), 1);
    }

    @Test
    void calcularHorasExtrasTest(){
        empleadoEntity.setRut("12345678-9");
        empleadoEntity.setCategoria("A");
        empleadoEntity.setNombres("Juan");
        empleadoEntity.setApellidos("Perez");
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2021-05-01"));
        empleadoEntity.setFechaDeNacimiento(Date.valueOf("1990-05-01"));
        planillaService.calcularHorasExtras(empleadoEntity);
        verify(dataRepository).sumHorasExtras(empleadoEntity.getRut());
    }
}
