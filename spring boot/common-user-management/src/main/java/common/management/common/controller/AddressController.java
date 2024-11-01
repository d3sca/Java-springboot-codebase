package common.management.common.controller;

import common.management.common.payload.request.CreateGovernorateRequest;
import common.management.common.payload.request.CreateRegionRequest;
import common.management.common.service.AddressService;
import common.management.common.util.OperationStatus;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class AddressController {
    private final OperationStatus operationStatus;
    private final AddressService addressService;

    /*======= REGION ============*/

    @PostMapping("/region")
    public ResponseEntity<?> createRegion(@Valid @RequestBody CreateRegionRequest request){
        return operationStatus.handle(addressService.createRegion(request));
    }

    @PutMapping("/region/{regionId}")
    public ResponseEntity<?> updateRegion(@Valid @RequestBody CreateRegionRequest request,@PathVariable(name = "regionId")Long regionId ){
        return operationStatus.handle(addressService.updateRegion(request,regionId));
    }

    @DeleteMapping("/region/{regionId}")
    public ResponseEntity<?> deleteRegion(@PathVariable(name = "regionId")Long regionId ){
        return operationStatus.handle(addressService.deleteRegion(regionId));
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<?> getOneRegion(@PathVariable(name = "regionId")Long regionId ){
        return operationStatus.handle(addressService.getOneRegionById(regionId));
    }

    @GetMapping("/region")
    public ResponseEntity<?> getAllRegions(){
        return operationStatus.handle(addressService.getAllRegions());
    }

    @GetMapping("/region/{regionId}/governorates")
    public ResponseEntity<?> getGovernoratesByRegionId(@PathVariable(name = "regionId")Long regionId){
        return operationStatus.handle(addressService.getGovernoratesByRegionId(regionId));
    }

    /*======= GOVERNORATE ============*/

    @PostMapping("/governorate")
    public ResponseEntity<?> createGovernorate(@Valid @RequestBody CreateGovernorateRequest request){
        return operationStatus.handle(addressService.createGovernorate(request));
    }

    @PutMapping("/governorate/{governorateId}")
    public ResponseEntity<?> updateGovernorate(@Valid @RequestBody CreateGovernorateRequest request, @PathVariable(name = "governorateId")Long governorateId ){
        return operationStatus.handle(addressService.updateGovernorate(request,governorateId));
    }

    @DeleteMapping("/governorate/{governorateId}")
    public ResponseEntity<?> deleteGovernorate(@PathVariable(name = "governorateId")Long governorateId ){
        return operationStatus.handle(addressService.deleteGovernorate(governorateId));
    }

    @GetMapping("/governorate/{governorateId}")
    public ResponseEntity<?> getOneGovernorate(@PathVariable(name = "governorateId")Long governorateId ){
        return operationStatus.handle(addressService.getOneGovernorateById(governorateId));
    }

    @GetMapping("/governorate")
    public ResponseEntity<?> getAllGovernorates(){
        return operationStatus.handle(addressService.getAllGovernorates());
    }


}
