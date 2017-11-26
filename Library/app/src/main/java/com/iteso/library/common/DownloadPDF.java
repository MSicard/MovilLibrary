package com.iteso.library.common;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.iteso.library.beans.MyBookDetail;
import com.iteso.library.gui.ActivityMyBookDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Desarrollo on 25/11/2017.
 */

public class DownloadPDF extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String url;
    private String isbn;
    private MyBookDetail myBookDetail;
    private DatabaseReference databaseReference;
    private static final int  MEGABYTE = 1024 * 1024;

    public DownloadPDF(Context context, String url, String isbn, DatabaseReference databaseReference, MyBookDetail myBookDetail){
        this.context = context;
        this.url = url;
        this.isbn = isbn;
        this.databaseReference = databaseReference;
        this.myBookDetail = myBookDetail;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Download");
        folder.mkdir();

        File pdfFile = new File(folder, isbn + ".pdf");

        try{
            pdfFile.createNewFile();
        }catch (IOException e){}

        try {

            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {}
        catch (MalformedURLException e) {}
        catch (IOException e) {}

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();
        myBookDetail.setDownload(false);
        databaseReference.setValue(myBookDetail);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Download is finished", Toast.LENGTH_LONG).show();
        myBookDetail.setDownload(true);
        databaseReference.setValue(myBookDetail);
    }
}
