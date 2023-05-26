package de.dhbw.christian.application;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.inventoryitem.InventoryItem;
import de.dhbw.christian.domain.inventoryitem.InventoryItemDomainService;
import de.dhbw.christian.domain.inventoryitem.InventoryItemRepository;
import de.dhbw.christian.domain.section.Section;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class InventoryItemApplicationService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemDomainService inventoryItemDomainService;

    public InventoryItemApplicationService(InventoryItemRepository inventoryItemRepository, InventoryItemDomainService inventoryItemDomainService) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryItemDomainService = inventoryItemDomainService;
    }

    public List<InventoryItem> findAllInventoryItems() {
        return this.inventoryItemRepository.findAllInventoryItems();
    }

    public List<InventoryItem> findAllBySectionName(String sectionName) {
        return this.inventoryItemRepository.findAllBySectionName(sectionName);
    }

    public List<InventoryItem> findAllByEAN(EAN ean) {
        return this.inventoryItemRepository.findAllByEAN(ean);
    }

    public InventoryItem findByEANAndSectionName(EAN ean, String sectionName) {
        return this.inventoryItemRepository.findByEANAndSectionName(ean, sectionName);
    }

    public InventoryItem findById(long id) {
        return this.inventoryItemRepository.findById(id);
    }

    public InventoryItem save(InventoryItem inventoryItem) {
        return this.inventoryItemRepository.save(inventoryItem);
    }

    public void deleteByEANAndSectionName(EAN ean, String sectionName) {
        InventoryItem inventoryItem = this.findByEANAndSectionName(ean, sectionName);
        this.deleteById(inventoryItem.getId());
    }

    public void deleteById(long id) {
        this.inventoryItemRepository.deleteById(id);
    }

    public InventoryItem sellAmountById(long id, long amount) throws RuntimeException {
        InventoryItem inventoryItem = this.findById(id);
        return this.sellAmount(inventoryItem, amount);
    }

    public InventoryItem sellAmountByEANAndSectionName(EAN ean, String sectionName, long amount) {
        InventoryItem inventoryItem = this.findByEANAndSectionName(ean, sectionName);
        return this.sellAmount(inventoryItem, amount);
    }

    private InventoryItem sellAmount(InventoryItem inventoryItem, long amount) {
        this.inventoryItemDomainService.sellAmount(inventoryItem, amount);
        return this.save(inventoryItem);
    }

    public InventoryItem importAmountById(long id, long amount) {
        InventoryItem inventoryItem = this.findById(id);
        return this.importAmount(inventoryItem, amount);
    }

    public InventoryItem importAmountByEANAndSectionName(EAN ean, String sectionName, long amount) {
        InventoryItem inventoryItem = this.findByEANAndSectionName(ean, sectionName);
        return this.importAmount(inventoryItem, amount);
    }

    public InventoryItem importAmount(InventoryItem inventoryItem, long amount) {
        this.inventoryItemDomainService.importAmount(inventoryItem, amount);
        return this.save(inventoryItem);
    }

    public InventoryItem changeTrayById(long id, String tray) {
        InventoryItem inventoryItem = this.findById(id);
        return this.changeTray(inventoryItem, tray);
    }

    public InventoryItem changeTrayByEANAndSectionName(EAN ean, String sectionName, String tray) {
        InventoryItem inventoryItem = this.findByEANAndSectionName(ean, sectionName);
        return this.changeTray(inventoryItem, tray);
    }

    public InventoryItem changeTray(InventoryItem inventoryItem, String tray) {
        this.inventoryItemDomainService.changeTray(inventoryItem, tray);
        return this.save(inventoryItem);
    }

    public InventoryItem changeSectionByEANAndSectionName(EAN ean, String sectionName, Section section) {
        InventoryItem inventoryItem = this.findByEANAndSectionName(ean, sectionName);
        return this.changeSection(inventoryItem, section);
    }

    public InventoryItem changeSectionById(long id, Section section) {
        InventoryItem inventoryItem = this.findById(id);
        return this.changeSection(inventoryItem, section);
    }

    public InventoryItem changeSection(InventoryItem inventoryItem, Section section) {
        this.inventoryItemDomainService.changeSection(inventoryItem, section);
        return this.save(inventoryItem);
    }
}
