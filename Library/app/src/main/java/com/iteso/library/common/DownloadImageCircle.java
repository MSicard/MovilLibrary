package com.iteso.library.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maritza on 26/11/2017.
 */

public class DownloadImageCircle extends AsyncTask<Void, Void, Bitmap> {

    private ImageView imageView;
    private String url;

    public DownloadImageCircle(ImageView imageView, String url){
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try{
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        }catch(MalformedURLException url){
            return null;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(Utils.getRoundedShape(bitmap));
    }
}
