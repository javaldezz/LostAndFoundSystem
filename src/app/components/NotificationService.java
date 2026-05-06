package app.components;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.NotificationLog;
import app.entities.Report;
import app.entities.Student;
import app.repositories.NotificationLogRepository;

@Component
public class NotificationService {

	@Autowired
	private NotificationLogRepository notificationLogRepository;

	public void notifyPotentialMatch(Student user, Report report, String link) {
		// Compose SMS text
		String messageBody = String.format(
				"Potential match found for your %s report (ID: %d). Check: %s",
				report.getType().toLowerCase(), report.getId(), link);

		// For skeleton, we'll just log the notification
		// In final, we will call TwilioClient.sendSms()
		try {
			// TwilioClient.sendSms(user.getPhone(), messageBody);
			// For now, just log as SENT
			NotificationLog log = new NotificationLog();
			log.setUser(user);
			log.setProvider("TWILIO");
			log.setProviderMessageId("placeholder_" + System.currentTimeMillis());
			log.setStatus("SENT");
			log.setMessageBody(messageBody);
			log.setSentAt(LocalDateTime.now());
			notificationLogRepository.save(log);
		} catch (Exception e) {
			NotificationLog log = new NotificationLog();
			log.setUser(user);
			log.setProvider("TWILIO");
			log.setStatus("FAILED");
			log.setMessageBody(messageBody);
			log.setSentAt(LocalDateTime.now());
			notificationLogRepository.save(log);
		}
	}

	public void notifyClaimStatus(Student reporter, Report report) {
		String messageBody = String.format("Your %s report (ID: %d) has been claimed.",
				report.getType().toLowerCase(), report.getId());

		try {
			NotificationLog log = new NotificationLog();
			log.setUser(reporter);
			log.setProvider("TWILIO");
			log.setProviderMessageId("placeholder_" + System.currentTimeMillis());
			log.setStatus("SENT");
			log.setMessageBody(messageBody);
			log.setSentAt(LocalDateTime.now());
			notificationLogRepository.save(log);
		} catch (Exception e) {
			NotificationLog log = new NotificationLog();
			log.setUser(reporter);
			log.setProvider("TWILIO");
			log.setStatus("FAILED");
			log.setMessageBody(messageBody);
			log.setSentAt(LocalDateTime.now());
			notificationLogRepository.save(log);
		}
	}
}

