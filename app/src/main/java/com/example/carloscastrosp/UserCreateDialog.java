package com.example.carloscastrosp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import com.example.carloscastrosp.MainActivity;
import com.example.carloscastrosp.Models.User;

import java.util.List;

public class UserCreateDialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private DialogFragment dialogFragment;
    private View viewDialog;
    private MainActivity mainActivity;
    private User user;

    private EditText tvUsername;
    private Spinner slcRol;
    private CompoundButton tgAdmin;
    private Button btnCancelar;
    private Button btnGuardar;

    public UserCreateDialog(DialogFragment dialogFragment, View viewDialog, MainActivity mainActivity) {
        this.dialogFragment = dialogFragment;
        this.viewDialog = viewDialog;
        this.mainActivity = mainActivity;

        this.btnCancelar = viewDialog.findViewById(R.id.btnCancelar);
        this.btnGuardar = viewDialog.findViewById(R.id.btnGuardar);
        this.tgAdmin = viewDialog.findViewById(R.id.tgAdmin);
        this.slcRol = viewDialog.findViewById(R.id.slcRol);
        this.tvUsername = viewDialog.findViewById(R.id.tvUsername);

        btnCancelar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        tgAdmin.setOnCheckedChangeListener(this);
        slcRol.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(mainActivity, R.array.roles, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slcRol.setAdapter(adapterSpinner);

        this.user = new User();
        this.user.setAdmin(false);
        this.user.setRol("Supervisor");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGuardar){

            if(validarUsuario()){
                this.user.setUsername(tvUsername.getText().toString());
                this.user.setId(generarId());

                MainActivity.userList.add(user);

                this.mainActivity.actualizarTextbox();
                this.mainActivity.actualizarSharedPreferences();

                dialogFragment.dismiss();

            }else{
                Toast.makeText(viewDialog.getContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            }

        }else if(view.getId() == R.id.btnCancelar){
            dialogFragment.dismiss();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        this.user.setAdmin(b);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                this.user.setRol("Supervisor");
                break;
            case 1:
                this.user.setRol("Construction Manager");
                break;
            case 2:
                this.user.setRol("Project Manager");
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validarUsuario(){
        return !tvUsername.getText().toString().isEmpty();
    }

    private int generarId(){
        int id = 0;
        List<User> list = MainActivity.userList;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == null)
                continue;
            else if(list.get(i).getId() > id)
                id = list.get(i).getId();
        }

        return id + 1;
    }
}
