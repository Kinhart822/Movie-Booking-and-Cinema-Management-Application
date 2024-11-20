package vn.edu.usth.mcma.frontend.Personal;

public class BookingHistory_Item {
    String time;
    String site;
    String item_name;
    String quantity;

    public BookingHistory_Item(String time, String site, String item_name, String quantity) {
        this.time = time;
        this.site = site;
        this.item_name = item_name;
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
