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
                        Appointment appt = null;
                        try
                        {
                            String beginTime = start.getText().toString();
                            String endTime = end.getText().toString();
                            String desciptionAppt = descripion.getText().toString();
                            String ownerAppt = owner.getText().toString();
                            Log.v("Print", beginTime);

                            String [] begSplit = beginTime.split(" ");
                            String [] endSplit = beginTime.split(" ");
                            if(begSplit.length != 3 || endSplit.length != 3)
                            {
                                throw new Exception("Date formatted incorrectly must be of the form mm/dd/yyyy hh:mm am/pm");
                            }
                            String beginD = begSplit[0];
                            String beginT = begSplit[1];
                            String beginTimeOfDay = begSplit[2];
                            String endD = endSplit[0];
                            String endT = endSplit[1];
                            String endTimeOfDay = endSplit[2];
                            appt = new Appointment(beginD, beginT, endD, endT, desciptionAppt, beginTimeOfDay, endTimeOfDay);
                            Log.v("Print", appt.toString());

                        }
                        catch (Exception err)
                        {
                            Log.v("print", err.getMessage());
                        }



                    }
                    }
        );
    }

}
