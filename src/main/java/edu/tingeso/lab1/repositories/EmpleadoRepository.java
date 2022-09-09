package edu.tingeso.lab1.repositories;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    public EmpleadoEntity findByRut(String rut);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE empleados e SET e.anios_en_empresa = TIMESTAMPDIFF(year,e.fecha_de_ingreso, now()) WHERE e.id >= 0",
            nativeQuery = true)
    void updateAnios();


    /*@Query("select e from EmpleadoEntity e where e.nombre = :nombre")
    EmpleadoEntity findByNameCustomQuery(@Param("nombre") String nombre);

    @Query(value = "select * from empleados as e where e.nombre = :nombre",
            nativeQuery = true)
    EmpleadoEntity findByNameNativeQuery(@Param("nombre") String nombre);*/

}