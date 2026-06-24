package model;

public abstract class Facility extends CampusEntity {
    protected  double maintainanceCost;
    protected int usageFrequency;
    private static int totalFacility = 0;

    public Facility(String entityID, String name, String location, double maintainanceCost, int usageFrequency) {
        super(entityID, name, location);
        this.maintainanceCost = maintainanceCost;
        this.usageFrequency = usageFrequency;
        totalFacility++ ;
    }

    public static int getTotalFacility() {
        return  totalFacility;
    }

    @Override
    public double calculateOperationalCost() {
        return maintainanceCost + (usageFrequency * 20);
    }

    // Feed JTable later..
    public Object[] toTableRow() {
        return new Object[] {
                entityID,
                name,
                location,
                calculateOperationalCost()
        };
    }
}
