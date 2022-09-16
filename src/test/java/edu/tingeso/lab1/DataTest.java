package edu.tingeso.lab1;
import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.services.DataService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    DataService dataService = new DataService();
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
}