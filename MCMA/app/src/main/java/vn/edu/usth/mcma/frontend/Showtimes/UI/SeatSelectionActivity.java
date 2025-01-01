package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.SeatStatus;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.HeldSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.UnavailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllAvailableSeatsByScreenAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllHeldSeatsByScreenAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllUnavailableSeatsByScreenAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.SeatAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;

public class SeatSelectionActivity extends AppCompatActivity {
    private double totalTicketPrice;
    private int totalTicketCount;
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private Theater selectedTheater;
    private String selectedShowtime;
    private Movie selectedMovie;
    private TextView showtime;
    private int guestQuantity;
    private double totalTicketAndSeatPrice;
    private int totalSeatCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        totalTicketCount = getIntent().getIntExtra("TOTAL_TICKET_COUNT", 0);
        totalTicketPrice = getIntent().getDoubleExtra("TOTAL_TICKET_PRICE", 0.0);
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        guestQuantity = totalTicketCount;

        setupTheaterInfo();
        setupRecyclerView();
        setupCheckoutButton();
        setupBackButton();
        fetchAllSeats();
    }

    private void setupRecyclerView() {
        seatRecyclerView = findViewById(R.id.seatRecyclerView);
        seatRecyclerView.setLayoutManager(new GridLayoutManager(this, 10));
    }

    private void fetchAllSeats() {
        int screenId = getIntent().getIntExtra("SELECTED_SCREEN", 1);
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllAvailableSeatsByScreenAPI api = retrofitService.getRetrofit().create(GetAllAvailableSeatsByScreenAPI.class);

        api.getAvailableSeatsByScreen(screenId).enqueue(new Callback<List<AvailableSeatResponse>>() {
            @Override
            public void onResponse(Call<List<AvailableSeatResponse>> call, Response<List<AvailableSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AvailableSeatResponse> availableSeats = response.body();

                    // Fetch other seat types (Unavailable and Held)
                    fetchUnavailableAndHeldSeats(screenId, availableSeats);
                } else {
                    Toast.makeText(SeatSelectionActivity.this, "Failed to fetch available seats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AvailableSeatResponse>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUnavailableAndHeldSeats(int screenId, List<AvailableSeatResponse> availableSeats) {
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllUnavailableSeatsByScreenAPI getAllUnavailableSeatsByScreenAPI = retrofitService.getRetrofit().create(GetAllUnavailableSeatsByScreenAPI.class);
        getAllUnavailableSeatsByScreenAPI.getUnavailableSeatsByScreen(screenId).enqueue(new Callback<List<UnavailableSeatResponse>>() {
            @Override
            public void onResponse(Call<List<UnavailableSeatResponse>> call, Response<List<UnavailableSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UnavailableSeatResponse> unavailableSeats = response.body();
                    fetchHeldSeats(screenId, availableSeats, unavailableSeats);
                } else {
                    Toast.makeText(SeatSelectionActivity.this, "Failed to fetch unavailable seats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UnavailableSeatResponse>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchHeldSeats(int screenId, List<AvailableSeatResponse> availableSeats, List<UnavailableSeatResponse> unavailableSeats) {
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllHeldSeatsByScreenAPI getAllHeldSeatsByScreenAPI = retrofitService.getRetrofit().create(GetAllHeldSeatsByScreenAPI.class);
        getAllHeldSeatsByScreenAPI.getHeldSeatsByScreen(screenId).enqueue(new Callback<List<HeldSeatResponse>>() {
            @Override
            public void onResponse(Call<List<HeldSeatResponse>> call, Response<List<HeldSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HeldSeatResponse> heldSeats = response.body();

                    // Combine all seats and sort them by row and column
                    List<Object> allSeats = new ArrayList<>();
                    allSeats.addAll(availableSeats);
                    allSeats.addAll(unavailableSeats);
                    allSeats.addAll(heldSeats);

                    // Sort the list by row and column
                    Collections.sort(allSeats, new Comparator<Object>() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            int row1 = getRow(o1);
                            int col1 = getColumn(o1);
                            int row2 = getRow(o2);
                            int col2 = getColumn(o2);

                            // Sort by row first, then by column
                            if (row1 != row2) {
                                return Integer.compare(row1, row2);
                            } else {
                                return Integer.compare(col1, col2);
                            }
                        }
                    });

                    seatAdapter = new SeatAdapter(allSeats, SeatSelectionActivity.this, new SeatAdapter.OnSeatSelectedListener() {
                        @Override
                        public void onSeatSelected(AvailableSeatResponse seat) {
                            updateSelectedSeatsDisplay();
                        }
                    }, guestQuantity);

                    seatRecyclerView.setAdapter(seatAdapter);
                } else {
                    Toast.makeText(SeatSelectionActivity.this, "Failed to fetch held seats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HeldSeatResponse>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getRow(Object seat) {
        if (seat instanceof AvailableSeatResponse) {
            return ((AvailableSeatResponse) seat).getSeatRow();
        } else if (seat instanceof UnavailableSeatResponse) {
            return ((UnavailableSeatResponse) seat).getSeatRow();
        } else if (seat instanceof HeldSeatResponse) {
            return ((HeldSeatResponse) seat).getSeatRow();
        }
        return 0;
    }

    private int getColumn(Object seat) {
        if (seat instanceof AvailableSeatResponse) {
            return ((AvailableSeatResponse) seat).getSeatColumn();
        } else if (seat instanceof UnavailableSeatResponse) {
            return ((UnavailableSeatResponse) seat).getSeatColumn();
        } else if (seat instanceof HeldSeatResponse) {
            return ((HeldSeatResponse) seat).getSeatColumn();
        }
        return 0;
    }


    @SuppressLint("SetTextI18n")
    public void updateSelectedSeatsDisplay() {
        TextView noOfSeatsTV = findViewById(R.id.no_of_seats);
        TextView seatPriceTV = findViewById(R.id.seat_price_total);
        Button checkoutButton = findViewById(R.id.checkout_button);

        Set<AvailableSeatResponse> selectedSeats = seatAdapter.getSelectedSeats();
        int seatCount = selectedSeats.size();
        noOfSeatsTV.setText(seatCount + " seat(s)");

        // Tính giá tiền ghế
        double seatPriceAdditional = calculateTotalPrice(selectedSeats);
        double totalPrice = totalTicketPrice + seatPriceAdditional;
        totalTicketAndSeatPrice = totalPrice;
        totalSeatCount = seatCount;
        seatPriceTV.setText(formatCurrency(totalPrice));

        // Kích hoạt nút "Checkout" nếu số ghế chọn đúng
        boolean isCorrectSeatCount = seatCount == guestQuantity;
        checkoutButton.setEnabled(isCorrectSeatCount);
        checkoutButton.setBackgroundResource(
                isCorrectSeatCount ? R.drawable.rounded_active_background : R.drawable.rounded_dark_background
        );
    }

    @SuppressLint("SetTextI18n")
    private void setupTheaterInfo() {
        TextView theaterNameTV = findViewById(R.id.theater_name);
        TextView movieNameTV = findViewById(R.id.movie_name2);
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView releaseDateTV = findViewById(R.id.movie_release_date);
        showtime = findViewById(R.id.movie_duration);

        // Get selected screen room from intent
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (selectedScreenRoom != null) {
            screenNumberTV.setText(selectedScreenRoom);
        } else {
            screenNumberTV.setText("Screen 1");
        }
        theaterNameTV.setText(selectedTheater.getName());
        movieNameTV.setText(selectedMovie.getTitle());

        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        if (releaseDateTV != null) {
            releaseDateTV.setText(selectedDate);
        }

        String selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        if(showtime != null){
            showtime.setText(selectedShowtime);
        }
    }

    private double calculateTotalPrice(Set<AvailableSeatResponse> selectedSeats) {
        return selectedSeats.stream()
                .mapToDouble(seat -> seat.getSeatPrice() != null ? seat.getSeatPrice().intValue() : 0)
                .sum();
    }

    private String formatCurrency(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(price);
    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            Set<AvailableSeatResponse> selectedSeats = seatAdapter.getSelectedSeats();
            int seatCount = selectedSeats.size();
            if (seatCount != guestQuantity) {
                Toast.makeText(this, "Please choose the correct number of seats", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate total seat price
            double seatPriceAdditional = calculateTotalPrice(selectedSeats);
            double totalPrice = totalTicketPrice + seatPriceAdditional;
            ArrayList<AvailableSeatResponse> seatsList = new ArrayList<>(selectedSeats);

            // Prepare intent for ComboSelectionActivity
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
            Intent intent = new Intent(this, ComboSelectionActivity.class);

            intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketItems));
//            intent.putParcelableArrayListExtra(ComboSelectionActivity.EXTRA_SELECTED_SEATS, new ArrayList<>(selectedSeats));
            // Pass through extras from previous activities
            intent.putExtra(ComboSelectionActivity.EXTRA_THEATER, getIntent().getSerializableExtra("SELECTED_THEATER"));
            intent.putExtra("THEATER_NAME", getIntent().getStringExtra("THEATER_NAME"));
            int cinemaId = getIntent().getIntExtra("CINEMA_ID", 0);
            cinemaId = selectedTheater.getId();
            intent.putExtra("CINEMA_ID", cinemaId);
            intent.putExtra(ComboSelectionActivity.EXTRA_MOVIE, getIntent().getSerializableExtra("SELECTED_MOVIE"));
            intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
            intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));
            int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
            intent.putExtra("MOVIE_BANNER", movieBannerResId);
            intent.putExtra("SELECTED_DATE", getIntent().getStringExtra("SELECTED_DATE"));
            intent.putExtra("MOVIE_TITLE", getIntent().getStringExtra("MOVIE_TITLE"));
            intent.putExtra("TOTAL_TICKET_AND_SEAT_PRICE", totalTicketAndSeatPrice);
            intent.putExtra("TOTAL_SEAT_COUNT", totalSeatCount);
            intent.putExtra("TOTAL_TICKET_COUNT", totalTicketCount);
            startActivity(intent);
        });
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}