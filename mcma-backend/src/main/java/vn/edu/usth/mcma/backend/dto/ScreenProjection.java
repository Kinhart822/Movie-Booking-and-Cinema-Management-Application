package vn.edu.usth.mcma.backend.dto;

public interface ScreenProjection {
    Long getId();
    String getName();
    String getType();
    Integer getNumberOfSeats();
    String getLastModifiedBy();
    String getLastModifiedDate();
    Integer getStatus();
}
