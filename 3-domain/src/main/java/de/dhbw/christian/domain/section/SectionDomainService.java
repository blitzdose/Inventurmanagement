package de.dhbw.christian.domain.section;

public class SectionDomainService {
    public void checkTrayMandatoryAllowed(Section section) throws RuntimeException {
        if (section.isTrayMandatory() && section.getInventoryItems().stream().anyMatch(inventoryItem -> inventoryItem.getTray() == null)) {
            throw new RuntimeException("Nicht alle Produkte in diesem Bereich haben ein Regal / Fach angegeben");
        }
    }
}
