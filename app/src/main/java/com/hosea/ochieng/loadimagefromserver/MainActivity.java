package com.hosea.ochieng.loadimagefromserver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoadImageFromServer((ImageView)findViewById(R.id.image1), (ImageView)findViewById(R.id.image2))
                .execute("http://expancespatialsolutions.com/KNav/plan_1.jpg", "http://expancespatialsolutions.com/KNav/plan_2.jpg");
    }

    private class LoadImageFromServer extends AsyncTask<String, Void, ArrayList<Bitmap>>{
        ImageView image1;
        ImageView image2;

        public LoadImageFromServer(ImageView image1, ImageView image2) {
            this.image1 = image1;
            this.image2 = image2;
            Toast.makeText(MainActivity.this, "Loading images, just a few minutes", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings) {
            String imageUrl1 = strings[0];
            String imageUrl2 = strings[1];

            Bitmap image1Bitmap = null;
            Bitmap image2Bitmap = null;

            ArrayList<Bitmap> allImageBitmaps = new ArrayList<>();

            try {
                InputStream in1 = new URL(imageUrl1).openStream();
                InputStream in2 = new URL(imageUrl2).openStream();

                image1Bitmap = BitmapFactory.decodeStream(in1);
                image2Bitmap = BitmapFactory.decodeStream(in2);

                allImageBitmaps.add(image1Bitmap);
                allImageBitmaps.add(image2Bitmap);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return allImageBitmaps;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            image1.setImageBitmap(bitmaps.get(0));
            image2.setImageBitmap(bitmaps.get(1));
        }
    }
}
