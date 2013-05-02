package com.example.sip;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This activity is responsible for the screen showing results which are schools in a form of list.
 * This list is in descending order according to the gcse resuls. Also on the left of each of the element
 * of the list there is a check box that gives the ability to the user to check the schools that wants to compare
 * on the map.
 * @author Loucas Stylianou
 *
 */
public class SchoolListActivity extends Activity {
	/**
	 * This field creates a new custom adapter for the list
	 */
	 MyCustomAdapter dataAdapter = null;
	 /**
	  * A list of schools' ids
	  */
	 private ArrayList<Integer> schools;
	 /**
	  * A list of school instances
	  */
	 private ArrayList<School> schoolList = new ArrayList<School>();
	 
	 /**
	  * When the activity is created, it gets the data passed on the intent and sets the view to connect with 
	  * the school_list_main.xml file. Also defines what to do when a check box is clicked and initialises the 
	  * adapter in order to get the list view.
	  */
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.school_list_main);
	 
	  Bundle intent = getIntent().getExtras();
	  schools = intent.getIntegerArrayList("Schools");
	  
	  //Generate list View from ArrayList
	  displayListView();
	 
	  checkButtonClick();
	 
	 }
	 /**
	  * When the back button is pressed set all schools boolean variable for selection to false and terminate the 
	  * activity.
	  */
	 @Override
	 public void onBackPressed() {
		 for (int i=0;i<schoolList.size();i++){
			 schoolList.get(i).setSelected(false);
		 }
		 finish();
		 
	 }
	 /**
	  * Copy schools to the array list of schools and create the array adapter. Initialise the list view with
	  * the created adapter. Also when an item of the list is selected output a message showing the school name and 
	  * the gcse results.
	  */
	 private void displayListView() {
		 
		 for (int i=0;i<schools.size();i++){
			 schoolList.add(SchoolStore.getSpecificSchool(schools.get(i)-1));
		 }

		 
	 
	  //create an ArrayAdaptar from the String Array
	  dataAdapter = new MyCustomAdapter(this,
	    R.layout.school_list_info, schoolList);
	  ListView listView = (ListView) findViewById(R.id.listView1);
	  // Assign adapter to ListView
	  listView.setAdapter(dataAdapter);
	 
	  listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		    
		    // When clicked, show a toast with the TextView text
		    School school = (School) parent.getItemAtPosition(position);
		    Toast.makeText(getApplicationContext(),
		      school.getName() + "\nGCSE Results: " + school.getGcseResults() + "%", 
		      Toast.LENGTH_SHORT).show();
			
		}
	  });
	 
	 }
	 
	 /**
	  * This class contains information of the customised adapter needed to create the list view for 
	  * the SchoolListActivity
	  * @author Loucas Stylianou
	  *
	  */
	 private class MyCustomAdapter extends ArrayAdapter<School> {
	 
		/**
		 * An array list of schools 
		 */
	  private ArrayList<School> schoolList=null;
	 
	  /**
	   * Class constructor
	   * @param context The context of this activity
	   * @param textViewResourceId The id of the textview to connect it with the one in the xml file
	   * @param SchoolList The list of schools
	   */
	  public MyCustomAdapter(Context context, int textViewResourceId, 
	    ArrayList<School> SchoolList) {
	   super(context, textViewResourceId, SchoolList);
	   this.schoolList = new ArrayList<School>();
	   this.schoolList= SchoolList;
	  }
	 
	  /**
	   * A class used to create the view
	   * @author Loucas Stylianou
	   *
	   */
	  private class ViewHolder {
		  /**
		   * Text View
		   */
	   TextView code;
	   /**
	    * Check box
	    */
	   CheckBox name;
	  }
	 
	  /**
	   * Construct the view for the list view. 
	   */
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	   ViewHolder holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	 
	   if (convertView == null) {
	   LayoutInflater vi = (LayoutInflater)getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.school_list_info, null);
	 
	   holder = new ViewHolder();
	   holder.code = (TextView) convertView.findViewById(R.id.code);
	   holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
	   convertView.setTag(holder);
	 
	    holder.name.setOnClickListener( new View.OnClickListener() {  
	     public void onClick(View v) {  
	      CheckBox cb = (CheckBox) v ;  
	      School school = (School) cb.getTag();  
	      school.setSelected(cb.isChecked());
	     }  
	    });  
	   } 
	   else {
	    holder = (ViewHolder) convertView.getTag();
	   }
	 
	   School school = schoolList.get(position);
	   holder.code.setText(school.getName());
	   //holder.name.setText(school.getName());
	   holder.name.setChecked(school.isSelected());
	   holder.name.setTag(school);
	 
	   return convertView;
	 
	  }
	 
	 }
	 /**
	  * When the button to compare chosen items on the map is selected then finds which items are selected,
	  * stores the schools ids in an arraylist of integers and pass the list to the next activity using the intent.
	  * The next activity is the MapSchoolResultActivity. 
	  */
	 private void checkButtonClick() {
	 
	 
	  Button myButton = (Button) findViewById(R.id.findSelected);
	  myButton.setOnClickListener(new OnClickListener() {
	 
	   @Override
	   public void onClick(View v) {
		   ArrayList<School> schoolList = dataAdapter.schoolList;
		   ArrayList<Integer> tempList = new ArrayList<Integer>();
		   for(int i=0;i<schoolList.size();i++){
			   if(schoolList.get(i).isSelected()){
			      tempList.add(schoolList.get(i).getSchoolId());
			      
			   }
		   }
		   if (!tempList.isEmpty()){
			   Intent intent = new Intent(getApplicationContext(),MapSchoolResultActivity.class);
			   intent.putIntegerArrayListExtra("schoolList", tempList);
			   startActivity(intent);
		   }
		   else {
			    Toast.makeText(getApplicationContext(),
					      "No School Selected!", 
					      Toast.LENGTH_SHORT).show();
		   }
	 
	   }
	  });
	 
	 }
	 
}
