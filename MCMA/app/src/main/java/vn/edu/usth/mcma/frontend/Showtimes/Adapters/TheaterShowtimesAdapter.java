package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.ScheduleResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.ScreenResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetDateScheduleByScreenIdAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetScreenByCinemaIdAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;

public class TheaterShowtimesAdapter extends RecyclerView.Adapter<TheaterShowtimesAdapter.TheaterViewHolder> {
    private List<Theater> theaters = new ArrayList<>();
    private String selectedDate;
    private String movieTitle;
    private OnShowtimeClickListener listener;
    private SparseBooleanArray expandedStates = new SparseBooleanArray();
    private Integer selectedScreenId;
    private Integer movieId;
    public interface OnShowtimeClickListener {
        void onShowtimeClick(Theater theater, String showtime, String screenRoom);
    }

    public TheaterShowtimesAdapter(OnShowtimeClickListener listener,Integer movieId) {
        this.listener = listener;
        this.movieId = movieId; // Initialize movieId
    }

    public void setTheaters(List<Theater> theaters, String selectedDate, String movieTitle) {
        this.theaters = theaters;
        this.selectedDate = selectedDate;
        this.movieTitle = movieTitle;
        expandedStates.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theater_showtimes_item, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        holder.bind(theaters.get(position), position, movieId);
    }

    @Override
    public int getItemCount() {
        return theaters.size();
    }

