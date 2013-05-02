package com.example.sip;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * This activity contains information for the Scholarships by Schools screen. Is connected to the
 * scholar_schools_activity.xml file.
 * @author Loucas Stylianou
 *
 */
public class ScholarSchoolActivity extends Activity implements OnSeekBarChangeListener {

	/**
	 * Variable that connects to the seek bar responsible for the minimum given level of unemployment rate
	 */
	private SeekBar lowUnemploymentBar;
	/**
	 * Variable that connects to the seek bar responsible for the maximum given level of the unemployment rate 
	 */
	private SeekBar	maxUnemploymentBar;
	/**
	 * Variable that connects to the seek bar responsible for the minimum given level of crime rate
	 */
	private SeekBar	lowCrimeBar;
	/**
	 * Variable that connects to the seek bar responsible for the maximum given level of crime rate
	 */
	private SeekBar maxCrimeBar;
	/**
	 * Variable that connects to the seek bar responsible for the minimum given level of gcse average score for schools
	 */
	private SeekBar	lowGcseBar;
	/**
	 * Variable that connects to the seek bar responsible for the maximum given level of gcse average score for schools
	 */
	private SeekBar maxGcseBar;
	/**
	 * Variable that connects to the text view responsible for the minimum given level of unemployment rate
	 */
	private TextView txtLowUnemployment;
	/**
	 * Variable that connects to the text view responsible for the maximum given level of unemployment rate
	 */
	private TextView txtMaxUnemployment;
	/**
	 * Variable that connects to the text view responsible for the minimum given level of crime rate
	 */
	private TextView txtLowCrimeRate;
	/**
	 * Variable that connects to the text view responsible for the maximum given level of crime rate
	 */
	private TextView txtMaxCrimeRate;
	/**
	 * Variable that connects to the text view responsible for the minimum given level of gcse average score for schools
	 */
	private TextView txtLowGcse;
	/**
	 * Variable that connects to the text view responsible for the maximum given level of gcse average score for schools
	 */
	private TextView txtMaxGcse;
	/**
	 * This button is connected with the corresponding button in the xml file. Processing of the user inputs is
	 * done here and passes results to the ListResultActivity
	 */
	private Button btnResultsList;
	/**
	 * A list of schools ids that needs to get sorted according the gcse results.
	 */
	private int[] unsortedList;

	/**
	 * When the activity is created set the content view and connect the class fields that are actually 
	 * android widgets with the corresponding ones in the xml file. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scholar_school_activity);
		
		connectWidgets();
		lowUnemploymentBar.setOnSeekBarChangeListener(this);
		maxUnemploymentBar.setOnSeekBarChangeListener(this);
		lowCrimeBar.setOnSeekBarChangeListener(this);
		maxCrimeBar.setOnSeekBarChangeListener(this);
		lowGcseBar.setOnSeekBarChangeListener(this);
		maxGcseBar.setOnSeekBarChangeListener(this);

		
	}
	/**
	 * Initialise the class fields with their corresponding value found in the xml file.
	 */
	private void connectWidgets() {
		lowUnemploymentBar = (SeekBar)findViewById(R.id.skbSchoolLowUnemployment);
		maxUnemploymentBar = (SeekBar)findViewById(R.id.skbSchoolMaxUnemployment);
		lowCrimeBar = (SeekBar)findViewById(R.id.skbSchoolLowCrime);
		maxCrimeBar = (SeekBar)findViewById(R.id.skbSchoolMaxCrime);
		lowGcseBar = (SeekBar)findViewById(R.id.skbLowGcse);
		maxGcseBar = (SeekBar)findViewById(R.id.skbMaxGcse);
		
		
		txtLowUnemployment = (TextView)findViewById(R.id.txtSchoolLowUnemployment);
		txtMaxUnemployment = (TextView)findViewById(R.id.txtSchoolMaxUnemployment);
		txtLowCrimeRate = (TextView)findViewById(R.id.txtSchoolLowCrime);
		txtMaxCrimeRate = (TextView)findViewById(R.id.txtSchoolMaxCrime);
		txtLowGcse = (TextView)findViewById(R.id.txtLowGcse);
		txtMaxGcse = (TextView)findViewById(R.id.txtMaxGcse);
		
		btnResultsList = (Button)findViewById(R.id.btnSchoolResults);

		
	}
	
