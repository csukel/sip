package com.example.sip;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.maps.model.LatLng;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * Welcome Activity is the first screen that user can see during the execution of the program. 
 * During this activity the reading of the data that are stored in the sql database is taken place 
 * and also a nice 3d cube is appeared on the screen with the logo of the program.
 * @author Loucas Stylianou
 *
 */
public class WelcomeActivity extends Activity {

	/**
	 * An array list that its purpose is to store all the boroughs when they are retrieved from the database
	 */
    private ArrayList<Borough> boroughArray = new ArrayList<Borough>();
    /**
     * An array list that its purpose is to store all the schools when they are retrieved from the database
     */
    private ArrayList<School> schoolArray = new ArrayList<School>();
    
	   GLView view;
	   
	   /**
	    * When the activity is created set the content on the screen and read all data needed from the database.
	    * The view of this activity is a 3D cube with the logo of the program on it.
	    * When the user tap on the screen then the activity is terminated and the MainActivity appears.
	    */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      view = new GLView(this);
	      setContentView(view);
	      
			//create an instance of the sql existing database
		    MyDatabase myDatabase = new MyDatabase(this);

		    //read all boroughs' data
		    Cursor cur = null;//declare a cursor
		    try {
		    cur = myDatabase.getAllBoroughs();//retrieve query with all boroughs' data
		    }catch (Exception e){
		    	Log.e("Exception","Error while getting cursor " + e.getMessage());
		    }

		    //keep reading data until there is no more row to read
		    do {
		    	int id = cur.getInt(0);
		    	String name = cur.getString(1);
		    	float Lat = cur.getFloat(2);
		    	float Long = cur.getFloat(3);
		    	float crimeRate = cur.getFloat(4)/10;
		    	float unemploymentRate = cur.getFloat(5);
		    	float gcseResults = cur.getFloat(6);
		    	float degreeQual = cur.getFloat(7);
		    	float higherEdu = cur.getFloat(8);
		    	float gceAQual = cur.getFloat(9);
		    	float gcseQual = cur.getFloat(10);
		    	float otherQual = cur.getFloat(11);
		    	float noQual = cur.getFloat(12);
		    	
		    	//store data on a temporary borough object
		    	Borough tempBorough = new Borough(id,name,Lat,Long,crimeRate,unemploymentRate,gcseResults,degreeQual
		    			,higherEdu,gceAQual,gcseQual,otherQual,noQual);
		    	//define a list of latitude longitude objects
		    	List<LatLng> boundaries = new ArrayList<LatLng>();
		    	Cursor cursor = null;//define a new cursor
			    try {
			    cursor = myDatabase.getBoundaries(tempBorough.getId());//get query
			    }catch (Exception e){
			    	Log.e("Exception","Error while getting cursor " + e.getMessage());
			    }
			    //keep reading data until there are no more rows to read 
			    do {
					float Latitude = cursor.getFloat(1);
					float Longitude = cursor.getFloat(2);
			    	LatLng tempCoordinates = new LatLng(Latitude,Longitude);//store latitude, longitude  
			    	boundaries.add(tempCoordinates);//add the object lat,long to the list
			    }while(cursor.moveToNext());
			    cursor.close();//close cursor
			    tempBorough.setBoundaries(boundaries);//add list object to the temporary borough object
		    	boroughArray.add(tempBorough);//add object to the temporary array of boroughs
		    	
		    }while(cur.moveToNext());
		    BoroughStore.addBorough(boroughArray);//add the temporary array of boroughs to the BoroughStore
		    boroughArray = null;
		    
		    /*----------------------------------------------------------------------------------------------------------------*/	    
		    //read all schools' data
		    try {
		    cur = myDatabase.getAllSchools();
		    }catch (Exception e){
		    	Log.e("Exception","Error while getting cursor " + e.getMessage());
		    }
		     
		    do {
		    	int id = cur.getInt(0);
		    	int bid = cur.getInt(1);
		    	String name = cur.getString(2);
		    	float gcseResults = cur.getFloat(3);
		    	float Lat = cur.getFloat(4);
		    	float Long = cur.getFloat(5);
		    	int num_of_students = cur.getInt(6);

		    	School tempSchool = new School(id,bid,name,gcseResults,Lat,Long,num_of_students);
		    	schoolArray.add(tempSchool);
		    	
		    }while(cur.moveToNext());
		    SchoolStore.addSchools(schoolArray);
		    
		    
		    
