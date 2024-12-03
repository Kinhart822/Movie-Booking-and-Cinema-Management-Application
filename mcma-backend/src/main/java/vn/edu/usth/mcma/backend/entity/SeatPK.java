package vn.edu.usth.mcma.backend.entity;

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
public class SeatPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column
    private Long screenId;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer row;
    @Column(name = "col", columnDefinition = "TINYINT UNSIGNED")
    private Integer column;
}
