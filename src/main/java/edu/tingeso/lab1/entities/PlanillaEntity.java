package edu.tingeso.lab1.entities;

import lombok.*;

import javax.persistence.*;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Entity
@Table(name = "planillas")
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
        private Integer sueldoFijo;
        private Integer bonificacionServicios;
        private Integer horasExtras;
        private Double descuentos;
        private Double sueldoBruto;
        private Double cotizacionPrevisional;
        private Double cotizacionSalud;
        private Double sueldoFinal;
}
