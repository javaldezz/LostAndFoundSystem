package app.components;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.StudentDTO;
import app.entities.Student;
import app.repositories.StudentRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	public List<StudentDTO> getAllStudents() {
	    return studentRepository.findAll().stream()
	        .map(student -> {
	             StudentDTO dto = new StudentDTO();
	             dto.setId(student.getId());
	             dto.setFullName(student.getFullName());
	             dto.setEmail(student.getEmail());
	             dto.setPhone(student.getPhone());
	             dto.setRole(student.getRole());
	             dto.setCreatedAt(student.getCreatedAt());
	             return dto;
	        })
	        .collect(Collectors.toList());
	}

	public StudentDTO getStudentById(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 user_not_found"));
		
		StudentDTO dto = new StudentDTO();
		dto.setId(student.getId());
		dto.setFullName(student.getFullName());
		dto.setEmail(student.getEmail());
		dto.setPhone(student.getPhone());
		dto.setRole(student.getRole());
		return dto;
	}

	public StudentDTO createStudent(StudentDTO dto) {
		// Validate email format
		if (dto.getEmail() == null || !dto.getEmail().endsWith("@ateneo.edu")) {
			throw new ServiceException("400 validation_error: Email must end with @ateneo.edu");
		}

		// Check email uniqueness
		Student existing = studentRepository.findByEmail(dto.getEmail());
		if (existing != null) {
			throw new ServiceException("409 email_exists");
		}

		// Validate fullName length
		if (dto.getFullName() == null || dto.getFullName().length() < 2 || dto.getFullName().length() > 200) {
			throw new ServiceException("400 validation_error: Full name must be between 2 and 200 characters");
		}

		Student student = new Student();
		student.setFullName(dto.getFullName());
		student.setEmail(dto.getEmail());
		student.setPhone(dto.getPhone());
		student.setRole(dto.getRole() != null ? dto.getRole() : "STUDENT");
		student.setCreatedAt(LocalDateTime.now());

		student = studentRepository.save(student);

		StudentDTO result = new StudentDTO();
		result.setId(student.getId());
		result.setFullName(student.getFullName());
		result.setEmail(student.getEmail());
		result.setPhone(student.getPhone());
		result.setRole(student.getRole());
		result.setCreatedAt(student.getCreatedAt());
		return result;
	}

	public void deleteStudent(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 student_not_found"));

		studentRepository.delete(student);
	}
}

