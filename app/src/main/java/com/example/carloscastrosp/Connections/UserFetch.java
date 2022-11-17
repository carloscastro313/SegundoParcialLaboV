package com.example.carloscastrosp.Connections;

import android.os.Handler;

import com.example.carloscastrosp.HTTP.HTTPExecute;
import com.example.carloscastrosp.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFetch extends HTTPExecute<List<User>> {
    public UserFetch(Handler handler, String url) {
        super(handler, url);
    }

    @Override
    public List<User> stringToObj(String json) throws JSONException {
        List<User> userList = new ArrayList<>();
        JSONArray arr = new JSONArray(json);


        for (int i = 0; i < arr.length(); i++) {
            JSONObject user = arr.getJSONObject(i);

            userList.add(new User(
                    user.getInt("id"),
                    user.getString("username"),
                    user.getString("rol"),
                    user.getBoolean("admin")
            ));
        }

        return userList;
    }
}