package com.itechart.lab.model;

public abstract class Entity {

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Entity entity = (Entity) object;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return 31 * id;
    }


    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
