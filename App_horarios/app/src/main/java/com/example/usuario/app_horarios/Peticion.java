package com.example.usuario.app_horarios;

/**
 * Created by USUARIO on 30/03/2018.
 */

public class Peticion {

    public String id, firstDay, lastDay, firstTime, lastTime, reason, userId, nombre;

    public Peticion(String id, String firstDay, String lastDay, String firstTime, String lastTime, String reason, String userId) {
        this.id = id;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.reason = reason;
        this.userId = userId;
    }

    public Peticion() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
