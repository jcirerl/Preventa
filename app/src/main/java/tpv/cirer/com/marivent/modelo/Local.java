package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 15/10/2016.
 */

public class Local {
    private String LocalLocal;
    private String LocalNombre_local;
    private String LocalUrlimagen;

    public Local(){}

    public Local(String local, String nombre, String urlimagen){
        this.LocalLocal = local;
        this.LocalNombre_local = nombre;
        this.LocalUrlimagen=urlimagen;
    }
    public void setLocalUrlimagen(String localurlimagen) {
        this.LocalUrlimagen = localurlimagen;
    }
    public String getLocalUrlimagen() {
        return LocalUrlimagen;
    }

    public void setLocalLocal(String localLocal){
        this.LocalLocal = localLocal;
    }

    public void setLocalNombre_local(String localNombre_local){
        this.LocalNombre_local = localNombre_local;
    }

    public String getLocalLocal(){
        return this.LocalLocal;
    }

    public String getLocalNombre_local(){
        return this.LocalNombre_local;
    }


}
