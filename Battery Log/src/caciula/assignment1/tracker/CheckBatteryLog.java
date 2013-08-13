package caciula.assignment1.tracker;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * Purpose: Displays all entries created by the user, with the option to delete
 * and create entries.
 * 
 * Design rationale: I decided to write to file a single line per entry. I first
 * intended to have an OO solution (have a LogEntry class and save it to file as
 * an object), but once I got to designing a custom Adaptor for the ListView, it 
 * got too hard. Furthermore, deleting an entry first requires the user to select
 * one from the list and then pressing the delete button. I did it this way, instead
 * of selecting multiple entries, because from my trials once you selected an entry,
 * all indications of you having selected it disappeared, so it remained up to the
 * user to remember which ones were selected.
 * 
 * Outstanding issues: None.
 */

public class CheckBatteryLog extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_battery_log);
        checkLog();
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    	checkLog();
    }
    
    private void checkLog() {
    	//Code for displaying text in a ListView is from: http://www.vogella.com/articles/AndroidListView/article.html
    	ListView listView = (ListView) findViewById(R.id.log_entries);
    	listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        
        List<String> logfileEntries = new ArrayList<String>();
         
        //Code to read a file, line by line, is from: http://www.roseindia.net/java/beginners/java-read-file-line-by-line.shtml
 		try {
 			FileInputStream fis = openFileInput("logfile");
 			DataInputStream dis = new DataInputStream(fis);
 			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
 			
 			String line;
 			
 			while ((line = br.readLine()) != null) {
 				String[] tokens = line.split("\\|");
 				String message = "Date: " + tokens[0] + "\nDescription: " + tokens[1] + "\nStarting battery %: " + tokens[2] + "\nEnding battery %: " + tokens[3] + "\nTime (in seconds): " + tokens[4];
 				
 				logfileEntries.add(message);
 			}
 			
 			dis.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}

 		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
 		          android.R.layout.simple_list_item_1, android.R.id.text1, logfileEntries);
 		listView.setAdapter(adapter);
    }
    
    public void createEntry (View view) {
    	Intent intent = new Intent(this, CreateEntry.class);
    	startActivity(intent);
    }
    
    public void deleteEntry (View view) {
    	ListView listView = (ListView) findViewById(R.id.log_entries);
    	
    	List<String> logfileEntries = new ArrayList<String>();
    	
    	listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    	int selectedItemPosition = listView.getCheckedItemPosition();
    	
    	if (selectedItemPosition >= 0) {
    		try {
     			FileInputStream fis = openFileInput("logfile");
     			DataInputStream dis = new DataInputStream(fis);
     			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
     			
     			String line;
     			int rowNum = 0;
     			
     			while ((line = br.readLine()) != null) {
     				if (rowNum != selectedItemPosition) {
     					logfileEntries.add(line);
     				}
     				rowNum++;
     			}
     			
     			dis.close();
     		} catch (Exception e) {
     			e.printStackTrace();
     		}
    		
    		try {
    			//Code to (over)write to a new file is from: http://developer.android.com/guide/topics/data/data-storage.html
    			FileOutputStream fos;
    			fos = openFileOutput("logfile", Context.MODE_PRIVATE);
    			
    			for (int i = 0; i < logfileEntries.size(); i++) {
    				String message = logfileEntries.get(i) + "\n";
    				fos.write(message.getBytes());
    			}
    			
    			fos.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		checkLog();
    	}
    }
}