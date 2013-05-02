package com.example.sip;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * This activity contains information for the screen where the borough results are shown on a list
 * in scholarships function.
 * @author Loucas Stylianou
 *
 */
public class ListResultActivity extends ListActivity {
	/**
	 * Retrieve the list with boroughs ids passed using intent by the previous activity
	 */
	static int[] boroughsIds=null;
	/**
	 * Used to store boroughs names that their ids are stored in the boroughsIds list
	 */
	static String[] boroughs = null;
	
	/**
	 * When activity is created get the intent passed by the previous activity and the extras stored on this 
	 * intent and store them. Then set the list adapter with the string array to fill the list with the results.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		Bundle intentBorough = getIntent().getExtras();
		boroughsIds = intentBorough.getIntArray("sortedList");
		boroughs = new String[boroughsIds.length]; 
		
		for (int i=0;i<boroughs.length;i++){
			boroughs[i] = BoroughStore.getSpecificBorough(boroughsIds[i]-1).getName();
		}
		
		setListAdapter(new MyArrayAdapter(this, boroughs));
		
	}

	/**
	 * When an item of the list is selected switch to Borough Profile activity
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		
		Intent intentProfile = new Intent(getApplicationContext(),BoroughProfileActivity.class);
		intentProfile.putExtra("name", selectedValue);
		int bId = 0;
		//get the Borough's id according to the name of the item that has been selected
		for (int i=0;i<BoroughStore.getSize();i++){
			if (BoroughStore.getSpecificBorough(i).getName().equals(selectedValue)){
				bId = BoroughStore.getSpecificBorough(i).getId();
				break;
			}
		}
		intentProfile.putExtra("id", bId);
		startActivity(intentProfile);
 
	}

}


