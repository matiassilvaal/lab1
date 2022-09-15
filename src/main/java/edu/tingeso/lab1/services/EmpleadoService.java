package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public ArrayList<EmpleadoEntity> obtenerEmpleados(){
        return (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
    }

    public void guardarEmpleado(EmpleadoEntity empleado){
        empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }



}


