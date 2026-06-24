package model;

import interfaces.Notifiable;

public class SecurityService extends ServiceUnit implements Notifiable {

    public SecurityService(String entityID, String name, String location, int staffCount, int serviceHours) {
        super(entityID, name, location, staffCount, serviceHours);
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Security Alert: " + message);
    }
}
