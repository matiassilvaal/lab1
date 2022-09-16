package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<EmpleadoEntity> obtenerEmpleados(){
        return empleadoRepository.findAll();
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }



}


