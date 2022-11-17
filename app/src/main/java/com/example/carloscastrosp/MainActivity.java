package com.example.carloscastrosp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.carloscastrosp.Connections.UserFetch;
import com.example.carloscastrosp.Dialog.GenericDialog;
import com.example.carloscastrosp.Models.User;
import com.example.carloscastrosp.Search.SearchEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    static List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUsuarios();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_header, menu);

        MenuItem menuItem = menu.findItem(R.id.buscar);
        SearchEvent searchEvent = new SearchEvent(userList, this);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(searchEvent);

        return true;
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        userList = (List<User>) message.obj;
        actualizarTextbox();
        actualizarSharedPreferences();

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.agregar_usuario){
            View dialog = LayoutInflater.from(this).inflate(R.layout.agregar_usuario,null);
            GenericDialog genericDialog = new GenericDialog("Crear usuario",dialog);
            UserCreateDialog userCreateDialog = new UserCreateDialog(genericDialog,dialog,this);

            genericDialog.show(this.getSupportFragmentManager(),"Dialog crear usuario");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUsuarios() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("misUsuarios", Context.MODE_PRIVATE);
        String usuariosCompleto = sharedPreferences.getString("usuarios", "vacio");


        if("vacio".equals(usuariosCompleto)){
            getApi();
        }else{
            jsonToList(usuariosCompleto);
            actualizarTextbox();
            actualizarSharedPreferences();
        }
    }

    private void jsonToList(String json) {
        List<User> list = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(json);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject user = arr.getJSONObject(i);

                list.add(new User(
                        user.getInt("id"),
                        user.getString("username"),
                        user.getString("rol"),
                        user.getBoolean("admin")
                ));
            }

        }catch (JSONException ex){

        }

        userList = list;
    }

    private void getApi(){
        Handler handler = new Handler(this);

        UserFetch fetch = new UserFetch(handler,"http://192.168.1.46:3001/usuarios");
        fetch.start();
    }

    public void actualizarTextbox(){
        TextView tv = super.findViewById(R.id.usuarios);
        tv.setText(userList.toString());
    }

    public void actualizarSharedPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("misUsuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuarios", userList.toString());
        editor.commit();
    }
}