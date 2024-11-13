package vn.edu.usth.mcma.service.admin.service.dto.response;

import java.time.Instant;

public interface MovieProjection {
    Long getId();
    String getName();
    Integer getLength();
    Instant getDatePublish();
    String getRatingName();
    String getRatingDescription();
    String getTrailerLink();
    Long getCreatedBy();
    Long getLastModifiedBy();
    Instant getCreatedDate();
    Instant getLastModifiedDate();
}
