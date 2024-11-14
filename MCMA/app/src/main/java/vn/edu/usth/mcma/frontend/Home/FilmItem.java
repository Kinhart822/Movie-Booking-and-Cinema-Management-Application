package vn.edu.usth.mcma.frontend.Home;

public class FilmItem {
    private String name;
    private String category;
    private int film_image;

    public FilmItem(String name, String category, int film_image) {
        this.name = name;
        this.category = category;
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

    public int getFilm_image() {
        return film_image;
    }

    public void setFilm_image(int film_image) {
        this.film_image = film_image;
    }
}
