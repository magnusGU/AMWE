package com.example.amwe.ControllerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.R;
import com.example.amwe.Model.Item;

import java.util.ArrayList;

public class SearchPage extends Fragment {

    SearchView search;
    PopupMenu popupMenu;
    private static ListingAdapter listingAdapter;
    private boolean alphabetically = true;
    private boolean price = true;
    private boolean date = true;

    public SearchPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_page, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.RecycleView);
        createList(recyclerView);

        setHasOptionsMenu(true);

        search = v.findViewById(R.id.searchBar);
        search.setOnQueryTextListener(searchListner());
        ImageButton sortButton = v.findViewById(R.id.filter_sort);
        popupMenu = new PopupMenu(getContext(), sortButton);
        popupMenu.getMenuInflater().inflate(R.menu.sort_function, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(popupMenuListener());

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });

        //ImageButton filterSort =v.findViewById(R.id.filter_sort);
        //filterSort.setOnClickListener(openFilterSort());

        return v;

    }

    /**
     * As the name says, this function returns all popup menu names to starting format
     */
    private void resetAllPopupMenuNames() {
        popupMenu.getMenu().getItem(0).setTitle("Namn");
        popupMenu.getMenu().getItem(1).setTitle("Pris");
        popupMenu.getMenu().getItem(2).setTitle("Datum");
    }

    /**
     * Sorts the listings based on the menuitem clicked
     * @return a new menuListener which will handle the menu clicked in the popupMeny
     */
    private PopupMenu.OnMenuItemClickListener popupMenuListener() {
        return new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // All names are reset to remove direction, also booleans are reset when another
                // button is clicked, to make sure the first click always sort in ascending order
                resetAllPopupMenuNames();
                switch (menuItem.getItemId()) {
                    case R.id.sort_alphabetically:
                        price = true;
                        date = true;
                        if (alphabetically) {
                            listingAdapter.getSort().sortAlphabetically();
                            menuItem.setTitle("Namn ▼");
                            listingAdapter.notifyDataSetChanged();
                            alphabetically = false;
                        } else {
                            listingAdapter.getSort().sortAlphabeticallyReversed();
                            menuItem.setTitle("Namn ▲");
                            listingAdapter.notifyDataSetChanged();
                            alphabetically = true;
                        }
                        break;
                    case R.id.sort_price:
                        alphabetically = true;
                        date = true;
                        if (price) {
                            listingAdapter.getSort().sortPrice();
                            menuItem.setTitle("Pris ▼");
                            listingAdapter.notifyDataSetChanged();
                            price = false;
                        } else {
                            listingAdapter.getSort().sortPriceReversed();
                            menuItem.setTitle("Pris ▲");
                            listingAdapter.notifyDataSetChanged();
                            price = true;
                        }
                        break;
                    case R.id.sort_date:
                        alphabetically = true;
                        price = true;
                        if (date) {
                            listingAdapter.getSort().sortDate();
                            menuItem.setTitle("Datum ▼");
                            listingAdapter.notifyDataSetChanged();
                            date = false;
                        } else {
                            listingAdapter.getSort().sortDateReversed();
                            menuItem.setTitle("Datum ▲");
                            listingAdapter.notifyDataSetChanged();
                            date = true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        };
    }

  /*
    private View.OnClickListener openFilterSort() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FilterSort.class);
                startActivity(intent);
            }
        };
    }
    */


    /**
     * Searches among the listings based on the input to the search bar.
     *
     * @return A SearchView.OnQueryTextListener that should be applied to the search bar.
     */
    private SearchView.OnQueryTextListener searchListner() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { //for updating the search after filling in text completely and submitting it in search bar, not used since we want it updated in real time
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) { //updating search in real time as the user writes
                listingAdapter.getSearch().performFiltering(s);
                listingAdapter.notifyDataSetChanged();
                return false;
            }
        };
    }

    /**
     * Populates the SearchPage with listings.
     *
     * @param recyclerView The RecyclerView that should be populated.
     */
    private void createList(RecyclerView recyclerView) {
        ArrayList<Item> currentListings = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //sketchy but we will have to discuss this
        ListingAdapter adapter = new ListingAdapter(currentListings, "currentListings");
        listingAdapter = adapter;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}