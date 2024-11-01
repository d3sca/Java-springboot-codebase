package common.management.notification;

import common.management.common.events.NotificationEvent;
import common.management.notification.model.Module;

import java.util.List;

public interface PushNotificationService {

    void send(NotificationEvent event, String message, Module module, List<String> notifyTo);
}
