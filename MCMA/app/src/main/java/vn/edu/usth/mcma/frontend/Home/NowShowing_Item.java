package vn.edu.usth.mcma.frontend.Home;

public class NowShowing_Item {
    private String name;
    private String category;
    private String time;
    private String age_limit;
    private int film_image;

    public NowShowing_Item(String name, String category, String time, String age_limit, int film_image) {
        this.name = name;
        this.category = category;
        this.time = time;
        this.age_limit = age_limit;
        this.film_image = film_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(String age_limit) {
        this.age_limit = age_limit;
    }

    public int getFilm_image() {
        return film_image;
    }

    public void setFilm_image(int film_image) {
        this.film_image = film_image;
    }
}
