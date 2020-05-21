package edu.cibertec.votoelectronico.mapping.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.cibertec.votoelectronico.domain.complex.VotoResumen;
import edu.cibertec.votoelectronico.dto.VotoResumenDto;
import edu.cibertec.votoelectronico.mapping.VotoResumenDtoMapper;

@Mapper(componentModel = "cdi")
public interface MSVotoResumenDtoMapper extends VotoResumenDtoMapper {

	@Mapping(target = "grupoPolitico", source = "object.grupoPolitico")
	@Mapping(target = "cantidad", source = "object.cantidad")
	VotoResumenDto convertFrom(VotoResumen object);

	@Override
	default VotoResumen convertTo(VotoResumenDto object) {
		return new VotoResumen();
	}

}
