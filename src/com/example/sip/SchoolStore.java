package com.example.sip;

import java.util.ArrayList;
/**
 * SchoolStore is an object containing an array list of all the schools used in the program
 * @author Loucas Stylianou
 *
 */
public class SchoolStore {
	
	static ArrayList<School> schoolsArray;
	/**
	 * SchoolStore constructor. Initialises the array list of schools.
	 */
	public SchoolStore(){
		schoolsArray = new ArrayList<School>();
	}
	/**
	 * Given an array list of schools stores it in the array list of schools of this class for future use
	 * @param tempSchoolArray Array list of schools taken during the reading of the sql database.
	 */
	public static void addSchools(ArrayList<School> tempSchoolArray){
		schoolsArray = tempSchoolArray;
	}
	/**
	 * Access a specific school in the list using the index which also can be expressed as school's id minus one
	 * @param index Pointer of the location of a school in the list
	 * @return A specific school
	 */
	public static School getSpecificSchool(int index){
		return schoolsArray.get(index);
	}
	/**
	 * Retrieve all the schools that are stored in the list of this class
	 * @return A list of all schools
	 */
	public static ArrayList<School> getSchools(){
		return schoolsArray;
	}
	/**
	 * Retrieve the size of the list
	 * @return Size of the array list 
	 */
	public static int getSize(){
		return schoolsArray.size();
	}

}
