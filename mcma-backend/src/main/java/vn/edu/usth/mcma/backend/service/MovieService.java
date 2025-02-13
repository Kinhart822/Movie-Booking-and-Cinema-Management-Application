package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.admin.dto.movie.MovieShortResponse;
import vn.edu.usth.mcma.backend.dto.cinema.ScheduleOfScreenResponse;
import vn.edu.usth.mcma.backend.dto.cinema.ScheduleResponse;
import vn.edu.usth.mcma.backend.dto.movie.MovieRequest;
import vn.edu.usth.mcma.backend.dto.movie.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.entity.*;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.*;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
                .findById(movieRequest.getRatingName())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        movieRepository
                .save(movie
                        .toBuilder()
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
                        .build());
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
        //validating ids
        //find distinct non null scheduleIds
        Set<Long> scheduleIds = requests.stream().map(MovieScheduleRequest::getScheduleId).filter(Objects::nonNull).collect(Collectors.toSet());
        List<Schedule> schedules = scheduleRepository.findAllById(scheduleIds);
        if (scheduleIds.size() != schedules.size()) {
            throw new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND.setDescription(String.format("Schedule not found with ids: %s", scheduleIds.removeAll(schedules.stream().map(Schedule::getId).collect(Collectors.toSet())))));
        }
        //find distinct movieIds
        Set<Long> screenIds = requests.stream().map(MovieScheduleRequest::getScreenId).collect(Collectors.toSet());
        List<Screen> screens = screenRepository.findAllById(screenIds);
        if (screenIds.size() != screens.size()) {
            throw new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND.setDescription(String.format("Screen not found with ids: %s", screenIds.removeAll(screens.stream().map(Screen::getId).collect(Collectors.toSet())))));
        }
        //find distinct movieIds
        Set<Long> movieIds = requests.stream().map(MovieScheduleRequest::getMovieId).collect(Collectors.toSet());
        List<Movie> movies = movieRepository.findAllById(movieIds);
        if (movieIds.size() != movies.size()) {
            throw new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND.setDescription(String.format("Movie not found with ids: %s", movieIds.removeAll(movies.stream().map(Movie::getId).collect(Collectors.toSet())))));
        }
        Map<Long, Schedule> scheduleMap = new HashMap<>();
        Map<Long, Screen> screenMap = new HashMap<>();
        Map<Long, Movie> movieMap = new HashMap<>();
        schedules.forEach(s -> scheduleMap.put(s.getId(), s));
        screens.forEach(s -> screenMap.put(s.getId(), s));
        movies.forEach(s -> movieMap.put(s.getId(), s));

        // validate start time
        Instant now = Instant.now();
        requests.forEach(r -> {
            Movie movie = movieMap.get(r.getMovieId());
            Instant releaseDate = movie.getReleaseDate();
            Instant startDateTime = r.getStartDateTime();
            if (releaseDate.isAfter(now)) {
                throw new BusinessException(ApiResponseCode.MOVIE_NOT_PUBLISHED);
            }
            if (startDateTime.isBefore(Instant.now())) {
                throw new BusinessException(ApiResponseCode.INVALID_SCHEDULE_START_TIME.setDescription("cannot set schedule start time before current date"));
            }
            //todo warning database query in for loop
            if (!scheduleRepository.eventsInRange(r.getScreenId(), startDateTime, movie.getLength()).isEmpty()) {
                throw new BusinessException(ApiResponseCode.SCREEN_OCCUPIED);
            }
        });
        List<Schedule> toSave = new ArrayList<>();
        requests.forEach(r -> {
            int status;
            if (r.getStatus() == null) {
                status = CommonStatus.ACTIVE.getStatus();
            } else {
                Optional<CommonStatus> statusOpt = Optional.ofNullable(CommonStatus.getByStatus(r.getStatus()));
                if (statusOpt.isPresent()) {
                    status = statusOpt.get().getStatus();
                } else {
                    throw new BusinessException(ApiResponseCode.INVALID_STATUS);
                }
            }
            if (r.getScheduleId() == null) {
                toSave.add(Schedule.builder()
                        .screen(screenMap.get(r.getScreenId()))
                        .movie(movieMap.get(r.getMovieId()))
                        .startDateTime(r.getStartDateTime())
                        .status(status).build());
                return;
            }
            toSave.add(scheduleMap.get(r.getScheduleId()).toBuilder()
                    .screen(screenMap.get(r.getScreenId()))
                    .movie(movieMap.get(r.getMovieId()))
                    .startDateTime(r.getStartDateTime())
                    .status(status).build());
        });
        scheduleRepository.saveAll(toSave);
        return ApiResponse.ok();
    }

    public List<ScheduleOfScreenResponse> findAllScheduleByCinema(Long cinemaId) {
        Map<Screen, List<Schedule>> screenScheduleMap = new HashMap<>();
        List<Schedule> schedules = scheduleRepository.findAllScheduleByCinema(cinemaId);
        schedules.forEach(s -> screenScheduleMap
                .computeIfAbsent(s.getScreen(), scheduleList -> new ArrayList<>())
                .add(s));
        List<ScheduleOfScreenResponse> responses = new ArrayList<>();
        screenScheduleMap.forEach((screen, scheduleList) -> {
            responses.add(ScheduleOfScreenResponse.builder()
                            .screenId(screen.getId())
                            .screenName(screen.getName())
                            .schedules(scheduleList.stream().map(s -> ScheduleResponse.builder()
                                    .scheduleId(s.getId())
                                    .movieId(s.getMovie().getId())
                                    .movieName(s.getMovie().getName())
                                    .movieLength(s.getMovie().getLength())
                                    .startDateTime(s.getStartDateTime())
                                    .status(s.getStatus()).build()).toList())
                    .build());
        });
        return responses;
    }

    public List<MovieShortResponse> findAllMovies() {
        return movieRepository.findAll().stream().map(m -> MovieShortResponse.builder()
                .id(m.getId())
                .name(m.getName())
                .poster(m.getPoster())
                .length(m.getLength())
                .releaseDate(m.getReleaseDate())
                .trailerYoutubeId(m.getTrailerYoutubeId())
                .status(m.getStatus()).build()).toList();
    }
    /*
     * TODO:USER
     */
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
