package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.HeldSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.UnavailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllAvailableSeatsByScreenAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllHeldSeatsByScreenAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllUnavailableSeatsByScreenAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.SeatAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
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
    private List<TicketItem> tickets = new ArrayList<>();
    private int movieId;
    private int selectedCityId;
    private int selectedCinemaId;
    private int selectedScreenId;
    private int selectedScheduleId;
    private List<Integer> selectedTicketIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        selectedCityId = getIntent().getIntExtra("SELECTED_CITY_ID", -1);
        selectedCinemaId = getIntent().getIntExtra("SELECTED_CINEMA_ID", -1);
        selectedScheduleId = getIntent().getIntExtra("SELECTED_SCHEDULE_ID", -1);

        totalTicketCount = getIntent().getIntExtra("TOTAL_TICKET_COUNT", 0);
        Log.d("SeatSelection", "TOTAL_TICKET_COUNT received: " + totalTicketCount);

        tickets = getIntent().getParcelableArrayListExtra("SELECTED_TICKET_ITEMS");
        Log.d("SeatSelection", "SELECTED_TICKET_ITEMS received: " + tickets);

        selectedTicketIds = getIntent().getIntegerArrayListExtra("SELECTED_TICKET_IDS");
        Log.d("SeatSelection", "SELECTED_TICKET_ITEMS received: " + selectedTicketIds);

        totalTicketPrice = getIntent().getDoubleExtra("TOTAL_TICKET_PRICE", 0.0);
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        guestQuantity = totalTicketCount;

        setupTheaterInfo();
        setupCheckoutButton();
        setupBackButton();
        fetchAllSeats();
    }

    private void fetchAllSeats() {
        selectedScreenId = getIntent().getIntExtra("SELECTED_SCREEN_ID", -1);
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllAvailableSeatsByScreenAPI api = retrofitService.getRetrofit().create(GetAllAvailableSeatsByScreenAPI.class);

        api.getAvailableSeatsByScreen(selectedScreenId).enqueue(new Callback<List<AvailableSeatResponse>>() {
            @Override
            public void onResponse(Call<List<AvailableSeatResponse>> call, Response<List<AvailableSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AvailableSeatResponse> availableSeats = response.body();

                    // Fetch other seat types (Unavailable and Held)
                    fetchUnavailableAndHeldSeats(selectedScreenId, availableSeats);
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

                    // Tính toán maxRow và maxColumn từ danh sách ghế
                    int maxRow = 0;
                    int maxColumn = 0;

                    // Tính toán maxRow và maxColumn từ các ghế có sẵn
                    for (AvailableSeatResponse seat : availableSeats) {
                        int row = getRow(seat);
                        int col = getColumn(seat);
                        if (row > maxRow) maxRow = row;
                        if (col > maxColumn) maxColumn = col;
                    }

                    // Tính toán maxRow và maxColumn từ các ghế không có sẵn
                    for (UnavailableSeatResponse seat : unavailableSeats) {
                        int row = getRow(seat);
                        int col = getColumn(seat);
                        if (row > maxRow) maxRow = row;
                        if (col > maxColumn) maxColumn = col;
                    }

                    // Tính toán maxRow và maxColumn từ các ghế đã giữ
                    for (HeldSeatResponse seat : heldSeats) {
                        int row = getRow(seat);
                        int col = getColumn(seat);
                        if (row > maxRow) maxRow = row;
                        if (col > maxColumn) maxColumn = col;
                    }

                    // Tạo ma trận với kích thước động
                    Object[][] seatMatrix = new Object[maxRow][maxColumn];
                    List<Object> allSeats = new ArrayList<>();
                    allSeats.addAll(availableSeats);
                    allSeats.addAll(unavailableSeats);
                    allSeats.addAll(heldSeats);

                    placeSeatsInMatrix(seatMatrix, allSeats);

                    seatAdapter = new SeatAdapter(seatMatrix, SeatSelectionActivity.this, new SeatAdapter.OnSeatSelectedListener() {
                        @Override
                        public void onSeatSelected(AvailableSeatResponse seat) {
                            updateSelectedSeatsDisplay();
                        }
                    }, guestQuantity);

                    seatRecyclerView = findViewById(R.id.seatRecyclerView);
                    seatRecyclerView.setLayoutManager(new GridLayoutManager(SeatSelectionActivity.this, seatMatrix[0].length + 1));
                    seatRecyclerView.setAdapter(seatAdapter);
                    seatAdapter.notifyDataSetChanged();
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

    private void placeSeatsInMatrix(Object[][] matrix, List<?> seats) {
        for (Object seat : seats) {
            int row = getRow(seat) - 1;
            int col = getColumn(seat) - 1;
            if (row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length && matrix[row][col] == null) {
                matrix[row][col] = seat;
            }
        }
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
        if (showtime != null) {
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

            List<Integer> selectedSeatIds = new ArrayList<>();
            List<AvailableSeatResponse> selectedSeatsList = new ArrayList<>(selectedSeats);
            for (AvailableSeatResponse seat : selectedSeatsList) {
                selectedSeatIds.add(seat.getSeatId());
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
            intent.putParcelableArrayListExtra("SELECTED_TICKET_ITEMS", new ArrayList<>(tickets));
            intent.putExtra("TOTAL_TICKET_PRICE", totalTicketPrice);
            intent.putParcelableArrayListExtra("SELECTED_SEAT_ITEMS", new ArrayList<>(selectedSeatsList));

            // Booking
            intent.putExtra("MOVIE_ID", movieId);
            intent.putExtra("SELECTED_CITY_ID", selectedCityId);
            intent.putExtra("SELECTED_CINEMA_ID", selectedCinemaId);
            intent.putExtra("SELECTED_SCREEN_ID", selectedScreenId);
            intent.putExtra("SELECTED_SCHEDULE_ID", selectedScheduleId);
            intent.putIntegerArrayListExtra("SELECTED_TICKET_IDS", new ArrayList<>(selectedTicketIds));
            intent.putIntegerArrayListExtra("SELECTED_SEAT_IDS", new ArrayList<>(selectedSeatIds));

            startActivity(intent);
        });
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}