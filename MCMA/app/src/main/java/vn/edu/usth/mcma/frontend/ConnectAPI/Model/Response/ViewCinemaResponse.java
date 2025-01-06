package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

import lombok.Data;

@Data
public class ViewCinemaResponse {
    private List<Long> cinemaIdList;
    private List<String> cinemaNameList;
    private List<String> cityNameList;
    private List<String> cinemaAddressList;
}

