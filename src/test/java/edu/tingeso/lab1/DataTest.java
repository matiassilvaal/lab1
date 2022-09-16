package edu.tingeso.lab1;
import edu.tingeso.lab1.services.DataService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    DataService dataService = new DataService();
    @Test
    void readIntoListTest() {
        assertNotNull(dataService.readIntoList("src/main/resources/static/uploads/"+"Data.txt"));
    }
    @Test
    void nullReadIntoListTest() {
        assertNull(dataService.readIntoList(""));
    }
    @Test
    void notFoundReadIntoListTest() {
        assertNull(dataService.readIntoList("src/main/resources/static/uploads/" + "Data1.txt"));
    }
}