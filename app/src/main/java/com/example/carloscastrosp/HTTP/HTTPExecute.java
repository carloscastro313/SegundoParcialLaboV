package com.example.carloscastrosp.HTTP;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;

import java.util.List;

public abstract class HTTPExecute<T> extends Thread {

    public Handler handler;
    public String url;

    public HTTPExecute(Handler handler, String url){
        this.handler = handler;
        this.url = url;
    }

    @Override
    public void run() {
        String json = HTTPConnection.HttpRequest(url);

        Message message = new Message();

        try {
            message.obj = stringToObj(json);
        }catch (JSONException e){
            e.printStackTrace();
        }

        this.handler.sendMessage(message);
    }

    public abstract T stringToObj(String json) throws JSONException;

}
