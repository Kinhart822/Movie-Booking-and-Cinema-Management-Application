package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ListFoodAndDrinkToOrderingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.ViewAllFoodsAndDrinksByCinemaAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Models.DrinkItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.FoodItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.ComboAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;

public class ComboSelectionActivity extends AppCompatActivity {
    public static final String EXTRA_SELECTED_SEATS = "extra_selected_seats";
    public static final String EXTRA_THEATER = "extra_theater";
    public static final String EXTRA_MOVIE = "extra_movie";

    private double seatPriceTotal;
    private RecyclerView comboRecyclerView;
    private Button checkoutButton;
    private ComboAdapter comboAdapter;
    private TextView seatCountText;
    private TextView seatPriceText;
    private TextView comboPriceText;
    private TextView totalPriceText;
    private TextView theaterNameTV;
    private String cinemaName;
    private TextView movieNameTV;
    private String movieName;
    private TextView releaseDateTV;
    private TextView showtime;
    private TextView screenRoomTV;
    private int cinemaId;
    private int movieId;
    private int totalTicketCount;
    private List<ComboItem> comboItemList = new ArrayList<>();
    private int totalComboCount;
    private List<TicketItem> items = new ArrayList<>();
    private List<AvailableSeatResponse> seatItems = new ArrayList<>();
    private double totalTicketPrice;
    private double totalPriceOfSelectedChoice;
    private int selectedCityId;
    private int selectedScreenId;
    private int selectedScheduleId;
    private List<Integer> selectedTicketIds = new ArrayList<>();
    private List<Integer> selectedSeatIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_selection);

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        selectedCityId = getIntent().getIntExtra("SELECTED_CITY_ID", -1);
        cinemaId = getIntent().getIntExtra("SELECTED_CINEMA_ID", -1);
        selectedScreenId = getIntent().getIntExtra("SELECTED_SCREEN_ID", -1);
        selectedScheduleId = getIntent().getIntExtra("SELECTED_SCHEDULE_ID", -1);

        totalTicketCount = getIntent().getIntExtra("TOTAL_TICKET_COUNT", 0);
        Log.d("ComboSelection", "TOTAL_TICKET_COUNT received: " + totalTicketCount);
        totalTicketPrice = getIntent().getDoubleExtra("TOTAL_TICKET_PRICE", 0.0);
        Log.d("ComboSelection", "TOTAL_TICKET_PRICE received: " + totalTicketPrice);
        items = getIntent().getParcelableArrayListExtra("SELECTED_TICKET_ITEMS");
        Log.d("ComboSelection", "SELECTED_TICKET_ITEMS received: " + items);

        seatItems = getIntent().getParcelableArrayListExtra("SELECTED_SEAT_ITEMS");
        Log.d("ComboSelection", "SELECTED_SEAT_ITEMS received: " + seatItems);

        initializeViews();
        handleIntentExtras();
        setupBackButton();
        setupCheckoutButton();
        fetchComboItems(cinemaId);
    }

    @SuppressLint("DefaultLocale")
    private void handleIntentExtras() {
        try {
            // Parse seat price and count
            seatPriceTotal = getIntent().getDoubleExtra("TOTAL_TICKET_AND_SEAT_PRICE", 0.0);
            int seatCount = getIntent().getIntExtra("TOTAL_SEAT_COUNT", 0);

            // Theater name handling
            Theater selectedTheater = (Theater) getIntent().getSerializableExtra(EXTRA_THEATER);
            String theaterName = getIntent().getStringExtra("THEATER_NAME");
            if (selectedTheater != null && theaterNameTV != null) {
                theaterNameTV.setText(theaterName != null ? theaterName : selectedTheater.getName());
                cinemaName = selectedTheater.getName();
            }

            // Movie name handling
            Movie selectedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
            if (selectedMovie != null && movieNameTV != null) {
                movieNameTV.setText(selectedMovie.getTitle());
                movieName = selectedMovie.getTitle();
            }

            String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
            if (releaseDateTV != null) {
                releaseDateTV.setText(selectedDate);
            }

            // Screen number handling
            String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
            if (screenRoomTV != null) {
                screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
            }

            String selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
            if (showtime != null) {
                showtime.setText(selectedShowtime);
            }

            // Find TextView references with null checks
            TextView seatCountText = findViewById(R.id.no_of_seats);
            TextView seatPriceText = findViewById(R.id.seat_price_total);

            if (seatCountText != null) {
                seatCountText.setText(String.format("%d seat(s)", seatCount));
            } else {
                Log.e("ComboSelectionActivity", "seatCountText is null");
            }

            if (seatPriceText != null) {
                seatPriceText.setText(PriceCalculator.formatPrice(seatPriceTotal));
            } else {
                Log.e("ComboSelectionActivity", "seatPriceText is null");
            }
        } catch (Exception e) {
            Log.e("ComboSelectionActivity", "Error in handleIntentExtras", e);
            // Optionally show a toast or handle the error appropriately
            Toast.makeText(this, "Error loading seat information", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews() {
        comboRecyclerView = findViewById(R.id.combo_recycler_view);
        checkoutButton = findViewById(R.id.checkout_button);
        seatCountText = findViewById(R.id.no_of_seats);
        seatPriceText = findViewById(R.id.seat_price_total);
        comboPriceText = findViewById(R.id.combo_price_total);
        totalPriceText = findViewById(R.id.total_price_0);
        theaterNameTV = findViewById(R.id.theater_name);
        releaseDateTV = findViewById(R.id.movie_release_date);
        screenRoomTV = findViewById(R.id.screen_number);
        showtime = findViewById(R.id.movie_duration);

        movieNameTV = findViewById(R.id.movie_name2);
        if (checkoutButton == null) {
            Log.e("ComboSelectionActivity", "checkoutButton is null");
        }
    }

    private void fetchComboItems(int cinemaId) {
        RetrofitService retrofitService = new RetrofitService(this);
        ViewAllFoodsAndDrinksByCinemaAPI apiService = retrofitService.getRetrofit().create(ViewAllFoodsAndDrinksByCinemaAPI.class);

        apiService.ViewFoodsAndDrinks(cinemaId).enqueue(new Callback<List<ListFoodAndDrinkToOrderingResponse>>() {
            @Override
            public void onResponse(Call<List<ListFoodAndDrinkToOrderingResponse>> call, Response<List<ListFoodAndDrinkToOrderingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ListFoodAndDrinkToOrderingResponse> comboData = response.body();
                    List<ComboItem> comboItems = convertResponseToComboItems(comboData);
                    updateComboList(comboItems);
                    comboItemList.addAll(comboItems);
                } else {
                    Log.e("ComboSelectionActivity", "Failed to load combos: " + response.message());
                    Toast.makeText(ComboSelectionActivity.this, "No available combos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListFoodAndDrinkToOrderingResponse>> call, Throwable t) {
                Log.e("StoreFragment", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(ComboSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<ComboItem> convertResponseToComboItems(List<ListFoodAndDrinkToOrderingResponse> comboData) {
        List<ComboItem> items = new ArrayList<>();
//        List<FoodItem> foodItems = new ArrayList<>();
//        List<DrinkItem> drinkItems = new ArrayList<>();
        for (ListFoodAndDrinkToOrderingResponse response : comboData) {
            for (int i = 0; i < response.getFoodNameList().size(); i++) {
                items.add(new ComboItem(
                        response.getFoodNameList().get(i),
                        response.getImageUrlFoodList().get(i),
                        response.getFoodPriceList().get(i),
                        response.getFoodIds().get(i),
                        0
                ));
//                foodItems.add(new FoodItem(
//                        response.getFoodNameList().get(i),
//                        response.getImageUrlFoodList().get(i),
//                        response.getFoodPriceList().get(i),
//                        response.getFoodIds().get(i)
//                ));
            }
            for (int i = 0; i < response.getDrinkNameList().size(); i++) {
                items.add(new ComboItem(
                        response.getDrinkNameList().get(i),
                        response.getImageUrlDrinkList().get(i),
                        response.getDrinkPriceList().get(i),
                        response.getDrinkIds().get(i),
                        1
                ));
//                drinkItems.add(new DrinkItem(
//                        response.getDrinkNameList().get(i),
//                        response.getImageUrlDrinkList().get(i),
//                        response.getDrinkPriceList().get(i),
//                        response.getDrinkIds().get(i)
//                ));
            }
        }
        return items;
    }

    private void updateComboList(List<ComboItem> comboItems) {
        comboAdapter = new ComboAdapter(comboItems); // Gán giá trị cho biến toàn cục
        comboAdapter.setTotalPriceChangedListener(items -> {
            if (totalPriceText != null) {
                updateTotalPrice(comboItems);
            }
        });

        comboRecyclerView.setAdapter(comboAdapter);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(ComboSelectionActivity.this));
        updateTotalPrice(comboItems);
    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPrice(List<ComboItem> comboItems) {
        try {
            if (comboAdapter == null) {
                Log.e("ComboSelectionActivity", "ComboAdapter is null");
                return;
            }

            // Tính tổng giá combo
            double comboPriceTotal = comboItems.stream()
                    .mapToDouble(ComboItem::getTotalPrice)
                    .sum();

            totalComboCount = comboItems.stream()
                    .mapToInt(ComboItem::getQuantity)
                    .sum();
            Log.d("ComboSelectionActivity", "Combo Count:" + totalComboCount);

            // Tính tổng giá toàn bộ
            double totalPrice = seatPriceTotal + comboPriceTotal;
            totalPriceOfSelectedChoice = totalPrice;

            // Cập nhật UI
            if (comboPriceText != null) {
                comboPriceText.setText(String.format("$%.2f", comboPriceTotal));
            }
            if (totalPriceText != null) {
                totalPriceText.setText(String.format("$%.2f", totalPrice));
            }

            // Bật hoặc tắt nút thanh toán dựa trên tổng giá
            if (checkoutButton != null) {
                checkoutButton.setEnabled(totalPrice > 0);
                checkoutButton.setBackgroundResource(
                        totalPrice > 0 ? R.drawable.rounded_active_background : R.drawable.rounded_dark_background
                );
            }
        } catch (Exception e) {
            Log.e("ComboSelectionActivity", "Error in updateTotalPrice", e);
        }
    }


    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
            List<Seat> selectedSeats = getIntent().getParcelableArrayListExtra(EXTRA_SELECTED_SEATS);
//            List<ComboItem> selectedComboItems = comboAdapter.getSelectedComboItems();

            List<Integer> selectedFoodIds = new ArrayList<>();
            List<Integer> selectedDrinkIds = new ArrayList<>();
            comboItemList.stream()
                    .filter(item -> item.getType() == 0 && item.getQuantity() > 0)
                    .forEach(item -> {
                        for (int i = 0; i < item.getQuantity(); i++) {
                            selectedFoodIds.add(item.getComboIds());
                        }
                    });
            comboItemList.stream()
                    .filter(item -> item.getType() == 1 && item.getQuantity() > 0)
                    .forEach(item -> {
                        for (int i = 0; i < item.getQuantity(); i++) {
                            selectedDrinkIds.add(item.getComboIds());
                        }
                    });

            selectedTicketIds =  getIntent().getIntegerArrayListExtra("SELECTED_TICKET_IDS");
            selectedSeatIds = getIntent().getIntegerArrayListExtra("SELECTED_SEAT_IDS");


            // Create intent to PaymentBookingActivity
            Intent intent = new Intent(this, PaymentBookingActivity.class);
            intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketItems));
            intent.putExtra("SELECTED_MOVIE", getIntent().getSerializableExtra("SELECTED_MOVIE"));
            intent.putExtra("SELECTED_THEATER", getIntent().getSerializableExtra("SELECTED_THEATER"));
            intent.putExtra("THEATER_NAME", getIntent().getStringExtra("THEATER_NAME"));
            intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
            intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));
            intent.putExtra("SELECTED_DATE", getIntent().getStringExtra("SELECTED_DATE"));
            int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
            intent.putExtra("MOVIE_BANNER", movieBannerResId);
            intent.putExtra("MOVIE_NAME", movieName);
            intent.putExtra("CINEMA_NAME", cinemaName);
            intent.putExtra("TOTAL_TICKET_COUNT", totalTicketCount);
            intent.putExtra("TOTAL_COMBO_COUNT", totalComboCount);
            intent.putParcelableArrayListExtra("SELECTED_COMBO_ITEMS", new ArrayList<>(comboItemList));
            intent.putExtra("TOTAL_PRICE_OF_SELECTED_CHOICE", totalPriceOfSelectedChoice);
            intent.putParcelableArrayListExtra("SELECTED_TICKET_ITEMS", new ArrayList<>(items));
            intent.putExtra("TOTAL_TICKET_PRICE", totalTicketPrice);
            intent.putParcelableArrayListExtra("SELECTED_SEAT_ITEMS", new ArrayList<>(seatItems));

            // Booking
            intent.putExtra("MOVIE_ID", movieId);
            intent.putExtra("SELECTED_CITY_ID", selectedCityId);
            intent.putExtra("SELECTED_CINEMA_ID", cinemaId);
            intent.putExtra("SELECTED_SCREEN_ID", selectedScreenId);
            intent.putExtra("SELECTED_SCHEDULE_ID", selectedScheduleId);
            intent.putIntegerArrayListExtra("SELECTED_TICKET_IDS", new ArrayList<>(selectedTicketIds));
            intent.putIntegerArrayListExtra("SELECTED_SEAT_IDS", new ArrayList<>(selectedSeatIds));
            intent.putIntegerArrayListExtra("SELECTED_FOOD_IDS", new ArrayList<>(selectedFoodIds));
            intent.putIntegerArrayListExtra("SELECTED_DRINK_IDS", new ArrayList<>(selectedDrinkIds));
            startActivity(intent);
        });
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
