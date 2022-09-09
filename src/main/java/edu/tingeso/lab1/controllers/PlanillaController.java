package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.entities.PlanillaEntity;
import edu.tingeso.lab1.services.DataService;
import edu.tingeso.lab1.services.EmpleadoService;
import edu.tingeso.lab1.services.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;

@Controller
@RequestMapping
public class PlanillaController {
    @Autowired
    PlanillaService planillaService;
    EmpleadoService empleadoService;
    DataService dataService;
    @GetMapping("/mplanilla")
    public String mostrarPlanilla(Model model){
        ArrayList<PlanillaEntity>planilla=planillaService.obtenerPlanilla();
        model.addAttribute("planilla",planilla);
        return "mplanilla";
    }
    @GetMapping("/justificativo")
    public String justificativo(Model model){
        return "justificativo";
    }

    @RequestMapping(value="/loadjustificativo", method = {RequestMethod.GET, RequestMethod.PUT})
    public String ingresarJustificativo(@RequestParam String fecha, @RequestParam String rut, RedirectAttributes attributes){
        if(!fecha.isBlank() && !rut.isBlank()){
            Boolean res = planillaService.ingresarJustificativo(Date.valueOf(fecha), rut);
            if(res == true){
                attributes.addFlashAttribute("message", "Se ha cargado el justificativo");
            }
            else{
                attributes.addFlashAttribute("message", "No se puede justificar un atraso menor de 70 minutos");
            }
        }
        else{
            attributes.addFlashAttribute("message", "Debe ingresar todos los datos");
        }
        return "redirect:/justificativo";
    }
    @GetMapping("/horasextras")
    public String horasextras(){
        return "horasextras";
    }
}
