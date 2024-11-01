package common.management.common.service;

import common.management.common.model.Region;
import common.management.common.payload.request.CreateGovernorateRequest;
import common.management.common.payload.request.CreateRegionRequest;
import common.management.common.payload.response.GovernorateListResponse;
import common.management.common.payload.response.RegionListResponse;
import common.management.common.util.OpWrapper;

import java.util.List;

public interface AddressService {
    int createRegion(CreateRegionRequest request);

    int createGovernorate(CreateGovernorateRequest request);

    int updateRegion(CreateRegionRequest request, Long regionId);

    int updateGovernorate(CreateGovernorateRequest request, Long governorateId);

    int deleteRegion(Long regionId);

    int deleteGovernorate(Long governorateId);

    OpWrapper<List<RegionListResponse>> getAllRegions();

    OpWrapper<List<GovernorateListResponse>> getAllGovernorates();

    OpWrapper<Region> getOneRegionById(Long regionId);

    OpWrapper<GovernorateListResponse> getOneGovernorateById(Long governorateId);

    OpWrapper<List<GovernorateListResponse>> getGovernoratesByRegionId(Long regionId);
}
