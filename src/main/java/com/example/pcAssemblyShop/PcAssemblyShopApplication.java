package com.example.pcAssemblyShop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PcAssemblyShopApplication {

	public static boolean Admin_Mode= false;
	public static void main(String[] args) {
		SpringApplication.run(PcAssemblyShopApplication.class, args);
	}

}
