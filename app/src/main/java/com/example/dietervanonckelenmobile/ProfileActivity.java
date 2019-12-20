package com.example.dietervanonckelenmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView Email;
    private TextView Uid;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Email = (TextView) findViewById(R.id.profileEmail);
        Uid = (TextView) findViewById(R.id.profileUid);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(R.id.Logout);
        user = mAuth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            String uid = user.getUid();
            Email.setText(email);
            Uid.setText(uid);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.Lessenrooster:
                Intent intent = new Intent(this, LessenRoosterActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.Ureningeven:
                Intent intent2 = new Intent(this, UrenIngevenActivity.class);
                this.startActivity(intent2);
                return true;
            case R.id.Overzicht:
                Intent intent3 = new Intent(this, OverzichtUrenActivity.class);
                this.startActivity(intent3);
                return true;
            case R.id.Logout:
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v == logout) {
                            if (user != null) {
                                mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        }
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}