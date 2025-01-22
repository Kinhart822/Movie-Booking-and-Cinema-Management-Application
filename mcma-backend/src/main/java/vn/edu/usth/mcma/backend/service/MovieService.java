package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import constants.PerformerType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.entity.*;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.*;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ScheduleRepository scheduleRepository;
    private final JwtHelper jwtHelper;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    /*
     * ========
     * movie
     * ========
     */
    public ApiResponse updateMovie(Long movieId, MovieRequest movieRequest) {
        Movie movie = movieRepository
                .findById(movieId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Rating rating = ratingRepository
                .findById(movieRequest.getRatingId())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        movieRepository
                .save(movie
                        .toBuilder()
                        .name(movieRequest.getName())
                        .overview(movieRequest.getOverview())
                        .imageBase64(movieRequest.getImageBase64())
                        .backgroundImageBase64(movieRequest.getBackgroundImageBase64())
                        .length(movieRequest.getLength())
                        .publishDate(Instant.parse(movieRequest.getPublishDate()))
                        .trailerUrl(movieRequest.getTrailerUrl())
                        .status(movieRequest.getStatus())
                        .rating(rating)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build());
        return ApiResponse.success();
    }
    /*
     * ========
     * schedule
     * ========
     */
    public ApiResponse addMovieToSchedule(MovieScheduleRequest request) {
        Movie movie = movieRepository
                .findById(request.getMovieId())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Instant startTime = request.getStartTime();
        Instant endTime = startTime.plusSeconds(movie.getLength());
        if (movie.getPublishDate().isAfter(Instant.now())) {
            throw new BusinessException(ApiResponseCode.MOVIE_NOT_PUBLISHED);
        }
        if (startTime.isBefore(Instant.now())) {
            throw new BusinessException(ApiResponseCode.INVALID_START_TIME);
        }
        if (!scheduleRepository.eventsInRange(request.getScreenId(), startTime, endTime).isEmpty()) {
            throw new BusinessException(ApiResponseCode.SCREEN_OCCUPIED);
        }
        scheduleRepository.save(Schedule
                .builder()
                .screen(screenRepository
                        .findById(request.getScreenId())
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND)))
                .movie(movie)
                .startTime(startTime)
                .endTime(endTime)
                .status(CommonStatus.ACTIVE.getStatus())
                .build());
        return ApiResponse.success();
    }
    /*
     * TODO:USER
     */
    public List<SearchMovieByNameResponse> getAllMovies(String title) {
        return movieRepository
                .findAllByNameContainingAndStatusIs(title, CommonStatus.ACTIVE.getStatus())
                .stream()
                .map(m -> SearchMovieByNameResponse
                        .builder()
                        .id(m.getId())
                        .name(m.getName())
                        .description(m.getOverview())
                        .imageUrl(m.getImageBase64())
                        .backgroundImageUrl(m.getBackgroundImageBase64())
                        .length(m.getLength()/60)
                        .publishDate(m.getPublishDate().toString().substring(0,10))
                        .trailerUrl(m.getTrailerUrl())
                        .rating(RatingPresentation
                                .builder()
                                .id(m.getRating().getId())
                                .name(m.getRating().getName())
                                .description(m.getRating().getDescription())
                                .build())
                        .genres(m
                                .getGenreSet()
                                .stream()
                                .map(g -> GenrePresentation
                                        .builder()
                                        .id(g.getId())
                                        .name(g.getName())
                                        .build())
                                .toList())
                        .performers(m
                                .getPerformerSet()
                                .stream()
                                .map(p -> PerformerPresentation
                                        .builder()
                                        .id(p.getId())
                                        .name(p.getName())
                                        .typeId(p.getTypeId())
                                        .build())
                                .toList()).build())
                .toList();
    }

    public List<MovieDetailShort> findAllNowShowing() {
        List<MovieDetailShortProjection> movieProjections = movieRepository.findAllMovieDetailShort(List.of(CommonStatus.ACTIVE.getStatus()), Instant.now());
        Map<Long, Movie> movieMap = new HashMap<>();
        movieRepository
                .findAllById(movieProjections
                        .stream()
                        .map(MovieDetailShortProjection::getId)
                        .toList())
                .forEach(m -> movieMap.put(m.getId(), m));
        return movieProjections
                .stream()
                .map(p -> MovieDetailShort.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .length(p.getLength())
                        .imageBase64(p.getImageBase64())
                        .rating(movieMap
                                .get(p.getId())
                                .getRating()
                                .getName())
                        .build())
                .toList();
    }

    public MovieDetail findMovieDetail(Long id) {
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        List<String> directors = movie
                .getPerformerSet()
                .stream()
                .filter(p -> p.getTypeId() == PerformerType.Director.getId())
                .map(Performer::getName)
                .toList();
        List<String> actors = movie
                .getPerformerSet()
                .stream()
                .filter(p -> p.getTypeId() == PerformerType.Actor.getId())
                .map(Performer::getName)
                .toList();
        return MovieDetail.builder()
                .id(movie.getId())
                .name(movie.getName())
                .length(movie.getLength())
                .overview(movie.getOverview())
                .publishDate(movie.getPublishDate())
                .trailerUrl(movie.getTrailerUrl())
                .imageBase64(movie.getImageBase64())
                .backgroundImageBase64(movie.getBackgroundImageBase64())
                .genres(movie
                        .getGenreSet()
                        .stream()
                        .map(Genre::getName)
                        .toList())
                .directors(directors)
                .actors(actors)
                .rating(movie.getRating().getName())
                .avgVotes(reviewRepository.findAvgVoteByMovieIdAndStatus(id, CommonStatus.ACTIVE.getStatus()))
                .build();
    }

