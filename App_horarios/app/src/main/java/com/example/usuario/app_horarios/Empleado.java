package com.example.usuario.app_horarios;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USUARIO on 27/02/2018.
 */

public class Empleado implements Parcelable {

    public String nombre;
    public String apellidos;
    public String email;
    public String restaurante;
    public String startDate;
    public String id;
    public String rol;

    public Empleado(String nombre, String apellidos, String email, String restaurante, String startDate, String id, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.restaurante = restaurante;
        this.startDate = startDate;
        this.id = id;
        this.rol = rol;
    }

    public Empleado() {
    }

    protected Empleado(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        email = in.readString();
        restaurante = in.readString();
        startDate = in.readString();
        id = in.readString();
        rol = in.readString();
    }


    public static final Creator<Empleado> CREATOR = new Creator<Empleado>() {
        @Override
        public Empleado createFromParcel(Parcel in) {
            return new Empleado(in);
        }

        @Override
        public Empleado[] newArray(int size) {
            return new Empleado[size];
        }
    };

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        restaurante = restaurante;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(email);
        dest.writeString(restaurante);
        dest.writeString(startDate);
        dest.writeString(id);
        dest.writeString(rol);
    }
}
