package com.kuntzeprojects.jaspercourse.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kuntzeprojects.jaspercourse.repositories.EnderecoRepository;
import com.kuntzeprojects.jaspercourse.repositories.FuncionarioRepository;
import com.kuntzeprojects.jaspercourse.repositories.NivelRepository;
import com.kuntzeprojects.jaspercourse.services.JasperService;

import net.sf.jasperreports.engine.JRException;

@Controller
public class JasperController {
	
	@Autowired
	private JasperService jspService;
	
	@Autowired
	private NivelRepository nvRepo;
	
	@Autowired
	private EnderecoRepository enRepo;
	
	@Autowired
	private FuncionarioRepository fnRepo;
	
	@GetMapping(value = "/reports")
	public String abrir() {
		return "reports";
	}
	
	@GetMapping(value = "/rel01")
	public void relatorio01(
			@RequestParam("code") String code, 
			@RequestParam("acao") String acao, 
			HttpServletResponse response) throws IOException {
		byte[] bytes = jspService.exportPdf(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		if(acao.equals("v")) {
			response.setHeader("Content-disposition", "inline; filename=relatorio-"+ code + ".pdf");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=relatorio-"+ code + ".pdf");
		}
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping(value = "/rel08/{code}")
	public void relatorio08(
			@PathVariable String code, 
			@RequestParam(name = "nivel", required = false) String nivel, 
			@RequestParam(name = "uf", required = false) String uf, 
			HttpServletResponse response) throws IOException {
		jspService.addParam("NIVEL_DESC", nivel.isEmpty() ? null : nivel);
		jspService.addParam("UF", uf.isEmpty() ? null : uf);
		
		byte[] bytes = jspService.exportPdf(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping(value = "/rel10")
	public void relatorio10(
			@RequestParam("code") String code, 
			@RequestParam("acao") String acao, 
			HttpServletResponse response) throws IOException {
		byte[] bytes = jspService.exportPdf(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		if(acao.equals("v")) {
			response.setHeader("Content-disposition", "inline; filename=relatorio-"+ code + ".pdf");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=relatorio-"+ code + ".pdf");
		}
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping(value = "/rel19/{code}")
	public void relatorio19(
			@PathVariable String code, 
			@RequestParam(name = "idf", required = false) Long id,  
			HttpServletResponse response) throws IOException {
		jspService.addParam("ID_FUNCIONARIO", id);
		
		byte[] bytes = jspService.exportPdf(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping(value = "/rel19/html/{code}")
	public void relatorio19HTML(
			@PathVariable String code, 
			@RequestParam(name = "idf", required = false) Long id,  
			HttpServletResponse response,
			HttpServletRequest request) throws IOException, JRException {

		response.setContentType(MediaType.TEXT_HTML_VALUE);
		jspService.addParam("ID_FUNCIONARIO", id);
		jspService.exportHtml(code, request, response).exportReport();
	}
	
//	@GetMapping(value = "/rel09/{code}")
//	public void relatorio09(
//			@PathVariable String code, 
//			@RequestParam(name = "nivel", required = false) String nivel, 
//			@RequestParam(name = "uf", required = false) String uf, 
//			HttpServletResponse response) throws IOException {
//		jspService.addParam("NIVEL_DESC", nivel.isEmpty() ? null : nivel);
//		jspService.addParam("UF", uf.isEmpty() ? null : uf);
//		
//		byte[] bytes = jspService.exportPdf(code);
//		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//		response.getOutputStream().write(bytes);
//	}
	
	@ModelAttribute("niveis")
	public List<String> getNiveis() {
		return nvRepo.findNiveis();
	}
	
	@ModelAttribute("ufs")
	public List<String> getUfs() {
		return enRepo.findUfs();
	}

	@GetMapping(value = "/funcionarios")
	public ModelAndView findFuncionariosByName(@RequestParam("nome") String nome) {
		return new ModelAndView("reports", "funcionarios", fnRepo.findFuncionariosByNome(nome));
	}
}