package vn.edu.usth.mcma.service.common.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
public class MoviePerformerPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column
    private Long movieId;
    @Column
    private Long performerDetailId;
}
