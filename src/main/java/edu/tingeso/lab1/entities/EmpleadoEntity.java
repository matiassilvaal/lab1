package edu.tingeso.lab1.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
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
    // Solo rut, nombres, apellidos, fecha de nacimiento, categoria e ingreso los dan en el excel, lo demas inicializarlo en null/0
    private String rut;
    private String nombres;
    private String apellidos;
    private Date fechaDeNacimiento;
    private String categoria;
    private Date fechaDeIngreso;
}
