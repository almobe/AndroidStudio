package com.example.usuario.mynavigationdrawer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.mynavigationdrawer.Model.Elemento;
import com.example.usuario.mynavigationdrawer.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Dinamico.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Dinamico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Dinamico extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Cosas que queremos presentar en el fragment
    private ImageView imagen;
    private TextView nombre;
    private TextView telefono;

    public Fragment_Dinamico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dinamico.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dinamico newInstance(String param1, String param2) {
        Fragment_Dinamico fragment = new Fragment_Dinamico();
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

        //Inflamos el fragmento con el layout
        View vista = inflater.inflate(R.layout.fragment_dinamico, container, false);

        imagen = (ImageView) vista.findViewById(R.id.imagen_dinamica);
        nombre = (TextView) vista.findViewById(R.id.nombre_dinamico);
        telefono = (TextView) vista.findViewById(R.id.telefono_dinamico);

        //Generamos Bundle para recibir el objeto
        Bundle objetoElemento=getArguments();
        Elemento elemento = null;

        //Si el Bundle no está vacio
        if(objetoElemento != null){

            //El elemento será igual al que recibimos
            elemento = (Elemento) objetoElemento.getSerializable("objeto");

            //Método para asignar información en los elementos, información que recibimos del Bundle
            asignarInformacion(elemento);
        }

        return vista;
    }

    public void asignarInformacion(Elemento elemento){
        imagen.setImageResource(elemento.getImagen());
        nombre.setText(elemento.getNombre());
        telefono.setText(String.valueOf(elemento.getTelefono()));
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