//    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreSet(Integer movieGenreId) {
//        List<Object[]> results = movieRepository.getAllMoviesByMovieGenreSet(movieGenreId);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        return results.stream()
//                .map(result -> new SearchMovieByGenreResponse(
//                        (Integer) result[0],        // id
//                        (String) result[1],         // name
//                        (Integer) result[2],        // length
//                        (String) result[3],         // description
//                        (String) result[4],         // image Url
//                        (String) result[5],         // backgroundImageUrl
//                        (String) result[6],         // trailerLink
//                        result[7] != null ? dateFormat.format((Date) result[7]) : null, //  datePublish
//                        result[8] != null ? Arrays.asList(result[8].toString().split(",")) : new ArrayList<>(), // ratingNameList
//                        result[9] != null ? Arrays.asList(result[9].toString().split(",")) : new ArrayList<>(), // ratingDescriptionList
//                        result[10] != null ? Arrays.asList(result[10].toString().split(",")) : new ArrayList<>(), // genreNameList
//                        result[11] != null ? Arrays.asList(result[11].toString().split(",")) : new ArrayList<>(), // performerNameList
//                        result[12] != null ? Arrays.asList(result[12].toString().split(",")) : new ArrayList<>(), // performerType
//                        result[13] != null ? Arrays.asList(result[13].toString().split(",")) : new ArrayList<>() // performerSex
//                ))
//                .collect(Collectors.toList());
//    }
//
//    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreName(String name, Integer limit, Integer offset) {
//        List<Object[]> results = movieRepository.getAllMoviesByMovieGenreName(name, limit, offset);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        return results.stream()
//                .map(result -> new SearchMovieByGenreResponse(
//                        (Integer) result[0],        // id
//                        (String) result[1],         // name
//                        (Integer) result[2],        // length
//                        (String) result[3],         // description
//                        (String) result[4],         // image Url
//                        (String) result[5],         // backgroundImageUrl
//                        (String) result[6],         // trailerLink
//                        result[7] != null ? dateFormat.format((Date) result[7]) : null, //  datePublish
//                        result[8] != null ? Arrays.asList(result[8].toString().split(",")) : new ArrayList<>(), // ratingNameList
//                        result[9] != null ? Arrays.asList(result[9].toString().split(",")) : new ArrayList<>(), // ratingDescriptionList
//                        result[10] != null ? Arrays.asList(result[10].toString().split(",")) : new ArrayList<>(), // genreNameList
//                        result[11] != null ? Arrays.asList(result[11].toString().split(",")) : new ArrayList<>(), // performerNameList
//                        result[12] != null ? Arrays.asList(result[12].toString().split(",")) : new ArrayList<>(), // performerType
//                        result[13] != null ? Arrays.asList(result[13].toString().split(",")) : new ArrayList<>() // performerSex
//                ))
//                .collect(Collectors.toList());    }
}
