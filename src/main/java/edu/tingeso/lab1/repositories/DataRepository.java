package edu.tingeso.lab1.repositories;

import edu.tingeso.lab1.entities.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {

    @Query(value = "SELECT DISTINCT(d.fecha) from data as d", nativeQuery = true)
    Date[] findDistinctFecha();
    @Query(value = "select * from data as d where d.fecha = :fecha and d.rut = :rut ORDER BY d.id LIMIT 1",
            nativeQuery = true)
    DataEntity findEntrada(@Param("fecha") Date fecha, @Param("rut") String rut);

    @Query(value = "select * from data as d where d.fecha = :fecha and d.rut = :rut ORDER BY d.id DESC LIMIT 1",
            nativeQuery = true)
    DataEntity findSalida(@Param("fecha") Date fecha, @Param("rut") String rut);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE data d SET d.justificado = 1 WHERE d.rut = :rut and d.fecha = :fecha",
            nativeQuery = true)
    void updateJustificativo(@Param("rut") String rut, @Param("fecha") Date fecha);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE data d SET d.horas_extras = :horasExtras WHERE d.rut = :rut AND d.fecha = :fecha AND d.horas_extras > '18:00'",
            nativeQuery = true)
    void updateHorasExtras(@Param("rut") String rut, @Param("fecha") Date fecha, @Param("horasExtras") Integer horasExtras);

    @Query(value = "SELECT sum(horas_extras) FROM data WHERE rut=:rut", nativeQuery = true)
    Integer sumHorasExtras(@Param("rut") String rut);
}
