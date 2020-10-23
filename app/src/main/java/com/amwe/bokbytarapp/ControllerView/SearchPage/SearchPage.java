package com.amwe.bokbytarapp.ControllerView.SearchPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amwe.bokbytarapp.Model.Items.Item;
import com.amwe.bokbytarapp.R;

import java.util.ArrayList;

/**
 * This page handles the SearchPage where all the listings are presented. The listings can be
 * searched through and/or sorted in desired order. 
 *
 * Related to {@link com.amwe.bokbytarapp.R.layout#fragment_search_page}.
 *
 * @author Ali Alladin, Magnus Andersson,Elias Johansson
 */
public class SearchPage extends Fragment {

    private SearchView search;
    private PopupMenu popupMenu;
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
        search.setOnQueryTextListener(searchListener());
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
     *
     * @return a new menuListener which will handle the menu clicked in the popupMeny
     */
    private PopupMenu.OnMenuItemClickListener popupMenuListener() {
        return new PopupMenu.OnMenuItemClickListener() {
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
                            listingAdapter.sortMethod(1).sort();
                            menuItem.setTitle("Namn ▼");
                            listingAdapter.notifyDataSetChanged();
                            alphabetically = false;
                        } else {
                            listingAdapter.sortMethod(1).sortReversed();
                            menuItem.setTitle("Namn ▲");
                            listingAdapter.notifyDataSetChanged();
                            alphabetically = true;
                        }
                        break;
                    case R.id.sort_price:
                        alphabetically = true;
                        date = true;
                        if (price) {
                            listingAdapter.sortMethod(2).sort();
                            menuItem.setTitle("Pris ▼");
                            listingAdapter.notifyDataSetChanged();
                            price = false;
                        } else {
                            listingAdapter.sortMethod(2).sortReversed();
                            menuItem.setTitle("Pris ▲");
                            listingAdapter.notifyDataSetChanged();
                            price = true;
                        }
                        break;
                    case R.id.sort_date:
                        alphabetically = true;
                        price = true;
                        if (date) {
                            listingAdapter.sortMethod(3).sortReversed();
                            menuItem.setTitle("Datum ▲");
                            listingAdapter.notifyDataSetChanged();
                            date = true;
                        } else {
                            listingAdapter.sortMethod(3).sort();
                            menuItem.setTitle("Datum ▼");
                            listingAdapter.notifyDataSetChanged();
                            date = false;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        };
    }

    /**
     * Searches among the listings based on the input to the search bar.
     *
     * @return A SearchView.OnQueryTextListener that should be applied to the search bar.
     */
    private SearchView.OnQueryTextListener searchListener() {
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