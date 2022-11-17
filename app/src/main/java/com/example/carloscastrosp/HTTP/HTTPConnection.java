package com.example.carloscastrosp.HTTP;

import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HTTPConnection {

    public static String HttpRequest(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() != 200) throw new RuntimeException("Error" + urlConnection.getResponseCode());

            InputStream is = urlConnection.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = is.read(buffer)) != -1) {
                baos.write(buffer, 0, lenght);
            }

            is.close();

            return baos.toString();

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is,"icon");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
