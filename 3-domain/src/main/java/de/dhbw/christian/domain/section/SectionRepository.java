package de.dhbw.christian.domain.section;

import java.util.List;

public interface SectionRepository {

    List<Section> findAll();

    Section findByName(String name);

    Section save(Section section);

    void deleteByName(String name);
}
