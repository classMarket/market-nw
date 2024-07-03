package market_nw.market_nw.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "market_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String title;

    @Column(nullable = true)
    private String image;

    private String contents;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "deal_method")
    private String dealMethod;

    @Column(name = "offer_yn")
    private String offerYn;

    @Column(nullable = true)
    private String offerPrice;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "deal_hope_place")
    private String dealHopePlace;

    @Column(name = "read_cnt")
    private int readCnt;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    private String regId;

    @Column(name = "mod_date")
    private LocalDateTime modDate;

    private String modId;
}
