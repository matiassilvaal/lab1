package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import edu.tingeso.lab1.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

@Controller
@RequestMapping
public class DataController {
    @Autowired
    DataService dataService;
    private final String UPLOAD_DIR = "C:/Users/Matias/IdeaProjects/lab1/src/main/resources/static/uploads/";
    @PostMapping(value = "/load")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Porfavor, selecciona un archivo para cargar.");
            return "redirect:/importar";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            dataService.loadDataFromFile(UPLOAD_DIR + fileName);
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "No se ha podido cargar lo entregado");
            return "redirect:/importar";
        }
        attributes.addFlashAttribute("message", "Se ha cargado el archivo " + fileName + '!');
        return "redirect:/importar";
    }
    @GetMapping("/importar")
    public String importar(Model model) {
        ArrayList<DataEntity>data=dataService.obtenerData();
        model.addAttribute("data",data);
        return "importar";
    }

}
