package com.kuntzeprojects.jaspercourse.repositories;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kuntzeprojects.jaspercourse.entities.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	@Query("SELECT f.id AS id, f.nome AS nome " +
			"FROM Funcionario f "+
			"WHERE f.nome LIKE %:nome% "+
			"AND f.dataDemissao IS NOT NULL "+
			"AND f.nivel.id = 1")
	List<Tuple> findFuncionariosByNome(String nome);
}