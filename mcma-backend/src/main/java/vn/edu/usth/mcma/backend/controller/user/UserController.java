package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.dto.cinema.CinemaPresentation;
import vn.edu.usth.mcma.backend.dto.cinema.CityPresentation;
import vn.edu.usth.mcma.backend.dto.movie.MoviePresentation;
import vn.edu.usth.mcma.backend.security.JwtHelper;
import vn.edu.usth.mcma.backend.service.BookingService;
import vn.edu.usth.mcma.backend.service.MovieService;
import vn.edu.usth.mcma.backend.service.SeatService;
import vn.edu.usth.mcma.backend.service.ViewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final MovieService movieService;
    private final BookingService bookingService;
    private final JwtHelper jwtUtil;
    private final ViewService viewService;
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        Long userId = jwtUtil.getIdUserRequesting();
        return ResponseEntity.ok("Hello, User! Your ID is: %d".formatted(userId));
    }
//    @GetMapping("/search-movie-by-genre")
//    public ResponseEntity<List<SearchMovieByGenreResponse>> getAllMoviesByMovieGenreSet(
//            @RequestParam(required = false, name = "movieGenreId") Integer movieGenreId) {
//        return ResponseEntity.ok(movieService.getAllMoviesByMovieGenreSet(movieGenreId));
//    }
//    @GetMapping("/search-movie-by-movie-genre-name")
//    public ResponseEntity<List<SearchMovieByGenreResponse>> getAllMoviesByMovieGenreName(
//            @RequestParam(required = false, name = "name") String name,
//            @RequestParam(required = false, name = "limit", defaultValue = "100") Integer limit,
//            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset
//    ) {
//        return ResponseEntity.ok(movieService.getAllMoviesByMovieGenreName(name, limit, offset));
//    }
    // TODO: Booking

    @GetMapping("/information/movie-information/{movieId}")
    public ResponseEntity<MoviePresentation> getAllInformationOfSelectedMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(bookingService.getAllInformationOfSelectedMovie(movieId));
    }
    @GetMapping("/booking/allCitiesByMovie/{movieId}")
    public ResponseEntity<List<CityPresentation>> getAllCitiesByMovie(@PathVariable Long movieId) {
        List<CityPresentation> cityResponses = bookingService.getAllCitiesBySelectedMovie(movieId);
        return ResponseEntity.ok(cityResponses);
    }
    @GetMapping("/booking/allCinemasByMovieAndCity")
    public ResponseEntity<List<CinemaPresentation>> getAllCinemasByMovieAndCity(
            @RequestParam(required = false, name = "movieId") Long movieId,
            @RequestParam(required = false, name = "cityId") Long cityId) {
        return ResponseEntity.ok(bookingService.getAllCinemasBySelectedMovieAndSelectedCity(movieId, cityId));
    }
    @GetMapping("/booking/allScreenByCinema/{cinemaId}")
    public ResponseEntity<List<ScreenPresentation>> getAllScreenByCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(bookingService.getAllScreensBySelectedCinema(cinemaId));
    }
    @GetMapping("/booking/allSchedulesByMovieAndCinemaAndScreen")
    public ResponseEntity<List<SchedulePresentation>> getAllSchedulesByMovieAndScreen(
            @RequestParam(required = false, name = "movieId") Long movieId,
            @RequestParam(required = false, name = "screenId") Long screenId) {
        return ResponseEntity.ok(bookingService.getAllSchedulesByMovieAndScreen(movieId, screenId));
    }
