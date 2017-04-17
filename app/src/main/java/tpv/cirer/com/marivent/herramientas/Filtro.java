package tpv.cirer.com.marivent.herramientas;

public class Filtro {
    private static int optab;
    private static int opintervalo;
    private static int cobrodesdefactura;
    private static String tipoplato;
    private static boolean oplog;
    private static boolean oppedidomesa;
    private static String filelog;
    private static boolean cabecera;
    private static String urlFirma;
    private static String roles;
    private static int nivelroles;
    private static String ip_url;
    private static String t_fra;
    private static String mimesa;
    private static String miempleado;
    private static String namespace;
    private static String dirreportname;
    private static String dirpdfname;
    private static String driver;
    private static String host;
    private static String dbname;
    private static String username;
    private static String pwdname;
    private static String uurl;
    private static String moneda;
    private static String simbolo;
    private static boolean hide_toolbar1;
    private static boolean hide_toolbar2;
    private static int height_toolbar1;
    private static int height_toolbar2;
    private static String tipo_are;
    private static String tag_fragment;
    private static String seccion;
    private static String usuario;
	private static String empresa;
	private static String local;
	private static String caja;
    private static String grupo;
    private static String rango;
    private static String cod_pais;
    private static String cod_provincia;
    private static String cod_poblacion;
    private static String cod_calle;
    private static String seleccion;
    private static String serie;
    private static int factura;
    private static int pedido;
    private static int ivaincluido;
    private static String turno;
    private static int apertura;
    private static boolean opbarramapas;
    private static boolean search;
    private static boolean inicio;
    private static String opidioma;
    private static int opgrid;
    private static int opmesas;
    private static int optoolbar;
    private static String opurl;
    private static String iconAPP;
    private static String nomEmpresa;
    private static String mesa;
    private static String fechaapertura;
    private static String fecha;
    private static String fechainicio;
    private static String fechafinal;
    private static String opcuentacorreo;
    private static String oppwdcuentacorreo;
    private static boolean oppin;
    private static String opkeypin;
    private static String conexion;
    private static String estado;
    private static String empleado;
    private static boolean lineas;
    private static int valuefactura;

    private static int printDeviceType;
    private static String printIp;
    private static int printInterval;
    private static String printPrinterName;
    private static int printLanguage;

    private static int colorItemZero;
    private static int colorItem;
    private static int id;


  	public Filtro(){
        optab = 0;
        cobrodesdefactura = 0;
        tipoplato="";
        oplog = false;
        opintervalo = 0;
        oppedidomesa = false;
        filelog = "";
        cabecera = false;
        id=0;
        urlFirma="";
        printInterval=0;
        printIp ="";
        printDeviceType=0;
        printPrinterName="";
        printLanguage=0;

        colorItem =0;
        colorItemZero=0;

        roles="";
        nivelroles = 0;
        ip_url="";
        t_fra="";
        mimesa="";
        miempleado="";
        rango="";
        namespace="";
        dirreportname="";
        dirpdfname="";
        driver="";
        host="";
        dbname="";
        username="";
        pwdname="";
        uurl="";
        moneda="";
        simbolo="";
        hide_toolbar1=false;
        hide_toolbar2=false;
        optoolbar=0;
        height_toolbar1 = 0;
        height_toolbar2 = 0;
        tipo_are="";
        tag_fragment = "";
        seccion = "";
        usuario = "";
  		empresa = "";
  		grupo = "";
        caja = "";
        local = "";
        cod_pais = "";
        cod_provincia = "";
        cod_poblacion = "";
        cod_calle = "";
        seleccion = "";
        serie = "";
        ivaincluido = 0;
        valuefactura = 0;
        factura = 0;
        pedido = 0;
        estado = "";
        apertura = 0;
        opbarramapas = false;
        inicio=false;
        opidioma = "ESP";
        opgrid = 0;
        opmesas = 0;
        opurl="";
        iconAPP="";
        nomEmpresa="";
        turno = "";
        mesa = "";
        opcuentacorreo="";
        oppwdcuentacorreo="";
        oppin=false;
        opkeypin="";
        conexion="";
        empleado="";
        fechaapertura="";
        fecha="";
        fechainicio="";
        fechafinal="";
        lineas=false;
    }
    public static String getTipoPlato() {
        return tipoplato;
    }
    public static void setTipoPlato(String idtipoplato) {
        tipoplato = idtipoplato;
    }

