package de.dhbw.christian.application;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.product.ProductRepository;

import java.util.List;

public class ProductApplicationService {
    private  final ProductRepository productRepository;

    public ProductApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }
    public Product findByEAN(EAN ean) {
        return this.productRepository.findByEAN(ean);
    }
    public Product save(Product product) {
        return this.productRepository.save(product);
    }
    public void deleteByEAN(EAN ean) {
        this.productRepository.deleteByEAN(ean);
    }
}
