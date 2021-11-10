package com.example.appblehv3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View  vistaInfo;
    TextView  textView;
    TextView  dostxt;
    TextView  trestxt;


    public SessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionFragment newInstance(String param1, String param2) {
        SessionFragment fragment = new SessionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vistaInfo = inflater.inflate(R.layout.fragment_session, container , false);

        textView =  vistaInfo.findViewById(R.id.primertexto);
        dostxt =  vistaInfo.findViewById(R.id.segundotexto);
        trestxt = vistaInfo.findViewById(R.id.tercertexto);

        textView.setText(String.valueOf("AppBleH \n Es un a aplicación que  nace de la necesidad de llevar un monitoreo en  plantas  pequeñas de maceta, con el fin de  incentivar el cuidado, conocimiento de especies actas para cultivo urbanos, como ornamentales, aromáticas y medicinales. Nos apropiados  del proyecto con la ayuda de la tecnología del IoT Internet de las cosa, en la que  se puede  llevar el monitoreo mediante un sensor de Humedad HIGRÓMETRO  que mide la húmeda del suelo a partir de la variación de su conductividad. \n"));
        dostxt.setText(String.valueOf("El método de alistamiento del Sensor es  muy  fácil,  se clava en la tierra de la maceta introduciendo las puntas hasta  cubrir \n"));
        trestxt.setText(String.valueOf("Al realizar  los implementación del sensor ya se esta listo  con  iniciar  el monitoreo de humedad, la aplicación esta  pensada para   dar el detalle de la humedad del a tierra en  3 niveles humedad alta,  media y  baja.\n"));

        return vistaInfo;

    }

}