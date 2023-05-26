package de.dhbw.christian.adapters.section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SectionResource {
    private String name;
    private boolean trayMandatory = false;
}
