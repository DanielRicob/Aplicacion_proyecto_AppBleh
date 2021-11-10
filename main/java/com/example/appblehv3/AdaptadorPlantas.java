package com.example.appblehv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorPlantas extends RecyclerView.Adapter<AdaptadorPlantas.ViewEspeciePlantas>{

    ArrayList<PlantasInfo> listPlantas;

    public AdaptadorPlantas(ArrayList<PlantasInfo> listPlantas) {
        this.listPlantas = listPlantas;
    }
    @Override
    public ViewEspeciePlantas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ptlla_list_plantas,null,false);
        return new ViewEspeciePlantas(view);
    }

    @Override
    public void onBindViewHolder(ViewEspeciePlantas holder, int position) {
        holder.infTitulo.setText(listPlantas.get(position).getNombre());
        holder.infDescripcion.setText(listPlantas.get(position).getInfo());
        holder.picture.setImageResource(listPlantas.get(position).getpicture());

    }

    @Override
    public int getItemCount() {
        return listPlantas.size();
    }

    public class ViewEspeciePlantas extends RecyclerView.ViewHolder {

        TextView infTitulo,infDescripcion;
        ImageView picture;

        public ViewEspeciePlantas(View itemView) {
            super(itemView);
            infTitulo = itemView.findViewById(R.id.idtitulo);
            infDescripcion = itemView.findViewById(R.id.iddescripcion);
            picture = itemView.findViewById(R.id.idImagen);
        }
    }
}
