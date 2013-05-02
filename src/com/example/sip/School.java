package com.example.sip;
/**
 * School is an object containing information for a specific school in London..There exists 714 schools in the
 * database hence during execution there will be 714 instances of the school object.
 * @author Loucas Stylianou
 *
 */
public class School {
	/**
	 * School's name
	 */
	private String name;
	/**
	 * Borough's Id where the school exists. Used in order to establis the relationship between schools and boroughs
	 */
	private int boroughId;
	/**
	 * Schools' id which is used for ease of access of the instances of the school object
	 */
	private int schoolId;
	/**
	 * School's average points achieved by its students in GCSE exams
	 */
	private float gcseResults;
	/**
	 * School's coordinate latitude
	 */
	private float Latitude;
	/**
	 * School's coordinate longitude
	 */
	private float Longitude;
	/**
	 * Number of eligible students of the current school that were registered to take the gcse exams
	 */
	private int numOfEligibleStudents;
	/**
	 * Variable to check whether the current school is selected or not by the user in the SchoolListActivity
	 */
	private boolean selected = false;
	
	/**
	 * School constructor
	 * @param id School id is used for easy reference to each school
	 * @param bid Borough id is used to connect school and borough relation 
	 * @param Name School name
	 * @param results Average school results on GCSE exams
	 * @param Lat School coordinate: Latitude
	 * @param Long School coordinate: Longitude
	 * @param numStudents Number of eligible students
	 */
	public School(int id,int bid,String Name,float results,float Lat,float Long,int numStudents){
		
		this.schoolId = id;
		this.boroughId = bid;
		this.name = Name;
		this.gcseResults = results;
		this.Latitude = Lat;
		this.Longitude = Long;
		this.numOfEligibleStudents = numStudents;
		
	}
	/**
	 * Retrieve the name of the school
	 * @return School name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Retrieve the id of the school
	 * @return School id
	 */
	public int getSchoolId(){
		return schoolId;
	}
	
	/**
	 * Retrieve the borouhg's id where the school belongs to 
	 * @return Borough Id
	 */
	public int getBoroughId(){
		return boroughId;
	}
	
	/**
	 * Retrieve the average school points achieved in GCSE exams
	 * @return Gcse average points for the school 
	 */
	public float getGcseResults(){
		return gcseResults;
	}
	
	/**
	 * Retrieve the school coordinate of latitude
	 * @return School coordinate: Latitude
	 */
	public float getLat(){
		return Latitude;
	}
	
	/**
	 * Retrieve the school coordinate of longitude
	 * @return School coordinate: Longitude
	 */
	public float getLong(){
		return Longitude;
	}
	
	/**
	 * Retrieve the number of students that have been eligible to take the GCSE exams
	 * @return The number of eligible student in the school
	 */
	public int getNumOfStudents(){
		return numOfEligibleStudents;
	}
	
	/**
	 * This method is used in the school list activity in order to keep track which schools are marked by the user
	 * @return True if the school is selected otherwise false 
	 */
	 public boolean isSelected() {
		  return selected;
		 }
		 public void setSelected(boolean selected) {
		  this.selected = selected;
		 }

}
