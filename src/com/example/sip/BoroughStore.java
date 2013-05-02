package com.example.sip;


import java.util.ArrayList;


/**
 * Borough Store is an object where all the instances of boroughs are stored in an array list.
 * @author Loucas Stylianou
 *
 */
public class BoroughStore  {


	static ArrayList<Borough> boroughs;
	/**
	 * BoroughStore constructor. Initialises the array list of boroughs.
	 */
	public BoroughStore(){
		boroughs= new ArrayList<Borough>();
	}
	/**
	 * Adds a list of boroughs to the array list after the data has been retrieved from the sql database
	 * @param b ArrayList of boroughs
	 */
	public static void addBorough(ArrayList<Borough> b){
		boroughs=b;
	}
	
	/**
	 * Accessing a borough using the index which can be described also as the borough's id minus one
	 * @param index Pointer of the location of a borough in the array list
	 * @return A borough
	 */
	public static Borough getSpecificBorough(int index){
		return boroughs.get(index);
	}
	/**
	 * Retrieve the list of all boroughs
	 * @return Boroughs array list
	 */
	public static ArrayList<Borough> getBoroughs(){
		return boroughs;
	}
	/**
	 * Retrieve the size of the array list of boroughs
	 * @return Size of the list
	 */
	public static int getSize(){
		return boroughs.size();
	}

}
