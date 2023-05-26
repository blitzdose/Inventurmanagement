package de.dhbw.christian.application;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.domain.section.SectionDomainService;
import de.dhbw.christian.domain.section.SectionRepository;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;

import java.util.List;
import java.util.Optional;

public class SectionApplicationService {
    private  final SectionRepository sectionRepository;
    private final SectionDomainService sectionDomainService;

    public SectionApplicationService(SectionRepository sectionRepository, SectionDomainService sectionDomainService) {
        this.sectionRepository = sectionRepository;
        this.sectionDomainService = sectionDomainService;
    }

    public List<Section> findAll() {
        return this.sectionRepository.findAll();
    }
    public Section findByName(String name) {
        return this.sectionRepository.findByName(name);
    }
    public Section save(Section section) {
        return this.sectionRepository.save(section);
    }
    public void deleteByName(String name) {
        Section section = this.findByName(name);
        section.getSectionProducts().clear();
        this.save(section);
        this.sectionRepository.deleteByName(name);
    }

    public SectionProduct findByNameAndEAN(String name, EAN ean) {
        Section section = this.findByName(name);
        Optional<SectionProduct> optionalSectionProduct = section.getSectionProducts().stream().filter(sectionProduct -> sectionProduct.getEan().equals(ean)).findFirst();
        if (optionalSectionProduct.isPresent()) {
            return optionalSectionProduct.get();
        }
        throw new RuntimeException("Nicht gefunden");
    }

    public void addProduct(Section section, SectionProduct sectionProduct) {
        sectionDomainService.addProduct(section, sectionProduct);
        this.save(section);

    }

    public void deleteProduct(Section section, EAN ean) {
        sectionDomainService.deleteProduct(section, ean);
        this.save(section);
    }

    public void changeSection(Section oldSection, Section newSection, EAN ean) {
        sectionDomainService.changeSection(oldSection, newSection, ean);
        this.save(oldSection);
        this.save(newSection);
    }

    public SectionProduct changeTray(Section section, EAN ean, String tray) {
        return sectionDomainService.changeTray(section, ean, tray);
    }

    public SectionProduct importAmount(Section section, EAN ean, long amount) {
        return sectionDomainService.importAmount(section, ean, amount);
    }

    public SectionProduct sellAmount(Section section, EAN ean, long amount) {
        return sectionDomainService.sellAmount(section, ean, amount);
    }
}
