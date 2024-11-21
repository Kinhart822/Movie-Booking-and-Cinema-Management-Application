package vn.edu.usth.mcma.frontend.Search;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.recyclersearch_activity);

        items = new ArrayList<>();
        filteredItems = new ArrayList<>();

        items.add(new Search_Item("Olivia Adams", "Horror", "135 minutes", "T16", R.drawable.movie12));
        items.add(new Search_Item("Liam Johnson", "Action", "120 minutes", "T18", R.drawable.movie6));
        items.add(new Search_Item("Noah Brown", "Horror", "90 minutes", "P", R.drawable.movie8));

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
        // Handle item click
        Search_Item clickedItem = filteredItems.get(position);
        Toast.makeText(this, "Film: " + clickedItem.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
