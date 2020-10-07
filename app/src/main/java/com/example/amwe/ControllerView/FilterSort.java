package com.example.amwe.ControllerView;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.amwe.Model.SortFunction;
import com.example.amwe.R;

public class FilterSort extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_sort);

        Button sortButton = findViewById(R.id.sort_button);

        sortButton.setOnClickListener(showMenu(sortButton));
    }

    private View.OnClickListener showMenu(final Button sortButton) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), sortButton);
                popupMenu.getMenuInflater().inflate(R.menu.sort_function, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.sort_alphabetically:
                                /*
                                listingAdapter.getSort().sortAlphabetically();
                                listingAdapter.notifyDataSetChanged();
                                 */
                                sortButton.setText("Namn");
                                return true;
                            case R.id.sort_price:
                                /*
                                listingAdapter.getSort().sortPrice();
                                listingAdapter.notifyDataSetChanged();
                                 */
                                sortButton.setText("Pris");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        };
    }

}