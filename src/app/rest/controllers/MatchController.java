package app.rest.controllers;

import java.io.IOException;
import java.util.Arrays; 
import java.util.Collections; 
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors; 

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.ConversionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.dto.MatchDTO;
import app.dto.MatchDetailDTO;
import app.entities.Match;
import app.entities.Report;
import app.entities.Student;
import app.repositories.MatchRepository;
import app.components.MatchingService;
import app.components.TwilioComponent;
import app.components.MatchDetailMapper;

@Component
@Path("/api/matches")
public class MatchController {

	@Autowired
	private MatchDetailMapper matchDetailMapper; 
	
	@Autowired 
	private ConversionService conversionService;
	
	@Autowired 
	private TwilioComponent twilioComponent;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MatchingService matchingService;
	
	private static final Logger logger = LoggerFactory.getLogger(MatchingService.class); 

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNewMatches() {
        // 1. Run the matching process
        matchingService.runMatchingBatch(); 
        
        // 2. Fetch all matches and convert to list DTOs using ConversionService
        List<MatchDTO> dtos;
        
        dtos = matchRepository.findAll().stream()
                .map(match -> conversionService.convert(match, MatchDTO.class)) 
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    } 
	
    @GET
    @Path("/{matchId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchDetails(@PathParam("matchId") Long matchId) {
        try {
            Match match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new NotFoundException("Match not found with ID: " + matchId));

            MatchDetailDTO dto = matchDetailMapper.mapToMatchDetailDTO(match);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("Error fetching match details for {}: {}", matchId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Failed to retrieve match details.").build();
        }
    }

	
    @POST
    @Path("/{matchId}/claim")
    @Produces(MediaType.APPLICATION_JSON)
    public Response claimMatch(@PathParam("matchId") Long matchId) {
        try {
            Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("Match not found with ID: " + matchId));

            Report lostReport = match.getLostReport();
            
            Student lostStudent = lostReport.getStudent();
            String recipientPhone = null;

            if (lostStudent != null) {
                recipientPhone = lostStudent.getPhone();
            }
            
            if (recipientPhone == null || recipientPhone.trim().isEmpty()) {
                 logger.warn("Lost Report ID {} has no phone number. Skipping SMS.", lostReport.getId());
            } else {
                String message = String.format(
                    "Match Found! Your lost item '%s' may have been found. Check the Lost & Found system for Match #%d to verify.",
                    lostReport.getItem().getName(), 
                    matchId
                );
                twilioComponent.sendSMS(recipientPhone, message);
            }
            
            // Update and save match status
            match.setStatus("CLAIMED");
            matchRepository.save(match);

            return Response.ok("Claim status updated. Notification sent via SMS if phone number was available.").build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IOException e) { 
            logger.error("Error sending SMS for match {}: {}", matchId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Match claimed, but failed to send SMS notification: " + e.getMessage()).build();
        } catch (Exception e) { 
            logger.error("Error processing claim for match {}: {}", matchId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Failed to process claim.").build();
        }
    }
	
  
}