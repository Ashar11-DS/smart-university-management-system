package model;

import interfaces.Schedulable;

public class TransportService extends ServiceUnit implements Schedulable {

    public TransportService(String entityID, String name, String location, int staffCount, int serviceHours) {
        super(entityID, name, location, staffCount, serviceHours);
    }

    @Override
    public void generateSchedule() {
        System.out.println("Transport Schedule adjusted for peak hours.");
    }

    public void adjustForPeakHours(boolean isPeak) {
        if (isPeak) {
            System.out.println("Extra buses allocated for peak hours. ");
        } else {
            System.out.println("Normal transport schedule running. ");
        }
    }
}