	/**
	 * When the progress of a seek bar is changed write to the corresponding text view the new progress.
	 */
	@Override
	public void onProgressChanged(SeekBar bar, int progress,
			boolean fromUser) {
		 switch (bar.getId()) {

		    case R.id.skbSchoolLowUnemployment:
		    	txtLowUnemployment.setText("Lower bound: " + progress + "%");
		        break;

		    case R.id.skbSchoolMaxUnemployment:
		    	txtMaxUnemployment.setText("Upper bound: " + progress + "%");
		        break;
		        
		    case R.id.skbSchoolLowCrime:
		    	txtLowCrimeRate.setText("Lower bound: " + progress + "%");
		        break;
		        
		    case R.id.skbSchoolMaxCrime:
		    	txtMaxCrimeRate.setText("Upper bound: " + progress + "%");
		        break;
		        
		    case R.id.skbLowGcse:
		    	txtLowGcse.setText("Lower bound: " + progress + "%");
		    	break;
		    	
		    case R.id.skbMaxGcse:
		    	txtMaxGcse.setText("Upper bound: " + progress + "%");
		    	break;
		    }
		
	}
	
	/**
	 * Do nothing
	 */
	@Override
	public void onStartTrackingTouch(SeekBar bar) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Set the value of the secondary progress of a seek bar with the progress
	 */
	@Override
	public void onStopTrackingTouch(SeekBar bar) {
		 switch (bar.getId()) {

		    case R.id.skbSchoolLowUnemployment:
		    	lowUnemploymentBar.setSecondaryProgress(lowUnemploymentBar.getProgress());
		        break;

		    case R.id.skbSchoolMaxUnemployment:
		    	maxUnemploymentBar.setSecondaryProgress(maxUnemploymentBar.getProgress());
		        break;
		        
		    case R.id.skbSchoolLowCrime:
		    	lowCrimeBar.setSecondaryProgress(lowCrimeBar.getProgress());
		        break;
		        
		    case R.id.skbSchoolMaxCrime:
		    	maxCrimeBar.setSecondaryProgress(maxCrimeBar.getProgress());
		        break;    
		        
		    case R.id.skbLowGcse:
		    	lowGcseBar.setSecondaryProgress(lowGcseBar.getProgress());
		    	break;
		    	
		    case R.id.skbMaxGcse:
		    	maxGcseBar.setSecondaryProgress(maxGcseBar.getProgress());
		    	break;
		    }
		
	}
	/**
	 * This method defines activities actions according to user 's interaction with it when is running.
	 * When the button ResultList is clicked by the user, check the bounds then check for empty lists and finally
	 * get the matchedList that is a conjunction of the three others: unemploymentList, crimeList, gcseList
	 * use merge sort to sort the list in descending order according gcse results, store the list in an intent and 
	 * then start the SchoolListActivity.
	 */
	@Override
	public void onResume(){
		super.onResume();
		
		lowUnemploymentBar.setMax(16);
		maxUnemploymentBar.setMax(16);
		lowCrimeBar.setMax(27);
		maxCrimeBar.setMax(27);
		
		btnResultsList.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (areBoundsOk()) {
					ArrayList<Integer> UnemploymentList = getUnemploymentList((float)(lowUnemploymentBar.getProgress()/1.0),(float)(maxUnemploymentBar.getProgress()/1.0));
					ArrayList<Integer> CrimeList = getCrimeList((float)(lowCrimeBar.getProgress()/1.0),(float)(maxCrimeBar.getProgress()/1.0));
					ArrayList<Integer> GcseList = getGcseList((float)(lowGcseBar.getProgress()/1.0),(float)(maxGcseBar.getProgress()/1.0));
					
					//if there is no empty list then continue processing...
					if (noListIsEmpty(UnemploymentList,CrimeList,GcseList)) {
						ArrayList<Integer> unempl_crime_matchedList = getUnempl_Crime_MatchedList(UnemploymentList,CrimeList);
						
						if (!isUnempl_Crime_MatchedListEmpty(unempl_crime_matchedList)){
							ArrayList<Integer> matchedList = getMatchedList(unempl_crime_matchedList,GcseList);
							if (matchedList.isEmpty()) {
								Toast.makeText(getApplicationContext()
						        		,"ERROR: No Match Found... Change given range of Gcse Results"
						        		, Toast.LENGTH_LONG).show();
							}
							else {
								unsortedList = new int[matchedList.size()]; 
								for (int i=0;i<unsortedList.length;i++){
									unsortedList[i] = matchedList.get(i);
								}
								merge_sort(0,unsortedList.length-1);
								matchedList.clear();
								for (int i=0;i<unsortedList.length;i++){
									matchedList.add(unsortedList[i]);
								}
								Intent intent = new Intent(getApplicationContext(),SchoolListActivity.class);
								intent.putIntegerArrayListExtra("Schools", matchedList);
								startActivity(intent);
							}
						}
						
					}
					
				}
				
			}




			
		});

	}	
	/**
	 * This method finds schools that exists in the gcseList and the borough where they exist is also found in 
	 * the boroughList
	 * @param boroughList A list with boroughs' ids, conjunction of the unemploymentList and crimeList
	 * @param gcseList A list of schools' ids according to the gcse criteria set by the user
	 * @return A list of schools' ids that is conjunction of both lists send as parameters
	 */
	private ArrayList<Integer> getMatchedList(
			ArrayList<Integer> boroughList,
			ArrayList<Integer> gcseList) {
		
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i=0;i<gcseList.size();i++){
			for (int j=0;j<boroughList.size();j++){
				if (boroughList.get(j)==SchoolStore.getSpecificSchool(gcseList.get(i)-1).getBoroughId()){
					tempList.add(SchoolStore.getSpecificSchool(gcseList.get(i)-1).getSchoolId());
					
				}
				
			}
		}
		
		return tempList;
	}
	
	/**
	 * Check the bounds if are ok. Lower bound cant be greater than or equal to the upper bound.
	 * @return True if everything is fine, false otherwise.
	 */
	private boolean areBoundsOk() {
		//check the case of having a lower bound greater or equal than the upper bound
		boolean value = true;
		for (int i=0;i<3;i++){
			switch (i){
			
			case 0: 
				if (lowUnemploymentBar.getProgress() >= maxUnemploymentBar.getProgress()){
					initializeSeekBar_TextView(lowUnemploymentBar,maxUnemploymentBar,txtLowUnemployment,txtMaxUnemployment);
					value = false;
				}break;
			case 1:
				if (lowCrimeBar.getProgress() >= maxCrimeBar.getProgress()) {
			        initializeSeekBar_TextView(lowCrimeBar,maxCrimeBar,txtLowCrimeRate,txtMaxCrimeRate);
			        value = false;
				}break;
			case 2: 
				if (lowGcseBar.getProgress() >= maxGcseBar.getProgress()) {
			        initializeSeekBar_TextView(lowGcseBar,maxGcseBar,txtLowGcse,txtMaxGcse);
			        value = false;
				}break;
			}

		}
		if (!value){
	        Toast.makeText(getApplicationContext()
	        		,"ERROR: Check bounds... lower bound cannot be greater than or equal to upper bound"
	        		, Toast.LENGTH_LONG).show();
		}
		
		return value;

	}
	/**
	 * Check if there exists an empty list and output error message
	 * @param UnemploymentList A list containing boroughs' ids according to the user's set criteria for unemployment rate
	 * @param CrimeList A list containing boroughs' ids according to the user's set criteria for crime rate
	 * @param GcseList A list containing schools' ids according to the user's set criteria for gcse average score
	 * @return True if there exists an empty list, false otherwise
	 */
	public boolean  noListIsEmpty(ArrayList<Integer> UnemploymentList,ArrayList<Integer> CrimeList
			,ArrayList<Integer> GcseList){
		
		String msg="";
		boolean value = true;
		if (UnemploymentList.isEmpty()){
			msg = msg + "(Unemployment Rate) ";
	        initializeSeekBar_TextView(lowUnemploymentBar,maxUnemploymentBar,txtLowUnemployment,txtMaxUnemployment);
			value = false;
		}
		if (CrimeList.isEmpty()){
			msg = msg + "(Crime Rate) ";
	        initializeSeekBar_TextView(lowCrimeBar,maxCrimeBar,txtLowCrimeRate,txtMaxCrimeRate);
			value = false;
		}
		if (GcseList.isEmpty()){
			msg = msg + "(Gcse Results)";
	        initializeSeekBar_TextView(lowGcseBar,maxGcseBar,txtLowGcse,txtMaxGcse);
			value = false;
		}
		if (!value){
			Toast.makeText(getApplicationContext()
	        		,"ERROR: No Match Found... Change given range of " + msg
	        		, Toast.LENGTH_LONG).show();
		}
		return value;
	}
	/**
	 * This method is used to initialise seek bars when it is needed
	 * @param lowBar Lower bound seek bar
	 * @param maxBar Upper bound seek bar
	 * @param txtLow Text view for lower bound
	 * @param txtMax Text view for upper bound
	 */
	public void initializeSeekBar_TextView(SeekBar lowBar,SeekBar maxBar,TextView txtLow,TextView txtMax){
	    lowBar.setProgress(0);
	    maxBar.setProgress(0);
	    txtLow.setText("Lower bound: 0%");
	    txtMax.setText("Upper bound: 0%");
		
	}
	
	/**
	 * This method checks which boroughs fell into the given range of unemployment and store them in a list
	 * @param low Minimum level of unemployment rate selected by the user
	 * @param max Maximum level of unemployment rate selected by the user
	 * @return A list of boroughs' ids
	*/
	public ArrayList<Integer> getUnemploymentList(float low,float max){
			
		ArrayList<Integer> tempUnemploymentList = new ArrayList<Integer>();
		ArrayList<Borough> tempBoroughList = BoroughStore.getBoroughs();
		for (int i=0;i<tempBoroughList.size();i++){
			if ((tempBoroughList.get(i).getUnemploymentRate()>= low) && 
					(tempBoroughList.get(i).getUnemploymentRate()<=max)){
				tempUnemploymentList.add(tempBoroughList.get(i).getId());
			}		
		}
				
		return tempUnemploymentList;
	}
	/**
	 * This method checks which boroughs fell into the given range of crime and store them in a list
	 * @param low Minimum level of crime rate selected by the user
	 * @param max Maximum level of crime rate selected by the user
	 * @return A list of boroughs' ids
	 */
	public ArrayList<Integer> getCrimeList(float low,float max){
		
		ArrayList<Integer> tempCrimeList = new ArrayList<Integer>();
		ArrayList<Borough> tempBoroughList = BoroughStore.getBoroughs();
		
		for (int i=0;i<tempBoroughList.size();i++){
			
			float crimeRate = tempBoroughList.get(i).getCrimeRate();
			
			
			
			if ((crimeRate>= low) && (crimeRate<=max)){
				tempCrimeList.add(tempBoroughList.get(i).getId());
			}		
		}
			
		return tempCrimeList;
	}
	
	/**
	 * This method checks which schools fell into the given range of gcse results and store them in a list
	 * @param low Minimum level of gcse results selected by the user
	 * @param max Maximum level of gcse results selected by the user
	 * @return A list of schools' ids
	 */
	public ArrayList<Integer> getGcseList(float low,float max){
		
		ArrayList<Integer> tempGcseList = new ArrayList<Integer>();
		ArrayList<School> tempSchoolList = SchoolStore.getSchools();

		for (int i=0;i<tempSchoolList.size();i++){
			
			float gcseResults = tempSchoolList.get(i).getGcseResults();
			
			
			
			if ((gcseResults>= low) && (gcseResults<=max)){
				tempGcseList.add(tempSchoolList.get(i).getSchoolId());
			}		
		}
			
		return tempGcseList;
	}
	
	/**
	 * This method find which boroughs' ids are stored both list: UnemploymentList and crimeList
	 * @param UnemploymentList A list of boroughs' ids according to the unemployment
	 * @param crimeList A list of boroughs' ids according to the crime 
	 * @return A list of boroughs' ids which is a conjunction of UnemploymentList and crimeList.
	 */
	//return a list that contains Boroughs that are in both lists, Crime and Unemployment list
	public ArrayList<Integer> getUnempl_Crime_MatchedList(ArrayList<Integer> UnemploymentList,ArrayList<Integer> crimeList){
		
		ArrayList<Integer> tempList = new ArrayList<Integer>();
			
		for (int i=0;i<UnemploymentList.size();i++){
			for (int j=0;j<crimeList.size();j++){
				if (UnemploymentList.get(i)== crimeList.get(j)){
					tempList.add(UnemploymentList.get(i));
				}
			}
			
		}
		return tempList;
	}
	/**
	 * 
	 * @param unempl_crime_matchedList
	 * @return
	 */
	public boolean isUnempl_Crime_MatchedListEmpty(ArrayList<Integer> unempl_crime_matchedList){
		if (unempl_crime_matchedList.isEmpty()){
			Toast.makeText(getApplicationContext()
	        		,"ERROR: No Match Found... Please change the range of Unemployment and Crime Rates"
	        		, Toast.LENGTH_SHORT).show();
			return true;
		}
		else
			return false;
	}
	/**
	 * This method combines the divided parts of the array
	 * @param left
	 * @param mid
	 * @param right
	 */
	 public void merge(int left, int mid, int right) {
	        int temp [] =new int[right-left+1];
	        int i = left;
	        int j = mid+1;
	        int k = 0;
	        while (i <= mid && j <= right) {
	            if (SchoolStore.getSpecificSchool(unsortedList[i]-1).getGcseResults() >= SchoolStore.getSpecificSchool(unsortedList[j]-1).getGcseResults()) {
	                temp[k] = unsortedList[i];
	                k++;
	                i++;
	            } else { //array[i]>array[j]
	                temp[k] = unsortedList[j];
	                k++;
	                j++;
	            }
	        }
	        while(j<=right) temp[k++]=unsortedList[j++];
	        while(i<=mid) temp[k++]=unsortedList[i++];
	 
	        for(k=0;k<temp.length;k++) unsortedList[left+k]=temp[k];
	    }
		/**
		 * This method divides the array recursively
		 * @param left Index of the array for the lower location
		 * @param right Index of the array for the upper location
		 */
	    public void merge_sort(int left,int right){
	        // Check if low is smaller then high, if not then the array is sorted
	        if(left<right){
	            // Get the index of the element which is in the middle
	            int mid=(left+right)/2;
	            // Sort the left side of the array
	            merge_sort(left,mid);
	            // Sort the right side of the array
	            merge_sort(mid+1,right);
	            // Combine them both
	            merge(left,mid,right);
	        }
	 
	    }
}
