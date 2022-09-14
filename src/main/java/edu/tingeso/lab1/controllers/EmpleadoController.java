package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
        ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();
        model.addAttribute("empleados",empleados);
        return "index";
    }

    @PostMapping(value = "/agregar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String crearEmpleado(EmpleadoEntity empleado) {
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/listar";
    }

    //@DeleteMapping("/eliminar/{id}")
    @RequestMapping(value = "/eliminar/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String eliminarEmpleado(@PathVariable long id) {
        empleadoService.eliminarEmpleado(id);
        return "redirect:/listar";
    }

}