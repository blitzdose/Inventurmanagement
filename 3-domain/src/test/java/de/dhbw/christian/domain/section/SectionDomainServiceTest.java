package de.dhbw.christian.domain.section;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SectionDomainServiceTest {

    @Mock
    Section sectionMock;

    @Mock
    Section section2Mock;

    @Mock
    SectionProduct sectionProductMock;

    @Test
    public void testAddProduct() {
        assertNotNull(sectionMock);
        assertNotNull(sectionProductMock);

        when(sectionMock.getSectionProducts()).thenReturn(new ArrayList<>());

        SectionDomainService sectionDomainService = new SectionDomainService();
        sectionDomainService.addProduct(sectionMock, sectionProductMock);

        assertEquals(1, sectionMock.getSectionProducts().size());
        assertEquals(sectionProductMock, sectionMock.getSectionProducts().get(0));

    }

    @Test
    public void testDeleteProduct() {
        assertNotNull(sectionMock);
        assertNotNull(sectionProductMock);

        when(sectionProductMock.getEan()).thenReturn(new EAN("9780201379624"));

        List<SectionProduct> sectionProductList = new ArrayList<>();
        sectionProductList.add(sectionProductMock);

        when(sectionMock.getSectionProducts()).thenReturn(sectionProductList);

        SectionDomainService sectionDomainService = new SectionDomainService();
        sectionDomainService.deleteProduct(sectionMock, sectionProductMock.getEan());

        assertEquals(0, sectionMock.getSectionProducts().size());
    }

    @Test
    public void testChangeSection() {
        assertNotNull(sectionMock);
        assertNotNull(sectionProductMock);
        assertNotNull(section2Mock);

        when(sectionProductMock.getEan()).thenReturn(new EAN("9780201379624"));

        List<SectionProduct> sectionProductList = new ArrayList<>();
        sectionProductList.add(sectionProductMock);

        when(sectionMock.getSectionProducts()).thenReturn(sectionProductList);
        when(section2Mock.getSectionProducts()).thenReturn(new ArrayList<>());

        SectionDomainService sectionDomainService = new SectionDomainService();
        sectionDomainService.changeSection(sectionMock, section2Mock, sectionProductMock.getEan());

        assertEquals(1, section2Mock.getSectionProducts().size());
        assertEquals(0, sectionMock.getSectionProducts().size());
        assertEquals(sectionProductMock, section2Mock.getSectionProducts().get(0));
    }
}
