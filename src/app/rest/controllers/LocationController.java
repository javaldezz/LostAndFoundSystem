package app.rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.LocationService;
import app.dto.LocationDTO;
import app.dto.StatusDTO;

@Component
@Path("/api/locations")
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocations() {
        List<LocationDTO> dtos = locationService.getAllLocations();
        return Response.ok(dtos).build();
    }

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocation(@PathParam("id") Long id) {
		LocationDTO dto = locationService.getById(id);
		return Response.ok(dto).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createLocation(LocationDTO dto) {
		LocationDTO result = locationService.create(dto);
		return Response.ok(result).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteLocation(@PathParam("id") Long id) {
		locationService.delete(id);
		return Response.ok(new StatusDTO("deleted")).build();
	}
}

