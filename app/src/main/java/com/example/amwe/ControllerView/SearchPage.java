package com.example.amwe.ControllerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.R;
import com.example.amwe.Model.Item;

import java.util.ArrayList;

public class SearchPage extends Fragment {

    SearchView search;
    private static ListingAdapter listingAdapter;

    public SearchPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_page, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.RecycleView);
        createList(recyclerView);

        search = v.findViewById(R.id.searchBar);
        search.setOnQueryTextListener(searchListner());
        ImageButton sortButton = v.findViewById(R.id.imageButton);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_alphabetically:
                listingAdapter.getSort().sortAlphabetically();
                listingAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_price:
                listingAdapter.getSort().sortPrice();
                listingAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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