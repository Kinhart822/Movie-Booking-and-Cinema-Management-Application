package com.spring.service.impl;

import com.spring.dto.request.respond.MovieRespondRequest;
import com.spring.dto.response.movieRespond.CommentResponse;
import com.spring.dto.response.movieRespond.MovieRespondResponse;
import com.spring.dto.response.movieRespond.RatingResponse;
import com.spring.entities.*;
import com.spring.enums.BookingStatus;
import com.spring.enums.Type;
import com.spring.repository.*;
import com.spring.service.MovieRespondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MovieRespondServiceImpl implements MovieRespondService {

    @Autowired
    private MovieRespondRepository movieRespondRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public MovieRespondResponse createMovieRespond(Integer userId, MovieRespondRequest movieRespondRequest) {
        List<Booking> bookings = bookingRepository.findByUserIdAndMovieId(userId, movieRespondRequest.getMovieId());

        boolean hasValidBooking = bookings.stream().anyMatch(booking ->
                booking.getEndDateTime().isBefore(LocalDateTime.now()) &&
                        booking.getStatus() == BookingStatus.Completed
        );

        if (!hasValidBooking) {
            throw new IllegalStateException("You can only leave a response after watching the movie and the booking has ended.");
        }

        MovieRespond movieRespond = new MovieRespond();
        movieRespond.setDateCreated(new Date());
        movieRespond.setDateUpdated(new Date());
        movieRespond.setCreatedBy(Type.USER);
        movieRespond.setLastModifiedBy(Type.USER);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        movieRespond.setUser(user);

        Movie movie = movieRepository.findById(movieRespondRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        movieRespond.setMovie(movie);

        if (movieRespondRequest.getComment() != null) {
            Comment newComment = new Comment();
            newComment.setContent(movieRespondRequest.getComment());
            commentRepository.save(newComment);
            newComment.setMovieRespond(movieRespond);
            movieRespond.setComment(newComment);
        }

        if (movieRespondRequest.getSelectedRatingStar() != null) {
            Rating newRating = new Rating();
            newRating.setRatingStar(movieRespondRequest.getSelectedRatingStar());
            ratingRepository.save(newRating);
            newRating.setMovieRespond(movieRespond);
            movieRespond.setRating(newRating);
        }

        movieRespondRepository.save(movieRespond);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage("Your respond for %s is confirmed!".formatted(movie.getName()));
        notification.setDateCreated(LocalDateTime.now());
        notificationRepository.save(notification);

        return MovieRespondResponse.builder()
                .movieName(movie.getName())
                .content(movieRespond.getComment() != null ? movieRespond.getComment().getContent() : null)
                .ratingStar(movieRespond.getRating() != null ? movieRespond.getRating().getRatingStar() : null)
                .build();
    }

    @Override
    public MovieRespondResponse updateMovieRespond(Integer userId, MovieRespondRequest movieRespondRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Movie movie = movieRepository.findById(movieRespondRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        MovieRespond existingMovieRespond = movieRespondRepository.findByUserIdAndMovieId(userId, movieRespondRequest.getMovieId());

        if (existingMovieRespond == null) {
            throw new IllegalArgumentException("MovieRespond not found");
        }

        existingMovieRespond.setDateUpdated(new Date());
        existingMovieRespond.setLastModifiedBy(Type.USER);

        if (movieRespondRequest.getComment() != null) {
            Comment comment = existingMovieRespond.getComment();
            comment.setContent(movieRespondRequest.getComment());
            comment.setMovieRespond(existingMovieRespond);
            commentRepository.save(comment);
            existingMovieRespond.setComment(comment);
        }

        if (movieRespondRequest.getSelectedRatingStar() != null) {
            Rating rating = existingMovieRespond.getRating();
            rating.setRatingStar(movieRespondRequest.getSelectedRatingStar());
            rating.setMovieRespond(existingMovieRespond);
            ratingRepository.save(rating);
            existingMovieRespond.setRating(rating);
        }

        movieRespondRepository.save(existingMovieRespond);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage("Your respond for %s is updated!".formatted(movie.getName()));
        notification.setDateCreated(LocalDateTime.now());
        notificationRepository.save(notification);

        return MovieRespondResponse.builder()
                .movieName(movie.getName())
                .content(existingMovieRespond.getComment() != null ? existingMovieRespond.getComment().getContent() : null)
                .ratingStar(existingMovieRespond.getRating() != null ? existingMovieRespond.getRating().getRatingStar() : null)
                .build();
    }

    @Override
    public void deleteMovieRespond(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        MovieRespond existingMovieRespond = movieRespondRepository.findByUserIdAndMovieId(userId, movieId);

        if (existingMovieRespond == null) {
            throw new IllegalArgumentException("MovieRespond not found");
        }

        movieRespondRepository.delete(existingMovieRespond);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage("Your respond for %s is deleted!".formatted(movie.getName()));
        notification.setDateCreated(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public CommentResponse getMovieCommentByUserIdAndMovieId(Integer userId, Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Comment comment = commentRepository.findByUserIdAndMovieId(userId, movie.getId());

        if (comment == null) {
            throw new IllegalArgumentException("Comment not found for given user and movie.");
        }

        return CommentResponse.builder()
                .movieName(movie.getName())
                .content(comment.getContent())
                .build();
    }

    @Override
    public RatingResponse getMovieRatingByUserIdAndMovieId(Integer userId, Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Rating rating = ratingRepository.findByUserIdAndMovieId(userId, movie.getId());

        if (rating == null) {
            throw new IllegalArgumentException("Rating not found for given user and movie.");
        }

        return RatingResponse.builder()
                .movieName(movie.getName())
                .ratingStar(rating.getRatingStar())
                .build();
    }

    @Override
    public MovieRespondResponse getMovieRespondsByUserIdAndMovieId(Integer userId, Integer movieId) {
        MovieRespond movieRespond = movieRespondRepository.findByUserIdAndMovieId(userId, movieId);

        if (movieRespond == null) {
            throw new IllegalArgumentException("MovieRespond not found for given user and movie.");
        }

        return MovieRespondResponse.builder()
                .movieName(movieRespond.getMovie().getName())
                .content(movieRespond.getComment() != null ? movieRespond.getComment().getContent() : null)
                .ratingStar(movieRespond.getRating() != null ? movieRespond.getRating().getRatingStar() : null)
                .build();
    }

    @Override
    public List<CommentResponse> getMovieCommentsByUserId(Integer userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);

        if (comments == null) {
            throw new IllegalArgumentException("Comments not found for given user and movie.");
        }

        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .movieName(comment.getMovieRespond().getMovie().getName())
                        .content(comment.getContent())
                        .build())
                .toList();
    }

    @Override
    public List<CommentResponse> getMovieCommentsByMovieId(Integer movieId) {
        List<Comment> comments = commentRepository.findByMovieId(movieId);

        if (comments == null) {
            throw new IllegalArgumentException("Comments not found for given user and movie.");
        }

        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .movieName(comment.getMovieRespond().getMovie().getName())
                        .content(comment.getContent())
                        .build())
                .toList();
    }

    @Override
    public List<RatingResponse> getMovieRatingsByUserId(Integer userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);

        if (ratings == null) {
            throw new IllegalArgumentException("Ratings not found for given user and movie.");
        }

        return ratings.stream()
                .map(rating -> RatingResponse.builder()
                        .movieName(rating.getMovieRespond().getMovie().getName())
                        .ratingStar(rating.getRatingStar())
                        .build())
                .toList();
    }

    @Override
    public List<RatingResponse> getMovieRatingsByMovieId(Integer movieId) {
        List<Rating> ratings = ratingRepository.findByMovieId(movieId);

        if (ratings == null) {
            throw new IllegalArgumentException("Ratings not found for given user and movie.");
        }

        return ratings.stream()
                .map(rating -> RatingResponse.builder()
                        .movieName(rating.getMovieRespond().getMovie().getName())
                        .ratingStar(rating.getRatingStar())
                        .build())
                .toList();
    }

    @Override
    public List<MovieRespondResponse> getAllMovieRespondsByUserId(Integer userId) {
        List<MovieRespond> movieRespondList = movieRespondRepository.findByUserId(userId);

        if (movieRespondList == null) {
            throw new IllegalArgumentException("Movie Responds not found for given user and movie.");
        }

        return movieRespondList.stream()
                .map(movieRespond -> MovieRespondResponse.builder()
                        .movieName(movieRespond.getMovie().getName())
                        .content(movieRespond.getComment() != null ? movieRespond.getComment().getContent() : null)
                        .ratingStar(movieRespond.getRating() != null ? movieRespond.getRating().getRatingStar() : null)
                        .build())
                .toList();
    }

    @Override
    public List<MovieRespondResponse> getAllMovieRespondsByMovieId(Integer movieId) {
        List<MovieRespond> movieRespondList = movieRespondRepository.findByMovieId(movieId);

        if (movieRespondList == null) {
            throw new IllegalArgumentException("Movie Responds not found for given user and movie.");
        }

        return movieRespondList.stream()
                .map(movieRespond -> MovieRespondResponse.builder()
                        .movieName(movieRespond.getMovie().getName())
                        .content(movieRespond.getComment() != null ? movieRespond.getComment().getContent() : null)
                        .ratingStar(movieRespond.getRating() != null ? movieRespond.getRating().getRatingStar() : null)
                        .build())
                .toList();
    }
}
