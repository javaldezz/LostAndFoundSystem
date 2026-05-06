package app.rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 
import app.components.DashboardService;
import app.dto.DashboardSummaryDTO;

@Component
@Path("/api/dashboard")
public class DashboardController {

    @Autowired 
    private DashboardService dashboardService;

    @GET
    @Path("/counts") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboardCounts() {
        DashboardSummaryDTO summary = dashboardService.getSummaryCounts();
        return Response.ok(summary).build();
    }
}