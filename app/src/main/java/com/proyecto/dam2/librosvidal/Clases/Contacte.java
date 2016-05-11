package com.proyecto.dam2.librosvidal.Clases;



/**
 * Created by david on 5/5/16.
 */
public class Contacte {
    String nom;
    String lastMsg;
    String imagePerfilCont;
    String REGIDFOR;


    public Contacte(String nom,String REGIDFOR) {
        this.nom = nom;
        this.REGIDFOR = REGIDFOR;
    }

    public String getREGIDFOR() {
        return REGIDFOR;
    }

    public void setREGIDFOR(String REGIDFOR) {
        this.REGIDFOR = REGIDFOR;
    }

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
