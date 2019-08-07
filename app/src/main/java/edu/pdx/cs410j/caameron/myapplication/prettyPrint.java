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
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class prettyPrint extends AppCompatActivity {

    Button submit;
    EditText owner;
    TextView printArea;
    private ArrayList<AppointmentBook<Appointment>> book = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
