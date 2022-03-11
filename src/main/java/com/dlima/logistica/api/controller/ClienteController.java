package com.dlima.logistica.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dlima.logistica.domain.model.Cliente;
import com.dlima.logistica.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;

	@GetMapping
	public List<Cliente> lista() {
		return clienteRepository.findAll();
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<ResponseEntity<Cliente>> buscar(@PathVariable Long clienteId) {
		return clienteRepository.findById(clienteId).map(cliente -> ResponseEntity.ok(cliente)).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

//		Antigo
//		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
//		
//		if (cliente.isPresent()) { // se exisitr
//			return ResponseEntity.ok(cliente.get()); 
//		}
//		
//		return ResponseEntity.notFound().build();  // caso não exista

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) { //transformar objeto JSON em objeto Java
		return clienteRepository.save(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId); // setar o Id para não criar um novo cliente
		cliente = clienteRepository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build(); // nao existe 404
		}
		
		clienteRepository.deleteById(clienteId);
		
		return ResponseEntity.noContent().build(); // não contem corpo na resposta 204
	}

//	Exemplos de buscar por atributos 
//	@GetMapping("/clientes/nome")
//	List<Cliente> findByNome() {
//		return clienteRepository.findByNome("Joao da Silva");
//	}
//	
//	@GetMapping("/clientes/nome-like")
//	List<Cliente> findByNomeContaining() {
//		return clienteRepository.findByNomeContaining("Si");
//	}

}
