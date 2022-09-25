package edu.tingeso.lab1;
import edu.tingeso.lab1.entities.DataEntity;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.entities.PlanillaEntity;
import edu.tingeso.lab1.repositories.EmpleadoRepository;
import edu.tingeso.lab1.repositories.PlanillaRepository;
import edu.tingeso.lab1.services.EmpleadoService;
import edu.tingeso.lab1.services.PlanillaService;
import org.junit.jupiter.api.Test;
import java.sql.Time;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import edu.tingeso.lab1.repositories.DataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.tingeso.lab1.services.DataService;

import java.sql.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class PlanillaTest {
    @Mock private PlanillaRepository planillaRepository;
    @Mock private DataRepository dataRepository;
    @Mock private EmpleadoRepository empleadoRepository;
    PlanillaService planillaService;
    @InjectMocks
    DataService dataService;
    @InjectMocks
    EmpleadoService empleadoService;
    DataEntity dataEntity = new DataEntity();
    EmpleadoEntity empleadoEntity = new EmpleadoEntity();
    @BeforeEach void setUp() {
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
        planillaService.ingresarJustificativo(Date.valueOf("2021-05-01"), "12345678-9");
        verify(dataRepository).findEntrada(Date.valueOf("2021-05-01"), "12345678-9");

    }
    @Test
    void ingresarJustificativoTest2(){
        planillaService.ingresarJustificativo(Date.valueOf("2021-05-01"), "12345678-9");
        assertEquals(-2, planillaService.ingresarJustificativo(Date.valueOf("2021-05-01"), "12345678-9"));
    }
    @Test
    void calcularPlanillaTest(){
        planillaService.calcularPlanilla(List.of(empleadoEntity));
        verify(planillaRepository).deleteAll();
    }
    @Test
    void calcularPlanillaTest2(){
        EmpleadoEntity empleadoEntity1 = new EmpleadoEntity();
        empleadoEntity1.setRut("12345678-9");
        empleadoEntity1.setNombres("Juan");
        empleadoEntity1.setApellidos("Perez");
        empleadoEntity1.setFechaDeIngreso(Date.valueOf("2021-05-01"));
        empleadoEntity1.setFechaDeNacimiento(Date.valueOf("1990-05-01"));
        empleadoEntity1.setCategoria("A");

        PlanillaEntity planillaEntity = new PlanillaEntity();
        planillaEntity.setRut("12345678-9");
        planillaEntity.setApellidosNombres("Perez Juan");
        planillaEntity.setCategoria("A");
        planillaEntity.setDescuentos(0.0);
        planillaEntity.setSueldoBruto(0.0);
        planillaEntity.setCotizacionPrevisional(0.0);
        planillaEntity.setCotizacionSalud(0.0);
        planillaEntity.setSueldoFinal(0.0);
        planillaService.calcularPlanilla(List.of(empleadoEntity1));
        verify(planillaRepository).deleteAll();
    }
    @Test
    void calcularPlanillaTest3(){
        assertEquals(0, planillaService.calcularPlanilla(List.of(empleadoEntity)));
    }
    @Test
    void calcularPlanillaTest4(){
        List<EmpleadoEntity> empleados = empleadoRepository.findAll();
        assertEquals(1, planillaService.calcularPlanilla(empleados));
    }
    @Test
    void obtenerPlanillaTest(){
        planillaService.obtenerPlanilla();
        verify(planillaRepository).findAll();
    }
    @Test
    void guardarPlanillaTest(){
        planillaService.guardarPlanilla(new PlanillaEntity());
        verify(planillaRepository).save(new PlanillaEntity());
    }
    @Test
    void borrarPlanillaTest(){
        planillaService.borrarPlanilla();
        verify(planillaRepository).deleteAll();
    }

    @Test
    void ingresarHorasExtrasTest(){
        planillaService.ingresarHorasExtras(Date.valueOf("2021-05-01"), "12345678-9");
        dataService.ingresarHorasExtras("12345678-9",Date.valueOf("2021-05-01"),1);
        verify(dataRepository).updateHorasExtras("12345678-9", Date.valueOf("2021-05-01"), 1);
    }
    @Test
    void ingresarHorasExtrasTest2(){
        planillaService.ingresarHorasExtras(Date.valueOf("2021-05-01"), "12345678-9");
        assertEquals(-2, planillaService.ingresarHorasExtras(Date.valueOf("2021-05-01"), "12345678-9"));
    }
    @Test
    void sueldoFijoTest(){
        empleadoEntity.setCategoria("A");
        assertEquals(1700000, planillaService.sueldoFijo(empleadoEntity));
    }
    @Test
    void sueldoFijoTest2(){
        empleadoEntity.setCategoria("B");
        assertEquals(1200000, planillaService.sueldoFijo(empleadoEntity));
    }
    @Test
    void sueldoFijoTest3(){
        empleadoEntity.setCategoria("C");
        assertEquals(800000, planillaService.sueldoFijo(empleadoEntity));
    }
    @Test
    void sueldoFijoTest4(){
        empleadoEntity.setCategoria("X");
        assertEquals(0, planillaService.sueldoFijo(empleadoEntity));
    }

    @Test
    void montoAniosDeServicioTest(){
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2021-05-01"));
        assertEquals(0, planillaService.montoAniosDeServicio(empleadoEntity, 100));
    }
    @Test
    void montoAniosDeServicioTest2(){
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2016-01-01"));
        assertEquals(5, planillaService.montoAniosDeServicio(empleadoEntity, 100));
    }
    @Test
    void montoAniosDeServicioTest3(){
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2011-01-01"));
        assertEquals(8, planillaService.montoAniosDeServicio(empleadoEntity, 100));
    }
    @Test
    void montoAniosDeServicioTest4(){
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2006-01-01"));
        assertEquals(11, planillaService.montoAniosDeServicio(empleadoEntity, 100));
    }
    @Test
    void montoAniosDeServicioTest5(){
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2001-01-01"));
        assertEquals(14, planillaService.montoAniosDeServicio(empleadoEntity, 100));
    }
    @Test
    void montoAniosDeServicioTest6(){
        empleadoEntity.setFechaDeIngreso(Date.valueOf("1996-01-01"));
        assertEquals(17, planillaService.montoAniosDeServicio(empleadoEntity, 100));
    }
    @Test
    void calcularHorasExtrasCategoriaTest(){
        empleadoEntity.setCategoria("A");
        assertEquals(0, planillaService.calcularHorasExtrasCategoria(empleadoEntity, null));
    }
    @Test
    void calcularHorasExtrasCategoriaTest2(){
        empleadoEntity.setCategoria("A");
        assertEquals(25000, planillaService.calcularHorasExtrasCategoria(empleadoEntity, 1));
    }
    @Test
    void calcularHorasExtrasCategoriaTest3(){
        empleadoEntity.setCategoria("B");
        assertEquals(20000, planillaService.calcularHorasExtrasCategoria(empleadoEntity, 1));
    }
    @Test
    void calcularDescuentoPorFechaTest(){
        Date[] fechas = new Date[2];
        fechas[0] = Date.valueOf("2021-05-01");
        fechas[1] = Date.valueOf("2021-05-02");
        planillaService.calcularDescuentoPorFecha(empleadoEntity, 100, fechas);
        assertEquals(0, planillaService.calcularDescuentoPorFecha(empleadoEntity, 100, fechas));
    }
    @Test
    void calcularDescuentoPorFecha2(){
        Date[] fechas = new Date[1];
        planillaService.calcularDescuentoPorFecha(empleadoEntity, 100, fechas);
        verify(dataRepository).findEntrada(fechas[0], empleadoEntity.getRut() );
    }
    @Test
    void calcularPorHoraDescuentoTest(){
        dataEntity.setHora(Time.valueOf("08:15:00"));
        assertEquals(1,planillaService.calcularPorHoraDescuento(dataEntity, 100));
    }
    @Test
    void calcularPorHoraDescuentoTest2(){
        dataEntity.setHora(Time.valueOf("08:30:00"));
        assertEquals(3,planillaService.calcularPorHoraDescuento(dataEntity, 100));
    }
    @Test
    void calcularPorHoraDescuentoTest3(){
        dataEntity.setHora(Time.valueOf("08:50:00"));
        assertEquals(6,planillaService.calcularPorHoraDescuento(dataEntity, 100));
    }
    @Test
    void calcularPorHoraDescuentoTest4(){
        dataEntity.setHora(Time.valueOf("09:20:00"));
        dataEntity.setJustificado(false);
        assertEquals(15,planillaService.calcularPorHoraDescuento(dataEntity, 100));
    }
    @Test
    void calcularPorHoraDescuentoTest5(){
        dataEntity.setHora(Time.valueOf("09:20:00"));
        dataEntity.setJustificado(true);
        assertEquals(0,planillaService.calcularPorHoraDescuento(dataEntity, 100));
    }
    @Test
    void calcularPlanillaTest0(){
        planillaService.calcularPlanilla();
        verify(empleadoRepository).findAll();
    }
    @Test
    void calcularSueldoBrutoTest(){
        empleadoEntity.setCategoria("A");
        empleadoEntity.setFechaDeIngreso(Date.valueOf("2021-05-01"));
        assertEquals(1700000, planillaService.calcularSueldoBruto(empleadoEntity));
    }
    @Test
    void calcularHorasExtrasCategoriaTest4(){
        empleadoEntity.setCategoria("C");
        assertEquals(10000, planillaService.calcularHorasExtrasCategoria(empleadoEntity, 1));
    }
    @Test
    void calcularHorasExtrasCategoriaTest5(){
        empleadoEntity.setCategoria("X");
        assertEquals(0, planillaService.calcularHorasExtrasCategoria(empleadoEntity, 1));
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
