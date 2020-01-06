package com.example.dietervanonckelenmobile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OverzichtUrenActivity extends AppCompatActivity {

    private static final String TAG = "OverzichtUren";
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private ListView dbObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzichturen);

        dbObject = findViewById(R.id.urenOverzicht);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
           /*     Map<UurObject, String> map = (Map<UurObject, String>) dataSnapshot.getValue();
                dbObject.setText(map.toString());
                System.out.println(map);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            
        }
    }
}
