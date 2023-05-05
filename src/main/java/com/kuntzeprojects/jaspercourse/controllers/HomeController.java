package com.kuntzeprojects.jaspercourse.controllers;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuntzeprojects.jaspercourse.entities.Funcionario;
import com.kuntzeprojects.jaspercourse.repositories.FuncionarioRepository;

@Controller
@RequestMapping(value = "/")
public class HomeController {
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private FuncionarioRepository fnRepo;

	@GetMapping
	public String home() {
		return "index";
	}
	
	@GetMapping(value = "/conn")
	public String conn(Model model) {
		model.addAttribute("conn", connection != null ? "200" : "500");
		return "index";
	}
	
	@GetMapping(value = "/certificados")
	public String certificados(@RequestParam("cid") Long cid, Model model) {
		Funcionario f = fnRepo.findById(cid).get();
		model.addAttribute("mensagem", "Confirmamos a veracidade deste certificado, pertencente a " + f.getNome() +
		" emitido em " + f.getDataDemissao());
		return "index";
	}
}