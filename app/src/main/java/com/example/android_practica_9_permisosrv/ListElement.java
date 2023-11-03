package com.example.android_practica_9_permisosrv;

import java.io.Serializable;

public class ListElement {

    public String permiso;
    public boolean switchState;

    public ListElement(String permiso) {
        this.permiso = permiso;
        this.switchState = false;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
}
