package vn.edu.usth.mcma.backend.projection;

import java.time.Instant;

public interface CinemaProjection {
    Long getId();

    String getName();

    String getCity();

    Integer getNumberOfScreens();

    String getLastModifiedBy();

    Instant getLastModifiedDate();

    Integer getStatus();
}
