package model;

public class Classroom extends AcademicUnit{
    private boolean isAvailable;

    public Classroom(String entityID, String name, String location, int numberOfStudent, int equipmentCount, boolean isAvailable) {
        super(entityID, name, location, numberOfStudent, equipmentCount);
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() { // Getter
        return isAvailable;
    }

    public void setAvailable(boolean status) {  // Setter
        this.isAvailable = status;
    }

    @Override
    public String toString() {
        return super.toString() + " | Available: " + isAvailable;
    }
}
