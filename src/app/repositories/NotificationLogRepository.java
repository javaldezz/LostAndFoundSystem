package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.NotificationLog;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
}

