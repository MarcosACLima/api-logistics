package com.dlima.logistica.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dlima.logistica.api.assembler.EntregaAssembler;
import com.dlima.logistica.api.model.EntregaModel;
import com.dlima.logistica.api.model.input.EntregaInput;
import com.dlima.logistica.domain.model.Entrega;
import com.dlima.logistica.domain.repository.EntregaRepository;
import com.dlima.logistica.domain.service.FinalizacaoEntregaService;
import com.dlima.logistica.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor // injeta depencencias pelo construtor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaAssembler entregaAssembler;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput); // converter para entitidade Entrega
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);
		
		return entregaAssembler.toModel(entregaSolicitada); // retorna uma entregaModel (converte)
	}
	
	@GetMapping
	public List<EntregaModel> listar() {
		return entregaAssembler.toCollectionModel(entregaRepository.findAll());
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId) {
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))// converter o objeto entrega para um EntreModel
				.orElse(ResponseEntity.notFound().build());	
//					Conversao antiga sem o ModelMapper, gerando C??digo boilerplate
//					EntregaModel entregaModel = new EntregaModel();
//					// cliente
//					entregaModel.setId(entrega.getId());
//					entregaModel.setNomeCliente(entrega.getCliente().getNome());
//					// destinatario
//					entregaModel.setDestinatario(new DestinatarioModel());
//					entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
//					entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
//					entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
//					entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
//					entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
//					// entrega
//					entregaModel.setTaxa(entrega.getTaxa());
//					entregaModel.setStatus(entrega.getStatus());
//					entregaModel.setDataPedido(entrega.getDataPedido());
//					entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
//					return ResponseEntity.ok(entregaModel);
//				}).orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}
	
}
