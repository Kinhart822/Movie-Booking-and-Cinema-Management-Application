package vn.edu.usth.mcma.service.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Abstract class for entities which will holds definitions for
 *      created by, last modified by, created date, last modified date
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    @JsonIgnore
    private Long lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate;
}
