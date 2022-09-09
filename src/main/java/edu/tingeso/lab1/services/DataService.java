package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.sql.Date;
import java.text.ParseException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;

    public void loadDataFromFile(String file) {
        try {
            dataRepository.deleteAll();
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSplit = data.split(";");
                DataEntity dataEntity = new DataEntity(dataSplit[0],dataSplit[1],dataSplit[2]);
                dataRepository.save(dataEntity);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DataEntity> obtenerData() {
        return (ArrayList<DataEntity>) dataRepository.findAll();
    }

}