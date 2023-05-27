package de.dhbw.christian.adapters.product;

import de.dhbw.christian.domain.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class JsonToProductMapperTest {

    String productJson = "{\"name\": \"Nutella\", \"brand\": \"Ferrero\"}";

    @Test
    public void testMap() {
        Product product = new Product();
        product.setName("Nutella");
        product.setBrand("Ferrero");
        product.setPrice(BigDecimal.ZERO);

        Product productFromMap = JsonToProductMapper.map(productJson);

        assertEquals(product.toString(), productFromMap.toString());
    }
}
