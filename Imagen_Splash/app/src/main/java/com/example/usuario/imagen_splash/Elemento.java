package com.example.usuario.imagen_splash;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//Implementamos Serializable para poder transportar un objeto de la clase de un fragment a otro
public class Elemento implements Serializable {

    //Variables de la clase Elemento
    private String nombre;
    private int imagen;
    private int telefono;

    //Constructor de la clase
   public Elemento (String nombre, int imagen, int telefono){
       this.nombre=nombre;
       this.imagen=imagen;
       this.telefono=telefono;
    }

    //GETTERS AND SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }


}
