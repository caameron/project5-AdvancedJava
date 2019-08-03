package edu.pdx.cs410j.caameron.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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


}
