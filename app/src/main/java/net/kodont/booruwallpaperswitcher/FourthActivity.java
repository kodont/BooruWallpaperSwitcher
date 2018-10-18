package net.kodont.booruwallpaperswitcher;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class FourthActivity
        extends AppCompatActivity
        implements Spinner.OnItemSelectedListener
{

    Spinner dayOfWeekSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.days_of_week,
                        android.R.layout.simple_spinner_dropdown_item
                );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        dayOfWeekSpinner.setAdapter(adapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences.Editor editor = getSharedPreferences(
                MainActivity.PREFERENCES_NAME,
                MODE_PRIVATE).edit();
        editor.putInt(MainActivity.DAY_OF_WEEK, position);
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
