package edu.tingeso.lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
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
        private Boolean justificado;
        private Integer horasExtras;

        public DataEntity(String fecha, String hora, String rut) {
                this.fecha = Date.valueOf(convertirFecha(fecha));
                this.hora = Time.valueOf(convertirHora(hora));
                this.rut = rut;
                this.justificado = false;
                this.horasExtras = 0;
        }

        private String convertirFecha(String fecha) {
                String anio = fecha.split("/")[0];
                String mes = fecha.split("/")[1];
                String dia = fecha.split("/")[2];
                return anio + "-" + mes + "-" + dia;
        }
        private String convertirHora(String hora) {
                String hr = hora.split(":")[0];
                String minuto = hora.split(":")[1];
                return hr + ":" + minuto + ":00";
        }

}
