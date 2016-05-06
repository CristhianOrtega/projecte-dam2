package com.proyecto.dam2.librosvidal.Clases;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by david on 11/4/16.
 */
public class Product implements Serializable{
    int id;
    String titol;
    String descripcio;
    double preu;
    String foto;
    boolean peticio;
    boolean venta;
    boolean intercanvi;
    String regId;


    public Product(int id, String titol, String descripcio, double preu, boolean peticio, boolean venta, boolean intercanvi, String foto,String regId) {
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.preu = preu;
        this.peticio = peticio;
        this.venta = venta;
        this.intercanvi = intercanvi;
        this.foto = foto;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isPeticio() {
        return peticio;
    }

    public void setPeticio(boolean peticio) {
        this.peticio = peticio;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isIntercanvi() {
        return intercanvi;
    }

    public void setIntercanvi(boolean intercanvi) {
        this.intercanvi = intercanvi;
    }
}
