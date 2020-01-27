package com.example.dietervanonckelenmobile;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OverzichtUrenActivity extends AppCompatActivity {

    private static final String TAG = "OverzichtUren";
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;
    private ImageView icoon;
    private List<UurObject> urenList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();

        recyclerView = findViewById(R.id.my_recycler_view);
        icoon = findViewById(R.id.imageViewR);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(urenList, this);
        recyclerView.setAdapter(mAdapter);



        Log.d(TAG, "onCreate: overzicht activity rendered");

        db = FirebaseFirestore.getInstance();

        db.collection("uren").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot object : list) {
                                UurObject uur = object.toObject(UurObject.class);
                                urenList.add(uur);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}
