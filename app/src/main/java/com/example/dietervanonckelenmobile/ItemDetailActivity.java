package com.example.dietervanonckelenmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";
    UurObject uur;
    TextView name, date, hours, lesson;
    String nameS, dateS, hoursS, lessonS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.i(TAG, "Items are created");

        uur = new UurObject();
        name = findViewById(R.id.nameR);
        date = findViewById(R.id.dateR);
        hours = findViewById(R.id.urenR);
        lesson = findViewById(R.id.lesR);
        Log.i(TAG, "Views found");


        nameS = getIntent().getStringExtra("naam");
        dateS = getIntent().getStringExtra("datum");
        hoursS = getIntent().getStringExtra("uren");
        lessonS = getIntent().getStringExtra("les");

        String title = nameS + " " + lessonS + " op " + dateS;
        setTitle(title);

        name.setText(nameS);
        date.setText(dateS);
        hours.setText(hoursS);
        lesson.setText(lessonS);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
