package edu.tingeso.lab1.repositories;

import edu.tingeso.lab1.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
}