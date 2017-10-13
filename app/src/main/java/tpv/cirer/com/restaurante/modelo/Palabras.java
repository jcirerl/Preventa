package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 21/01/2017.
 */

public class Palabras {
    private int PalabrasId;
    private String PalabrasIdioma;
    private String PalabrasClavestring;
    private String PalabrasPalabra;

    public Palabras(){}

    public Palabras(int id, String idioma, String clavestring, String palabra){
        this.PalabrasId=id;
        this.PalabrasIdioma=idioma;
        this.PalabrasClavestring = clavestring;
        this.PalabrasPalabra = palabra;
    }
    public int getPalabrasId() {
        return PalabrasId;
    }
    public void setPalabrasId(int palabrasId) {
        this.PalabrasId = palabrasId;
    }

    public void setPalabrasIdioma(String palabrasidioma) {
        this.PalabrasIdioma = palabrasidioma;
    }
    public String getPalabrasIdioma() {
        return PalabrasIdioma;
    }

    public void setPalabrasPalabra(String palabrasPalabras){
        this.PalabrasPalabra = palabrasPalabras;
    }

    public void setPalabrasClavestring(String palabrasClavestring){
        this.PalabrasClavestring = palabrasClavestring;
    }

    public String getPalabrasPalabra(){
        return this.PalabrasPalabra;
    }

    public String getPalabrasClavestring(){
        return this.PalabrasClavestring;
    }

}
