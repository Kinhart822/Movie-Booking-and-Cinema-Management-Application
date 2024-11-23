package vn.edu.usth.mcma.frontend.Search;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class Search_Activity extends AppCompatActivity implements SearchViewInterface {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Search_Adapter adapter;
    private List<Search_Item> items;
    private List<Search_Item> filteredItems;
    private List<View> buttons; // Danh sách lưu các button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.recyclersearch_activity);

        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
        buttons = new ArrayList<>(); // Khởi tạo danh sách button

        items.add(new Search_Item("Olivia Adams", "Horror", "135 minutes", "T16", R.drawable.movie12));
        items.add(new Search_Item("Liam Johnson", "Action", "120 minutes", "T18", R.drawable.movie6));
        items.add(new Search_Item("Noah Brown", "Horror", "90 minutes", "P", R.drawable.movie8));
        items.add(new Search_Item("Grace Morgan", "Horror", "85 minutes", "P", R.drawable.movie1));
        items.add(new Search_Item("Isabella Lewis", "Comedy", "100 minutes", "P", R.drawable.movie3));
        items.add(new Search_Item("Evelyn", "Sci-Fi", "94 minutes", "T13", R.drawable.movie4));
        items.add(new Search_Item("Jack", "Action", "114 minutes", "P", R.drawable.movie5));
        items.add(new Search_Item("Tino", "Horror", "125 minutes", "P", R.drawable.movie7));

        filteredItems.addAll(items);

        adapter = new Search_Adapter(this, filteredItems, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        setupCategoryButton(findViewById(R.id.btn_all), "All");
        setupCategoryButton(findViewById(R.id.btn_action), "Action");
        setupCategoryButton(findViewById(R.id.btn_adventure), "Adventure");
        setupCategoryButton(findViewById(R.id.btn_comedy), "Comedy");
        setupCategoryButton(findViewById(R.id.btn_horror), "Horror");
        setupCategoryButton(findViewById(R.id.btn_drama), "Drama");
    }

    private void setupCategoryButton(View button, String category) {
        buttons.add(button);
        button.setOnClickListener(v -> {
            resetButtonColors();
            button.setBackgroundColor(Color.LTGRAY);
            filterByCategory(category);
        });
    }

    private void resetButtonColors() {
        for (View button : buttons) {
            button.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void filterByCategory(String category) {
        filteredItems.clear();

        if (category.equalsIgnoreCase("All")) {
            filteredItems.addAll(items);
        } else {
            for (Search_Item item : items) {
                if (item.getCategory().equalsIgnoreCase(category)) {
                    filteredItems.add(item);
                }
            }

            if (filteredItems.isEmpty()) {
                Toast.makeText(this, "No films found in category: " + category, Toast.LENGTH_SHORT).show();
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterList(String text) {
        filteredItems.clear();
        for (Search_Item item : items) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Search_Item clickedItem = filteredItems.get(position);
        Toast.makeText(this, "Film: " + clickedItem.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
