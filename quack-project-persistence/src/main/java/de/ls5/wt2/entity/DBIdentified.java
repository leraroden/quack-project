package de.ls5.wt2.entity;

import javax.persistence.*;

@MappedSuperclass
public class DBIdentified {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

}
