package edu.cibertec.votoelectronico.mapping.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.dto.VotoDto;
import edu.cibertec.votoelectronico.mapping.VotoDtoMapper;

@Mapper(componentModel = "cdi")
public interface MSVotoDtoMapper extends VotoDtoMapper {

	@Mapping(target = "votoId", source = "object.votoId")
	@Mapping(target = "dni", source = "object.dni")
	@Mapping(target = "fecha", source = "object.fecha", dateFormat = "yyyyMMddHHmmss")
	@Mapping(target = "grupoPolitico", source = "object.grupoPolitico.nombre")
	VotoDto convertFrom(Voto object);

	@Override
	default Voto convertTo(VotoDto object) {
		return new Voto();
	}

}
