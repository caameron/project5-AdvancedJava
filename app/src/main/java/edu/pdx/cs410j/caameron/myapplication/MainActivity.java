package edu.pdx.cs410j.caameron.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuHelp:
                Log.v("PRINTHELP", "HELP");
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

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
