package br.com.zup.nossocartao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class NossoCartaoV2Application {

	public static void main(String[] args) {
		SpringApplication.run(NossoCartaoV2Application.class, args);
	}

}
