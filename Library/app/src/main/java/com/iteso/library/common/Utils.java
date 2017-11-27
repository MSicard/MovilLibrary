package com.iteso.library.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maritza on 28/09/2017.
 */

public class Utils {

    //Método que hace redonda la imagen
    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 200;
        int targetHeight = 200;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);

        Path path = new Path();

        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);

        Bitmap sourceBitmap = scaleBitmapImage;

        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
                targetHeight), null);

        return targetBitmap;
    }

    //Hace la conversión del date a un string que cambia dependiendo que tanta diferencia de horas y días hay entre la actual y la mandada
    public static String putTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes());
        Calendar c2 = Calendar.getInstance();
        c2.getTime();

        String pattern = "dd MMMMMM HH:MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if(c2.get(Calendar.YEAR) - c.get(Calendar.YEAR) < 1){
            Log.v("year", Integer.toString(c2.get(Calendar.MONTH) - c.get(Calendar.MONTH)));
            if(c2.get(Calendar.MONTH) - c.get(Calendar.MONTH) < 1){
                if(c2.get(Calendar.DAY_OF_MONTH) - c.get(Calendar.DAY_OF_MONTH)< 1){
                    if(c2.get(Calendar.HOUR) - c.get(Calendar.HOUR) < 1){
                        String minutos = "Hace " + Integer.toString(c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE)) + " minutos";
                        return minutos;
                    }
                    else{
                        String horas = "Hace " + Integer.toString(c.get(Calendar.HOUR)) + " horas";
                        return horas;
                    }
                }else{
                    return simpleDateFormat.format(date);
                }
            }
            else{
                return simpleDateFormat.format(date);
            }
        }else{
            return simpleDateFormat.format(date);
        }
    }

    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isConnectedMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

}
