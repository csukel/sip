package com.example.sip;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * This activity contains a google map object and boroughs are added on the map in the form of a mark. 
 * Also boundaries of the boroughs are represented by the use of polygons.
 * @author Loucas Stylianou
 *
 */
public class LondonMapActivity extends FragmentActivity {
	
	/**
	 * This button is used to show and hide markers on the map according to the current state
	 */
	private Button btnMarkers;
	
	/**
	 * This array list contains all the markers that are added to the map
	 */
    ArrayList<Marker> markerArray = new ArrayList<Marker>();
    
    /**
     * This list contains all the boroughs instances
     */
    ArrayList<Borough> boroughArray;
    
    /**
     * This variable is used to know whether markers are visible on the map or not
     */
    private boolean markersVisibility = false;
    
	/**
	 * Google map object. Creates a map using the google maps api version 2
	 */	
	private GoogleMap myMap;
	
	/**
	 * When the activity is created, create the map, add the markers(boroughs' pointers) and polygons(boroughs' boundaries) 
	 * on it and connect the widgets with the class fields. The camera and the zoom are initialised to show London.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.londonmap_activity);
		
		connectWidgets();
		
        //info msg using toast
        Toast.makeText(getApplicationContext(), "Press on a marker and then on the title to get more info for a specific Borough", Toast.LENGTH_LONG).show();
        //get data for boroughs
        boroughArray = BoroughStore.getBoroughs();
       

        //initial camera position and zoom level
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(51.5171,-0.1062));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);

        //focus on London by changing the camera location and zoom meter
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);
        
		if(myMap!=null){
			//add markers and polygons on the map
		   for (int i=0;i<boroughArray.size();i++){
			   
			   markerArray.add(myMap.addMarker(new MarkerOptions()
		       .position(new LatLng(boroughArray.get(i).getLat(),boroughArray.get(i).getLong() ))//get coordinates for each borough
		       .title(boroughArray.get(i).getName())));//set the name of the borough as title for each marker 
			   
			   markerArray.get(i).setVisible(false);
			   
				myMap.addPolygon(new PolygonOptions().addAll(BoroughStore.getSpecificBorough(i).getBoundaries())
						.strokeColor(Color.BLACK)
						.strokeWidth((float)6)
						.fillColor(Color.YELLOW));
		   }
			
					
		}
        

	}


	/**
	 * This method establishes the connection between the fields of this class and the corresponding
	 * android widgets in the londonmap_activity.xml file.
	 */
	private void connectWidgets() {
		btnMarkers = (Button)findViewById(R.id.btnMarkers);
		
		//in order to make the google maps work on android versions older than v4 make use of support map fragment
		android.support.v4.app.FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment=(SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
        //initial map 
        myMap = mySupportMapFragment.getMap();
	}


	/**
	 * When the activity is running define some actions that should be taken according to the users tap events.
	 * When the user click on an info window then create an intent, store the borough name and id in the intent
	 * and step to the next activity which is the BoroughProfileActivity.
	 * When the button of markers in clicked show/hide the markers that are drawn on the map. Also when the user
	 * tap somewhere in the map (not a marker) animate the camera and change position of the camera for the map.
	 */
	@Override
	public void onResume(){
		super.onResume();
		
		
		//when the info box of each marker is pressed...
		myMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				//create an intent with the activity that should go to
				Intent intentBorough = new Intent(getApplicationContext(), BoroughProfileActivity.class);
				
				//get name of borough and index in the array lists
				intentBorough.putExtra("name", boroughArray.get(markerArray.indexOf(arg0)).getName());
				intentBorough.putExtra("id",  boroughArray.get(markerArray.indexOf(arg0)).getId());
				//start the new activity (window) 
				startActivity(intentBorough);
				
			}
		});
		//show or hide markers on the map
		btnMarkers.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (!markersVisibility){
					for (int i = 0;i<markerArray.size();i++){
						markerArray.get(i).setVisible(true);
					}
					btnMarkers.setText("Hide Markers");
					markersVisibility = true;
				}
				else {
					for (int i = 0;i<markerArray.size();i++){
						markerArray.get(i).setVisible(false);
					}
					markersVisibility = false;
					btnMarkers.setText("Show Markers");
				}
			}
			
		});
		//when a point on the map is clicked animate camera and move it to that point
		myMap.setOnMapClickListener(new OnMapClickListener(){

			
			 @Override
			 public void onMapClick(LatLng point) {
				 
			  myMap.animateCamera(CameraUpdateFactory.newLatLng(point));

			 }
			
		});
			
		
		

	}


}
