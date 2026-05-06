package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	
	Location findByBuildingAndRoom(String building, String room);
}

