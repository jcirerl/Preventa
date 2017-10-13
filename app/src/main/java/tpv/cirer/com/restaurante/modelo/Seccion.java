package tpv.cirer.com.restaurante.modelo;

public class Seccion {

	private String SeccionSeccion;
	private String SeccionImage;
	private String SeccionNombre_Sec;
    private String SeccionFecha_Apertura;
    private String SeccionMax_Caja;
	private String SeccionUrlimagen;
	private int SeccionApertura;
	private int SeccionIvaIncluido;
    private int SeccionId;

    public Seccion(){}

    public Seccion(String seccion, String nombre, int ivaincluido){
        this.SeccionSeccion = seccion;
        this.SeccionNombre_Sec = nombre;
        this.SeccionIvaIncluido = ivaincluido;
    }
    public int getSeccionId() {
        return SeccionId;
    }

    public void setSeccionId(int seccionId) {
        this.SeccionId = seccionId;
    }

	public int getSeccionApertura() {
		return SeccionApertura;
	}

	public void setSeccionApertura(int seccionApertura) {
		this.SeccionApertura = seccionApertura;
	}
	public int getSeccionIvaIncluido() {
		return SeccionIvaIncluido;
	}

	public void setSeccionIvaIncluido(int seccionIvaIncluido) {
		this.SeccionIvaIncluido = seccionIvaIncluido;
	}

	public String getSeccionNombre_Sec() {
		return SeccionNombre_Sec;
	}

	public void setSeccionNombre_Sec(String seccionNombre_Sec) {
		this.SeccionNombre_Sec = seccionNombre_Sec;
	}
    public String getSeccionFecha_Apertura() {
        return SeccionFecha_Apertura;
    }

    public void setSeccionFecha_Apertura(String SeccionFecha_Apertura) {
        this.SeccionFecha_Apertura = SeccionFecha_Apertura;
    }
    public String getSeccionMax_Caja() {
        return SeccionMax_Caja;
    }

    public void setSeccionMax_Caja(String SeccionMax_Caja) {
        this.SeccionMax_Caja = SeccionMax_Caja;
    }

	public String getSeccionSeccion() {
		return SeccionSeccion;
	}

	public void setSeccionSeccion(String SeccionSeccion) {
		this.SeccionSeccion = SeccionSeccion;
	}

   public String getSeccionUrlimagenopen() {
        return SeccionUrlimagen;
    }

    public void setSeccionUrlimagenopen(String seccionurlimagenopen) {
        this.SeccionUrlimagen = seccionurlimagenopen;
    }
    public String getSeccionUrlimagenclose() {
        return SeccionUrlimagen;
    }

    public void setSeccionUrlimagenclose(String seccionurlimagenclose) {
        this.SeccionUrlimagen = seccionurlimagenclose;
    }

}