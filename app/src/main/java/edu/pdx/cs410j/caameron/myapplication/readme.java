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


        String about = "Caameron Nakasone\nAdvanced Programming in Java\nProject 5: A Small Device Application for an Appointment Book\n\n" +
                "Project 5 is an application written using Android Studio. It is a small android application which provides the functionality" +
                "of the previous projects. This includes creating appointments and multiple appointment books, searching, and printing the contents " +
                "of the book. The way to create appointments/appointment books, searching, and printing are the same as well. To create an appointments " +
                "you will need to provide owner, date, decsription, begin time and end time. Searching will just require an owner, begin time, and end time and " +
                "printing will only need an owner. These fields will be Edit Texts on the screen for you to input your data.\n\n" +
                "The application will start on the home page with three different buttons which correspond to the functionality above. Each one will take you to" +
                " a different screen with the corresponding edit texts. There will also be a submit button that needs to be pressed when you are" +
                " done entering in information. Error messages will come up in the form of toasts and let you know what is wrong" +
                ". Lastly on the home screen there will be a menu icon (top right) which contains a README option. This will take you " +
                "the README screen and display this page";

        content.setText(about);
    }
}
