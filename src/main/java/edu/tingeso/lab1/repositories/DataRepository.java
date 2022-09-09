package edu.tingeso.lab1.repositories;

import edu.tingeso.lab1.entities.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {
}
