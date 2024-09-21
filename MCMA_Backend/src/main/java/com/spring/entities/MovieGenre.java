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
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "movie", joinColumns = @JoinColumn(name = "movieGenre_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movieSet;

    @OneToOne(mappedBy = "movieGenre", cascade = CascadeType.ALL)
    private MovieGenreDetail movieGenreDetail;
}
