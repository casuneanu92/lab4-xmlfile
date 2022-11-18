package org.example.domain;

public class Entity<ID> {
    private ID idEntity;

    public Entity(ID idEntity) {
        this.idEntity = idEntity;
    }

    public Entity() {
    }

    public ID getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(ID idEntity) {
        this.idEntity = idEntity;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "idEntity=" + idEntity +
                '}';
    }
}
