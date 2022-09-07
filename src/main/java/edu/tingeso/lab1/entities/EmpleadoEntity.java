package edu.tingeso.lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String nombres;
    private String apellidos;
    private Date fechaDeNacimiento;
    private Double sueldoFijo;
    private Double sueldoLiquido;
    private Character categoria;
    private Double descuento;
    private Integer horasExtras;
    private Date fechaDeIngreso;
}
