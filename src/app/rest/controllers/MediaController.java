package app.rest.controllers;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.MediaService;
import app.dto.PhotoDTO;
import app.dto.StatusDTO;

@Component
@Path("/api/media/photos")
public class MediaController {

	Logger logger = LoggerFactory.getLogger(MediaController.class);
	
	@Autowired
	private MediaService mediaService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhoto(@PathParam("id") Long id) {
		PhotoDTO dto = mediaService.getPhoto(id);
		return Response.ok(dto).build();
	}

	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadPhoto(@FormDataParam("file") InputStream uploadedInputStream,
						 @FormDataParam("file") FormDataContentDisposition fileDetails, 
						 @FormDataParam("reportId") Long reportId,
						 @FormDataParam("itemId") Long itemId) 
	{
		PhotoDTO result = mediaService.uploadPhoto(uploadedInputStream, fileDetails.getFileName(), reportId, itemId);
		return Response.ok(result).build();
	} 
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePhoto(@PathParam("id") Long id) {
		mediaService.deletePhoto(id);
		return Response.ok(new StatusDTO("deleted")).build();
	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response uploadPhoto(
//			@javax.ws.rs.FormParam("reportId") Long reportId,
//			@javax.ws.rs.FormParam("itemId") Long itemId) {
//		// For skeleton, we'll create a placeholder photo without actual file upload
//		// In production, you would use proper multipart file handling
//		PhotoDTO result = mediaService.uploadPhoto(null, reportId, itemId);
//		return Response.ok(result).build();
//	}
	
}



