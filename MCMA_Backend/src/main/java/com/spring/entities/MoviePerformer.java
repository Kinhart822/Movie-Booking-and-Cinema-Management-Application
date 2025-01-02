package com.spring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Deprecated
public class MoviePerformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "moviePerformerSet")
    private Set<Movie> movieSet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moviePerformerDetail_id")
    private MoviePerformerDetail moviePerformerDetail;
}
