package com.springExamenopdracht;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import service.BookService;
import service.BookServiceImpl;
import validator.BookValidation;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class EwdExamenopdrachtApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(EwdExamenopdrachtApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/catalogue");
		registry.addViewController("/403").setViewName("403");
	}
	
	@Bean
	BookValidation isbnValidation() {
		return new BookValidation();
	}
	
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}
	
	@Bean
	BookService bookService() {
		return new BookServiceImpl();
	}
}
