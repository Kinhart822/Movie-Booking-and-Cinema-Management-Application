package vn.edu.usth.mcma.frontend.Showtimes.Models;

import java.util.Set;

public class Theater {
    private String id;
    private String name;
    private String address;
    private String city;
    private int imageResId;
    private Set<TheaterType> availableTypes;

    public Theater(String id, String name, String address, String city, int imageResId, Set<TheaterType> availableTypes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.imageResId = imageResId;
        this.availableTypes = availableTypes;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public Set<TheaterType> getAvailableTypes() {
        return availableTypes;
    }

    public void setAvailableTypes(Set<TheaterType> availableTypes) {
        this.availableTypes = availableTypes;
    }
}
