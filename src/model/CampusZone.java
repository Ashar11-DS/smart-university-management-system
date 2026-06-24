package model;

import java.io.Serializable;
import java.util.ArrayList;

public class CampusZone implements Serializable {

    private String zoneName;
    private ArrayList<Facility> facilities;
    private ArrayList<ServiceUnit> services;

    public CampusZone(String zoneName) {
        this.zoneName = zoneName;
        this.facilities = new ArrayList<>();
        this.services = new ArrayList<>();
    }

    public void addFacility(Facility f) {
        facilities.add(f);
    }

    public void addServices(ServiceUnit s) {
        services.add(s);
    }

    public String getZoneName()                    { return zoneName; }
    public ArrayList<Facility>    getFacilities()  { return facilities; }
    public ArrayList<ServiceUnit> getServices()    { return services; }


    public void displayZoneDetails() {
        System.out.println("Zone: " + zoneName);
        System.out.println("Facilities:");
        for (Facility f    : facilities) System.out.println("  " + f);
        System.out.println("Services:");
        for (ServiceUnit s : services)   System.out.println("  " + s);
    }

    @Override
    public String toString() {
        return zoneName + " | Facilities: " + facilities.size()
                + " | Services: " + services.size();
    }
}
