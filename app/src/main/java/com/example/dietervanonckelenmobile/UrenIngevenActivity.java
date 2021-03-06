package com.example.dietervanonckelenmobile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class UrenIngevenActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private EditText naam;
    private EditText les;
    private EditText uren;
    private EditText datum;
    private Button indienen;
    private String parsedNaam;
    private String parsedLes;
    private String parsedUren;
    private String parsedDatum;
    private String userId;
    public int notifyId = 1;
    public String channelId = "some_channel_id";
    private static final String TAG = "Logging write to db";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ureningeven);
        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db = FirebaseFirestore.getInstance();

        naam = findViewById(R.id.naam);
        les = findViewById(R.id.les);
        uren = findViewById(R.id.uren);
        datum = findViewById(R.id.datum);
        indienen = findViewById(R.id.indienen);
        Log.d(TAG, "onCreate: ingeven activity rendered");


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "HoursId";
        CharSequence channelName = "HoursChannel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);

        indienen.setOnClickListener(v -> {
            parsedDatum = datum.getText().toString();
            parsedLes = les.getText().toString();
            parsedNaam = naam.getText().toString();
            parsedUren = uren.getText().toString();
            if (v == indienen) {
                urenIngeven(parsedNaam, parsedLes, parsedUren, parsedDatum);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void urenIngeven(String naam, String les, String uren, String datum) {
        CollectionReference dbUren = db.collection("uren");
        final UurObject object = new UurObject(naam, les, uren, datum);
        dbUren.add(object).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "urenIngeven: succes ");
            Toast.makeText(UrenIngevenActivity.this, "De uren zijn opgeslagen", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UrenIngevenActivity.this, ProfileActivity.class);
            UrenIngevenActivity.this.startActivity(intent);


            Intent notifyIntent = new Intent(UrenIngevenActivity.this, ItemListActivity.class);
            // Just a random request code /demonstration
            int uniqueInt = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getActivity(UrenIngevenActivity.this, uniqueInt, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = new Notification.Builder(UrenIngevenActivity.this)
                    .setContentTitle("Workhours")
                    .setContentText("Hours have been added!")
                    .setSmallIcon(R.drawable.ic_verified)
                    .setChannelId(channelId)
                    .setContentIntent(pendingIntent)
                    .build();

            notificationManager.notify(notifyId, notification);
        }).addOnFailureListener(e -> Log.e(TAG, "Received an exception after trying to write data to db :  " + e.getMessage()));
    }
}



