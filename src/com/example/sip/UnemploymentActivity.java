package com.example.sip;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * UnemploymentActivity is a class that manages the screen of the Unemployment Scheme function.
 * Contains information of the widgets used for this screen.
 * @author Loucas Stylianou
 *
 */
public class UnemploymentActivity extends Activity implements OnSeekBarChangeListener {
	/**
	 * Variable that connects to the seek bar responsible for the minimum given level of unemployment rate
	 */
	private SeekBar lowUnemploymentBar;
	/**
	 * Variable that connects to the seek bar responsible for the maximum given level of the unemployment rate 
	 */
	private SeekBar	maxUnemploymentBar;
	/**
	 * Variable that connects to the seek bar responsible for the minimum given level of qualification rate
	 */
	private SeekBar	lowQualBar;
	/**
	 * Variable that connects to the seek bar responsible for the maximum given level of qualification rate
	 */
	private SeekBar maxQualBar;
	/**
	 * Variable that connects to the text view responsible for the minimum given level of unemployment rate
	 */
	private TextView txtLowUnemployment;
	/**
	 * Variable that connects to the text view responsible for the maximum given level of the unemployment rate 
	 */
	private TextView txtMaxUnemployment;
	/**
	 * Variable that connects to the text view responsible for the minimum given level of the qualification rate 
	 */
	private TextView txtLowQualRate;
	/**
	 * Variable that connects to the text view responsible for the maximum given level of the qualification rate 
	 */
	private TextView txtMaxQualRate;
	/**
	 * Variable that connects to the button responsible do some actions when is pressed
	 */
	private Button btnResult;
	/**
	 * Variable that connects to the spinner that contains the list of categories for the qualification rate
	 */
	private Spinner sprQualCat;
	
	/**
	 * Variable for the item that is selected using the spinner, also known as
	 * drop down list.
	 */
	private String QualCategorySelected;
	
	/**
	 * When the activity is created widgets are connected with the right 
	 * ones used in unemployment_activity.xml file
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unemployment_activity);
		
		//connect widgets with class fields
		connectWidgets();
		
		//use the bars to check the progress when is changed by the user
		lowUnemploymentBar.setOnSeekBarChangeListener(this);
		maxUnemploymentBar.setOnSeekBarChangeListener(this);
		lowQualBar.setOnSeekBarChangeListener(this);
		maxQualBar.setOnSeekBarChangeListener(this);
		
		
	}

	/**
	 * This method recognises which seek bar's value of progress is changed by the user
	 * and writes the new value on the right text view widget in the screen.
	 * @param bar The seek bar that the user changes its value of progress
	 * @param progress The value of the progress.
	 * @param fromUser <Code>True<Code> if the change is done from the user and <Code>false<Code> otherwise.
	 */
	@Override
	public void onProgressChanged(SeekBar bar, int progress,
			boolean fromUser) {
		
		//find which bar's progress is changed and write progress to the right text view widget
	    switch (bar.getId()) {

	    case R.id.skbLowUnemployment:
	    	txtLowUnemployment.setText("Lower bound: " + progress + "%");
	        break;

	    case R.id.skbMaxUnemployment:
	    	txtMaxUnemployment.setText("Upper bound: " + progress + "%");
	        break;
	        
	    case R.id.skbLowQual:
	    	txtLowQualRate.setText("Lower bound: " + progress + "%");
	        break;
	        
	    case R.id.skbMaxQual:
	    	txtMaxQualRate.setText("Upper bound: " + progress + "%");
	        break;    
	    }
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar bar) {
		// Change nothing
		
	}
	/**
	 * This method is used to recognise when the user stop using one of the seek bars in this activity to 
	 * store the progress as secondary progress.
	 * 
	 * @param bar The seek bar that the user interacts with.
	 */
	@Override
	public void onStopTrackingTouch(SeekBar bar) {
		
	    switch (bar.getId()) {

	    case R.id.skbLowUnemployment:
	    	lowUnemploymentBar.setSecondaryProgress(lowUnemploymentBar.getProgress());
	        break;

	    case R.id.skbMaxUnemployment:
	    	maxUnemploymentBar.setSecondaryProgress(maxUnemploymentBar.getProgress());
	        break;
	        
	    case R.id.skbLowQual:
	    	lowQualBar.setSecondaryProgress(lowQualBar.getProgress());
	        break;
	        
	    case R.id.skbMaxQual:
	    	maxQualBar.setSecondaryProgress(maxQualBar.getProgress());
	        break;    
	    }

	}
	/**
	 * One of majors stage of an activity life cycle. During onResume set the maximum level of the seek bars.
	 * Also the definition of the spinner widget(also known as drop down list) is included and finally 
	 * the action that is needed to be done when the button is clicked by the user.
	 */
	@Override
	public void onResume(){
		super.onResume();
		
		lowUnemploymentBar.setMax(16);
		maxUnemploymentBar.setMax(16);
		
		/**
		 * when the an item is selected using the spinner set new Max levels 
		 * for Qualification lower bound and upper bound rate
		 */
		sprQualCat.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				QualCategorySelected= (String)sprQualCat.getItemAtPosition(sprQualCat.getSelectedItemPosition());
				if (QualCategorySelected.equals("Degree")){
					lowQualBar.setMax(55);
					maxQualBar.setMax(55);
				}
				else if (QualCategorySelected.equals("Higher Education")){
					lowQualBar.setMax(12);
					maxQualBar.setMax(12);
				}
				else if (QualCategorySelected.equals("GCE A Level")){
					lowQualBar.setMax(26);
					maxQualBar.setMax(26);
				}
				else if (QualCategorySelected.equals("GCSE Level")) {
					lowQualBar.setMax(33);
					maxQualBar.setMax(33);
				}
				else if (QualCategorySelected.equals("Other Qualifications")){
					lowQualBar.setMax(39);
					maxQualBar.setMax(39);
				}
				else if (QualCategorySelected.equals("No Qualifications")) {
					lowQualBar.setMax(24);
					maxQualBar.setMax(24);
				}
			    lowQualBar.setProgress(0);
			    maxQualBar.setProgress(0);
			    txtLowQualRate.setText("Lower bound: 0%");
			    txtMaxQualRate.setText("Upper bound: 0%");
			}

