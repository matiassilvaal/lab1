package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public Integer loadDataFromFile(MultipartFile file) {
        if (file.isEmpty()) {
            return -1;
        }
        if (file.getOriginalFilename() == null) {
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

    public void deleteData() {
        dataRepository.deleteAll();
    }
    public void readDataFromFile(String file){
        deleteData();
        dataRepository.saveAll(readIntoList(file));
    }
    public ArrayList<DataEntity> readIntoList(String file){
        try {
            //dataRepository.deleteAll();
            ArrayList<DataEntity> dataList = new ArrayList<>();
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSplit = data.split(";");
                DataEntity dataEntity = new DataEntity(dataSplit[0],dataSplit[1],dataSplit[2]);
                dataList.add(dataEntity);
                //dataRepository.save(dataEntity);
            }
            myReader.close();
            return dataList;
        } catch (FileNotFoundException | ParseException e) {
            return null;
        }
    }

    public DataEntity encontrarEntrada(String rut, Date fecha){
        return dataRepository.findEntrada(fecha,rut);
    }
    public DataEntity encontrarSalida(String rut, Date fecha){
        return dataRepository.findSalida(fecha,rut);
    }
    public void ingresarJustificativo(String rut, Date fecha){
        dataRepository.updateJustificativo(rut, fecha);
    }
    public void ingresarHorasExtras(String rut, Date fecha, Integer horas){
        dataRepository.updateHorasExtras(rut,fecha, horas);
    }
    public Integer obtenerSumaHorasExtras(String rut){
        return dataRepository.sumHorasExtras(rut);
    }
    public Date[] obtenerFechasUnicas(){
        return dataRepository.findDistinctFecha();
    }
    public ArrayList<DataEntity> obtenerData() {
        return (ArrayList<DataEntity>) dataRepository.findAll();
    }

}