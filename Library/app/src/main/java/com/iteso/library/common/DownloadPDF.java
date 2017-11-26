package com.iteso.library.common;

import android.os.AsyncTask;
import android.os.Environment;

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
    private String url;
    private String isbn;
    private static final int  MEGABYTE = 1024 * 1024;

    public DownloadPDF(String url,String isbn){
        this.url = url;
        this.isbn = isbn;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "testthreepdf");
        folder.mkdir();

        File pdfFile = new File(folder, isbn + ".pdf");

        try {
            pdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
