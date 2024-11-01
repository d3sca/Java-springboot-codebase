package common.management.notification;

import common.management.common.events.NotificationEvent;
import common.management.notification.model.Module;
import common.management.notification.model.NotificationEntity;
import common.management.notification.repository.FcmTokenRepository;
import common.management.notification.repository.NotificationRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmService implements common.management.notification.PushNotificationService {
    private final FcmTokenRepository fcmTokenRepository;
    private final NotificationRepository notificationRepository;


    @Override
    public void send(NotificationEvent event, String message, Module module, List<String> notifyTo) {
        try {
            if(notifyTo == null || notifyTo.isEmpty()){
                log.error("[ERROR] notify to cannot be empty : {}",event);
                return;
            }

            var notification = new NotificationEntity().create(event, message, module, notifyTo);
            notificationRepository.save(notification);
//            sendMulticastMessage(notification);
        }catch (Exception e){
            log.error("[EXCEPTION] FcmService->send : {},{}",e.getMessage(),e);
        }
    }

    /*
    public void sendMulticastMessage(NotificationEntity request)
            throws  FirebaseMessagingException {
        MulticastMessage message = getPreconfiguredMessageToToken(request);
        BatchResponse response = FirebaseMessaging.getInstance(FirebaseApp.getInstance()).sendEachForMulticast(message);
        if(response.getFailureCount() > 0){
            log.error("FCM => failure count: {}",response.getFailureCount());
        }
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


    private MulticastMessage getPreconfiguredMessageToToken(NotificationEntity request) {
        return getPreconfiguredMessageBuilder(request)
                .build();
    }

    private MulticastMessage.Builder getPreconfiguredMessageBuilder(NotificationEntity request) {
        List<String> tokens = fcmTokenRepository.findAllByUserId(request.getNotifyTo());
        Notification notification = Notification.builder()
                .setTitle(request.getMessage())
                .build();
        return MulticastMessage.builder()
                .addAllTokens(tokens)
                .putData("entityId",request.getEntityId())
                .putData("module",request.getModule().name())
                .putData("event",request.getEvent().name())
                .setNotification(notification);
    }

     */
}
