package vn.edu.usth.mcma.backend.module.auth.entity;

import java.time.Instant;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;
}
