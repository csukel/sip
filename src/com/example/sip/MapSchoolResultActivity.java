package com.example.sip;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This activity contains information for the screen that is responsible to show on a google map the results(schools)
 * which the user has selected before in SchoolListActivity. Schools are shown as markers and boroughs as 
 * polygons. Schools are coloured according the gcse results and boroughs according the unemployment rate.
 * @author Loucas Stylianou
 *
 */
public class MapSchoolResultActivity extends FragmentActivity {
	
	/**
	 * This button when is pressed explains what the green colour means
	 */
	private Button btngreen;
	
	/**
	 * This button when is pressed explains what the yellow colour means
	 */
	private Button btnyellow;
	
	/**
	 * This button when is pressed explains what the red colour means
	 */
	private Button btnred;
	
	/**
	 * Shows/hides markers drawn on the map.
	 */
	private Button btnMarker;
	
	/**
	 * A google map object contains map information
	 */
	private GoogleMap myMap;
	
	/**
	 * This array list contains all the markers that are added on the map
	 */
	private ArrayList<Marker> markerArray = new ArrayList<Marker>();
	
	/**
	 * A list to store the schools that are going to be used according to the results of the 
	 * previous activity
	 */
	private ArrayList<School> schoolList = new ArrayList<School>();
	
	/**
	 * This variable is used in conjunction of the button markers to show/hide the markers. Shows the current
     * state of the markers.
	 */
	private boolean markersVisibility = false;
	
	/**
	 * This index is used when markers are added to the map in order to set their visibility to false
	 */
	private int markerIndex = 0;
	
	/**
	 * When this activity is created, schools are shown in the form of markers on the map but first the user should press
	 * show markers to see them. Also polygon (boroughs' boundaries) are drawn on the map according to some
	 * criteria. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_result_activity);
		
		connectWidgets();
		
		Bundle intent = getIntent().getExtras();
		ArrayList<Integer> tempList = intent.getIntegerArrayList("schoolList");

		
		try {
			for (int i=0;i<tempList.size();i++){
				schoolList.add(SchoolStore.getSpecificSchool(tempList.get(i)-1));
			}
		}catch (NullPointerException e){
			Log.e("ERROR", "Error when copying tempList to schoolList:" +e.toString());
		}
		tempList = null;
		
        //initial camera position and zoom level
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(51.5171,-0.1062));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);

        //focus on London by changing the camera location and zoom meter
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);
        
    	if(myMap!=null){
    		for (int i=0;i<schoolList.size();i++){
    			float gcseResult = schoolList.get(i).getGcseResults();
    			float unemploymentRate = BoroughStore.getSpecificBorough(schoolList.get(i).getBoroughId()-1).getUnemploymentRate();
    		    int	boroughIndex = schoolList.get(i).getBoroughId()-1;
    		      		   
    		    
    			if (gcseResult>=0 && gcseResult<=40){
					   addMarker(i,BitmapDescriptorFactory.HUE_RED);
					   int color = getBoroughFillColor(unemploymentRate);
					   addPolygon(boroughIndex,color);

    				
    			}
    			else if (gcseResult>=40.1 && gcseResult<=69.9){
					   addMarker(i,BitmapDescriptorFactory.HUE_YELLOW);
					   int color = getBoroughFillColor(unemploymentRate);
					   addPolygon(boroughIndex,color);
    			}
    			else {
					   addMarker(i,BitmapDescriptorFactory.HUE_GREEN);
					   int color = getBoroughFillColor(unemploymentRate);
					   addPolygon(boroughIndex,color);
    				
    			}
    		}
    	}
	}

	
	/**
	 * This method is used to get the needed colour for the polygons (boroughs) according to their
	 * unemployment rate.
	 * @param unemploymentRate The unemployment rate of the borough.
	 * @return The colour that should be used for the setFillColor of a polygon
	 */
	private int getBoroughFillColor(float unemploymentRate) {
		
		if (unemploymentRate>=0 && unemploymentRate<=8.0 ){
			    return Color.GREEN;
			   
		   }
		   else if (unemploymentRate>=8.1 && unemploymentRate<=11.0 ){
			   return Color.YELLOW;
			   
		   }
		   else { 
			   return Color.RED;
		   }
		
	}

	/**
	 * This method connects android widgets found in the map_result_activity.xml file with this class
	 * corresponding fields. Also initialises map object and calls the SupportMapFragment in order to make it
	 * compatible with older versions of android apis.
	 */
	private void connectWidgets() {
		btngreen = (Button)findViewById(R.id.btngreen);
		btnyellow = (Button)findViewById(R.id.btnorange);
		btnred = (Button)findViewById(R.id.btnred);
		btnMarker = (Button)findViewById(R.id.btnMarker);
		//in order to make the google maps work on android versions older than v4 make use of support map fragment
		android.support.v4.app.FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment 
         = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
        //initial map 
        myMap = mySupportMapFragment.getMap();	
	}
	
