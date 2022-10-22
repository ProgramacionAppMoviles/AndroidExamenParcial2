package com.jcab.examen2domovil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {
    Button btnBuscar, btnFecha;
    KeyEvents in1,in2,in3;
    RadioButton rbtnM, rbtnF;
    EditText eApePat,eApeMat,eNom;
    TextView txtFech;
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItem;
    String genero="";
    int dia,mes,anio;

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    String []arrayentidad = {"AGUASCALIENTES","BAJA CALIFORNIA","BAJA CALIFORNIA SUR","CAMPECHE","COAHUILA",
            "COLIMA","CHIAPAS","CHIHUAHUA","DISTRITO FEDERAL","DURANGO","GUANAJUATO","GUERRERO","HIDALGO",
            "JALISCO","MÉXICO","MICHOACAN","MORELOS","NAYARIT","NUEVO LEON","OAXACA","PUEBLA","QUERETARO","QUINTANA ROO",
            "SAN LUIS POTOSI","SINALOA","SONORA","TABASCO","TAMAULIPAS","TLAXCALA","VERACRUZ","YUCATAN","ZACATECAS"};
    String []entidadValue = {"AS","BC","BS","CC","CL","CM","CS","CH","DF","DG","GT","GR","HG","JC","MC","MN","MS"
            ,"NT","NL","OC","PL","QT","QR","SP","SL","SR","TC","TS","TL","VZ","YN","ZS"};
    Map<String,String> entidad = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //llenar
        for(int i = 0; i<this.arrayentidad.length;i++){
            entidad.put(arrayentidad[i],entidadValue[i]);
        }


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
        this.in1 = new KeyEvents(R.id.apemat);
        this.in2 = new KeyEvents(R.id.apepat);
        this.in3 = new KeyEvents(R.id.nombres);
        eApeMat.addTextChangedListener(this.in1);
        eApePat.addTextChangedListener(this.in2);
        eNom.addTextChangedListener(this.in3);
        //TextView
        txtFech = (TextView) findViewById(R.id.fechanac);

        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item_entidad, arrayentidad);

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
                        txtFech.setBackgroundResource(R.drawable.valido);
                    }
                }
                ,dia,mes,anio);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            case R.id.btnBuscar:
                //validaciones...
                if(this.in1.getValido() && this.in2.getValido() && this.in3.getValido()
                && !(this.txtFech.getText().toString().equals(""))
                && (this.rbtnF.isChecked() || this.rbtnM.isChecked())
                && !(this.autoCompleteText.getText().toString().equals(""))){
                    generarCurp();
                }else{
                   AlertDialog myalert=generarAlerta("Error","Formulario incorrecto, intenta de nuevo.");
                    myalert.show();
                }
                break;
        }
    }

    public AlertDialog generarAlerta(String title,String msg){
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
        builder1.setIcon(R.drawable.warningicon);
        AlertDialog myalert = builder1.create();

        return myalert;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void generarCurp()  {
        String nombres,apellido1,apellido2;
        boolean band = true;
        nombres = this.eNom.getText().toString().trim();
        apellido1 = this.eApePat.getText().toString().trim();
        apellido2 = this.eApeMat.getText().toString().trim();
        String curp = "";
        int i = 1;
        curp+=apellido1.charAt(0);
        while(band && apellido1.length()>1){
            Character letra = apellido1.charAt(i);
            if("aeiou".contains(String.valueOf(letra).toLowerCase())){
                curp+=apellido1.charAt(i);
                band = false;
            }
                  i++;
        }
        curp+=apellido2.charAt(0);
        curp+=nombres.charAt(0);
        Date fecha;
        try {
            fecha = formato.parse(txtFech.getText().toString());
            GregorianCalendar calendario = new GregorianCalendar();
            calendario.setTime(fecha);
            Log.i("fecha",""+calendario.get(Calendar.YEAR));
            int year = calendario.get(Calendar.YEAR);
            i=0;
            while(i<2){
                int coc = year%10;
                year/=10;
                curp+=coc;
                i++;
            }
            curp+=calendario.get(Calendar.MONTH)+1;
            curp+=calendario.get(Calendar.DAY_OF_MONTH);
        }catch(Exception e){

        }
        Log.i("curp",""+curp);
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
           this.eApePat.setText("$");
           return true;
        }
        Log.i("log","eventKey");
        return true;
    }



    class KeyEvents implements TextWatcher{
        private int id;
        private boolean valido;
        KeyEvents(int id){
            this.id=id;
            this.valido=false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.i("id",""+this.id);
            EditText text = (EditText)  findViewById(id);
            String myText = text.getText().toString().trim();
            if(myText.matches("[a-zA-Z\\ ]+")) {
                text.setBackgroundResource(R.drawable.valido);
                this.valido=true;
            }else{
                text.setBackgroundResource(R.drawable.error);
                this.valido=false;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
        public boolean getValido(){
            return this.valido;
        }
    }

}

