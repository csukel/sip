package com.example.sip;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This activity contains information of the screen called Boroughs Profile.
 * @author Loucas Stylianou
 *
 */
public class BoroughProfileActivity extends Activity {
	/**
	 * Text View displaying the name of the current borough
	 */
	private TextView boroughName;
	/**
	 * Text View displaying the unemployment rate of the current borough
	 */
	private TextView txtUnemployment;
	/**
	 * Text View displaying the crime rate of the current borough
	 */
	private TextView txtCrime;
	/**
	 * Text View displaying the average scored points for gcse exams of the current borough
	 */
	private TextView txtGcse;
	/**
	 * Text View displaying the qualification rate of the current borough for people with degree
	 */
	private TextView txtDegree;
	/**
	 * Text View displaying the qualification rate of the current borough for people with qualifications
	 * up to higher education
	 */
	private TextView txtHigherEdu;
	/**
	 * Text View displaying the qualification rate of the current borough for people with qualifications
	 * up to GCE A level
	 */
	private TextView txtGceQual;
	/**
	 * Text View displaying the qualification rate of the current borough for people with qualifications
	 * up to GCSE level
	 */
	private TextView txtGcseQual;
	/**
	 * Text View displaying the qualification rate of the current borough for people with other qualifications
	 */
	private TextView txtOtherQual;
	/**
	 * Text View displaying the qualification rate of the current borough for people with no qualifications
	 */
	private TextView txtNoQual;
	/**
	 * Image button that contains the map of the current borough
	 */
	private ImageButton imgButton;
	/**
	 * Progress bar displaying the unemployment rate of the current borough
	 */
	private ProgressBar prbUnemployment;
	/**
	 * Progress bar displaying the crime rate of the current borough
	 */
	private ProgressBar prbCrime;
	/**
	 * Progress bar displaying the average scored points for gcse exams of the current borough
	 */
	private ProgressBar prbGcseResults;
	/**
	 * Progress bar displaying the qualification rate of the current borough for people with degree
	 */
	private ProgressBar prbDegreeQual;
	/**
	 * Progress bar displaying the qualification rate of the current borough for people with qualifications
	 * up to higher education
	 */
	private ProgressBar prbHigherEdu;
	/**
	 * Progress bar displaying the qualification rate of the current borough for people with qualifications
	 * up to GCE A level
	 */
	private ProgressBar prbGceAQual;
	/**
	 * Progress bar displaying the qualification rate of the current borough for people with qualifications
	 * up to GCSE level
	 */
	private ProgressBar prbGcseQual;
	/**
	 * Progress bar displaying the qualification rate of the current borough for people with other qualifications
	 */
	private ProgressBar prbOtherQual;
	/**
	 * Progress bar displaying the qualification rate of the current borough for people with no qualifications
	 */
	private ProgressBar prbNoQual;

	/**
	 * Variable used as the index to get the Borough object from the BoroughStore class
	 */
	private int index;
	
