package de.dhbw.christian.domain.product;

import de.dhbw.christian.EAN.abstraction.EAN;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findByEAN(EAN ean);

    Product save(Product product);

    void deleteByEAN(EAN ean);
}
