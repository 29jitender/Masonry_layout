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
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class MainActivity extends Activity {
	
	private ListView left_list;
	private ListView right_list;
	private List_adapter leftAdapter;
	private List_adapter rightAdapter;
	private ArrayList<String> left;
	private ArrayList<String> right;
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
		left_list.setOnScrollListener(scrollListener);

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
	
OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView v, int scrollState) {	
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
			if( (right_list.getLastVisiblePosition() == right_list.getAdapter().getCount() -1 &&
					right_list.getChildAt(right_list.getChildCount() - 1).getBottom() <= right_list.getHeight())
					||left_list.getLastVisiblePosition() == left_list.getAdapter().getCount() -1 &&
							left_list.getChildAt(left_list.getChildCount() - 1).getBottom() <= left_list.getHeight())
				{
				Log.i("its the end","hola");
				
			    left.addAll(left);
				right.addAll(right);
 				leftAdapter.notifyDataSetChanged();		
  				rightAdapter.notifyDataSetChanged();		

  					}
				
				 
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
		  left = new ArrayList<String>();
		 right = new ArrayList<String>();
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