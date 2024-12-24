package vn.edu.usth.mcma.frontend.Showtimes.Models;

import java.io.Serializable;
import java.util.Set;

public class Theater implements Serializable {
    private int id;
    private String name;
    private String address;
    private String city;
    private int imageResId;

    public Theater(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Theater(int id, String name, String address, String city, int imageResId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.imageResId = imageResId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
