package com.example.usuario.app_horarios;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorManager extends RecyclerView.Adapter<AdaptadorManager.PeticionManagerViewHolder> implements View.OnClickListener {

    /*Adaptador utilizado en el activity donde se muestran todas las peticiones de los distintos empleados*/

    //ArrayList referente a los elementos que vamos metiendo en el Array
    private List<Peticion> listaPeticiones;

    DatabaseReference bbdd, bbdd2;

    public AdaptadorManager(ArrayList<Peticion> lista) {
        this.listaPeticiones=lista;
    }

    /*Se "infla" el view con el layout list_item_2 preparado para ello*/
    @Override
    public PeticionManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_2, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new PeticionManagerViewHolder(v);
    }

    /*Hacemos el BindViewHolder para que de cada Petición se rellene en el listItem sus características*/
    @Override
    public void onBindViewHolder(AdaptadorManager.PeticionManagerViewHolder holder, int position) {
        final Peticion peticion = listaPeticiones.get(position);
        holder.motivo.setText(peticion.getReason());
        holder.fechas.setText(peticion.getFirstDay()+" - "+peticion.getLastDay());
        holder.horas.setText(peticion.getFirstTime()+" - "+peticion.getLastTime());

    }

    @Override
    public int getItemCount() {
        return this.listaPeticiones.size();
    }


    @Override
    public void onClick(View v) {

    }

    public class PeticionManagerViewHolder extends RecyclerView.ViewHolder {
        //Variables de la clase Peticion
        public TextView horas;
        public TextView motivo;
        public TextView fechas;
        public TextView empleado;

        public PeticionManagerViewHolder (View v) {
            super(v);
            motivo = (TextView) v.findViewById(R.id.textViewRecyclerReasonCrew);
            fechas = (TextView) v.findViewById(R.id.textViewRecyclerDatesCrew);
            horas = (TextView) v.findViewById(R.id.textViewRecyclerHourCrew);

        }
    }
}
