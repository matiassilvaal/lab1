package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping
public class DataController {
    @Autowired
    DataService dataService;
    @PostMapping(value = "/load")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        switch (dataService.loadDataFromFile(file)) {
            case -1 -> attributes.addFlashAttribute("message", "Porfavor, selecciona un archivo para cargar.");
            case 0 -> attributes.addFlashAttribute("message", "No se ha podido cargar lo entregado");
            case 1 -> attributes.addFlashAttribute("message", "Se ha cargado el archivo!");
        }
        return "redirect:/importar";
    }
    @GetMapping("/importar")
    public String importar(Model model) {
        ArrayList<DataEntity>data=dataService.obtenerData();
        model.addAttribute("data",data);
        return "importar";
    }

}
