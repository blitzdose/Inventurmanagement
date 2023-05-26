package de.dhbw.christian.application;

import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.domain.section.SectionDomainService;
import de.dhbw.christian.domain.section.SectionRepository;

import java.util.List;

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
        sectionDomainService.checkTrayMandatoryAllowed(section);
        return this.sectionRepository.save(section);
    }
    public void deleteByName(String name) {
        this.sectionRepository.deleteByName(name);
    }
}
