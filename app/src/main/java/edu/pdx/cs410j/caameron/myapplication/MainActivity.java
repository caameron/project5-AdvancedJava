package edu.pdx.cs410j.caameron.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Main screen class for the application, contains three buttons to go to three different
 * funcionality and a menu button for help
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Method to inflate and show the menu options button in the screen
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu, menu);
        return true;
    }

    /**
     * Method to set the actions of what happens when one of the menu items is selected. Currently
     * there is only one item which can be selected which is the README item, which will take the
     * user to the README screen
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuHelp:
                Intent launch = new Intent(MainActivity.this, readme.class);
                startActivityForResult(launch, 0);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sets the view of the screen upon creation based on xml file
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method to start the README screen when it is invoked. Called through a button on the screen
     * @param view
     */
    public void readMe(View view)
    {
        Intent intent = new Intent(this, readme.class);
        startActivity(intent);
    }

    /**
     * Method to start the Make Appointment screen when it is invoked. Called through a button on the screen
     * @param view
     */
    public void make_appointment(View view)
    {
        Intent intent = new Intent(this, appointmentActivity.class);
        startActivity(intent);
    }

    /**
     * Method to start the Print Appointment Book screen when it is invoked. Called through a button on the screen
     * @param view
     */
    public void pretty_print(View view)
    {
        Intent intent = new Intent(this, prettyPrint.class);
        startActivity(intent);
    }

    /**
     * Method to start the Search Appointment screen when it is invoked. Called through a button on the screen
     * @param view
     */
    public void search_appointment(View view)
    {
        Intent intent = new Intent(this, searchAppointment.class);
        startActivity(intent);
    }

}
