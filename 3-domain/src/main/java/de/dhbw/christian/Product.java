package de.dhbw.christian;

import de.dhbw.christian.EAN.EAN;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class Product {

    @Column(length = 13)
    @Id
    private EAN ean;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private BigDecimal price = new BigDecimal(0);

    private Date expirationDate;

}
