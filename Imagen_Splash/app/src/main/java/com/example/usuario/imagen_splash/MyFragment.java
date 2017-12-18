package com.example.usuario.imagen_splash;

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

    //Declaramos sin iniciar el RecyclerView, el Adapatador y el ArrayList
    RecyclerView rv;
    Adaptador ARVElemento;
    private List<Elemento> elementos;



    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    //Pasamos la info que teníamos anteriormente en el MainActivity a este método del Fragment
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);



        //Iniciamos el objeto RecyclerView con el Recycler de la interfaz .xml
        //Haciendo previamente un getActivity()
        rv= (RecyclerView)getActivity().findViewById(R.id.RECYCLER_VIEW);

        //Creamos un objeto LinearLayoutManager, le decimos que tiene que ser vertical y se lo pasamos al RecyclerView
        LinearLayoutManager lim = new LinearLayoutManager(getActivity());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lim);

        //Llamamos a los métodos data() y incializaAdapatador()
        data();
        inicializaAdaptador();

    }

    //Método que inicializa el ArrayList y en cual le pasamos los elementos que queremos que cree
    //y que pase al RecyclerView
    public void data(){
        elementos = new ArrayList();

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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

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
