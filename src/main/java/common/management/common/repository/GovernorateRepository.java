package common.management.common.repository;

import common.management.common.model.Governorate;
import common.management.common.payload.response.GovernorateListResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GovernorateRepository extends CrudRepository<Governorate,Long> {
    @Query("""
            SELECT g.id AS id, g.region.id AS regionId, g.name AS name , g.nameAr AS nameAr,g.nameEx AS nameEx FROM Governorate g
            """)
    List<GovernorateListResponse> findAllCustom();

    @Query("""
            SELECT g.id AS id, g.region.id AS regionId, g.name AS name , g.nameAr AS nameAr,g.nameEx AS nameEx FROM Governorate g
            WHERE g.id = :id
            """)
    Optional<GovernorateListResponse> findByIdCustom(Long id);


    @Query("""
            SELECT g.id AS id, g.region.id AS regionId, g.name AS name ,
            g.nameAr AS nameAr,g.nameEx AS nameEx FROM Governorate g
            WHERE g.region.id = :regionId
            """)
    List<GovernorateListResponse> findAllByRegionId(Long regionId);
}
