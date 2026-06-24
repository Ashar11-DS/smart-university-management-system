package model;

import interfaces.Notifiable;

public class HealthCenter extends ServiceUnit implements Notifiable {

    public HealthCenter(String entityID, String name, String location, int staffCount, int serviceHours) {
        super(entityID, name, location, staffCount, serviceHours);
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Health Center Alert: " + message);
    }
}
