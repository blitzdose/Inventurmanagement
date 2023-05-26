package de.dhbw.christian.domain.section;

import de.dhbw.christian.domain.sectionproduct.SectionProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "section", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<SectionProduct> sectionProducts = new ArrayList<>();

}
