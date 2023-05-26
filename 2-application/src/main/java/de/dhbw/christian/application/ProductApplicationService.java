package de.dhbw.christian.application;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.product.ProductRepository;

import java.util.List;

public class ProductApplicationService {
    private final ProductRepository productRepository;
    private final SectionApplicationService sectionApplicationService; //Pretty sure that's not the right way, but I was not able to cascade delete the sectionProducts

    public ProductApplicationService(ProductRepository productRepository, SectionApplicationService sectionApplicationService) {
        this.productRepository = productRepository;
        this.sectionApplicationService = sectionApplicationService;
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
        sectionApplicationService.findAll().forEach(section -> sectionApplicationService.deleteProduct(section, ean));
        this.productRepository.deleteByEAN(ean);
    }
}
