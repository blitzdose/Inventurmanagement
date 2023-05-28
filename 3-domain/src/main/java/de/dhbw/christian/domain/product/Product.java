package de.dhbw.christian.domain.product;

import de.dhbw.christian.abstraction.EAN.EAN;
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
    public EAN ean;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String brand;

    @Column(nullable = false)
    public BigDecimal price = new BigDecimal(0);

    @Temporal(TemporalType.DATE)
    public Date expirationDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    public List<SectionProduct> sectionProducts = new ArrayList<>();

    @Override
    public String toString() {
        return ean + ", " +
                name + ", " +
                brand + ", " +
                price + ", " +
                expirationDate;
    }
}
