package edu.pdx.cs410j.caameron.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class readme extends AppCompatActivity {
    TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readme);
        content = findViewById(R.id.readMeText);


        String about = "Caameron Nakasone\nAdvanced Programming in Java\nProject 4: A REST-ful Appointment Book Web Service\n" +
                "Project 4 will take in arguments from the command line to create an Appointment Book and an Appointment\n" +
                "that will be automatically added to the created Appointment Book. The arguments include the name of the owner for the\n" +
                "appointment book and details about the appointment which will be added (description, start date/time and end date/time\n" +
                "There are now host and port options which can specify a host and port for the project to make get and post requests to the server\n" +
                "The server code is in the AppointmentBookServlet.java file and will allow you to create appointments and appointment books on the server\n" +
                "You can then get all the appointments that will be formatted using PrettyPrinter, and there will also be an option to search for\n" +
                "specific appointments within a time period\n" +
                "To create an appointment on the server the arguments are the same. However to get all appointments of a certain book only the owner\n" +
                "is needed and to search only the owner of the book and the start and end dates are needed.\n" +
                "There are also README and print options which you may choose to invoke. The README brings up this message and the print\n" +
                "option will print the appointment that has just been added to the standard output. No fields can be empty from the command\n" +
                "line and the dates and times must be formatted correctly for the program to run. If you have entered in something\n" +
                "incorrectly an error message will be printed out and the program will exit.\n";
        content.setText(about);
    }
}
