package de.dhbw.christian.aggregate;

import de.dhbw.christian.Product;
import de.dhbw.christian.Section;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InventoryItem {

    @OneToOne
    private Section section;

    @OneToOne
    private Product product;

    @Column(nullable = false)
    private long amount;

    private String tray;

    @Id
    @GeneratedValue
    private Long id;

    public long sellAmount(long amount) {
        if (this.amount - amount < 0) {
            throw new RuntimeException("Kann nicht mehr verkaufen, als vorhanden sind");
        }
        this.amount -= amount;
        return this.amount;
    }

    public long buyAmount(long amount) {
        this.amount += amount;
        return this.amount;
    }

    public static InventoryItem.InventoryItemBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends InventoryItem.InventoryItemBuilder {
        public InventoryItem build() {
            InventoryItem inventoryItem = super.build();
            if (inventoryItem.section.isTrayMandatory() && inventoryItem.tray == null) {
                throw new RuntimeException("Regal / Fach ist notwendig");
            }
            return inventoryItem;
        }
    }

}
