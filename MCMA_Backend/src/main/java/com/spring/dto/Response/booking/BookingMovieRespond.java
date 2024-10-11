package com.spring.dto.Response.booking;

import com.spring.entities.MovieGenre;
import com.spring.entities.MoviePerformer;
import com.spring.entities.MovieRatingDetail;
import com.spring.entities.MovieSchedule;
import com.spring.enums.PerformerSex;
import com.spring.enums.PerformerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingMovieRespond {
    private String movieName;
    private Integer movieLength;
    private String publishedDate;
    private String trailerLink;

    private List<String> movieGenreNameList;
    private List<String> movieGenreDescriptions;

    private List<String> moviePerformerNameList;
    private List<String> moviePerformerDobList;
    private List<PerformerSex> moviePerformerSex;
    private List<PerformerType> moviePerformerType;

    private List<String> movieRatingDetailNameList;
    private List<String> movieRatingDetailDescriptions;
}
