package com.example.amwe.ControllerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.amwe.R;
import com.example.amwe.model.Item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SearchView search;

    private static ListingAdapter listingAdapter;

    public SearchPage() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchPage.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchPage newInstance(String param1, String param2) {
        SearchPage fragment = new SearchPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

       return v;
    }

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


    private void createList(RecyclerView recyclerView){
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