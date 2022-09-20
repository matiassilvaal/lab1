package edu.tingeso.lab1;
import static org.mockito.Mockito.verify;

import edu.tingeso.lab1.repositories.DataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.services.DataService;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DataTest {
    @Mock private DataRepository dataRepository;
    DataService dataService = new DataService(dataRepository);
    @Test
    void readIntoListTest() {
        assertNotNull(dataService.readIntoList("Data.txt"));
    }
    @Test
    void readIntoListTestEmpty() {
        assertNotNull(dataService.readIntoList("Data2.txt"));
    }
    @Test
    void nullReadIntoListTest() {
        List<DataEntity> emptyList = Collections.emptyList();
        assertEquals(emptyList, dataService.readIntoList(""));
    }
    @Test
    void notFoundReadIntoListTest() {
        List<DataEntity> emptyList = Collections.emptyList();
        assertEquals(emptyList, dataService.readIntoList("Data1.txt"));
    }

    @BeforeEach void setUp() {
        DataEntity data = new DataEntity();
        data.setRut("12345678-9");
        data.setHora(Time.valueOf("09:00:00"));
        data.setHorasExtras(0);
        data.setFecha(Date.valueOf("2020-01-01"));
        data.setJustificado(false);
        this.dataService = new DataService(dataRepository);
    }
    @Test
    void encontrarEntradaTest(){
        dataService.encontrarEntrada("12345678-9", Date.valueOf("2020-01-01"));
        verify(dataRepository).findEntrada( Date.valueOf("2020-01-01"),"12345678-9");
    }

    @Test
    void encontrarSalidaTest(){
        dataService.encontrarSalida("12345678-9", Date.valueOf("2020-01-01"));
        verify(dataRepository).findSalida( Date.valueOf("2020-01-01"),"12345678-9");
    }

    @Test
    void ingresarJustificativoTest(){
        dataService.ingresarJustificativo("12345678-9", Date.valueOf("2020-01-01"));
        verify(dataRepository).updateJustificativo("12345678-9",Date.valueOf("2020-01-01"));
    }

    @Test
    void ingresarHorasExtrasTest(){
        dataService.ingresarHorasExtras("12345678-9", Date.valueOf("2020-01-01"), 1);
        verify(dataRepository).updateHorasExtras("12345678-9",Date.valueOf("2020-01-01"), 1);
    }

    @Test
    void obtenerSumaHorasExtrasTest(){
        dataService.obtenerSumaHorasExtras("12345678-9");
        verify(dataRepository).sumHorasExtras("12345678-9");
    }

    @Test
    void obtenerFechasUnicasTest(){
        dataService.obtenerFechasUnicas();
        verify(dataRepository).findDistinctFecha();
    }

    @Test
    void obtenerDataPorFechaTest(){
        dataService.obtenerData();
        verify(dataRepository).findAll();
    }

    @Test
    void guardarDataTest(){
        List<DataEntity> data = dataService.readIntoList("Data.txt");
        dataService.guardarData(data);
        verify(dataRepository).saveAll(data);
    }

    @Test
    void deleteDataTest(){
        dataService.deleteData();
        verify(dataRepository).deleteAll();
    }

    @Test
    void readDataFromFileTest(){
        dataService.readDataFromFile();
        verify(dataRepository).saveAll(dataService.readIntoList("Data.txt"));
    }

}