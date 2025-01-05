package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

public class ViewCityResponse {
    private List<Integer> cityIds;
    private List<String> cityNameList;

    public ViewCityResponse(List<Integer> cityIds, List<String> cityNameList) {
        this.cityIds = cityIds;
        this.cityNameList = cityNameList;
    }

    public List<Integer> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Integer> cityIds) {
        this.cityIds = cityIds;
    }

    public List<String> getCityNameList() {
        return cityNameList;
    }

    public void setCityNameList(List<String> cityNameList) {
        this.cityNameList = cityNameList;
    }
}
