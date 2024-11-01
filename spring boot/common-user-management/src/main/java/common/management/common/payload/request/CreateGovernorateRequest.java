package common.management.common.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGovernorateRequest(
        @NotBlank
        String name,
        String nameAr,
        String nameEx,
        @NotNull
        Long regionId
) {
}
