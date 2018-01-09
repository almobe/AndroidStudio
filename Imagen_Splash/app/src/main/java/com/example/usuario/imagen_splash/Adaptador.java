package com.example.usuario.imagen_splash;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ElementoViewHolder> implements View.OnClickListener {

    //ArrayList referente a los elementos que vamos metiendo en el Array
    private List<Elemento> listaElementos;

    //Objeto listener que declaramos e iniciaremos en el setOnclick
    private View.OnClickListener listener;

    //Constructor del Adaptador
    public Adaptador(List<Elemento> lista){
        this.listaElementos=lista;
    }

    //Método para crear la vista "inflando" el layout list_items
    @Override
    public ElementoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layParams);
        //Implementamos el setOnclick sobre la vista
        v.setOnClickListener(this);
        return new ElementoViewHolder(v);
    }

    //Método para crear el ViewHolder, por un lado creamos el objeto element en base a su posición
    //Tras esto, con los métodos setters le metemos a cada element un texto y una imagen en los elementos del
    //RecyclerView
    @Override
    public void onBindViewHolder(ElementoViewHolder holder, int position) {
        Elemento element = listaElementos.get(position);
        holder.nombre.setText(element.getNombre());
        holder.imagen.setImageResource(element.getImagen());
    }

    //Método que nos devuelve el número de items
    @Override
    public int getItemCount(){
        return this.listaElementos.size();
    }

    //Método que pone en marcha el listener sobre los elementos o partes del RecyclerView
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    //INNER CLASS en la cual vamos a hacer referencia a los elementos y sus variables
    //Accediendo a ellos a través del XML
    public  class ElementoViewHolder extends RecyclerView.ViewHolder {

        //Variables de la clase Elemento
        public ImageView imagen;
        public TextView nombre;
        public ImageButton phone;

        //Constructor ViewHolder que nos servirá par asignar a cada parte del layout su variable
        public ElementoViewHolder(View v){
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imageViewRecycler);
            nombre = (TextView) v.findViewById(R.id.textViewRecycler);
            phone = (ImageButton) v.findViewById(R.id.imageButton);
        }

    }
}











