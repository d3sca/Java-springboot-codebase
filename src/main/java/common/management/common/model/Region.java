package common.management.common.model;

import common.management.common.payload.request.CreateRegionRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regions")
@NoArgsConstructor
@Getter
@Setter
public class Region extends Localize{

    public Region setNames(CreateRegionRequest request){
        this.setName(request.name());
        this.setNameAr(request.nameAr());
        this.setNameEx(request.nameEx());
        return this;
    }
}
