package de.dhbw.christian.adapters.section;

import de.dhbw.christian.adapters.sectionproduct.SectionProductToSectionProductResourceMapper;
import de.dhbw.christian.domain.section.Section;

import java.util.function.Function;

public class SectionToSectionResourceMapper implements Function<Section, SectionResource> {
    @Override
    public SectionResource apply(Section section) {
        return map(section);
    }

    public static SectionResource map(Section section) {
        return new SectionResource(section.getName(), section.isTrayMandatory(), section.getSectionProducts().stream().map(new SectionProductToSectionProductResourceMapper()).toList());
    }
}
