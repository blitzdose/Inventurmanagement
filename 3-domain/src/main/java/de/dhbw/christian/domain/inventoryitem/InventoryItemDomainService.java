package de.dhbw.christian.domain.inventoryitem;

import de.dhbw.christian.domain.section.Section;

public class InventoryItemDomainService {

    public void sellAmount(InventoryItem inventoryItem, long amount) {
        if (inventoryItem.getAmount() - amount < 0) {
            throw new RuntimeException("Kann nicht mehr verkaufen, als vorhanden sind");
        }
        inventoryItem.setAmount(inventoryItem.getAmount() - amount);
    }

    public void importAmount(InventoryItem inventoryItem, long amount) {
        inventoryItem.setAmount(inventoryItem.getAmount() + amount);
    }

    public void changeTray(InventoryItem inventoryItem, String tray) {
        if (tray == null && inventoryItem.getSection().isTrayMandatory()) {
            throw new RuntimeException("Regal / Fach ist notwendig");
        }
        inventoryItem.setTray(tray);
    }

    public void changeSection(InventoryItem inventoryItem, Section section) {
        if (inventoryItem.getTray() == null && section.isTrayMandatory()) {
            throw new RuntimeException("Regal / Fach ist notwendig in neuem Bereich");
        }
        inventoryItem.setSection(section);
    }
}
