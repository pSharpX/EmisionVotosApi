package edu.cibertec.votoelectronico.mapping.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.cibertec.votoelectronico.domain.GrupoPolitico;
import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.dto.EmisionVotoDto;
import edu.cibertec.votoelectronico.mapping.EmisionVotoDtoMapper;
import edu.cibertec.votoelectronico.mapping.qualifiers.StringToGrupoPoliticoMapper;

@Mapper(componentModel = "cdi")
public interface MSEmisionVotoDtoMapper extends EmisionVotoDtoMapper {

	@Override
	default EmisionVotoDto convertFrom(Voto object) {
		return new EmisionVotoDto();
	}

	@Mapping(target = "dni", source = "object.dni")
	@Mapping(target = "fecha", source = "object.fecha", dateFormat = "yyyyMMddHHmmss") // dd-MM-yyyy HH:mm:ss
	@Mapping(target = "grupoPolitico", source = "object.grupoPolitico", qualifiedBy = StringToGrupoPoliticoMapper.class)
	Voto convertTo(EmisionVotoDto object);

	@StringToGrupoPoliticoMapper
	public static GrupoPolitico StringToGrupoPolitico(String object) {
		GrupoPolitico grupoPolitico = new GrupoPolitico();
		grupoPolitico.setNombre(object);
		return grupoPolitico;
	}
}
