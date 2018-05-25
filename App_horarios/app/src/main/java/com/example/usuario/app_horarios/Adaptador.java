package com.example.usuario.app_horarios;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.*;


public class Adaptador extends RecyclerView.Adapter<Adaptador.PeticionViewHolder> implements View.OnClickListener {

    //ArrayList referente a los elementos que vamos metiendo en el Array
    private List<Peticion> listaPeticiones;

    //Objeto listener que declaramos e iniciaremos en el setOnclick
    private View.OnClickListener listener;

    DatabaseReference bbdd;

    FirebaseAuth mAuth;

    private int borrado;

    //Constructor del Adaptador
    public Adaptador(ArrayList<Peticion> lista){
        this.listaPeticiones=lista;
    }

    private ListAdapterListener mListener;

    public interface ListAdapterListener { // create an interface
        void onClickAtOKButton(int position); // create callback function
    }



    //Método para crear la vista "inflando" el layout list_items
    @Override
    public PeticionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layParams);
        //Implementamos el setOnclick sobre la vista
        v.setOnClickListener(this);
        return new PeticionViewHolder(v);
    }

    //Método para crear el ViewHolder, por un lado creamos el objeto element en base a su posición
    //Tras esto, con los métodos setters le metemos a cada element un texto y una imagen en los elementos del
    //RecyclerView
    @Override
    public void onBindViewHolder(final PeticionViewHolder holder, final int position) {
        final Peticion peticion = listaPeticiones.get(position);
        holder.motivo.setText(peticion.getReason());
        holder.horas.setText(peticion.getFirstTime()+" - "+peticion.getLastTime());
        holder.fechas.setText(peticion.getFirstDay()+" - "+peticion.getLastDay());

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference("peticiones");

        //Inicializamos objeto de autenticator
        mAuth = FirebaseAuth.getInstance();

        holder.papelera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("¿Desea borrar la petición con motivo: \""+holder.motivo.getText().toString()+"\"?")
                        .setTitle("Advertencia")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // metodo que se debe implementar

                                        borrado =0;
                                        bbdd.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                //Recorrer todos los productos
                                                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                                                    Peticion peticionRescatada = dataSnapshot2.getValue(Peticion.class);
                                                    String idPeticion = peticionRescatada.getId();
                                                    String clave =dataSnapshot2.getKey();

                                                    //Si el que hay en el spinner coincide lo borramos con el método removeValue()
                                                    if(peticion.getId().equals(idPeticion) && borrado==0){
                                                        if(peticionRescatada.getUserId().equals(mAuth.getCurrentUser().getUid())){
                                                            bbdd.child(clave).removeValue();
                                                            Toast.makeText(v.getContext(), "Petición"+ peticion.getReason() + "borrada correctamente", Toast.LENGTH_LONG).show();
                                                            borrado = 1;//Pasamos borrado a 1 para que deje de borrar
                                                        }else{
                                                            Toast.makeText(v.getContext(), "Petición de otro usuario", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });




                                        }
                                    }
                                );
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

    }

    //Método que nos devuelve el número de items
    @Override
    public int getItemCount(){
        return this.listaPeticiones.size();
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
    public  class PeticionViewHolder extends RecyclerView.ViewHolder {

        //Variables de la clase Elemento
        public TextView horas;
        public TextView motivo;
        public TextView fechas;
        public ImageButton papelera;


        //Constructor ViewHolder que nos servirá par asignar a cada parte del layout su variable
        public PeticionViewHolder(View v){
            super(v);
            motivo = (TextView) v.findViewById(R.id.textViewRecyclerReason);
            fechas = (TextView) v.findViewById(R.id.textViewRecyclerDates);
            horas = (TextView) v.findViewById(R.id.textViewRecyclerHour);
            papelera = (ImageButton) v.findViewById(R.id.imageButtonDelete);
        }


    }



}











