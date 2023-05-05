package com.kuntzeprojects.jaspercourse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kuntzeprojects.jaspercourse.entities.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long>{
	
	@Query("SELECT n.nivel FROM Nivel n")
	List<String> findNiveis();
}
