package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;

public class QuantityAndTypeTicketDialog extends Dialog {
    private ImageButton btnClose;
    private Button btnContinue;
    private EditText etGuestQuantity;
    private RecyclerView recyclerViewTicketTypes;
    private TicketTypeAdapter ticketTypeAdapter;
    private int guestQuantity = 0;
    private OnDialogActionListener listener;

    public interface OnDialogActionListener {
        void onContinueClicked(List<TicketTypeItem> selectedTickets);
        void onCloseClicked();
    }

    public QuantityAndTypeTicketDialog(@NonNull Context context, OnDialogActionListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_quantity_ticket_layout);
        setupDialog();
        initializeViews();
        setupGuestQuantityInput();
        setupTicketTypeRecyclerView();
        setupContinueButton();
    }

    private void setupDialog() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setDimAmount(0.5f);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(false);
    }

    private void initializeViews() {
        btnClose = findViewById(R.id.btnClose);
        btnContinue = findViewById(R.id.btnContinue);
        etGuestQuantity = findViewById(R.id.etGuestQuantity);
        recyclerViewTicketTypes = findViewById(R.id.recyclerViewTicketTypes);

        btnClose.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCloseClicked();
            }
            dismiss();
        });
    }

    private void setupGuestQuantityInput() {
        etGuestQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    guestQuantity = Integer.parseInt(s.toString());
                    if (guestQuantity <= 0) {
                        Toast.makeText(getContext(), "Please enter quantity >= 1", Toast.LENGTH_SHORT).show();
                        recyclerViewTicketTypes.setVisibility(View.GONE);
                    } else {
                        recyclerViewTicketTypes.setVisibility(View.VISIBLE);
                        ticketTypeAdapter.setGuestQuantity(guestQuantity);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Please enter the integer form", Toast.LENGTH_SHORT).show();
                    recyclerViewTicketTypes.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupTicketTypeRecyclerView() {
        List<TicketTypeItem> ticketTypes = createTicketTypes();
        ticketTypeAdapter = new TicketTypeAdapter(ticketTypes, guestQuantity);
        recyclerViewTicketTypes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTicketTypes.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewTicketTypes.setAdapter(ticketTypeAdapter);
    }

    private List<TicketTypeItem> createTicketTypes() {
        List<TicketTypeItem> ticketTypes = new ArrayList<>();
        ticketTypes.add(new TicketTypeItem("Kid", 0));
        ticketTypes.add(new TicketTypeItem("Adult", 0));
        ticketTypes.add(new TicketTypeItem("Student", 0));
        return ticketTypes;
    }

    private void setupContinueButton() {
        btnContinue.setOnClickListener(v -> {
            List<TicketTypeItem> selectedTickets = ticketTypeAdapter.getSelectedTickets();
            int totalSelectedQuantity = selectedTickets.stream()
                    .mapToInt(TicketTypeItem::getQuantity)
                    .sum();

            if (validateTicketSelection(totalSelectedQuantity)) {
                if (listener != null) {
                    listener.onContinueClicked(selectedTickets);
                }
                dismiss();
            } else {
                Toast.makeText(getContext(), "Please order the correct number of seats", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateTicketSelection(int totalSelectedQuantity) {
        return totalSelectedQuantity == guestQuantity;
    }

    public static class TicketTypeItem {
        private String type;
        private int quantity;
        private boolean isSelected;

        public TicketTypeItem(String type, int quantity) {
            this.type = type;
            this.quantity = quantity;
            this.isSelected = false;
        }

        // Getters and setters
        public String getType() { return type; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public boolean isSelected() { return isSelected; }
        public void setSelected(boolean selected) { isSelected = selected; }
    }

    private class TicketTypeAdapter extends RecyclerView.Adapter<TicketTypeAdapter.TicketTypeViewHolder> {
        private List<TicketTypeItem> ticketTypes;
        private int guestQuantity;

        public TicketTypeAdapter(List<TicketTypeItem> ticketTypes, int guestQuantity) {
            this.ticketTypes = ticketTypes;
            this.guestQuantity = guestQuantity;
        }

        public void setGuestQuantity(int guestQuantity) {
            this.guestQuantity = guestQuantity;
            notifyDataSetChanged();
        }

        public List<TicketTypeItem> getSelectedTickets() {
            return ticketTypes.stream()
                    .filter(TicketTypeItem::isSelected)
                    .collect(Collectors.toList());
        }

        @NonNull
        @Override
        public TicketTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ticket_type, parent, false);
            return new TicketTypeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TicketTypeViewHolder holder, int position) {
            TicketTypeItem ticketType = ticketTypes.get(position);
            holder.bind(ticketType, position);
        }

        @Override
        public int getItemCount() {
            return ticketTypes.size();
        }

        class TicketTypeViewHolder extends RecyclerView.ViewHolder {
            private View circleSelector;
            private TextView tvTicketType;
            private EditText etTicketQuantity;

            public TicketTypeViewHolder(@NonNull View itemView) {
                super(itemView);
                circleSelector = itemView.findViewById(R.id.circleSelector);
                tvTicketType = itemView.findViewById(R.id.tvTicketType);
                etTicketQuantity = itemView.findViewById(R.id.etTicketQuantity);
            }

            public void bind(TicketTypeItem ticketType, int position) {
                tvTicketType.setText(ticketType.getType());
                etTicketQuantity.setVisibility(View.GONE);
                circleSelector.setBackgroundResource(R.drawable.circle_selector_default);

                // Manage selection logic based on guest quantity
                circleSelector.setOnClickListener(v -> {
                    int selectedCount = (int) ticketTypes.stream()
                            .filter(TicketTypeItem::isSelected)
                            .count();

                    if (!ticketType.isSelected()) {
                        if ((guestQuantity == 1 && selectedCount == 0) ||
                                (guestQuantity == 2 && selectedCount < 2) ||
                                (guestQuantity >= 3)) {
                            ticketType.setSelected(true);
                            circleSelector.setBackgroundResource(R.drawable.circle_selector_selected);
                            etTicketQuantity.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ticketType.setSelected(false);
                        circleSelector.setBackgroundResource(R.drawable.circle_selector_default);
                        etTicketQuantity.setVisibility(View.GONE);
                        etTicketQuantity.setText("");
                        ticketType.setQuantity(0);
                    }
                });

                // Quantity input with validation
                etTicketQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            int quantity = Integer.parseInt(s.toString());
                            if (ticketType.isSelected()) {
                                ticketType.setQuantity(quantity);
                                handleQuantityLogic(position);
                            }
                        } catch (NumberFormatException e) {
                            // Handle invalid input
                        }
                    }
                });
            }

            private void handleQuantityLogic(int currentPosition) {
                int selectedCount = (int) ticketTypes.stream()
                        .filter(TicketTypeItem::isSelected)
                        .count();

                int totalQuantity = ticketTypes.stream()
                        .filter(TicketTypeItem::isSelected)
                        .mapToInt(TicketTypeItem::getQuantity)
                        .sum();

                if (guestQuantity == 1 && totalQuantity > 1) {
                    // Reset other selections
                    for (int i = 0; i < ticketTypes.size(); i++) {
                        if (i != currentPosition) {
                            ticketTypes.get(i).setSelected(false);
                            ticketTypes.get(i).setQuantity(0);
                            notifyItemChanged(i);
                        }
                    }
                } else if (guestQuantity == 2 && totalQuantity > 2) {
                    // If total quantity exceeds 2, reset other selections
                    for (int i = 0; i < ticketTypes.size(); i++) {
                        if (i != currentPosition) {
                            ticketTypes.get(i).setSelected(false);
                            ticketTypes.get(i).setQuantity(0);
                            notifyItemChanged(i);
                        }
                    }
                } else if (guestQuantity >= 3 && totalQuantity > guestQuantity) {
                    // If total quantity exceeds guest quantity, reset other selections
                    for (int i = 0; i < ticketTypes.size(); i++) {
                        if (i != currentPosition) {
                            ticketTypes.get(i).setSelected(false);
                            ticketTypes.get(i).setQuantity(0);
                            notifyItemChanged(i);
                        }
                    }
                }
            }
        }
    }
    public static class OnDialogActionListenerAdapter implements OnDialogActionListener {
        @Override
        public void onContinueClicked(List<TicketTypeItem> selectedTickets) {
            // Default implementation (can be overridden)
        }

        @Override
        public void onCloseClicked() {
            // Default implementation (can be overridden)
        }
    }
}
