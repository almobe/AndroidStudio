package com.example.usuario.app_horarios;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdaptadorRoles extends RecyclerView.Adapter<AdaptadorRoles.PasarAGerenciaViewHolder> implements View.OnClickListener {

   private List<Empleado> listaEmpleados;

   private View.OnClickListener listener;

   DatabaseReference bbdd;

   private int ascendido;

   public AdaptadorRoles(List<Empleado> lista) {
       this.listaEmpleados=lista;
   }

   private Adaptador.ListAdapterListener mListener;

    /*El view lo "inflamos con el layout list_item_3*/
    @Override
    public PasarAGerenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_3, parent, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layParams);
        //Implementamos el setOnclick sobre la vista
        v.setOnClickListener(this);
        return new PasarAGerenciaViewHolder(v);
    }

    /*Por cada empleado del array de empleados recibido en el constructor hacemos lo siguiente:*/
    @Override
    public void onBindViewHolder(final PasarAGerenciaViewHolder holder, int position) {
        final Empleado empleado = listaEmpleados.get(position);

        holder.nombre.setText(empleado.getNombre()+" "+empleado.getApellidos());

        bbdd = FirebaseDatabase.getInstance().getReference("empleados");

        /*Cada empleado tiene un interruptor para pasar a gerencia, si se pulsa...*/
        holder.interrupptor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*...nos sale un diálogo preguntándonos si queremos pasar a gerencia al empleado
                seleccionado...
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(buttonView.getContext());
                builder.setMessage("¿Desea pasar al modo gerencia al empleado "+holder.nombre.getText().toString()+"?")
                        .setTitle("Advertencia")
                        .setCancelable(false)
                        /*...en caso de marcar que no, no hace nada*/
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        holder.interrupptor.setChecked(false);
                                        dialog.cancel();
                                    }
                                })
                        /*en caso de marcar que sí, procede a ello*/
                        .setPositiveButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // metodo que se debe implementar

                                        ascendido=0;
                                        bbdd.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                //Recorrer todos los empleados
                                                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                                                    Empleado empleadoRescatado = dataSnapshot2.getValue(Empleado.class);
                                                    String idEmpleado = empleadoRescatado.getId();
                                                    String clave =dataSnapshot2.getKey();

                                                    //Si el seleccionado coincide con el actual y no
                                                    if(empleado.getId().equals(idEmpleado) && ascendido==0){
                                                            empleadoRescatado.setRol("manager");
                                                            bbdd.child(clave).setValue(empleadoRescatado);
                                                            ascendido = 1;//Pasamos borrado a 1 para que deje de borrar
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

    @Override
    public int getItemCount() {
        return this.listaEmpleados.size();
    }

    //Método que pone en marcha el listener sobre los elementos o partes del RecyclerView
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public  class PasarAGerenciaViewHolder extends RecyclerView.ViewHolder {

        //Variables de la clase Elemento;
        public TextView nombre;
        public Switch interrupptor;


        //Constructor ViewHolder que nos servirá par asignar a cada parte del layout su variable
        public PasarAGerenciaViewHolder(View v){
            super(v);
            nombre = (TextView) v.findViewById(R.id.textViewRecyclerEmployer);
            interrupptor = (Switch) v.findViewById(R.id.switch2);
        }


    }
}
