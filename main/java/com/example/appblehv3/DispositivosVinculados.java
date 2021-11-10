package com.example.appblehv3;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class DispositivosVinculados extends AppCompatActivity {

    private static final String TAG = "DispositivosVinculados";
    ListView idLista;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter mPairedDeviceArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_vinculados);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VerificarEstadoBT();
        mPairedDeviceArrayAdapter = new ArrayAdapter(this, R.layout.dispositivos_encontrados);
        idLista = (ListView) findViewById(R.id.IdLista);
        idLista.setAdapter(mPairedDeviceArrayAdapter);
        idLista.setOnItemClickListener(mDeviceClickListener);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice device : pairedDevices){
                mPairedDeviceArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            }
        }
    }
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void  onItemClick(AdapterView av, View v, int arg2, long arg3){
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() -17);

            finishAffinity();

            Intent Next = new Intent(DispositivosVinculados.this,Monitor.class);
            Next.putExtra(EXTRA_DEVICE_ADDRESS,address);
            startActivity(Next);
        }
    };

    private void VerificarEstadoBT(){
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBtAdapter==null){
            Toast.makeText(getBaseContext(), "El dispositvo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else {if (mBtAdapter.isEnabled()) {
            Log.d(TAG, "...Bluetooth Activado...");
        }else {Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent,1);
              }
        }
    }
}