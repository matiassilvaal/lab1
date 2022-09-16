package edu.tingeso.lab1.services;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.entities.PlanillaEntity;
import edu.tingeso.lab1.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@SuppressWarnings({"deprecation", "SpringJavaAutowiredFieldsWarningInspection"})
@Service
public class PlanillaService {
    static final String HORAMAXIMA = "09:10:00";
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    DataService dataService;
    @Autowired
    PlanillaRepository planillaRepository;

    public List<PlanillaEntity> obtenerPlanilla(){
        return planillaRepository.findAll();
    }

    public Integer ingresarJustificativo(Date fecha, String rut){

        DataEntity info = dataService.encontrarEntrada(rut,fecha);
        if(info == null) return -2;
        switch(calcularSiJustificativo(info)){
            case -1:
                return -1;
            case 0:
                return 0;
            case 1:
                dataService.ingresarJustificativo(rut, fecha);
                return 1;
            default:
                return -2;
        }
    }
    public Integer calcularSiJustificativo(DataEntity info){
        if(info.getJustificado()) return -1;
        if(info.getHora().compareTo(Time.valueOf(HORAMAXIMA)) >= 0) return 1;
        else return 0;
    }

    public Integer ingresarHorasExtras(Date fecha, String rut){
        DataEntity info = dataService.encontrarSalida(rut,fecha);
        if(info == null) return -2;
        if(info.getHorasExtras() > 0) return -1;
        Integer calculo = calcularSiHorasExtras(info);
        if(calculo != 0){
            dataService.ingresarHorasExtras(rut,fecha,calculo);
            return 1;
        }
        else return 0;
    }
    public Integer calcularSiHorasExtras(DataEntity info){
        if(info.getHorasExtras() > 0) return -1;
        int diferencia = info.getHora().getHours()-Time.valueOf("18:00:00").getHours();
        return Math.max(diferencia, 0);
    }

