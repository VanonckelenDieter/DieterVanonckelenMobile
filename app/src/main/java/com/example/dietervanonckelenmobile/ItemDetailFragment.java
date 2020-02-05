package com.example.dietervanonckelenmobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    private static final String TAG = "itemDetailFragment";
    private UurObject uur;
    private TextView name, date, hours, lesson;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_item_detail, container, false);

        name = rootView.findViewById(R.id.nameR);
        date = rootView.findViewById(R.id.dateR);
        hours = rootView.findViewById(R.id.urenR);
        lesson = rootView.findViewById(R.id.lesR);

        uur = new UurObject();
        uur.setNaam(getArguments().getString("naam"));
        uur.setDatum(getArguments().getString("datum"));
        uur.setUren(getArguments().getString("uren"));
        uur.setLes(getArguments().getString("les"));

        if (uur != null) {
            name.setText(uur.getNaam());
            date.setText(uur.getDatum());
            hours.setText(uur.getUren());
            lesson.setText(uur.getLes());
            Log.i(TAG, "Data successfully retrieved");

        }
        return rootView;
    }
}
