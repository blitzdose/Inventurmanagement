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
@Cacheable(false)
public class SectionProduct {

    @Id
    @Column(length = 13, name = "EAN")
    private EAN ean;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_EAN", nullable = false)
    private Product product;

    private long amount = 0;

    private String tray;

    @ManyToOne
    @JoinColumn(name = "SECTION_NAME", nullable = false)
    private Section section;

    public static class SectionProductBuilder {
        private EAN ean;
        private Product product;
        private long amount;
        private String tray;
        private Section section;

        public SectionProductBuilder setProduct(Product product) {
            this.product = product;
            this.ean = product.getEan();
            return this;
        }

        public SectionProductBuilder setAmount(long amount) {
            this.amount = amount;
            return this;
        }

        public SectionProductBuilder setTray(String tray) {
            this.tray = tray;
            return this;
        }

        public SectionProductBuilder setSection(Section section) {
            this.section = section;
            return this;
        }

        public SectionProduct build() {
            return new SectionProduct(ean, product, amount, tray, section);
        }
    }
}
