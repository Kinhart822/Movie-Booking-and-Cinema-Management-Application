package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.entity.Movie;
import vn.edu.usth.mcma.backend.entity.MovieSchedule;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.MovieRepository;
import vn.edu.usth.mcma.backend.repository.MovieScheduleRepository;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;

import java.time.Instant;

@Transactional
@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final MovieScheduleRepository movieScheduleRepository;
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
        if (!movieScheduleRepository.eventsInRange(startTime, endTime).isEmpty()) {
            throw new BusinessException(ApiResponseCode.SCREEN_OCCUPIED);
        }
        movieScheduleRepository.save(MovieSchedule
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

    // TODO: USER
//    public List<SearchMovieByNameResponse> getAllMovies(String title, Integer limit, Integer offset) {
//        List<Object[]> results = movieRepository.getAllMovies(title, limit, offset);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        return results.stream()
//                .map(result -> new SearchMovieByNameResponse(
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
