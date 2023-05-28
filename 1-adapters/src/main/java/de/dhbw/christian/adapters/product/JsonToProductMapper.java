package de.dhbw.christian.adapters.product;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.dhbw.christian.abstraction.EAN.EAN;
import de.dhbw.christian.domain.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

public class JsonToProductMapper {

    public static Product map(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        ProductResource productResource = gson.fromJson(json, ProductResource.class);
        EAN ean = null;
        if (productResource.getEan() != null) {
            ean = new EAN(productResource.getEan());
        }
        if (productResource.getPrice() == null) {
            productResource.setPrice(BigDecimal.ZERO);
        }
        return new Product(ean, productResource.getName(), productResource.getBrand(), productResource.getPrice(), productResource.getExpirationDate(), new ArrayList<>());
    }
}
