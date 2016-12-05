package com.example.ashutosh.customadaptertest;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;

    int[] image={R.drawable.ecotourism1,R.drawable.hebbe2,R.drawable.historical1,R.drawable.historical2,R.drawable.lalbag1,
            R.drawable.lalbag2,R.drawable.lalbag3,R.drawable.park1,R.drawable.park2,R.drawable.park3};

    /*
    * store all the data somewhere {garbage name,garbage price,img}
    * define the structure of singme row of your listview inside row.xml,
    * seprate layout file
    * create your custom adapter that  puts the data for each row inside getview
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list_view);


        list.setAdapter(new MyAdapter(this));

    }


    class SingleRow {
        String garbageName;
        String garbagePrice;
        int    image;

        public SingleRow(String garbageName, String garbagePrice, int image) {
            this.garbageName = garbageName;
            this.garbagePrice = garbagePrice;
            this.image=image;
        }
    }

    /*
    * create a class that extends Base Adapter and implements all the method
     * mainntain some array inside your base adapter class that will contain all the data
     * use the get view method to fill the data from your array inside the custom structure of that
     * -single row for the row*/
    class MyAdapter extends BaseAdapter {
        Context context;
        ArrayList<SingleRow> list;

        MyAdapter(Context c) {
            context = c;
            list = new ArrayList<SingleRow>();
            /*
            * 1- get the title,discription and image from xml
            * 2- group each title, its related description and its image into a single row
            * 3- put the single row objecct inside the array list
            **/

            Resources res = c.getResources();
            String[] placeName = res.getStringArray(R.array.placeName);
            String[] placeRating = res.getStringArray(R.array.placeRating);
            /*int[] image={R.drawable.ecotourism1,R.drawable.hebbe2,R.drawable.historical1,R.drawable.historical2,R.drawable.lalbag1,
                    R.drawable.lalbag2,R.drawable.lalbag3,R.drawable.park1,R.drawable.park2,R.drawable.park3};*/

            for (int i = 0; i < 10; i++) {
                list.add(new SingleRow(placeName[i], placeRating[i], image[i]));
            }

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            /*
            * 1- get the root view
            * 2- use the root view to find the other view
            * 3-set the value
            * */
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.row, viewGroup, false);
            TextView t1 = (TextView) v.findViewById(R.id.place_name);
            TextView t2 = (TextView) v.findViewById(R.id.place_rating);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);


            Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), image[i], 100, 100);
            SingleRow temp = list.get(i);

            t1.setText(temp.garbageName);
            t2.setText(temp.garbagePrice);


            imageView.setImageBitmap(bitmap);
            //imageView.setImageResource(temp.image);
            return v;//return the root view of your row.xml
        }
    }

        public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                             int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        public static int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }
    }

