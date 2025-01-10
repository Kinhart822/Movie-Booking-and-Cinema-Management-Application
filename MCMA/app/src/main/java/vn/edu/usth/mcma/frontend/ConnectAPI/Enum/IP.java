package vn.edu.usth.mcma.frontend.ConnectAPI.Enum;

public enum IP {
    KINHART822("192.168.33.102"),
    MINOXD_LAPTOP("192.168.0.109"),
    MINOXD_PX6A("10.34.61.77"),
    MINOXD_MLCM("192.168.1.89"),
    ;
    private final String ip;
    IP(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return "http://" + this.ip + ":8080/";
    }
}
