package common.management.notification.processor;

import common.management.common.events.NotificationEvent;
import common.management.notification.PushNotificationService;
import common.management.notification.external.NotificationStaffService;
import common.management.notification.model.Module;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerNotificationProcessor implements common.management.notification.processor.NotificcationEventProcessor {
    private final NotificationStaffService staffService;
    private final PushNotificationService pushNotificationService;
    private final Module module = Module.customer;

    @Override
    public void process(NotificationEvent event) {
        if (event.entityId().isEmpty() || !(event.entityId().get() instanceof Long) ) {
            log.error("Entity id not valid or not found");
            return;
        }
        var message = getMessage(event);
        var notifyTo = getNotifyTo(event);

        pushNotificationService.send(event, message, module, notifyTo);
    }

    private String getMessage(NotificationEvent event) {
        return switch (event.event()) {
            case customer_signup -> "New customer registered";
            default -> "Action on customer";
        };
    }

    private List<String> getNotifyTo(NotificationEvent event){
        List<String> notifyTo = new ArrayList<>();
        //write logic here to determine receivers of this notification

        return notifyTo;
    }

}
