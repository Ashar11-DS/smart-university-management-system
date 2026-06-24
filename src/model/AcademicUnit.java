package model;

public abstract class AcademicUnit extends CampusEntity {
    protected int numberOfStudent;
    protected int equipmentCount;

    public AcademicUnit(String entityID, String name, String location, int numberOfStudent, int equipmentCount) {
        super(entityID, name, location);
        this.numberOfStudent = numberOfStudent;
        this.equipmentCount = equipmentCount;
    }

    @Override
    public double calculateOperationalCost() {
        return  numberOfStudent * 50 + equipmentCount * 100;
    }
}
