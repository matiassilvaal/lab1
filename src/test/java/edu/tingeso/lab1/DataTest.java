package edu.tingeso.lab1;
import static org.mockito.Mockito.verify;

import edu.tingeso.lab1.repositories.DataRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.services.DataService;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
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

}