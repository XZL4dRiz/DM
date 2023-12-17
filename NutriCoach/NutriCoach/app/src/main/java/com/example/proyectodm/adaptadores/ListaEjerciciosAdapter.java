package com.example.proyectodm.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodm.R;
import com.example.proyectodm.VerEjerciciosActivity;
import com.example.proyectodm.entidades.Ejercicios;

import java.util.ArrayList;

public class ListaEjerciciosAdapter extends RecyclerView.Adapter<ListaEjerciciosAdapter.EjerciciosViewHolder>{

    ArrayList<Ejercicios> listaEjercicios;
    ArrayList<Ejercicios> listaOriginal;

    public ListaEjerciciosAdapter(ArrayList<Ejercicios> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaEjercicios);
    }

    @NonNull
    @Override
    public ListaEjerciciosAdapter.EjerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_ejercicios, null, false);
        return new EjerciciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaEjerciciosAdapter.EjerciciosViewHolder holder, int position) {
        holder.viewNombre.setText(listaEjercicios.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public class EjerciciosViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre;

        public EjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombreEjercicios);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerEjerciciosActivity.class);
                    intent.putExtra("ID", listaEjercicios.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
