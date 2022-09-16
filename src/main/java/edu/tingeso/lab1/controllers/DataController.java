package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping
public class DataController {
    static final String MESSAGE = "message";
    @Autowired
    DataService dataService;
    @PostMapping(value = "/load")
    public String uploadFile(RedirectAttributes attributes) {
        switch (dataService.readDataFromFile()) {
            case 0 -> attributes.addFlashAttribute(MESSAGE, "No se ha podido cargar el archivo");
            case 1 -> attributes.addFlashAttribute(MESSAGE, "Se ha cargado el archivo!");
            default -> attributes.addFlashAttribute(MESSAGE, "Error desconocido");
        }
        return "redirect:/importar";
    }
    @GetMapping("/importar")
    public String importar(Model model) {
        List<DataEntity> data=dataService.obtenerData();
        model.addAttribute("data",data);
        return "importar";
    }

}
