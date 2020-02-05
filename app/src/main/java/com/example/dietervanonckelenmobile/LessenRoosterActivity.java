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

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.ic_verified));
        CalendarView calendarView = findViewById(R.id.calenderView);



        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                events.add(new EventDay(clickedDayCalendar, R.drawable.ic_launcher_foreground));
                CalendarView calendarView = findViewById(R.id.calenderView);
                calendarView.setEvents(events);
            }
        });
    }
}
