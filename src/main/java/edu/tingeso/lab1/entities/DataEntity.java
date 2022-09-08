package edu.tingeso.lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true, nullable = false)
        private Long id;
        private Date fecha;
        private Time hora;
        private String rut;

        public DataEntity(File data){
            String[] dataString = data.toString().split(";");
            Integer year = Integer.parseInt(dataString[0].split("/")[0]);
            Integer month = Integer.parseInt(dataString[0].split("/")[1]);
            Integer day = Integer.parseInt(dataString[0].split("/")[2]);
            Integer hour = Integer.parseInt(dataString[1].split(":")[0]);
            Integer minute = Integer.parseInt(dataString[1].split(":")[1]);
            this.fecha = new Date(year, month, day);
            this.hora = new Time(hour, minute, 0);
            this.rut = dataString[1];
        }

}
