package com.example.sip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity is the main screen of the program. Contains all three functions, Scholarships, Unemployment Scheme
 * and London Map.
 * @author Loucas
 *
 */
public class MainActivity extends Activity {
	
	/**
	 * This button when is clicked switches to the Unemployment Scheme activity
	 */
	private Button btnUnemploymentScheme;
	
	/**
	 * This button connects this activity with the London Map activity
	 */
	private Button btnLondonMap;
	
	/**
	 * This button connects this activity with the scholarships by boroughs and scholarships by schools activities
	 */
	private Button btnScholarships;

 
    /**
     * When the activity is created connect android widgets found in xml file with the corresponding fields of
     * this class
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		
		//connet widgets
		connectWidgets();

	}

	//connect widgets of the layout with the java objects
	private void connectWidgets() {
		
		btnUnemploymentScheme = (Button)findViewById(R.id.btnUnemployment);
		btnLondonMap = (Button)findViewById(R.id.btnMap);
		btnScholarships = (Button)findViewById(R.id.btnScholarship);
		
	}


	/**
	 * When the activity is running and the user clicks on one of the buttons then the program steps to the
	 * corresponding activity(screen). In the case of scholarships, when the button is clicked, a
	 * small alert dialog is created instead of having another activity. This button contains three buttons:
	 * school, borough, close. School connects this activity with the ScholarSchoolActivity, borough with 
	 * ScholarBoroughActivity and the close button dismisses the alert dialog box.
	 */
	@Override
	public void onResume(){
		super.onResume();
		
		//On click function for London Map button
		btnLondonMap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//Create an intent and switch to London Map Activity(Screen)
				Intent intentMap = new Intent(getApplicationContext(),LondonMapActivity.class);
				startActivity(intentMap);
			}
			
		});
		//On click function for Unemployment Button
		btnUnemploymentScheme.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//Create an intent and switch to Unemployment Activity(Screen)
				Intent intentUnempl = new Intent(getApplicationContext(),UnemploymentActivity.class);
				startActivity(intentUnempl);			}
			
		});
		
		//On click function for scholarships button...
		btnScholarships.setOnClickListener(new View.OnClickListener() {
		    @SuppressWarnings("deprecation")
			public void onClick(View view) {
		    	
		    	final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		    	//Create a new Alert Dialog window
	
		        //Set a title, a message and an icon for the dialog box
		    	
		        alert.setTitle("Scholarships by:");
		        alert.setMessage("Select Category");
		        alert.setIcon(R.drawable.alert);

		        
		        //Create a button named School
		        alert.setPositiveButton("School",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int which) {
		        	   Intent intentScholarSchool = new Intent(getApplicationContext(),ScholarSchoolActivity.class);
		        	   startActivity(intentScholarSchool);
		           }
		        });
		        
		        //Create a button named Borough
		        alert.setNeutralButton("Borough", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   Intent intentScholarBorough = new Intent(getApplicationContext(),ScholarBoroughActivity.class);
			        	   startActivity(intentScholarBorough);
			           }
			        });
		        //Create a button named Close 
		        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   //Close the dialog box
			              dialog.dismiss();
			           }
			        });
		        //Show the dialog box
		        alert.show();  //<-- See This!
		       
		    }

		});
		
	}

	
}
