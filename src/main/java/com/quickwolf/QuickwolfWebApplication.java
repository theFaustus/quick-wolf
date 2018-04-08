package com.quickwolf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.quickwolf.config.QuickWolfConfiguration;

@SpringBootApplication
@ComponentScan(basePackages = "com.quickwolf.web")
@Import({ QuickWolfConfiguration.class })
public class QuickwolfWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickwolfWebApplication.class, args);
	}
}
