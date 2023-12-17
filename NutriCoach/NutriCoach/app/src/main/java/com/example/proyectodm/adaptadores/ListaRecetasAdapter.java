package com.example.proyectodm.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodm.VerActivity;
import com.example.proyectodm.entidades.Recetas;

import com.example.proyectodm.R;

import java.util.ArrayList;

public class ListaRecetasAdapter extends RecyclerView.Adapter<ListaRecetasAdapter.RecetasViewHolder>{

    ArrayList<Recetas> listaRecetas;
    ArrayList<Recetas> listaOriginal;

    public ListaRecetasAdapter(ArrayList<Recetas> listaRecetas) {
        this.listaRecetas = listaRecetas;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaRecetas);
    }

    @NonNull
    @Override
    public ListaRecetasAdapter.RecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_recetas, null, false);
        return new RecetasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRecetasAdapter.RecetasViewHolder holder, int position) {
        holder.viewNombre.setText(listaRecetas.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    public class RecetasViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre;

        public RecetasViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaRecetas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