	/**
	 * When the activity is created, get the intent from the previous activity including the extra 
	 * information that the intent carries on with it. Use this information to find the borough and fill
	 * text views and progress bars with the corresponding details.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boroughprofile_activity);
		
		Bundle intentBorough = getIntent().getExtras();
		String name = intentBorough.getString("name");
		int id = intentBorough.getInt("id");
		
		index = id-1;
		//connect widgets with xml layout
		connectWidgets();
		//set title 
		boroughName.setText(name);

		//find images resources ids
		int resourceId = getResources().getIdentifier("b" + id, "drawable", "com.example.sip");
		//set picture for imgButton according to the borough
		imgButton.setImageDrawable(getResources().getDrawable(resourceId));
		//help message
		Toast.makeText(getApplicationContext(), "Press on the image for more info", Toast.LENGTH_SHORT).show();
		
		


	}
	/**
	 * Connect widgets with xml layout (boroughprofile_activity.xml)
	 */
	private void connectWidgets() {

		boroughName = (TextView)findViewById(R.id.boroughName);
		prbUnemployment = (ProgressBar)findViewById(R.id.prbUnemployment);
		prbCrime = (ProgressBar)findViewById(R.id.prbCrime);
		prbGcseResults = (ProgressBar)findViewById(R.id.prbGcseResults);
		prbDegreeQual = (ProgressBar)findViewById(R.id.prbDegreeQual);
		prbHigherEdu = (ProgressBar)findViewById(R.id.prbHigherEdu);
		prbGceAQual = (ProgressBar)findViewById(R.id.prbGceAQual);
		prbGcseQual = (ProgressBar)findViewById(R.id.prbGcseQual);
		prbOtherQual = (ProgressBar)findViewById(R.id.prbOtherQual);
		prbNoQual = (ProgressBar)findViewById(R.id.prbNoQual);
		
		txtUnemployment = (TextView)findViewById(R.id.txtTitle);
		txtCrime = (TextView)findViewById(R.id.txtMaxUnemployment);
		txtGcse = (TextView)findViewById(R.id.txtgcse);
		txtDegree = (TextView)findViewById(R.id.txtDegree);
		txtHigherEdu = (TextView)findViewById(R.id.txtHigherEdu);
		txtGceQual = (TextView)findViewById(R.id.txtMaxQual);
		txtGcseQual = (TextView)findViewById(R.id.txtGcseQual);
		txtOtherQual = (TextView)findViewById(R.id.txtOtherQual);
		txtNoQual = (TextView)findViewById(R.id.txtNoQual);
		
		imgButton = (ImageButton)findViewById(R.id.imgBtnBorough);
		
	}

	/**
	 * When activity is on resume which means running and the image button is pressed, fill the progress bars
	 * and text views with the information of the current borough.
	 */
	public void onResume(){
		super.onResume();

		imgButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Borough tempBorough = BoroughStore.getSpecificBorough(index);
				//set values to the widgets (progress bars and textViews)
				setValues(tempBorough);
				//Do not allow to press again this button
				imgButton.setEnabled(false);
			}

			
		});
		
	}
	

	/**
	 * This method set the progress to each of the progress bar in this activity and sets the text for each 
	 * corresponding text view
	 * @param tempBorough Instance of the borough object
	 */
	private void setValues(Borough tempBorough) {
		
		prbUnemployment.setProgress((int) tempBorough.getUnemploymentRate());
		prbCrime.setProgress((int)tempBorough.getCrimeRate());
		prbGcseResults.setProgress((int) tempBorough.getGcseResults());
		prbDegreeQual.setProgress((int)tempBorough.getDegreeQual());
		prbHigherEdu.setProgress((int)tempBorough.getHigherEdu());
		prbGceAQual.setProgress((int)tempBorough.getGceAQual());
		prbGcseQual.setProgress((int)tempBorough.getGcseQual());
		prbOtherQual.setProgress((int)tempBorough.getOtherQual());
		prbNoQual.setProgress((int)tempBorough.getNoQual());

		txtUnemployment.setText(txtUnemployment.getText() + ": " + tempBorough.getUnemploymentRate() + "%" );
		txtCrime.setText(txtCrime.getText() + ": " + tempBorough.getCrimeRate() + "%");
		txtGcse.setText(txtGcse.getText() + ": " + tempBorough.getGcseResults() + "%");
		txtDegree.setText(txtDegree.getText() + ": " + tempBorough.getDegreeQual() + "%");
		txtHigherEdu.setText(txtHigherEdu.getText() + ": " + tempBorough.getHigherEdu() + "%");
		txtGceQual.setText(txtGceQual.getText() + ": " + tempBorough.getGceAQual() + "%");
		txtGcseQual.setText(txtGcseQual.getText() + ": " + tempBorough.getGcseQual() + "%");
		txtOtherQual.setText(txtOtherQual.getText() + ": " + tempBorough.getOtherQual() + "%");
		txtNoQual.setText(txtNoQual.getText() + ": " + tempBorough.getNoQual() + "%");
		
	}

}
