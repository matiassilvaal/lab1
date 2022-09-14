package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    /*public Optional<EmpleadoEntity> obtenerPorId(Long id){
        return empleadoRepository.findById(id);
    }

    public EmpleadoEntity obtenerPorRut(String rut){
        return empleadoRepository.findByRut(rut);
    }
*/
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    /*public void updateAniosEnEmpresa(){
        empleadoRepository.updateAnios();
    }*/


}


