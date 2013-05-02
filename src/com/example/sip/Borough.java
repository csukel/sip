package com.example.sip;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;

/**
 * Borough is an object containing information for one of the thirty two boroughs of London....In total thirty two instances of a Borough are going to be used in the execution of the program.
 * 
 * @author Loucas Stylianou
 * 
 * 
 */
public class Borough {
	/**
	 * Borough Id. A field created for ease of accessing instances of borough object.
	 */
	private int id;
	/**
	 * Borough's name
	 */
	private String name;
	/**
	 * Borough's coordinate latitude
	 */
	private float Latitude;
	/**
	 * Borough's coordinate longitude
	 */
	private float Longitude;
	/**
	 * Borough's crime rate
	 */
	private float crimeRate;
	/**
	 * Borough's unemployment rate
	 */
	private float unemploymentRate;
	/**
	 * Borough's average points scored in gcse exams
	 */
	private float gcseResults;
	/**
	 * Borough's qualification rate for people having degree as their top qualification
	 */
	private float degreeQual;
	/**
	 * Borough's qualification rate for people with qualification up to higher education level
	 */
	private float higherEdu;
	/**
	 * Borough's qualification rate for people with qualification up to the GCE A level
	 */
	private float gceAQual;
	/**
	 * Borough's qualification rate for people with qualification up to the GCSE level
	 */
	private float gcseQual;
	/**
	 * Borough's qualification rate for people with other qualifications
	 */
	private float otherQual;
	/**
	 * Borough's qualification rate for people with no qualifications
	 */
	private float noQual;
	/**
	 * A list of coordinates used to draw polygons (Boroughs' boundaries) on a google map object
	 */
	private List<LatLng> boundaries;
	
	/**
	 * Borough constructor
	 * @param ID Borough Id for easy access
	 * @param Name Borough's name
	 * @param Lat Borough's coordinate:Latitude
	 * @param Long Borough's coordinate:Longitude
	 * @param crime Borough's crime rate
	 * @param unemployment Borough's unemployment rate
	 * @param gcse Borough's average score for gcse exams
	 * @param degree Borough's qualification rate for people with a degree as qualification
	 * @param higherE Borough's qualification rate for people with higher education as qualification
	 * @param gceAQ Borough's qualification rate for people with gce A level as qualifications 
	 * @param gcseQ Borough's qualification rate for people with gcse level as qualifications
	 * @param otherQ Borough's qualification rate for people with other qualifications than the above
	 * @param noQ Borough's qualification rate for people with no qualifications
	 */
	public Borough(int ID,String Name,float Lat,float Long,float crime,float unemployment,float gcse,float degree,
			float higherE,float gceAQ,float gcseQ,float otherQ,float noQ){
		
		this.id=ID;
		this.name=Name;
		this.Latitude=Lat;
		this.Longitude=Long;
		this.crimeRate = crime;
		this.unemploymentRate = unemployment;
		this.gcseResults = gcse;
		this.degreeQual = degree;
		this.higherEdu = higherE;
		this.gceAQual = gceAQ;
		this.gcseQual = gcseQ;
		this.otherQual = otherQ;
		this.noQual = noQ;
	}
	
	/**
	 * Retrieve borough's id 
	 * @return id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Retrieve borough's name
	 * @return Borough name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Retrieve borough's latitude
	 * @return Latitude
	 */
	public float getLat(){
		return Latitude;
	}
	
	/**
	 * Retrieve borough's longitude
	 * @return Longitude
	 */
	public float getLong(){
		return Longitude;
	}
	
	/**
	 * Retrieve borough's crime rate
	 * @return Crime rate
	 */
	public float getCrimeRate(){
		return crimeRate;
	}
	
	/**
	 * Retrieve borough's unemployment rate
	 * @return Unemployment Rate
	 */
	public float getUnemploymentRate(){
		return unemploymentRate;
	}
	
	/**
	 * Retrieve borough's gcse results average score
	 * @return Gcse Results
	 */
	public float getGcseResults(){
		return gcseResults;
	}
	
	/**
	 * Retrieve the qualification rate for people with degree qualifications
	 * @return Degree Qualification Rate
	 */
	public float getDegreeQual(){
		return degreeQual;
	}
	
	/**
	 * Retrieve the qualification rate for people with higher education qualifications
	 * @return Higher Education qualification rate
	 */
	public float getHigherEdu(){
		return higherEdu;
	}

	/**
	 * Retrieve the qualification rate for people with gce A level qualification
	 * @return GCE A Level Qualification Rate
	 */
	public float getGceAQual(){
		return gceAQual;
	}
	
	/**
	 * Retrieve the qualification rate for people with gcse qualification
	 * @return GCSE Level Qualification Rate
	 */
	public float getGcseQual(){
		return gcseQual;
	}
	
	/**
	 * Retrieve the qualification rate for people having other qualifications than the above
	 * @return Other Qualifications Rate
	 */
	public float getOtherQual(){
		return otherQual;
	}
	
	/**
	 * Retrieve the qualification rate for people having no qualifications
	 * @return No Qualifications Rate
	 */
	public float getNoQual(){
		return noQual;
	}
	
	/**
	 * Store the list of coordinates that are used to draw boundaries for each Borough
	 * @param b A list of coordinates
	 */
	public void setBoundaries(List<LatLng> b){
		this.boundaries = b;
	}
	
	/**
	 * Retrieve the list of the Borough's boundaries (list of coordinates)
	 * @return List of coordinates
	 */
	public List<LatLng> getBoundaries(){
		return boundaries;
	}
}
