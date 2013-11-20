package com.qaffeinate.masonry_layout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView left_list;
	private ListView right_list;
	private List_adapter leftAdapter;
	private List_adapter rightAdapter;

	int[] leftViewsHeights;
	int[] rightViewsHeights;
	 ArrayList<String> url_array;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	

		left_list = (ListView) findViewById(R.id.left_list);
		right_list = (ListView) findViewById(R.id.right_list);		
		json_stuff();				
		load_cdn();		
		left_list.setOnTouchListener(touchListener);
		right_list.setOnTouchListener(touchListener);		
  	}
	
	// same scroll on both list
	OnTouchListener touchListener = new OnTouchListener() {					
		boolean moved = false;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.equals(left_list) && !moved) {
				moved = true;
				right_list.dispatchTouchEvent(event);
			} else if (v.equals(right_list) && !moved) {
				moved = true;
				left_list.dispatchTouchEvent(event);
			}
			
			moved = false;
			return false;
		}
	};
	
 

	private void json_stuff(){
		url_array= new ArrayList<String>();

		try {
			InputStream is = this.getAssets().open("Sample_JSON.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String bufferString = new String(buffer);
 			
 			JSONObject jsonobject = new JSONObject(bufferString);
 			
 			JSONArray array1= jsonobject.getJSONArray("genericStreamEntry");
			for(int i=0;i<array1.length();i++){
	 			JSONObject obj1= array1.getJSONObject(i);
	 			JSONObject obj2 =obj1.getJSONObject("productReviewDetails");
	 			JSONObject obj3 =obj2.getJSONObject("productDetails");
	 			JSONObject obj4 =obj3.getJSONObject("imageDetails");
	 			String key =obj4.getString("cdnKeyWebList");
	 			url_array.add(key);

			}
 			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  	}
	private void load_cdn(){
		ArrayList<String> left = new ArrayList<String>();
		ArrayList<String> right = new ArrayList<String>();
		 int count = 0;
		 Boolean check=false;
	        while (count < url_array.size()) {
	        	if(check){
	        		right.add(url_array.get(count));
	        		check=false;
	        	}
	        	else{
	        		left.add(url_array.get(count));
	        		check=true;
	        	}
	        		        	
 	            count++;
	        }
 
	 
 
		
		leftAdapter = new List_adapter(this, R.layout.item, left);
		rightAdapter = new List_adapter(this, R.layout.item, right);
		left_list.setAdapter(leftAdapter);
		right_list.setAdapter(rightAdapter);
		
 	}


}