package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.repositories.DataRepository;
import edu.tingeso.lab1.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;

@Controller
@RequestMapping
public class DataController {
    @Autowired
    DataService dataService;
    @GetMapping("/index")
    public String index(Model model) {
        ArrayList<DataEntity>data=dataService.obtenerData();
        model.addAttribute("data",data);
        return "index";
    }
}
