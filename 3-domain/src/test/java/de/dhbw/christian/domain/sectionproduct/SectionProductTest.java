package de.dhbw.christian.domain.sectionproduct;

import de.dhbw.christian.abstraction.EAN.EAN;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.section.Section;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SectionProductTest {

    @Test
    public void testCustomBuilder() {
        EAN ean = new EAN("9780201379624");
        Product product = new Product();
        product.setEan(ean);

        SectionProduct sectionProduct = new SectionProduct.SectionProductBuilder()
                .setSection(new Section())
                .setAmount(20)
                .setTray("Regal4Fach5")
                .setProduct(product)
                .build();

        assertEquals(ean, sectionProduct.getEan());
    }
}
