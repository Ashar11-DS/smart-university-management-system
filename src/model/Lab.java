package model;

public class Lab extends AcademicUnit {
    private String labType;        // e.g. "Computer", "Physics", "Chemistry"
    private boolean isAvailable;

    public Lab(String entityID, String name, String location, int numberOfStudent, int equipmentCount, String labType, boolean isAvailable) {
        super(entityID, name, location, numberOfStudent, equipmentCount);
        this.labType = labType;
        this.isAvailable = isAvailable;
    }

    // Getter
    public String getLabType()   { return labType; }
    public boolean isAvailable() { return isAvailable; }

    // Setter
    public void setAvailable(boolean status) { this.isAvailable = status; }


    @Override
    public double calculateOperationalCost() {
        return numberOfStudent * 60 + equipmentCount * 200;
    }

    @Override
    public String toString() {
        return super.toString() + " | Lab type: " + labType
                + " | Available: " + isAvailable;
    }
}

