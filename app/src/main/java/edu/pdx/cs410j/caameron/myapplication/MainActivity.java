package edu.pdx.cs410j.caameron.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.pdx.cs410J.AbstractAppointment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void make_appointment(View view)
    {
        Intent intent = new Intent(this, appointmentActivity.class);
        startActivity(intent);
    }

    public void pretty_print(View view)
    {
        Intent intent = new Intent(this, prettyPrint.class);
        startActivity(intent);
    }

    public void search_appointment(View view)
    {
        Intent intent = new Intent(this, searchAppointment.class);
        startActivity(intent);
    }

}
