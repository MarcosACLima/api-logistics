package com.dlima.logistica.domain.service;

import org.springframework.stereotype.Service;

import com.dlima.logistica.domain.exception.EntidadeNaoEncontradaException;
import com.dlima.logistica.domain.model.Entrega;
import com.dlima.logistica.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaEntregaService {
	
	private EntregaRepository entregaRepository;

	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
	}

}
