package common.management.notification.controller;

import common.management.common.util.OperationStatus;
import common.management.notification.UserNotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserNotificationController {
    private final OperationStatus operationStatus;
    private final UserNotificationService notificationService;

    @PostMapping("/token")
    public ResponseEntity<?> updateToken(@RequestBody @NotEmpty String token){
        return operationStatus.handle(notificationService.updateToken(token));
    }
}
