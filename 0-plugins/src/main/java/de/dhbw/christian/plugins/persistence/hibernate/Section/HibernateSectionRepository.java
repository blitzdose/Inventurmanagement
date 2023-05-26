package de.dhbw.christian.plugins.persistence.hibernate.Section;

import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.domain.section.SectionRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class HibernateSectionRepository implements SectionRepository {

    final private EntityManager entityManager;

    public HibernateSectionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Section> findAll() {
        return entityManager.createQuery("SELECT section FROM Section section", Section.class).getResultList();
    }

    @Override
    public Section findByName(String name) {
        return entityManager.find(Section.class, name);
    }

    @Override
    public Section save(Section section) {
        entityManager.getTransaction().begin();
        Section persistentSection = entityManager.find(Section.class, section.getName());
        if (persistentSection == null) {
            entityManager.persist(section);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        return section;
    }

    @Override
    public void deleteByName(String name) {
        entityManager.getTransaction().begin();
        Section section = this.findByName(name);
        try {
            entityManager.remove(section);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Section does not exist");
        }
    }
}
