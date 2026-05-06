package app.repositories;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.dto.MatchDTO;
import app.entities.Match;
import app.entities.Report;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

	ConcurrentLinkedQueue<Long> findByLostReportAndFoundReport(Report lostReport, Report foundReport);

	Collection<MatchDTO> findByStatus(String string);
	
}

