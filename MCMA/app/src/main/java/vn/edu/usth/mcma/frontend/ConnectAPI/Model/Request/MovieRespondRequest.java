package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request;

public class MovieRespondRequest {
    private Integer movieId;
    private String comment;
    private Double selectedRatingStar;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getSelectedRatingStar() {
        return selectedRatingStar;
    }

    public void setSelectedRatingStar(Double selectedRatingStar) {
        this.selectedRatingStar = selectedRatingStar;
    }
}
