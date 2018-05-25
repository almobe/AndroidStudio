package com.example.usuario.app_horarios;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by USUARIO on 27/11/2017.
 */

public class Principal extends AppCompatActivity implements FragmentCalendario.OnFragmentInteractionListener, FragmentHistorial.OnFragmentInteractionListener {

    //private TextView nombreTool;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    MyPagerAdapter myPagerAdapter;
    private Button buttonConfig;

    private TextView nombreTool;
    Empleado empleado, empleadoActual;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        nombreTool = (TextView) findViewById(R.id.nombre_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        buttonConfig = (Button) findViewById(R.id.user_toolbar);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);


        //Declaración de las tabs que darán acceso a los diferentes fragments
        //Aquí introducimos los iconos de las tabs
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_today_white_18dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history_white_18dp);

        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_empleados));

        empleadoActual = new Empleado();

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Recorremos todos los empleados, la condición utilizada en este caso para hacer
                //el "match" del empleado actual es que el mail sea igual al mail del login.
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    empleado = datasnapshot.getValue(Empleado.class);
                    Log.i("Nombre", empleado.getNombre());
                    if (empleado.getEmail().equals(user.getEmail())){
                        Log.i("Nombre actual", empleado.getNombre());
                        empleadoActual.setNombre(empleado.getNombre());
                        empleadoActual.setApellidos(empleado.getApellidos());
                        empleadoActual.setEmail(empleado.getEmail());
                        empleadoActual.setRestaurante(empleado.getRestaurante());
                        empleadoActual.setId(empleado.getId());
                        nombreTool.setText("Hola \n"+empleadoActual.getNombre());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Botón de arriba a la derecha que da acceso al activity de ajustes de la app
        buttonConfig.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent new_intent = new Intent(Principal.this, Ajustes.class);
                startActivity(new_intent);
            }

        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        Fragment fragment;

        //Función que devuelve el fragment del tab seleccionado
        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    fragment = new FragmentCalendario();
                    break;
                case 1:
                    fragment = new FragmentHistorial();
                    break;
                default:
                    fragment = null;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        //Método que aplica un nombre al tab
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "CALENDARIO";
                case 1:
                   return "HISTORIAL";
            }
            return null;
        }
    }
}