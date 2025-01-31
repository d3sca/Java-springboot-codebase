package common.management.common.repository;

import common.management.common.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUserId(String id);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
