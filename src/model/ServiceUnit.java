package model;

public abstract class ServiceUnit extends CampusEntity{
    protected int serviceHours;
    protected int staffCount;

    public ServiceUnit(String entityID, String name, String location, int staffCount, int serviceHours) {
        super(entityID, name, location);
        this.staffCount = staffCount;
        this.serviceHours = serviceHours;
    }

    @Override
    public double calculateOperationalCost() {
        return  serviceHours * 30 + staffCount * 200;
    }
}
