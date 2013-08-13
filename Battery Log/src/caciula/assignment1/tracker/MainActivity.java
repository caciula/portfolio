package caciula.assignment1.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
 * Purpose: This is the main screen that the user encounters. From here, they can
 * choose to either view their log entries, or to view statistics gathered from
 * said log.
 * 
 * Design rationale: I figured the easiest way to avoid clutter is to present users
 * with a screen such as this, instead of having the statistics button in the same
 * activity as the log entries viewer
 * 
 * Outstanding issues: None.
 */

//The image file is from http://www.xda-developers.com/android/dramatically-improve-droid-incredible-battery-life/

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void checkLog (View view) {
    	Intent intent = new Intent(this, CheckBatteryLog.class);
    	startActivity(intent);
    }
    
    public void checkStatistics (View view) {
    	Intent intent = new Intent(this, CheckBatteryStatistics.class);
    	startActivity(intent);
    }
}
