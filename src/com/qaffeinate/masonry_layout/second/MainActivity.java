package com.qaffeinate.masonry_layout.second;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.qaffeinate.masonry_layout.R;

public class MainActivity extends Activity implements ScrollViewListener  {

    private LinearLayout left = null;
    private LinearLayout right = null;
	 ArrayList<String> url_array;
	    float imageWidth;
	    private Customscroll scrollView1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        float width = (this).getWindowManager().getDefaultDisplay().getWidth();
  		imageWidth = (width  / 2);

        left = (LinearLayout) findViewById(R.id.left);
        right = (LinearLayout) findViewById(R.id.right);
        scrollView1 = (Customscroll) findViewById(R.id.scroll_view);
        scrollView1.setScrollViewListener(this);
        calling();

        
    }
    public void onScrollChanged(Customscroll scrollView, int x, int y, int oldx, int oldy) {
         View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

         if (diff == 0) {
         		url_array.addAll(url_array);
        	//	additem();
        		
        }
    }
    
    public void additem(){
    	 for (int i = 0; i < url_array.size(); i++) {
             View column = getColumnView(i);
               if (i % 2 == 0) {
                 left.addView(column);
             } else {
                 right.addView(column);
             }
         }
    }
    
    
    
public void calling(){
	
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
	additem();
}






    private View getColumnView(int position) {
    	AQuery aq = new AQuery(this);	
        final ImageView img=new ImageView(MainActivity.this);
     
		aq.id(img).image("http://www.wooplr.com/serve?blob-key="+url_array.get(position), true, true, 0, 0, new BitmapAjaxCallback(){//using android query and setting progress bar also
        @Override
        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
            //img.setImageBitmap(bm);
        	float i = ((float) imageWidth) / ((float) bm.getWidth());
    		float imageHeight = i * (bm.getHeight());
    		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
    		params.height = (int) imageHeight;
    		params.width = (int) imageWidth;
    		img.setLayoutParams(params);
    		img.setImageBitmap(bm);        
        }
        
		});
        return img;

    }
    
 
}
