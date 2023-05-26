package de.dhbw.christian.adapters.inventoryItem;

import de.dhbw.christian.domain.inventoryitem.InventoryItem;

import java.util.function.Function;

public class InventoryItemToInventoryItemResourceMapper implements Function<InventoryItem, InventoryItemResource> {
    @Override
    public InventoryItemResource apply(InventoryItem inventoryItem) {
        return map(inventoryItem);
    }

    public static InventoryItemResource map(InventoryItem inventoryItem) {
        return new InventoryItemResource(
                inventoryItem.getId(),
                inventoryItem.getProduct().getEan().toString(),
                inventoryItem.getSection().getName(),
                inventoryItem.getAmount(),
                inventoryItem.getTray()
        );
    }
}