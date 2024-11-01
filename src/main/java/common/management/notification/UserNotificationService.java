package common.management.notification;

import common.management.common.service.UserService;
import common.management.notification.model.FcmToken;
import common.management.notification.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static common.management.common.util.OperationStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserNotificationService implements common.management.notification.NotificationService {
    private final FcmTokenRepository fcmTokenRepository;
    private final UserService userService;

    @Transactional
    @Override
    public int updateToken(String token){
        var userId = userService.getAuthUserId();
        if(userId.isEmpty()) return OP_STATUS_FORBIDDEN;

        var fcmToken = fcmTokenRepository.findByUserId(userId.get());
        if(fcmToken.isEmpty()){
            return addNewToken(userId.get(),token);
        }
        fcmToken.get().updateToken(token);
        return OP_STATUS_SUCCESS;
    }

    private int addNewToken(String userId,String token){
        FcmToken fcmToken = new FcmToken(userId,token);
        fcmTokenRepository.save(fcmToken);
        return OP_STATUS_SUCCESS;
    }
}
