package vn.edu.usth.mcma.frontend.constant;

public enum IP {
    //todo dotenv
    KINHART822("192.168.33.102"),
    MINOXD_LAPTOP("192.168.0.109"),
    MINOXD_PX6A("10.34.61.77"),
    MINOXD_P2XL("192.168.92.77"),
    MINOXD_MLCM("192.168.1.89"),
    MINOXD_CIRCLE_K("172.19.200.92"),
    MINOXD_FPT("100.80.28.111"),
    ;
    private final String ip;
    IP(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return "http://" + this.ip + ":8080/";
    }
}