//    @GetMapping("/booking/allTickets")
//    public ResponseEntity<List<TicketPresentation>> getAllTickets() {
//        return ResponseEntity.ok(bookingService.getAllTickets());
//    }
//    @GetMapping("/booking/allUnavailableSeatsByScreen/{screenId}")
//    public ResponseEntity<List<UnavailableSeatResponse>> getAllUnavailableSeatsByScreen(@PathVariable Integer screenId) {
//        List<UnavailableSeatResponse> unavailableSeatResponses = bookingService.getAllUnavailableSeatsBySelectedScreen(screenId);
//        return ResponseEntity.ok(unavailableSeatResponses);
//    }
//    @GetMapping("/booking/allHeldSeatsByScreen/{screenId}")
//    public ResponseEntity<List<HeldSeatResponse>> getAllHeldSeatsByScreen(@PathVariable Integer screenId) {
//        List<HeldSeatResponse> heldSeatResponses = bookingService.getAllHeldSeatsBySelectedScreen(screenId);
//        return ResponseEntity.ok(heldSeatResponses);
//    }
//    @GetMapping("/booking/allAvailableSeatsByScreen/{screenId}")
//    public ResponseEntity<List<AvailableSeatResponse>> getAllAvailableSeatsByScreen(@PathVariable Integer screenId) {
//        List<AvailableSeatResponse> availableSeatResponses = bookingService.getAllAvailableSeatsBySelectedScreen(screenId);
//        return ResponseEntity.ok(availableSeatResponses);
//    }
//    @GetMapping("/booking/allFoodsAndDrinksByCinema/{cinemaId}")
//    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> getAllFoodsAndDrinksByCinema(@PathVariable Integer cinemaId) {
//        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponses = bookingService.getAllFoodsAndDrinksByCinema(cinemaId);
//        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponses);
//    }
//    @GetMapping("/booking/allCouponsByUser")
//    public ResponseEntity<List<CouponResponse>> getAllCouponsByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<CouponResponse> couponResponses = bookingService.getAllCouponsByUser(userId);
//        return ResponseEntity.ok(couponResponses);
//    }
//    @GetMapping("/booking/allCouponsByMovie/{movieId}")
//    public ResponseEntity<List<CouponResponse>> getAllCoupons(@PathVariable Integer movieId) {
//        List<CouponResponse> couponResponses = bookingService.getAllCouponsByMovie(movieId);
//        return ResponseEntity.ok(couponResponses);
//    }
//    @PostMapping("/booking/processingBooking")
//    public ResponseEntity<SendBookingResponse> processingBooking(HttpServletRequest request, @RequestBody BookingRequest bookingRequest) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        SendBookingResponse bookingResponse = bookingService.processingBooking(userId, bookingRequest);
//        return ResponseEntity.ok(bookingResponse);
//    }
//    @PostMapping("/booking/completeBooking")
//    public ResponseEntity<BookingResponse> completeBooking(HttpServletRequest request, @RequestBody BookingRequest bookingRequest) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        BookingResponse bookingResponse = bookingService.completeBooking(userId, bookingRequest);
//        return ResponseEntity.ok(bookingResponse);
//    }
////    @PostMapping("/booking/updateBookingSeat")
////    public ResponseEntity<String> updateBookingSeat(@RequestBody BookingRequest bookingRequest) {
////        bookingService.updateBookingSeat(bookingRequest);
////        return ResponseEntity.ok("Seat(s) updated successfully");
////    }
//    @PostMapping("/booking/cancel-booking/{bookingId}")
//    public ResponseEntity<String> cancelBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        bookingService.cancelBooking(bookingId, userId);
//        return ResponseEntity.ok("Booking canceled successfully");
//    }
//    @PostMapping("/booking/revoke-cancel-booking/{bookingId}")
//    public ResponseEntity<String> revokeCancelBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        bookingService.revokeCancelBooking(bookingId, userId);
//        return ResponseEntity.ok("Booking reinstated successfully");
//    }
//    @DeleteMapping("booking/delete-booking/{bookingId}")
//    public ResponseEntity<String> deleteBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        bookingService.deleteBooking(bookingId, userId);
//        return ResponseEntity.ok("Booking deleted successfully");
//    }
//    // TODO: Comments and Ratings for a Movie
//    @PostMapping("/movieRespond/add")
//    public ResponseEntity<MovieRespondResponse> addRespond(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        MovieRespondResponse movieRespondResponse = movieRespondService.createMovieRespond(userId, movieRespondRequest);
//        return ResponseEntity.ok(movieRespondResponse);
//    }
//    @PutMapping("/movieRespond/update")
//    public ResponseEntity<MovieRespondResponse> updateRespond(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        MovieRespondResponse movieRespondResponse = movieRespondService.updateMovieRespond(userId, movieRespondRequest);
//        return ResponseEntity.ok(movieRespondResponse);
//    }
//    @DeleteMapping("/movieRespond/delete/{movieId}")
//    public ResponseEntity<String> deleteRespond(HttpServletRequest request, @PathVariable Integer movieId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        movieRespondService.deleteMovieRespond(userId, movieId);
//        return ResponseEntity.ok("Delete Movie Respond Successfully");
//    }
//    // TODO: View
//    @GetMapping("/view/cityList")
//    public ResponseEntity<ViewCityResponse> getCities() {
//        ViewCityResponse viewCityResponse = viewService.getAvailableCities();
//        return ResponseEntity.ok(viewCityResponse);
//    }
//    @GetMapping("/view/cinemaList")
//    public ResponseEntity<ViewCinemaResponse> getCinemaList() {
//        ViewCinemaResponse viewCinemaResponse = viewService.getAvailableCinemaList();
//        return ResponseEntity.ok(viewCinemaResponse);
//    }
//    @GetMapping("/view/allScreen")
//    public ResponseEntity<List<ScreenResponse>> getAllScreen() {
//        List<ScreenResponse> screenResponses = viewService.getAllScreens();
//        return ResponseEntity.ok(screenResponses);
//    }
//    @GetMapping("/view/allScheduleByCinema/{cinemaId}")
//    public ResponseEntity<ScheduleSelectedByCinemaResponse> getAllSchedulesBySelectedCinema(@PathVariable Integer cinemaId) {
//        ScheduleSelectedByCinemaResponse scheduleResponses = viewService.getAllSchedulesBySelectedCinema(cinemaId);
//        return ResponseEntity.ok(scheduleResponses);
//    }
//    @GetMapping("/view/allScheduleByCinemaAndMovie")
//    public ResponseEntity<ScheduleResponse> getAllScheduleByCinemaAndMovie(
//            @RequestParam Integer movieId,
//            @RequestParam Integer cinemaId
//    ) {
//        ScheduleResponse screenResponses = viewService.getAllSchedulesBySelectedMovieAndSelectedCinema(movieId, cinemaId);
//        return ResponseEntity.ok(screenResponses);
//    }
//    @GetMapping("/view/getAllMovieInformationBySelectedDateSchedule")
//    public ResponseEntity<List<MovieResponse>> getAllMovieInformationBySelectedDateSchedule(@RequestParam String date) {
//        List<MovieResponse> listFoodAndDrinkToOrderingResponse = viewService.getAllMovieInformationBySelectedDateSchedule(date);
//        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponse);
//    }
//    @GetMapping("/view/getAllFoodsAndDrinksByCinema/{cinemaId}")
//    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> viewAllFoodsAndDrinksByCinema(@PathVariable Integer cinemaId) {
//        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponse = viewService.getAllFoodsAndDrinksByCinema(cinemaId);
//        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponse);
//    }
//    @GetMapping("/view/allCoupons")
//    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
//        List<CouponResponse> couponResponses = viewService.getAllCoupons();
//        return ResponseEntity.ok(couponResponses);
//    }
//    @GetMapping("/view/couponsByUser")
//    public ResponseEntity<ViewCouponsResponse> getCoupons(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        ViewCouponsResponse viewCouponsResponse = viewService.getAvailableCouponsForUser(userId);
//        return ResponseEntity.ok(viewCouponsResponse);
//    }
//    @GetMapping("/view/couponsByMovie/{movieId}")
//    public ResponseEntity<ViewCouponsResponse> getCouponsByMovie(@PathVariable Integer movieId) {
//        ViewCouponsResponse viewCouponsResponse = viewService.getAvailableCouponsByMovieId(movieId);
//        return ResponseEntity.ok(viewCouponsResponse);
//    }
//    @GetMapping("/view/comingSoonMovies")
//    public ResponseEntity<List<MovieDetail>> getAvailableComingSoonMovies() {
//        return ResponseEntity.ok(viewService.getAvailableComingSoonMovies());
//    }
//    @GetMapping("/view/notifications")
//    public ResponseEntity<NotificationResponse> getNotifications(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        NotificationResponse notificationResponse = notificationService.getNotificationsByUserId(userId);
//        return ResponseEntity.ok(notificationResponse);
//    }
//    @GetMapping("/view/allBookings")
//    public ResponseEntity<List<BookingResponse>> getAllBookings() {
//        List<BookingResponse> bookingResponses = viewService.getAllBookings();
//        return ResponseEntity.ok(bookingResponses);
//    }
//    @GetMapping("/view/allBookingsByUser")
//    public ResponseEntity<List<BookingResponse>> getAllBookingsByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<BookingResponse> bookingResponses = viewService.getAllBookingsByUser(userId);
//        return ResponseEntity.ok(bookingResponses);
//    }
//    @GetMapping("/view/getAllCompletedBookingsThatHaveStartDateTimeHigherThanNowByUser")
//    public ResponseEntity<List<BookingResponse>> getAllCompletedBookingsThatHaveStartDateTimeHigherThanNowByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<BookingResponse> bookingResponses = viewService.getAllCompletedBookingsThatHaveStartDateTimeHigherThanNowByUser(userId);
//        return ResponseEntity.ok(bookingResponses);
//    }
//    @GetMapping("/view/getAllBookingsThatHaveStartDateTimeHigherThanNowByUser")
//    public ResponseEntity<List<BookingResponse>> getAllBookingsThatHaveStartDateTimeHigherThanNowByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<BookingResponse> bookingResponses = viewService.getAllBookingsThatHaveStartDateTimeHigherThanNowByUser(userId);
//        return ResponseEntity.ok(bookingResponses);
//    }
//    @GetMapping("/view/allBookingsCanceledByUser")
//    public ResponseEntity<List<BookingResponse>> getAllBookingsCanceled(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<BookingResponse> bookingResponses = viewService.getAllBookingsCanceledByUser(BookingStatus.CANCELLED, userId);
//        return ResponseEntity.ok(bookingResponses);
//    }
//    @GetMapping("/view/viewCommentByUserAndMovie/{movieId}")
//    public ResponseEntity<CommentResponse> viewCommentByUserAndMovie(HttpServletRequest request, @PathVariable Integer movieId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        CommentResponse commentResponse = movieRespondService.getMovieCommentByUserIdAndMovieId(userId, movieId);
//        return ResponseEntity.ok(commentResponse);
//    }
//    @GetMapping("/view/viewCommentByUser")
//    public ResponseEntity<List<CommentResponse>> viewCommentByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByUserId(userId);
//        return ResponseEntity.ok(commentResponses);
//    }
//    @GetMapping("/view/viewCommentByMovie/{movieId}")
//    public ResponseEntity<List<CommentResponse>> viewCommentByMovie(@PathVariable Integer movieId) {
//        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByMovieId(movieId);
//        return ResponseEntity.ok(commentResponses);
//    }
//    @GetMapping("/view/viewRatingByUserAndMovie/{movieId}")
//    public ResponseEntity<RatingResponse> viewRatingByUserAndMovie(HttpServletRequest request, @PathVariable Integer movieId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        RatingResponse ratingResponse = movieRespondService.getMovieRatingByUserIdAndMovieId(userId, movieId);
//        return ResponseEntity.ok(ratingResponse);
//    }
//    @GetMapping("/view/viewRatingByUser")
//    public ResponseEntity<List<RatingResponse>> viewRatingByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByUserId(userId);
//        return ResponseEntity.ok(ratingResponses);
//    }
//    @GetMapping("/view/viewRatingByMovie/{movieId}")
//    public ResponseEntity<List<RatingResponse>> viewRatingByMovie(@PathVariable Integer movieId) {
//        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByMovieId(movieId);
//        return ResponseEntity.ok(ratingResponses);
//    }
//    @GetMapping("/view/viewMovieRespondByUserAndMovie/{movieId}")
//    public ResponseEntity<MovieRespondResponse> viewMovieRespondByUserAndMovie(HttpServletRequest request, @PathVariable Integer movieId) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        MovieRespondResponse movieRespondResponse = movieRespondService.getMovieRespondsByUserIdAndMovieId(userId, movieId);
//        return ResponseEntity.ok(movieRespondResponse);
//    }
//    @GetMapping("/view/viewMovieRespondByUser")
//    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByUser(HttpServletRequest request) {
//        Integer userId = jwtUtil.getUserIdFromToken(request);
//        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByUserId(userId);
//        return ResponseEntity.ok(movieRespondResponses);
//    }
//    @GetMapping("/view/viewMovieRespondByMovie/{movieId}")
//    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByMovie(@PathVariable Integer movieId) {
//        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByMovieId(movieId);
//        return ResponseEntity.ok(movieRespondResponses);
//    }
}

