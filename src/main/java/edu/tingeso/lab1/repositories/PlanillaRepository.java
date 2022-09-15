package edu.tingeso.lab1.repositories;

import edu.tingeso.lab1.entities.PlanillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaRepository extends JpaRepository<PlanillaEntity, Long> {
}
