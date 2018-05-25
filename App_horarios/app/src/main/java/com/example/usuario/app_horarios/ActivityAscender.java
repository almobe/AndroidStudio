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


//#################################################################
//Activity que utilizamos como ejemplo de las funcionalidades que se pueden implementar en la parte
//de la app destinada a la gerencia.
//#################################################################

public class ActivityAscender extends AppCompatActivity {


    private ArrayList<Empleado> arrayEmpleados;


    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    Empleado empleado;

    //Declaramos sin iniciar el RecyclerView, el Adapatador y el ArrayList
    private RecyclerView rv;
    private AdaptadorRoles adapter;
    /*Importante el adapter, ya que este activity lo único que va a hacer es sacar los empleados a
    un array y pasárselos al adapter*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ascenso);

        //Declaración de instancia de BBDD de empleados
        bbdd = FirebaseDatabase.getInstance().getReference("empleados");

        rv= (RecyclerView) findViewById(R.id.RECYCLER_VIEW_ASCENSOS);

        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lim);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                lim.getOrientation());

        rv.addItemDecoration(dividerItemDecoration);

        arrayEmpleados = new ArrayList<Empleado>();


        //Método para sacar los nombres de usuarios por el spinner
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Ante cualquier modificación, se limpia el array
                arrayEmpleados.clear();

                //Con un bucle for por cada nombre de usuario que encuentre lo añade al array
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    empleado = datasnapshot.getValue(Empleado.class);
                    String nombreEmpleado = empleado.getNombre();
                    if (empleado.getRol().equals("crew")) {
                        arrayEmpleados.add(empleado);
                    }
                }

                adapter = new AdaptadorRoles(arrayEmpleados);
                rv.setAdapter(adapter);
                //Sobre el recycler se hace un setAdapter del adaptador anteriormente iniciado
                //rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });



    }
}
