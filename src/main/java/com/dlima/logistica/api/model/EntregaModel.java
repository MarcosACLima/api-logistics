package com.dlima.logistica.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.dlima.logistica.domain.model.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaModel {
	
	private Long id;
	private ClienteResumoModel cliente;
	private DestinatarioModel destinatario;
	private BigDecimal taxa; // taxa pode ter nome diferente do model/entidade
	private StatusEntrega status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;

}
