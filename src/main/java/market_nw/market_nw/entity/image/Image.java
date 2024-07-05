package market_nw.market_nw.entity.image;


import lombok.Data;
import lombok.NoArgsConstructor;
import market_nw.market_nw.entity.AuditingEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
public class Image extends AuditingEntity { //이미지주소 db저장용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    private String imgUrl;

    public Image(String imageUrl) {
        this.imgUrl = imageUrl;
    }
}