    public static boolean getOplog() {
        return oplog;
    }
    public static void setOplog(boolean idoplog) {
        oplog = idoplog;
    }
    public static boolean getOppedidomesa() {
        return oppedidomesa;
    }
    public static void setOppedidomesa(boolean idoppedidomesa) {
        oppedidomesa = idoppedidomesa;
    }
    public static void setOptab(int idoptab) {
        optab = idoptab;
    }
    public static int getOptab() { return optab;
    }
    public static void setOpintervalo(int idopintervalo) {
        opintervalo = idopintervalo;
    }
    public static int getOpintervalo() { return opintervalo;
    }

    public static String getFilelog() {
        return filelog;
    }
    public static void setFilelog(String idfilelog) {
        filelog = idfilelog;
    }
    public static String getUrlFirma() {
        return urlFirma;
    }
    public static void setUrlFirma(String idurlfirma) {
        urlFirma = idurlfirma;
    }
    public static int getId() { return id;
    }
    public static void setId(int idid) {
        id = idid;
    }
    public static void setCobroDesdeFactura(int idcobrodesdefactura) {
        cobrodesdefactura = idcobrodesdefactura;
    }
    public static int getCobroDesdeFactura() { return cobrodesdefactura;
    }
    public static void setNivelRoles(int idnivelroles) {
        nivelroles = idnivelroles;
    }
    public static int getNivelroles() { return nivelroles;
    }


    public static int getColorItemZero() { return colorItemZero;
    }
    public static void setColorItemZero(int coloritemzero) {
        colorItemZero = coloritemzero;
    }
    public static int getColorItem() {
        return colorItem;
    }
    public static void setColorItem(int coloritem) {
        colorItem = coloritem;
    }
    public static int getPrintdeviceType() {
        return printDeviceType;
    }
    public static void setPrintDeviceType(int idprintdevicetype) {
        printDeviceType = idprintdevicetype;
    }
    public static String getPrintIp() {
        return printIp;
    }
    public static void setPrintIp(String idprintip) {
        printIp = idprintip;
    }

    public static String getPrintPrinterName() {
        return printPrinterName;
    }
    public static void setPrintPrinterName(String idprintprintername) {
        printPrinterName = idprintprintername;
    }
    public static int getPrintLanguage() {
        return printLanguage;
    }
    public static void setPrintLanguage(int idprintlanguage) {
        printLanguage = idprintlanguage;
    }

    public static int getPrintInterval() {
        return printInterval;
    }
    public static void setPrintInterval(int idprintinterval) {
        printInterval = idprintinterval;
    }


    public static String getRoles() {
        return roles;
    }
    public static void setRoles(String idroles) {
        roles = idroles;
    }

    public static String getT_fra() {
        return t_fra;
    }
    public static void setT_fra(String idt_fra) {
        t_fra = idt_fra;
    }

    public static String getIp_url() {
        return ip_url;
    }
    public static void setIp_url(String idip_url) {
        ip_url = idip_url;
    }

    public static String getOpurl() {
        return opurl;
    }
    public static void setOpurl(String idopurl) {
        opurl = idopurl;
    }

    public static String getNamespace() {
        return namespace;
    }
    public static void setNamespace(String idnamespace) {
        namespace = idnamespace;
    }

    public static String getDirreportname() {
        return dirreportname;
    }
    public static void setDirreportname(String iddirreportname) {
        dirreportname = iddirreportname;
    }

    public static String getDirpdfname() {
        return dirpdfname;
    }
    public static void setDirpdfname(String iddirpdfname) {
        dirpdfname = iddirpdfname;
    }

    public static String getDriver() {
        return driver;
    }
    public static void setDriver(String iddriver) {
        driver = iddriver;
    }

    public static String getHost() {
        return host;
    }
    public static void setHost(String idhost) {
        host = idhost;
    }

    public static String getDbname() {
        return dbname;
    }
    public static void setDbname(String iddbname) {
        dbname = iddbname;
    }

