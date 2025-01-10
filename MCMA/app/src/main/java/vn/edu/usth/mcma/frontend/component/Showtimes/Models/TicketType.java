package vn.edu.usth.mcma.frontend.component.Showtimes.Models;

import vn.edu.usth.mcma.R;

public enum TicketType {
//    KID("Kid", R.drawable.ic_kid, 25000,2),
    ADULT("Adult", R.drawable.ic_adult, 50000,1),
    STUDENT("Student", R.drawable.ic_student, 75000,5),
    CHILD("Child", R.drawable.ic_adult, 20000,2),
    TEEN("Teen", R.drawable.ic_adult, 40000,3),
    SENIOR("Senior", R.drawable.ic_adult, 30000,4),
    COUPLE("Couple Ticket", R.drawable.ic_adult, 90000,6),
    FAMILY("Family Ticket", R.drawable.ic_adult, 150000,7);


    private final String name;
    private final int imageResourceId;
    private final int price;
    private final int id;

    TicketType(String name, int imageResourceId, int price, int id) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.price = price;
        this.id = id;
    }

    public int getId() {
        return id;
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
    // Lookup method for mapping API names to enums
    public static TicketType fromName(String name) {
        for (TicketType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for name: " + name);
    }


}
