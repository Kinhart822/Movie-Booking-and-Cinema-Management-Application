package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.filter.TimeButton;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.model.ShowtimeOfMovieBySchedule;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {
    private final Context context;
    private final Long movieId;
    private final ITimeButton iTimeButton;
    private final Map<String, Map<String, List<ShowtimeOfMovieBySchedule>>> cinemaNameScreenTypeScheduleMap;
    private final List<String> cinemaNames;

    public CinemaAdapter(Context context, Long movieId, ITimeButton iTimeButton) {
        this.context = context;
        this.movieId = movieId;
        this.iTimeButton = iTimeButton;
        this.cinemaNameScreenTypeScheduleMap = new HashMap<>();
        this.cinemaNames = new ArrayList<>();
    }

    public void updateData(Map<String, Map<String, List<ShowtimeOfMovieBySchedule>>> newData) {
        this.cinemaNameScreenTypeScheduleMap.clear();
        this.cinemaNames.clear();
        if (newData != null) {
            this.cinemaNameScreenTypeScheduleMap.putAll(newData);
            this.cinemaNames.addAll(newData.keySet());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_movie_booking_cinema, parent, false));
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.expandCollapseButton.setBackground(null);
        holder.expandCollapseButton.setOnClickListener(null);
        holder.showtimeScreenTypeLinearLayout.setVisibility(View.VISIBLE);
        holder.showtimeScreenTypeLinearLayout.removeAllViews();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cinemaName = cinemaNames.get(position);
        Map<String, List<ShowtimeOfMovieBySchedule>> screenTypeScheduleMap = cinemaNameScreenTypeScheduleMap.get(cinemaName);
        if (screenTypeScheduleMap == null) {
            System.out.println("screenTypeScheduleMap is null");
            return;
        }
        holder.nameTextView.setText(cinemaName);
        holder.expandCollapseButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_down_gray));
        holder.showtimeScreenTypeLinearLayout.setVisibility(View.GONE);
        View.OnClickListener expandCollapseListener = v -> {
            if (holder.showtimeScreenTypeLinearLayout.getVisibility() == View.GONE) {
                holder.expandCollapseButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_up_gray));
                holder.showtimeScreenTypeLinearLayout.setVisibility(View.VISIBLE);
            } else if (holder.showtimeScreenTypeLinearLayout.getVisibility() == View.VISIBLE) {
                holder.expandCollapseButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_down_gray));
                holder.showtimeScreenTypeLinearLayout.setVisibility(View.GONE);
            }
        };
        holder.nameRelativeLayout.setOnClickListener(expandCollapseListener);
        holder.expandCollapseButton.setOnClickListener(expandCollapseListener);
        screenTypeScheduleMap
                .forEach((screenType, scheduleList) -> {
                    LinearLayout screenTypeLinearLayout = new LinearLayout(context);
                    screenTypeLinearLayout.setOrientation(LinearLayout.VERTICAL);
                    TextView screenTypeTextView = new TextView(context);
                    screenTypeTextView.setText(screenType);
                    screenTypeLinearLayout.addView(screenTypeTextView);
                    HorizontalScrollView scheduleTimeHorizontalScrollView = new HorizontalScrollView(context);
                    scheduleTimeHorizontalScrollView.setHorizontalScrollBarEnabled(false);
                    LinearLayout scheduleTimeLinearLayout = new LinearLayout(context);
                    scheduleTimeLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    scheduleList
                            .forEach((schedule) -> {
                                TimeButton showtimeTimeButton = new TimeButton(context);
                                showtimeTimeButton.setText(schedule.getTime().toString());
                                showtimeTimeButton.setOnClickListener(v -> iTimeButton.onClickListener(schedule.getScheduleId(), bookingId -> {
                                    //todo: time dialog

                                    Intent intent = new Intent(context, BookingSeatSelectionActivity.class);
                                    intent.putExtra(
                                            IntentKey.BOOKING.name(),
                                            Booking.builder()
                                                    .bookingId(bookingId)
                                                    .movieId(movieId)
                                                    .scheduleId(schedule.getScheduleId()).build());
                                    context.startActivity(intent);
                                }));
                                scheduleTimeLinearLayout.addView(showtimeTimeButton);
                            });
                    scheduleTimeHorizontalScrollView.addView(scheduleTimeLinearLayout);
                    screenTypeLinearLayout.addView(scheduleTimeHorizontalScrollView);
                    holder.showtimeScreenTypeLinearLayout.addView(screenTypeLinearLayout);
                });
    }

    @Override
    public int getItemCount() {
        return cinemaNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout nameRelativeLayout;
        TextView nameTextView;
        ImageButton expandCollapseButton;
        LinearLayout showtimeScreenTypeLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameRelativeLayout = itemView.findViewById(R.id.name);
            nameTextView = itemView.findViewById(R.id.text_view_name);
            expandCollapseButton = itemView.findViewById(R.id.image_button_expand_collapse);
            showtimeScreenTypeLinearLayout = itemView.findViewById(R.id.linear_layout_showtime_screen_type);
        }
    }
}
