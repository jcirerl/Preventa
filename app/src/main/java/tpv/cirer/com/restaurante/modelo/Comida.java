package tpv.cirer.com.restaurante.modelo;

import tpv.cirer.com.restaurante.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Comida {
    private String articulo;
    private float precio;
    private String nombre;
    private int idDrawable;
    private String urlimagen;
    private String tipo_are;
    private int tiva_id;
    private int individual;

    public Comida(float precio, String nombre, int idDrawable, String urlimagen, int individual) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.urlimagen = urlimagen;
        this.individual = individual;
    }
    public Comida() {
    }

    public static final List<Comida> COMIDAS_POPULARES = new ArrayList<Comida>();
    public static final List<Comida> BEBIDAS = new ArrayList<>();
    public static final List<Comida> POSTRES = new ArrayList<>();
    public static final List<Comida> PLATILLOS = new ArrayList<>();

    static {
        COMIDAS_POPULARES.add(new Comida(5, "Camarones Tismados", R.drawable.camarones,"",1));
        COMIDAS_POPULARES.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca,"",1));
        COMIDAS_POPULARES.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi,"",1));
        COMIDAS_POPULARES.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich,"",1));
        COMIDAS_POPULARES.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo,"",1));

        PLATILLOS.add(new Comida(5, "Camarones Tismados", R.drawable.camarones,"",1));
        PLATILLOS.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca,"",1));
        PLATILLOS.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi,"",1));
        PLATILLOS.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich,"",1));
        PLATILLOS.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo,"",1));

        BEBIDAS.add(new Comida(3, "Taza de Café", R.drawable.cafe,"",1));
        BEBIDAS.add(new Comida(12, "Coctel Tronchatoro", R.drawable.coctel,"",1));
        BEBIDAS.add(new Comida(5, "Jugo Natural", R.drawable.jugo_natural,"",1));
        BEBIDAS.add(new Comida(24, "Coctel Jordano", R.drawable.coctel_jordano,"",1));
        BEBIDAS.add(new Comida(30, "Botella Vino Tinto Darius", R.drawable.vino_tinto,"",1));

        POSTRES.add(new Comida(2, "Postre De Vainilla", R.drawable.postre_vainilla,"",1));
        POSTRES.add(new Comida(3, "Flan Celestial", R.drawable.flan_celestial,"",1));
        POSTRES.add(new Comida(2.5f, "Cupcake Festival", R.drawable.cupcakes_festival,"",1));
        POSTRES.add(new Comida(4, "Pastel De Fresa", R.drawable.pastel_fresa,"",1));
        POSTRES.add(new Comida(5, "Muffin Amoroso", R.drawable.muffin_amoroso,"",1));
    }

    public float getPrecio() {
        return precio;
    }
    public int getTiva_id() {
        return tiva_id;
    }
    public int getIndividual() {
        return individual;
    }
    public void setIndividual(int comidaindividual) {
        this.individual = comidaindividual;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
    public void setNombre(String comidanombre) {
        this.nombre = comidanombre;
    }
    public void setPrecio(float comidaprecio) {
        this.precio = comidaprecio;
    }
    public void setTiva_id(int comidativa_id) {
        this.tiva_id = comidativa_id;
    }

    public String getUrlimagen() {
        return urlimagen;
    }
    public void setUrlimagen(String comidaurlimagen) {
        this.urlimagen = comidaurlimagen;
    }
    public String getTipo_are() {
        return tipo_are;
    }
    public void setTipo_are(String comidatipo_are) {
        this.tipo_are = comidatipo_are;
    }
    public String getArticulo() {
        return articulo;
    }
    public void setArticulo(String comidaarticulo) {
        this.articulo = comidaarticulo;
    }


}
