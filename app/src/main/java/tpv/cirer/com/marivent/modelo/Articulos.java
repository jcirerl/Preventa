package tpv.cirer.com.marivent.modelo;

import android.graphics.drawable.Drawable;

/**
 * Created by JUAN on 31/01/2017.
 */

public class Articulos {
    private String name;
    private String code;
    private Drawable imagen;
    public Articulos(String name, String code, Drawable imagen){
        this.name = name;
        this.code = code;
        this.imagen = imagen;
    }
    public String getName() {
        return name;
    }
    public Drawable getImagen() {
        return imagen;
    }
    public String getCode() {
        return code;
    }

}

