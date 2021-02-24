package com.gmail.murilo145farias.DesafioAPI;

import io.swagger.annotations.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Collections;

@EnableSwagger2
@SpringBootApplication
public class DesafioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApiApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.gmail.murilo145farias.DesafioAPI"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Desafio API",
				"API referente ao desafio proposto",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Murilo Farias",
						"https://github.com/murilofarias/Desafio_API",
						"murilo145farias@gmail.com"),
				"API License",
				"https://github.com/murilofarias/Desafio_API",
				Collections.emptyList());


	}

}
