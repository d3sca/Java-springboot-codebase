package common.management.notification.model;

import common.management.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FcmToken extends BaseEntity {
    @Column(unique = true,nullable = false)
    String userId;
    @Column(unique = true,nullable = false)
    String token;

    public void updateToken(String token){
        this.token = token;
    }
}
