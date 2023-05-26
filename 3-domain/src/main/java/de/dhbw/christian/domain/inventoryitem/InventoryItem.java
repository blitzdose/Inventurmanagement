package de.dhbw.christian.domain.inventoryitem;

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
public class InventoryItem {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SECTION")
    private Section section;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PRODUCT_EAN")
    private Product product;

    @Column(nullable = false)
    private long amount;

    private String tray;

    @Id
    @GeneratedValue
    private Long id;

    public static class CustomInventoryItemBuilder extends InventoryItem.InventoryItemBuilder {
        public InventoryItem build() {
            InventoryItem inventoryItem = super.build();
            if (inventoryItem.section.isTrayMandatory() && inventoryItem.tray == null) {
                throw new RuntimeException("Regal / Fach ist notwendig");
            }
            return inventoryItem;
        }
    }

}
