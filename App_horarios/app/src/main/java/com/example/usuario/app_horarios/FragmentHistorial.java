package com.example.usuario.app_horarios;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class FragmentHistorial extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the ajustes initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Declaramos sin iniciar el RecyclerView, el Adapatador y el ArrayList
    private RecyclerView rv;
    private Adaptador adapter;
    private ArrayList<Peticion> peticiones;

    private DatabaseReference bbdd;

    private FirebaseUser user;

    private ImageButton deleteButton;

    public FragmentHistorial() {
        // Required empty public constructor
    }


    public static FragmentHistorial newInstance(String param1, String param2) {
        FragmentHistorial fragment = new FragmentHistorial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Cargamos el layout
        View vista =inflater.inflate(R.layout.fragment_historial, container, false);

        peticiones = new ArrayList<>();
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_peticiones));

        deleteButton = (ImageButton) vista.findViewById(R.id.imageButtonDelete);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //Iniciamos el objeto RecyclerView con el Recycler de la interfaz .xml
        rv= (RecyclerView) vista.findViewById(R.id.RECYCLER_VIEW);

        //Creamos un objeto LinearLayoutManager, le decimos que tiene que ser vertical y se lo pasamos al RecyclerView
        LinearLayoutManager lim = new LinearLayoutManager(getContext());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lim);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                lim.getOrientation());

        rv.addItemDecoration(dividerItemDecoration);
        //Llamamos a los métodos data() y incializaAdapatador()
        data();
        inicializaAdaptador();

        return vista;
    }


    //Método en cual introducimos elementos en el ArrayList para pasárselos al Recycler
    //Los vamos creando pasando datos al constructor de la clase Petición
    public void data(){

        //Ahora vamos con la selección de peticiones para cada empleado
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                peticiones.clear();
                //Recorremos cada uno de las peticiones,
                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                    Peticion peticion = dataSnapshot2.getValue(Peticion.class);
                    //id del usuario actual
                    String currentId = user.getUid();
                    //Si coincide el empleado de la petición con el empleado actual..
                    if(peticion.getUserId().equals(currentId)){
                        peticiones.add(peticion);
                        Collections.reverse(peticiones);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });


    }

    //Método en el cual inicalizamos el adaptador
    public void inicializaAdaptador(){
        adapter= new Adaptador(peticiones);
        rv.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * ajustes to allow an interaction in this ajustes to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
