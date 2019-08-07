package edu.pdx.cs410j.caameron.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class searchAppointment extends AppCompatActivity {

    Button submit;
    EditText startDate, endDate;
    TextView printArea;
    private ArrayList<AppointmentBook<Appointment>> book = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);

        submit = findViewById(R.id.submitSearch);
        startDate = findViewById(R.id.startDateSearch);
        endDate = findViewById(R.id.endDateSearch);
        printArea = findViewById(R.id.searchPrintArea);

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );
    }
}
