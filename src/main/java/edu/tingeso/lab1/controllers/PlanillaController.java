package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.PlanillaEntity;
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
    @GetMapping("/mplanilla")
    public String mostrarPlanilla(Model model){
        ArrayList<PlanillaEntity>planilla=planillaService.obtenerPlanilla();
        model.addAttribute("planilla",planilla);
        return "mplanilla";
    }
    @GetMapping("/justificativo")
    public String justificativo(){
        return "justificativo";
    }

    @RequestMapping(value="/loadjustificativo", method = {RequestMethod.GET, RequestMethod.PUT})
    public String ingresarJustificativo(@RequestParam String fecha, @RequestParam String rut, RedirectAttributes attributes){
        if(!fecha.isBlank() && !rut.isBlank()){
            Integer res = planillaService.ingresarJustificativo(Date.valueOf(fecha), rut);
            switch (res) {
                case 1 -> attributes.addFlashAttribute("message", "Se ha cargado el justificativo");
                case 0 ->
                        attributes.addFlashAttribute("message", "No se puede justificar un atraso menor de 70 minutos");
                case -1 -> attributes.addFlashAttribute("message", "Este justificativo ya fue ingresado");
            }
        }
        else{ attributes.addFlashAttribute("message", "Debe ingresar todos los datos"); }
        return "redirect:/justificativo";
    }
    @GetMapping("/horasextras")
    public String horasextras(){ return "horasextras"; }

    @RequestMapping(value="/loadhorasextras", method = {RequestMethod.GET, RequestMethod.PUT})
    public String ingresarHorasExtras(@RequestParam String fecha, @RequestParam String rut, RedirectAttributes attributes){
        if(!fecha.isBlank() && !rut.isBlank()){
            Integer res = planillaService.ingresarHorasExtras(Date.valueOf(fecha), rut);
            switch (res) {
                case 1 -> attributes.addFlashAttribute("message", "Se han aceptado las horas extras");
                case 0 ->
                        attributes.addFlashAttribute("message", "No se puede aceptar menos de 1 hora extra");
                case -1 -> attributes.addFlashAttribute("message", "Estas horas extras ya fueron aceptadas");
                case -2 -> attributes.addFlashAttribute("message", "El rut o la fecha no existen");
            }
        }
        else{ attributes.addFlashAttribute("message", "Debe ingresar todos los datos"); }
        return "redirect:/horasextras";
    }
    @GetMapping("/cplanilla")
    public String calcularPlanilla(){
        return "cplanilla";
    }
    @RequestMapping(value="/calcularplanilla", method = {RequestMethod.GET, RequestMethod.PUT})
    public String calculosDePlanilla(){
        return "index";
    }
}
