package com.example.usuario.app_horarios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityPorEmpleado extends AppCompatActivity {

    private Spinner spinnerEmpleados;

    private ArrayList<String> arrayEmpleados;
    private ArrayAdapter<String> adapter2;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;
    DatabaseReference bbdd2;

    Empleado empleado, empleado_actual;

    //Declaramos sin iniciar el RecyclerView, el Adapatador y el ArrayList
    private RecyclerView rv;
    private AdaptadorManager adapter;
    private ArrayList<Peticion> peticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.por_empleado);

        //Iniciamos spinner, firebasedatabase, arraylist de empleados para el spinner y adaptador del spinner
        spinnerEmpleados = (Spinner) findViewById(R.id.spinner_empleados);

        bbdd = FirebaseDatabase.getInstance().getReference("empleados");

        arrayEmpleados = new ArrayList<String>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayEmpleados);
        spinnerEmpleados.setAdapter(adapter2);

        //Método para sacar los nombres de empleados por el spinner
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Ante cualquier modificación, se limpia el array
                arrayEmpleados.clear();

                //Con un bucle for por cada nombre de empledao que encuentre lo añade al array
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    empleado = datasnapshot.getValue(Empleado.class);
                    String nombreEmpleado = empleado.getNombre();
                    //Condición de que el empleado tenga que tener el rol "crew" para entrar en el arraylist
                    if (empleado.getRol().equals("crew")){
                        arrayEmpleados.add(nombreEmpleado);
                    }
                }
                //Sobre el spinner se hace un setAdapter del adaptador anteriormente iniciado
                spinnerEmpleados.setAdapter(adapter2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //Iniciamos ArrayList de peticiones, firebasedatabse de peticiones y recycler view
        peticiones = new ArrayList<>();
        bbdd2 = FirebaseDatabase.getInstance().getReference("peticiones");
        rv= (RecyclerView) findViewById(R.id.RECYCLER_VIEW_POR_EMPLEADO);

        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lim);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                lim.getOrientation());

        rv.addItemDecoration(dividerItemDecoration);

        //Método para realizar acciones en base al empleado que se selecciona del spinner
        spinnerEmpleados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Primera acción, se selecciona un empleado que coincida el nombre del empleado con el del spinner
                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            empleado = datasnapshot.getValue(Empleado.class);
                            if (empleado.getNombre().equals(spinnerEmpleados.getSelectedItem().toString())){
                                empleado_actual = empleado;
                            }
                        }
                        adapter = new AdaptadorManager(peticiones);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                //Segunda acción: del empleado seleccionado se sacan sus peticiones por el recyclerView
                bbdd2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        peticiones.clear();
                        //Recorremos cada uno de las peticiones
                        for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            Peticion peticion = dataSnapshot2.getValue(Peticion.class);
                            //Si coincide el empleado de la petición con el empleado seleccionado
                            if(peticion.getUserId().equals(empleado_actual.getId())){
                                peticiones.add(peticion);
                            }
                        }
                        //Hacemos un reverse sobre el array concreto debido a que sale en el orden que se han hecho las peticiones
                        //y nosotros queremos que se muestre en orden con la última posición pedida arriba.
                        Collections.reverse(peticiones);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }


}
