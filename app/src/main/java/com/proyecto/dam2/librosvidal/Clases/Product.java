package com.proyecto.dam2.librosvidal.Clases;

import android.widget.ImageView;

/**
 * Created by david on 11/4/16.
 */
public class Product {
    int id;
    String titol;
    String descripcio;
    double preu;
    ImageView foto;
    boolean peticio;
    boolean venta;
    boolean intercanvi;


    public Product(int id, String titol, String descripcio, double preu, boolean peticio, boolean venta, boolean intercanvi) {
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.preu = preu;
        this.peticio = peticio;
        this.venta = venta;
        this.intercanvi = intercanvi;
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

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
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
