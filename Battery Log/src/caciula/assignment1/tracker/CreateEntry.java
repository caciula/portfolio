package caciula.assignment1.tracker;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
 * Purpose: Creates a new entry to insert into the log file.
 * 
 * Design rationale: Everything is really standard; this simply reads all values from
 * the EditText boxes and appends them to the log file.
 * 
 * Outstanding issues: None.
 */

public class CreateEntry extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        
        //Code to get the date is from: http://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        EditText dateEntry = (EditText) findViewById(R.id.date_entry);
        dateEntry.setText(dateFormat.format(cal.getTime()));
    }
    
    public void submit (View view) {
    	EditText dateEntry = (EditText) findViewById(R.id.date_entry);
    	EditText descriptionEntry = (EditText) findViewById(R.id.description_entry);
    	EditText startingBatteryEntry = (EditText) findViewById(R.id.starting_battery_entry);
    	EditText endingBatteryEntry = (EditText) findViewById(R.id.ending_battery_entry);
    	EditText timeHourEntry = (EditText) findViewById(R.id.time_hour_entry);
    	EditText timeMinuteEntry = (EditText) findViewById(R.id.time_minute_entry);
    	EditText timeSecondEntry = (EditText) findViewById(R.id.time_second_entry);
    	
    	String date = dateEntry.getText().toString();
    	String description = descriptionEntry.getText().toString();
    	String startingBattery = startingBatteryEntry.getText().toString();
    	String endingBattery = endingBatteryEntry.getText().toString();
    	String hour = timeHourEntry.getText().toString();
    	String minute = timeMinuteEntry.getText().toString();
    	String second = timeSecondEntry.getText().toString();
    	
    	if ((date.length() == 0)||(description.length() == 0)||(startingBattery.length() == 0)||(endingBattery.length() == 0)||(hour.length() == 0)||(minute.length() == 0)||(second.length() == 0)) {
    		TextView error = (TextView) findViewById(R.id.error);
    		error.setText("Error: one or more fields are empty");
    		return;
    	}
    	
    	if ((!startingBattery.matches("^[0-9]+\\.?[0-9]*$"))||(!endingBattery.matches("^[0-9]+\\.?[0-9]*$"))) {
    		TextView error = (TextView) findViewById(R.id.error);
    		error.setText("Error: battery percentages aren't floats");
    		return;
    	}
    	
    	if ((!hour.matches("^[0-9]+$"))||(!minute.matches("^[0-9]+$"))||(!second.matches("^[0-9]+$"))) {
    		TextView error = (TextView) findViewById(R.id.error);
    		error.setText("Error: time values aren't integers");
    		return;
    	}
    	
    	float startingBatteryFloat = Float.parseFloat(startingBattery);
    	startingBattery = String.format("%.2f", startingBatteryFloat);
    	
    	float endingBatteryFloat = Float.parseFloat(endingBattery);
    	endingBattery = String.format("%.2f", endingBatteryFloat);
    	
    	if ((startingBatteryFloat > 100)||(endingBatteryFloat < 0)||(startingBatteryFloat < endingBatteryFloat)) {
    		TextView error = (TextView) findViewById(R.id.error);
    		error.setText("Error: invalid battery usage range");
    		return;
    	}
    	
    	int hourInt = java.lang.Integer.parseInt(hour);
    	int minuteInt = java.lang.Integer.parseInt(minute);
    	int secondInt = java.lang.Integer.parseInt(second);
    	int timeInSeconds = (3600*hourInt)+(60*minuteInt)+(secondInt);
    	String time = "" + timeInSeconds;
    	
    	String message = date + "|" + description + "|" + startingBattery + "|" +
    			endingBattery + "|" + time + "\n";

		try {
			FileOutputStream fos;
			fos = openFileOutput("logfile", Context.MODE_APPEND);
			fos.write(message.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	finish();
    }
}