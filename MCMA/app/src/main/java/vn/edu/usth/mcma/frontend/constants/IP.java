package vn.edu.usth.mcma.frontend.constants;

public enum IP {
    KINHART822("192.168.33.102"),
    MINOXD_LAPTOP("192.168.0.109"),;
    private final String ip;
    IP(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return "http://" + this.ip+ ":8080/";
    }
}