    public static String getUsername() {
        return username;
    }
    public static void setUsername(String idusername) {
        username = idusername;
    }

    public static String getPwdname() {
        return pwdname;
    }
    public static void setPwdname(String idpwdname) {
        pwdname = idpwdname;
    }

    public static String getMoneda() {
        return moneda;
    }
    public static void setMoneda(String idmoneda) {
        moneda = idmoneda;
    }

    public static String getSimbolo() {
        return simbolo;
    }
    public static void setSimbolo(String idsimbolo) {
        simbolo = idsimbolo;
    }

    public static String getUsuario() {
        return usuario;
    }
    public static void setUsuario(String idusuario) {
        usuario = idusuario;
    }

    public static String getTag_fragment() {
        return tag_fragment;
    }
    public static void setTag_fragment(String idtag_fragment) {
        tag_fragment = idtag_fragment;
    }

    public static String getGrupo() {
        return grupo;
    }
    public static void setGrupo(String idgrupo) {
        grupo = idgrupo;
    }
    public static String getEmpresa() {
        return empresa;
    }
    public static void setEmpresa(String idempresa) {
        empresa = idempresa;
    }
    public static String getLocal() {
        return local;
    }
    public static void setLocal(String idlocal) {
        local = idlocal;
    }
    public static String getSeccion() {
        return seccion;
    }
    public static void setSeccion(String idseccion) {
        seccion = idseccion;
    }

	public static String getConexion() {
        return conexion;
    }
    public static void setConexion(String idconexion) {
        conexion = idconexion;
    }
    public static boolean getCabecera() {
        return cabecera;
    }
    public static void setCabecera(boolean idcabecera) {
        cabecera = idcabecera;
    }
    public static boolean getlineas() {
        return lineas;
    }
    public static void setlineas(boolean idlineas) {
        lineas = idlineas;
    }

    public static boolean getoppin() {
        return oppin;
     }
    public static void setoppin(boolean idoppin) {
        oppin = idoppin;
    }
	public static String getopkeypin() {
        return opkeypin;
    }
    public static void setopkeypin(String idkeypin) {
        opkeypin = idkeypin;
    }    

  	public static String getopcuentacorreo() {
        return opcuentacorreo;
    }
    public static void setopcuentacorreo(String idcuentacorreo) {
        opcuentacorreo = idcuentacorreo;
    }
  	public static String getoppwdcuentacorreo() {
        return oppwdcuentacorreo;
    }
    public static void setoppwdcuentacorreo(String idpwdcuentacorreo) {
        oppwdcuentacorreo = idpwdcuentacorreo;
    }
  	
    public static String getTurno() {
        return turno;
     }
    public static void setTurno(String idturno) {
        turno = idturno;
    }

    public static String getRango() {
        return rango;
    }
    public static void setRango(String idrango) {
        rango = idrango;
    }

    public static String getMiempleado() {
        return miempleado;
    }
    public static void setMiempleado(String idmiempleado) {
        miempleado = idmiempleado;
    }
 
    public static String getMimesa() {
        return mimesa;
    }
    public static void setMimesa(String idmimesa) {
        mimesa = idmimesa;
    }

    public static String getMesa() {
        return mesa;
    }
    public static void setMesa(String idmesa) {
        mesa = idmesa;
    }

    public static String getEmpleado() {
        return empleado;
    }
    public static void setEmpleado(String idempleado) {
        empleado = idempleado;
    }

    public static String getFechaapertura() {
        return fechaapertura;
    }
    public static void setFechaapertura(String idfechaapertura) {
        fechaapertura = idfechaapertura;
    }

    public static String getFecha() {
        return fecha;
    }
    public static void setFecha(String idfecha) {
        fecha = idfecha;
    }
    public static String getFechaInicio() {
        return fechainicio;
    }
    public static void setFechaInicio(String idfechainicio) {
        fechainicio = idfechainicio;
    }
    public static String getFechaFinal() {
        return fechafinal;
    }
    public static void setFechaFinal(String idfechafinal) {
        fechafinal = idfechafinal;
    }

    public static String getIconAPP() {
        return iconAPP;
    }
    public static void setIconAPP(String idiconAPP) {
        iconAPP = idiconAPP;
    }
  	public static String getnomEmpresa() {
        return nomEmpresa;
    }
    public static void setNomEmpresa(String idnomEmpresa) {
        nomEmpresa = idnomEmpresa;
    }
  	