    class TheaterViewHolder extends RecyclerView.ViewHolder {
        private TextView theaterName;
        private TextView theaterAddress;
        private FlexboxLayout showtimesContainer;
        private FlexboxLayout screenRoomsContainer;
        private ImageView arrowIcon;
        private View divider;
        private ConstraintLayout headerLayout;
        private String selectedScreenRoom;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            theaterName = itemView.findViewById(R.id.theater_name);
            theaterAddress = itemView.findViewById(R.id.theater_address);
            showtimesContainer = itemView.findViewById(R.id.showtimes_container);
            screenRoomsContainer = itemView.findViewById(R.id.screen_rooms_container);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);
            divider = itemView.findViewById(R.id.divider);
            headerLayout = itemView.findViewById(R.id.header_layout);
        }

        void bind(Theater theater, int position,Integer movieId) {
            theaterName.setText(theater.getName());
            theaterAddress.setText(theater.getAddress());

            boolean isExpanded = expandedStates.get(position, false);
            screenRoomsContainer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            showtimesContainer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            arrowIcon.setRotation(isExpanded ? 180f : 0f);

            // Show divider for all items except the last one
            divider.setVisibility(position < theaters.size() - 1 ? View.VISIBLE : View.GONE);

            headerLayout.setOnClickListener(v -> {
                boolean newState = !expandedStates.get(position, false);
                expandedStates.put(position, newState);

                // Animate arrow rotation
                arrowIcon.animate()
                        .rotation(newState ? 180f : 0f)
                        .setDuration(200)
                        .start();

                // Show/hide showtimes container
                if (newState) {
                    screenRoomsContainer.setVisibility(View.VISIBLE);
                    populateScreenRooms(theater,theater.getId(),movieId);
                } else {
                    screenRoomsContainer.setVisibility(View.GONE);
                    showtimesContainer.setVisibility(View.GONE);
                }
            });

            // Populate showtimes only if expanded
            if (isExpanded) {
                populateScreenRooms(theater,theater.getId(),movieId);
            }
        }
        private void populateScreenRooms(Theater theater, int cinemaId, Integer movieId) {
            screenRoomsContainer.removeAllViews();

            Context context = itemView.getContext();
            ColorStateList textColorStateList = ContextCompat.getColorStateList(context, R.color.button_text_selector);

            // Use RetrofitService directly
            RetrofitService retrofitService = new RetrofitService(context);
            GetScreenByCinemaIdAPI apiService = retrofitService.getRetrofit().create(GetScreenByCinemaIdAPI.class);

            Call<List<ScreenResponse>> call = apiService.getScreenByCinemaId(cinemaId);
            call.enqueue(new Callback<List<ScreenResponse>>() {
                @Override
                public void onResponse(Call<List<ScreenResponse>> call, Response<List<ScreenResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<ScreenResponse> screens = response.body();

                        for (ScreenResponse screen : screens) {
                            Button screenRoomButton = new Button(context);
                            screenRoomButton.setText(screen.getScreenName());

                            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(8, 8, 8, 8);
                            screenRoomButton.setLayoutParams(params);
                            screenRoomButton.setBackground(ContextCompat.getDrawable(context, R.drawable.date_button_selector));
                            screenRoomButton.setTextColor(textColorStateList);
                            screenRoomButton.setAllCaps(false);
                            screenRoomButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                            screenRoomButton.setOnClickListener(v -> {

                                // Log the screen ID
                                Log.d("ScreenRoomClick", "Clicked Screen: " + screen.getScreenId());

                                // Reset previous button state if any
                                for (int j = 0; j < screenRoomsContainer.getChildCount(); j++) {
                                    screenRoomsContainer.getChildAt(j).setSelected(false);
                                }

                                // Select current button
                                screenRoomButton.setSelected(true);

                                // Store selected screen room
                                selectedScreenRoom = screenRoomButton.getText().toString();
                                selectedScreenId = screen.getScreenId();
                                // Populate showtimes
                                showtimesContainer.setVisibility(View.VISIBLE);
                                populateShowtimes(theater,movieId);
                            });

                            screenRoomsContainer.addView(screenRoomButton);
                        }
                    } else {
                        Toast.makeText(context, "Failed to fetch screen rooms", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ScreenResponse>> call, Throwable t) {
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



        private void populateShowtimes(Theater theater, Integer movieId) {
            showtimesContainer.removeAllViews();
            Context context = itemView.getContext();
            if (movieId == null || selectedScreenId == null) {
                Toast.makeText(context, "Movie ID or Screen ID not set", Toast.LENGTH_SHORT).show();
                return;
            }
            ColorStateList textColorStateList = ContextCompat.getColorStateList(context, R.color.button_text_selector);

            // Retrofit setup for the API call
            RetrofitService retrofitService = new RetrofitService(context);
            GetDateScheduleByScreenIdAPI dateScheduleByScreenIdAPI = retrofitService.getRetrofit().create(GetDateScheduleByScreenIdAPI.class);

            // Call the API to get schedules
            dateScheduleByScreenIdAPI.getAllSchedulesByMovieAndCinemaAndScreen(movieId, theater.getId(), selectedScreenId)
                    .enqueue(new Callback<List<ScheduleResponse>>() {
                        @Override
                        public void onResponse(Call<List<ScheduleResponse>> call, Response<List<ScheduleResponse>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<ScheduleResponse> schedules = response.body();

                                // Group schedules by date
                                Map<String, List<ScheduleResponse>> schedulesByDate = new LinkedHashMap<>();
                                for (ScheduleResponse schedule : schedules) {
                                    schedulesByDate
                                            .computeIfAbsent(schedule.getDate(), k -> new ArrayList<>())
                                            .add(schedule);
                                }

                                // Create layout for date buttons
                                FlexboxLayout dateLayout = new FlexboxLayout(context);
                                dateLayout.setLayoutParams(new FlexboxLayout.LayoutParams(
                                        FlexboxLayout.LayoutParams.MATCH_PARENT,
                                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                                ));
                                dateLayout.setFlexWrap(FlexWrap.WRAP);

                                // Create layout for time buttons
                                FlexboxLayout timeLayout = new FlexboxLayout(context);
                                timeLayout.setLayoutParams(new FlexboxLayout.LayoutParams(
                                        FlexboxLayout.LayoutParams.MATCH_PARENT,
                                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                                ));
                                timeLayout.setFlexWrap(FlexWrap.WRAP);

                                // Add date and time buttons
                                for (String date : schedulesByDate.keySet()) {
                                    Button dateButton = new Button(context);
                                    dateButton.setText(date);

                                    // Button appearance
                                    dateButton.setTextColor(textColorStateList);
                                    dateButton.setAllCaps(false);
                                    dateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                    dateButton.setBackground(ContextCompat.getDrawable(context, R.drawable.date_button_selector));

                                    // Set layout params
                                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    params.setMargins(8, 8, 8, 8);
                                    dateButton.setLayoutParams(params);

                                    // Handle date button click
                                    dateButton.setOnClickListener(v -> {
                                        // Reset previous date button states
                                        for (int j = 0; j < dateLayout.getChildCount(); j++) {
                                            dateLayout.getChildAt(j).setSelected(false);
                                        }
                                        // Set the clicked button as selected
                                        dateButton.setSelected(true);

                                        // Clear the time layout
                                        timeLayout.removeAllViews();

                                        // Populate time buttons for the selected date
                                        for (ScheduleResponse schedule : schedulesByDate.get(date)) {
                                            Button timeButton = new Button(context);
                                            timeButton.setText(schedule.getTime());

                                            // Time button appearance
                                            timeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.date_button_selector));
                                            timeButton.setTextColor(textColorStateList);
                                            timeButton.setAllCaps(false);
                                            timeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                                            // Set time button layout params
                                            FlexboxLayout.LayoutParams timeParams = new FlexboxLayout.LayoutParams(
                                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                                            );
                                            timeParams.setMargins(8, 8, 8, 8);
                                            timeButton.setLayoutParams(timeParams);

                                            // Handle time button click
                                            timeButton.setOnClickListener(v1 -> {
                                                // Reset previous time button states
                                                for (int k = 0; k < timeLayout.getChildCount(); k++) {
                                                    timeLayout.getChildAt(k).setSelected(false);
                                                }
                                                // Set the clicked button as selected
                                                timeButton.setSelected(true);

                                                // Pass selected scheduleId to the listener
                                                listener.onShowtimeClick(theater, schedule.getTime(), selectedScreenRoom);
                                            });

                                            timeLayout.addView(timeButton);
                                        }
                                    });

                                    // Add date button to date layout
                                    dateLayout.addView(dateButton);
                                }

                                // Add date layout and time layout to the showtimes container
                                showtimesContainer.addView(dateLayout);
                                showtimesContainer.addView(timeLayout);
                            } else {
                                // Show error message if fetching schedules fails
                                Toast.makeText(context, "Failed to fetch schedules", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ScheduleResponse>> call, Throwable t) {
                            // Show error message if the API call fails
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }






//        private void populateShowtimes(Theater theater) {
//            showtimesContainer.removeAllViews();
//            List<String> showtimes = new ArrayList<>();
//            List<Movie> movies = TheaterDataProvider.getMoviesForTheater(selectedDate, (Activity) itemView.getContext());
//
//            for (Movie movie : movies) {
//                if (movie.getTitle().equals(movieTitle)) {
//                    showtimes.addAll(movie.getShowtimes());
//                    break;
//                }
//            }
//
//            // Create showtime buttons with appropriate styling
//            Context context = itemView.getContext();
//            for (String time : showtimes) {
//                Button timeButton = new Button(context);
//                timeButton.setText(time);
//                timeButton.setOnClickListener(v -> {
//                    // Pass selected screen room to the listener
//                    listener.onShowtimeClick(theater, time, selectedScreenRoom);
//                });
//                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
//                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
//                        FlexboxLayout.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins(8, 8, 8, 8);
//                timeButton.setLayoutParams(params);
//
//                showtimesContainer.addView(timeButton);
//            }
//        }
    }
}
