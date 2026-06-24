package model;

public class Hostel extends  Facility{
    private int roomCount;

    public Hostel(String entityID, String name, String location, double maintainanceCost, int usageFrequency, int roomCount) {
        super(entityID, name, location, maintainanceCost, usageFrequency);
        this.roomCount = roomCount;
    }

    @Override
    public String toString() {
        return super.toString() + " | Rooms: " + roomCount;
    }
}
