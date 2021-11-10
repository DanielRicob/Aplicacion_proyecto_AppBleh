package com.example.appblehv3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Monitor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "Monitor";
    //public String readMessage;
    TextView tvtMensaje;
    TextView mtv;
    EditText edtTextoOut;
    Button btnEnviar;
    Button btnIniciar;
    Button btnDesconectar;
    Button btnatras;
    Button btnriego;
    ImageView Humedo_24;
    ImageView SinAgua_24;
    ImageView ConAgua_24;

    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBt;

    private static  final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = null;
    //private String Mydatos;
    Boolean estadoBoton = true;

    int diaA ,mesA , anioA, anioR,mesR, diaR;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

        edtTextoOut = findViewById(R.id.edtTextoOut);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnIniciar  = findViewById(R.id.btnIniciar);
        btnDesconectar = findViewById(R.id.btnDesconectar);
        btnatras = findViewById(R.id.btnAtras);
        btnriego = findViewById(R.id.btnriego);
        tvtMensaje = findViewById(R.id.tvtMensaje);
        mtv = findViewById(R.id.mtv);

        Humedo_24 =  findViewById(R.id.Humedo_24);
        SinAgua_24 =  findViewById(R.id.SinAgua_24);
        ConAgua_24 =  findViewById(R.id.ConAgua_24);

        bluetoothIn = new Handler(){
            public void handleMessage(android.os.Message msg){
                if (msg.what == handlerState){
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);
                    tvtMensaje.setText(readMessage);

                    float registro = Float.parseFloat(String.valueOf(readMessage));
                    if(registro > 700){
                        SinAgua_24.setVisibility(View.VISIBLE);
                        ConAgua_24.setVisibility(View.INVISIBLE);
                        Humedo_24.setVisibility(View.INVISIBLE);
                    }else if(registro > 500 && registro <= 699){
                        Humedo_24.setVisibility(View.VISIBLE);
                        ConAgua_24.setVisibility(View.INVISIBLE);
                        SinAgua_24.setVisibility(View.INVISIBLE);
                    }else if(registro <= 499  ){
                        ConAgua_24.setVisibility(View.VISIBLE);
                        SinAgua_24.setVisibility(View.INVISIBLE);
                        Humedo_24.setVisibility(View.INVISIBLE);}

                }
            }
        };

        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String GetDat = edtTextoOut.getText().toString();
                MyConexionBt.write(GetDat);
                Log.e(TAG, "Dato Edit  " + GetDat);
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( estadoBoton == true) {
                    btnIniciar.setText("DETENER");
                    String ini = "I";
                    MyConexionBt.write(ini);
                    estadoBoton = false;
                    Log.e(TAG, "Dato Edit  " + ini);
                }else{
                    btnIniciar.setText("INICIAR");
                    String ini = "D";
                    MyConexionBt.write(ini);
                    estadoBoton = true;
                    Log.e(TAG, "Dato Edit  " + ini);

                }
            }

        });


        btnDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket!=null) {
                    try {
                        MyConexionBt.write("D");
                        btSocket.close();
                    } catch (IOException e) {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConexionBt.write("D");
                Toast.makeText(getBaseContext(), "Cerrar Conexion", Toast.LENGTH_LONG).show();
                Intent Next = new Intent(getBaseContext(), MainActivity.class);
                startActivity(Next);
            }
        });


        btnriego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Calendar calendar = Calendar.getInstance();
                anioA = calendar.get(Calendar.YEAR);
                mesA = calendar.get(Calendar.MONTH);
                diaA = calendar.get(Calendar.DATE);
                Toast.makeText(Monitor.this.getBaseContext(), "Fecha " + diaA + "-" + (mesA + 1) + "-" + anioA, Toast.LENGTH_LONG).show();
                ShowfechaRiego();
            }
        });
    }

    private void ShowfechaRiego() {
       DatePickerDialog fechaRiego = new DatePickerDialog(
                Monitor.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE)
        );
        fechaRiego.show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int anioR, int mesR, int diaR) {
        Toast.makeText(Monitor.this.getBaseContext(), "Fecha " + diaR + "-" + (mesR + 1) + "-" + anioR, Toast.LENGTH_LONG).show();
        //mtv.setText(diaR + "/" +(mesR+1) + "/" + anioR);
        mtv.setText(String.valueOf("Dias Riego: " + Riego(diaR, mesR, anioR, true)));
    }



     private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException{
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        address = intent.getStringExtra(DispositivosVinculados.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        try {
            btSocket = createBluetoothSocket(device);
        }catch (IOException e){
            Toast.makeText(getBaseContext(),"La Creaccion del Socket fallo",Toast.LENGTH_SHORT).show();
        }
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {btSocket.close();
            } catch (IOException e2) {}
        }

        MyConexionBt = new ConnectedThread(btSocket);
        MyConexionBt.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            btSocket.close();
        }catch (IOException e2) {}
    }

    private void VerificarEstadoBT() {

        if(btAdapter==null){
            Toast.makeText(getBaseContext(), "El dispositivo no sopoerta Bluetooth",Toast.LENGTH_SHORT).show();
        } else {
            if(btAdapter.isEnabled()) {
                Log.i(TAG, "VerificarEstadoBT: inicia conexion");
            } else {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,1);
            }
        }
    }

    private class ConnectedThread extends Thread{
        private  final InputStream mmInStream;
        private  final OutputStream mmOutStream;


        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e){}

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        @Override
        public void run() {
            byte[] buffer = new byte[8];
            int bytes;
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    Delay();
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                    Log.e(TAG, "dato:  "+ readMessage);
                } catch (IOException e) {
                    break;
                }
            }

        }



        public void write(String input){
            try {
                mmOutStream.write(input.getBytes());
            }catch (IOException e){
                MyConexionBt.write("D");
                Toast.makeText(getBaseContext(),"Fallo la Conexion",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    private void Delay(){
        try {
            Thread.sleep(1490);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int Riego(int dia, int mes , int anio, boolean infin){

            Calendar inicio = Calendar.getInstance();
            inicio.set(anio,mes,dia);
            inicio.set(Calendar.HOUR,0);
            inicio.set(Calendar.HOUR_OF_DAY,0);
            inicio.set(Calendar.MINUTE,0);
            inicio.set(Calendar.SECOND,0);

            Calendar actual = Calendar.getInstance();
            actual.set(Calendar.HOUR,0);
            actual.set(Calendar.HOUR_OF_DAY,0);
            actual.set(Calendar.MINUTE,0);
            actual.set(Calendar.SECOND,0);

            long finMS = actual.getTimeInMillis();
            long inicioMS = inicio.getTimeInMillis();

            int Calculadias = (int) ((Math.abs(finMS-inicioMS))/(1000*60*60*25));
            if (true){Calculadias++;}

        return Calculadias;
    }

}