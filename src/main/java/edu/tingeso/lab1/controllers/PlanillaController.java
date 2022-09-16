package edu.tingeso.lab1.controllers;

import edu.tingeso.lab1.entities.PlanillaEntity;
import edu.tingeso.lab1.services.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping
public class PlanillaController {
    static final String MESSAGE = "message";
    @Autowired
    PlanillaService planillaService;
    @GetMapping("/mplanilla")
    public String mostrarPlanilla(Model model){
        List<PlanillaEntity> planilla=planillaService.obtenerPlanilla();
        if(planilla.isEmpty()){
            model.addAttribute(MESSAGE,"No existen planillas calculadas.");
        }
        model.addAttribute("planilla",planilla);
        return "mplanilla";
    }
    @GetMapping("/justificativo")
    public String justificativo(){
        return "justificativo";
    }


    @RequestMapping("/loadjustificativo")
    @PatchMapping(params = "/loadjustificativo/{fecha}/{rut}", consumes = "application/json")
    public String ingresarJustificativo(@RequestParam String fecha, @RequestParam String rut, RedirectAttributes attributes){
        if(!fecha.isBlank() && !rut.isBlank()){
            Integer res = planillaService.ingresarJustificativo(Date.valueOf(fecha), rut);
            switch (res) {
                case -2 -> attributes.addFlashAttribute(MESSAGE, "No se ha encontrado al empleado en la fecha ingresada");
                case -1 -> attributes.addFlashAttribute(MESSAGE, "Este justificativo ya fue ingresado");
                case 0 -> attributes.addFlashAttribute(MESSAGE, "No se puede justificar un atraso menor de 70 minutos");
                case 1 -> attributes.addFlashAttribute(MESSAGE, "Se ha cargado el justificativo");
                default -> attributes.addFlashAttribute(MESSAGE, "Error desconocido");
            }
        }
        else{ attributes.addFlashAttribute(MESSAGE, "Debe ingresar todos los datos"); }
        return "redirect:/justificativo";
    }
    @GetMapping("/horasextras")
    public String horasextras(){ return "horasextras"; }

    @RequestMapping("/loadhorasextras")
    @PatchMapping(params = "/loadhorasextras/{fecha}/{rut}", consumes = "application/json")
    public String ingresarHorasExtras(@RequestParam String fecha, @RequestParam String rut, RedirectAttributes attributes){
        if(!fecha.isBlank() && !rut.isBlank()){
            Integer res = planillaService.ingresarHorasExtras(Date.valueOf(fecha), rut);
            switch (res) {
                case 1 -> attributes.addFlashAttribute(MESSAGE, "Se han aceptado las horas extras");
                case 0 -> attributes.addFlashAttribute(MESSAGE, "No se puede aceptar menos de 1 hora extra");
                case -1 -> attributes.addFlashAttribute(MESSAGE, "Estas horas extras ya fueron aceptadas");
                case -2 -> attributes.addFlashAttribute(MESSAGE, "El rut o la fecha no existen");
                default -> attributes.addFlashAttribute(MESSAGE, "Error desconocido");
            }
        }
        else{ attributes.addFlashAttribute(MESSAGE, "Debe ingresar todos los datos"); }
        return "redirect:/horasextras";
    }
    @GetMapping("/cplanilla")
    public String calcularPlanilla(){
        return "cplanilla";
    }
    @PostMapping(value = "/calcularplanilla")
    public String calculosDePlanilla(RedirectAttributes attributes){
        Integer res = planillaService.calcularPlanilla();
        if(res == 1) {
            attributes.addFlashAttribute(MESSAGE, "Se ha calculado la planilla");
        }
        else{
            attributes.addFlashAttribute(MESSAGE, "Error inesperado, no se pudo calcular la planilla");
        }
        return "redirect:/cplanilla";
    }
}
