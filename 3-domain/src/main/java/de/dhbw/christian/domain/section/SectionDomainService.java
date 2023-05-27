package de.dhbw.christian.domain.section;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;

import java.util.Optional;

public class SectionDomainService {

    public void addProduct(Section section, SectionProduct sectionProduct) {
        if (section.isTrayMandatory() && sectionProduct.getTray() == null) {
            throw new RuntimeException("Regal / Fach ist notwendig");
        }
        if (section.getSectionProducts().stream().anyMatch(sectionProduct1 -> sectionProduct1.getEan().equals(sectionProduct.getEan()))) {
            throw new RuntimeException("Produkt existiert in Bereich");
        }
        section.getSectionProducts().add(sectionProduct);
    }

    public void deleteProduct(Section section, EAN ean) {
        section.getSectionProducts().remove(this.getSectionProductByEAN(section, ean));
    }

    public void changeSection(Section oldSection, Section newSection, EAN ean) {
        SectionProduct sectionProduct = getSectionProductByEAN(oldSection, ean);
        if (newSection.isTrayMandatory() && sectionProduct.getTray() == null) {
            throw new RuntimeException("Regal / Fach ist notwendig in neuem Bereich");
        }
        if (newSection.getSectionProducts().stream().anyMatch(sectionProduct1 -> sectionProduct1.getEan().equals(sectionProduct.getEan()))) {
            throw new RuntimeException("Produkt existiert in Bereich");
        }
        oldSection.getSectionProducts().remove(sectionProduct);
        newSection.getSectionProducts().add(sectionProduct);
    }

    public SectionProduct changeTray(Section section, EAN ean, String tray) {
        SectionProduct sectionProduct = getSectionProductByEAN(section, ean);
        if (section.isTrayMandatory() && tray == null) {
            throw new RuntimeException("Regal / Fach ist notwendig");
        }
        sectionProduct.setTray(tray);
        return sectionProduct;
    }

    public SectionProduct importAmount(Section section, EAN ean, long amount) {
        SectionProduct sectionProduct = getSectionProductByEAN(section, ean);
        sectionProduct.setAmount(sectionProduct.getAmount() + amount);
        return sectionProduct;
    }

    public SectionProduct sellAmount(Section section, EAN ean, long amount) {
        SectionProduct sectionProduct = getSectionProductByEAN(section, ean);

        if (sectionProduct.getAmount() - amount < 0) {
            throw new RuntimeException("Kann nicht mehr verkaufen, als vorhanden sind");
        }
        sectionProduct.setAmount(sectionProduct.getAmount() - amount);
        return sectionProduct;
    }

    private SectionProduct getSectionProductByEAN(Section section, EAN ean) {
        Optional<SectionProduct> optionalSectionProduct = section.getSectionProducts().stream().filter(sectionProduct -> sectionProduct.getEan().equals(ean)).findFirst();
        if (optionalSectionProduct.isEmpty()) {
            throw new RuntimeException("Produkt nicht gefunden");
        }
        return optionalSectionProduct.get();
    }
}
