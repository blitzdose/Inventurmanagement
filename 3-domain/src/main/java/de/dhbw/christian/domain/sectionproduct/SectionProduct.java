package de.dhbw.christian.domain.sectionproduct;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.section.Section;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Cacheable(false)
public class SectionProduct {

    @Id
    @Column(length = 13, name = "EAN")
    private EAN ean;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_EAN", nullable = false)
    private Product product;

    private long amount;

    private String tray;

    @ManyToOne
    @JoinColumn(name = "SECTION_NAME", nullable = false)
    private Section section;

    public static class CustomBuilder extends SectionProduct.SectionProductBuilder {
        @Override
        public SectionProduct build() {
            SectionProduct sectionProduct =  super.build();
            sectionProduct.setEan(sectionProduct.getProduct().getEan());
            return sectionProduct;
        }
    }
}
