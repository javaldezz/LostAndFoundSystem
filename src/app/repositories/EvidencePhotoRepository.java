package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.EvidencePhoto;

@Repository
public interface EvidencePhotoRepository extends JpaRepository<EvidencePhoto, Long> {
	
	List<EvidencePhoto> findByReportId(Long reportId);
	
	List<EvidencePhoto> findByItemId(Long itemId);
}

