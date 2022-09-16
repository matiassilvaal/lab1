package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/listar")
    public String listar(Model model) {
        List<EmpleadoEntity> empleados=empleadoService.obtenerEmpleados();
        model.addAttribute("empleados",empleados);
        return "index";
    }


}