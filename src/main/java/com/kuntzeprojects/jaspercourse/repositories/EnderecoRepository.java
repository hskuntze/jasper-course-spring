package com.kuntzeprojects.jaspercourse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kuntzeprojects.jaspercourse.entities.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

	@Query("SELECT DISTINCT e.uf FROM Endereco e ORDER BY e.uf ASC")
	List<String> findUfs();
}