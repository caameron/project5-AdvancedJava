package edu.pdx.cs410j.caameron.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;


public class appointmentActivity extends AppCompatActivity {

    Button submit;
    EditText owner, descripion, start, end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        submit = findViewById(R.id.submitAppointment);
        owner = findViewById(R.id.ownerInput);
        descripion = findViewById(R.id.descriptionInput);
        start = findViewById(R.id.startTimeInput);
        end = findViewById(R.id.endTimeInput);

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("print", submit.getText().toString() + owner.getText().toString());

                    }
                    }
        );
    }

}
