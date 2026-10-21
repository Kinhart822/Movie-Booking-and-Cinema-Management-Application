package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentMethod extends AbstractAuditing {
    @Id private String id;
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String imageBase64;

    private String appPackageName;
}
