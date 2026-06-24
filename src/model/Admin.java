package model;

import interfaces.Notifiable;

public class Admin implements Notifiable {

    private String adminID;
    private String name;
    private Role role;

    public Admin(String adminID, String name) {
        this.adminID = adminID;
        this.name = name;
        this.role = Role.ADMIN;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Admin Notification: " + message);
    }

    @Override
    public String toString() {
        return  adminID + " - " + name;
    }
}
