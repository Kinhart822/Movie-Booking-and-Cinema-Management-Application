package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.ScheduleResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.ScreenResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetDateScheduleByScreenIdAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetScreenByCinemaIdAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;

public class TheaterShowtimesAdapter extends RecyclerView.Adapter<TheaterShowtimesAdapter.TheaterViewHolder> {

    private List<Theater> theaters = new ArrayList<>();
    private String selectedDate;
    private String movieTitle;
    private OnShowtimeClickListener listener;
    private SparseBooleanArray expandedStates = new SparseBooleanArray();
    private Integer selectedScreenId;
    private Integer selectedScheduleId;
    private Integer movieId;

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Theater theater,String date, String showtime, Integer screenId, String screenRoom, Integer scheduleId);
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
    public void setOnShowtimeClickListener(OnShowtimeClickListener listener) {
        this.listener = listener;
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
        private Integer selectedCinemaId;

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

                selectedCinemaId = theater.getId();

                // Animate arrow rotation
                arrowIcon.animate()
                        .rotation(newState ? 180f : 0f)
                        .setDuration(200)
                        .start();

                // Show/hide showtimes container
                if (newState) {
                    screenRoomsContainer.setVisibility(View.VISIBLE);
                    populateScreenRooms(theater,selectedCinemaId,movieId);
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
                @SuppressLint("SetTextI18n")
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

            RetrofitService retrofitService = new RetrofitService(context);
            GetDateScheduleByScreenIdAPI dateScheduleByScreenIdAPI = retrofitService.getRetrofit().create(GetDateScheduleByScreenIdAPI.class);

            dateScheduleByScreenIdAPI.getAllSchedulesByMovieAndCinemaAndScreen(movieId, theater.getId(), selectedScreenId)
                    .enqueue(new Callback<ScheduleResponse>() {
                        @Override
                        public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ScheduleResponse scheduleResponse = response.body();

                                List<String> dates = scheduleResponse.getDate();
                                List<String> times = scheduleResponse.getTime();
                                List<Integer> scheduleIds = scheduleResponse.getScheduleId();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                Date today = new Date();

                                Map<String, List<Pair<String, Integer>>> schedulesByDateAndTime = new TreeMap<>((d1, d2) -> {
                                    try {
                                        return sdf.parse(d1).compareTo(sdf.parse(d2));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        return 0;
                                    }
                                });

                                for (int i = 0; i < dates.size(); i++) {
                                    try {
                                        Date scheduleDate = sdf.parse(dates.get(i));
                                        if (scheduleDate != null && scheduleDate.after(today)) {
                                            String date = dates.get(i);
                                            String time = times.get(i);
                                            Integer scheduleId = scheduleIds.get(i);

                                            schedulesByDateAndTime.computeIfAbsent(date, k -> new ArrayList<>())
                                                    .add(new Pair<>(time, scheduleId));
                                        }
                                    } catch (ParseException e) {
                                        Toast.makeText(context, "Error parsing date: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
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

                                for (Map.Entry<String, List<Pair<String, Integer>>> entry : schedulesByDateAndTime.entrySet()) {
                                    String date = entry.getKey();
                                    List<Pair<String, Integer>> timesForDate = entry.getValue();

                                    Button dateButton = new Button(context);
                                    dateButton.setText(date);
                                    dateButton.setTextColor(textColorStateList);
                                    dateButton.setAllCaps(false);
                                    dateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                    dateButton.setBackground(ContextCompat.getDrawable(context, R.drawable.date_button_selector));

                                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    params.setMargins(8, 8, 8, 8);
                                    dateButton.setLayoutParams(params);

                                    dateButton.setOnClickListener(v -> {
                                        for (int j = 0; j < dateLayout.getChildCount(); j++) {
                                            dateLayout.getChildAt(j).setSelected(false);
                                        }
                                        dateButton.setSelected(true);

                                        timeLayout.removeAllViews();

                                        for (Pair<String, Integer> timePair : timesForDate) {
                                            String time = timePair.first;
                                            Integer scheduleId = timePair.second;

                                            Button timeButton = new Button(context);
                                            timeButton.setText(time);
                                            timeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.date_button_selector));
                                            timeButton.setTextColor(textColorStateList);
                                            timeButton.setAllCaps(false);
                                            timeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                                            FlexboxLayout.LayoutParams timeParams = new FlexboxLayout.LayoutParams(
                                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                                            );
                                            timeParams.setMargins(8, 8, 8, 8);
                                            timeButton.setLayoutParams(timeParams);

                                            timeButton.setOnClickListener(v1 -> {
                                                for (int k = 0; k < timeLayout.getChildCount(); k++) {
                                                    timeLayout.getChildAt(k).setSelected(false);
                                                }
                                                timeButton.setSelected(true);

                                                listener.onShowtimeClick(theater, date, time, selectedScreenId, selectedScreenRoom, scheduleId);
                                            });

                                            timeLayout.addView(timeButton);
                                        }
                                    });

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
                        public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                            // Show error message if the API call fails
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

