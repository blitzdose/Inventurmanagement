package de.dhbw.christian.plugins.persistence.hibernate.Product;

import de.dhbw.christian.abstraction.EAN.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.product.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
        Arrays.stream(product.getClass().getDeclaredFields()).forEach(new Consumer<>() {
            @Override
            @SneakyThrows
            public void accept(Field field) {
                if (field.get(product) != null && !field.get(product).equals(BigDecimal.ZERO)) {
                    persistentProduct.getClass().getDeclaredField(field.getName()).set(persistentProduct, field.get(product));
                }
            }
        });
        return entityManager.merge(persistentProduct);
    }

    @Override
    public void deleteByEAN(EAN ean) {
        entityManager.getTransaction().begin();
        Product product = this.findByEAN(ean);
        try {
            entityManager.remove(product);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
    }
}
