package de.dhbw.christian.adapters.sectionproduct;

import de.dhbw.christian.adapters.product.ProductResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutputSectionProductResource {
    private String ean;
    private long amount;
    private String tray;
    private ProductResource productResource;
}
