package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

public class ViewCinemaResponse {
    private List<Integer> cinemaIdList;
    private List<String> cinemaNameList;

    public List<Integer> getCinemaIdList() {
        return cinemaIdList;
    }

    public void setCinemaIdList(List<Integer> cinemaIdList) {
        this.cinemaIdList = cinemaIdList;
    }

    public List<String> getCinemaNameList() {
        return cinemaNameList;
    }

    public void setCinemaNameList(List<String> cinemaNameList) {
        this.cinemaNameList = cinemaNameList;
    }
}

