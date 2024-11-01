package common.management.common.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Address{
        @NotNull
        @ManyToOne
        Governorate governorate;
}
