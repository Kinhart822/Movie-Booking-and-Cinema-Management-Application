package vn.edu.usth.mcma.backend.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.edu.usth.mcma.backend.common.constant.ApiResponseCode;
import vn.edu.usth.mcma.backend.common.enums.CommonStatus;

import vn.edu.usth.mcma.backend.dto.request.movie.FilterMovie;
import vn.edu.usth.mcma.backend.dto.response.movie.MovieShortResponse;
import vn.edu.usth.mcma.backend.dto.response.cinema.ScheduleOfScreenResponse;
import vn.edu.usth.mcma.backend.dto.response.cinema.ScheduleResponse;
import vn.edu.usth.mcma.backend.dto.request.movie.MovieRequest;
import vn.edu.usth.mcma.backend.dto.request.movie.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.entity.Movie;
import vn.edu.usth.mcma.backend.entity.Rating;
import vn.edu.usth.mcma.backend.entity.Schedule;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.MovieRepository;
import vn.edu.usth.mcma.backend.repository.RatingRepository;
import vn.edu.usth.mcma.backend.repository.ScheduleRepository;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.security.jwt.JwtHelper;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ScheduleRepository scheduleRepository;
    private final JwtHelper jwtHelper;
    private final RatingRepository ratingRepository;

    /*
     * ========
     * movie
     * ========
     */
    public ApiResponse updateMovie(Long movieId, MovieRequest movieRequest) {
        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Rating rating =
                ratingRepository
                        .findById(movieRequest.getRatingName())
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        Movie movieToSave =
                movie.toBuilder()
                        .name(movieRequest.getName())
                        .overview(movieRequest.getOverview())
                        .poster(movieRequest.getPoster())
                        .banner(movieRequest.getBanner())
                        .length(movieRequest.getLength())
                        .releaseDate(Instant.parse(movieRequest.getPublishDate()))
                        .trailerYoutubeId(movieRequest.getTrailerUrl())
                        .status(movieRequest.getStatus())
                        .rating(rating)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build();
        movieRepository.save(movieToSave);
        return ApiResponse.ok();
    }

    /*
     * ========
     * schedule
     * ========
     */
    /*
     * if scheduleId is provided -> update existing schedule
     * else create new schedule
     */
    public ApiResponse addSchedules(List<MovieScheduleRequest> requests) {
        // validating ids
        // find distinct non null scheduleIds
        Set<Long> scheduleIds =
                requests.stream()
                        .map(r -> r.getScheduleId())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
        List<Schedule> schedules = scheduleRepository.findAllById(scheduleIds);
        if (scheduleIds.size() != schedules.size()) {
            Set<Long> missingIds = new HashSet<>(scheduleIds);
            missingIds.removeAll(
                    schedules.stream().map(s -> s.getId()).collect(Collectors.toSet()));
            throw new BusinessException(
                    ApiResponseCode.ENTITY_NOT_FOUND.setDescription(
                            String.format("Schedule not found with ids: %s", missingIds)));
        }
        // find distinct movieIds
        Set<Long> screenIds =
                requests.stream().map(r -> r.getScreenId()).collect(Collectors.toSet());
        List<Screen> screens = screenRepository.findAllById(screenIds);
        if (screenIds.size() != screens.size()) {
            Set<Long> missingIds = new HashSet<>(screenIds);
            missingIds.removeAll(screens.stream().map(s -> s.getId()).collect(Collectors.toSet()));
            throw new BusinessException(
                    ApiResponseCode.ENTITY_NOT_FOUND.setDescription(
                            String.format("Screen not found with ids: %s", missingIds)));
        }
        // find distinct movieIds
        Set<Long> movieIds = requests.stream().map(r -> r.getMovieId()).collect(Collectors.toSet());
        List<Movie> movies = movieRepository.findAllById(movieIds);
        if (movieIds.size() != movies.size()) {
            Set<Long> missingIds = new HashSet<>(movieIds);
            missingIds.removeAll(movies.stream().map(m -> m.getId()).collect(Collectors.toSet()));
            throw new BusinessException(
                    ApiResponseCode.ENTITY_NOT_FOUND.setDescription(
                            String.format("Movie not found with ids: %s", missingIds)));
        }
        Map<Long, Schedule> scheduleMap = new HashMap<>();
        Map<Long, Screen> screenMap = new HashMap<>();
        Map<Long, Movie> movieMap = new HashMap<>();
        schedules.forEach(s -> scheduleMap.put(s.getId(), s));
        screens.forEach(s -> screenMap.put(s.getId(), s));
        movies.forEach(s -> movieMap.put(s.getId(), s));

        // validate start time
        Instant now = Instant.now();
        requests.forEach(
                r -> {
                    Movie movie = movieMap.get(r.getMovieId());
                    Instant releaseDate = movie.getReleaseDate();
                    Instant startDateTime = r.getStartDateTime();
                    if (releaseDate.isAfter(now)) {
                        throw new BusinessException(ApiResponseCode.MOVIE_NOT_PUBLISHED);
                    }
                    if (startDateTime.isBefore(Instant.now())) {
                        throw new BusinessException(
                                ApiResponseCode.INVALID_SCHEDULE_START_TIME.setDescription(
                                        "cannot set schedule start time before current date"));
                    }
                    // todo warning database query in for loop
                    if (!scheduleRepository
                            .eventsInRange(r.getScreenId(), startDateTime, movie.getLength())
                            .isEmpty()) {
                        throw new BusinessException(ApiResponseCode.SCREEN_OCCUPIED);
                    }
                });
        List<Schedule> toSave = new ArrayList<>();
        requests.forEach(
                r -> {
                    int status;
                    if (r.getStatus() == null) {
                        status = CommonStatus.ACTIVE.getStatus();
                    } else {
                        Optional<CommonStatus> statusOpt =
                                Optional.ofNullable(CommonStatus.getByStatus(r.getStatus()));
                        if (statusOpt.isPresent()) {
                            status = statusOpt.get().getStatus();
                        } else {
                            throw new BusinessException(ApiResponseCode.INVALID_STATUS);
                        }
                    }
                    if (r.getScheduleId() == null) {
                        toSave.add(
                                Schedule.builder()
                                        .screen(screenMap.get(r.getScreenId()))
                                        .movie(movieMap.get(r.getMovieId()))
                                        .startDateTime(r.getStartDateTime())
                                        .status(status)
                                        .build());
                        return;
                    }
                    toSave.add(
                            scheduleMap.get(r.getScheduleId()).toBuilder()
                                    .screen(screenMap.get(r.getScreenId()))
                                    .movie(movieMap.get(r.getMovieId()))
                                    .startDateTime(r.getStartDateTime())
                                    .status(status)
                                    .build());
                });
        scheduleRepository.saveAll(toSave);
        return ApiResponse.ok();
    }

    public List<ScheduleOfScreenResponse> findAllScheduleByCinema(Long cinemaId) {
        Map<Screen, List<Schedule>> screenScheduleMap = new HashMap<>();
        List<Schedule> schedules = scheduleRepository.findAllScheduleByCinema(cinemaId);
        schedules.forEach(
                s ->
                        screenScheduleMap
                                .computeIfAbsent(s.getScreen(), scheduleList -> new ArrayList<>())
                                .add(s));
        List<ScheduleOfScreenResponse> responses = new ArrayList<>();
        screenScheduleMap.forEach(
                (screen, scheduleList) -> {
                    responses.add(
                            ScheduleOfScreenResponse.builder()
                                    .screenId(screen.getId())
                                    .screenName(screen.getName())
                                    .schedules(
                                            scheduleList.stream()
                                                    .map(
                                                            s ->
                                                                    ScheduleResponse.builder()
                                                                            .scheduleId(s.getId())
                                                                            .movieId(
                                                                                    s.getMovie()
                                                                                            .getId())
                                                                            .movieName(
                                                                                    s.getMovie()
                                                                                            .getName())
                                                                            .movieLength(
                                                                                    s.getMovie()
                                                                                            .getLength())
                                                                            .startDateTime(
                                                                                    s
                                                                                            .getStartDateTime())
                                                                            .status(s.getStatus())
                                                                            .build())
                                                    .toList())
                                    .build());
                });
        return responses;
    }

    public List<MovieShortResponse> findAllMovies() {
        return movieRepository.findAll().stream()
                .map(
                        m ->
                                MovieShortResponse.builder()
                                        .id(m.getId())
                                        .name(m.getName())
                                        .poster(m.getPoster())
                                        .length(m.getLength())
                                        .releaseDate(m.getReleaseDate())
                                        .trailerYoutubeId(m.getTrailerYoutubeId())
                                        .status(m.getStatus())
                                        .build())
                .toList();
    }

    public List<FilterMovie> filterByMovie() {
        return movieRepository.findAll().stream()
                .map(m -> FilterMovie.builder().movieId(m.getId()).movieName(m.getName()).build())
                .toList();
    }
    /*
     * USER
     */
    //    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreSet(Integer movieGenreId)
    // {
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
    //                        result[7] != null ? dateFormat.format((Date) result[7]) : null, //
    // datePublish
    //                        result[8] != null ? Arrays.asList(result[8].toString().split(",")) :
    // new ArrayList<>(), // ratingNameList
    //                        result[9] != null ? Arrays.asList(result[9].toString().split(",")) :
    // new ArrayList<>(), // ratingDescriptionList
    //                        result[10] != null ? Arrays.asList(result[10].toString().split(",")) :
    // new ArrayList<>(), // genreNameList
    //                        result[11] != null ? Arrays.asList(result[11].toString().split(",")) :
    // new ArrayList<>(), // performerNameList
    //                        result[12] != null ? Arrays.asList(result[12].toString().split(",")) :
    // new ArrayList<>(), // performerType
    //                        result[13] != null ? Arrays.asList(result[13].toString().split(",")) :
    // new ArrayList<>() // performerSex
    //                ))
    //                .collect(Collectors.toList());
    //    }
    //
    //    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreName(String name, Integer
    // limit, Integer offset) {
    //        List<Object[]> results = movieRepository.getAllMoviesByMovieGenreName(name, limit,
    // offset);
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
    //                        result[7] != null ? dateFormat.format((Date) result[7]) : null, //
    // datePublish
    //                        result[8] != null ? Arrays.asList(result[8].toString().split(",")) :
    // new ArrayList<>(), // ratingNameList
    //                        result[9] != null ? Arrays.asList(result[9].toString().split(",")) :
    // new ArrayList<>(), // ratingDescriptionList
    //                        result[10] != null ? Arrays.asList(result[10].toString().split(",")) :
    // new ArrayList<>(), // genreNameList
    //                        result[11] != null ? Arrays.asList(result[11].toString().split(",")) :
    // new ArrayList<>(), // performerNameList
    //                        result[12] != null ? Arrays.asList(result[12].toString().split(",")) :
    // new ArrayList<>(), // performerType
    //                        result[13] != null ? Arrays.asList(result[13].toString().split(",")) :
    // new ArrayList<>() // performerSex
    //                ))
    //                .collect(Collectors.toList());    }
}
