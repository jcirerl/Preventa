package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 02/01/2017.
 */

public class Cruge {
    private String CrugeUser;
    private String CrugeAction;
    public Cruge(){}

    public Cruge( String action){
        this.CrugeAction = action;
    }
    public String getAction() {
        return CrugeAction;
    }

}
