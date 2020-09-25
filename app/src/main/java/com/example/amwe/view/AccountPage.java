package com.example.amwe.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amwe.R;
import com.example.amwe.model.Database;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public AccountPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountPage.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountPage newInstance(String param1, String param2) {
        AccountPage fragment = new AccountPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View v= inflater.inflate(R.layout.fragment_account_page, container, false);
        Button logOutButton = v.findViewById(R.id.account_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        Button addListing = v.findViewById(R.id.account_page_add_listing_button);
        addListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddListing.class));
            }
        });

        initUI(v);

        return v;
        }

    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), Login.class));
    }
    private void initUI(View v){
        Database db = new Database();
        TextView name =v.findViewById(R.id.account_page_name);
        ImageView profilePicture = v.findViewById(R.id.account_page_profile_picture);
        //Obviously temporary but works now as a test.
        name.setText(db.getName());

    }
}