package app.components;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.PhotoDTO;
import app.entities.EvidencePhoto;
import app.entities.Item;
import app.entities.Report;
import app.repositories.EvidencePhotoRepository;
import app.repositories.ItemRepository;
import app.repositories.ReportRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class MediaService {

	@Autowired
	private EvidencePhotoRepository photoRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	String UPLOAD_PATH = "temp/";
	
	public PhotoDTO getPhoto(Long id) {
		EvidencePhoto photo = photoRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 photo_not_found"));

		PhotoDTO dto = new PhotoDTO();
		dto.setPhotoId(photo.getPhotoId());
		dto.setUrl(photo.getUrl());
		dto.setChecksum(photo.getChecksum());
		dto.setUploadedAt(photo.getUploadedAt());

		PhotoDTO.LinkedToDTO linkedTo = new PhotoDTO.LinkedToDTO();
		if (photo.getReport() != null) {
			linkedTo.setReportId(photo.getReport().getId());
		}
		if (photo.getItem() != null) {
			linkedTo.setItemId(photo.getItem().getId());
		}
		dto.setLinkedTo(linkedTo);

		return dto;
	}

	public PhotoDTO uploadPhoto(InputStream fileInputStream, String fileName, Long reportId, Long itemId) {

		// Enforce XOR: exactly one of reportId or itemId
		if ((reportId == null && itemId == null) || (reportId != null && itemId != null)) {
			throw new ServiceException("400 link_required");
		}

		// Validate linked entity exists
		if (reportId != null) {
			reportRepository.findById(reportId)
					.orElseThrow(() -> new ServiceException("404 linked_entity_not_found"));
		}
		if (itemId != null) {
			itemRepository.findById(itemId)
					.orElseThrow(() -> new ServiceException("404 linked_entity_not_found"));
		}

		// creates a unique name
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        try {
        	byte[] fileData = fileInputStream.readAllBytes();

            String base64String = Base64.getEncoder().encodeToString(fileData);
            System.out.println("--- START BASE64 IMAGE DATA (truncated) ---");
            System.out.println(base64String.substring(0, Math.min(base64String.length(), 500)) + "...");
            System.out.println("--- END BASE64 IMAGE DATA ---");
            
        	File dir = new File(UPLOAD_PATH);
            dir.mkdirs(); // creates the 'temp/' directory 

            int read = 0;
            byte[] bytes = new byte[1024];

            File file = new File(dir, uniqueFileName); 
            
            OutputStream out = new FileOutputStream(file);
            while ((read = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

		EvidencePhoto photo = new EvidencePhoto();
		photo.setUrl(uniqueFileName); // <-- Store the filename in the 'url' field
		photo.setChecksum("sha256:not_implemented_yet");
		photo.setUploadedAt(LocalDateTime.now());

		if (reportId != null) {
			Report report = reportRepository.findById(reportId)
				.orElseThrow(() -> new ServiceException("500 internal_error: Failed to retrieve report"));
			photo.setReport(report);

			}

		if (itemId != null) {
			Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ServiceException("500 internal_error: Failed to retrieve item"));
			photo.setItem(item);

		}


		photo = photoRepository.save(photo);

		return getPhoto(photo.getPhotoId()); 
	}
    

	public void deletePhoto(Long id) {
		EvidencePhoto photo = photoRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 photo_not_found"));

		if (photo.getReport() != null && !"PENDING".equals(photo.getReport().getClaimedStatus())
				&& !"UNCLAIMED".equals(photo.getReport().getClaimedStatus())) {
			throw new ServiceException("409 cannot_delete_in_use");
		}

		photoRepository.delete(photo);
	}
}

