package com.example.usuario.mynavigationdrawer;

import com.example.usuario.mynavigationdrawer.Model.Elemento;

/**
 * Created by USUARIO on 08/01/2018.
 */

//Se encargará de comunicar los dos fragments
public interface IComunica_Fragments {

    //Método para pasar el objeto de un Fragment a otro
    public void enviarElemento(Elemento elemento);

}
