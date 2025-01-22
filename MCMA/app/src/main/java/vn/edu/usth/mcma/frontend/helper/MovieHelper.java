package vn.edu.usth.mcma.frontend.helper;

import java.time.Instant;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.frontend.dto.response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.model.Genre;
import vn.edu.usth.mcma.frontend.model.Movie;
import vn.edu.usth.mcma.frontend.model.Performer;
import vn.edu.usth.mcma.frontend.model.Rating;
import vn.edu.usth.mcma.frontend.model.Review;

public class MovieHelper {
    public static Movie mapToMovie(NowShowingResponse response) {
        return Movie.builder()
                .id(response.getId())
                .name(response.getName())
                .length(response.getLength())
                .description(response.getDescription())
                .publishDate(Instant.parse(response.getPublishDate()))
                .trailerUrl(response.getTrailerUrl())
                .imageBase64(response.getImageBase64())
                .backgroundImageBase64(response.getBackgroundImageBase64())
                .genres(response
                        .getGenres()
                        .stream()
                        .map(g -> Genre.builder()
                                .id(g.getId())
                                .name(g.getName())
                                .description(g.getDescription())
                                .imageBase64(g.getImageBase64())
                                .build())
                        .collect(Collectors.toList()))
                .performers(response
                        .getPerformers()
                        .stream()
                        .map(p -> Performer.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .typeId(p.getTypeId())
                                .dob(Instant.parse(p.getDob()))
                                .sex(p.getSex())
                                .build())
                        .collect(Collectors.toList()))
                .rating(Rating.builder()
                        .id(response.getRating().getId())
                        .name(response.getRating().getName())
                        .description(response.getRating().getDescription())
                        .build())
                .reviews(response
                        .getReviews()
                        .stream()
                        .map(r -> Review.builder()
                                .id(r.getId())
                                .userId(r.getUserId())
                                .userComment(r.getUserComment())
                                .userVote(r.getUserVote())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
