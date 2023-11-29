package com.example.bambergBeverageBox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BambergBeverageBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BambergBeverageBoxApplication.class, args);
	}

}
