package edu.pdx.cs410j.caameron.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Class which will handle the creation and methods on the print appointment books screen.
 */
public class prettyPrint extends AppCompatActivity {

    Button submit;
    EditText owner;
    TextView printArea;
    private ArrayList<AppointmentBook<Appointment>> book = new ArrayList<>();

    /**
     * Gets the intent for the main page of the application and starts it. Used for the back button on the screen
     * @param view
     */
    public void backToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Method that sets the view for the screen and sets the submit button to a onClick Listerner
     *  that uses the PrettyPrinter.java class to print the contents of an Appointment book
     *  by the owners name
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pretty_print);


        submit = findViewById(R.id.submitPrettyPrint);
        owner = findViewById(R.id.ownerPrettyPrint);
        printArea = findViewById(R.id.PrettyPrintArea);
        printArea.setMovementMethod(new ScrollingMovementMethod());

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            FileInputStream fileI = new FileInputStream(new File(getApplicationContext().getFilesDir(), "appointmentsBooks.txt"));
                            ObjectInputStream ois = new ObjectInputStream(fileI);
                            book = (ArrayList<AppointmentBook<Appointment>>)ois.readObject();
                            ois.close();
                            fileI.close();

                            boolean ownerFound = false;
                            if(book.isEmpty() == true)
                            {
                                throw new Exception("Error in printing: No appointment Books in memory");
                            }
                            else
                            {
                                try {
                                    for(AppointmentBook<Appointment> apptBook : book)
                                    {
                                        String name = apptBook.getOwnerName();
                                        if(name.equals(owner.getText().toString()))
                                        {
                                            //Found Book Print it
                                            PrettyPrinter prettyPrinter = new PrettyPrinter();
                                            prettyPrinter.dump(apptBook);
                                            printArea.setText(prettyPrinter.getPrettyOutput());
                                            Log.v("print", prettyPrinter.getPrettyOutput());
                                            ownerFound = true;
                                            break;
                                        }
                                    }
                                    if(ownerFound == false) {
                                        printArea.setText("Owner Not Found");
                                        throw new Exception("No Appointment Book found with that owner name");
                                    }

                                }
                                catch (Exception err)
                                {
                                    printArea.setText("Owner Not Found");
                                    throw new Exception("No Appointment Book found with that owner name");
                                }
                            }

                        } catch (Exception err)
                        {
                            Toast errMessage = Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT);
                            errMessage.show();
                            return;
                        }

                    }
                }
        );

    }
}
