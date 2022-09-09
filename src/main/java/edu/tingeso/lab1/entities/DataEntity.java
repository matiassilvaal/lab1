package edu.tingeso.lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "data")
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

        public DataEntity(String fecha, String hora, String rut) throws ParseException {
                this.fecha = Date.valueOf(convertirFecha(fecha));
                this.hora = Time.valueOf(convertirHora(hora));
                this.rut = rut;
        }

        private String convertirFecha(String fecha) throws ParseException {
                String anio = fecha.split("/")[0];
                String mes = fecha.split("/")[1];
                String dia = fecha.split("/")[2];
                String newFecha = anio + "-" + mes + "-" + dia;
                return newFecha;
        }
        private String convertirHora(String hora) throws ParseException {
                String hr = hora.split(":")[0];
                String minuto = hora.split(":")[1];
                String newHora = hr + ":" + minuto + ":00";
                return newHora;
        }

}
