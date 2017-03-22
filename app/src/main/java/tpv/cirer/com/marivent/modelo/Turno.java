package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 18/10/2016.
 */

public class Turno {
    private String TurnoCod_turno;
    private String TurnoNombre_Turno;
    private int TurnoApertura;
    private String TurnoFecha_Apertura;
    private String TurnoUrlimagen;
    private int TurnoId;
    public Turno(){}

    public Turno(String Cod_turno, String nombre){
        this.TurnoCod_turno = Cod_turno;
        this.TurnoNombre_Turno = nombre;
    }
    public int getTurnoId() {
        return TurnoId;
    }

    public void setTurnoId(int turnoId) {
        this.TurnoId = turnoId;
    }

    public int getTurnoApertura() {
        return TurnoApertura;
    }

    public void setTurnoApertura(int turnoApertura) {
        this.TurnoApertura = turnoApertura;
    }

    public String getTurnoNombre_Turno() {
        return TurnoNombre_Turno;
    }

    public void setTurnoNombre_Turno(String turnoNombre_Turno) {
        this.TurnoNombre_Turno = turnoNombre_Turno;
    }

    public String getTurnoCod_turno() {
        return TurnoCod_turno;
    }

    public void setTurnoCod_turno(String TurnoCod_turno) {
        this.TurnoCod_turno = TurnoCod_turno;
    }
    public void setTurnoUrlimagenopen(String turnourlimagenopen) {
        this.TurnoUrlimagen = turnourlimagenopen;
    }
    public String getTurnoUrlimagenopen() {
        return TurnoUrlimagen;
    }
    public String getTurnoUrlimagenclose() {
        return TurnoUrlimagen;
    }

    public void setTurnoUrlimagenclose(String turnourlimagenclose) {
        this.TurnoUrlimagen = turnourlimagenclose;
    }
    public String getTurnoFecha_Apertura() {
        return TurnoFecha_Apertura;
    }

    public void setTurnoFecha_Apertura(String TurnoFecha_Apertura) {
        this.TurnoFecha_Apertura = TurnoFecha_Apertura;
    }

}
