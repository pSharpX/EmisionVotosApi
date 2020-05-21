package edu.cibertec.votoelectronico.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cibertec.votoelectronico.dto.EmisionVotoDto;

@Path("/votoelectronico")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface VotoElectronicoResource {

	@GET
	@Path("/resultado")
	public Response obtenerResultados();

	@GET
	@Path("/resultado/{grupoPolitico}")
	public Response obtenerResultados(@PathParam("grupoPolitico") String grupoPolitico);

	@GET
	@Path("/")
	public Response obtener();

	@POST
	@Path("/emitir")
	public Response emitirVoto(@Valid @NotNull EmisionVotoDto emisionVotoDto);

}
