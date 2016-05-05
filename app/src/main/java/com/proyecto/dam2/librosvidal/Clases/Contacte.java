package com.proyecto.dam2.librosvidal.Clases;

import android.graphics.Bitmap;

/**
 * Created by david on 5/5/16.
 */
public class Contacte {
    String nom;
    String lastMsg;
    String imagePerfilCont;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImagePerfilCont() {
        return imagePerfilCont;
    }

    public void setImagePerfilCont(String imagePerfil) {
        this.imagePerfilCont = imagePerfil;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
