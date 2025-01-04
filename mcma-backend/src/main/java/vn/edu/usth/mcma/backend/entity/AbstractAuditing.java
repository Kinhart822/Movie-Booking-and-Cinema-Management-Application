package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Abstract class for entities which will holds definitions for
 *      created by, last modified by, created date, last modified date
 */
@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class) error: loop
@Getter
@Setter
public class AbstractAuditing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;
    @LastModifiedBy
    @Column
    private Long lastModifiedBy;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column
    private Instant lastModifiedDate;
}
