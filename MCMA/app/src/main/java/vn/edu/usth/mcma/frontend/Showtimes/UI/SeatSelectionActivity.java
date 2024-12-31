package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;

public class SeatSelectionActivity extends AppCompatActivity {
    private int totalTicketPrice; // store total ticket price from previous activity
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private List<List<Seat>> seatLayout;
    private Theater selectedTheater;
    private String selectedShowtime;
    private Movie selectedMovie;
    private int totalSeatsPerRow;
    private int guestQuantity; //variable to track kid ticket quantity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        // Retrieve guest quantity from previous activity
        List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
        guestQuantity = calculateTotalTicketQuantity(ticketItems);
        // Calculate total ticket price
        totalTicketPrice = calculateTotalTicketPrice(ticketItems);
        // Determine seats per row based on theater and movie duration
//        totalSeatsPerRow = determineTotalSeatsPerRow();
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        setupTheaterInfo();
//        setupSeatLayout();
        setupRecyclerView();
        setupCheckoutButton();
        setupBackButton();
        fetchAllSeats();
//        updateSelectedSeatsDisplay();
    }

    private int calculateTotalTicketQuantity(List<TicketItem> ticketItems) {
        return ticketItems.stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();
    }
    // New method to calculate total ticket price
    private int calculateTotalTicketPrice(List<TicketItem> ticketItems) {
        return (int) ticketItems.stream()
                .mapToDouble(TicketItem::getTotalPrice)
                .sum();
    }
    private void setupRecyclerView() {
        seatRecyclerView = findViewById(R.id.seatRecyclerView);
        seatRecyclerView.setLayoutManager(new GridLayoutManager(this, 10)); // Default grid size, updated later
    }

    private void fetchAllSeats() {
        int screenId = getIntent().getIntExtra("SELECTED_SCREEN_ROOM", 1);
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllAvailableSeatsByScreenAPI api = retrofitService.getRetrofit().create(GetAllAvailableSeatsByScreenAPI.class);

        api.getAvailableSeatsByScreen(screenId).enqueue(new Callback<List<AvailableSeatResponse>>() {
            @Override
            public void onResponse(Call<List<AvailableSeatResponse>> call, Response<List<AvailableSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Prepare seat layout from API response
                    prepareSeatLayout(response.body());
                    fetchUnavailableAndHeldSeats();

                } else {
                    Toast.makeText(SeatSelectionActivity.this, "Failed to fetch seat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AvailableSeatResponse>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchUnavailableAndHeldSeats() {
        int screenId = getIntent().getIntExtra("SELECTED_SCREEN_ROOM", 1);
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllUnavailableSeatsByScreenAPI getAllUnavailableSeatsByScreenAPI = retrofitService.getRetrofit().create(GetAllUnavailableSeatsByScreenAPI.class);
        getAllUnavailableSeatsByScreenAPI.getUnavailableSeatsByScreen(screenId).enqueue(new Callback<List<UnavailableSeatResponse>>() {
            @Override
            public void onResponse(Call<List<UnavailableSeatResponse>> call, Response<List<UnavailableSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateSeatLayoutWithUnavailableSeats(response.body());
                } else {
                    Toast.makeText(SeatSelectionActivity.this, "Failed to fetch unavailable seats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UnavailableSeatResponse>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this, "Failed to fetch unavailable seats", Toast.LENGTH_SHORT).show();
            }
        });

        GetAllHeldSeatsByScreenAPI getAllHeldSeatsByScreenAPI = retrofitService.getRetrofit().create(GetAllHeldSeatsByScreenAPI.class);
        getAllHeldSeatsByScreenAPI.getHeldSeatsByScreen(screenId).enqueue(new Callback<List<HeldSeatResponse>>() {
            @Override
            public void onResponse(Call<List<HeldSeatResponse>> call, Response<List<HeldSeatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateSeatLayoutWithHeldSeats(response.body());
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
    private void updateSeatLayoutWithUnavailableSeats(List<UnavailableSeatResponse> unavailableSeats) {
        for (UnavailableSeatResponse seat : unavailableSeats) {
            int row = seat.getSeatRow() - 1;
            int col = seat.getSeatColumn() - 1;

            if (seatLayout != null && row < seatLayout.size() && col < seatLayout.get(row).size()) {
                Seat currentSeat = seatLayout.get(row).get(col);
                if (currentSeat != null) {
                    currentSeat.setAvailable(false); // Mark seat as unavailable
                }
            }
        }
        seatAdapter.notifyDataSetChanged();
    }

    private void updateSeatLayoutWithHeldSeats(List<HeldSeatResponse> heldSeats) {
        for (HeldSeatResponse seat : heldSeats) {
            int row = seat.getSeatRow() - 1;
            int col = seat.getSeatColumn() - 1;

            if (seatLayout != null && row < seatLayout.size() && col < seatLayout.get(row).size()) {
                Seat currentSeat = seatLayout.get(row).get(col);
                if (currentSeat != null) {
                    currentSeat.setAvailable(false); // Mark seat as held (if held logic exists)
                }
            }
        }
        seatAdapter.notifyDataSetChanged();
    }

    private void prepareSeatLayout(List<AvailableSeatResponse> apiSeats) {
        // Determine the layout dimensions dynamically
        int maxRow = apiSeats.stream().mapToInt(AvailableSeatResponse::getSeatRow).max().orElse(0);
        int maxCol = apiSeats.stream().mapToInt(AvailableSeatResponse::getSeatColumn).max().orElse(0);

        // Initialize the seat layout
        seatLayout = new ArrayList<>();
        for (int i = 0; i < maxRow; i++) {
            seatLayout.add(new ArrayList<>());
            for (int j = 0; j < maxCol; j++) {
                seatLayout.get(i).add(null); // Placeholder for seats
            }
        }

        // Populate the layout with seat data
        for (AvailableSeatResponse seatResponse : apiSeats) {
            int row = seatResponse.getSeatRow() - 1;
            int col = seatResponse.getSeatColumn() - 1;

            SeatType seatType = determineSeatType(seatResponse.getAvailableSeatsType());
            boolean isAvailable = !"unAvailableSeat".equalsIgnoreCase(seatResponse.getAvailableSeat());

            seatLayout.get(row).set(col, new Seat(
                    seatResponse.getScreenName(),
                    seatType,
                    isAvailable
            ));
        }

        // Remove empty rows
        seatLayout.removeIf(row -> row.stream().allMatch(java.util.Objects::isNull));

        // Update the RecyclerView
        totalSeatsPerRow = maxCol;
        seatRecyclerView.setLayoutManager(new GridLayoutManager(this, totalSeatsPerRow));
        seatAdapter = new SeatAdapter(apiSeats, this, seat -> updateSelectedSeatsDisplay(), guestQuantity);
        seatRecyclerView.setAdapter(seatAdapter);
    }

    private SeatType determineSeatType(String seatType) {
        switch (seatType.toLowerCase()) {
            case "vip":
                return SeatType.VIP;
            case "couple":
                return SeatType.COUPLE;
            default:
                return SeatType.STAND;
        }
    }
    private void updateSelectedSeatsDisplay() {
        TextView noOfSeatsTV = findViewById(R.id.no_of_seats);
        TextView seatPriceTV = findViewById(R.id.seat_price_total);
        Button checkoutButton = findViewById(R.id.checkout_button);

        // Get selected seats from adapter
//        Set<Seat> selectedSeats = seatAdapter.getSelectedSeats();
        Set<AvailableSeatResponse> selectedSeats = seatAdapter.getSelectedSeats();
        // Update number of seats with special handling for couple seats
//        int seatCount = selectedSeats.stream()
//                .mapToInt(seat -> seat.getType() == SeatType.COUPLE ? 2 : 1)
//                .sum();
        int seatCount = selectedSeats.size();
        noOfSeatsTV.setText(seatCount + " ghế");

        // Calculate seat price
        int seatPriceAdditional = calculateTotalPrice(selectedSeats);
        int totalPrice = totalTicketPrice + seatPriceAdditional;
        seatPriceTV.setText(formatCurrency(totalPrice));

        // Enable/disable checkout based on selected seats matching ticket quantity
        boolean isCorrectSeatCount = seatCount == guestQuantity;
        checkoutButton.setEnabled(isCorrectSeatCount);
        checkoutButton.setBackgroundResource(
                isCorrectSeatCount ?
                        R.drawable.rounded_active_background :
                        R.drawable.rounded_dark_background
        );
    }

//    private int determineTotalSeatsPerRow() {
//        // Randomly choose between 12 and 17 seats
//        return new Random().nextInt(6) + 12;
//    }
//
//    private void setupSeatLayout() {
//        seatLayout = new ArrayList<>();
//        int rowCount = 8;
//
//        // Rows A to G with equal number of seats
//        for (int row = 0; row < rowCount - 1; row++) {
//            List<Seat> currentRow = createRowWithSeats(row);
//            seatLayout.add(currentRow);
//        }
//
//        // Last row (H) - Couple seats
//        List<Seat> coupleRow = createCoupleRow();
//        seatLayout.add(coupleRow);
//    }
//
//    private List<Seat> createRowWithSeats(int row) {
//        List<Seat> currentRow = new ArrayList<>();
//        int vipStartIndex = calculateVIPStartIndex();
//        int vipEndIndex = vipStartIndex + 4;
//
//        for (int seat = 0; seat < totalSeatsPerRow; seat++) {
//            SeatType seatType = determineSeatType(row, seat, vipStartIndex, vipEndIndex);
//            currentRow.add(new Seat(
//                    ((char)('A' + row)) + String.valueOf(seat + 1),
//                    seatType,
//                    seatType != SeatType.SOLD
//            ));
//        }
//        return currentRow;
//    }
//
//    private List<Seat> createCoupleRow() {
//        List<Seat> coupleRow = new ArrayList<>();
//        int coupleSeats = totalSeatsPerRow / 2;
//
//        for (int i = 0; i < coupleSeats; i++) {
//            coupleRow.add(new Seat(
//                    "H" + (i + 1),
//                    SeatType.COUPLE,
//                    true
//            ));
//        }
//
//        return coupleRow;
//    }
//
//    private int calculateVIPStartIndex() {
//        return (totalSeatsPerRow - 4) / 2;
//    }
//
//    private SeatType determineSeatType(int row, int seatIndex, int vipStartIndex, int vipEndIndex) {
//        // First 3 rows are standard seats
//        if (row < 3) {
//            return SeatType.STAND;
//        }
//
//        // VIP seats in middle rows (D, E, F, G)
//        if (row >= 3 && row <= 6) {
//            if (seatIndex >= vipStartIndex && seatIndex < vipStartIndex + 4) {
//                return SeatType.VIP;
//            }
//        }
//
//        // Randomly assign some seats as sold
//        return new Random().nextDouble() < 0.1 ? SeatType.SOLD : SeatType.STAND;
//    }
//
//    // Price calculation methods

//
//    private void setupRecyclerView() {
//        seatRecyclerView = findViewById(R.id.seatRecyclerView);
//        int maxRowSeats = seatLayout.stream().mapToInt(List::size).max().orElse(17);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, maxRowSeats);
//        seatRecyclerView.setLayoutManager(gridLayoutManager);
//        seatAdapter = new SeatAdapter(seatLayout, seat -> {
//            updateSelectedSeatsDisplay();
//        }, guestQuantity); // Pass total guest quantity to adapter
//        seatRecyclerView.setAdapter(seatAdapter);
//    }
//    private void fetchAllSeat(){
//        int screenId = getIntent().getIntExtra("SELECTED_SCREEN_ROOM",1);
//        RetrofitService retrofitService = new RetrofitService(this);
//        GetAllAvailableSeatsByScreenAPI getAllAvailableSeatsByScreenAPI = retrofitService.getRetrofit().create(GetAllAvailableSeatsByScreenAPI.class);
//        getAllAvailableSeatsByScreenAPI.getAvailableSeatsByScreen(screenId).enqueue(new Callback<List<AvailableSeatResponse>>() {
//            @Override
//            public void onResponse(Call<List<AvailableSeatResponse>> call, Response<List<AvailableSeatResponse>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<AvailableSeatResponse> apiSeats = response.body();
//
//                    // Prepare data for 10x10 grid
//                    List<List<AvailableSeatResponse>> rows = new ArrayList<>();
//                    for (int i = 0; i < 10; i++) { // Assume 10 rows initially
//                        List<AvailableSeatResponse> row = new ArrayList<>();
//                        for (int j = 0; j < 10; j++) { // 10 columns
//                            row.add(null); // Initialize with null
//                        }
//                        rows.add(row);
//                    }
//
//                    // Fill in the seat data from the API response
//                    for (AvailableSeatResponse seat : apiSeats) {
//                        int row = seat.getSeatRow() - 1;
//                        int col = seat.getSeatColumn() - 1;
//                        rows.get(row).set(col, seat);
//                    }
//
//                    // Filter out rows where all seats are null
//                    rows.removeIf(row -> row.stream().allMatch(java.util.Objects::isNull));
//
//                    // Flatten the rows back into a single list
//                    List<AvailableSeatResponse> filteredSeats = new ArrayList<>();
//                    for (List<AvailableSeatResponse> row : rows) {
//                        filteredSeats.addAll(row);
//                    }
//
//                    // Update the RecyclerView with new matrix size
//                    int columns = rows.isEmpty() ? 0 : rows.get(0).size();
//                    recyclerView.setLayoutManager(new GridLayoutManager(SeatSelectionActivity.this, columns));
//                    seatsAdapter = new SeatsAdapter(SeatSelectionActivity.this, filteredSeats);
//                    recyclerView.setAdapter(seatsAdapter);
//
//
//                } else {
//                    Toast.makeText(SeatSelectionActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<AvailableSeatResponse>> call, Throwable t) {
//
//            }
//        });
//    }
    private void setupTheaterInfo() {
        TextView theaterNameTV = findViewById(R.id.theater_name);
        TextView movieNameTV = findViewById(R.id.movie_name2);
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView releaseDateTV = findViewById(R.id.movie_release_date);
        TextView durationTV = findViewById(R.id.movie_duration);

        // Get selected screen room from intent
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (selectedScreenRoom != null) {
            screenNumberTV.setText(selectedScreenRoom);
        } else {
            screenNumberTV.setText("Screen 1"); // Default fallback
        }
        theaterNameTV.setText(selectedTheater.getName());
        movieNameTV.setText(selectedMovie.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd'th' MMM, yyyy", Locale.getDefault());
        releaseDateTV.setText(dateFormat.format(new Date()));
        String[] showtimeParts = selectedShowtime.split(":");
        int startHour = Integer.parseInt(showtimeParts[0]);
        int endHour = startHour + 2;
        durationTV.setText(String.format("%02d:00 - %02d:00", startHour, endHour));
    }
    private int calculateTotalPrice(Set<AvailableSeatResponse> selectedSeats) {
        int basePrice = 0;
        for (AvailableSeatResponse seat : selectedSeats) {
            String seatType = seat.getAvailableSeatsType(); // Ensure this method exists
            switch (seatType) {
                case "VIP":
                    basePrice += 150000; // VIP seat price
                    break;
                case "COUPLE":
                    basePrice += 200000; // Couple seat price
                    break;
                case "STANDARD":
                    basePrice += 100000; // Standard seat price
                    break;
                default:
                    break; // Handle other cases if necessary
            }
        }
        return basePrice;
    }

//    private int calculateTotalPrice(Set<Seat> selectedSeats) {
//        int basePrice = 0;
//        for (Seat seat : selectedSeats) {
//            switch (seat.getType()) {
//                case VIP:
//                    basePrice += 150000; // VIP seat price
//                    break;
//                case COUPLE:
//                    basePrice += 200000; // Couple seat price
//                    break;
//                case STAND:
//                    basePrice += 100000; // Standard seat price
//                    break;
//            }
//        }
//        return basePrice;
//    }

    private String formatCurrency(int price) {
        return String.format("%,d đ", price);
    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            Set<AvailableSeatResponse> selectedSeats = seatAdapter.getSelectedSeats();
            // Calculate seat count with special handling for COUPLE seats
//            int seatCount = selectedSeats.stream()
//                    .mapToInt(seat -> seat.getType() == SeatType.COUPLE ? 2 : 1)
//                    .sum();
            int seatCount = selectedSeats.size();
            if (seatCount != guestQuantity) {
                Toast.makeText(this, "Please choose the correct number of seats", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate total seat price
            int seatPriceAdditional = calculateTotalPrice(selectedSeats);
            int totalPrice = totalTicketPrice + seatPriceAdditional;
            ArrayList<AvailableSeatResponse> seatsList = new ArrayList<>(selectedSeats);

            // Prepare intent for ComboSelectionActivity
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
            Intent intent = new Intent(this, ComboSelectionActivity.class);

            intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketItems));
            intent.putExtra(ComboSelectionActivity.EXTRA_SEAT_PRICE, totalPrice);
            intent.putExtra(ComboSelectionActivity.EXTRA_SEAT_COUNT, guestQuantity);
//            intent.putParcelableArrayListExtra(ComboSelectionActivity.EXTRA_SELECTED_SEATS, new ArrayList<>(selectedSeats));
            // Pass through extras from previous activities
            intent.putExtra(ComboSelectionActivity.EXTRA_THEATER, getIntent().getSerializableExtra("SELECTED_THEATER"));
            intent.putExtra("THEATER_NAME", getIntent().getStringExtra("THEATER_NAME"));
            intent.putExtra(ComboSelectionActivity.EXTRA_MOVIE, getIntent().getSerializableExtra("SELECTED_MOVIE"));
            intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
            intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));
            int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
            intent.putExtra("MOVIE_BANNER", movieBannerResId);
            intent.putExtra("MOVIE_TITLE", getIntent().getStringExtra("MOVIE_TITLE"));
            startActivity(intent);
        });
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}