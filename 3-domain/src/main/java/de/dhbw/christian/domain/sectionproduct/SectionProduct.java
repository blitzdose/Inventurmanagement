package de.dhbw.christian.domain.sectionproduct;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionProduct {

    @Id
    private EAN ean;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    private long amount;

    private String tray;

    public static class CustomBuilder extends SectionProduct.SectionProductBuilder {
        @Override
        public SectionProduct build() {
            SectionProduct sectionProduct =  super.build();
            sectionProduct.setEan(sectionProduct.getProduct().getEan());
            return sectionProduct;
        }
    }
}
