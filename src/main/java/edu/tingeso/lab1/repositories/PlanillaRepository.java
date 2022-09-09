package edu.tingeso.lab1.repositories;

import edu.tingeso.lab1.entities.DataEntity;
import edu.tingeso.lab1.entities.EmpleadoEntity;
import edu.tingeso.lab1.entities.PlanillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

public interface PlanillaRepository extends JpaRepository<PlanillaEntity, Long> {


    /*@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE planilla p SET p.descuentos = p.sueldoFijo-p.sueldoFijo*0.15 WHERE p.rut = :rut",
            nativeQuery = true)
    void updateJustificativo(String rut);*/
}
