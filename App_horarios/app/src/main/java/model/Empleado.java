package model;

import android.os.Parcel;
import android.os.Parcelable;

/*
*Creamos la clase Empleado con las variables que puede llegar a tener, igualmente creamos el constructor
* pasándole por parámetro las preguntas que hacemos en el registro y le añadimos los setters y los
* getters para poder acceder a los elementos encapsulados.
* Con la ayuda de la web www.parcelable.com hacemos la clase Parcelable para permitir el paso de objetos
* de esta clase entre Activitys.
 */

public class Empleado implements Parcelable {

    private String id="";
    private String nombre="";
    private String apellidos="";
    private String email="";
    private String password="";
    private int telefono=0;
    private String restaurante="";

    public Empleado (String n, String a, String e, String p, String r){
        this.nombre=n;
        this.apellidos=a;
        this.email=e;
        this.password=p;
        this.restaurante=r;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }


    //Elementos creados al hacer la clase Parcelable
    protected Empleado(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        email = in.readString();
        password = in.readString();
        telefono = in.readInt();
        restaurante = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(telefono);
        dest.writeString(restaurante);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Empleado> CREATOR = new Parcelable.Creator<Empleado>() {
        @Override
        public Empleado createFromParcel(Parcel in) {
            return new Empleado(in);
        }

        @Override
        public Empleado[] newArray(int size) {
            return new Empleado[size];
        }
    };
}
