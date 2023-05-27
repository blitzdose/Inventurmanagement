package de.dhbw.christian.adapters.product;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class ProductToProductResourceMapperTest {

    @Test
    public void testMap() {
        EAN ean = new EAN("9780201379624");
        String name = "Nutella";
        String brand = "Ferrero";
        BigDecimal price = BigDecimal.valueOf(3.75);
        Date date = Date.from(Instant.now());
        ArrayList<SectionProduct> sectionProducts = new ArrayList<>();

        Product product = new Product(ean, name, brand, price, date, sectionProducts);
        ProductResource productResource = new ProductResource(ean.toString(), name, brand, price, date);

        ProductResource productResourceFromMap = ProductToProductResourceMapper.map(product);

        assertEquals(productResource, productResourceFromMap);
    }
}