    public Integer calcularAniosDeServicio(EmpleadoEntity empleado){
        return 2022-(empleado.getFechaDeIngreso().getYear()+1900);
    }
    public Integer sueldoFijo(EmpleadoEntity empleado){
        switch (empleado.getCategoria()) {
            case "A" -> {
                return 1700000;
            }
            case "B" -> {
                return 1200000;
            }
            case "C" -> {
                return 800000;
            }
            default -> {
                return 0;
            }
        }
    }
    public Integer montoAniosDeServicio(EmpleadoEntity empleado, Integer sueldoFijo){
        int anios = calcularAniosDeServicio(empleado);
        if(anios >= 5 && anios < 10) return sueldoFijo * 5 / 100;
        else if (anios >= 10 && anios < 15) return sueldoFijo * 8 / 100;
        else if (anios >= 15 && anios < 20) return sueldoFijo * 11 / 100;
        else if (anios >= 20 && anios < 25) return sueldoFijo * 14 / 100;
        else if (anios >= 25) return sueldoFijo * 17 / 100;
        return 0;
    }
    public Integer calcularHorasExtras(EmpleadoEntity empleado){
        return calcularHorasExtrasCategoria(empleado, dataService.obtenerSumaHorasExtras(empleado.getRut()));
    }
    public Integer calcularHorasExtrasCategoria(EmpleadoEntity empleado, Integer horasExtras){
        if(horasExtras == null) return 0;
        return switch (empleado.getCategoria()) {
            case "A" -> horasExtras * 25000;
            case "B" -> horasExtras * 20000;
            case "C" -> horasExtras * 10000;
            default -> 0;
        };
    }
    public Double calcularDescuentos(EmpleadoEntity empleado, Integer sueldoFijo) {
        Date[] fechas = dataService.obtenerFechasUnicas();
        return calcularDescuentoPorFecha(empleado, sueldoFijo, fechas);
    }
    public Double calcularDescuentoPorFecha(EmpleadoEntity empleado, Integer sueldoFijo, Date[] fechas){
        double descuento = 0.0;
        for(Date fecha : fechas){
            DataEntity entrada = dataService.encontrarEntrada(empleado.getRut(),fecha);
            descuento += calcularPorHoraDescuento(entrada, sueldoFijo);
        }
        return descuento;
    }
    public Double calcularPorHoraDescuento(DataEntity entrada, Integer sueldoFijo) {
        if (entrada.getHora().compareTo(Time.valueOf("08:10:00")) > 0 && entrada.getHora().compareTo(Time.valueOf("08:25:00")) < 0)
            return sueldoFijo * 0.01;
        else if (entrada.getHora().compareTo(Time.valueOf("08:25:00")) > 0 && entrada.getHora().compareTo(Time.valueOf("08:45:00")) < 0)
            return sueldoFijo * 0.03;
        else if (entrada.getHora().compareTo(Time.valueOf("08:45:00")) > 0 && entrada.getHora().compareTo(Time.valueOf(HORAMAXIMA)) < 0)
            return sueldoFijo * 0.06;
        else if (entrada.getHora().compareTo(Time.valueOf(HORAMAXIMA)) > 0 && !entrada.getJustificado())
            return sueldoFijo * 0.15;
        return 0.0;
    }
    public Double calcularSueldoBruto(EmpleadoEntity empleado){
        Integer sueldoFijo = sueldoFijo(empleado);
        Integer montoAniosDeServicio = montoAniosDeServicio(empleado, sueldoFijo);
        Integer horasExtras = calcularHorasExtras(empleado);
        return (double) sueldoFijo + (double) montoAniosDeServicio + (double) horasExtras;
    }
    public Double calcularContizacionPrevisional(EmpleadoEntity empleado){
        Double sueldoBruto = calcularSueldoBruto(empleado);
        return (sueldoBruto-calcularDescuentos(empleado, sueldoFijo(empleado)))*0.1;
    }
    public Double calcularCotizacionSalud(EmpleadoEntity empleado){
        Double sueldoBruto = calcularSueldoBruto(empleado);
        return (sueldoBruto-calcularDescuentos(empleado, sueldoFijo(empleado)))*0.08;
    }
    public Double calcularSueldoFinal(EmpleadoEntity empleado){
        Double sueldoBruto = calcularSueldoBruto(empleado);
        Double descuentos = calcularDescuentos(empleado, sueldoFijo(empleado));
        Double cotizacionPrevisional = calcularContizacionPrevisional(empleado);
        Double cotizacionSalud = calcularCotizacionSalud(empleado);
        return sueldoBruto-descuentos-cotizacionPrevisional-cotizacionSalud;
    }
    public void guardarPlanilla(PlanillaEntity planilla){
        planillaRepository.save(planilla);
    }
    public void borrarPlanilla(){
        planillaRepository.deleteAll();
    }
    public Integer calcularPlanilla(){
        List<EmpleadoEntity> empleados = empleadoService.obtenerEmpleados();
        return calcularPlanilla(empleados);
    }
    public Integer calcularPlanilla(List<EmpleadoEntity> empleados){
        borrarPlanilla();
        try{

            for (EmpleadoEntity empleado : empleados){
                PlanillaEntity planilla = new PlanillaEntity();
                planilla.setRut(empleado.getRut());
                planilla.setApellidosNombres(empleado.getApellidos()+" "+empleado.getNombres());
                planilla.setCategoria(empleado.getCategoria());
                planilla.setAniosEnEmpresa(calcularAniosDeServicio(empleado));
                planilla.setSueldoFijo(sueldoFijo(empleado));
                planilla.setBonificacionServicios(montoAniosDeServicio(empleado,planilla.getSueldoFijo()));
                planilla.setHorasExtras(calcularHorasExtras(empleado));
                planilla.setDescuentos(calcularDescuentos(empleado,planilla.getSueldoFijo()));
                planilla.setSueldoBruto(calcularSueldoBruto(empleado));
                planilla.setCotizacionPrevisional(calcularContizacionPrevisional(empleado));
                planilla.setCotizacionSalud(calcularCotizacionSalud(empleado));
                planilla.setSueldoFinal(calcularSueldoFinal(empleado));
                guardarPlanilla(planilla);
            }
            return 1;
        }
        catch (Exception e){
            return 0;
        }

    }

}
