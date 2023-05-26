package de.dhbw.christian.plugins.persistence.hibernate.Section;

import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.domain.section.SectionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
        if (persistentSection != null) {
            section = merge(section, persistentSection);
        } else {
            entityManager.persist(section);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        return section;
    }

    private Section merge(Section section, Section persistentSection) {
        entityManager.detach(persistentSection);
        persistentSection.setTrayMandatory(section.isTrayMandatory());
        section = entityManager.merge(persistentSection);
        return section;
    }

    @Override
    public void deleteByName(String name) {
        entityManager.getTransaction().begin();
        Section section = this.findByName(name);
        entityManager.remove(section);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