		    /*----------------------------------------------------------------------------------------------------------------*/	        
		    cur.close();
		    myDatabase.close(); //close database 
		    
		    view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(intent);
					
				}
		    	
		    });
	   }

	   /**
	    * Override onPause and also put onPause the view of this activity
	    */
	   @Override
	   protected void onPause() {
	       super.onPause();
	       view.onPause();
	   }

	   /**
	    * Override onResume and also put onResume the view of this activity
	    */
	   @Override
	   protected void onResume() {
	       super.onResume();
	       view.onResume();

	   }
}

/**
 * This class is responsible for the view of the WelcomeActivity
 * @author Loucas Stylianou
 *
 */
class GLView extends GLSurfaceView {
	   private final GLRenderer renderer;

	   GLView(Context context) {
	      super(context);
	      renderer = new GLRenderer(context);
	      setRenderer(renderer);
	   }
}

/**
 * This class initialises the 3d cube, the start time and the frames for the animation that is used for the 
 * view of the WelcomeActivity.
 * @author Loucas Stylianou
 *
 */
class GLRenderer implements GLSurfaceView.Renderer {
	   private static final String TAG = "GLRenderer";
	   private final Context context;
	   
	   
	   private final GLCube cube = new GLCube();
	   
	   
	   private long startTime;
	   private long fpsStartTime;
	   private long numFrames;
	   

	   
	   GLRenderer(Context context) {
	      this.context = context;
	   }

	   
	   
	   public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	      
	      // ...
	      
	      
	      
	      boolean SEE_THRU = true;
	      
	      
	      startTime = System.currentTimeMillis();
	      fpsStartTime = startTime;
	      numFrames = 0;
	      

	      // Define the lighting
	      
	      float lightAmbient[] = new float[] { 0.2f, 0.2f, 0.2f, 1 };
	      float lightDiffuse[] = new float[] { 1, 1, 1, 1 };
	      float[] lightPos = new float[] { 1, 1, 1, 1 };
	      gl.glEnable(GL10.GL_LIGHTING);
	      gl.glEnable(GL10.GL_LIGHT0);
	      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
	      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
	      gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
	      

	      // What is the cube made of?
	      
	      float matAmbient[] = new float[] { 1, 1, 1, 1 };
	      float matDiffuse[] = new float[] { 1, 1, 1, 1 };
	      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
	            matAmbient, 0);
	      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
	            matDiffuse, 0);
	      

	      
	      // Set up any OpenGL options we need
	      gl.glEnable(GL10.GL_DEPTH_TEST); 
	      gl.glDepthFunc(GL10.GL_LEQUAL);
	      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	      // Optional: disable dither to boost performance
	      // gl.glDisable(GL10.GL_DITHER);
	      

	      
	      // ...
	      if (SEE_THRU) {
	         gl.glDisable(GL10.GL_DEPTH_TEST);
	         gl.glEnable(GL10.GL_BLEND);
	         gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
	      }
	      
	      
	      // Enable textures
	      gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	      gl.glEnable(GL10.GL_TEXTURE_2D);

	      // Load the cube's texture from a bitmap
	      GLCube.loadTexture(gl, context, R.drawable.logosip);
	      
	      
	      
	      
	   }
	   
	   

	   
	   public void onSurfaceChanged(GL10 gl, int width, int height) {
	      
	      // ...
	      
	      
	      // Define the view frustum
	      gl.glViewport(0, 0, width, height);
	      gl.glMatrixMode(GL10.GL_PROJECTION);
	      gl.glLoadIdentity();
	      float ratio = (float) width / height;
	      GLU.gluPerspective(gl, 45.0f, ratio, 1, 100f); 
	      
	   }
	   

	   
	   
	   
	   public void onDrawFrame(GL10 gl) {
	      
	      // ...
	      
	      
	      
	      
	      // Clear the screen to black
	      gl.glClear(GL10.GL_COLOR_BUFFER_BIT
	            | GL10.GL_DEPTH_BUFFER_BIT);

	      // Position model so we can see it
	      gl.glMatrixMode(GL10.GL_MODELVIEW);
	      gl.glLoadIdentity();
	      gl.glTranslatef(0, 0, -3.0f);

	      // Other drawing commands go here...
	      
	      
	      // Set rotation angle based on the time
	      long elapsed = System.currentTimeMillis() - startTime;
	      gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
	      gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);

	      
	      // Draw the model
	      cube.draw(gl);
	      
	      
	      // Keep track of number of frames drawn
	      
	      numFrames++;
	      long fpsElapsed = System.currentTimeMillis() - fpsStartTime;
	      if (fpsElapsed > 5 * 1000) { // every 5 seconds
	         float fps = (numFrames * 1000.0F) / fpsElapsed;
	         Log.d(TAG, "Frames per second: " + fps + " (" + numFrames
	               + " frames in " + fpsElapsed + " ms)");
	         fpsStartTime = System.currentTimeMillis();
	         numFrames = 0;
	      }
	      
	      
	      
	      
	      
	   }
	   
	   
	   
	}

