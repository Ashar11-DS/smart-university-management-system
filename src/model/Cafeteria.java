package model;

public class Cafeteria extends Facility{

    private int seatingCapacity;

    public Cafeteria(String entityID, String name, String location, double maintainanceCost, int usageFrequency, int seatingCapacity) {
        super(entityID, name, location, maintainanceCost, usageFrequency);
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String toString() {
        return  super.toString() + " | Seating: " + seatingCapacity;
    }
}
