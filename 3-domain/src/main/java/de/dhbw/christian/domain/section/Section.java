package de.dhbw.christian.domain.section;

import de.dhbw.christian.domain.sectionproduct.SectionProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Cacheable(false)
public class Section {

    @Id
    @Column(name = "SECTION_NAME")
    private String name;

    private boolean trayMandatory = false;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "section")
    private List<SectionProduct> sectionProducts = new ArrayList<>();

}
