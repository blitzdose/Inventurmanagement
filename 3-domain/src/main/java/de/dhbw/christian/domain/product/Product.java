package de.dhbw.christian.domain.product;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable(false)
public class Product {

    @Id
    @Column(length = 13, name = "PRODUCT_EAN")
    private EAN ean;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private BigDecimal price = new BigDecimal(0);

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<SectionProduct> sectionProducts = new ArrayList<>();

    @Override
    public String toString() {
        return ean + ", " +
                name + ", " +
                brand + ", " +
                price + ", " +
                expirationDate;
    }
}
