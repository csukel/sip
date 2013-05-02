package com.example.sip;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * This class contains information of the list that is created to support the list view for the ListResultActivity
 * class.
 * @author Loucas Stylianou
 *
 */
public class MyArrayAdapter extends ArrayAdapter<String> {
	/**
	 * The context of the adapter
	 */
	private final Context context;
	/**
	 * This array of strings will contains the boroughs names that should be outputed on the list
	 */
	private final String[] values;
 

	/**
	 * The class constructor
	 * @param context The context that is passed through the ListResultActivity
	 * @param values The borough names that should be on the list
	 */
	public MyArrayAdapter(Context context, String[] values) {
		super(context, R.layout.list_result_activity, values);
		this.context = context;
		this.values = values;
	}
 
	/**
	 * This method defines the view of the list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_result_activity, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
		
		//find the correct borough's image and set it to the image view
		for (int i=0;i<BoroughStore.getSize();i++){
			if (BoroughStore.getSpecificBorough(i).getName().equals(s)){
				int resourceId = context.getResources().getIdentifier("b" + BoroughStore.getSpecificBorough(i).getId()
						, "drawable", "com.example.sip");
				
				imageView.setImageResource(resourceId);
				float gcseResults = BoroughStore.getSpecificBorough(i).getGcseResults();
				if (gcseResults>=55 && gcseResults<=60.0 ){
					textView.setTextColor(Color.RED);
				}
				else if (gcseResults>=60.1 && gcseResults<=64 ){
					textView.setTextColor(Color.rgb(255,215,0));
				}
				else {
					textView.setTextColor(Color.rgb(34,139,34));
				}
			
			}
		}
		return rowView;
	}
}
