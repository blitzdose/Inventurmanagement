package de.dhbw.christian.adapters.inventoryItem;

import de.dhbw.christian.domain.inventoryitem.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryItemResource {
    private long id;
    private String ean;
    private String sectionName;
    private long amount = 0;
    private String tray = null;
}
