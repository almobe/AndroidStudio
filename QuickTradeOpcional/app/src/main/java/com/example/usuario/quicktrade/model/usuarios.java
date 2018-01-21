package com.example.usuario.quicktrade.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.quicktrade.R;

/**
 * Created by USUARIO on 12/12/2017.
 */

public class usuarios {

    private String nombre_usuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;


    public usuarios() {

    }

    public usuarios(String nombre_usuario, String nombre, String apellidos, String correo, String direccion) {
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.direccion = direccion;

    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
