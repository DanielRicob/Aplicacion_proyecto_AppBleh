package com.example.appblehv3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PlantsFragment extends Fragment {

    ArrayList<PlantasInfo> listPlantas;
    RecyclerView recyclerPlantas;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlantsFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlantsFragment newInstance(String param1, String param2) {
        PlantsFragment fragment = new PlantsFragment();
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
        View vista = inflater.inflate(R.layout.fragment_plants, container, false);

        listPlantas = new ArrayList<>();
        recyclerPlantas= vista.findViewById(R.id.RecyclerId);
        recyclerPlantas.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarInfoPlantas();

        AdaptadorPlantas adapter = new AdaptadorPlantas(listPlantas);
        recyclerPlantas.setAdapter(adapter);


        return vista;
    }

    private void llenarInfoPlantas() {

        listPlantas.add(new PlantasInfo("Savila","Arbusto acaule o con tallo corto de hasta 30 cm, erecto, sin rebrotes laterales. Las hojas",R.drawable.igsavila));
        listPlantas.add(new PlantasInfo("Kactus","Cactaceae, las cactáceas. Sin embargo, hay una excepción, Rhipsalis baccifera,",R.drawable.imgkactus));

    }
}