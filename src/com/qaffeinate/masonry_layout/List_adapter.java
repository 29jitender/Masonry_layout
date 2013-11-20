package com.qaffeinate.masonry_layout;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

public class List_adapter extends ArrayAdapter<String>{

    Context context; 
    LayoutInflater inflater;
    int layoutResourceId;
    float imageWidth;
    
    public List_adapter(Context context, int layoutResourceId, ArrayList<String> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        
        float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int)dpTopx(10f, (Activity)context);
 		imageWidth = ((width - (3 * margin)) / 2);
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout row = (FrameLayout) convertView;
        ItemHolder holder;
        String item = getItem(position);
        
		if (row == null) {
			holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (FrameLayout) inflater.inflate(layoutResourceId, parent, false);
            ImageView itemImage = (ImageView)row.findViewById(R.id.item_image);
			holder.itemImage = itemImage;
		} else {
			holder = (ItemHolder) row.getTag();
		}
		
		row.setTag(holder);
 		AQuery aq = new AQuery(row);			 
			aq.id(holder.itemImage).progress(R.id.progress).image("http://www.wooplr.com/serve?blob-key="+item, true, true, 0, 0, new BitmapAjaxCallback(){//using android query and setting progress bar also
	        @Override
	        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
	        	  Log.i("status ",status.getMessage()+"");
 
 	    	  setimage(bm, iv);

	        
	        }
	        
	});
		
        return row;
    }

    public static class ItemHolder
    {
    	ImageView itemImage;
    }
	
 	private void setimage(Bitmap bitmap, ImageView imageView){
		
 		float i = ((float) imageWidth) / ((float) bitmap.getWidth());
		float imageHeight = i * (bitmap.getHeight());
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
		params.height = (int) imageHeight;
		params.width = (int) imageWidth;
		imageView.setLayoutParams(params);
		imageView.setImageBitmap(bitmap);
	}
	
	public static float dpTopx(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi/160f);
	    return px;
	}

}