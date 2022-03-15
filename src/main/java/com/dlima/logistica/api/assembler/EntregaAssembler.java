package com.dlima.logistica.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.dlima.logistica.api.model.EntregaModel;
import com.dlima.logistica.api.model.input.EntregaInput;
import com.dlima.logistica.domain.model.Entrega;

import lombok.AllArgsConstructor;

/* Converter um tipo de objeto para outro, evitando dependencia do projeto do ModelMapper */
@AllArgsConstructor
@Component
public class EntregaAssembler {
	
	private ModelMapper modelMapper;
	
	public EntregaModel toModel(Entrega entrega) {
		return modelMapper.map(entrega, EntregaModel.class);
	}
	
	public List<EntregaModel> toCollectionModel(List<Entrega> entregas) {
		return entregas.stream()
				.map(this::toModel) // stream de EntregaModel
				.collect(Collectors.toList()); // converter em lista		
	}
	
	/* Converter um tipo EntregaInput em uma Entidade Entrega */
	public Entrega toEntity(EntregaInput entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}

}
