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
import com.google.android.gms.maps.model.PolygonOptions;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * This activity is used by two other activities to show Borough results on the map. These two activities are the 
 * following: UnemploymentActivity, ScholarBoroughActivity. Boroughs are added on the map in the form of markers and 
 * also their boundaries are drawn by the use of polygons.
 * @author Loucas Stylianou
 *
 */
public class MapBoroughResultActivity extends FragmentActivity {
	
	/**
	 * This array list contains all the markers that are added on the map
	 */
    ArrayList<Marker> markerArray = new ArrayList<Marker>();
    
    /**
     * This array list contains Boroughs that are results of the previous activities and passed on as 
     * extra using the intent
     */
    ArrayList<Borough> boroughArray = new ArrayList<Borough>();
    
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
     * This button shows/hides the markers when is pressed depending on the previous state
     */
    private Button btnMarker;
    
    /**
     * This variable is used to differentiate from which activity the program jump to this activity and 
     * by which criteria is going to colour the markers and the polygons
     */
    private String coloring = null;
    
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
     * This is the map object
     */
	private GoogleMap myMap;
	
	
	/**
	 * When this activity is created all data passed through the intent by the previous activity are stored to the 
	 * corresponding fields. Also the connection between this class fields and the android widgets found in the 
	 * map_result_activity.xml file is established. The camera of google map object focus on London territory and zooms
	 * as much as is needed. Markers and polygons are added on the map and colored according to the information 
	 * that has been retrieved from  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_result_activity);
		
		Bundle intentUnemploymentMap = getIntent().getExtras();
		coloring = intentUnemploymentMap.getString("Coloring");
		ArrayList<Integer> boroughList = intentUnemploymentMap.getIntegerArrayList("matchedList");
		
		connectWidgets();
              
        //get data for boroughs
        for (int i=0;i<boroughList.size();i++){
        	//get borough by using its Id minus 1 which gives the index where it is stored in the store
        	boroughArray.add(BoroughStore.getSpecificBorough(boroughList.get(i)-1));
        	
        }
        
        
        //initial camera position and zoom level
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(51.5171,-0.1062));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);

        //focus on London by changing the camera location and zoom meter
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);
        
        //add markers and polygons on the map according to results
		if(myMap!=null){
		   for (int i=0;i<boroughArray.size();i++){
			   
			   
			   if (coloring.equals("Unemployment Rate")){
				   float unemploymentRate = boroughArray.get(i).getUnemploymentRate();
				   //add markers and colored them according to the unemployment level
				   if (unemploymentRate>=0 && unemploymentRate<=8.0 ){
					   
					   addMarker(i,BitmapDescriptorFactory.HUE_GREEN,"Unemployment Rate: " + boroughArray.get(i).getUnemploymentRate() + "%");

					   addPolygon(i,Color.GREEN);
					   

				   }
				   else if (unemploymentRate>=8.1 && unemploymentRate<=11.0 ){
					   
					   addMarker(i,BitmapDescriptorFactory.HUE_YELLOW,"Unemployment Rate: " + boroughArray.get(i).getUnemploymentRate() + "%");

					   addPolygon(i,Color.YELLOW);

				   }
				   else if (unemploymentRate>=11.1 && unemploymentRate<=16.0 ){
					   
					   addMarker(i,BitmapDescriptorFactory.HUE_RED,"Unemployment Rate: " + boroughArray.get(i).getUnemploymentRate() + "%");

					   addPolygon(i,Color.RED);
				   }
			   }
			   else if (coloring.equals("GCSE Results")){
				   
				   float gcseResults = boroughArray.get(i).getGcseResults();
				   //add markers and colored them according to the gcse results
				   if (gcseResults>=55 && gcseResults<=60.0 ){

					   
					   addMarker(i,BitmapDescriptorFactory.HUE_RED,"GCSE Results: " + boroughArray.get(i).getGcseResults() + "%");

					   addPolygon(i,Color.RED);
				   }
				   else if (gcseResults>=60.1 && gcseResults<=64 ){
					   
					   addMarker(i,BitmapDescriptorFactory.HUE_YELLOW,"GCSE Results: " + boroughArray.get(i).getGcseResults() + "%");

					   addPolygon(i,Color.YELLOW);
				   }
				   else if (gcseResults>=64.1 && gcseResults<=74){
					   addMarker(i,BitmapDescriptorFactory.HUE_GREEN,"GCSE Results: " + boroughArray.get(i).getGcseResults() + "%");
					   
					   addPolygon(i,Color.GREEN);
				   }
			   }
		   }
		}
        

	}


	/**
	 * This method established connection between this class (activity) fields and the 
	 * android widgets found in map_result_activity.xml file
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
	 * This method defines the actions that should be done under user interaction with the program during
	 * the execution stage. When the user clicks on the info window of a marker then this activity terminates and
	 * steps to the next activity which is the BoroughProfileActivity. Also if the user press on one of the 
	 * green, yellow and red buttons the corresponding message box appears to introduce to the user the use
	 * of colour for markers and polygons. This is done by the use of the toast. Finally when the user clicks on the
	 * button for markers then it shows/hides the markers on the map according the state that is taken by the 
	 * boolean variable markersVisibility.
	 */
	@Override
	public void onResume(){
		super.onResume();
		
		//when the info window of each marker is pressed...
		myMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				//create an intent with the activity that should go to
				Intent intentBorough = new Intent(getApplicationContext(), BoroughProfileActivity.class);
				
				//get borough's name and id 
				intentBorough.putExtra("name", marker.getTitle());
				int id = 0;
				for (int i=0;i<BoroughStore.getSize();i++){
					if (BoroughStore.getSpecificBorough(i).getName().equals(marker.getTitle())){
						id = BoroughStore.getSpecificBorough(i).getId();
					}
				}
				intentBorough.putExtra("id", id);
				//start the new activity (window) 
				startActivity(intentBorough);
				
			}
		});
		//when button green is pressed output a help msg in the form of a toast
		btngreen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (coloring.equals("Unemployment Rate")){
			        Toast.makeText(getApplicationContext(),
			        		"Low level of Unemployment... Range: 0%-8%", 
			        		Toast.LENGTH_LONG).show();
				}
				else if (coloring.equals("GCSE Results")){
			        Toast.makeText(getApplicationContext(),
			        		"High level of GCSE Results... Range: 64.1%-74%", 
			        		Toast.LENGTH_LONG).show();
				}
				
			}
			
		});	
		//when button yellow is pressed output a help msg in the form of a toast
		btnyellow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (coloring.equals("Unemployment Rate")){
			        Toast.makeText(getApplicationContext(),
			        		"Medium level of Unemployment... Range: 8.1%-11%", 
			        		Toast.LENGTH_LONG).show();
				}
				else if (coloring.equals("GCSE Results")) {
			        Toast.makeText(getApplicationContext(),
			        		"Medium level of GCSE Results... Range: 60.1%-64%", 
			        		Toast.LENGTH_LONG).show();
				}
	
			}
			
		});	
		//when button red is pressed output a help msg in the form of a toast
		btnred.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (coloring.equals("Unemployment Rate")){
			        Toast.makeText(getApplicationContext(),
			        		"High level of Unemployment... Range: 11.1%-16%", 
			        		Toast.LENGTH_LONG).show();
				}
				else if (coloring.equals("GCSE Results")){
			        Toast.makeText(getApplicationContext(),
			        		"Low level of GCSE Results... Range: 55%-60%", 
			        		Toast.LENGTH_LONG).show();
				}
	
			}
			
		});	
		//change visibility of markers and the text of the button
		btnMarker.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
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
	
	/**
	 * This method takes two parameters index and colour. Index is used to get the borough's boundaries and the 
	 * colour is used to set the fill colour of the polygon according to the given one. 
	 * @param index Location of the borough object on the boroughArray
	 * @param color The colour that should be used to paint the polygon.
	 */
	//add polygon
	public void addPolygon(int index,int color){
		myMap.addPolygon(new PolygonOptions().addAll(boroughArray.get(index).getBoundaries())
				.strokeColor(Color.BLACK)
				.strokeWidth((float)6)
				.fillColor(color));
	}
	
	/**
	 * This method takes as parameters the index which is the location of the borough in the boroughArray, the colour
	 * that the marker should have and the snippet which is the text below the title shown on the info box of the 
	 * marker.
	 * @param index Location of the borough in the boroughArray
	 * @param color Color of the marker
	 * @param snippet Text below the title of the marker on the info box
	 */
	//add marker
	public void addMarker(int index,float color,String snippet){
		   markerArray.add(myMap.addMarker(new MarkerOptions()
		   .icon(BitmapDescriptorFactory.defaultMarker(color))//change default color of the marker
	       .position(new LatLng(boroughArray.get(index).getLat(),boroughArray.get(index).getLong() ))//get coordinates for each borough
	       .title(boroughArray.get(index).getName())
	       .snippet(snippet)));
		   //change visibility of the marker
		   markerArray.get(markerIndex).setVisible(false);
		   markerIndex++;
		   
	}
	

}