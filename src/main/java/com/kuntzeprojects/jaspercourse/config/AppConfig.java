package com.kuntzeprojects.jaspercourse.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.sql.DataSource;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.jasperreports.j2ee.servlets.ImageServlet;

@Configuration
public class AppConfig {

	@Bean
	Connection conn(DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}

    @Bean
    ServletRegistrationBean<Servlet> imageServlet() {
        ServletRegistrationBean<Servlet> servlet = new ServletRegistrationBean<>(
                new ImageServlet(), "/image/servlet/*");
        servlet.setLoadOnStartup(1);
        return servlet;
    }
}