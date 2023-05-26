package de.dhbw.christian.domain.inventoryitem;

import de.dhbw.christian.EAN.abstraction.EAN;

import java.util.List;

public interface InventoryItemRepository {

    List<InventoryItem> findAllInventoryItems();

    List<InventoryItem> findAllBySectionName(String sectionName);

    List<InventoryItem> findAllByEAN(EAN ean);

    InventoryItem findByEANAndSectionName(EAN ean, String sectionName);

    InventoryItem findById(long id);

    InventoryItem save(InventoryItem inventoryItem);

    void deleteById(long id);

}
