package common.management.notification;

import org.springframework.transaction.annotation.Transactional;

public interface NotificationService {
    @Transactional
    int updateToken(String token);
}
