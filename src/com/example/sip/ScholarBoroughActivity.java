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
 * This activity represents the screen that asks the user to select ranges for unemployment and crime rate to
 * see the results. Contains seekbars for inputting data and text views for outputting information.  
 * @author Loucas Stylianou
 *
 */
public class ScholarBoroughActivity extends Activity implements OnSeekBarChangeListener {
	
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
	 * This button is connected with the corresponding button in the xml file. Processing of the user inputs is
	 * done here and passes results to the MapBoroughResultActivity
	 */
	private Button btnResultMap;
	
	/**
	 *  This button is connected with the corresponding button in the xml file. Processing of the user inputs is
	 * done here and passes results to the ListResultActivity
	 */
	private Button btnResultList;
	
	
	/**
	 * A list of boroughs ids that needs to get sorted according the gcse results.
	 */
	private int[] unsortedList;
	
	/**
	 * When this activity is created all fields of the class that are android widgets connect to the corresponding
	 * ones found in the xml file.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scholarborough_activity);
		
		//connect widgets with the xml layout
		connectWidgets();
		
		lowUnemploymentBar.setOnSeekBarChangeListener(this);
		maxUnemploymentBar.setOnSeekBarChangeListener(this);
		lowCrimeBar.setOnSeekBarChangeListener(this);
		maxCrimeBar.setOnSeekBarChangeListener(this);

	}

	/**
	 * When the progress of one of the seek bars is changed find which one has been changed and write the progress
	 * to the corresponding textview.
	 */
	@Override
	public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
	    switch (bar.getId()) {

	    case R.id.skbScholarLowUnemployment:
	    	txtLowUnemployment.setText("Lower bound: " + progress + "%");
	        break;

	    case R.id.skbScholarMaxUnemployment:
	    	txtMaxUnemployment.setText("Upper bound: " + progress + "%");
	        break;
	        
	    case R.id.skbLowCrime:
	    	txtLowCrimeRate.setText("Lower bound: " + progress + "%");
	        break;
	        
	    case R.id.skbMaxCrime:
	    	txtMaxCrimeRate.setText("Upper bound: " + progress + "%");
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
	 * Store the progress of the bar that its progress has been changed to its secondary progress.
	 */
	@Override
	public void onStopTrackingTouch(SeekBar bar) {
	    switch (bar.getId()) {

	    case R.id.skbScholarLowUnemployment:
	    	lowUnemploymentBar.setSecondaryProgress(lowUnemploymentBar.getProgress());
	        break;

	    case R.id.skbScholarMaxUnemployment:
	    	maxUnemploymentBar.setSecondaryProgress(maxUnemploymentBar.getProgress());
	        break;
	        
	    case R.id.skbLowCrime:
	    	lowCrimeBar.setSecondaryProgress(lowCrimeBar.getProgress());
	        break;
	        
	    case R.id.skbMaxCrime:
	    	maxCrimeBar.setSecondaryProgress(maxCrimeBar.getProgress());
	        break;    
	    }
		
	}
	
	/**
	 * When the activity is running the maximum level of the seek bars is set up and the actions of the buttons
	 * are defined in this method. For both buttons when one of them is clicked check the upper and lower bounds
	 * if everything is fine and then get the lists of boroughs one for the unemployment and one for the crime. Then 
	 * check if there is an empty list. After that check for boroughs that are in both lists and store them in another
	 * list called matchedList. Then check this list if is empty. Then use the merge sort to sort the list
	 * in a descending order. If is not empty and the button that was clicked was the 
	 * ResultMap sorted list to the MapBroughResultActivity otherwise if the button was the ResultList jump to the ListResultActivity.
	 */
	@Override
	public void onResume(){
		super.onResume();
		
		lowUnemploymentBar.setMax(16);
		maxUnemploymentBar.setMax(16);
		lowCrimeBar.setMax(27);
		maxCrimeBar.setMax(27);
		
		btnResultMap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

				//do the processing if bounds are ok
				if (areBoundsOk()) {
					
					ArrayList<Integer> UnemploymentList = getUnemploymentList((float)(lowUnemploymentBar.getProgress()/1.0),(float)(maxUnemploymentBar.getProgress()/1.0));
					ArrayList<Integer> CrimeList = getCrimeList((float)(lowCrimeBar.getProgress()/1.0),(float)(maxCrimeBar.getProgress()/1.0));
	
					//if there is no empty list then continue processing...
					if (noListIsEmpty(UnemploymentList,CrimeList)) {
						ArrayList<Integer> matchedList = getMatchedList(UnemploymentList,CrimeList);
						
						if (!isMatchedListEmpty(matchedList)){
							Intent intentUnemploymentMap = new Intent(getApplicationContext(), MapBoroughResultActivity.class);
							intentUnemploymentMap.putIntegerArrayListExtra("matchedList", matchedList);
							intentUnemploymentMap.putExtra("Coloring", "GCSE Results");
							startActivity(intentUnemploymentMap);
						}
					}	
				}
			}		
		});	
		
		btnResultList.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//do the processing if bounds are ok
				if (areBoundsOk()) {
					
					ArrayList<Integer> UnemploymentList = getUnemploymentList((float)(lowUnemploymentBar.getProgress()/1.0),(float)(maxUnemploymentBar.getProgress()/1.0));
					ArrayList<Integer> CrimeList = getCrimeList((float)(lowCrimeBar.getProgress()/1.0),(float)(maxCrimeBar.getProgress()/1.0));
	
					//if there is no empty list then continue processing...
					if (noListIsEmpty(UnemploymentList,CrimeList)) {
						ArrayList<Integer> matchedList = getMatchedList(UnemploymentList,CrimeList);
						
						if (!isMatchedListEmpty(matchedList)){
							unsortedList = new int[matchedList.size()]; 
							for (int i=0;i<unsortedList.length;i++){
								unsortedList[i] = matchedList.get(i);
							}
							merge_sort(0,unsortedList.length-1);
							
							Intent intentList = new Intent(getApplicationContext(), ListResultActivity.class);
							intentList.putExtra("sortedList", unsortedList);
							//intentList.putIntegerArrayListExtra("matchedList", matchedList);
							//intentUnemploymentMap.putExtra("Coloring", "GCSE Results");
							startActivity(intentList);
						}
					}	
				}
			}		
		});
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
		 * This method takes the two lists, unemploymentList and crimeList and finds if there exist 
		 * boroughs ids stored in both lists and store them in another list.
		 * @param UnemploymentList List of boroughs' ids according to unemployment
		 * @param crimeList List of boroughs' ids according to crime
		 * @return A list that is the conjunction of these two lists.
		 */
		public ArrayList<Integer> getMatchedList(ArrayList<Integer> UnemploymentList,ArrayList<Integer> crimeList){
			
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
		 * Check bounds of for unemployment and crime rate 
		 * @return True if everything is ok, false otherwise
		 */
		public boolean areBoundsOk(){
			//check the case of having a lower bound greater or equal than the upper bound
			boolean value = true;
			for (int i=0;i<2;i++){
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
		 * This method checks that there is no exist an empty list 
		 * @param UnemploymentList List of boroughs' ids for unemployment
		 * @param CrimeList List of boroughs' ids for crime
		 * @return True if one of them or both are empty, false otherwise.
		 */
		public boolean  noListIsEmpty(ArrayList<Integer> UnemploymentList,ArrayList<Integer> CrimeList){
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
			if (!value){
				Toast.makeText(getApplicationContext()
		        		,"ERROR: No Match Found... Change given range of " + msg
		        		, Toast.LENGTH_LONG).show();
			}
			return value;
		
		}
		
		/**
		 * Check if the matchedList is empty
		 * @param matchedList A list that is a conjunction of unemploymentList and crimeList
		 * @return True if it is empty, false otherwise
		 */
		public boolean isMatchedListEmpty(ArrayList<Integer> matchedList){
			if (matchedList.isEmpty()){
				Toast.makeText(getApplicationContext()
		        		,"ERROR: No Match Found... Please change the range"
		        		, Toast.LENGTH_SHORT).show();
				return true;
			}
			else
				return false;
		}
		
		/**
		 * This method takes two seek bars one for the upper and one for the lower bound and the two corresponding
		 * text views and initialise them.
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
		 * Connect the class fields that are android widgets with the corresponding ones in the xml file.
		 */
		public void connectWidgets(){
			lowUnemploymentBar = (SeekBar)findViewById(R.id.skbScholarLowUnemployment);
			maxUnemploymentBar = (SeekBar)findViewById(R.id.skbScholarMaxUnemployment);
			lowCrimeBar = (SeekBar)findViewById(R.id.skbLowCrime);
			maxCrimeBar = (SeekBar)findViewById(R.id.skbMaxCrime);
			
			
			txtLowUnemployment = (TextView)findViewById(R.id.txtScholarLowUnemployment);
			txtMaxUnemployment = (TextView)findViewById(R.id.txtScholarMaxUnemployment);
			txtLowCrimeRate = (TextView)findViewById(R.id.txtLowCrime);
			txtMaxCrimeRate = (TextView)findViewById(R.id.txtMaxCrime);
			
			btnResultMap = (Button)findViewById(R.id.btnResultMap);
			btnResultList = (Button)findViewById(R.id.btnResultList);
			
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
		            if (BoroughStore.getSpecificBorough(unsortedList[i]-1).getGcseResults() >= BoroughStore.getSpecificBorough(unsortedList[j]-1).getGcseResults()) {
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
