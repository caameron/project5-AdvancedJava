package edu.pdx.cs410j.caameron.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class appointmentActivity extends AppCompatActivity {

    Button submit;
    EditText owner, descripion, start, end;
    private ArrayList<AppointmentBook<Appointment>> book = new ArrayList<>();
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
                        Log.v("print", submit.getText().toString() + owner.getText().toString());
                        Appointment appt = null;
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
                                    Log.v("print", b.getAppointments().toString());
                                }
                            }

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
                        }
                        //Serialize Book and write to file
                        try {
//                            FileOutputStream file = new FileOutputStream("appointmentsBooksSave.txt");

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
                        }
                    }
                    }

        );
    }

}
