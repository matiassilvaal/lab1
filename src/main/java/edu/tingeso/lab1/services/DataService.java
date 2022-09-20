package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;
    public DataService(DataRepository dataRepository)
    {
        // this keyword refers to current instance
        this.dataRepository = dataRepository;
    }

    public void deleteData() {
        dataRepository.deleteAll();
    }
    public Integer readDataFromFile(){
        deleteData();
        List<DataEntity> res = readIntoList("Data.txt");
        if(Boolean.FALSE.equals(res.isEmpty())) {
            guardarData(res);
            return 1;
        }
        return 0;

    }
    public List<DataEntity> readIntoList(String file){
        try {
            ArrayList<DataEntity> dataList = new ArrayList<>();
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSplit = data.split(";");
                DataEntity dataEntity = new DataEntity(dataSplit[0],dataSplit[1],dataSplit[2]);
                dataList.add(dataEntity);
            }
            myReader.close();
            return dataList;
        } catch (FileNotFoundException e) {
            return List.of();
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
    public List<DataEntity> obtenerData() {
        return dataRepository.findAll();
    }
    public void guardarData(List<DataEntity> res){
        dataRepository.saveAll(res);
    }

}