package com.example.usuario.imagen_splash;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Declaramos sin iniciar el RecyclerView, el Adapatador y el ArrayList
    RecyclerView rv;
    Adaptador ARVElemento;
    private ArrayList<Elemento> elementos;

    //Referencia a la interface
    IComunica_Fragments interfaceComunicaFragments;

    //Crear referencia a Activity que nos va a permitir tener el contexto de nuestra app
    Activity activity;

    public MyFragment() {

    }

    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
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
        View vista =inflater.inflate(R.layout.my_fragment, container, false);

        //Iniciamos ArrayList;
        elementos = new ArrayList<>();

        //Iniciamos el objeto RecyclerView con el Recycler de la interfaz .xml
        rv= (RecyclerView) vista.findViewById(R.id.RECYCLER_VIEW);

        //Creamos un objeto LinearLayoutManager, le decimos que tiene que ser vertical y se lo pasamos al RecyclerView
        LinearLayoutManager lim = new LinearLayoutManager(getContext());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lim);

        //Llamamos a los métodos data() y incializaAdapatador()
        data();
        inicializaAdaptador();

        ARVElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Seleccion: "+
                        elementos.get(rv.
                                getChildAdapterPosition(view)).getNombre(),Toast.LENGTH_SHORT).show();

                //Enviamos el objeto personaje seleccionado con la interface como puente
                interfaceComunicaFragments.enviarElemento(elementos.get(rv.getChildAdapterPosition(view)));
            }
        });


        return vista;
    }

    //Método en cual introducimos elementos en el ArrayList para pasárselos al Recycler
    //Los vamos creando pasando datos al constructor de la clase Elemento
    public void data(){

        elementos.add(new Elemento("Ghost",R.drawable.greenghost,612345789));
        elementos.add(new Elemento("Iker Jimenez",R.drawable.iker,698745612));
        elementos.add(new Elemento("JJ Benitez",R.drawable.jj,635753951));
        elementos.add(new Elemento("Ghostbusters",R.drawable.caza,654789321));
        elementos.add(new Elemento("Police",R.drawable.policia,666999888));
        elementos.add(new Elemento("Bruja Lola",R.drawable.bruja,902902902));
        elementos.add(new Elemento("Sandro Rey",R.drawable.brujo,906906906));
        elementos.add(new Elemento("Bruce Willis",R.drawable.bruce,654164937));
        elementos.add(new Elemento("Bomberos",R.drawable.bombero,652316954));

    }

    //Método en el cual inicalizamos el adaptador
    public void inicializaAdaptador(){
        ARVElemento= new Adaptador(elementos);
        rv.setAdapter(ARVElemento);
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

        /*Si el contexto que está lllegando es una instancia de un activity...
        ...voy a decirle a la actividad creada que sea igual a ese contexto...
        ...y le vamos a decir al objeto de la interfaz que sea igual al contexto de la actividad
        */
        if (context instanceof Activity){
            this.activity= (Activity) context;
            interfaceComunicaFragments = (IComunica_Fragments) this.activity;
        }

        if (context instanceof Fragment_Dinamico.OnFragmentInteractionListener) {
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
     * fragment to allow an interaction in this fragment to be communicated
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
