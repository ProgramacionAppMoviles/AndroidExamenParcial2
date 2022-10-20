package com.jcab.examen2domovil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBuscar, btnFecha;
    RadioButton rbtnM, rbtnF;
    EditText eApePat,eApeMat,eNom;
    TextView txtFech;
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItem;
    String genero="";
    String []entidad = {"Hola","Juan","Cris","Pamela","Carlos"};
    int dia,mes,anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnFecha = (Button) findViewById(R.id.btnFecha);
        //Poner a los botones que escuchen
        btnFecha.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

        //RadioButton
        rbtnF = (RadioButton) findViewById(R.id.radioButtonMujer);
        rbtnM = (RadioButton) findViewById(R.id.radioButtonHombre);
        //Poner a los botones que escuchen
        rbtnM.setOnClickListener(this);
        rbtnF.setOnClickListener(this);

        //EditText
        eApeMat = (EditText) findViewById(R.id.apemat);
        eApePat = (EditText) findViewById(R.id.apepat);
        eNom = (EditText) findViewById(R.id.nombres);
        //Ponerlos a escuchar

        //TextView
        txtFech = (TextView) findViewById(R.id.fechanac);

        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item_entidad,entidad);

        //AutoCompleteTextView
        autoCompleteText = findViewById(R.id.auto_complete_txt);
        //Poner a escuchar al AutoCompleteTextView
        autoCompleteText.setAdapter(adapterItem);

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarCalendario(){
        final Calendar calendar= Calendar.getInstance();
        dia=calendar.get(Calendar.DAY_OF_MONTH);
        mes=calendar.get(Calendar.MONTH);
        anio=calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        txtFech.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                }
                ,dia,mes,anio);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.radioButtonHombre:
                if(rbtnF.isChecked()== true){
                    rbtnF.setChecked(false);
                }
                genero="M";
                break;
            case R.id.radioButtonMujer:
                if(rbtnM.isChecked()== true){
                    rbtnM.setChecked(false);
                }
                genero="F";
                break;
            case R.id.btnFecha:
                mostrarCalendario();
                break;
        }
    }

}