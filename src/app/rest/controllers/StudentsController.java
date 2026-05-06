package app.rest.controllers;

import java.util.List;

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

import app.components.StudentService;
import app.dto.StatusDTO;
import app.dto.StudentDTO;

@Component
@Path("/api/students")
public class StudentsController {

	@Autowired
	private StudentService studentService;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        List<StudentDTO> dtos = studentService.getAllStudents();
        return Response.ok(dtos).build();
    }

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("id") Long id) {
		StudentDTO dto = studentService.getStudentById(id);
		return Response.ok(dto).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStudent(StudentDTO dto) {
		StudentDTO result = studentService.createStudent(dto);
		return Response.ok(result).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("id") Long id) {
		studentService.deleteStudent(id);
		return Response.ok(new StatusDTO("deleted")).build();
	}
}

