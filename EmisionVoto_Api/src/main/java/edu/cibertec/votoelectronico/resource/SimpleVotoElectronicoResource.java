package edu.cibertec.votoelectronico.resource;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.application.qualifier.VotoServiceQualifier;
import edu.cibertec.votoelectronico.domain.GrupoPolitico;
import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;
import edu.cibertec.votoelectronico.dto.EmisionVotoDto;
import edu.cibertec.votoelectronico.dto.VotoDto;
import edu.cibertec.votoelectronico.dto.VotoResumenDto;
import edu.cibertec.votoelectronico.mapping.MapperFactoryRegistry;
import edu.cibertec.votoelectronico.resource.communication.EmisionVotoResponse;
import edu.cibertec.votoelectronico.resource.communication.ListVotoResponse;
import edu.cibertec.votoelectronico.resource.communication.ResumenProcesoResponse;
import edu.cibertec.votoelectronico.service.GrupoPoliticoService;
import edu.cibertec.votoelectronico.service.VotoService;

@Path("/votoelectronico")
public class SimpleVotoElectronicoResource implements VotoElectronicoResource {

	private final Logger LOG = LoggerFactory.getLogger(SimpleVotoElectronicoResource.class);

	@Context
	UriInfo uriInfo;

	@Inject
	private ValidatorFactory factory;

	@Inject
	private @VotoServiceQualifier VotoService votoService;
	@Inject
	private GrupoPoliticoService grupoPoliticoService;
	@Inject
	private MapperFactoryRegistry mapper;

	@PostConstruct
	public void init() {
		LOG.info("On Init method...");
	}

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

	@Override
	public Response obtenerResultados() {
		try {
			Collection<VotoResumen> resultados = this.votoService.results();
			Function<VotoResumen, VotoResumenDto> convert = (VotoResumen object) -> this.mapper.convertFrom(object,
					VotoResumenDto.class);
			Collection<VotoResumenDto> collection = resultados.stream().map(convert).collect(Collectors.toList());
			return Response.status(Response.Status.OK).entity(new ResumenProcesoResponse(collection)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new ResumenProcesoResponse(e.getMessage())).build();
		}
	}

	@Override
	public Response obtenerResultados(String grupoPolitico) {
		try {
			Collection<VotoResumen> resultados = this.votoService.results(grupoPolitico);
			Function<VotoResumen, VotoResumenDto> convert = (VotoResumen object) -> this.mapper.convertFrom(object,
					VotoResumenDto.class);
			Collection<VotoResumenDto> collection = resultados.stream().map(convert).collect(Collectors.toList());
			return Response.status(Response.Status.OK).entity(new ResumenProcesoResponse(collection)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new ResumenProcesoResponse(e.getMessage())).build();
		}
	}

	@Override
	public Response emitirVoto(EmisionVotoDto resource) {
		try {
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<EmisionVotoDto>> violations = validator.validate(resource);
			if (violations.size() > 0) {
				StringBuilder errors = new StringBuilder();
				for (ConstraintViolation<EmisionVotoDto> violation : violations) {
					errors.append(violation.getMessage() + ", ");
				}
				return Response.status(Response.Status.BAD_REQUEST).entity(new EmisionVotoResponse(errors.toString()))
						.build();
			}

			GrupoPolitico grupoPolitico = this.grupoPoliticoService.findByName(resource.getGrupoPolitico());
			if (Objects.isNull(grupoPolitico)) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(new EmisionVotoResponse("No existe grupo politico.")).build();
			}
			Voto voto = this.mapper.convertTo(resource, Voto.class);
			voto.setGrupoPolitico(grupoPolitico);
			this.votoService.emitirVoto(voto);
			VotoDto votoDto = this.mapper.convertFrom(voto, VotoDto.class);
			return Response.status(Response.Status.CREATED).entity(new EmisionVotoResponse(votoDto)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new EmisionVotoResponse(e.getMessage())).build();
		}
	}

	@Override
	public Response obtener() {
		try {
			Collection<Voto> votos = this.votoService.list();
			Function<Voto, VotoDto> convert = (Voto object) -> this.mapper.convertFrom(object, VotoDto.class);
			Collection<VotoDto> collection = votos.stream().map(convert).collect(Collectors.toList());
			return Response.status(Response.Status.OK).entity(new ListVotoResponse(collection)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ListVotoResponse(e.getMessage()))
					.build();
		}
	}

}
