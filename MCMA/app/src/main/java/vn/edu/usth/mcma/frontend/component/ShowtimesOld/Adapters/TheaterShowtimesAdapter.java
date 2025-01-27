package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.ScreenResponse;
import vn.edu.usth.mcma.frontend.dto.response.Schedule;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Theater;

public class TheaterShowtimesAdapter extends RecyclerView.Adapter<TheaterShowtimesAdapter.TheaterViewHolder> {

    private List<Theater> theaters = new ArrayList<>();
    private final OnShowtimeClickListener listener;
    private final SparseBooleanArray expandedStates = new SparseBooleanArray();
    private Long selectedScreenId;
    private final Long movieId;

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Theater theater, String date, String showtime, Long screenId, String screenRoom, Long scheduleId);
    }

    public TheaterShowtimesAdapter(OnShowtimeClickListener listener, Long movieId) {
        this.listener = listener;
        this.movieId = movieId; // Initialize movieId
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
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

    public class TheaterViewHolder extends RecyclerView.ViewHolder {
        private final TextView theaterName;
        private final TextView theaterAddress;
        private final FlexboxLayout showtimesContainer;
        private final FlexboxLayout screenRoomsContainer;
        private final ImageView arrowIcon;
        private final View divider;
        private final ConstraintLayout headerLayout;
        private String selectedScreenRoom;
        private Long selectedCinemaId;

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

        void bind(Theater theater, int position, Long movieId) {
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
        private void populateScreenRooms(Theater theater, Long cinemaId, Long movieId) {
            screenRoomsContainer.removeAllViews();

            Context context = itemView.getContext();
            ColorStateList textColorStateList = ContextCompat.getColorStateList(context, R.color.button_text_selector);

            ApiService
                    .getCinemaApi(context)
                    .getScreenByCinemaId(cinemaId).enqueue(new Callback<>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<List<ScreenResponse>> call, @NonNull Response<List<ScreenResponse>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<ScreenResponse> screens = response.body();

                                for (ScreenResponse screen : screens) {
                                    Button screenRoomButton = new Button(context);
                                    screenRoomButton.setText(screen.getName());

                                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    params.setMargins(8, 8, 8, 8);
                                    screenRoomButton.setLayoutParams(params);
                                    screenRoomButton.setBackground(ContextCompat.getDrawable(context, R.drawable.button_selector_showtime));
                                    screenRoomButton.setTextColor(textColorStateList);
                                    screenRoomButton.setAllCaps(false);
                                    screenRoomButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                                    screenRoomButton.setOnClickListener(v -> {

                                        // Log the screen ID
                                        Log.d("ScreenRoomClick", "Clicked Screen: " + screen.getId());

                                        // Reset previous button state if any
                                        for (int j = 0; j < screenRoomsContainer.getChildCount(); j++) {
                                            screenRoomsContainer.getChildAt(j).setSelected(false);
                                        }

                                        // Select current button
                                        screenRoomButton.setSelected(true);

                                        // Store selected screen room
                                        selectedScreenRoom = screenRoomButton.getText().toString();
                                        selectedScreenId = screen.getId();
                                        // Populate showtimes
                                        showtimesContainer.setVisibility(View.VISIBLE);
                                        populateShowtimes(theater, movieId);
                                    });

                                    screenRoomsContainer.addView(screenRoomButton);
                                }
                            } else {
                                Toast.makeText(context, "Failed to fetch screen rooms", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<ScreenResponse>> call, @NonNull Throwable t) {
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }


        private void populateShowtimes(Theater theater, Long movieId) {
            showtimesContainer.removeAllViews();
            Context context = itemView.getContext();
            if (movieId == null || selectedScreenId == null) {
                Toast.makeText(context, "Movie ID or Screen ID not set", Toast.LENGTH_SHORT).show();
                return;
            }
            ColorStateList textColorStateList = ContextCompat.getColorStateList(context, R.color.button_text_selector);

            ApiService
                    .getMovieApi(context)
                    .getAllSchedulesByMovieAndScreen(movieId, selectedScreenId)
                    .enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Schedule>> call, @NonNull Response<List<Schedule>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Schedule> schedules = response.body();

                                List<String> dates = schedules
                                        .stream()
                                        .map(s -> s.getStartTime().substring(0, 10))
                                        .collect(Collectors.toList());
                                List<String> times = schedules
                                        .stream()
                                        .map(s -> s.getStartTime().substring(11, 16))
                                        .collect(Collectors.toList());
                                List<Long> scheduleIds = schedules
                                        .stream()
                                        .map(Schedule::getId)
                                        .collect(Collectors.toList());

                                //todo dd/MM/yyyy
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date today;
                                try {
                                    today = sdf.parse(sdf.format(new Date()));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }

                                Map<String, List<Pair<String, Long>>> schedulesByDateAndTime = new TreeMap<>((d1, d2) -> {
                                    try {
                                        return Objects.requireNonNull(sdf.parse(d1)).compareTo(sdf.parse(d2));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        return 0;
                                    }
                                });

                                for (int i = 0; i < dates.size(); i++) {
                                    try {
                                        Date scheduleDate = sdf.parse(dates.get(i));

                                        if (scheduleDate != null && (scheduleDate.equals(today) || scheduleDate.after(today))) {
                                            String date = dates.get(i);
                                            String time = times.get(i);
                                            Long scheduleId = scheduleIds.get(i);

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

                                for (Map.Entry<String, List<Pair<String, Long>>> entry : schedulesByDateAndTime.entrySet()) {
                                    String date = entry.getKey();
                                    List<Pair<String, Long>> timesForDate = entry.getValue();

                                    Button dateButton = new Button(context);
                                    dateButton.setText(date);
                                    dateButton.setTextColor(textColorStateList);
                                    dateButton.setAllCaps(false);
                                    dateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                    dateButton.setBackground(ContextCompat.getDrawable(context, R.drawable.button_selector_showtime));

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

                                        for (Pair<String, Long> timePair : timesForDate) {
                                            String time = timePair.first;
                                            Long scheduleId = timePair.second;

                                            Button timeButton = new Button(context);
                                            timeButton.setText(time);
                                            timeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.button_selector_showtime));
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
                                                String dateTimeStr = dateButton.getText() + " " + time;
                                                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                                Date selectedDateTime;
                                                try {
                                                    selectedDateTime = dateTimeFormat.parse(dateTimeStr);
                                                } catch (ParseException e) {
                                                    Toast.makeText(context, "Error parsing date and time: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(selectedDateTime);
                                                calendar.add(Calendar.MINUTE, 10);
                                                Date selectedDateTimePlus10Minutes = calendar.getTime();
                                                Date currentTime = new Date();
                                                if (selectedDateTimePlus10Minutes.before(currentTime)) {
                                                    Toast.makeText(context, "The schedule is over", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Proceed with the listener action if the time is in the future
                                                    for (int k = 0; k < timeLayout.getChildCount(); k++) {
                                                        timeLayout.getChildAt(k).setSelected(false);
                                                    }
                                                    timeButton.setSelected(true);
                                                    listener.onShowtimeClick(theater, date, time, selectedScreenId, selectedScreenRoom, scheduleId);
                                                }
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
                        public void onFailure(@NonNull Call<List<Schedule>> call, @NonNull Throwable t) {
                            // Show error message if the API call fails
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