	/**
	 * This method is responsible to create polygons according to the given parameters.
	 * @param index Location of the borough in the BoroughSore.
	 * @param color The colour that the polygon should be drawn with.
	 */
	//add polygon
	public void addPolygon(int index,int color){
		try {
		myMap.addPolygon(new PolygonOptions().addAll(BoroughStore.getSpecificBorough(index).getBoundaries())
				.strokeColor(Color.BLACK)
				.strokeWidth((float)6)
				.fillColor(color));
		} catch (NullPointerException e){
			Log.e("ERROR", "Error addPolygon:" +e.toString());
		}
	}
	
	/**
	 * This method is responsible to create and add markers on the map. Markers are used to show schools location.
	 * @param index The location of the school object in the schoolList. Used to retrieve school's name and coordinates.
	 * @param color The colour that the marker should have according to the given criteria.
	 */
	//add marker
	public void addMarker(int index,float color){
		try {
		   markerArray.add(myMap.addMarker(new MarkerOptions()
		   .icon(BitmapDescriptorFactory.defaultMarker(color))//change default color of the marker
	       .position(new LatLng(schoolList.get(index).getLat(),schoolList.get(index).getLong() ))//get coordinates for each borough
	       .title(schoolList.get(index).getName())));
		   //change visibility of the marker
		   markerArray.get(markerIndex).setVisible(false);
		   markerIndex++;
		}catch(NullPointerException e) {
			Log.e("ERROR","Error when adding markers:" +e.toString());
		}
		   
	}
	
	/**
	 * When the activity is running the user could click on the button show markers to see the markers on the
	 * map and the same button to hide markers of the map. Also the user has the ability to click on a marker
	 * info window and then a dialog box appears containing information for the specific school.
	 */
	@Override
	public void onResume(){
		super.onResume();
		//garbage collector is called for better memory management
		System.gc();
		
		//when the info box of each marker is pressed...
		myMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				final AlertDialog.Builder alert = new AlertDialog.Builder(MapSchoolResultActivity.this);
		    	//Create a new Alert Dialog window
	
		        //Set a title, a message and an icon for the dialog box
		    	int index = markerArray.indexOf(marker);
		    	
		    	//fill the title and the msg of the dialog box
		        alert.setTitle("School info");
		        alert.setMessage("School name: " + schoolList.get(index).getName()+
		        		"\n"+"\nBorough: " + BoroughStore.getSpecificBorough(schoolList.get(index).getBoroughId()-1).getName() +
		        		"\n" + "\nGcse Results: " + schoolList.get(index).getGcseResults() + "%" +
		        		"\n" + "\nEligible students: " + schoolList.get(index).getNumOfStudents());

		        
		        //Dismiss button for the dialog box
		        alert.setNeutralButton("Close", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   //Close the dialog box
				           dialog.dismiss();
			           }
			        });
		        //Show the dialog box
		        alert.show();  //<-- See This!
				
			}
		});
		
		//when button green is pressed output a help msg in the form of a toast
		btngreen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

		        Toast.makeText(getApplicationContext(),
		        		"Boroughs: \nLow Level of Unemployment Rate.... Range: 0%-8% " +
		        		"\n\nSchools: \nHigh level of GCSE Results... Range: 69.91%-100%", 
		        		Toast.LENGTH_LONG).show();
				
			}
			
		});	
		//when button yellow is pressed output a help msg in the form of a toast
		btnyellow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

		        Toast.makeText(getApplicationContext(),
		        		"Boroughs:\nMedium level of Unemployment... Range: 8.1%-11%" +
		        		"\n\nSchools:\nMedium level of GCSE Results... Range: 40.1%-69.9%", 
		        		Toast.LENGTH_LONG).show();
	
			}
			
		});	
		//when button red is pressed output a help msg in the form of a toast
		btnred.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

		        Toast.makeText(getApplicationContext(),
		        		"Boroughs:\nHigh level of Unemployment... Range: 11.1%-16%" +
		        		"\n\nSchools:\nLow level of GCSE Results... Range: 0%-40%", 
		        		Toast.LENGTH_LONG).show();
	
			}
			
		});	
		//change visibility of markers and the text of the button
		btnMarker.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				try {
					if (!markersVisibility){
						for (int i = 0;i<markerArray.size();i++){
							markerArray.get(i).setVisible(true);
						}
						btnMarker.setText("Hide Markers");
						markersVisibility = true;
					}
					else {
						for (int i = 0;i<markerArray.size();i++){
							markerArray.get(i).setVisible(false);
						}
						markersVisibility = false;
						btnMarker.setText("Show Markers");
					}
				}catch(NullPointerException e){
					Log.e("ERROR","Error when setting markers visibility: " + e.toString());
					
				}
			}
			
		});
		//on map click event animate camera and move it to the point on the map that was clicked on
		myMap.setOnMapClickListener(new OnMapClickListener(){

			
			 @Override
			 public void onMapClick(LatLng point) {
				 
			  myMap.animateCamera(CameraUpdateFactory.newLatLng(point));

			 }
			
		});
		
	}

}
