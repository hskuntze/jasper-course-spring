package com.kuntzeprojects.jaspercourse.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

@Service
public class JasperService {

	@Autowired
	private Connection connection;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	private static final String JASPER_DIR = "classpath:jasper/";
	private static final String PREFIX = "funcionarios-";
	private static final String SUFIX = ".jasper";
	
	private Map<String, Object> params = new HashMap<>();
	
	public JasperService() {
		this.params.put("IMAGE_DIR", JASPER_DIR);
		this.params.put("SUB_REPORT_DIR", JASPER_DIR);
	}
	
	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}
	
	public byte[] exportPdf(String code) {
		byte[] bytes = null;
		try {
			File file = ResourceUtils.getFile(JASPER_DIR.concat(PREFIX).concat(code).concat(SUFIX));
			JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);
			bytes = JasperExportManager.exportReportToPdf(print);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public HtmlExporter exportHtml(String code, HttpServletRequest request, HttpServletResponse response) {
		HtmlExporter htmlExp = null;

		try {
//			File file = ResourceUtils.getFile(JASPER_DIR.concat(PREFIX).concat(code).concat(SUFIX));
//			JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);
			
			Resource resource = resourceLoader.getResource(JASPER_DIR.concat(PREFIX).concat(code).concat(SUFIX));
			InputStream stream = resource.getInputStream();
			JasperPrint print = JasperFillManager.fillReport(stream, params, connection);
			htmlExp = new HtmlExporter();
			htmlExp.setExporterInput(new SimpleExporterInput(print));
			
			SimpleHtmlExporterOutput htmlExpOutput = new SimpleHtmlExporterOutput(response.getWriter());	
			HtmlResourceHandler resourceHandler = new WebHtmlResourceHandler(request.getContextPath() + "/image/servlet?image={0}");
			
			htmlExpOutput.setImageHandler(resourceHandler);
			htmlExp.setExporterOutput(htmlExpOutput);
			
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return htmlExp;
	}
}