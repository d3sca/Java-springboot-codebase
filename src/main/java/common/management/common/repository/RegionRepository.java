package common.management.common.repository;

import common.management.common.model.Region;
import common.management.common.payload.response.RegionListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {
    Optional<Region> findByName(String name);

    @Query("""
            SELECT r.id AS id, r.name AS name , r.nameAr AS nameAr,r.nameEx AS nameEx FROM Region r
            """)
    List<RegionListResponse> findAllCustom();
}
