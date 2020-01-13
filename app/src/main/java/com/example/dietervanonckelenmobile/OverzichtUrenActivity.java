package com.example.dietervanonckelenmobile;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OverzichtUrenActivity extends AppCompatActivity {

    private static final String TAG = "OverzichtUren";
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(this);


        Log.d(TAG, "onCreate: overzicht activity rendered");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<UurObject> objects = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    objects.add(postSnapshot.getValue(UurObject.class));
                }
                String[] array = new String[objects.size()];
                int index = 0;
                for (UurObject value : objects) {
                    array[index] = String.valueOf(value);
                }

                mAdapter = new MyAdapter(array);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);
                Log.v(TAG, "TEST");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
