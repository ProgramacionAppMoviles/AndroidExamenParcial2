package com.jcab.examen2domovil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class mostrar_curp extends AppCompatActivity implements View.OnClickListener{

    TextView tshowCurp;
    ImageView back,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_curp);

        tshowCurp = (TextView) findViewById(R.id.textCurp);

        back = (ImageView) findViewById(R.id.back);
        info = (ImageView) findViewById(R.id.info);

        info.setOnClickListener(this);
        back.setOnClickListener(this);

        Intent mPantalla = getIntent();
        String curp = mPantalla.getStringExtra(MainActivity.CURP);

        tshowCurp.setText(curp);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void otraConsulta(){
        Intent otracon = new Intent(this, MainActivity.class);
        startActivity(otracon);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.info:
                AlertDialog myalert = generarAlerta("Información",
                        "Te muestra curp de los datos ingresados " +
                                "anteriormente, para consultar otra" +
                                " curp regresa con el icono flecha." +
                                "\n\nPuedes ingresar otros datos para consultar "+
                                "otra curp",
                        R.drawable.info_icon);
                myalert.show();
                break;
            case R.id.back:
                otraConsulta();
                break;
        }
    }

    public AlertDialog generarAlerta(String title, String msg, int icon){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                    /*builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });*/

        builder1.setIcon(icon);
        AlertDialog myalert = builder1.create();

        return myalert;
    }

}