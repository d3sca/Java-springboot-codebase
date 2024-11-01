package common.management.notification.repository;

import common.management.notification.model.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface FcmTokenRepository extends JpaRepository<FcmToken,Long> {
    @Query("""
            SELECT t.token FROM FcmToken t
            WHERE t.userId IN (:userIds)
            """)
    List<String> findAllByUserId(List<String> userIds);

    Optional<FcmToken> findByUserId(String userId);
}
