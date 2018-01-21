package com.example.usuario.quicktrade.model;

/**
 * Created by USUARIO on 02/01/2018.
 */

public class Producto {
    private String Nombre;
    private String Descripcion;
    private String Categoria;
    private double Precio;
    private String Usuario;

    public Producto(){

    }

    public Producto (String n, String d, String c, double p, String usuario){
        this.Nombre=n;
        this.Descripcion=d;
        this.Categoria=c;
        this.Precio=p;
        this.Usuario=usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
