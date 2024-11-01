package common.management.common.model;

import common.management.common.payload.request.CreateGovernorateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "governorates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Governorate extends Localize{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id",referencedColumnName = "id")
    private Region region;

    public Governorate setNames(CreateGovernorateRequest request){
        this.setName(request.name());
        this.setNameAr(request.nameAr());
        this.setNameEx(request.nameEx());
        return this;
    }
}
