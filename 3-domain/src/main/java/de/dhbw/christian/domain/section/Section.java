package de.dhbw.christian.domain.section;

import de.dhbw.christian.domain.inventoryitem.InventoryItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Section {

    @Id
    @Column(name = "SECTION_NAME")
    private String name;

    private boolean trayMandatory = false;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<InventoryItem> inventoryItems = new ArrayList<>();

}
