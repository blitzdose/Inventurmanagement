package de.dhbw.christian.plugins.persistence.hibernate.InventoryItem;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.inventoryitem.InventoryItem;
import de.dhbw.christian.domain.inventoryitem.InventoryItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Parameter;
import jakarta.persistence.Persistence;

import java.util.List;

public class HibernateInventoryItemRepository implements InventoryItemRepository {
    final private EntityManager entityManager;

    public HibernateInventoryItemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<InventoryItem> findAllInventoryItems() {
        entityManager.clear();
        return entityManager.createQuery(
                "SELECT inventoryItem " +
                        "FROM InventoryItem inventoryItem", InventoryItem.class).getResultList();
    }

    @Override
    public List<InventoryItem> findAllBySectionName(String sectionName) {
        System.out.println(sectionName);


        return entityManager.createQuery(
                "SELECT inventoryItem " +
                        "FROM InventoryItem inventoryItem " +
                        "WHERE inventoryItem.section.name = :sectionName", InventoryItem.class).setParameter("sectionName", sectionName).getResultList();
    }

    @Override
    public List<InventoryItem> findAllByEAN(EAN ean) {
        return entityManager.createQuery(
                "SELECT inventoryItem " +
                        "FROM InventoryItem inventoryItem " +
                        "WHERE inventoryItem.product.ean = :ean", InventoryItem.class).setParameter("ean", ean).getResultList();
    }

    @Override
    public InventoryItem findByEANAndSectionName(EAN ean, String sectionName) {
        return entityManager.createQuery(
                "SELECT inventoryItem " +
                        "FROM InventoryItem inventoryItem " +
                        "WHERE inventoryItem.section.name = :sectionName " +
                        "AND inventoryItem.product.ean = :ean", InventoryItem.class)
                .setParameter("sectionName", sectionName).setParameter("ean", ean).getSingleResult();
    }

    @Override
    public InventoryItem findById(long id) {
        return entityManager.find(InventoryItem.class, id);
    }

    @Override
    public InventoryItem save(InventoryItem inventoryItem) {
        entityManager.getTransaction().begin();

        InventoryItem persistentInventoryItem = this.findById(inventoryItem.getId());
        if (persistentInventoryItem != null) {
            inventoryItem = this.merge(inventoryItem, persistentInventoryItem);
        } else {
            entityManager.persist(inventoryItem);
        }

        entityManager.flush();
        entityManager.getTransaction().commit();
        return inventoryItem;
    }

    private InventoryItem merge(InventoryItem inventoryItem, InventoryItem persistentInventoryItem) {
        entityManager.detach(persistentInventoryItem);
        persistentInventoryItem.setAmount(inventoryItem.getAmount());
        persistentInventoryItem.setTray(inventoryItem.getTray());
        persistentInventoryItem.setSection(inventoryItem.getSection());
        persistentInventoryItem.setProduct(inventoryItem.getProduct());
        inventoryItem = entityManager.merge(persistentInventoryItem);
        return inventoryItem;
    }

    @Override
    public void deleteById(long id) {
        entityManager.getTransaction().begin();
        InventoryItem inventoryItem = this.findById(id);
        entityManager.remove(inventoryItem);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