  	public static String getUrl() {
        return uurl;
    }
    public static void setUrl(String idurl) {
        uurl = idurl;
    }
    public static boolean getInicio() {
        return inicio;
    }
    public static void setInicio(boolean idinicio) {
        inicio = idinicio;
    }

    public static int getOpgrid() {
        return opgrid;
    }
    public static void setOpgrid(int idgrid) {
        opgrid = idgrid;
    }
    public static int getOptoolbar() {
        return optoolbar;
    }
    public static void setOptoolbar(int idoptoolbar) {
        optoolbar = idoptoolbar;
    }
    public static int getOpmesas() {
        return opmesas;
    }
    public static void setOpmesas(int idmesas) {
        opmesas = idmesas;
    }

  	public static String getIdioma() {
        return opidioma;
    }
    public static void setIdioma(String idopidioma) {
        opidioma = idopidioma;
    }
  	public static boolean getopbarramapas() {
        return opbarramapas;
     }
    public static void setopbarramapas(boolean idopbarramapas) {
        opbarramapas = idopbarramapas;
    }

    public static boolean getSearch() {
        return search;
    }
    public static void setSearch (boolean idsearch) {
        search = idsearch;
    }

    public static boolean getHide_toolbar1() {
        return hide_toolbar1;
    }
    public static void setHide_toolbar1 (boolean idhide_toolbar) {
        hide_toolbar1 = idhide_toolbar;
    }
    public static int getHeight_toolbar1() {
        return height_toolbar1;
    }
    public static void setHeight_toolbar1(int idheight) {
        height_toolbar1 = idheight;
    }
    public static boolean getHide_toolbar2() {
        return hide_toolbar2;
    }
    public static void setHide_toolbar2 (boolean idhide_toolbar) {
        hide_toolbar2 = idhide_toolbar;
    }
    public static int getHeight_toolbar2() {
        return height_toolbar2;
    }
    public static void setHeight_toolbar2(int idheight) {
        height_toolbar2 = idheight;
    }

  	public static String getSerie() {
        return serie;
     }
    public static void setSerie(String idserie) {
        serie = idserie;
    }

    public static int getFactura() {
        return factura;
    }
    public static void setFactura(int idfactura) {
        factura = idfactura;
    }

    public static int getValueFactura() {
        return valuefactura;
    }
    public static void setValueFactura(int idvaluefactura) {
        valuefactura = idvaluefactura;
    }

    public static int getIvaIncluido() {
        return ivaincluido;
    }
    public static void setIvaIncluido(int idivaincluido) {
        ivaincluido = idivaincluido;
    }

    public static int getPedido() {
        return pedido;
    }
    public static void setPedido(int idpedido) { pedido = idpedido; }
    public static String getCaja() {
        return caja;
    }
    public static void setCaja(String idcaja) {
        caja = idcaja;
    }
     public static String getPais() {
        return cod_pais;
    }
    public static void setPais(String Idpais) {
        cod_pais = Idpais;
    }
    public static String getProvincia() {
        return cod_provincia;
    }
    public static void setProvincia(String Idprovincia) {
        cod_provincia = Idprovincia;
    }
    public static String getPoblacion() {
        return cod_poblacion;
    }
    public static void setPoblacion(String Idpoblacion) {
        cod_poblacion = Idpoblacion;
    }
    public static String getCalle() {
        return cod_calle;
    }
    public static void setCalle(String Idcalle) {
        cod_calle = Idcalle;
    }

    public static String getEstado() {
        return estado;
    }
    public static void setEstado(String idestado) {
        estado = idestado;
    }

    public static String getTipo_are() {
        return tipo_are;
    }
    public static void setTipo_are(String idtipo_are) {
        tipo_are = idtipo_are;
    }

    public static int getApertura() {
        return apertura;
    }
    public static void setApertura(int idapertura) {
        apertura = idapertura;
    }

    public static String getSeleccion() {
        return seleccion;
    }
    public static void setSeleccion(String id_seleccion) {
        seleccion = id_seleccion;
    }

}
