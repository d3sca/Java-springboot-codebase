package common.management.common.service.impl;

import common.management.common.model.Governorate;
import common.management.common.model.Region;
import common.management.common.payload.request.CreateGovernorateRequest;
import common.management.common.payload.request.CreateRegionRequest;
import common.management.common.payload.response.GovernorateListResponse;
import common.management.common.payload.response.RegionListResponse;
import common.management.common.repository.GovernorateRepository;
import common.management.common.repository.RegionRepository;
import common.management.common.service.AddressService;
import common.management.common.util.OpWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static common.management.common.util.OperationStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final GovernorateRepository governorateRepository;
    private final RegionRepository regionRepository;

    /*=========== REGIONS ===============*/
    @Override
    public int createRegion(CreateRegionRequest request){
        var region  = regionRepository.findByName(request.name());
        if(region.isPresent()){
            return OP_STATUS_REGION_WITH_SAME_NAME_EXISTS;
        }
        var newRegion = new Region().setNames(request);
        var saved =  regionRepository.save(newRegion);
        return OP_STATUS_SUCCESS;
    }

    @Override
    public int updateRegion(CreateRegionRequest request, Long regionId){
        var region  = regionRepository.findById(regionId);
        if(region.isEmpty()){
            return OP_STATUS_REGION_NOT_FOUND;
        }
        region.get().setNames(request);
        var saved =  regionRepository.save(region.get());
        return OP_STATUS_SUCCESS;
    }

    @Override
    public int deleteRegion(Long regionId){
        var region  = regionRepository.findById(regionId);
        if(region.isEmpty()){
            return OP_STATUS_REGION_NOT_FOUND;
        }
        region.get().setDeleted(true);
        regionRepository.save(region.get());
        return OP_STATUS_SUCCESS;
    }

    @Override
    public OpWrapper<List<RegionListResponse>> getAllRegions(){
        return new OpWrapper<>(OP_STATUS_SUCCESS,regionRepository.findAllCustom());
    }

    @Override
    public OpWrapper<Region> getOneRegionById(Long regionId){
        var region  = regionRepository.findById(regionId);
        if(region.isEmpty()) return new OpWrapper<>(OP_STATUS_REGION_NOT_FOUND,null);
        return new OpWrapper<>(OP_STATUS_SUCCESS,region.get());
    }


    /*=========== GIVERNORATES ===============*/

    @Override
    public int createGovernorate(CreateGovernorateRequest request){
        var region  = regionRepository.findById(request.regionId());
        if(region.isEmpty()) return OP_STATUS_REGION_NOT_FOUND;
        var newGov = new Governorate().setNames(request);
        newGov.setRegion(region.get());
        governorateRepository.save(newGov);
        return OP_STATUS_SUCCESS;
    }

    @Override
    public int updateGovernorate(CreateGovernorateRequest request, Long governorateId){
        var governorate  = governorateRepository.findById(governorateId);
        if(governorate.isEmpty()) return OP_STATUS_GOVERNORATE_NOT_FOUND;

        if(governorate.get().getRegion()== null || !governorate.get().getRegion().getId().equals(request.regionId())){
            var region  = regionRepository.findById(request.regionId());
            if(region.isEmpty()){
                return OP_STATUS_REGION_NOT_FOUND;
            }
            governorate.get().setRegion(region.get());
        }
        governorate.get().setNames(request);
        var saved =  governorateRepository.save(governorate.get());
        return OP_STATUS_SUCCESS;
    }

    @Override
    public int deleteGovernorate(Long governorateId){
        var governorate  = governorateRepository.findById(governorateId);
        if(governorate.isEmpty()){
            return OP_STATUS_GOVERNORATE_NOT_FOUND;
        }
        governorate.get().setDeleted(true);
        governorateRepository.save(governorate.get());
        return OP_STATUS_SUCCESS;
    }

    @Override
    public OpWrapper<List<GovernorateListResponse>> getAllGovernorates(){
        return new OpWrapper<>(OP_STATUS_SUCCESS,governorateRepository.findAllCustom());
    }

    @Override
    public OpWrapper<GovernorateListResponse> getOneGovernorateById(Long governorateId){
        var governorate  = governorateRepository.findByIdCustom(governorateId);
        if(governorate.isEmpty()) return new OpWrapper<>(OP_STATUS_GOVERNORATE_NOT_FOUND,null);
        return new OpWrapper<>(OP_STATUS_SUCCESS,governorate.get());
    }

    @Override
    public OpWrapper<List<GovernorateListResponse>> getGovernoratesByRegionId(Long regionId){
        return new OpWrapper<>(OP_STATUS_SUCCESS,governorateRepository.findAllByRegionId(regionId));
    }

}
