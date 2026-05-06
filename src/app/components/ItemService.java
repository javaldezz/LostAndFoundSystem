package app.components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.dto.ItemDTO;
import app.entities.EvidencePhoto;
import app.entities.Item;
import app.repositories.EvidencePhotoRepository;
import app.repositories.ItemRepository;
import app.repositories.ReportRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private EvidencePhotoRepository photoRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Transactional(readOnly = true)
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll(); 
        
        return items.stream()
                .map(item -> {
                    ItemDTO dto = new ItemDTO();
                    dto.setId(item.getId());
                    dto.setName(item.getName());
                    dto.setCategory(item.getCategory());
                    dto.setDescription(item.getDescription()); 
                    dto.setTags(item.getTags());
                    dto.setStatus(item.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
	public ItemDTO getItem(Long id) {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 item_not_found"));

		ItemDTO dto = new ItemDTO();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setCategory(item.getCategory());
		dto.setDescription(item.getDescription());
		dto.setTags(item.getTags());
		dto.setStatus(item.getStatus());

		return dto;
	}

	public ItemDTO createItem(ItemDTO dto) {
		// Validate name
		if (dto.getName() == null || dto.getName().length() < 2 || dto.getName().length() > 120) {
			throw new ServiceException("400 validation_error: Name must be between 2 and 120 characters");
		}

		// Validate category
		if (dto.getCategory() == null) {
			throw new ServiceException("400 validation_error: Category is required");
		}


		Item item = new Item();
		item.setName(dto.getName());
		item.setCategory(dto.getCategory());
		item.setDescription(dto.getDescription());
		item.setTags(dto.getTags());
		item.setStatus("ACTIVE");
		item.setCreatedAt(LocalDateTime.now());
		item.setUpdatedAt(LocalDateTime.now());

		item = itemRepository.save(item);

		ItemDTO result = new ItemDTO();
		result.setId(item.getId());
		result.setName(item.getName());
		result.setCategory(item.getCategory());
		result.setDescription(item.getDescription());
		result.setTags(item.getTags());
		result.setStatus(item.getStatus());
		return result;
	}

	public void deleteItem(Long id) {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 item_not_found"));

		List<app.entities.Report> reports = reportRepository.findByItemId(id);
		boolean hasOpenReports = reports.stream()
				.anyMatch(r -> !"CLAIMED".equals(r.getClaimedStatus()));
		if (hasOpenReports) {
			throw new ServiceException("409 has_open_reports");
		}

		itemRepository.delete(item);
	}

   public ItemDTO mapToItemDTO(Item item) {
       if (item == null) {
           return null;
       }

       ItemDTO dto = new ItemDTO();
       dto.setId(item.getId()); 
       dto.setName(item.getName());
       dto.setCategory(item.getCategory());
       dto.setDescription(item.getDescription());
       dto.setTags(item.getTags()); 
       
       return dto;
   }
}
