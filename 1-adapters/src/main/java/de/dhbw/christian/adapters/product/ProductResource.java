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

}
