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
    @Query(value = "select * from data as d where d.fecha = :fecha and d.rut = :rut",
            nativeQuery = true)
    DataEntity findByRutAndFecha(@Param("fecha") Date fecha, @Param("rut") String rut);

    /*@Query(value = "select COUNT(*) from da ta as d where d.fecha = :fecha and d.rut = :rut",
            nativeQuery = true)
    DataEntity countNotJustificado(@Param("fecha") Date fecha, @Param("rut") String rut);*/
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE data d SET d.justificado = 1 WHERE d.rut = :rut",
            nativeQuery = true)
    void updateJustificativo(String rut);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE data d SET d.horas_extras = :horasExtras WHERE d.rut = :rut",
            nativeQuery = true)
    void updateHorasExtras(String rut, Integer horasExtras);
}
