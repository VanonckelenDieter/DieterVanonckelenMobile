package com.example.dietervanonckelenmobile;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class LessenRoosterActivity extends AppCompatActivity {
    List<EventDay> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessenrooster);

        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        CalendarView calendarView = findViewById(R.id.calenderView);
        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                events.add(new EventDay(clickedDayCalendar, R.drawable.ic_verified));
                calendarView.setEvents(events);
            }
        });
    }
}
