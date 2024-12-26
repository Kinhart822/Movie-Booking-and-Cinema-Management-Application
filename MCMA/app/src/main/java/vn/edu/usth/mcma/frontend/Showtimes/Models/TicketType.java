package vn.edu.usth.mcma.frontend.Showtimes.Models;

import vn.edu.usth.mcma.R;

public enum TicketType {
    KID("Kid", R.drawable.ic_kid, 25000),
    ADULT("Adult", R.drawable.ic_adult, 50000),
    STUDENT("Student", R.drawable.ic_student, 75000),
    CHILD("Child", R.drawable.ic_adult, 20000),
    TEEN("Teen", R.drawable.ic_adult, 40000),
    SENIOR("Senior", R.drawable.ic_adult, 30000),
    COUPLE("Couple Ticket", R.drawable.ic_adult, 90000),
    FAMILY("Family Ticket", R.drawable.ic_adult, 150000);


    private final String name;
    private final int imageResourceId;
    private final int price;

    TicketType(String name, int imageResourceId, int price) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getPrice() {
        return price;
    }


}
