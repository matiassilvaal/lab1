package edu.tingeso.lab1.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true, nullable = false)

        @Getter private Long id;
        // Solo rut, nombres, apellidos, fecha de nacimiento, categoria e ingreso los dan en el excel, lo demas inicializarlo en null/0
        private String rut;
        private String apellidosNombres;
        private String categoria;
        private Integer aniosEnEmpresa;
        private Double sueldoFijo;
        private Integer bonificacionServicios;
        private Integer horasExtras;
        private Integer descuentos;
        private Double sueldoBruto;
        private Double contizacionPrevisional;
        private Double cotizacionSalud;
        private Double sueldoFinal;
}
