package vn.edu.usth.mcma.frontend.component.Showtimes.UI;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.dto.Response.ListFoodAndDrinkToOrderingResponse;
import vn.edu.usth.mcma.frontend.network.apis.ViewAllFoodsAndDrinksByCinemaAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.component.Showtimes.Adapters.ComboAdapter;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.component.Showtimes.Utils.PriceCalculator;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

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

        movieId = getIntent().getIntExtra(IntentKey.MOVIE_ID.name(), -1);
        selectedCityId = getIntent().getIntExtra(IntentKey.SELECTED_CITY_ID.name(), -1);
        cinemaId = getIntent().getIntExtra(IntentKey.SELECTED_CINEMA_ID.name(), -1);
        selectedScreenId = getIntent().getIntExtra(IntentKey.SELECTED_SCREEN_ID.name(), -1);
        selectedScheduleId = getIntent().getIntExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), -1);

        totalTicketCount = getIntent().getIntExtra(IntentKey.TOTAL_TICKET_COUNT.name(), 0);
        Log.d("ComboSelection", IntentKey.TOTAL_TICKET_COUNT.name() +" received: " + totalTicketCount);
        totalTicketPrice = getIntent().getDoubleExtra(IntentKey.TOTAL_TICKET_PRICE.name(), 0.0);
        Log.d("ComboSelection", IntentKey.TOTAL_TICKET_PRICE.name()+" received: " + totalTicketPrice);
        items = getIntent().getParcelableArrayListExtra(IntentKey.SELECTED_TICKET_ITEMS.name());
        Log.d("ComboSelection", IntentKey.SELECTED_TICKET_ITEMS.name()+" received: " + items);

        seatItems = getIntent().getParcelableArrayListExtra(IntentKey.SELECTED_SEAT_ITEMS.name());
        Log.d("ComboSelection", IntentKey.SELECTED_SEAT_ITEMS.name()+" received: " + seatItems);

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
            seatPriceTotal = getIntent().getDoubleExtra(IntentKey.TOTAL_TICKET_AND_SEAT_PRICE.name(), 0.0);
            int seatCount = getIntent().getIntExtra(IntentKey.TOTAL_SEAT_COUNT.name(), 0);

            // Theater name handling
            Theater selectedTheater = (Theater) getIntent().getSerializableExtra(EXTRA_THEATER);
            String theaterName = getIntent().getStringExtra(IntentKey.THEATER_NAME.name());
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

            String selectedDate = getIntent().getStringExtra(IntentKey.SELECTED_DATE.name());
            if (releaseDateTV != null) {
                releaseDateTV.setText(selectedDate);
            }

            // Screen number handling
            String selectedScreenRoom = getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name());
            if (screenRoomTV != null) {
                screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
            }

            String selectedShowtime = getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name());
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
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name());
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

            selectedTicketIds =  getIntent().getIntegerArrayListExtra(IntentKey.SELECTED_TICKET_IDS.name());
            selectedSeatIds = getIntent().getIntegerArrayListExtra(IntentKey.SELECTED_SEAT_IDS.name());


            // Create intent to PaymentBookingActivity
            Intent intent = new Intent(this, PaymentBookingActivity.class);
            intent.putParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name(), new ArrayList<>(ticketItems));
            intent.putExtra(IntentKey.SELECTED_MOVIE.name(), getIntent().getSerializableExtra(IntentKey.SELECTED_MOVIE.name()));
            intent.putExtra(IntentKey.SELECTED_THEATER.name(), getIntent().getSerializableExtra(IntentKey.SELECTED_THEATER.name()));
            intent.putExtra(IntentKey.THEATER_NAME.name(), getIntent().getStringExtra(IntentKey.THEATER_NAME.name()));
            intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name()));
            intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name()));
            intent.putExtra(IntentKey.SELECTED_DATE.name(), getIntent().getStringExtra(IntentKey.SELECTED_DATE.name()));
            int movieBannerResId = getIntent().getIntExtra(IntentKey.MOVIE_BANNER.name(), 0);
            intent.putExtra(IntentKey.MOVIE_BANNER.name(), movieBannerResId);
            intent.putExtra(IntentKey.MOVIE_NAME.name(), movieName);
            intent.putExtra(IntentKey.CINEMA_NAME.name(), cinemaName);
            intent.putExtra(IntentKey.TOTAL_TICKET_COUNT.name(), totalTicketCount);
            intent.putExtra(IntentKey.TOTAL_COMBO_COUNT.name(), totalComboCount);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_COMBO_ITEMS.name(), new ArrayList<>(comboItemList));
            intent.putExtra(IntentKey.TOTAL_PRICE_OF_SELECTED_CHOICE.name(), totalPriceOfSelectedChoice);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_TICKET_ITEMS.name(), new ArrayList<>(items));
            intent.putExtra(IntentKey.TOTAL_TICKET_PRICE.name(), totalTicketPrice);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_SEAT_ITEMS.name(), new ArrayList<>(seatItems));

            // Booking
            intent.putExtra(IntentKey.MOVIE_ID.name(), movieId);
            intent.putExtra(IntentKey.SELECTED_CITY_ID.name(), selectedCityId);
            intent.putExtra(IntentKey.SELECTED_CINEMA_ID.name(), cinemaId);
            intent.putExtra(IntentKey.SELECTED_SCREEN_ID.name(), selectedScreenId);
            intent.putExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), selectedScheduleId);
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_TICKET_IDS.name(), new ArrayList<>(selectedTicketIds));
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_SEAT_IDS.name(), new ArrayList<>(selectedSeatIds));
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_FOOD_IDS.name(), new ArrayList<>(selectedFoodIds));
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_DRINK_IDS.name(), new ArrayList<>(selectedDrinkIds));
            startActivity(intent);
        });
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
