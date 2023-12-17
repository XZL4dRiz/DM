package com.example.proyectodm.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodm.entidades.Recetas;

import com.example.proyectodm.R;

import java.util.ArrayList;

public class ListaIngredientesAdapter extends RecyclerView.Adapter<ListaIngredientesAdapter.IngredientesViewHolder>{


    ArrayList<Recetas> listaIngredientes;
    ArrayList<Recetas> listaOriginalIngredientes;

    public ListaIngredientesAdapter(ArrayList<Recetas> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
        listaOriginalIngredientes = new ArrayList<>();
        listaOriginalIngredientes.addAll(listaIngredientes);
    }

    @NonNull
    @Override
    public ListaIngredientesAdapter.IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_ingredientes, null, false);
        return new ListaIngredientesAdapter.IngredientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaIngredientesAdapter.IngredientesViewHolder holder, int position) {
        holder.viewNombreIngredientes.setText(listaIngredientes.get(position).getIngredientes());
    }

    @Override
    public int getItemCount() {
        return listaIngredientes.size();
    }

    public class IngredientesViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombreIngredientes;
        public IngredientesViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombreIngredientes = itemView.findViewById(R.id.viewNombre);
        }
    }
}