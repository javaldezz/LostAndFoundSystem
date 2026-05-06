package app.rest.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.ItemService;
import app.components.MediaService;
import app.dto.ItemDTO;
import app.dto.PhotoDTO;
import app.dto.StatusDTO;
import app.entities.EvidencePhoto;
import app.entities.Item;
import app.repositories.EvidencePhotoRepository;
import app.repositories.ItemRepository;

@Component
@Path("/api/items")
public class ItemsController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private EvidencePhotoRepository photoRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllItems() {
		List<ItemDTO> items = itemService.getAllItems();
		return Response.ok(items).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("id") Long id) {
		ItemDTO dto = itemService.getItem(id);
		
		// Get item entity for timestamps
		app.entities.Item item = itemRepository.findById(id).orElse(null);

		// Load photos
		List<EvidencePhoto> photos = photoRepository.findByItemId(id);
		List<PhotoDTO> photoDTOs = photos.stream().map(photo -> {
			PhotoDTO photoDTO = new PhotoDTO();
			photoDTO.setPhotoId(photo.getPhotoId());
			photoDTO.setUrl(photo.getUrl());
			return photoDTO;
		}).collect(Collectors.toList());

		// Create response with photos
		ItemResponseDTO response = new ItemResponseDTO();
		response.setId(dto.getId());
		response.setName(dto.getName());
		response.setCategory(dto.getCategory());
		response.setDescription(dto.getDescription());
		response.setStatus(dto.getStatus());
		response.setTags(dto.getTags());
		response.setPhotos(photoDTOs);
		if (item != null) {
			response.setCreatedAt(item.getCreatedAt());
			response.setUpdatedAt(item.getUpdatedAt());
		}

		return Response.ok(response).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createItem(ItemDTO dto) {
		ItemDTO result = itemService.createItem(dto);
		
		// Get item entity for timestamps
		Item item = itemRepository.findById(result.getId()).orElse(null);
		
		ItemResponseDTO response = new ItemResponseDTO();
		response.setId(result.getId());
		response.setName(result.getName());
		response.setCategory(result.getCategory());
		response.setDescription(result.getDescription());
		response.setTags(result.getTags());
		response.setStatus(result.getStatus());
		if (item != null) {
			response.setCreatedAt(item.getCreatedAt());
			response.setUpdatedAt(item.getUpdatedAt());
		}
		return Response.ok(response).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("id") Long id) {
		itemService.deleteItem(id);
		return Response.ok(new StatusDTO("deleted")).build();
	}

	// Helper class for response
	public static class ItemResponseDTO extends ItemDTO {
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private List<PhotoDTO> photos = new ArrayList<>();

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		public List<PhotoDTO> getPhotos() {
			return photos;
		}

		public void setPhotos(List<PhotoDTO> photos) {
			this.photos = photos;
		}
	}
}

