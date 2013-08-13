package caciula.assignment1.tracker;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/*
 * Purpose: Displays the three statistics asked for on the assignment page.
 * 
 * Design rationale: Very straightforward calculations. Also, if the file is
 * empty, or the total time amounts to zero, the consumption rate is automatically
 * set to zero to avoid dividing by zero errors.
 * 
 * Outstanding issues: None.
 */

public class CheckBatteryStatistics extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_battery_statistics);
        
        TextView batteryUsedEntry = (TextView) findViewById(R.id.statistics_battery_used_entry);
        TextView batteryTimeEntry = (TextView) findViewById(R.id.statistics_battery_time_entry);
        TextView batteryRateEntry = (TextView) findViewById(R.id.statistics_battery_rate_entry);
        
        float totalOverallBatteryPercentageUsed = 0;
        float totalOverallBatteryTime = 0;
        float overallEnergyConsumptionRate = 0;
        
        try {
 			FileInputStream fis = openFileInput("logfile");
 			DataInputStream dis = new DataInputStream(fis);
 			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
 			
 			String line;
 			
 			while ((line = br.readLine()) != null) {
 				String[] tokens = line.split("\\|");
 				
 				float startingBatteryPercentage = Float.parseFloat(tokens[2]);
 				float endingBatteryPercentage = Float.parseFloat(tokens[3]);
 				int time = java.lang.Integer.parseInt(tokens[4]);
 				
 				totalOverallBatteryPercentageUsed += (startingBatteryPercentage - endingBatteryPercentage);
 				totalOverallBatteryTime += time;
 			}
 			
 			if (totalOverallBatteryTime == 0) {
 				overallEnergyConsumptionRate = 0;
 			} else {
 				overallEnergyConsumptionRate = (totalOverallBatteryPercentageUsed / totalOverallBatteryTime) * 3600;
 			}
 			
 			dis.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
        
        batteryUsedEntry.setText(String.format("%.2f", totalOverallBatteryPercentageUsed));
        batteryTimeEntry.setText(String.format("%.1f", totalOverallBatteryTime));
        batteryRateEntry.setText(String.format("%.1f", overallEnergyConsumptionRate));
    }
}