package de.dhbw.christian.adapters.product;

import de.dhbw.christian.domain.product.Product;

import java.util.function.Function;

public class ProductToProductResourceMapper implements Function<Product, ProductResource> {
    @Override
    public ProductResource apply(Product product) {
        return map(product);
    }

    public static ProductResource map(Product product) {
        return new ProductResource(product.getEan().toString(), product.getName(), product.getBrand(), product.getPrice(), product.getExpirationDate());
    }
}
