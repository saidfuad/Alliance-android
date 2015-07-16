package com.bensoft.utils;

/**
 * Created by Benson on 3/16/2015.
 */
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.bensoft.main.R;


public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);

            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Log.d("Height:", "" + height);
            Log.d("Width:", "" + width);
            
            imageView.setLayoutParams(new GridView.LayoutParams((width-32)/3, (width-32)/3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array to be loaded in gridview
    public Integer[] mThumbIds = {
        R.drawable.news,R.drawable.sign_doc,
        R.drawable.mapping,R.drawable.inputs,
        R.drawable.train,R.drawable.train_mat,
        R.drawable.calender,R.drawable.forms,
        R.drawable.soda,R.drawable.update_area,
        R.drawable.settings
    };
}
