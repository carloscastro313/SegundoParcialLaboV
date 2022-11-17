package com.example.carloscastrosp.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class GenericDialog extends DialogFragment {
    private String title;
    private String positivo;
    private View view;
    private DialogInterface.OnClickListener listener;
    private String mensaje;

    public GenericDialog(String title, String mensaje, String positivo) {
        this.title = title;
        this.mensaje = mensaje;
        this.positivo = positivo;
    }

    public GenericDialog(String title, View view) {
        this.title = title;
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(super.getActivity());

        if (!("".equals(this.title)) && this.title != null) builder.setTitle(this.title);
        if (!("".equals(this.positivo)) && this.positivo != null) builder.setPositiveButton(this.positivo, this.listener);
        if (!("".equals(this.mensaje)) && this.mensaje != null) builder.setMessage(this.mensaje);

        if (this.view != null) builder.setView(this.view);

        return builder.create();
    }
}
