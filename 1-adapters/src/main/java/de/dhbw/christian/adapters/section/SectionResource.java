package de.dhbw.christian.adapters.section;

import de.dhbw.christian.adapters.sectionproduct.OutputSectionProductResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SectionResource {
    private String name;
    private boolean trayMandatory;
    private List<OutputSectionProductResource> sectionProductResourceList;
}