/**
 * This class creates the 3d cube used for the view of the Welcome Activity
 * @author Loucas Stylianou
 *
 */
class GLCube {
	   private final IntBuffer mVertexBuffer;
	   
	   
	   private final IntBuffer mTextureBuffer;

	   /**
	    * Class constructor. Draws the cube.
	    */
	   public GLCube() {
	      
	      int one = 65536;
	      int half = one / 2;
	      int vertices[] = { 
	            // FRONT
	            -half, -half, half, half, -half, half,
	            -half, half, half, half, half, half,
	            // BACK
	            -half, -half, -half, -half, half, -half,
	            half, -half, -half, half, half, -half,
	            // LEFT
	            -half, -half, half, -half, half, half,
	            -half, -half, -half, -half, half, -half,
	            // RIGHT
	            half, -half, -half, half, half, -half,
	            half, -half, half, half, half, half,
	            // TOP
	            -half, half, half, half, half, half,
	            -half, half, -half, half, half, -half,
	            // BOTTOM
	            -half, -half, half, -half, -half, -half,
	            half, -half, half, half, -half, -half, };
	      
	      
	      int texCoords[] = {
	            // FRONT
	            0, one, one, one, 0, 0, one, 0,
	            // BACK
	            one, one, one, 0, 0, one, 0, 0,
	            // LEFT
	            one, one, one, 0, 0, one, 0, 0,
	            // RIGHT
	            one, one, one, 0, 0, one, 0, 0,
	            // TOP
	            one, 0, 0, 0, one, one, 0, one,
	            // BOTTOM
	            0, 0, 0, one, one, 0, one, one, };
	      

	      
	      // Buffers to be passed to gl*Pointer() functions must be
	      // direct, i.e., they must be placed on the native heap
	      // where the garbage collector cannot move them.
	      //
	      // Buffers with multi-byte data types (e.g., short, int,
	      // float) must have their byte order set to native order
	      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	      vbb.order(ByteOrder.nativeOrder());
	      mVertexBuffer = vbb.asIntBuffer();
	      mVertexBuffer.put(vertices);
	      mVertexBuffer.position(0);
	      

	      
	      // ...
	      ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
	      tbb.order(ByteOrder.nativeOrder());
	      mTextureBuffer = tbb.asIntBuffer();
	      mTextureBuffer.put(texCoords);
	      mTextureBuffer.position(0);
	      
	   }
	   
	   /**
	    * Draw the cube
	    * @param gl
	    */
	   public void draw(GL10 gl) { 
	      gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
	      
	      
	      gl.glEnable(GL10.GL_TEXTURE_2D); // workaround bug 3623
	      gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);
	      
	      

	      gl.glColor4f(1, 1, 1, 1);
	      gl.glNormal3f(0, 0, 1);
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	      gl.glNormal3f(0, 0, -1);
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

	      gl.glColor4f(1, 1, 1, 1);
	      gl.glNormal3f(-1, 0, 0);
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
	      gl.glNormal3f(1, 0, 0);
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

	      gl.glColor4f(1, 1, 1, 1);
	      gl.glNormal3f(0, 1, 0);
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
	      gl.glNormal3f(0, -1, 0);
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
	   }
	   
	   /**
	    * This method loads the texture to the cube
	    * @param gl
	    * @param context Get the WelcomeActivity context
	    * @param resource Image of the program logo
	    */
	   static void loadTexture(GL10 gl, Context context, int resource) {
	      Bitmap bmp = BitmapFactory.decodeResource(
	            context.getResources(), resource);
	      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
	            GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
	      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
	            GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	      bmp.recycle();
	   }
	   
	}
