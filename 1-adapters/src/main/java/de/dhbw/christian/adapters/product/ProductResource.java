package de.dhbw.christian.adapters.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ProductResource {

    private String ean;
    private String name;
    private String brand;
    private BigDecimal price;
    private Date expirationDate;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductResource productResource) {
            return productResource.getEan().equals(this.getEan()) &&
                    productResource.getName().equals(this.getName()) &&
                    productResource.getBrand().equals(this.getBrand()) &&
                    productResource.getPrice().equals(this.getPrice()) &&
                    productResource.getExpirationDate().equals(this.getExpirationDate());
        }
        return false;
    }
}
