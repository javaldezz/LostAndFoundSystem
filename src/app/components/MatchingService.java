package app.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.Match;
import app.entities.Report;
import app.repositories.MatchRepository;
import app.repositories.ReportRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class MatchingService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private MatchRepository matchRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(MatchingService.class); 
    private ConcurrentLinkedQueue<Long> matchingQueue = new ConcurrentLinkedQueue<>();
	public void queueForMatching(Long reportId) {
		matchingQueue.offer(reportId);
		logger.info("Report ID {} queued for matching. Current queue size: {}", reportId, matchingQueue.size());
	}

	@Scheduled(fixedRate = 300000)
	public void runMatchingBatch() {
		List<Long> reportIds = new ArrayList<>();
		Long reportId;
		while ((reportId = matchingQueue.poll()) != null) {
			reportIds.add(reportId);
		}

		List<Report> lostReports = reportRepository.findByTypeAndClaimedStatus("LOST", "UNCLAIMED");
		List<Report> foundReports = reportRepository.findByTypeAndClaimedStatus("FOUND", "UNCLAIMED");
		
		logger.info("Matching Batch Status: Found {} UNCLAIMED LOST Reports.", lostReports.size());
        logger.info("Matching Batch Status: Found {} UNCLAIMED FOUND Reports.", foundReports.size());
        
		for (Report lostReport : lostReports) {
			for (Report foundReport : foundReports) {
				// Check if items match (same category and similar name)
				if (lostReport.getItem() != null && foundReport.getItem() != null) {
					if (lostReport.getItem().getCategory().equals(foundReport.getItem().getCategory())) {
						
						String lostTagsString = lostReport.getItem().getTags();
		                String foundTagsString = foundReport.getItem().getTags();
		                
						// Tag Matching Logic
		                Set<String> lostTags = new HashSet<String>(cleanAndSplitTags(lostTagsString));
		                Set<String> foundTags = new HashSet<String>(cleanAndSplitTags(foundTagsString));
		                
		                // Find Intersection (Common Tags)
		                Set<String> commonTags = new HashSet<>(lostTags);
		                commonTags.retainAll(foundTags);
		                
		                int sharedTagCount = commonTags.size();
		                
		                // Calculate Union Size (Total unique tags)
		                Set<String> unionTags = new HashSet<>(lostTags);
		                unionTags.addAll(foundTags);
		                int unionSize = unionTags.size();
		                
		                double similarityScore = 0.0;

		                if (unionSize > 0) {
		                    // Jaccard Index: Intersection / Union
		                    similarityScore = (double) sharedTagCount / unionSize;
		                }
		                
		                logger.info("Calculated Similarity Score: {}", similarityScore);
		                logger.info("Intersection: {}, Union: {}", sharedTagCount, unionSize);
		                
		                String commonTagsString = String.join(", ", commonTags);
		                
		                if (similarityScore >= 0.5) { 
		            		logger.info("Match Found");
	                    	if (matchRepository.findByLostReportAndFoundReport(lostReport, foundReport).isEmpty()) {
		                        Match match = new Match();
		                        match.setLostReport(lostReport);
		                        match.setFoundReport(foundReport);
		                        match.setStatus("NEW");
		                        match.setSimilarityScore(similarityScore); 
		                        match.setCommonTags(commonTagsString);
		                        matchRepository.save(match);
		                     }
		                }
					}
				}
			}
		}
	}
	
	private List<String> cleanAndSplitTags(String tagsString) {
        if (tagsString == null || tagsString.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        return Arrays.stream(tagsString.split(","))
            .map(String::trim)
            .filter(tag -> !tag.isEmpty())
            .collect(Collectors.toList());
    }
}

