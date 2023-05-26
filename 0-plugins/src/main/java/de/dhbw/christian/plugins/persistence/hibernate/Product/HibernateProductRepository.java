package de.dhbw.christian.plugins.persistence.hibernate.Product;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.product.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class HibernateProductRepository implements ProductRepository {
    final private EntityManager entityManager;

    public HibernateProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT product FROM Product product", Product.class).getResultList();
    }

    @Override
    public Product findByEAN(EAN ean) {
        return entityManager.find(Product.class, ean);
    }

    @Override
    public Product save(Product product) {
        entityManager.getTransaction().begin();
        Product persistentProduct = entityManager.find(Product.class, product.getEan());
        if (persistentProduct != null) {
            product = merge(product, persistentProduct);
        } else {
            entityManager.persist(product);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        return product;
    }

    private Product merge(Product product, Product persistentProduct) {
        entityManager.detach(persistentProduct);
        if (product.getName() != null) {
            persistentProduct.setName(product.getName());
        }
        if (product.getBrand() != null) {
            persistentProduct.setBrand(product.getBrand());
        }
        if (product.getPrice() != null) {
            persistentProduct.setPrice(product.getPrice());
        }
        if (product.getExpirationDate() != null) {
            persistentProduct.setExpirationDate(product.getExpirationDate());
        }
        product = entityManager.merge(persistentProduct);
        return product;
    }

    @Override
    public void deleteByEAN(EAN ean) {
        entityManager.getTransaction().begin();
        Product product = this.findByEAN(ean);
        entityManager.remove(product);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
