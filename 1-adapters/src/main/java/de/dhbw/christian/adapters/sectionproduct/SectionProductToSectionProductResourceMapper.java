package de.dhbw.christian.adapters.sectionproduct;

import de.dhbw.christian.adapters.product.ProductToProductResourceMapper;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;

import java.util.function.Function;

public class SectionProductToSectionProductResourceMapper implements Function<SectionProduct, OutputSectionProductResource> {
    @Override
    public OutputSectionProductResource apply(SectionProduct sectionProduct) {
        return map(sectionProduct);
    }

    public static OutputSectionProductResource map(SectionProduct sectionProduct) {
        return new OutputSectionProductResource(
                sectionProduct.getEan().toString(),
                sectionProduct.getAmount(),
                sectionProduct.getTray(),
                ProductToProductResourceMapper.map(sectionProduct.getProduct())
        );
    }
}
