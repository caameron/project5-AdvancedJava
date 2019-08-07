package edu.pdx.cs410j.caameron.myapplication;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class searchAppointment extends AppCompatActivity {

    Button submit;
    EditText startDate, endDate, owner;
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
        owner = findViewById(R.id.ownerSearch);
        printArea = findViewById(R.id.searchPrintArea);
        printArea.setMovementMethod(new ScrollingMovementMethod());

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try
                        {
                            FileInputStream fileI = new FileInputStream(new File(getApplicationContext().getFilesDir(), "appointmentsBooks.txt"));
                            ObjectInputStream ois = new ObjectInputStream(fileI);
                            book = (ArrayList<AppointmentBook<Appointment>>)ois.readObject();
                            ois.close();
                            fileI.close();

                            AppointmentBook<Appointment> bookToPrint =null;
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
                                            ownerFound = true;
                                            bookToPrint = apptBook;
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

                            //MAKE FAKE APPOINTMENT TO GET DATE OBJECTS FROM beginTime and endTime
                            Appointment fakeAppt = null;
                            AppointmentBook<Appointment> fakeBook = null;
                            String beginTime = startDate.getText().toString();
                            String endTime = endDate.getText().toString();
                            try
                            {
                                String [] begSplit = beginTime.split(" ");
                                String [] endSplit = endTime.split(" ");
                                if(begSplit.length != 3 || endSplit.length != 3)
                                {
                                    throw new Exception("Date formatted incorrectly must be of the form mm/dd/yyyy hh:mm am/pm");
                                }
                                String beginT = begSplit[1];
                                String beginDate = begSplit[0];
                                String beginTimeOfDay = begSplit[2];
                                String endT = endSplit[1];
                                String endDate = endSplit[0];
                                String endTimeOfDay = endSplit[2];
                                fakeAppt = new Appointment(beginDate, beginT, endDate, endT, "fake", beginTimeOfDay, endTimeOfDay);
                                fakeBook = new AppointmentBook<>("FakeOwner");
                            }
                            catch (Exception err)
                            {
                                throw new Exception("Error in searching for appointments: " + err.getMessage());
                            }

                            for(Appointment appt: bookToPrint.getAppointments())
                            {
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                                String begin = sdf.format(appt.getBeginTime());
                                String end = sdf.format(appt.getEndTime());
                                String fakeBegin = sdf.format(fakeAppt.getBeginTime());
                                String fakeEnd = sdf.format(fakeAppt.getEndTime());
                                Date fakeBeginD = null;
                                Date fakeEndD = null;
                                Date beginD = null;
                                Date endD = null;

                                try {
                                    beginD = sdf.parse(begin);
                                    endD = sdf.parse(end);
                                    fakeBeginD = sdf.parse(fakeBegin);
                                    fakeEndD = sdf.parse(fakeEnd);
                                } catch (ParseException e) {
                                    throw new IOException(e.getMessage());
                                }
                                if(beginD.compareTo(fakeBeginD) >= 0)
                                {
                                    if(endD.compareTo(fakeEndD) <= 0){
                                        fakeBook.addAppointment(appt);
                                    }
                                }

                            }
                            if(fakeBook.getAppointments() == null) {
                                printArea.setText("No Appointments Found within date range for that owner");
                                throw new Exception("No Appointments Found within date range for that owner");
                            }
                            else {
                                PrettyPrinter prettyPrinter = new PrettyPrinter();
                                prettyPrinter.dump(fakeBook);
                                StringBuilder outputBegin = new StringBuilder("Appointments between dates of " + fakeAppt.getBeginTimeString() + " Until " + fakeAppt.getEndTimeString());
                                StringBuilder output = new StringBuilder(prettyPrinter.getPrettyOutput());
                                output.delete(0, 30);
                                outputBegin.append(output);
                                printArea.setText(outputBegin.toString());
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
