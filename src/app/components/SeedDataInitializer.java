package app.components;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.ItemDTO;
import app.dto.LocationDTO;
import app.dto.StudentDTO;
import app.entities.Item;
import app.entities.Location;
import app.entities.Student;
import app.repositories.ItemRepository;
import app.repositories.LocationRepository;
import app.repositories.StudentRepository;

@Component
public class SeedDataInitializer {

	@Autowired
	private StudentService studentService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private MatchingService matchingService;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ItemRepository itemRepository;

	@PostConstruct
	public void init() {
		// Only seed if database is empty
		if (studentRepository.count() == 0) {
			seedData();
		}
	}

	private void seedData() {
		// Create students
		StudentDTO student1 = new StudentDTO();
		student1.setFullName("Juan Dela Cruz");
		student1.setEmail("juan.delacruz@ateneo.edu");
		student1.setPhone("+639171234567");
		student1.setRole("STUDENT");
		student1 = studentService.createStudent(student1);

		StudentDTO student2 = new StudentDTO();
		student2.setFullName("Maria Santos");
		student2.setEmail("maria.santos@ateneo.edu");
		student2.setPhone("+639178765432");
		student2.setRole("STUDENT");
		student2 = studentService.createStudent(student2);

		StudentDTO admin = new StudentDTO();
		admin.setFullName("Admin User");
		admin.setEmail("admin@ateneo.edu");
		admin.setPhone("+639179999999");
		admin.setRole("ADMIN");
		admin = studentService.createStudent(admin);

		// Create locations
		LocationDTO loc1 = new LocationDTO();
		loc1.setBuilding("SEC");
		loc1.setRoom("B201");
		loc1.setLat(14.64);
		loc1.setLng(121.07);
		loc1 = locationService.create(loc1);

		LocationDTO loc2 = new LocationDTO();
		loc2.setBuilding("Faura");
		loc2.setRoom("102");
		loc2.setLat(14.64);
		loc2.setLng(121.07);
		loc2 = locationService.create(loc2);

		// Create items
		ItemDTO item1 = new ItemDTO();
		item1.setName("Blue Umbrella");
		item1.setCategory("PERSONAL_EFFECT");
		item1.setDescription("Foldable, with white stripes");
		item1.setTags("blue, umbrella, foldable");
		item1 = itemService.createItem(item1);

		ItemDTO item2 = new ItemDTO();
		item2.setName("Calculator");
		item2.setCategory("ELECTRONICS");
		item2.setDescription("Casio fx-991EX");
		item2.setTags("casio, 991ex, calculator");
		item2 = itemService.createItem(item2);

		ItemDTO item3 = new ItemDTO();
		item3.setName("Wallet");
		item3.setCategory("WALLET");
		item3.setDescription("Brown leather");
		item3.setTags("brown, leather, wallet");
		item3 = itemService.createItem(item3);

		// Create reports
		app.dto.LostReportDTO lostReport = new app.dto.LostReportDTO();
		lostReport.setReporterId(student1.getId());
		app.dto.ItemDTO lostItem = new app.dto.ItemDTO();
		lostItem.setName("Calculator");
		lostItem.setCategory("ELECTRONICS");
		lostItem.setDescription("Casio fx-991EX");
		lostItem.setTags("casio, 991ex");
		lostReport.setItem(lostItem);
		lostReport.setLocationId(loc1.getId());
		lostReport.setLastSeenAt(LocalDateTime.now().minusDays(2));
		lostReport.setNotes("Left after class");
		reportService.createLostReport(lostReport);

		app.dto.FoundReportDTO foundReport = new app.dto.FoundReportDTO();
		foundReport.setReporterId(student2.getId());
		foundReport.setItemData(item1);
		foundReport.setLocationId(loc2.getId());
		foundReport.setFoundAt(LocalDateTime.now().minusDays(1));
		foundReport.setNotes("Turned in to MVP guard");
		reportService.createFoundReport(foundReport);

		// Run matching
		matchingService.runMatchingBatch();
	}
}

