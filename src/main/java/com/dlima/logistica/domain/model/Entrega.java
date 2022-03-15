package com.dlima.logistica.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne // muitas entregas possui 1 clinte
	private Cliente cliente;
	
	@Embedded // abstrair os dados do destinario
	private Destinatario destinatario;
	
	private BigDecimal taxa;
	
	/* Cascade de Dependencias, sincronizar ocorrencias com a entrega  */
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) // 1 Entrega tem muitas (N) Ocorrencias
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	
	@Enumerated(EnumType.STRING) // armazenar o propria String da ENUM
	private StatusEntrega status;
	
	private OffsetDateTime dataPedido;
	
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this); // a propria entrega
		
		this.getOcorrencias().add(ocorrencia); // add a propria ocorrencia criada acima na Lista
		
		return ocorrencia;
	}

}