			/**
			 * When nothing is selected from the spinner do nothing
			 */
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {/*do nothing*/}
			
		});

		/**
		 * When the button is clicked, first check the lower and upper bounds for any unusual input from the user and 
		 * after that check if there are results between the given ranges. Finally find matced results from both lists
		 * and pass them using the intent to the next activity.
		 */
		btnResult.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

				//check the bounds and if everything is ok continue with the processing
				if (areBoundsOk()) {
					
					ArrayList<Integer> UnemploymentList = getUnemploymentList((float)(lowUnemploymentBar.getProgress()/1.0),(float)(maxUnemploymentBar.getProgress()/1.0));
					ArrayList<Integer> QualificationList = getQualificationList(QualCategorySelected,(float)(lowQualBar.getProgress()/1.0),(float)(maxQualBar.getProgress()/1.0));
					
					//if there is no empty list then continue processing...
					if (noListIsEmpty(UnemploymentList, QualificationList)) {
						ArrayList<Integer> matchedList = getMatchedList(UnemploymentList,QualificationList);				

						if (!isMatchedListEmpty(matchedList)) {
							Intent intentUnemploymentMap = new Intent(getApplicationContext(), MapBoroughResultActivity.class);
							intentUnemploymentMap.putIntegerArrayListExtra("matchedList", matchedList);
							intentUnemploymentMap.putExtra("Coloring", "Unemployment Rate");
							startActivity(intentUnemploymentMap);
						}
					}					
				}
			}	
		});
	}
	
	/**
	 * This method takes the lower bound and the upper bound for the unemployment rate and check 
	 * the borough list for results.
	 * @param low Minimum given level of unemployment
	 * @param max Maximum given level of unemployment
	 * @return Matched Boroughs' ids for Unemployment Rate in a form of a list
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
	 * This method takes the category from the spinner and the upper and lower bound for the qualification rate 
	 * and goes through the borough list to find if there are any results.
	 * @param category The item that has been selected from the user using the spinner
	 * @param low Minimum given level for qualification rate
	 * @param max Maximun given level for qualification rate
	 * @return Matched Boroughs' ids for Qualification Rate according to the given Qualification Category in a form of a list
	 */
	public ArrayList<Integer> getQualificationList(String category,float low,float max){
		
		ArrayList<Integer> tempQualificationList = new ArrayList<Integer>();
		ArrayList<Borough> tempBoroughList = BoroughStore.getBoroughs();
		
		for (int i=0;i<tempBoroughList.size();i++){
			
			float qualificationRate;
			
			if (category.equals("Degree")) {
				qualificationRate = tempBoroughList.get(i).getDegreeQual();
			}
			else if (category.equals("Higher Education")){
				qualificationRate = tempBoroughList.get(i).getHigherEdu();
			}
			else if (category.equals("GCE A Level")){
				qualificationRate = tempBoroughList.get(i).getGceAQual();
			}
			else if (category.equals("GCSE Level")){
				qualificationRate = tempBoroughList.get(i).getGcseQual();
			}
			else if (category.equals("Other Qualifications")){
				qualificationRate = tempBoroughList.get(i).getOtherQual();
			}
			else {
				qualificationRate = tempBoroughList.get(i).getNoQual();
			}
			
			
			if ((qualificationRate>= low) && (qualificationRate<=max)){
				tempQualificationList.add(tempBoroughList.get(i).getId());
			}		
		}
			
		return tempQualificationList;
	}
	
	
	/**
	 * This method takes as parameters the two lists, Unemployment and Qualification and tries
	 * to find any matching results that are found in both lists.
	 * @param UnemploymentList Contains the boroughs ids' that are result of the given range for the unemployment rate.
	 * @param QualificationList Contains the boroughs ids' that are result of the given range for the qualification rate.
	 * @return A list that contains Boroughs that are in both lists, Qualification and Unemployment list
	 */
	public ArrayList<Integer> getMatchedList(ArrayList<Integer> UnemploymentList,ArrayList<Integer> QualificationList){
		
		ArrayList<Integer> tempList = new ArrayList<Integer>();
			
		for (int i=0;i<UnemploymentList.size();i++){
			for (int j=0;j<QualificationList.size();j++){
				if (UnemploymentList.get(i)== QualificationList.get(j)){
					tempList.add(UnemploymentList.get(i));
				}
			}
			
		}
		return tempList;
	}
	/**
	 * Check if there is a case where the lower bound is greater than or equal to the upper bound of any of the
	 * seek bars in the current activity.
	 * @return <Code>True<Code> if there exists one or more, otherwise <Code>false<Code>.
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
				if (lowQualBar.getProgress() >= maxQualBar.getProgress()) {
			        initializeSeekBar_TextView(lowQualBar,maxQualBar,txtLowQualRate,txtMaxQualRate);
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
	 * This method takes the two list and check if there is an empty list and if there is one outputs
	 * an info message on the screen to inform the user.
	 * @param UnemploymentList Contains the boroughs ids' that are result of the given range for the unemployment rate.
	 * @param QualificationList Contains the boroughs ids' that are result of the given range for the qualification rate.
	 * @return <Code>True<Code> if there is no list empty, <Code>false<Code> otherwise.
	 */
	public boolean  noListIsEmpty(ArrayList<Integer> UnemploymentList,ArrayList<Integer> QualificationList){
		//check if the lists are empty and report back to the user
		if (UnemploymentList.isEmpty() && QualificationList.isEmpty()) {
			Toast.makeText(getApplicationContext()
	        		,"ERROR: Unemployment Rate and Qualification Rate... No Match Found... Change given range"
	        		, Toast.LENGTH_LONG).show();
	        initializeSeekBar_TextView(lowUnemploymentBar,maxUnemploymentBar,txtLowUnemployment,txtMaxUnemployment);
	        initializeSeekBar_TextView(lowQualBar,maxQualBar,txtLowQualRate,txtMaxQualRate);
		    return false;
		}
		else if (UnemploymentList.isEmpty()){
			Toast.makeText(getApplicationContext()
	        		,"ERROR: Unemployment Rate... No Match Found... Change given range"
	        		, Toast.LENGTH_LONG).show();
	        initializeSeekBar_TextView(lowUnemploymentBar,maxUnemploymentBar,txtLowUnemployment,txtMaxUnemployment);

		    return false;
		}
		else if (QualificationList.isEmpty()){
			Toast.makeText(getApplicationContext()
	        		,"ERROR: Qualification Rate... No Match Found... Change given range"
	        		, Toast.LENGTH_LONG).show();
	        initializeSeekBar_TextView(lowQualBar,maxQualBar,txtLowQualRate,txtMaxQualRate);

		    return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Check if the matchedList is empty and if it is then it outputs
	 * an error message on the screen to inform the user to change the bounds.
	 * @param matchedList A list that contains the results of the conjunction of the two lists(Unemployment and Qualification)
	 * @return <Code>True<Code> if the match list is empty, <Code>false<Code> otherwise.
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
	 * This method initialises (connects) the fields that declared for this class with the right widgets in the 
	 * xml file
	 */
	public void connectWidgets(){
		sprQualCat = (Spinner)findViewById(R.id.sprQualCat);
		
		lowUnemploymentBar = (SeekBar)findViewById(R.id.skbLowUnemployment);
		maxUnemploymentBar = (SeekBar)findViewById(R.id.skbMaxUnemployment);
		lowQualBar = (SeekBar)findViewById(R.id.skbLowQual);
		maxQualBar = (SeekBar)findViewById(R.id.skbMaxQual);
		
		txtLowUnemployment = (TextView)findViewById(R.id.txtLowUnemployment);
		txtMaxUnemployment = (TextView)findViewById(R.id.txtMaxUnemployment);
		txtLowQualRate = (TextView)findViewById(R.id.txtLowQual);
		txtMaxQualRate = (TextView)findViewById(R.id.txtMaxQual);
		
		btnResult = (Button)findViewById(R.id.btnResult);
	}
	/**
	 * This method is used to initialised any given seek bar and text bar with the value of 0 for seek bars
	 * and a text for the text view.
	 * @param lowBar Minimum level seek bar
	 * @param maxBar Maximum level seek bar
	 * @param txtLow TextView that points the minimum level
	 * @param txtMax TextView that points the maximum level
	 */
	public void initializeSeekBar_TextView(SeekBar lowBar,SeekBar maxBar,TextView txtLow,TextView txtMax){
	    lowBar.setProgress(0);
	    maxBar.setProgress(0);
	    txtLow.setText("Lower bound: 0%");
	    txtMax.setText("Upper bound: 0%");
		
	}
}
