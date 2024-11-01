package common.management.common.payload.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRegionRequest(
        @NotBlank
        String name,
        String nameAr,
        String nameEx
) {
}
