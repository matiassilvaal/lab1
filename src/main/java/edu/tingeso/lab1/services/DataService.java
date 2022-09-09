package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public Integer loadDataFromFile(MultipartFile file) {
        if (file.isEmpty()) {
            return -1;
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            readDataFromFile(UPLOAD_DIR + fileName);

        } catch (IOException e) {
            return 0;
        }
        return 1;
    }

    public void readDataFromFile(String file){
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