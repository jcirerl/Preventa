package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 15/10/2016.
 */

public class Local {
    private String LocalLocal;
    private String LocalNombre_local;
    private String LocalUrlimagen;
    private int LocalResize_logo_print_width;
    private int LocalResize_logo_print_height;
    private int LocalResize_logo_screen_width;
    private int LocalResize_logo_screen_height;
    private String LocalUrl_admin;
    private String LocalUrl_redsys;

    public Local(){}

    public Local(String local,
                 String nombre,
                 String urlimagen,
                 int localResize_logo_screen_width,
                 int localResize_logo_screen_height,
                 int localResize_logo_print_width,
                 int localResize_logo_print_height,
                 String localUrl_admin,
                 String localUrl_redsys){
        this.LocalLocal = local;
        this.LocalNombre_local = nombre;
        this.LocalUrlimagen=urlimagen;
        this.LocalResize_logo_screen_width = localResize_logo_screen_width;
        this.LocalResize_logo_screen_height = localResize_logo_screen_height;
        this.LocalResize_logo_print_width=localResize_logo_print_width;
        this.LocalResize_logo_print_height=localResize_logo_print_height;
        this.LocalUrl_admin = localUrl_admin;
        this.LocalUrl_redsys =localUrl_redsys;
    }
    public String getLocalUrl_redsys() {
        return LocalUrl_redsys;
    }
    public void setLocalUrl_redsys(String localUrlRedsys) {
        this.LocalUrl_redsys = localUrlRedsys;
    }
    public String getLocalUrl_admin() {
        return LocalUrl_admin;
    }
    public void setLocalUrl_admin(String localUrlAdmin) {
        this.LocalUrl_admin = localUrlAdmin;
    }
    public void setLocalUrlimagen(String localurlimagen) {
        this.LocalUrlimagen = localurlimagen;
    }
    public String getLocalUrlimagen() {
        return LocalUrlimagen;
    }
    public void setLocalResize_logo_screen_width(int localResize_logo_screen_width) {
        this.LocalResize_logo_screen_width = localResize_logo_screen_width;
    }
    public int getLocalResize_logo_screen_width() {
        return LocalResize_logo_screen_width;
    }
    public void setLocalResize_logo_screen_height(int localresize_logo_screen_height) {
        this.LocalResize_logo_screen_height = localresize_logo_screen_height;
    }
    public int getLocalResize_logo_screen_height() {
        return LocalResize_logo_screen_height;
    }

    public void setLocalResize_logo_print_width(int localresize_logo_print_width) {
        this.LocalResize_logo_print_width = localresize_logo_print_width;
    }
    public int getLocalResize_logo_print_width() {
        return LocalResize_logo_print_width;
    }
    public void setLocalResize_logo_print_height(int localresize_logo_print_height) {
        this.LocalResize_logo_print_height = localresize_logo_print_height;
    }
    public int getLocalResize_logo_print_height() {
        return LocalResize_logo_print_height;
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
