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


public class Adaptador extends RecyclerView.Adapter<Adaptador.ElementoViewHolder> {

    //ArrayList referente a los elementos que vamos metiendo en el Array
    private List<Elemento> listaElementos;

    //INNER CLASS en la cual vamos a hacer referencia a los elementos y sus variables
    //Accediendo a ellos a través del XML
    public  class ElementoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Variables de la clase Elemento
        public ImageView imagen;
        public TextView nombre;
        public ImageButton phone;

        //Constructor ViewHolder que nos servirá
        public ElementoViewHolder(View v){
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imageViewRecycler);
            nombre = (TextView) v.findViewById(R.id.textViewRecycler);
            phone = (ImageButton) v.findViewById(R.id.imageButton);
            phone.setOnClickListener(this);
        }

        //Método onClick implementado para definir las acciones que queremos que haga
        @Override
        public void onClick(View view) {
            //Vamos a utilizar Toast, por un lado necesitamos saber el contexto
            Context context=view.getContext();
            //Por otro para saber a que elemento nos referimos tenemos que saber la posición en el adaptador
            int position=getAdapterPosition();
            //Por último aquí creamos el objeto elemento en base a la posición del elemento marcado
            //en relación con el adaptador
            Elemento elemento=listaElementos.get(position);

            if  (elemento.getNombre()=="Ghostbusters"){
                Toast toast = Toast.makeText(context,"Has tomado la mejor decisión. Llamando al número de teléfono : "+elemento.getTelefono(),Toast.LENGTH_LONG);
                toast.show();
            }else {
                Toast toas = Toast.makeText(context, "Llamando al número de teléfono : " + elemento.getTelefono(), Toast.LENGTH_LONG);
                toas.show();
            }
        }
    }

    //Constructor del Adaptador
    public Adaptador(List<Elemento> lista){
        this.listaElementos=lista;
    }

    //Método que nos devuelve el número de items
    @Override
    public int getItemCount(){
        return this.listaElementos.size();
    }

    //Método para crear la vista "inflando" el layout list_items
    @Override
    public ElementoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ElementoViewHolder(v);
    }

    //Método para crear el ViewHolder, por un lado creamos el objeto element en base a su posición
    //Tras esto, con los métodos setters le metemos a cada element un texto y una imagen
    @Override
    public void onBindViewHolder(ElementoViewHolder holder, int position) {
        Elemento element = listaElementos.get(position);
        holder.nombre.setText(element.getNombre());
        holder.imagen.setImageResource(element.getImagen());
    }




}
