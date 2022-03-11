package com.dlima.logistica.api.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlima.logistica.domain.model.Cliente;

@RestController
public class ClienteController {
	
	/* Injetar EntityManager na variavel de instancia media */
	@PersistenceContext
	private EntityManager manager;

	@GetMapping("/clientes")
	public List<Cliente> lista() {
		return manager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	};

}
