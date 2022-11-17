package com.example.carloscastrosp.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.carloscastrosp.Dialog.GenericDialog;
import com.example.carloscastrosp.Models.User;

import java.util.List;

public class SearchEvent implements SearchView.OnQueryTextListener {
    private List<User> list;
    private AppCompatActivity activity;

    public SearchEvent(List<User> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        for (int i = 0; i < this.list.size(); i++) {
            User user = this.list.get(i);

            if (query.equals(user.getUsername())) {
                String mensaje = "El rol del usuario es " + user.getRol();
                GenericDialog dialog = new GenericDialog("Usuario encontrado", mensaje, "Cerrar");

                dialog.show(this.activity.getSupportFragmentManager(), "Dialog encontró usuario");
                return false;
            }
        }
        GenericDialog dialog = new GenericDialog("Usuario no encontrado", "El usuario ".concat(query).concat(" no esta dentro de la lista"), "Cerrar" );

        dialog.show(this.activity.getSupportFragmentManager(), "Dialog NO encontró usuario");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
