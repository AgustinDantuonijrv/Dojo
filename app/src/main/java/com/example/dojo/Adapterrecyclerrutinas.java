package com.example.dojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterrecyclerrutinas extends RecyclerView.Adapter<Adapterrecyclerrutinas.ViewHolder> implements View.OnClickListener {

    Context context;
    ArrayList<Pojofisico> datapojo;
    private OnItemListener onItemListener;

public Adapterrecyclerrutinas(Context c, ArrayList<Pojofisico> p, Fisico onItemListener)
{
    context = c;
    datapojo = p;
    this.onItemListener = onItemListener;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false),onItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Observacionesdeusuario.setText(datapojo.get(position).getObservacionesdeusuario());
        holder.diaparte.setText(datapojo.get(position).getDiaparte());
        holder.rutinaFormato.setText(datapojo.get(position).getRutinaformato());
    }

    @Override
    public int getItemCount() {
        return datapojo.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        //Declare textviees,edittext etc
        TextView Observacionesdeusuario,diaparte,rutinaFormato;
        Adapterrecyclerrutinas.OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);

//initialize the things that were mentioned before
            Observacionesdeusuario = (TextView) itemView.findViewById(R.id.resultadosobservaciones);
            diaparte = (TextView) itemView.findViewById(R.id.resultadosdetalles);
            rutinaFormato = (TextView) itemView.findViewById(R.id.resultadosejercicio);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public void onClick(View view) {

    }
    public interface OnItemListener{
        void onItemClick(int position);
    }

}
