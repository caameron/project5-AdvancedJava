package edu.pdx.cs410j.caameron.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Class to handle making appointments for the application.
 */
public class appointmentActivity extends AppCompatActivity {

    Button submit;
    EditText owner, descripion, start, end;
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
     * Sets the screen for Making Appointments upon creation. Sets a onClick listener to the submit button which will
     * handle the creation of and Appointment book. It will take the values from all the fields on the screen and
     * use it to create and Appointment using the Appointment.java and AppointmentBook.java classes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //Load previous appointments and their books if needed

        submit = findViewById(R.id.submitAppointment);
        owner = findViewById(R.id.ownerInput);
        descripion = findViewById(R.id.descriptionInput);
        start = findViewById(R.id.startTimeInput);
        end = findViewById(R.id.endTimeInput);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Appointment appt;
                        if(book.isEmpty()) {
                            try {
                                FileInputStream fileI = new FileInputStream(new File(getApplicationContext().getFilesDir(), "appointmentsBooks.txt"));
                                ObjectInputStream ois = new ObjectInputStream(fileI);
                                book = (ArrayList<AppointmentBook<Appointment>>)ois.readObject();
                                ois.close();
                                fileI.close();
                            } catch (Exception err) {
                                try {
                                    FileOutputStream newF = new FileOutputStream(new File(getApplicationContext().getFilesDir(), "appointmentsBooks.txt"));
                                    ObjectOutputStream oos = new ObjectOutputStream(newF);
                                    oos.writeObject(book);
                                    oos.close();
                                    newF.close();
                                }
                                catch (Exception err2)
                                {
                                    Log.v("ERRR", err2.getMessage());
                                }
                            }
                        }
                        try
                        {
//                            FileInputStream fileI = new FileInputStream("appointmentsBooksSave.txt");
                            FileInputStream fileI = new FileInputStream(new File(getApplicationContext().getFilesDir(), "appointmentsBooks.txt"));
                            ObjectInputStream ois = new ObjectInputStream(fileI);
                            book = (ArrayList<AppointmentBook<Appointment>>)ois.readObject();
                            ois.close();
                            fileI.close();

                            if(!book.isEmpty())
                            {
                                for(AppointmentBook<Appointment> b : book)
                                {
                                    Log.v("printBooks", b.getAppointments().toString());
                                }
                            }

                            String beginTime = start.getText().toString();
                            String endTime = end.getText().toString();
                            String desciptionAppt = descripion.getText().toString();
                            String ownerAppt = owner.getText().toString();

                            String [] begSplit = beginTime.split(" ");
                            String [] endSplit = endTime.split(" ");
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


                            boolean ownerFound = false;
                            if(book.isEmpty() == true)
                            {
                                try {
                                    AppointmentBook<Appointment> newBook = new AppointmentBook<>(ownerAppt);
                                    newBook.addAppointment(appt);
                                    book.add(newBook);
                                }
                                catch (Exception err)
                                {
                                    throw new Exception("Error in saving to file: " + err.getMessage());
                                }
                            }
                            else
                            {
                                try {
                                    for(AppointmentBook<Appointment> apptBook : book)
                                    {
                                        String name = apptBook.getOwnerName();
                                        if(name.equals(ownerAppt))
                                        {
                                            apptBook.addAppointment(appt);
                                            ownerFound =true;
                                            break;
                                        }
                                    }

                                    if(ownerFound ==false)
                                    {
                                        AppointmentBook<Appointment> newBook = new AppointmentBook<>(ownerAppt);
                                        newBook.addAppointment(appt);
                                        book.add(newBook);
                                    }
                                }
                                catch (Exception err)
                                {
                                    throw new Exception("Error in saving to file: " + err.getMessage());
                                }
                            }

                        }
                        catch (Exception err)
                        {
                            Toast errMessage = Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT);
                            errMessage.show();
                            return;
                        }
                        //Serialize Book and write to file
                        try {
                            FileOutputStream file = new FileOutputStream(new File(getApplicationContext().getFilesDir(), "appointmentsBooks.txt"));
                            ObjectOutputStream oos = new ObjectOutputStream(file);
                            oos.writeObject(book);
                            oos.close();
                            file.close();
                        }
                        catch (Exception err)
                        {
                            Toast errMessage = Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT);
                            errMessage.show();
                            return;
                        }

                        Toast success = Toast.makeText(getApplicationContext(), "Appointment Created", Toast.LENGTH_SHORT);
                        success.show();
                        TextView print = findViewById(R.id.printAppointment);
                        print.setText(appt.toString());
                    }
                    }

        );
    }

}
