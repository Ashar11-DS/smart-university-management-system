package model;

import java.io.Serializable;

public abstract class CampusEntity implements Serializable {
    protected String entityID;
    protected String name;
    protected String location;

    public CampusEntity(String entityID, String name, String location) {
        this.entityID = entityID;
        this.name = name;
        this.location = location;
    }

    public abstract double calculateOperationalCost(); // abstract method

    // getters
    public String getEntityID() {
        return entityID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return  entityID + " - " + name + " (" + location + ") ";
    }

}
