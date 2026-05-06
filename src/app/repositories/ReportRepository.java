package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	
	List<Report> findByItemId(Long itemId);
	
	List<Report> findByTypeAndClaimedStatus(String type, String claimedStatus);
	
	List<Report> findByType(String type);
	
	long countByType(String type);
}

