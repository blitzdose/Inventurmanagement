package de.dhbw.christian.plugins.rest;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.adapters.product.JsonToProductMapper;
import de.dhbw.christian.adapters.product.ProductResource;
import de.dhbw.christian.adapters.product.ProductToProductResourceMapper;
import de.dhbw.christian.application.ProductApplicationService;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.javalin.extension.EndpointMapping;
import de.dhbw.christian.javalin.extension.JavalinController;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;

import java.util.List;

@JavalinController(endpoint = "/api/product")
public class ProductController {

    private final ProductApplicationService productApplicationService;

    public ProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "")
    public List<ProductResource> getProductList(Context context) {
        return productApplicationService.findAll().stream().map(new ProductToProductResourceMapper()).toList();
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/{EAN}")
    public ProductResource getProductByEAN(Context context) {
        return ProductToProductResourceMapper.map(productApplicationService.findByEAN(new EAN(context.pathParam("EAN"))));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/{EAN}")
    public ProductResource createProduct(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));

        if (productApplicationService.findByEAN(ean) != null) {
            throw new RuntimeException();
        }

        Product product = JsonToProductMapper.map(context.body());
        product.setEan(ean);
        product = productApplicationService.save(product);

        return ProductToProductResourceMapper.map(product);
    }

    @EndpointMapping(handlerType = HandlerType.PUT, endpoint = "/{EAN}")
    public ProductResource updateProduct(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));
        Product product = JsonToProductMapper.map(context.body());
        product.setEan(ean);

        product = productApplicationService.save(product);
        return ProductToProductResourceMapper.map(product);
    }

    @EndpointMapping(handlerType = HandlerType.DELETE, endpoint = "/{EAN}")
    public boolean deleteProduct(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));
        productApplicationService.deleteByEAN(ean);
        return true;
    }
}
