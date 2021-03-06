package tpv.cirer.com.restaurante.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.Formatter;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.epson.eposprint.Print;
import com.spyhunter99.supertooltips.ToolTip;
import com.spyhunter99.supertooltips.ToolTipManager;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import tpv.cirer.com.restaurante.BuildConfig;
import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.conexion_http_post.FileDownloader;
import tpv.cirer.com.restaurante.conexion_http_post.JSONParserNew;
import tpv.cirer.com.restaurante.herramientas.ArticulosListArrayAdapter;
import tpv.cirer.com.restaurante.herramientas.ArticulosListArrayAdapterNew;
import tpv.cirer.com.restaurante.herramientas.CargaFragment;
import tpv.cirer.com.restaurante.herramientas.DatePickerFragment;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.ProgressbarUtills;
import tpv.cirer.com.restaurante.herramientas.SerialExecutor;
import tpv.cirer.com.restaurante.herramientas.TaskHelper;
import tpv.cirer.com.restaurante.herramientas.Utils;
import tpv.cirer.com.restaurante.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.restaurante.modelo.Articulo;
import tpv.cirer.com.restaurante.modelo.ArticuloNotificacionTerminal;
import tpv.cirer.com.restaurante.modelo.Articulos;
import tpv.cirer.com.restaurante.modelo.ArticulosNew;
import tpv.cirer.com.restaurante.modelo.CabeceraEmpr;
import tpv.cirer.com.restaurante.modelo.Caja;
import tpv.cirer.com.restaurante.modelo.Categoria;
import tpv.cirer.com.restaurante.modelo.Comida;
import tpv.cirer.com.restaurante.modelo.Cruge;
import tpv.cirer.com.restaurante.modelo.Dcj;
import tpv.cirer.com.restaurante.modelo.DocumentoFactura;
import tpv.cirer.com.restaurante.modelo.DocumentoPedido;
import tpv.cirer.com.restaurante.modelo.Empleado;
import tpv.cirer.com.restaurante.modelo.Empresa;
import tpv.cirer.com.restaurante.modelo.Fac;
import tpv.cirer.com.restaurante.modelo.Fra;
import tpv.cirer.com.restaurante.modelo.Grupo;
import tpv.cirer.com.restaurante.modelo.LineaDocumentoFactura;
import tpv.cirer.com.restaurante.modelo.LineaDocumentoPedido;
import tpv.cirer.com.restaurante.modelo.Local;
import tpv.cirer.com.restaurante.modelo.Mesa;
import tpv.cirer.com.restaurante.modelo.Moneda;
import tpv.cirer.com.restaurante.modelo.Observacion;
import tpv.cirer.com.restaurante.modelo.Palabras;
import tpv.cirer.com.restaurante.modelo.Param;
import tpv.cirer.com.restaurante.modelo.Plato;
import tpv.cirer.com.restaurante.modelo.Popular;
import tpv.cirer.com.restaurante.modelo.Rango;
import tpv.cirer.com.restaurante.modelo.Seccion;
import tpv.cirer.com.restaurante.modelo.Simbolo;
import tpv.cirer.com.restaurante.modelo.Terminal;
import tpv.cirer.com.restaurante.modelo.TipoCobro;
import tpv.cirer.com.restaurante.modelo.Turno;
import tpv.cirer.com.restaurante.modelo.Userrel;
import tpv.cirer.com.restaurante.modelo.Zonas;
import tpv.cirer.com.restaurante.print.PrintTicket;
import tpv.cirer.com.restaurante.servicios.ServiceMesas;

import static tpv.cirer.com.restaurante.print.PrintTicket.getResizedBitmap;
import static tpv.cirer.com.restaurante.ui.LoginActivity.lcruge;
import static tpv.cirer.com.restaurante.ui.SplashScreen.lpalabras;

//import android.support.v7.app.AlertDialog;

public class ActividadPrincipal extends AppCompatActivity implements View.OnKeyListener,
                                                                     SearchView.OnQueryTextListener,
                                                                     AdapterView.OnItemSelectedListener,
                                                                     FragmentManager.OnBackStackChangedListener,
                                                                     AdaptadorCategorias.OnHeadlineSelectedListener,
                                                                     AdaptadorPopulares.OnHeadlineSelectedListener,
                                                                     ArticulosListArrayAdapterNew.OnHeadlineSelectedListenerArticulos,
                                                                     ArticulosListArrayAdapter.OnHeadlineSelectedListenerArticulos,
                                                                     AdaptadorSeccionHeader.OnHeadlineSelectedListenerSeccionHeader,
                                                                     AdaptadorSeccionHeader_ochopulgadas.OnHeadlineSelectedListenerSeccionHeader,
                                                                     AdaptadorCajaHeader.OnHeadlineSelectedListenerCajaHeader,
                                                                     AdaptadorTurnoHeader.OnHeadlineSelectedListenerTurnoHeader,
                                                                     AdaptadorDcjHeader.OnHeadlineSelectedListenerDcjHeader,
                                                                     AdaptadorDcjHeader_ochopulgadas.OnHeadlineSelectedListenerDcjHeader,
                                                                     AdaptadorDocumentoPedidoHeaderAsus.OnHeadlineSelectedListenerDocumentoPedidoHeader,
                                                                     AdaptadorDocumentoPedidoHeaderSony.OnHeadlineSelectedListenerDocumentoPedidoHeader,
                                                                     AdaptadorDocumentoPedidoHeaderOchoPulgadas.OnHeadlineSelectedListenerDocumentoPedidoHeader,
                                                                     AdaptadorDocumentoFacturaHeaderAsus.OnHeadlineSelectedListenerDocumentoFacturaHeader,
                                                                     AdaptadorDocumentoFacturaHeaderSony.OnHeadlineSelectedListenerDocumentoFacturaHeader,
                                                                     AdaptadorDocumentoFacturaHeaderOchoPulgadas.OnHeadlineSelectedListenerDocumentoFacturaHeader,
                                                                     AdaptadorMessageHeader.OnHeadlineSelectedListenerMessageHeader,
                                                                     AdaptadorLineaDocumentoFacturaHeaderAsus.OnHeadlineSelectedListenerLineaDocumentoFacturaHeader,
                                                                     AdaptadorLineaDocumentoFacturaHeaderSony.OnHeadlineSelectedListenerLineaDocumentoFacturaHeader,
                                                                     AdaptadorLineaDocumentoFacturaHeaderOchoPulgadas.OnHeadlineSelectedListenerLineaDocumentoFacturaHeader,
                                                                     AdaptadorDivisionLineaDocumentoFacturaHeaderSony.OnHeadlineSelectedListenerLineaDocumentoFacturaHeader,
                                                                     AdaptadorLineaDocumentoPedidoHeaderAsus.OnHeadlineSelectedListenerLineaDocumentoPedidoHeader,
                                                                     AdaptadorLineaDocumentoPedidoHeaderSony.OnHeadlineSelectedListenerLineaDocumentoPedidoHeader,
                                                                     AdaptadorLineaDocumentoPedidoHeaderOchoPulgadas.OnHeadlineSelectedListenerLineaDocumentoPedidoHeader{
    Intent intent;
    TextView txtMesaOpen;
    ImageView imagemesa;
    MesasResultReceiver resultReceiver;
    RelativeLayout layout;

    ToolTipManager tooltips;

    ProgressDialog pDialogtft,pDialogTipoare,pDialogUserrel,pDialogGrup,pDialogTerminal,pDialogFra,pDialogCruge,pDialogPlato,pDialogFac,pDialogPalabras,
            pDialogEmpr,pDialogLocal,pDialogSec,pDialogSecFechas,pDialogCaja,pDialogTurno,pDialogMesa,pDialogRango,pDialogEmpleado,pDialogMoneda;
    ProgressDialog pDialog,pDialogParam,pDialogZonas,pDialogAreNotif;
    public static ProgressbarUtills progressbarUtills;
    public static TextView itempedido;
    public static TextView itemseccion;
    public static TextView itemcaja;
    public static TextView itemturno;
    public static TextView itemdcj;
    public static TextView itemfactura;
    public static TextView itemmesas;
    public static TextView itemmessage;

    public static final String TAG_IP = "IP";
    private static final String TAG_PALABRAS = "Lista Palabras";
    private String url_palabras;

    public static String URL_TFTBUFFET;
    public static ArrayList<TipoCobro> tftbuffetList;
    public static ArrayList<TipoCobro> tftList;

    String TAG = "Nueva Linea";
    String TAG_GRUPO = "GRUPO: ";
    String TAG_EMPRESA = "EMPRESA: ";
    String TAG_LOCAL = "LOCAL: ";
    String TAG_SECCION = "SECCION: ";
    String TAG_PARAM = "PARAMETROS: ";
    String TAG_CAJA = "CAJA: ";
    String TAG_SERIE = "SERIE";
    String TAG_TURNO = "TURNO: ";
    String TAG_MESA="MESA: ";
    String TAG_EMPLEADO = "EMPLEADO: ";
    String TAG_MONEDA = "MONEDA: ";
    String TAG_RANGO = "RANGO: ";
    String TAG_TERMINAL = "TERMINAL";
    String TAG_FACTURA = "factura";
    String TAG_ID = "id";
    String TAG_MESA_FTP = "mesa";
    String TAG_FAC = "FACTURA";
    String TAG_TFT = "TFT: ";
    String TAG_ARENOTIF = "ARE NOTIF";

    public static boolean usuario_identificado;
    public static int position_usuario;

    // url to update Factura
    private static final String url_update_Factura = Filtro.getUrl()+"/modifica_cobro_factura_resta.php";

    // JSON Node names
    private static final String TAG_PID = "pid";
    private static final String TAG_ESTADO = "estado";
    private static final String TAG_TFRA = "t_fra";
    private static final String TAG_IMPCOBRO = "imp_cobro";
    private static final String TAG_IMPDIFERENCIA = "imp_diferencia";
    private static final String TAG_DTO = "dto";
    private static final String TAG_OBS = "obs";
    private static final String TAG_ZONAS = "ZONAS";
    
    // single Seccion url
    private static final String url_update_caja = Filtro.getUrl()+"/modifica_caja.php";
    private static final String url_update_turno = Filtro.getUrl()+"/modifica_turno.php";
    private static final String url_update_message = Filtro.getUrl()+"/modifica_message.php";
    private static final String url_create_cliente = Filtro.getUrl()+"/crear_cliente.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ULTIMOID = "ultimoid";

    SharedPreferences shared;
    ArrayList<String> arrPackage;

    public static String pid;
    String tabla;
    String lintabla;
    String preu;
    String nombre;
    String origenCobro;
    String saldo_inicio;
    String obs;
    String comensales;
    String valuemesa;
    String valueusermessage;
    String oldvaluemesa;
    String newvaluemesa;
    String valueempleado;
    String oldvalueempleado;
    String newvalueempleado;
    int indiceCategoria;
    public static String InUsuarios;
    public TextView txtBoxTotal;
    public TextView txtBoxTotalCobro;
    public TextView txtBoxTotalFactura;
    public TextView txtBoxDif;
    MenuItem buffetItem;

    boolean ArticuloGrupo = false;
    ImageView ArticuloImagenGrupo;
    ImageView logo;
    String ArticuloUrlImagen;
    String ArticuloCodigoGrupo;
    String ArticuloNombreGrupo;
    String ArticuloPrecioGrupo;
    String ArticuloTivaGrupo;
    boolean ArticuloBuffet = false;
    boolean ArticuloCobro = false;
    int ArticuloIdGrupo;
    int nArticuloPositionSelected;
    private List<Articulos> articulosList;
    public static List<ArticulosNew> articulosListNew;


    int idPedido;
    int idFactura;
    int idPosition;
    int factura_ftp;
    String serie_ftp;

    public static Bitmap imagelogo;
    public static Bitmap imagelogoprint;

    private ArrayList<Integer> mSelectedItems;
    private ArrayList<Integer> mSelectedItemsCant;
    private ArrayList<Integer> mSelectedItemsTipoPlato;
    private ArrayList<Float> mSelectedItemsPreu;
    private ArrayList<Integer> mSelectedItemsTipoare;

    MenuItem itemCarrito;
    public static LayerDrawable iconCarrito;
    private int mYear,mMonth,mDay;
    private StringBuilder nowYYYYMMDD;
    private StringBuilder nowDDMMYYYY;
    private SimpleDateFormat dateformatYYYYMMDD;
    private SimpleDateFormat dateformatDDMMYYYY;

    private TextView lblGrup;
    private TextView lblEmpr;
    private TextView lblLocal;
    private TextView lblSec;
    private TextView lblCaja;
    private TextView lblTurno;
    private TextView lblFra;
    private TextView lblRango;
    private TextView lblMesa;
    private TextView lblEmpleados;
    private TextView lblMoneda;
    private TextView lblTerminal;
    private TextView lblFecha;
    private TextView lblCarrito;

    public static TextView txtFecha_apertura;
    Button btnFecha;

    public static final String TAG_CATEGORIA = "Lista Categorias";
    private static final String TAG_USERREL = "Lista Usuarios Rel";
    private static final String TAG_CRUGE = "Lista Cruge";
    private static final String TAG_PLATO = "Lista Plato: ";

    public static List<Categoria> lcategoria;
    public static List<Userrel> luserrel;
//    public static List<Cruge> lcruge;
    public static List<Plato> lplato;
    public static List<Fac> lfac;
    public static List<Param> lparam;
    public static List<Zonas> lzonas;
    
    public static ArrayList<ArrayList<Comida>> comidas; //Definicion array de comidas
    public static List<Articulo> larticulo;
    public static ArrayList<ArrayList<Popular>> populares;
    private static ArrayList<Comida> lcomida;
    public static ArrayList<Popular> lpopular;
    public static ArrayList<ArrayList<Observacion>> observaciones; //Definicion array de observaciones articulos
    public static ArrayList<Observacion> lobservacion;
    public static List<CabeceraEmpr> lcabeceraempr;
    public static ArrayList<Articulo> larticulobuffet;
    public static ArrayList<ArticuloNotificacionTerminal> larticulonotificacionterminal;
    
    private static int IndiceSeccion;

    private String url_tipoare;
    private String url_userrel;
    private String url_cruge;
    private String url_platos;
    private String url_fac;

    public static MySerialExecutor mSerialExecutorActivity;

    public static String CountTable;

    // url to create new linea factuca factura
    private static String URL_GRUPOS;
    private static String URL_EMPRESAS;
    private static String URL_LOCALES;
    private static String URL_SECCIONES;
    private static String URL_SECCIONES_FECHAS;
    private static String URL_CAJAS;
    private static String URL_FRAS;
    private static String URL_TURNOS;
    private static String URL_RANGOS;
    private static String URL_MESAS;
    private static String URL_MESA_BUFFET;
    private static String URL_EMPLEADOS;
    private static String URL_MONEDAS;
    private static String URL_TERMINALES;
    private static String URL_ARTICULOS;
    private static String URL_ARTICULONOTIFICACIONTERMINAL;
    private static String URL_PARAMS;
    private static String URL_ZONAS;
    
    private static String url_create_ftp;
    private static String url_create_ftpcobro;
    private static String url_create_lft;
    private static String url_updatepreu_lft;
    private static String url_updatenombre_lft;

    private static String url_updatesaldo_inicio_dcj;
    private static String url_updateapertura_dcj;

    public static String url_count;

    private static String url_delete_id;
    private static String url_addcant_id;
    private static String url_minuscant_id;
    private static String url_update_cabecera;

    private static String url_create_lpd;
    private static String url_updateobs_lpd;

    private static String url_updateobs_ftp;
    private static String url_updateobs_pdd;
    private static String url_updatecomensales_pdd;
    private static String url_updatemesa_pdd;
    private static String url_updatemesa_ftp;
    private static String url_updateempleado_pdd;
    private static String url_updateempleado_ftp;

    private static String url_updatemesa_mesa;
    private static String url_pedido_a_factura_id;
    private static String url_ticket_a_factura;
    private static String urlCabeceraEmpr;

    private static ArrayList<Mesa> mesaList;
    private static ArrayList<Mesa> mesaListBuffet;
    private static String URL_MESA;

    private static ArrayList<Fra> frafacturaList;
    private static String URL_FRA;

    private ArrayList<Empleado> empleadoList;
    private static String URL_EMPLEADO;

    private ArrayList<Simbolo> simboloList;

    private ArrayList<Grupo> grupList;
    private Spinner cmbToolbarGrup;

    private ArrayList<Empresa> emprList;
    private Spinner cmbToolbarEmpr;

    public static ArrayList<Local> localList;
    private Spinner cmbToolbarLocal;

    private ArrayList<Seccion> secList;
    private Spinner cmbToolbarSec;

    private ArrayList<Caja> cajaList;
    private Spinner cmbToolbarCaja;

    private ArrayList<Turno> turnoList;
    private Spinner cmbToolbarTurno;

    private ArrayList<Fra> fraList;
    private Spinner cmbToolbarFra;

    private ArrayList<Rango> rangosList;
    private Spinner cmbToolbarRangos;

    public static ArrayList<Mesa> mesasList;
    private Spinner cmbToolbarMesas;

    public static ArrayList<Empleado> empleadosList;
    private Spinner cmbToolbarEmpleados;

    private ArrayList<Moneda> monedaList;
    private Spinner cmbToolbarMoneda;

    public static ArrayList<Terminal> terminalList;
    public static Spinner cmbToolbarTerminal;

    private static JSONParserNew jsonparsernew = null;

    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SALDO = "saldo";
    String TAG_LINEAS = "lineas";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private long backPressedTime = 0;    // used by onBackPressed()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));


        setContentView(R.layout.actividad_principal);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        usuario_identificado = true;
        position_usuario = 0;

        tooltips = new ToolTipManager(this);
        progressbarUtills = new ProgressbarUtills();

 /*        Toolbar toolbar1 = (Toolbar) findViewById(R.id.appbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_ricoparico); //also displays wide logo_ricoparico
        */
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

/*        shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        // add values for your ArrayList any where...
        arrPackage = new ArrayList<>();
        arrPackage.add("local1");
        arrPackage.add("servidor1");
        packagesharedPreferences();
        retriveSharedValue();
*/
        // CONTROL PEDIDOS DESDE MESA
        if (Filtro.getOppedidomesa()) {
            resultReceiver = new MesasResultReceiver(null);

            intent = new Intent(this, ServiceMesas.class);
            intent.putExtra("receiver", resultReceiver);
            startService(intent);
        }
        // VALORES DE PREFERENCIAS //
        Filtro.setInicio(true);

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(
                        ActividadPrincipal.this);
        Filtro.setOpgrid(Integer.parseInt(pref.getString("opgrid", "8")));
        Filtro.setOpmesas(Integer.parseInt(pref.getString("opmesas", "128")));
        Filtro.setOptipoarticulo(Float.parseFloat(pref.getString("optipoarticulo", "16.0")));
        Filtro.setOptoolbar(Integer.parseInt(pref.getString("optoolbar", "0")));
        Filtro.setOptab(Integer.parseInt(pref.getString("optab", "0")));
        Filtro.setOppedidomesa(Boolean.parseBoolean(pref.getString("oppedidomesa", "false")));

        Filtro.setOppedidodirectomesa(Boolean.parseBoolean(pref.getString("oppedidodirectomesa", "false")));
        Filtro.setOpfacturadirectomesa(Boolean.parseBoolean(pref.getString("opfacturadirectomesa", "false")));

        Filtro.setOpokprint(Boolean.parseBoolean(pref.getString("opokprint", "false")));

        Filtro.setOpintervalo(Integer.parseInt(pref.getString("opintervalo", "10000")));
        Filtro.setOplog(Boolean.parseBoolean(pref.getString("oplog", "true")));
        Filtro.setOptipotablet(Integer.parseInt(pref.getString("optipotablet", "0")));
        Filtro.setFilelog("");
        Log.i("oplog", Boolean.toString((Filtro.getOplog())));
        if (Filtro.getOptoolbar() == 0) {
            Filtro.setHide_toolbar1(false);
            Filtro.setHide_toolbar2(false);
        } else {
            Filtro.setHide_toolbar1(true);
            Filtro.setHide_toolbar2(true);
        }
////                    Log.i("pixels",Double.toString(checkDimension(getApplicationContext())));
        if (Filtro.getOplog()) {
            Log.i("oplog", Boolean.toString((Filtro.getOplog())));
            /// REGISTRAR LOG APLICACION
            String fileName = "logcat_" + System.currentTimeMillis() + ".txt";
            try {
                File outputFile = new File(Environment.getExternalStoragePublicDirectory("/www/tpv/log"), fileName);
                Filtro.setFilelog(outputFile.getAbsolutePath());
                @SuppressWarnings("unused")
                Process process = Runtime.getRuntime().exec("logcat -v time -f " + outputFile.getAbsolutePath() + " -r 32000 ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // VALORES PROVISIONALES PARA PRUEBAS //

/*        Filtro.setPrintDeviceType(Print.DEVTYPE_TCP);
        Filtro.setPrintIp("192.168.1.100");
        Filtro.setPrintInterval(1000);
        Filtro.setPrintLanguage(0);
        Filtro.setPrintPrinterName("TM-T20II");
*/
///        Filtro.setUrl("http://localhost:8080/tpv");
        ///    Filtro.setNamespace("http://jcirerl-001-site1.dtempurl.com/webserviceslinux/");
/*        Filtro.setNamespace("http://192.168.1.36:8082/");
        Filtro.setDirreportname("tpv/rpt/");
        Filtro.setDirpdfname("tpv/pdf/");
        Filtro.setDriver("MYSQL");
        Filtro.setHost("192.168.1.33");
        Filtro.setDbname("tpv_maritimo");
        Filtro.setUsername("root");
        Filtro.setPwdname("");
*/
        Calendar currentDate = Calendar.getInstance(); //Get the current date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //format it as per your requirement
        String dateNow = formatter.format(currentDate.getTime());

        Filtro.setFechaapertura(dateNow);
        Log.i("FechaApertura", Filtro.getFechaapertura());

        Filtro.setT_fra("");
        Filtro.setGrupo("");
        Filtro.setEmpresa("");
        Filtro.setLocal("");
        Filtro.setSeccion("");
        Filtro.setCaja("");
        Filtro.setTurno("");
        Filtro.setSerie("");
        Filtro.setRango("");
        Filtro.setMesa("");
        Filtro.setEmpleado("");
        Filtro.setFecha(dateNow);
        Filtro.setEstado("");
        Filtro.setFechaInicio("");
        Filtro.setFechaFinal("");
        Filtro.setMoneda("");
        Filtro.setSimbolo("");
        Filtro.setCod_cliente("");
        Filtro.setSerieFac("");

        Filtro.setTag_fragment("");
        Filtro.setValueFactura(10000000);
        Filtro.setSearch(false);
        Filtro.setColorItemZero(ContextCompat.getColor(getApplicationContext(), R.color.green_300));
        Filtro.setColorItem(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));

        // COLOR BARRA NAVIGATION ABAJO TABLBT
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryColor));
        }

        lfac = new ArrayList<Fac>();
        url_fac = Filtro.getUrl() + "/get_fac.php";

        // RELLENAMOS TIPOS DE PLATOS
        url_platos = Filtro.getUrl() + "/get_platos.php";
        lplato = new ArrayList<Plato>();

        // RELLENAMOS TIPOS DE ARTICULOS
        url_tipoare = Filtro.getUrl() + "/RellenaListaTiposArticulo.php";
        lcategoria = new ArrayList<Categoria>();

        // RELLENAMOS ARTICULOS NOTIFICACION TERMINAL
        URL_ARTICULONOTIFICACIONTERMINAL = Filtro.getUrl() + "/RellenaListaARENOTIFTERMINAL.php";
        larticulonotificacionterminal = new ArrayList<ArticuloNotificacionTerminal>();

        // RELLENAMOS ZONAS
        lzonas = new ArrayList<Zonas>();
        URL_ZONAS = Filtro.getUrl() + "/RellenaListaZonas.php";

        // RELLENAMOS MESAS BUFFET
        mesaListBuffet = new ArrayList<Mesa>();
        URL_MESA_BUFFET = Filtro.getUrl() + "/get_mesas.php";

        // Rellenar TIPO DE COBRO BUFFET
        tftbuffetList = new ArrayList<TipoCobro>();
        // Rellenar string toolbar_tft
        tftList = new ArrayList<TipoCobro>();
        URL_TFTBUFFET = Filtro.getUrl() + "/get_tiposcobro.php";


        URL_TFTBUFFET = Filtro.getUrl() + "/get_tiposcobro.php";
        //  RELACION USUARIO
        lparam = new ArrayList<Param>();
        URL_PARAMS = Filtro.getUrl() + "/RellenaListaPARAM.php";

        url_userrel = Filtro.getUrl() + "/RellenaListaUserrel.php";
        luserrel = new ArrayList<Userrel>();
        new GetUserrel().execute(url_userrel);

        // RELLENAMOS ACCIONES PERMITIDAS CRUGE (ahora lo carga de loginactivity
//        url_cruge = Filtro.getUrl() + "/RellenaListaCruge.php";
//        lcruge = new ArrayList<Cruge>();
//        new GetCruge().execute(url_cruge);


        // Rellenar string toolbar_grupo
        grupList = new ArrayList<Grupo>();
        // Rellenar string toolbar_empresa
        emprList = new ArrayList<Empresa>();
        lcabeceraempr = new ArrayList<CabeceraEmpr>();

        simboloList = new ArrayList<Simbolo>();
        // Rellenar string toolbar_local
        localList = new ArrayList<Local>();
        // Rellenar string toolbar_seccion
        secList = new ArrayList<Seccion>();
        // Rellenar string toolbar_caja
        cajaList = new ArrayList<Caja>();
        // Rellenar string toolbar_turno
        turnoList = new ArrayList<Turno>();
        // Rellenar string toolbar_fra
        fraList = new ArrayList<Fra>();
        frafacturaList = new ArrayList<Fra>();

        // Rellenar string toolbar_rango
        rangosList = new ArrayList<Rango>();
        // Rellenar string toolbar_mesas
        mesasList = new ArrayList<Mesa>();
        // Rellenar string toolbar_empleado
        empleadosList = new ArrayList<Empleado>();
        // Rellenar string toolbar_moneda
        monedaList = new ArrayList<Moneda>();
        // Rellenar string toolbar_terminal
        terminalList = new ArrayList<Terminal>();

        // Appbar page filter grupos
        lblGrup = (TextView) findViewById(R.id.LblGrup);
        lblGrup.setText(ValorCampo(R.id.LblGrup, lblGrup.getClass().getName()));
        cmbToolbarGrup = (Spinner) findViewById(R.id.CmbToolbarGrup);
        cmbToolbarGrup.setOnItemSelectedListener(this);
        URL_GRUPOS = Filtro.getUrl() + "/get_grupos.php";
///        new GetGrupos().execute(URL_GRUPOS);

        // Appbar page filter empresas
        lblEmpr = (TextView) findViewById(R.id.LblEmpr);
        lblEmpr.setText(ValorCampo(R.id.LblEmpr, lblEmpr.getClass().getName()));
        cmbToolbarEmpr = (Spinner) findViewById(R.id.CmbToolbarEmpr);

        cmbToolbarEmpr.setOnItemSelectedListener(this);
        URL_EMPRESAS = Filtro.getUrl() + "/get_empresas.php";

        //Appbar page filter Local
        lblLocal = (TextView) findViewById(R.id.LblLocal);
        lblLocal.setText(ValorCampo(R.id.LblLocal, lblLocal.getClass().getName()));
        cmbToolbarLocal = (Spinner) findViewById(R.id.CmbToolbarLocal);

        cmbToolbarLocal.setOnItemSelectedListener(this);
        URL_LOCALES = Filtro.getUrl() + "/get_locales.php";

        //Appbar page filter Sec
        lblSec = (TextView) findViewById(R.id.LblSec);
        lblSec.setText(ValorCampo(R.id.LblSec, lblSec.getClass().getName()));
        cmbToolbarSec = (Spinner) findViewById(R.id.CmbToolbarSec);

        cmbToolbarSec.setOnItemSelectedListener(this);
        URL_SECCIONES = Filtro.getUrl() + "/get_secciones.php";

        //Appbar page filter Caja
        lblCaja = (TextView) findViewById(R.id.LblCaja);
        lblCaja.setText(ValorCampo(R.id.LblCaja, lblCaja.getClass().getName()));
        cmbToolbarCaja = (Spinner) findViewById(R.id.CmbToolbarCaja);

        cmbToolbarCaja.setOnItemSelectedListener(this);
        URL_CAJAS = Filtro.getUrl() + "/get_cajas.php";

        //Appbar page filter Turno
        lblTurno = (TextView) findViewById(R.id.LblTurno);
        lblTurno.setText(ValorCampo(R.id.LblTurno, lblTurno.getClass().getName()));
        cmbToolbarTurno = (Spinner) findViewById(R.id.CmbToolbarTurno);

        cmbToolbarTurno.setOnItemSelectedListener(this);
        URL_TURNOS = Filtro.getUrl() + "/get_turnos.php";

        //Appbar page filter Fra
        lblFra = (TextView) findViewById(R.id.LblFra);
        lblFra.setText(ValorCampo(R.id.LblFra, lblFra.getClass().getName()));
        cmbToolbarFra = (Spinner) findViewById(R.id.CmbToolbarFra);

        cmbToolbarFra.setOnItemSelectedListener(this);
        URL_FRAS = Filtro.getUrl() + "/get_fras.php";

        //Appbar page filter Rango
        lblRango = (TextView) findViewById(R.id.LblRangos);
        lblRango.setText(ValorCampo(R.id.LblRangos, lblRango.getClass().getName()));
        cmbToolbarRangos = (Spinner) findViewById(R.id.CmbToolbarRangos);

        cmbToolbarRangos.setOnItemSelectedListener(this);
        URL_RANGOS = Filtro.getUrl() + "/get_rangos.php";

        //Appbar page filter Mesas
        lblMesa = (TextView) findViewById(R.id.LblMesa);
        lblMesa.setText(ValorCampo(R.id.LblMesa, lblMesa.getClass().getName()));
        cmbToolbarMesas = (Spinner) findViewById(R.id.CmbToolbarMesas);

        cmbToolbarMesas.setOnItemSelectedListener(this);
        URL_MESAS = Filtro.getUrl() + "/get_mesas.php";

        //Appbar page filter Empleado
        lblEmpleados = (TextView) findViewById(R.id.LblEmpleados);
        lblEmpleados.setText(ValorCampo(R.id.LblEmpleados, lblEmpleados.getClass().getName()));
        cmbToolbarEmpleados = (Spinner) findViewById(R.id.CmbToolbarEmpleados);

        cmbToolbarEmpleados.setOnItemSelectedListener(this);
        URL_EMPLEADOS = Filtro.getUrl() + "/get_empleados.php";
        //Appbar page filter Moneda
        lblMoneda = (TextView) findViewById(R.id.LblMoneda);
        lblMoneda.setText(ValorCampo(R.id.LblMoneda, lblMoneda.getClass().getName()));
        cmbToolbarMoneda = (Spinner) findViewById(R.id.CmbToolbarMoneda);

        cmbToolbarMoneda.setOnItemSelectedListener(this);
        URL_MONEDAS = Filtro.getUrl() + "/get_monedas.php";

        //Appbar page filter Terminal
        lblTerminal = (TextView) findViewById(R.id.LblTerminal);
        lblTerminal.setText(ValorCampo(R.id.LblTerminal, lblTerminal.getClass().getName()));
        cmbToolbarTerminal = (Spinner) findViewById(R.id.CmbToolbarTerminal);

        cmbToolbarTerminal.setOnItemSelectedListener(this);
        URL_TERMINALES = Filtro.getUrl() + "/get_terminales.php";

        // Appbar Datos Fecha
        URL_SECCIONES_FECHAS = Filtro.getUrl() + "/get_secciones_fechas.php";

        lblFecha = (TextView) findViewById(R.id.LblFecha);
        lblFecha.setText(ValorCampo(R.id.LblFecha, lblFecha.getClass().getName()));
        txtFecha_apertura = (TextView) findViewById(R.id.Fecha_apertura);
   //     txtFecha_apertura.setEnabled(false);
        String xfecha="";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

        try{
            // Get the date today using Calendar object.
            Date today = Calendar.getInstance().getTime();

            String StringRecogido = sdf1.format(today);;
            Date datehora = sdf1.parse(StringRecogido);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(datehora);
/////            cal1.add(Calendar.DATE, 1);  // number of days to add
            xfecha = sdf2.format(cal1.getTime());  // dt is now the new date

            //System.out.println("Fecha input : "+datehora);
            ///*              xfecha = sdf2.format(datehora);

        } catch (Exception e) {
            e.getMessage();
        }


        // display Seccion data in EditText
        txtFecha_apertura.setText(xfecha);

        btnFecha = (Button) findViewById(R.id.date);
        btnFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Eliminar Teclado Virtual
                InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                showDatePicker();
            }
        });
        ///////////////////////////////////////////////////////////////////


        url_delete_id = Filtro.getUrl()+"/delete_id.php";
        url_addcant_id = Filtro.getUrl()+"/addcant_id.php";
        url_minuscant_id = Filtro.getUrl()+"/minuscant_id.php";
        url_update_cabecera = Filtro.getUrl()+"/update_cabecera.php";

        url_create_ftp = Filtro.getUrl()+"/crea_ftp.php";
        url_create_ftpcobro = Filtro.getUrl()+"/crea_ftpcobro.php";
        url_create_lft = Filtro.getUrl()+"/crea_lft.php";
        url_updatepreu_lft = Filtro.getUrl()+"/updatepreu_lft.php";
        url_updatenombre_lft = Filtro.getUrl()+"/updatenombre_lft.php";

        url_updatesaldo_inicio_dcj = Filtro.getUrl()+"/updatesaldo_inicio_dcj.php";
        url_updateapertura_dcj = Filtro.getUrl()+"/updateapertura_dcj.php";

        url_create_lpd = Filtro.getUrl()+"/crea_lpd.php";
        url_updateobs_lpd = Filtro.getUrl()+"/updateobs_lpd.php";

        url_updateobs_pdd = Filtro.getUrl()+"/updateobs_pdd.php";
        url_updatecomensales_pdd = Filtro.getUrl()+"/updatecomensales_pdd.php";
        url_updatemesa_pdd = Filtro.getUrl()+"/updatemesa_pdd.php";
        url_updateempleado_pdd = Filtro.getUrl()+"/updateempleado_pdd.php";

        url_updateobs_ftp = Filtro.getUrl()+"/updateobs_ftp.php";
        url_updatemesa_ftp = Filtro.getUrl()+"/updatemesa_ftp.php";
        url_updateempleado_ftp = Filtro.getUrl()+"/updateempleado_ftp.php";

        url_updatemesa_mesa = Filtro.getUrl()+"/updatemesa_mesa.php";

        url_pedido_a_factura_id = Filtro.getUrl()+"/invoice_pdd_ftp.php";
        url_ticket_a_factura = Filtro.getUrl()+"/invoice_ftp_fac.php";

        /////////////////////////////////////////////
        /// leemos altura APPBARLAYOUT
        TypedValue tv = new TypedValue();
        getBaseContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);

        Log.i("APPBARLAYOUT: ",Integer.toString(actionBarHeight));
        ///////////////////////////////////////////////////
        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
        Menu menudrawer = navigationView.getMenu();

        MenuItem nav_grupseccion = menudrawer.findItem(R.id.grupitem_seccion);
        nav_grupseccion.setTitle(ValorCampo(R.id.grupitem_seccion,nav_grupseccion.getClass().getName()));

        itempedido=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_pedido));

        // find MenuItem you want to change
        MenuItem nav_pedido = menudrawer.findItem(R.id.item_pedido);
        // set new title to the MenuItem
        nav_pedido.setTitle(ValorCampo(R.id.item_pedido,nav_pedido.getClass().getName()));

        itemseccion=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_seccion));
        MenuItem nav_seccion = menudrawer.findItem(R.id.item_seccion);
        nav_seccion.setTitle(ValorCampo(R.id.item_seccion,nav_seccion.getClass().getName()));

        itemcaja=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_caja));
        MenuItem nav_caja = menudrawer.findItem(R.id.item_caja);
        nav_caja.setTitle(ValorCampo(R.id.item_caja,nav_caja.getClass().getName()));

        itemturno=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_turno));
        MenuItem nav_turno = menudrawer.findItem(R.id.item_turno);
        nav_turno.setTitle(ValorCampo(R.id.item_turno,nav_turno.getClass().getName()));

        itemdcj=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_dcj));
        MenuItem nav_dcj = menudrawer.findItem(R.id.item_dcj);
        nav_dcj.setTitle(ValorCampo(R.id.item_dcj,nav_dcj.getClass().getName()));

        itemfactura=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_factura));
        MenuItem nav_factura = menudrawer.findItem(R.id.item_factura);
        nav_factura.setTitle(ValorCampo(R.id.item_factura,nav_factura.getClass().getName()));

        MenuItem nav_inicio = menudrawer.findItem(R.id.item_inicio);
        nav_inicio.setTitle(ValorCampo(R.id.item_inicio,nav_inicio.getClass().getName()));

        MenuItem nav_salir = menudrawer.findItem(R.id.item_salir);
        nav_salir.setTitle(ValorCampo(R.id.item_salir,nav_salir.getClass().getName()));

        itemmesas=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_mesas));
        MenuItem nav_mesa = menudrawer.findItem(R.id.item_mesas);
        nav_mesa.setTitle(ValorCampo(R.id.item_mesas,nav_mesa.getClass().getName()));

        itemmessage=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.item_message));
        MenuItem nav_message = menudrawer.findItem(R.id.item_message);
        nav_message.setTitle(ValorCampo(R.id.item_message,nav_message.getClass().getName()));

        MenuItem nav_categoria = menudrawer.findItem(R.id.item_categorias);
        nav_categoria.setTitle(ValorCampo(R.id.item_categorias,nav_categoria.getClass().getName()));

        MenuItem nav_grupconfiguracion = menudrawer.findItem(R.id.grupitem_configuracion);
        nav_grupconfiguracion.setTitle(ValorCampo(R.id.grupitem_configuracion,nav_grupconfiguracion.getClass().getName()));

        MenuItem nav_configuracion = menudrawer.findItem(R.id.item_configuracion);
        nav_configuracion.setTitle(ValorCampo(R.id.item_configuracion,nav_configuracion.getClass().getName()));


        initializeCountDrawer();

    }
    private void comprobarToolbar(){
        if (!getCruge("action_grup_view")) {
            int textviewGrupID = getResources().getIdentifier("LblGrup", "id", getPackageName());
            TextView txtGrup = (TextView) findViewById(textviewGrupID);
            txtGrup.setVisibility(View.GONE);

            int spinnerGrupID = getResources().getIdentifier("CmbToolbarGrup", "id", getPackageName());
            Spinner spnGrup = (Spinner) findViewById(spinnerGrupID);
            spnGrup.setVisibility(View.GONE);

        }

    }
    private void packagesharedPreferences() {
        SharedPreferences.Editor editor = shared.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(arrPackage);
        editor.putStringSet("opurl", set);
        editor.apply();
        Log.d("storesharedPref",""+set);
    }
    private void retriveSharedValue() {
        Set<String> set = shared.getStringSet("opurl", null);
        arrPackage.addAll(set);
        Log.d("retrivesharedPref",""+set);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        tooltips.onDestroy();
        tooltips = null;
        // CONTROL PEDIDO MESA
        if(Filtro.getOppedidomesa()) {
            stopService(intent);
        }
    }

    class UpdateUI implements Runnable
    {
        String updateString;

        public UpdateUI(String updateString) {
            this.updateString = updateString;
        }
        public void run() {

 //           txtview.setText("Timer "+updateString);
            txtMesaOpen.setText(updateString);
            if (Integer.parseInt(updateString) == 0) {
                imagemesa.setAlpha(0.3f); // COLOR APAGADO MESA CERRADA
            } else {
                imagemesa.setAlpha(1.0f); // COLOR BRILLANTE MESA ABIERTA
            }


        }
    }

    class MesasResultReceiver extends ResultReceiver
    {
        public MesasResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if(resultCode == 100){
////                runOnUiThread(new UpdateUI(resultData.getString("start")));
                runOnUiThread(new UpdateUI(Integer.toString(resultCode)));
            }
            else if(resultCode == 200){
////                runOnUiThread(new UpdateUI(resultData.getString("end")));
                runOnUiThread(new UpdateUI(Integer.toString(resultCode)));
            }
            else{
////                runOnUiThread(new UpdateUI("Result Received "+resultCode));
                runOnUiThread(new UpdateUI(Integer.toString(resultCode)));
            }
        }
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count>0) {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.contenedor_principal);
            Fragment currentFragmentCobro = fragmentManager.findFragmentById(R.id.lista_coordinator);
            if (currentFragmentCobro!=null){
//                Toast.makeText(this, "FragmentCobro Activo " + currentFragmentCobro.getTag().toString(), Toast.LENGTH_SHORT).show();
            }else {
                if (Filtro.getCobroDesdeFactura()==1){
                    Filtro.setCobroDesdeFactura(0);

                    setCabecera(getPalabras("Inicio"),0.00,0);

                    setTitle(getPalabras("Inicio"));
                    Spannable text = new SpannableString(getTitle());
                    text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.light_blue_500)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    setTitle(text);
                    CargaFragment cargafragment = new CargaFragment(FragmentoInicio.newInstance(),getSupportFragmentManager());
                    cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                    if (cargafragment.getFragment() != null){
                        cargafragment.setTransaction(R.id.contenedor_principal);
                    }
                }

            }
            ///           Toast.makeText(this, "Fragment Activo " + currentFragment.getTag().toString()+" "+Integer.toString(count), Toast.LENGTH_SHORT).show();
            Log.i("Fragment Activo: ",currentFragment.getTag().toString());
            //   System.out.println("====================================================changeeeeeeeeeeeeeeeeeeeeeeeeee");
        }else{
 ///           Toast.makeText(this, "NO HAY FRAGMENT EN LA COLA "+Filtro.getTag_fragment(), Toast.LENGTH_SHORT).show();

            int itemViewID = getResources().getIdentifier("mi_search", "id", getPackageName());
            SearchView item = (SearchView) findViewById(itemViewID);
            if (item!=null){
                Log.i("Searchview Activo: ","NO TOCA ESTAR ACTIVO");
                item.setVisibility(View.GONE);
            }
        }

    }
    private void initializeCountDrawer(){
        //Gravity property aligns the text
        itempedido.setGravity(Gravity.CENTER_VERTICAL);
        itempedido.setTypeface(null, Typeface.BOLD);
        itempedido.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itempedido.setText("0");
        itempedido.setEnabled(false);

        itemseccion.setGravity(Gravity.CENTER_VERTICAL);
        itemseccion.setTypeface(null, Typeface.BOLD);
        itemseccion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemseccion.setText("0");
        itemseccion.setEnabled(false);

        itemcaja.setGravity(Gravity.CENTER_VERTICAL);
        itemcaja.setTypeface(null, Typeface.BOLD);
        itemcaja.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemcaja.setText("0");
        itemcaja.setEnabled(false);

        itemturno.setGravity(Gravity.CENTER_VERTICAL);
        itemturno.setTypeface(null, Typeface.BOLD);
        itemturno.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemturno.setText("0");
        itemturno.setEnabled(false);

        itemdcj.setGravity(Gravity.CENTER_VERTICAL);
        itemdcj.setTypeface(null, Typeface.BOLD);
        itemdcj.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemdcj.setText("0");
        itemdcj.setEnabled(false);

        itemfactura.setGravity(Gravity.CENTER_VERTICAL);
        itemfactura.setTypeface(null, Typeface.BOLD);
        itemfactura.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemfactura.setText("0");
        itemfactura.setEnabled(false);

        itemmesas.setGravity(Gravity.CENTER_VERTICAL);
        itemmesas.setTypeface(null, Typeface.BOLD);
        itemmesas.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemmesas.setText("0");
        itemmesas.setEnabled(false);

        itemmessage.setGravity(Gravity.CENTER_VERTICAL);
        itemmessage.setTypeface(null, Typeface.BOLD);
        itemmessage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accentColor));
        itemmessage.setText("0");
        itemmessage.setEnabled(false);

    }
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Bundle args = new Bundle();

        // Poner ultima fecha registrada en la seccion.
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        SimpleDateFormat formatoDelTextoHoy = new SimpleDateFormat("dd-MM-yyyy");

        String strFecha = txtFecha_apertura.getText().toString();
        Date fechainicio = null;
        Date fechafinal = null;
        Date fechahoy = null;
        Log.i("Rol:",Filtro.getRoles()+" FA "+Filtro.getFechaapertura()+" FI "+Filtro.getFechaInicio()+" FF "+Filtro.getFechaFinal());
        try {
            if (Filtro.getRoles().equals("emp")) {
                fechainicio = formatoDelTexto.parse(Filtro.getFechaapertura());
                fechafinal = formatoDelTexto.parse(Filtro.getFechaapertura());
            }else{
                fechainicio = formatoDelTexto.parse(Filtro.getFechaInicio());
                fechafinal = formatoDelTexto.parse(Filtro.getFechaFinal());
            }
            fechahoy = formatoDelTextoHoy.parse(strFecha);
        } catch (ParseException ex) {
            Log.i("Rol:","ERROR");
            ex.printStackTrace();

        }
        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
//        System.out.println("Son las: "+formatoHora.format(fechaActual)+" de fecha: "+formatoFecha.format(fechaActual));

        //Fecha inicio desglosada:
        Calendar fecha_inicio = Calendar.getInstance();
        fecha_inicio.setTime(fechainicio);

        /// PONER FECHA MINIMO DATE EN FUNCION DEL USUARIO
        long minDate = fecha_inicio.getTime().getTime(); // Twice!
        args.putLong("minDate",minDate);
        //////////////////////////////////////////////////////7

        Calendar fecha_final = Calendar.getInstance();
        fecha_final.setTime(fechafinal);
////        fecha_cal_max.add(Calendar.DATE,30); //Añadir 1 dia al ultimo dia cerrado

        long maxDate = fecha_final.getTime().getTime(); // Twice!

        args.putLong("maxDate",maxDate);


        Calendar fecha_hoy = Calendar.getInstance();
        fecha_hoy.setTime(fechahoy);
////        fecha_cal_max.add(Calendar.DATE,30); //Añadir 1 dia al ultimo dia cerrado
        int año = fecha_hoy.get(Calendar.YEAR);
        int mes = fecha_hoy.get(Calendar.MONTH);
        int dia = fecha_hoy.get(Calendar.DAY_OF_MONTH);
        int maxhora = fecha_hoy.get(Calendar.HOUR_OF_DAY);
        int maxminuto = fecha_hoy.get(Calendar.MINUTE);
        int maxsegundo = fecha_hoy.get(Calendar.SECOND);


        args.putInt("year", año);
        args.putInt("month", mes);
        args.putInt("day", dia);


        date.setArguments(args);

        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");

        Log.i("FECHA APERTURA",txtFecha_apertura.getText().toString()+"_"+Filtro.getFechaInicio()+" _ "+Filtro.getFechaFinal()+" _ "+fecha_hoy.getTime());

    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            String numMes = mMonth < 10 ? "0" + mMonth : "" + mMonth;
            String numDia = mDay < 10 ? "0" + mDay : "" + mDay;
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            try{
                txtFecha_apertura.setText(new StringBuilder().append(numDia).append("-").append(numMes).append("-").append(mYear));
                String StringRecogido = String.valueOf(txtFecha_apertura);
                Date fecha = sdf1.parse(StringRecogido);
                dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
                nowYYYYMMDD = new StringBuilder(dateformatYYYYMMDD.format(fecha));
                dateformatDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
                nowDDMMYYYY = new StringBuilder(dateformatDDMMYYYY.format(fecha));
                txtFecha_apertura.setText(nowDDMMYYYY);
            }
            catch(Exception e){
                e.getMessage();
            }
            Filtro.setFecha(String.valueOf(year) + "-" + numMes
                    + "-" + numDia);
            Log.i("Fecha: ",Filtro.getFecha());

            /// CONTROLAR QUE FRAGMENT ESTA ACTIVO
            if (FragmentoCloseDocumentoPedido.getInstance() != null) {
 ///               FragmentoCloseDocumentoPedido.getInstance().onResume();
            }

            Toast.makeText(
                    getApplicationContext(),
                    String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth)+" / "+Filtro.getFecha(),
                    Toast.LENGTH_LONG).show();

        }
    };
    public static void setOverflowButtonColor(final Toolbar toolbar1, final int color) {
        Drawable drawable = toolbar1.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar1.setOverflowIcon(drawable);
        }
    }
    private void agregarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
///        toolbar.setBackgroundColor(Filtro.getColorItem()); PONER COLOR A TODA LA BARRA
///        setOverflowButtonColor(toolbar, Filtro.getColorItem()); // PONER COLOR AL BOTTON DEL FINAL DE LA BARRA
        TextView conn = (TextView) toolbar.findViewById(R.id.txtconnection);
        conn.setText(Filtro.getConexion());
        conn.setTextSize(16);
    //    txtview = (TextView) toolbar.findViewById(R.id.txtview);

        // CONTROL PEDIDO MESA
        if(Filtro.getOppedidomesa()) {
            txtMesaOpen = (TextView) toolbar.findViewById(R.id.txtMesasOpen);
            imagemesa = (ImageView) toolbar.findViewById(R.id.imageMesa);

            txtMesaOpen.setVisibility(View.VISIBLE);
            imagemesa.setVisibility(View.VISIBLE);
            imagemesa.findViewById(R.id.imageMesa).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!getCruge("action_message_admin")){
                        Snackbar.make(v, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoMessage.newInstance(),getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                        if (cargafragment.getFragment() != null){
                            cargafragment.setTransaction(R.id.contenedor_principal);
                            Snackbar.make(v, getPalabras("Ir")+" "+getPalabras("Mensaje"), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
  //      getSupportActionBar().setIcon(R.drawable.logo_ricoparico); //also displays wide logo_ricoparico

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
//            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
//            ab.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.drawer_toggle, null);
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, Filtro.getColorItem());
            ab.setHomeAsUpIndicator(drawable);
            ab.setDisplayHomeAsUpEnabled(true);

        }

    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public static Bitmap resizeBitmapImageFn(
            Bitmap bmpSource, int maxResolution){
        int iWidth = bmpSource.getWidth();
        int iHeight = bmpSource.getHeight();
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }

    public static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }


    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        boolean startactivity = false;
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        switch (itemDrawer.getItemId()) {
            case R.id.item_seccion:
                if(getCruge("action_sec_admin")){
                    fragmentoGenerico = new FragmentoSeccion();
                }
                break;
            case R.id.item_caja:
                if(getCruge("action_caja_admin")){
                    fragmentoGenerico = new FragmentoCaja();
                }
                break;
            case R.id.item_turno:
                if(getCruge("action_turno_admin")){
                    fragmentoGenerico = new FragmentoTurno();
                }
                break;
            case R.id.item_dcj:
                if(getCruge("action_dcj_admin")){
                    fragmentoGenerico = new FragmentoDcj();
                }
                break;
            case R.id.item_mesas:
                if(getCruge("action_mesas_admin")){

                    startactivity=true;
                    ///          startActivity(new Intent(this, MesasActivity.class));

                    // 1. create an intent pass class name or intnet action name
                    Intent intent = new Intent(this,MesasActivity.class);

                    // 2. put MESA, OPERACION in intent
                    intent.putExtra("Mesa",  "");
                    intent.putExtra("Action","");
                    intent.putExtra("Tabla","");

                    // 3. start the activity
                    startActivityForResult(intent, 1);

                }
                break;
            case R.id.item_message:
//                if(getCruge("action_message_admin")){
                    fragmentoGenerico = new FragmentoMessage();
//                }
                break;
            case R.id.item_planning:
                if(getCruge("action_mesas_admin")){
                    startActivity(new Intent(this, PlanningActivity.class));
            //                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            case R.id.item_pedido:
                if(getCruge("action_pdd_admin")){
                    Filtro.setMesa(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA());
                    fragmentoGenerico = new FragmentoPedido();
                }
                break;
            case R.id.item_factura:
                if(getCruge("action_ftp_admin")){
                    Filtro.setMesa(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA());
                    fragmentoGenerico = new FragmentoFactura();
                }
                break;
            case R.id.item_inicio:
//                if (!Filtro.getInicio()) {
//                    if (getCruge("action_site_index")) {
                        fragmentoGenerico = new FragmentoInicio();
//                    }
//                }
                break;
            case R.id.item_salir:
                // clean up
                finish();
//                super.onBackPressed();       // bye
                break;
            case R.id.item_cuenta:
                if(getCruge("action_search_index")){
                    fragmentoGenerico = new FragmentoCuenta();
                }
                break;
            case R.id.item_categorias:
                if(getCruge("action_are_admin")){
                    fragmentoGenerico = FragmentoCategorias.newInstance("");
                }
                break;
/*            case R.id.item_print:
 ///               fragmentoGenerico = PrintTicketFragment.newInstance(12,"A",47);
            break;
*/            case R.id.item_configuracion:
                if(getCruge("action_configuration")){
                    startactivity=true;
                    startActivity(new Intent(this, ActividadConfiguracion.class));

                }
                break;
            case R.id.item_nfc:
//                if(getCruge("action_nfc")){ //HAY QUE CREAR EN CRUGE
                startactivity=true;
                startActivity(new Intent(this, CreditCardNfcReaderActivity.class));

//                }
                break;
            case R.id.item_web:
                if(getCruge("action_webadmin")){ //HAY QUE CREAR EN CRUGE
                    startactivity=true;
                    startActivity(new Intent(this, WebadminActivity.class));
                }
                break;

        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico,fragmentoGenerico.getClass().getName())
 //                   .addToBackStack(fragmentoGenerico.getClass().getName())
                    .commit();
        }else{
            if(!startactivity) {
                Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
            }
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }
/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem infoItem = menu.findItem(R.id.action_info);
        // Retrieve the action-view from menu
        View v = MenuItemCompat.getActionView(infoItem);
        // Find the button within action-view
        ImageView b = (ImageView) v.findViewById(R.id.info);
        // Handle button click here
        return super.onPrepareOptionsMenu(menu);
    }
*/
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if(resultCode == RESULT_OK) {
//        tvResult.setText(data.getIntExtra("result",-1)+"");
        if (!isFinishing()) {
            switch (data.getStringExtra("Tabla").trim()) {
                case "pdd":
                    if (data.getStringExtra("Action").equals("ADD")) {
///                        Log.i("CreaPDD","ActividadPrincipal");

                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoPagesPedido.newInstance(Filtro.getPedido(), "OPEN",data.getStringExtra("Mesa") ,Integer.toString(Filtro.getPedido())), getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                        if (cargafragment.getFragment() != null) {
                            cargafragment.setTransactioncommitAllowingStateLoss(R.id.contenedor_principal);
///                            Log.i("CreaPDD","Generado FragmentoPagesPedido");

////                            Toast.makeText(this, getPalabras("Crear")+" "+getPalabras("Lineas")+" "+getPalabras("Pedido")+" " + Integer.toString(Filtro.getId()), Toast.LENGTH_SHORT).show();
////                            TaskHelper.execute(new CalculaCabecera(), "pdd", "lpd", "0");
                        }
                        //Calcular Items
                        mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());

                        CountTable="pdd";
                        url_count = Filtro.getUrl()+"/CountPddOpen.php";
                        mSerialExecutorActivity.execute(null);

                    }
                    if (data.getStringExtra("Action").equals("OPEN")) {
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoPedido.newInstance(), getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                        if (cargafragment.getFragment() != null) {
                            cargafragment.setTransactioncommitAllowingStateLoss(R.id.contenedor_principal);
                        }
                    }
                    break;
                case "ftp":
                    if (data.getStringExtra("Action").equals("ADD")) {

                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoPagesFactura.newInstance(Filtro.getId(), "OPEN",data.getStringExtra("Mesa"),Filtro.getSerie() ,Integer.toString(Filtro.getFactura()),data.getStringExtra("Total"),data.getStringExtra("Obs")), getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                        if (cargafragment.getFragment() != null) {
                            cargafragment.setTransactioncommitAllowingStateLoss(R.id.contenedor_principal);
 ////                           Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Lineas")+" "+getPalabras("Factura")+" "+ Integer.toString(Filtro.getFactura()), Toast.LENGTH_SHORT).show();
////                            TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "0");
                        }
                        //Calcular Items
                        mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());

                        CountTable="ftp";
                        url_count = Filtro.getUrl()+"/CountFtpOpen.php";
                        mSerialExecutorActivity.execute(null);
                    }
                    if (data.getStringExtra("Action").equals("OPEN")) {
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoFactura.newInstance(0), getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                        if (cargafragment.getFragment() != null) {
                            cargafragment.setTransactioncommitAllowingStateLoss(R.id.contenedor_principal);
                        }
                    }

                    break;
            }

/*            Toast.makeText(this, "MESA: " + data.getStringExtra("Mesa") + " " +
                                 "ACTION: " + data.getStringExtra("Action") + " " +
                                 "TABLA: " + data.getStringExtra("Tabla"), Toast.LENGTH_SHORT).show();
*/
        }
      /* if (!isFinishing()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            FragmentoPagesPedido dummyFragment = FragmentoPagesPedido.newInstance(2, "13", "2");
            ft.replace(R.id.contenedor_principal, dummyFragment);
            ft.commitAllowingStateLoss();

        }
        */
    }

}

    public static boolean getCruge (String search){
        boolean success = false;
        for(Cruge d : lcruge){
            if(d.getAction() != null && d.getAction().contains(search)){
                success = true;
                break;
            }
        }
        return success;
    }
    public void setCabecera(String titulo, double saldo, int numero){
              /// Poner Titulo CABECERA
        this.setTitle(titulo);

        Spannable text = new SpannableString(this.getTitle());
        if (this.getTitle().toString().contains("OPEN")){
            text.setSpan(new ForegroundColorSpan(Filtro.getColorItemZero()), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            this.setTitle(text);
        }else if (this.getTitle().toString().contains("CLOSE")){
            text.setSpan(new ForegroundColorSpan(Filtro.getColorItem()), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            this.setTitle(text);
        }else{
            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.light_blue_500)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            this.setTitle(text);
        }

        View rootView = ActividadPrincipal.this.getWindow().getDecorView().findViewById(android.R.id.content);
        int textViewID = getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);

        TextView textSaldo = (TextView) rootView.findViewById(textViewID);
        if (textSaldo !=null) {
            textSaldo.setText(String.format("%1$,.2f", saldo) + " " + Filtro.getSimbolo());
        }
        // Datos en appbar
        //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
        int txtViewID = getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
        TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
        txtSaldo.setText(String.format("%1$,.2f", saldo)+" "+Filtro.getSimbolo());
        txtSaldo.setTextSize(30);

/*
        /// Poner a cero saldos
        int textViewID = this.getResources().getIdentifier("texto_total_carrito", "id",this.getPackageName());
        Log.i("Cabecera ",titulo +" "+String.valueOf(saldo) +" "+String.valueOf(numero) +" "+String.valueOf(textViewID));

        TextView textSaldo = (TextView) this.findViewById(textViewID);
        textSaldo.setText(String.format("%1$,.2f", saldo) + " " + Filtro.getSimbolo());

        int txtViewID = this.getResources().getIdentifier("total_carrito", "id", this.getPackageName());
        TextView txtSaldo = (TextView) this.findViewById(txtViewID);
        txtSaldo.setText(String.format("%1$,.2f", saldo)+" "+Filtro.getSimbolo());
        txtSaldo.setTextSize(16);
*/
        if (!Filtro.getTag_fragment().contains("Pages")) {
            int itemViewID = getResources().getIdentifier("mi_search", "id", getPackageName());
            SearchView item = (SearchView) findViewById(itemViewID);
            if (item != null) {
                Log.i("Searchview Activo: ", "NO TOCA ESTAR ACTIVO");
                item.setVisibility(View.GONE);
            }
        }

        Utils.setBadgeCount(this, this.iconCarrito, numero);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);

        itemCarrito = menu.findItem(R.id.action_carrito);
        // Obtener drawable del item
        iconCarrito = (LayerDrawable) itemCarrito.getIcon();

        // Actualizar el contador
        Utils.setBadgeCount(this, iconCarrito, 0);
        buffetItem = menu.findItem(R.id.action_buffet);
        if(buffetItem != null)
        {
            View viewbuffet = MenuItemCompat.getActionView(buffetItem);
            // Find the button within action-view
            Button btnBuffet = (Button) viewbuffet.findViewById(R.id.btnBuffet);
            btnBuffet.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToolTip toolTip = new ToolTip()
                            .withText(getPalabras("Ir")+" "+getPalabras("Buffet"))
                            .withColor(Color.GREEN) //or whatever you want
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                            .withShadow();
                    tooltips.showToolTip(toolTip, v);
                    return true;
                }
            });

            btnBuffet.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (!getCruge("action_buffet_admin")){ // HAY QUE CREAR ESTA ACCION
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {
                        ArticuloIdGrupo = 0;
                        nArticuloPositionSelected = 0;
///                        ArticuloImagenGrupo = ""; IMAGEVIEW
                        ArticuloCodigoGrupo = "";
                        ArticuloNombreGrupo ="";
                        int x=0;
                        for(x=0;x<lcategoria.size();x++) {
                            if (lcategoria.get(x).getCategoriaTipo_are().equals(lparam.get(0).getDEFAULT_TIPO_ARTICULO_BUFFET().trim())) {
                                ArticuloCodigoGrupo = lcategoria.get(x).getCategoriaTipo_are();
                                ArticuloNombreGrupo = lcategoria.get(x).getCategoriaNombre_tipoare();
                                Log.i("BUFFET: ", Integer.toString(x) + " " + lcategoria.get(x).getCategoriaTipo_are());
                                break;
/*                                // Limpiamos tabla lcomida
                                for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                                    Comida comida = it.next();
                                }

                                for (ArrayList<Comida> list : ActividadPrincipal.comidas) { // iterate -list by list
                                    for (Comida comida : list) { //iterate element by element in a list
                                        if (comida.getTipo_are().equals(lcategoria.get(x).getCategoriaTipo_are()))  {
                                            lcomida.add(comida);
                                            Log.i("SERIAL COMIDA: ", comida.getNombre());
                                            Log.i("ImagenUrl",comida.getTipo_are()+" "+comida.getNombre()+" "+comida.getUrlimagen());
                                        }
                                    }
                                }
*/                            }
                        }
                        if (!ArticuloCodigoGrupo.equals("")) {
                            Log.i("ARE BUFFET", "OK");

                            indiceCategoria = x; // Ya no lo empleamos
                            if (null!=larticulobuffet) {
                                dialog_buffet();
                            }else{
                                Toast.makeText(getApplicationContext(), getPalabras("No")+" "+getPalabras("Abierto")+" BUFFET", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(TAG, "Failed to fetch data!");
                            Toast.makeText(getApplicationContext(), getPalabras("No existe")+" "+getPalabras("Tipo")+" "+getPalabras("Articulo")+" BUFFET", Toast.LENGTH_SHORT).show();
                        }

/*
                        larticulo = new ArrayList<Articulo>();
                        URL_ARTICULOS = Filtro.getUrl() + "/RellenaListaARE.php";
                        new GetAreBuffet().execute(URL_ARTICULOS, "BUFFET");
*/                    }
                }
            });
        }

        final MenuItem mesasItem = menu.findItem(R.id.action_mesas);
        if(mesasItem != null)
        {
            View viewmesas = MenuItemCompat.getActionView(mesasItem);
            // Find the button within action-view
            Button btnMesas = (Button) viewmesas.findViewById(R.id.btnMesas);
            btnMesas.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToolTip toolTip = new ToolTip()
                            .withText(getPalabras("Ir")+" "+getPalabras("Mesas"))
                            .withColor(Color.GREEN) //or whatever you want
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                            .withShadow();
                    tooltips.showToolTip(toolTip, v);
                    return true;
                }
            });

            btnMesas.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (!getCruge("action_mesas_admin")){
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {
                        // 1. create an intent pass class name or intnet action name
                        Intent intent = new Intent(ActividadPrincipal.this,MesasActivity.class);

                        // 2. put MESA, OPERACION in intent
                        intent.putExtra("Mesa",  "");
                        intent.putExtra("Action","");
                        intent.putExtra("Tabla","");

                        // 3. start the activity
                        startActivityForResult(intent, 1);
                    }
                }
            });
        }
        final MenuItem facturasItem = menu.findItem(R.id.action_facturas);
        if(facturasItem != null)
        {
            View viewfacturas = MenuItemCompat.getActionView(facturasItem);
            // Find the button within action-view
            Button btnFacturas = (Button) viewfacturas.findViewById(R.id.btnFacturas);
//in onCreate
            btnFacturas.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToolTip toolTip = new ToolTip()
                            .withText(getPalabras("Ir")+" "+getPalabras("Facturas"))
                            .withColor(Color.GREEN) //or whatever you want
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                            .withShadow();
                    tooltips.showToolTip(toolTip, v);
                    return true;
                }
            });

            btnFacturas.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (!getCruge("action_ftp_admin")){
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {
                        Filtro.setMesa(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA());
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoFactura.newInstance(0),getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                        if (cargafragment.getFragment() != null){
                            cargafragment.setTransaction(R.id.contenedor_principal);
                            Snackbar.make(view, getPalabras("Ir")+" "+getPalabras("Facturas"), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        final MenuItem pedidosItem = menu.findItem(R.id.action_pedidos);
        if(pedidosItem != null)
        {
            View viewpedidos = MenuItemCompat.getActionView(pedidosItem);
            // Find the button within action-view
            Button btnPedidos = (Button) viewpedidos.findViewById(R.id.btnPedidos);

            btnPedidos.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToolTip toolTip = new ToolTip()
                            .withText(getPalabras("Ir")+" "+getPalabras("Pedidos"))
                            .withColor(Color.GREEN) //or whatever you want
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                            .withShadow();
                    tooltips.showToolTip(toolTip, v);
                    return true;
                }
            });

            btnPedidos.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (!getCruge("action_pdd_admin")){
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {
                        Filtro.setMesa(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA());
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoPedido.newInstance(),getSupportFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                        if (cargafragment.getFragment() != null){
                            cargafragment.setTransaction(R.id.contenedor_principal);
                            Snackbar.make(view, getPalabras("Ir")+" "+getPalabras("Pedidos"), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }
        final MenuItem salirItem = menu.findItem(R.id.action_salir);
        if(salirItem != null)
        {
            View viewsalir = MenuItemCompat.getActionView(salirItem);
            // Find the button within action-view
            Button btnSalir = (Button) viewsalir.findViewById(R.id.btnSalir);
            btnSalir.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToolTip toolTip = new ToolTip()
                            .withText(getPalabras("Salir")+" "+getPalabras("Aplicacion"))
                            .withColor(Color.GREEN) //or whatever you want
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                            .withShadow();
                    tooltips.showToolTip(toolTip, v);
                    return true;
                }
            });

            btnSalir.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                finish();
                }
            });
        }

        final MenuItem infoItem = menu.findItem(R.id.action_info);
        if(infoItem != null)
        {
            View view = MenuItemCompat.getActionView(infoItem);
            // Find the button within action-view
            Button b = (Button) view.findViewById(R.id.btnInfo);
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToolTip toolTip = new ToolTip()
                            .withText(getPalabras("Activar")+" "+getPalabras("Filtros"))
                            .withColor(Color.GREEN) //or whatever you want
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                            .withShadow();
                    tooltips.showToolTip(toolTip, v);
                    return true;
                }
            });

            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
/*                    AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
                    float heightDp = getResources().getDisplayMetrics().heightPixels / 3;
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)appbar.getLayoutParams();
                    lp.height = (int)heightDp;
*/
                    /// leemos altura Toolbar1
                    Toolbar mToolbar1 = (Toolbar) findViewById(R.id.appbar1);
                    LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) mToolbar1.getLayoutParams();
                    float heightDp = getResources().getDisplayMetrics().heightPixels;
                    Log.i("HEIGHTDP: ",Float.toString(heightDp)+" - "+Integer.toString(Filtro.getHeight_toolbar1()));
                    if (Filtro.getHide_toolbar1()) {
                        layoutParams1.height = Filtro.getHeight_toolbar1();
                        Filtro.setHide_toolbar1(false);
                    }else {
                        Filtro.setHeight_toolbar1(layoutParams1.height);
                        layoutParams1.height = 0;
                        Filtro.setHide_toolbar1(true);
                    }
                    mToolbar1.setLayoutParams(layoutParams1);
                    /// leemos altura Toolbar2
                    Toolbar mToolbar2 = (Toolbar) findViewById(R.id.appbar2);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) mToolbar2.getLayoutParams();
                    Log.i("HEIGHTDP: ",Float.toString(heightDp)+" - "+Integer.toString(Filtro.getHeight_toolbar2()));
                    if (Filtro.getHide_toolbar2()) {
                        layoutParams2.height = Filtro.getHeight_toolbar2();
                        Filtro.setHide_toolbar2(false);
                    }else {
                        Filtro.setHeight_toolbar2(layoutParams2.height);
                        layoutParams2.height = 0;
                        Filtro.setHide_toolbar2(true);
                    }
                    mToolbar2.setLayoutParams(layoutParams2);

                    /*                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar1.getLayoutParams();
                    layoutParams.height = 42;
                    toolbar1.setLayoutParams(layoutParams);
*/                }
            });

            // Handle button click here
            /// REALIZAMOS CLICK BOTON INFO SI HEMOS SELECCIONADO OCULTAR FILTROS.
            if (Filtro.getOptoolbar()==1){
                b.performClick();
            }

        }

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                         return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        Log.i("TAGFRAGMENT",Filtro.getTag_fragment());
                        return true; // Return true to expand action view
                    }
                });

///        return super.onCreateOptionsMenu(menu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item_configuracion:
                if(getCruge("action_configuration")){
                    startActivity(new Intent(this, ActividadConfiguracion.class));
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Adding spinner data grupo
     */
    private void populateSpinnerGrup() {
        List<String> lables_grup = new ArrayList<String>();

        for (int i = 0; i < grupList.size(); i++) {
            lables_grup.add(grupList.get(i).getGrupoNombre());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_grup = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_grup);

        adapter_grup.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarGrup.setAdapter(adapter_grup);
        if (grupList.size()>0) {
            Filtro.setGrupo(grupList.get(0).getGrupoGrupo());
        }
    }

    /**
     * Adding spinner data empresa
     */
    private void populateSpinnerEmpr() {
        List<String> lables_empr = new ArrayList<String>();
        List<String> lables_simbolo = new ArrayList<String>();

        for (int i = 0; i < emprList.size(); i++) {
            lables_empr.add(emprList.get(i).getEmpresaRazon());
            lables_simbolo.add(simboloList.get(i).getMonedaSimbolo());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_empr = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_empr);

        adapter_empr.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarEmpr.setAdapter(adapter_empr);
        if (emprList.size()>0) {
            Filtro.setEmpresa(emprList.get(0).getEmpresaEmpresa());
            Filtro.setSimbolo(simboloList.get(0).getMonedaSimbolo());
        }
    }

    /**
     * Adding spinner data local
     */
    private void populateSpinnerLocal() {
        List<String> lables_local = new ArrayList<String>();

        for (int i = 0; i < localList.size(); i++) {
            lables_local.add(localList.get(i).getLocalNombre_local());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_local = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_local);

        adapter_local.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarLocal.setAdapter(adapter_local);
        if (localList.size()>0) {
            Filtro.setLocal(localList.get(0).getLocalLocal());
        }
    }
    /**
     * Adding spinner data sec
     */
    private void populateSpinnerSec() {
        List<String> lables_sec = new ArrayList<String>();

        for (int i = 0; i < secList.size(); i++) {
            lables_sec.add(secList.get(i).getSeccionNombre_Sec());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_sec = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_sec);

        adapter_sec.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarSec.setAdapter(adapter_sec);
        if (secList.size()>0) {
            Filtro.setSeccion(secList.get(0).getSeccionSeccion());
            Filtro.setIvaIncluido(secList.get(0).getSeccionIvaIncluido());
            //Calcular Items
            mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());
            if (Filtro.getInicio()) {

                CountTable = "sec";
                url_count = Filtro.getUrl() + "/CountSecOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "caja";
                url_count = Filtro.getUrl() + "/CountCajaOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "turno";
                url_count = Filtro.getUrl() + "/CountTurnoOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "dcj";
                url_count = Filtro.getUrl() + "/CountDcjOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "pdd";
                url_count = Filtro.getUrl() + "/CountPddOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "ftp";
                url_count = Filtro.getUrl() + "/CountFtpOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "mesas";
                url_count = Filtro.getUrl() + "/CountMesasOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "message";
                url_count = Filtro.getUrl() + "/CountMessageOpen.php";
                mSerialExecutorActivity.execute(null);

            }
        }
    }

    /**
     * Adding spinner data caja
     */
    private void populateSpinnerCaja() {
        List<String> lables_caja = new ArrayList<String>();

        for (int i = 0; i < cajaList.size(); i++) {
            lables_caja.add(cajaList.get(i).getCajaNombre_Caja());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_caja = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_caja);

        adapter_caja.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarCaja.setAdapter(adapter_caja);
        if (cajaList.size()>0) {
            Filtro.setCaja(cajaList.get(0).getCajaCaja());
            if (Filtro.getInicio()) {

                CountTable = "caja";
                url_count = Filtro.getUrl() + "/CountCajaOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "turno";
                url_count = Filtro.getUrl() + "/CountTurnoOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "dcj";
                url_count = Filtro.getUrl() + "/CountDcjOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "pdd";
                url_count = Filtro.getUrl() + "/CountPddOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "ftp";
                url_count = Filtro.getUrl() + "/CountFtpOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "mesas";
                url_count = Filtro.getUrl() + "/CountMesasOpen.php";
                mSerialExecutorActivity.execute(null);
            }

        }
    }
    /**
     * Adding spinner data turno
     */
    private void populateSpinnerTurno() {
        List<String> lables_turno = new ArrayList<String>();

        for (int i = 0; i < turnoList.size(); i++) {
            lables_turno.add(turnoList.get(i).getTurnoNombre_Turno());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_turno = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_turno);

        adapter_turno.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarTurno.setAdapter(adapter_turno);
        if (turnoList.size()>0) {
            Filtro.setTurno(turnoList.get(0).getTurnoCod_turno());
            if (Filtro.getInicio()) {

                CountTable = "turno";
                url_count = Filtro.getUrl() + "/CountTurnoOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "dcj";
                url_count = Filtro.getUrl() + "/CountDcjOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "pdd";
                url_count = Filtro.getUrl() + "/CountPddOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "ftp";
                url_count = Filtro.getUrl() + "/CountFtpOpen.php";
                mSerialExecutorActivity.execute(null);

                CountTable = "mesas";
                url_count = Filtro.getUrl() + "/CountMesasOpen.php";
                mSerialExecutorActivity.execute(null);
            }

        }
    }
    /**
     * Adding spinner data turno
     */
    private void populateSpinnerFra() {
        List<String> lables_fra = new ArrayList<String>();

        for (int i = 0; i < fraList.size(); i++) {
            lables_fra.add(fraList.get(i).getFraSerie());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_fra = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_fra);

        adapter_fra.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarFra.setAdapter(adapter_fra);
        if (fraList.size()>0) {
            Filtro.setSerie(fraList.get(0).getFraSerie());
        }
    }

    /**
     * Adding spinner data rangos
     */
    private void populateSpinnerRangos() {
        List<String> lables_rangos = new ArrayList<String>();

        for (int i = 0; i < rangosList.size(); i++) {
            lables_rangos.add(rangosList.get(i).getRangoNombre_Rangos());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_rangos = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_rangos);

        adapter_rangos.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarRangos.setAdapter(adapter_rangos);
        if (rangosList.size()>0) {
            Filtro.setRango(rangosList.get(0).getRangoRango());
        }
    }
    
    /**
     * Adding spinner data mesas
     */
    private void populateSpinnerMesas() {
        List<String> lables_mesas = new ArrayList<String>();

        for (int i = 0; i < mesasList.size(); i++) {
            lables_mesas.add(mesasList.get(i).getMesaNombre_Mesas());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_mesas = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_mesas);

        adapter_mesas.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarMesas.setAdapter(adapter_mesas);
        if (mesasList.size()>0) {
            Filtro.setMesa(mesasList.get(0).getMesaMesa());
        }
    }

    /**
     * Adding spinner data empleados
     */
    private void populateSpinnerEmpleados() {
        List<String> lables_empleados = new ArrayList<String>();

        InUsuarios = "";
        for (int i = 0; i < empleadosList.size(); i++) {
//***            if (empleadosList.get(i).getEmpleadoUsername().equals(Filtro.getUsuario())) {
                lables_empleados.add(empleadosList.get(i).getEmpleadoNombre_Empleado());
//***           }
            // Rellenamos string usuarios para condicion
            InUsuarios += ("'"+ empleadosList.get(i).getEmpleadoEmpleado().trim() +"',");
        }
        InUsuarios = InUsuarios.substring(0, InUsuarios.length() - 1);
        Log.i("InUsuarios",InUsuarios);
        ArrayAdapter<String> adapter_empleados = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,lables_empleados);

        adapter_empleados.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarEmpleados.setAdapter(adapter_empleados);
        if (empleadosList.size()>0) {
            Filtro.setEmpleado(empleadosList.get(0).getEmpleadoEmpleado());
        }
        if(!Filtro.getInicio()){
            // Ponemos en verde opcion menu
            final ActionBar ab = getSupportActionBar();
            if (ab != null) {
                // Poner ícono del drawer toggle
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.drawer_toggle, null);
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, Filtro.getColorItemZero());
                ab.setHomeAsUpIndicator(drawable);
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }

    }
    /**
     * Adding spinner data empleados
     */
    private void populateSpinnerMoneda() {
        List<String> lables_moneda = new ArrayList<String>();

        for (int i = 0; i < monedaList.size(); i++) {
            lables_moneda.add(monedaList.get(i).getMonedaNombre());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_moneda = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title, lables_moneda);

        adapter_moneda.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarMoneda.setAdapter(adapter_moneda);
        if (monedaList.size() > 0) {
            Filtro.setMoneda(monedaList.get(0).getMonedaMoneda());
        }
    }
    
    private void populateSpinnerTerminal() {
        List<String> lables_terminal = new ArrayList<String>();

        for (int i = 0; i < terminalList.size(); i++) {
            lables_terminal.add(terminalList.get(i).getTerminalTerminal());
        }
        ArrayAdapter<String> adapter_terminal = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title, lables_terminal);

        adapter_terminal.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarTerminal.setAdapter(adapter_terminal);
        if (terminalList.size() > 0) {
            Filtro.setPrintPrinterName(terminalList.get(0).getTerminalPrinter());
            Filtro.setPrintIp(terminalList.get(0).getTerminalPrintIp());
            Filtro.setPrintLanguage(terminalList.get(0).getTerminalPrintLanguage());
            Filtro.setPrintInterval(terminalList.get(0).getTerminalPrintInterval());
            switch (terminalList.get(0).getTerminalDeviceType()) {
                case "TCP":
                    Filtro.setPrintDeviceType(Print.DEVTYPE_TCP);
                    break;
                case "BLUETOOTH":
                    Filtro.setPrintDeviceType(Print.DEVTYPE_BLUETOOTH);
                    break;
                case "USB":
                    Filtro.setPrintDeviceType(Print.DEVTYPE_USB);
                    break;
            }
            Log.i("PRINT ","DeviceType: "+Filtro.getPrintdeviceType()+
                           " PrinterName: "+Filtro.getPrintPrinterName()+
                           " PrinterIP: "+Filtro.getPrintIp()+
                           " PrintLanguage: "+Integer.toString(Filtro.getPrintLanguage())+
                           " PrintInterval: "+Integer.toString(Filtro.getPrintInterval())
            );
        }

        /// TERMINAMOS DE CARGAR TODAS LOS FILTROS INICIALES Y LO PONEMOS A FALSE
        int text1ViewID = getResources().getIdentifier("etiqueta_carrito", "id", getPackageName());
        lblCarrito = (TextView) findViewById(text1ViewID);
        lblCarrito.setText(ValorCampo(text1ViewID, lblCarrito.getClass().getName()));
        lblCarrito.setTextSize(16);

        int textViewID = getResources().getIdentifier("texto_total_carrito", "id", getPackageName());
        TextView textSaldo = (TextView) findViewById(textViewID);
        textSaldo.setText(String.format("%1$,.2f", 0.00)+" "+Filtro.getSimbolo());
        textSaldo.setTextSize(30);
        // Datos en appbar
        int txtViewID = getResources().getIdentifier("total_carrito", "id", getPackageName());
        TextView txtSaldo = (TextView) findViewById(txtViewID);
        txtSaldo.setText(String.format("%1$,.2f", 0.00)+" "+Filtro.getSimbolo());
        txtSaldo.setTextSize(30);

        Filtro.setInicio(false);

        mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());
        /// CARGAR OBSERVACIONES
        observaciones = new ArrayList<ArrayList<Observacion>>();
        lobservacion = new ArrayList<Observacion>();
        CountTable="obs";
        url_count = Filtro.getUrl()+"/RellenaListaOBS.php";
        mSerialExecutorActivity.execute(null);
        ////////////////////////////////////////////////////////

        /// CARGAR POPULARES
        populares = new ArrayList<ArrayList<Popular>>();
        lpopular = new ArrayList<Popular>();
        CountTable="popular";
        url_count = Filtro.getUrl()+"/RellenaListaPopulares.php";
        mSerialExecutorActivity.execute(null);
        ////////////////////////////////////////////////////////

        CargaFragment cargafragment = new CargaFragment(FragmentoInicio.newInstance(),getSupportFragmentManager());
        cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
        if (cargafragment.getFragment() != null){
            cargafragment.setTransaction(R.id.contenedor_principal);
        }
/*        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        fragmentoGenerico = new FragmentoInicio();
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico,fragmentoGenerico.getClass().getName())
                    //                   .addToBackStack(fragmentoGenerico.getClass().getName())
                    .commit();
        }
*/

//        FragmentoInicio.getInstance().onResume(); // Cargamos PANTALLA INICIO
    }

    public void onSeccionSelected(int id) {


        if (Filtro.getTag_fragment().equals("FragmentoCloseSeccion")) {
            if (!getCruge("action_sec_update")) {
                Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
            }else{
                CargaFragment cargafragment = null;
                cargafragment = new CargaFragment(EditOpenSeccionFragment.newInstance(Integer.toString(id)),getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                if (cargafragment.getFragment() != null){
                    cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                    Toast.makeText(this, getPalabras("Ver")+" "+getPalabras("Seccion")+" " + Integer.toString(id)+ " "+Filtro.getTag_fragment(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        if (Filtro.getTag_fragment().equals("FragmentoOpenSeccion")) {
            if (!getCruge("action_sec_update")) {
                Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
            } else {
                CargaFragment cargafragment = null;
                cargafragment = new CargaFragment(EditCloseSeccionFragment.newInstance(Integer.toString(id)), getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                if (cargafragment.getFragment() != null) {
                    cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                    Toast.makeText(this, getPalabras("Ver") + " " + getPalabras("Seccion") + " " + Integer.toString(id) + " " + Filtro.getTag_fragment(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void onOpenSeccionSelected(int id) {
        if (!getCruge("action_sec_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            CargaFragment cargafragment = null;
            cargafragment = new CargaFragment(EditOpenSeccionFragment.newInstance(Integer.toString(id)), getSupportFragmentManager());
            cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
            if (cargafragment.getFragment() != null) {
                cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                Toast.makeText(this, getPalabras("Ver") + " " + getPalabras("Seccion") + " " + Integer.toString(id) + " " + Filtro.getTag_fragment(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onCloseSeccionSelected(int id) {
        if (!getCruge("action_sec_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            CargaFragment cargafragment = null;
            cargafragment = new CargaFragment(EditCloseSeccionFragment.newInstance(Integer.toString(id)),getSupportFragmentManager());
            cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
            if (cargafragment.getFragment() != null){
                cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                Toast.makeText(this, getPalabras("Ver")+" "+getPalabras("Seccion")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void onCajaSelected(int id) {
        if (!getCruge("action_caja_view")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Ver")+" "+getPalabras("Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
        }
    }
    public void onOpenCajaSelected(int id) {
        if (!getCruge("action_caja_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Abrir")+" "+getPalabras("Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
            new OpenCloseCaja().execute(Integer.toString(id), "1");
        }
    }
    public void onCloseCajaSelected(int id) {
        if (!getCruge("action_caja_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Cerrar")+" "+getPalabras("Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
            new OpenCloseCaja().execute(Integer.toString(id), "0");
        }
    }
    public void onTurnoSelected(int id) {
        if (!getCruge("action_turno_view")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Ver")+" "+getPalabras("Turno")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
        }
    }
    public void onOpenTurnoSelected(int id) {
        if (!getCruge("action_turno_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Abrir")+" "+getPalabras("Turno")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
            new OpenCloseTurno().execute(Integer.toString(id), "1");
        }
    }
    public void onCloseTurnoSelected(int id) {
        if (!getCruge("action_turno_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Cerrar")+" "+getPalabras("Turno")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
            new OpenCloseTurno().execute(Integer.toString(id), "0");
        }
    }
    public void onDcjSelected(int id) {
        if (!getCruge("action_dcj_view")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Ver")+" "+getPalabras("Diario Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
        }
    }

    public void onOpenDcjSelected(int id) {
        if (!getCruge("action_dcj_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Abrir")+" "+getPalabras("Diario Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
        }
    }
    public void onCloseDcjSelected(int id) {
        if (!getCruge("action_dcj_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            new UpdateAperturaDcj().execute(Integer.toString(id));

            Toast.makeText(this, getPalabras("Cerrar")+" "+getPalabras("Diario Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
        }
    }
    public void onPrintDcjSelected(int id, int apertura) {
        if (!getCruge("action_dcj_printg")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            CargaFragment cargafragment = null;
            cargafragment = new CargaFragment(PrintDcjFragment.newInstance(Integer.toString(id),apertura),getSupportFragmentManager());
            cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
            if (cargafragment.getFragment() != null){
                cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                Toast.makeText(this, getPalabras("Imprimir")+" "+getPalabras("Diario Caja")+" " + Integer.toString(id), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onUpdateLineasDocumentoPedidoSelected(int id, String estado, String mesa, String pedido) {

        Filtro.setPedido(Integer.parseInt(pedido));

        if (!getCruge("action_pdd_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else{
            CargaFragment cargafragment = null;
            cargafragment = new CargaFragment(FragmentoPagesPedido.newInstance(id, estado, mesa, pedido),getSupportFragmentManager());
            cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
            if (cargafragment.getFragment() != null){
                cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Lineas")+" " + getPalabras("Pedido")+" "+ pedido, Toast.LENGTH_SHORT).show();
  ////              TaskHelper.execute(new CalculaCabecera(),"pdd","lpd","0");
            }
        }
    }
    public void onUpdateLineasDocumentoFacturaSelected(ImageView image, int id, String mesa, String estado, String serie, String factura, String total, String obs) {
        Filtro.setSerie(serie);
        Filtro.setFactura(Integer.parseInt(factura));

        if (!getCruge("action_ftp_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else{
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.contenedor_principal);
            Log.i("Fragment Activo: ",currentFragment.getTag().toString());
            CargaFragment cargafragment = null;
            cargafragment = new CargaFragment(FragmentoPagesFactura.newInstance(id,estado,mesa,serie,factura,total,obs),getSupportFragmentManager());
            cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
            if (cargafragment.getFragment() != null){
/////**                cargafragment.setTransactionToBackStackTransition(this,R.id.contenedor_principal,currentFragment,image);
                cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Lineas")+" " + getPalabras("Factura")+" "+ factura, Toast.LENGTH_SHORT).show();
////                TaskHelper.execute(new CalculaCabecera(),"ftp","lft","0");
            }
        }
    }
    public void onUpdateDivisionLineasDocumentoFacturaSelected(ImageView image, int id, String mesa, String estado, String serie, String factura, String total, String obs) {
        Filtro.setSerie(serie);
        Filtro.setFactura(Integer.parseInt(factura));

        if (!getCruge("action_ftp_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else{
            if (estado.contains("CLOSE")) {
                Toast.makeText(this, getPalabras("Factura")+" " + estado + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.contenedor_principal);
                Log.i("Fragment Activo: ", currentFragment.getTag().toString());
                CargaFragment cargafragment = null;
                cargafragment = new CargaFragment(FragmentoDivisionPagesFactura.newInstance(id, estado, mesa, serie, factura, total, obs), getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                if (cargafragment.getFragment() != null) {
/////**                cargafragment.setTransactionToBackStackTransition(this,R.id.contenedor_principal,currentFragment,image);
                    cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                    Toast.makeText(this, getPalabras("Modificar") + " " + getPalabras("Lineas") + " " + getPalabras("Factura") + " " + factura, Toast.LENGTH_SHORT).show();
////                TaskHelper.execute(new CalculaCabecera(),"ftp","lft","0");
                }
            }
        }
    }

    public void onPopularSelected(String articulo, String nombre, String precio, String tiva) {
        if (!getCruge("action_popular_view")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Ver")+" "+getPalabras("Popular")+" "+ articulo, Toast.LENGTH_SHORT).show();
        //    Log.i("FRAGMENT: ", Filtro.getTag_fragment());
        }
    }
    public void onArticulosNewSelected(String nombre) {
                Toast.makeText(this, getPalabras("Articulo") + " " + nombre, Toast.LENGTH_SHORT).show();
    }
    public void onArticulosCobroNewChecked(String saldo) {
        String valor = txtBoxTotalFactura.getText().toString();
        valor = valor.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
        valor = valor.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        float totalfactura = Float.parseFloat(valor);
        valor = String.format("%1$,.2f", Float.parseFloat(saldo));
        valor = valor.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
        valor = valor.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        float totalcobro = Float.parseFloat(valor);
        Log.i("Cobros",String.valueOf(totalfactura)+" - "+String.valueOf(totalcobro));

        String space01 = new String(new char[01]).replace('\0', ' ');
        String myText = String.format("%1$,.2f", Float.parseFloat(saldo));
        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newText="";
        for (int ii = 0; ii < (10-myText.length()); ii++) {
            newText+=space01;
        }
        newText +=myText;
        txtBoxTotalCobro.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

        Filtro.setTotalcobro(totalfactura-totalcobro);

        myText = String.format("%1$,.2f", (totalfactura-totalcobro));
        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        newText="";
        for (int ii = 0; ii < (10-myText.length()); ii++) {
            newText+=space01;
        }
        newText +=myText;
        txtBoxDif.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

//        Toast.makeText(this, getPalabras("Articulo") + " " + saldo, Toast.LENGTH_SHORT).show();
    }

    public void onArticulosBuffetNewChecked(String saldo) {
        String space01 = new String(new char[01]).replace('\0', ' ');
        String myText = String.format("%1$,.2f", Float.parseFloat(saldo));
        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newText="";
        for (int ii = 0; ii < (10-myText.length()); ii++) {
            newText+=space01;
        }
        newText +=myText;
        txtBoxTotal.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

//        Toast.makeText(this, getPalabras("Articulo") + " " + saldo, Toast.LENGTH_SHORT).show();
    }
    public void onArticulosNewChecked(String codigo, int check) {
        for (ArticulosNew articulo : articulosListNew) { //iterate element by element in a list
            if (articulo.getCodigo().equals(codigo))  {
                if (check==1){
                    Log.i("Articulo OK: ", articulo.getName()+" "+articulo.getCodigo()+" " + String.valueOf(check));
                    articulo.setChecked(true);
                    break;
                }else{
                    articulo.setChecked(false);
                    Log.i("Articulo NOT OK: ", articulo.getName()+" "+articulo.getCodigo()+" " + String.valueOf(check));
                    break;
                }
            }
        }

    }
    public void onArticulosNewTipoare(String codigo, String name) {
        Log.i("Articulo Are : ", codigo+" "+name);
    }

    public void onArticulosNewTipoplato(String codigo, int pos) {
        for (ArticulosNew articulo : articulosListNew) { //iterate element by element in a list
            if (articulo.getCodigo().equals(codigo))  {
                Log.i("Articulo Plato : ", articulo.getName()+" "+articulo.getCodigo()+" " + String.valueOf(pos));
                articulo.setPosicionplato(pos);
                break;
            }
        }
    }

    public void onComidaSelected(ImageView imagen, String articulo, String nombre, String precio, String tiva, String estado, String individual, String urlimagen) {
        if (estado.contains("CLOSE") || estado.equals("")) {
            if (estado.contains("CLOSE")) {
                Toast.makeText(this, getPalabras("Documento") + " " + estado + " No " + getPalabras("Crear") + " " + getPalabras("Linea") + " " + getPalabras("Comida") + " " + articulo, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Filtro.getTag_fragment().equals("FragmentoLineaDocumentoFactura")) {
                if (!getCruge("action_lft_create")){
                    Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, getPalabras("Crear")+" "+getPalabras("Linea")+" " + getPalabras("Comida")+" "+ articulo, Toast.LENGTH_SHORT).show();
                    new CreaLineaDocumentoFactura().execute(articulo, nombre, precio, tiva, "1");
                }
            } else {
                String action;
                if (estado.contains("PRINT") ){
                    action = "action_lpd_create_admin";
                }else{
                    action = "action_lpd_create";
                }
                if (!getCruge(action)){
                    Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                }else {
                    if (Integer.parseInt(individual)==1){
                        Toast.makeText(this, getPalabras("Crear")+" "+getPalabras("Linea")+" " + getPalabras("Comida")+" "+ articulo, Toast.LENGTH_SHORT).show();
                        new CreaLineaDocumentoPedido().execute(articulo, nombre, precio, tiva,"1","1","","0",Filtro.getTipoPlato(),"0");
                    }else{
                        nArticuloPositionSelected = 0;

                        ArticuloUrlImagen = urlimagen;
                        ArticuloImagenGrupo = imagen;
                        ArticuloCodigoGrupo = articulo;
                        ArticuloNombreGrupo = nombre;
                        ArticuloPrecioGrupo = precio;
                        ArticuloTivaGrupo = tiva;
                        larticulo = new ArrayList<Articulo>();
                        URL_ARTICULOS = Filtro.getUrl() + "/RellenaListaARE.php";
                        new GetAreAre().execute(URL_ARTICULOS, articulo);

                    }
                }
            }
        }
    }
    public void onAddCantLineaDocumentoFacturaSelected(int id) {
        if (!getCruge("action_lft_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            url_addcant_id = Filtro.getUrl()+"/addcant_id.php";
            TaskHelper.execute(new AddCantLineaDocumentoFactura(),Integer.toString(id), "lft");
         }
    }
    public void onMinusCantLineaDocumentoFacturaSelected(int id) {
        if (!getCruge("action_lft_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            url_minuscant_id = Filtro.getUrl()+"/minuscant_id.php";
            TaskHelper.execute(new MinusCantLineaDocumentoFactura(),Integer.toString(id), "lft");
        }
    }
    public void onAddCantDivisionLineaDocumentoFacturaSelected(int id) {
        if (!getCruge("action_lft_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            url_addcant_id = Filtro.getUrl()+"/addcantdivision_id.php";
            TaskHelper.execute(new AddCantLineaDocumentoFactura(),Integer.toString(id), "lft");
        }
    }
    public void onMinusCantDivisionLineaDocumentoFacturaSelected(int id) {
        if (!getCruge("action_lft_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            url_minuscant_id = Filtro.getUrl()+"/minuscantdivision_id.php";
            TaskHelper.execute(new MinusCantLineaDocumentoFactura(),Integer.toString(id), "lft");
        }
    }

 /*   public void onUpdateLineaDocumentoFacturaSelectedOld(int id, String precio) {
        if (!getCruge("action_lft_update")) {
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Linea")+" " + getPalabras("Factura")+" "+ id, Toast.LENGTH_SHORT).show();
            final int ID = id;
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            precio = precio.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
            precio = precio.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
            precio = precio.replace(".", "");
            precio = precio.replace(",", ".");

            double xValor = Double.valueOf(precio);
            input.setText(String.format("%1$,.2f", xValor));

            // Ponerse al final del edittext
            int pos = input.getText().length();
            input.setSelection(pos);

            input.setTextColor(Color.RED);
            input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            KeyListener keyListener = DigitsKeyListener.getInstance("-,1234567890");
            input.setKeyListener(keyListener);

            alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Precio"));
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    if (value.matches("") || !value.matches("-?\\d+(\\,\\d+)?")) {
                        Toast.makeText(getApplicationContext(), getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Precio"), Toast.LENGTH_SHORT).show();
                        //            this.btnGuardar.setEnabled(false);
                    } else {
                        value = value.replace(".", "");
                        value = value.replace(",", ".");

                        input.setText(String.format("%1$,.2f", Double.valueOf(value)));

                        new UpdatePreuLineaDocumentoFactura().execute(Integer.toString(ID), value);

                        Toast.makeText(getApplicationContext(), input.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });
            alert.show();
        }
    }
*/
    public void onUpdateLineaDocumentoFacturaSelected(int id, String valor, String campo) {
        if (!getCruge("action_lft_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Linea")+" " + getPalabras("Factura")+" "+ id, Toast.LENGTH_SHORT).show();
            final int ID = id;
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            String cValor = valor;
            int pos = 0;
            switch (campo) {
                case "Preu":
                    valor = valor.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
                    valor = valor.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
                    valor = valor.replace(".", "");
                    valor = valor.replace(",", ".");

                    double xValor = Double.valueOf(valor);
                    input.setText(String.format("%1$,.2f", xValor));

                    // Ponerse al final del edittext
                    pos = input.getText().length();
                    input.setSelection(pos);

                    input.setTextColor(Color.RED);
                    input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    KeyListener keyListener = DigitsKeyListener.getInstance("-,1234567890");
                    input.setKeyListener(keyListener);

                    alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Precio"));
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (value.matches("") || !value.matches("-?\\d+(\\,\\d+)?")) {
                                Toast.makeText(getApplicationContext(), getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Precio"), Toast.LENGTH_SHORT).show();
                                //            this.btnGuardar.setEnabled(false);
                            } else {
                                value = value.replace(".", "");
                                value = value.replace(",", ".");

                                input.setText(String.format("%1$,.2f", Double.valueOf(value)));

                                new UpdatePreuLineaDocumentoFactura().execute(Integer.toString(ID), value);

                                Toast.makeText(getApplicationContext(), input.getText().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();

                    break;
                case "Nombre":
                    cValor = cValor.replace(Html.fromHtml("&nbsp;&nbsp;"), "");
                    valor = cValor;

                    input.setText(valor);

                    // Ponerse al final del edittext
                    pos = input.getText().length();
                    input.setSelection(pos);

                    input.setTextColor(Color.RED);
                    alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Nombre")+" "+getPalabras("Articulo"));
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString().trim();

                            new UpdateNombreLineaDocumentoFactura().execute(Integer.toString(ID), value);

                            Toast.makeText(getApplicationContext(), input.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();

                    break;
            }

        }
    }

    public void onDeleteLineaDocumentoFacturaSelected(int id) {
        if (!getCruge("action_lft_delete")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Borrar")+" "+getPalabras("Linea")+" " + getPalabras("Factura")+" "+ id, Toast.LENGTH_SHORT).show();
            url_delete_id = Filtro.getUrl()+"/delete_id.php";
            new DeleteLineaDocumentoFactura().execute(Integer.toString(id), "lft");
        }
    }
    public void onAddCantLineaDocumentoPedidoSelected(int id, int individual, String estado) {
        String action;
        if (estado.contains("PRINT")){
            action = "action_lpd_update_admin";
        }else{
            action = "action_lpd_update";
        }
        if (!getCruge(action)){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Insertar")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea")+" " + getPalabras("Pedido")+" "+ id, Toast.LENGTH_SHORT).show();
            if (individual==1) {
                url_addcant_id = Filtro.getUrl()+"/addcant_id.php";
            }else{
                url_addcant_id = Filtro.getUrl()+"/addcantgrupo_id.php";
            }
            new AddCantLineaDocumentoPedido().execute(Integer.toString(id), "lpd");
        }
    }
    public void onMinusCantLineaDocumentoPedidoSelected(int id, int individual, String estado) {
        String action;
        if (estado.contains("PRINT")){
            action = "action_lpd_update_admin";
        }else{
            action = "action_lpd_update";
        }
        if (!getCruge(action)){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Borrar")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea")+" " + getPalabras("Pedido")+" "+ id, Toast.LENGTH_SHORT).show();
            if (individual==1) {
                url_minuscant_id = Filtro.getUrl()+"/minuscant_id.php";
            }else{
                url_minuscant_id = Filtro.getUrl()+"/minuscantgrupo_id.php";
            }
            new MinusCantLineaDocumentoPedido().execute(Integer.toString(id), "lpd");
        }
    }

    public void onUpdateLineaDocumentoPedidoSelected(int id, String observa, String articulo, String estado) {
        String action;
        if (estado.contains("PRINT")){
            action = "action_lpd_update_admin";
        }else{
            action = "action_lpd_update";
        }
        if (!getCruge(action)){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Linea")+" " + getPalabras("Pedido")+" "+ id, Toast.LENGTH_SHORT).show();
            final int ID = id;
            final EditText input = new EditText(this);
            String cValor = observa;
            cValor = cValor.replace(Html.fromHtml("&nbsp;&nbsp;"), "");
            observa = cValor;

            final LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            for (ArrayList<Observacion> list : observaciones) { // iterate -list by list
                for (Observacion observacion : list) { //iterate element by element in a list
//                    Log.i("Obs Articulo",observacion.getArticulo()+" - "+articulo + " - "+observacion.getArticulo().length()+" - "+articulo.length());
                    if (observacion.getArticulo().equals(articulo.trim()))  {
                        final CheckBox opcion = new CheckBox(this);
                        opcion.setText(observacion.getNombre_obs());
                        layout.addView(opcion);
                        opcion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (input.getText().toString().trim().length()==0) {
                                    input.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                                }
                                if (input.getText().toString().trim().length()==0) {
                                    if(!opcion.getText().toString().equals(getPalabras("OTROS"))) {
                                        input.setText(opcion.getText().toString());
                                    }
                                }else{
                                    if(!opcion.getText().toString().equals(getPalabras("OTROS"))) {
                                        if(opcion.isChecked()) {
                                            input.setText(input.getText().toString().trim() + ", " + opcion.getText().toString());
                                        }
                                    }else{
                                        input.setText("");
                                    }
                                }
                            }
                        });
                    }
                }
            }

//            final EditText input = new EditText(this);
            input.setVisibility(View.VISIBLE);
            layout.addView(input);

            input.setText(observa.trim());

            // Ponerse al final del edittext
            int pos = input.getText().length();
            input.setSelection(pos);

            input.setTextColor(Color.RED);
            alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Observacion"));
///**            alert.setView(input);
            alert.setView(layout);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString().trim();

                    new UpdateObsLineaDocumentoPedido().execute(Integer.toString(ID), value);

                    Toast.makeText(getApplicationContext(), input.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });
  ///**          alert.show();
            final AlertDialog dialogo = alert.create();
            dialogo.show();

        }
    }
    public void onArticulosLineaDocumentoPedidoSelected(ImageView imagelinea, String articulo, String nombre) {
        if (!getCruge("action_lpd_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Consultar")+" "+getPalabras("Articulos")+" " +  articulo, Toast.LENGTH_SHORT).show();
            ArticuloImagenGrupo = imagelinea;
            ArticuloCodigoGrupo = articulo;
            ArticuloNombreGrupo = nombre;
            ArticuloPrecioGrupo = "";
            ArticuloTivaGrupo = "";
            larticulo = new ArrayList<Articulo>();
            URL_ARTICULOS = Filtro.getUrl() + "/RellenaListaARE.php";
            new GetAreAre().execute(URL_ARTICULOS, articulo);
        }
    }

    public void onDeleteLineaDocumentoPedidoSelected(int id, int individual, String estado) {
        String action;
        if (estado.contains("PRINT")){
            action = "action_lpd_delete_admin";
        }else{
            action = "action_lpd_delete";
        }
        if (!getCruge(action)){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Borrar")+" "+getPalabras("Linea")+" " + getPalabras("Pedido")+" "+ id, Toast.LENGTH_SHORT).show();
            if (individual==1) {
                url_delete_id = Filtro.getUrl()+"/delete_id.php";
            }else{
                url_delete_id = Filtro.getUrl()+"/deletegrupo_id.php";
            }
            new DeleteLineaDocumentoPedido().execute(Integer.toString(id), "lpd");
        }
    }
    /**
     * Adding spinner data mesa
     */
    private void populateSpinnerMesaPedido() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Spinner cmbToolbarMesa = new Spinner(this);

        List<String> lables_mesa = new ArrayList<String>();

        for (int i = 0; i < mesaList.size(); i++) {
            lables_mesa.add(mesaList.get(i).getMesaNombre_Mesas());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_mesa = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_mesa);

        adapter_mesa.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarMesa.setAdapter(adapter_mesa);
        if (mesaList.size()>0) {
            Filtro.setMimesa(mesaList.get(0).getMesaMesa());
            comensales = Integer.toString(mesaList.get(0).getMesaComensales());
        }

        cmbToolbarMesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setMimesa(mesaList.get(cmbToolbarMesa.getSelectedItemPosition()).getMesaMesa());
                comensales = Integer.toString(mesaList.get(cmbToolbarMesa.getSelectedItemPosition()).getMesaComensales());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Mesa"));
        alert.setView(cmbToolbarMesa);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mesaList.size()>0) {

                        newvaluemesa = Filtro.getMimesa();

                        new UpdateMesaDocumentoPedido().execute(Integer.toString(idPedido),newvaluemesa,oldvaluemesa);

                        Toast.makeText(getApplicationContext(), newvaluemesa,
                                Toast.LENGTH_SHORT).show();
                }

            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }
    private void populateSpinnerEmpleadoPedido() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Spinner cmbToolbarEmpleado = new Spinner(this);

        List<String> lables_empleado = new ArrayList<String>();


        for (int i= 0; i < empleadosList.size(); i++) {
            lables_empleado.add(empleadosList.get(i).getEmpleadoNombre_Empleado());
        }

        ArrayAdapter<String> adapter_empleado = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_empleado);

        adapter_empleado.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarEmpleado.setAdapter(adapter_empleado);
        if (empleadosList.size()>0) {
            Filtro.setMiempleado(empleadosList.get(0).getEmpleadoEmpleado());
        }

        cmbToolbarEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setMiempleado(empleadosList.get(cmbToolbarEmpleado.getSelectedItemPosition()).getEmpleadoEmpleado());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Empleado"));
        alert.setView(cmbToolbarEmpleado);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (empleadosList.size()>0) {

                    newvalueempleado = Filtro.getMiempleado();

                    new UpdateEmpleadoDocumentoPedido().execute(Integer.toString(idPedido),newvalueempleado,oldvalueempleado);
//                        new UpdateEmpleadoApertura().execute("0",oldvalueempleado);
//                        new UpdateEmpleadoApertura().execute("1",newvalueempleado);

                    Toast.makeText(getApplicationContext(), newvalueempleado,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }

    public void onUpdateDocumentoPedidoSelected(int id, String valor, String campo) {

        if (!getCruge("action_pdd_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Pedido")+" "+ id, Toast.LENGTH_SHORT).show();
            idPedido = id;
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            switch (campo) {
                case "Mesa":
                    oldvaluemesa = valor;
                    newvaluemesa = valor;
                    mesaList = new ArrayList<Mesa>();
                    URL_MESA = Filtro.getUrl() + "/get_mesas.php";
                    new GetMesaPedido().execute(URL_MESA);
                    break;
                case "Empleado":
                    oldvalueempleado = valor;
                    newvalueempleado = valor;
 /////                   empleadoList = new ArrayList<Empleado>();
 /////                   URL_EMPLEADO = Filtro.getUrl() + "/get_empleados.php";
 /////                   new GetEmpleadoPedido().execute(URL_EMPLEADO);
                    populateSpinnerEmpleadoPedido();
                    break;
                case "Comensales":
                    final EditText inputcomensales = new EditText(this);
                    inputcomensales.setText(String.format("%02d", 0));

                    // Ponerse al final del edittext
                    int poscomensales = inputcomensales.getText().length();
                    inputcomensales.setSelection(poscomensales);

                    inputcomensales.setTextColor(Color.RED);
                    inputcomensales.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                    KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                    inputcomensales.setKeyListener(keyListener);
                    alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Comensales"));
                    alert.setView(inputcomensales);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = inputcomensales.getText().toString();
                            if (value.matches("")) {
                                Toast.makeText(getApplicationContext(), getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                //            this.btnGuardar.setEnabled(false);
                            } else {

                                value = value.replace(".", "");
                                value = value.replace(",", "");

                                inputcomensales.setText(String.format("%2d", Integer.valueOf(value)));

                                new UpdateComensalesDocumentoPedido().execute(Integer.toString(idPedido),value);

                                //                                               Snackbar.make(view, "Creando Documento Pedido", Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), inputcomensales.getText().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();

                    break;

                case "Obs":
                    final EditText input = new EditText(this);

                    String cValor = valor;
                    cValor = cValor.replace(Html.fromHtml("&nbsp;&nbsp;"),"");
                    valor=cValor;

                    input.setText(valor);

                    // Ponerse al final del edittext
                    int pos = input.getText().length();
                    input.setSelection(pos);

                    input.setTextColor(Color.RED);
                    alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Observacion"));
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString().trim();

                            new UpdateObsDocumentoPedido().execute(Integer.toString(idPedido),value);

                            Toast.makeText(getApplicationContext(), input.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();

                    break;
            }

        }
    }
    /**
     * Adding spinner data mesa
     */
    private void populateSpinnerMesaFactura() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Spinner cmbToolbarMesa = new Spinner(this);

        List<String> lables_mesa = new ArrayList<String>();

        for (int i = 0; i < mesaList.size(); i++) {
            lables_mesa.add(mesaList.get(i).getMesaNombre_Mesas());
//            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_mesa = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_mesa);

        adapter_mesa.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarMesa.setAdapter(adapter_mesa);
        if (mesaList.size()>0) {
            Filtro.setMimesa(mesaList.get(0).getMesaMesa());
            comensales = Integer.toString(mesaList.get(0).getMesaComensales());
        }

        cmbToolbarMesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setMimesa(mesaList.get(cmbToolbarMesa.getSelectedItemPosition()).getMesaMesa());
                comensales = Integer.toString(mesaList.get(cmbToolbarMesa.getSelectedItemPosition()).getMesaComensales());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Mesa"));
        alert.setView(cmbToolbarMesa);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mesaList.size()>0) {

                    newvaluemesa = Filtro.getMimesa();

                    new UpdateMesaDocumentoFactura().execute(Integer.toString(idFactura),newvaluemesa,oldvaluemesa);

                    Toast.makeText(getApplicationContext(), newvaluemesa,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }
    /**
     * Adding spinner data empleado
     */
    private void populateSpinnerEmpleadoFactura() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Spinner cmbToolbarEmpleado = new Spinner(this);

        List<String> lables_empleado = new ArrayList<String>();


        for (int i= 0; i < empleadosList.size(); i++) {
            lables_empleado.add(empleadosList.get(i).getEmpleadoNombre_Empleado());
        }

        ArrayAdapter<String> adapter_empleado = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_empleado);

        adapter_empleado.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarEmpleado.setAdapter(adapter_empleado);
        if (empleadosList.size()>0) {
            Filtro.setMiempleado(empleadosList.get(0).getEmpleadoEmpleado());
        }

        cmbToolbarEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setMiempleado(empleadosList.get(cmbToolbarEmpleado.getSelectedItemPosition()).getEmpleadoEmpleado());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Empleado"));
        alert.setView(cmbToolbarEmpleado);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (empleadosList.size()>0) {

                    newvalueempleado = Filtro.getMiempleado();

                    new UpdateEmpleadoDocumentoFactura().execute(Integer.toString(idFactura),newvalueempleado,oldvalueempleado);
//                        new UpdateEmpleadoApertura().execute("0",oldvalueempleado);
//                        new UpdateEmpleadoApertura().execute("1",newvalueempleado);

                    Toast.makeText(getApplicationContext(), newvalueempleado,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }
    public void onUpdateCobroDocumentoFacturaSelected(int id, String serie, String factura, String impcobro) {
        if (!getCruge("action_ftp_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            // starting background task to update Factura
            pid = String.valueOf(id);
            serie_ftp = serie;
            factura_ftp = Integer.parseInt(factura);
            new SaveCobroFactura().execute(
                    String.valueOf(id),
                    lparam.get(0).getDEFAULT_TIPO_COBRO_OPEN_FACTURA(),
                    "02",
                    impcobro,
                    "0.00",
                    "0.00",
                    ""
            );
        }
    }

    public void onUpdateDocumentoFacturaSelected(int id, String valor, String campo) {
        if (!getCruge("action_ftp_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
             Toast.makeText(this, getPalabras("Modificar")+" "+getPalabras("Factura")+" "+ id, Toast.LENGTH_SHORT).show();
             idFactura = id;
             final AlertDialog.Builder alert = new AlertDialog.Builder(this);
             switch (campo) {
                 case "Mesa":
                     oldvaluemesa = valor;
                     newvaluemesa = valor;
                     mesaList = new ArrayList<Mesa>();
                     URL_MESA = Filtro.getUrl() + "/get_mesas.php";
                     new GetMesaFactura().execute(URL_MESA);
                     break;
                 case "Empleado":
                     oldvalueempleado = valor;
                     newvalueempleado = valor;
/////                     empleadoList = new ArrayList<Empleado>();
/////                     URL_EMPLEADO = Filtro.getUrl() + "/get_empleados.php";
/////                     new GetEmpleadoFactura().execute(URL_EMPLEADO);
                     populateSpinnerEmpleadoFactura();
                     break;
                 case "Obs":
                     final EditText input = new EditText(this);

                     String cValor = valor;
                     cValor = cValor.replace(Html.fromHtml("&nbsp;&nbsp;"), "");
                     valor = cValor;

                     input.setText(valor);

                     // Ponerse al final del edittext
                     int pos = input.getText().length();
                     input.setSelection(pos);

                     input.setTextColor(Color.RED);
                     alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Observacion"));
                     alert.setView(input);
                     alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int whichButton) {
                             String value = input.getText().toString().trim();

                             new UpdateObsDocumentoFactura().execute(Integer.toString(idFactura), value);

                             Toast.makeText(getApplicationContext(), input.getText().toString(),
                                     Toast.LENGTH_SHORT).show();
                         }
                     });

                     alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int whichButton) {
                             dialog.cancel();
                         }
                     });
                     alert.show();

                     break;
             }

         }
    }
    public void onDeleteDocumentoPedidoSelected(int id, String estado, int pedido) {
        if (!getCruge("action_pdd_delete")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            if (estado.contains("CLOSE")) {
                Toast.makeText(this, getPalabras("Pedido")+" " + estado + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getPalabras("Borrar")+" "+ getPalabras("Pedido")+" "+estado+" "+ id, Toast.LENGTH_SHORT).show();
                new DeleteDocumentoPedido().execute(Integer.toString(id), Integer.toString(pedido), "pdd");
            }
        }
    }
    public void onInvoiceDocumentoPedidoSelected(int id, String estado) {
        if (!getCruge("action_ftp_create")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            if (estado.contains("CLOSE")) {
                Toast.makeText(this, getPalabras("Pedido")+" " + estado + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getPalabras("Traspaso")+" "+ getPalabras("Pedido")+" "+getPalabras("Factura")+" "+ id, Toast.LENGTH_SHORT).show();
                new TraspasoPedidoFactura().execute(Integer.toString(id));
            }
        }
    }

    public void onDeleteDocumentoFacturaSelected(int id, String estado, String serie, int factura) {
        if (!getCruge("action_ftp_delete")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            if (estado.contains("CLOSE")) {
                Toast.makeText(this, getPalabras("Factura")+" " + estado + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getPalabras("Borrar")+" "+ getPalabras("Factura")+" "+estado+" "+ id, Toast.LENGTH_SHORT).show();
                new DeleteDocumentoFactura().execute(Integer.toString(id), serie, Integer.toString(factura),"ftp");
            }
        }
    }
    public void onCobroDocumentoFacturaSelected(int id, String estado, String serie, String factura, String total, String obs) {
        if (!getCruge("action_ftp_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            if (estado.contains("CLOSE")) {
                Toast.makeText(this, getPalabras("Factura")+" " + estado + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                dialog_cobro(id, serie,factura,total, obs, "lista");
/*                OrderCES orderCES = new OrderCES.Builder(AppConfigImpl.class)
                        .transactionType(TransactionType.AUTORIZACION)
                        .currency(Currency.EUR)
                        .consumerLanguage(Language.SPANISH)
                        .order("FACTURA PRUEBA")
                        .amount(1000)
                        .productDescription("FACTURA RESTAURANTE")
                        .payMethods(PaymentMethod.TARJETA)
                        .urlOk("http://grupcirer.org/yii_aguassv/")
                        .urlKo("http://grupcirer.org/yii_aguasnt/")
                        .urlNotification("http://www.grupcirer.org/")
                        .build();
                MessageOrderCESRequest messageOrderCESRequest = new MessageOrderCESRequest.Builder(AppConfigImpl.class)
                        .withOrder(orderCES)
                        .build();
*/
                // VERSION CORRECTA
/*                CargaFragment cargafragment = null;
                cargafragment = new CargaFragment(EditCobroFacturaFragment.newInstance(Integer.toString(id),serie,factura,"lista"),getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                if (cargafragment.getFragment() != null){
                    cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                    Toast.makeText(this, getPalabras("Cobro")+" " + getPalabras("Factura") + " " + id, Toast.LENGTH_SHORT).show();
                }
*/
            }
        }
    }
 /*
    final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    final EditText inputCodigo = new EditText(this);
    final EditText inputNif = new EditText(this);
    final EditText inputRazon = new EditText(this);
    final EditText inputNombre = new EditText(this);
    final EditText inputDireccion = new EditText(this);
    final EditText inputPoblacion = new EditText(this);
    final EditText inputProvincia = new EditText(this);
    final EditText inputPais = new EditText(this);
    final EditText inputTelefono = new EditText(this);
    final EditText inputEmail = new EditText(this);

    int pos = 0;

    int maxLengthCodigo = 20;
    int maxLengthNif = 20;
    int maxLengthRazon = 128;
    int maxLengthNombre = 128;
    int maxLengthDireccion = 128;
    int maxLengthPoblacion = 128;
    int maxLengthProvincia = 128;
    int maxLengthPais = 128;
    int maxLengthTelefono = 15;
    int maxLengthEmail = 128;

        inputCodigo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthCodigo)});
        inputCodigo.setText("");

        inputNif.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthNif)});
        inputNif.setText("");

        inputRazon.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthRazon)});
        inputRazon.setText("");

        inputNombre.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthNombre)});
        inputNombre.setText("");

        inputDireccion.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthDireccion)});
        inputDireccion.setText("");

        inputPoblacion.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthPoblacion)});
        inputPoblacion.setText("");

        inputProvincia.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthProvincia)});
        inputProvincia.setText("");

        inputPais.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthPais)});
        inputPais.setText("");

        inputTelefono.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthTelefono)});
        inputTelefono.setText("");

        inputEmail.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthEmail)});
        inputEmail.setText("");

    // Ponerse al final del edittext
    pos = inputCodigo.getText().length();
        inputCodigo.setSelection(pos);

    pos = inputNif.getText().length();
        inputNif.setSelection(pos);

    pos = inputRazon.getText().length();
        inputRazon.setSelection(pos);

    pos = inputNombre.getText().length();
        inputNombre.setSelection(pos);

    pos = inputDireccion.getText().length();
        inputDireccion.setSelection(pos);

    pos = inputPoblacion.getText().length();
        inputPoblacion.setSelection(pos);

    pos = inputProvincia.getText().length();
        inputProvincia.setSelection(pos);

    pos = inputPais.getText().length();
        inputPais.setSelection(pos);

    pos = inputTelefono.getText().length();
        inputTelefono.setSelection(pos);

    pos = inputEmail.getText().length();
        inputEmail.setSelection(pos);

        inputCodigo.setTextColor(Color.RED);
        inputNif.setTextColor(Color.RED);
        inputRazon.setTextColor(Color.RED);
        inputNombre.setTextColor(Color.RED);
        inputDireccion.setTextColor(Color.RED);
        inputPoblacion.setTextColor(Color.RED);
        inputProvincia.setTextColor(Color.RED);
        inputPais.setTextColor(Color.RED);
        inputTelefono.setTextColor(Color.RED);
        inputEmail.setTextColor(Color.RED);

        inputCodigo.setHint(getPalabras("Codigo"));
        layout.addView(inputCodigo);

        inputNif.setHint(getPalabras("Nif"));
        layout.addView(inputNif);

        inputRazon.setHint(getPalabras("Razon"));
        layout.addView(inputRazon);

        inputNombre.setHint(getPalabras("Nombre"));
        layout.addView(inputNombre);

        inputDireccion.setHint(getPalabras("Direccion"));
        layout.addView(inputDireccion);

        inputPoblacion.setHint(getPalabras("Poblacion"));
        layout.addView(inputPoblacion);

        inputProvincia.setHint(getPalabras("Provincia"));
        layout.addView(inputProvincia);

        inputPais.setHint(getPalabras("Pais"));
        layout.addView(inputPais);

        inputTelefono.setHint(getPalabras("Telefono"));
        layout.addView(inputTelefono);

        inputEmail.setHint(getPalabras("Email"));
        layout.addView(inputEmail);

    /// RELLENAR SPINNER SERIE FACTURACION
    final TextView titleBox = new TextView(this);
        titleBox.setHint(getPalabras("Seleccionar")+" "+getPalabras("Serie")+" "+getPalabras("Factura"));
        titleBox.setTextColor(Color.RED);
        layout.addView(titleBox);

    final Spinner cmbToolbarFra = new Spinner(this);

    List<String> lables_fra = new ArrayList<String>();

        for (int i = 0; i < frafacturaList.size(); i++) {
        lables_fra.add(frafacturaList.get(i).getFraSerie());
        //            Log.i("zona ",zonasList.get(i).getDescripcion());
    }
    ArrayAdapter<String> adapter_fra = new ArrayAdapter<>(
            this,
            R.layout.appbar_filter_title,lables_fra);

        adapter_fra.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarFra.setAdapter(adapter_fra);
        if (frafacturaList.size()>0) {
        Filtro.setSerieFac(frafacturaList.get(0).getFraSerie());
    }

        layout.addView(cmbToolbarFra);

        cmbToolbarFra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
        int arg2, long arg3) {


            Filtro.setSerieFac(frafacturaList.get(cmbToolbarFra.getSelectedItemPosition()).getFraSerie());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    });




        alert.setTitle(getPalabras("Datos")+" "+getPalabras("Cliente"));
        alert.setView(layout);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {

            new InsertarCliente().execute(
                    inputCodigo.getText().toString().trim(),
                    inputNif.getText().toString().trim(),
                    inputRazon.getText().toString().trim(),
                    inputNombre.getText().toString().trim(),
                    inputDireccion.getText().toString().trim(),
                    inputPoblacion.getText().toString().trim(),
                    inputProvincia.getText().toString().trim(),
                    inputPais.getText().toString().trim(),
                    inputTelefono.getText().toString().trim(),
                    inputEmail.getText().toString().trim()

            );

        }
    });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.cancel();
        }
    });
        alert.show();
*/
 public void dialog_cobro(final int id, final String serie, final String factura, String total, String obs, final String origen){

     final LinearLayout layout = new LinearLayout(this);
     layout.setId(R.id.layoutbuffet);
     layout.setOrientation(LinearLayout.VERTICAL);

     final AlertDialog.Builder alert = new AlertDialog.Builder(this);
     alert.setPositiveButton("OK", null);
     alert.setNegativeButton(getPalabras("Cancelar"), null);

     String[] articulos = new String[tftList.size()];
     String space01 = new String(new char[01]).replace('\0', ' ');


     articulosList = new ArrayList<Articulos>();

     for (int i = 0; i < tftList.size(); i++) {
         //////////////////////////////////////////////////////////
         articulos[i] =  StringUtils.rightPad(tftList.get(i).getTipoCobroNombre_tft().trim(),32,space01);

///            Drawables d = LoadImageFromWebOperations(comidas.get(indiceSeccion).get(i).getUrlimagen());
         CheckBox opcion = new CheckBox(this);
         String valor = "";

         if(tftList.get(i).getTipoCobroT_fra().trim().equals("CC")){
             valor="0.00";
         }else{
             valor = total;
             valor = valor.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
             valor = valor.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
             valor = valor.replace(".", "");
             valor = valor.replace(",", ".");

             Filtro.setTotalcobro(Float.parseFloat(valor)); // PASAMOS VALOR TOTAL FACTURA A TOTAL COBRO
         }

         articulosList.add(new Articulos(articulos[i], "COBRO", opcion, Float.parseFloat(valor), id, serie, factura ));

     }
     ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter(this, articulosList);

     origenCobro = origen;
     mSelectedItems = new ArrayList();  // Where we track the selected items
     mSelectedItemsPreu = new ArrayList();

     boolean[] checkedItems = new boolean[articulos.length];
     /*   for (int i = 0; i < articulos.length; i++) {
            if (i==0) {
                checkedItems[i] = true;
                mSelectedItems.add(0);

            } else {
                checkedItems[i] = false;
            }
        }
*/

     /// MOSTRAR FACTURA Y TOTAL
     final LinearLayout layout1 = new LinearLayout(this);
     layout1.setOrientation(LinearLayout.HORIZONTAL);


     final TextView titleBoxTotalFactura = new TextView(this);
     titleBoxTotalFactura.setHint(getPalabras("Total"));
     titleBoxTotalFactura.setTextSize(30);
     titleBoxTotalFactura.setTextColor(Color.RED);
     layout1.addView(titleBoxTotalFactura);

 //    layout.addView(layout1);
     final TextView txtBoxSpace = new TextView(this);
     txtBoxSpace.setText("                                  ");
     final TextView txtBoxSpace1 = new TextView(this);
     txtBoxSpace1.setText("                    ");

     final LinearLayout layout2 = new LinearLayout(this);
     layout2.setOrientation(LinearLayout.HORIZONTAL);

     txtBoxTotalFactura = new TextView(this);
     txtBoxTotalFactura.setText(total);
     txtBoxTotalFactura.setTextSize(30);
     txtBoxTotalFactura.setTextColor(Color.RED);
     layout2.addView(txtBoxTotalFactura);

//     layout.addView(layout2);

     /// RELLENAR TOTAL COBRO
     final TextView titleBoxTotalCobro = new TextView(this);
     titleBoxTotalCobro.setHint(getPalabras("Total")+" "+getPalabras("Cobro"));
     titleBoxTotalCobro.setTextSize(30);
     titleBoxTotalCobro.setTextColor(Color.RED);
  //   layout.addView(titleBoxTotalCobro);

     txtBoxTotalCobro = new TextView(this);
     txtBoxTotalCobro.setId(R.id.totalbuffet);
     String myText = String.format("%1$,.2f", Float.parseFloat("0.00"));
     myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
     myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
     String newText="";
     for (int ii = 0; ii < (10-myText.length()); ii++) {
         newText+=space01;
     }
     newText +=myText;
     txtBoxTotalCobro.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());
     txtBoxTotalCobro.setTextSize(30);
     txtBoxTotalCobro.setTypeface(txtBoxTotalCobro.getTypeface(), Typeface.BOLD);
     txtBoxTotalCobro.setTextColor(Color.RED);
//     layout.addView(txtBoxTotalCobro);

     Log.i("cobrosaldo", "cobrotextViewID: "+String.valueOf(txtBoxTotalCobro.getId()));


     final TextView titleBoxDif = new TextView(this);
     titleBoxDif.setHint(getPalabras("Diferencia"));
     titleBoxDif.setTextSize(30);
     titleBoxDif.setTextColor(Color.RED);

     txtBoxDif = new TextView(this);
     txtBoxDif.setText(total);
     txtBoxDif.setTextSize(30);
     txtBoxDif.setTextColor(Color.RED);

     switch (Filtro.getOptipotablet()) {
         case 0:
             layout.addView(layout1);
             layout.addView(layout2);
             layout.addView(titleBoxTotalCobro);
             layout.addView(txtBoxTotalCobro);
             layout.addView(titleBoxDif);
             layout.addView(txtBoxDif);

             break;
         case 1:
             layout.addView(layout1);
             layout.addView(layout2);
             layout.addView(titleBoxTotalCobro);
             layout.addView(txtBoxTotalCobro);
             layout.addView(titleBoxDif);
             layout.addView(txtBoxDif);

             break;
         case 2:
             layout1.addView(txtBoxSpace);
             layout1.addView(titleBoxTotalCobro);
             layout1.addView(txtBoxSpace1);
             layout1.addView(titleBoxDif);
             layout.addView(layout1);
             layout2.addView(txtBoxTotalCobro);
             layout2.addView(txtBoxDif);
             layout.addView(layout2);
             break;
     }

     final TextView titleBoxObs = new TextView(this);
     titleBoxObs.setHint(getPalabras("Observaciones"));
     titleBoxObs.setTextSize(30);
     titleBoxObs.setTextColor(Color.RED);
     layout.addView(titleBoxObs);

     final EditText txtBoxObs = new EditText(this);
     txtBoxObs.setText(obs);
     txtBoxObs.setTextSize(30);
     txtBoxObs.setTextColor(Color.RED);
     layout.addView(txtBoxObs);


     alert.setTitle(getPalabras("Cobro")+" "+getPalabras("Factura")+" "+serie+" "+factura);
//     Drawables d = LoadImageFromWebOperations(lcategoria.get(indiceCategoria).getCategoriaUrlimagen());
     alert.setIcon(R.drawable.visa32);
     alert.setView(layout);
     alert.setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             // a choice has been made!
             ////              String selectedVal = _animals[which].getVal();
             ////                Log.d(TAG, "chosen " + selectedVal );
             ////                dialog.dismiss();
         }
     });

/*
                alert.setMultiChoiceItems(articulos, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                        Log.i("which",String.valueOf(which));
                        if (mSelectedItems.size()>0) {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);

                        }else{
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);

                        }

                    }
                });
*/
/*        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                     ArticuloBuffet = true;
                     nArticuloPositionSelected=0;
                     int i = 0;
                     for (Articulos articulo : articulosList) { //iterate element by element in a list
///                        Log.i("Articulo: ", articulo.getName()+" "+articulo.getCant());
                        if (articulo.getChecked() && articulo.getCant()!=0)  {
                            mSelectedItems.add(i);
                            mSelectedItemsCant.add(articulo.getCant());
///                            Log.i("Articulo OK: ", articulo.getName()+" "+articulo.getCant());
                        }
                        i++;
                    }

                if (mSelectedItems.size()>0) {
                    new CreaDocumentoFactura().execute();
                }else{
                    Toast.makeText(getApplicationContext(), getPalabras("Debe seleccionar un articulo"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ArticuloBuffet = false;
                dialog.cancel();
            }
        });
*/
     ////       alert.show();
     final AlertDialog dialogo = alert.create();
     dialogo.getWindow().setType(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
     dialogo.setOnShowListener(new DialogInterface.OnShowListener() {

         @Override
         public void onShow(DialogInterface dialog) {

             Button b = dialogo.getButton(AlertDialog.BUTTON_POSITIVE);
             b.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(View view) {
                     // TODO Do something
                     ArticuloCobro = true;
                     nArticuloPositionSelected=0;
                     mSelectedItems.clear();  // Where we track the selected items
                     mSelectedItemsPreu.clear();
                     int i = 0;
                     for (Articulos articulo : articulosList) { //iterate element by element in a list
///                        Log.i("Articulo: ", articulo.getName()+" "+articulo.getCant());
                         if (articulo.getChecked())  {
                             mSelectedItems.add(i);
                             mSelectedItemsPreu.add(articulo.getImporte());
///                            Log.i("Articulo OK: ", articulo.getName()+" "+articulo.getImporte());
                         }
                         i++;
                     }

                     if (mSelectedItems.size()>0) {

                         serie_ftp = serie;
                         factura_ftp = Integer.parseInt(factura);

                         String valorcobro = txtBoxTotalCobro.getText().toString();
                         valorcobro = valorcobro.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
                         valorcobro = valorcobro.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
                         valorcobro = valorcobro.replace(".", "");
                         valorcobro = valorcobro.replace(",", ".");

                         final String v_cobro = valorcobro;
                         String valordif = txtBoxDif.getText().toString();
                         valordif = valordif.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
                         valordif = valordif.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
                         valordif = valordif.replace(".", "");
                         valordif = valordif.replace(",", ".");

                         final String v_dif = valordif;
                         if (Float.parseFloat(valordif)>0){
                             AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);
                             builder.setTitle(getPalabras("Confirma Diferencia"));
                             builder.setMessage(getPalabras("Confirma cobro a pesar diferencia")+"? "+txtBoxDif.getText().toString());
                             builder.setCancelable(false);
                             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
                                     new SaveCobroFactura().execute(
                                             String.valueOf(id),
                                             tftList.get(mSelectedItems.get(0)).getTipoCobroT_fra(),
                                             "14",
                                             v_cobro,
                                             v_dif,
                                             "0.00",
                                             txtBoxObs.getText().toString()
                                     );
                                     dialogo.cancel();
                                 }
                             });

                             builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
                                 }
                             });

                             builder.show();
                         }else {
                             new SaveCobroFactura().execute(
                                     String.valueOf(id),
                                     tftList.get(mSelectedItems.get(0)).getTipoCobroT_fra(),
                                     "14",
                                     valorcobro,
                                     valordif,
                                     "0.00",
                                     txtBoxObs.getText().toString()
                             );
                             dialogo.cancel();
                         }
                     }else{
                         Toast.makeText(getApplicationContext(), getPalabras("Debe seleccionar tipo de cobro"), Toast.LENGTH_SHORT).show();
                     }

                 }
             });
             Button bc = dialogo.getButton(AlertDialog.BUTTON_NEGATIVE);
             bc.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(View view) {
                     // TODO Do something
                     ArticuloCobro = false;
                     dialogo.cancel();

                 }
             });

         }
     });

     dialogo.show();
     Window window = dialogo.getWindow();
     switch (Filtro.getOptipotablet()) {
         case 0:
             window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
             break;
         case 1:
             window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
             break;
         case 2:
             window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
             break;
     }
     dialogo.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
     dialogo.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
 }

    private void control_articulos_cobro(){
        Log.i("CONTROL ARTICULOS",Integer.toString(nArticuloPositionSelected)+" "+Integer.toString(mSelectedItems.size()));
        if (nArticuloPositionSelected < mSelectedItems.size()) {
            // Conversion precio a formato para base de datos
            String cValor = String.format("%1$,.2f", mSelectedItemsPreu.get(nArticuloPositionSelected));
            TaskHelper.execute(new CreaLineaDocumentoFacturaCobro(),
                    serie_ftp,
                    String.valueOf(factura_ftp),
                    tftList.get(mSelectedItems.get(nArticuloPositionSelected)).getTipoCobroT_fra(),
                    cValor,
                    String.valueOf(mSelectedItems.get(nArticuloPositionSelected))
            );
            nArticuloPositionSelected++;
        }else{
            ArticuloCobro=false;
            if(origenCobro.equals("factura")){
                this.setCabecera(getPalabras("Inicio"),Float.parseFloat("0.00"),0);
                CargaFragment cargafragment = new CargaFragment(FragmentoInicio.newInstance(),getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(this);
                if (cargafragment.getFragment() != null){
                    cargafragment.setTransaction(R.id.contenedor_principal);
                }

            }
            if(origenCobro.equals("division")) {
                Filtro.setCobroDesdeFactura(1);
                this.onBackPressed();
            }

            if(origenCobro.equals("lista")) {
                FragmentoOpenDocumentoFactura.getInstance().onResume();
            }
        }
    }
    class CreaLineaDocumentoFacturaCobro extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Crear")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            String importe = args[3];
            importe = importe.replace(Filtro.getSimbolo(),"");
            importe = importe.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
            importe = importe.replace(".","");
            importe = importe.replace(",",".");

            idPosition=Integer.parseInt(args[4]);

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("grupo", Filtro.getGrupo());
            values.put("empresa", Filtro.getEmpresa());
            values.put("local", Filtro.getLocal());
            values.put("seccion", Filtro.getSeccion());
            values.put("caja", Filtro.getCaja());
            values.put("serie", args[0]);
            values.put("factura", args[1]);
            values.put("t_fra",args[2]);
            values.put("importe", importe);
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParserNew.makeHttpRequest(url_create_ftpcobro,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            Integer success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (success == 1) {
                if (ArticuloCobro){
                    Log.i("Cobro Impresion",String.valueOf(idPosition)+" "+
                            tftList.get(idPosition).getTipoCobroCopia_print()+" "+
                            tftList.get(idPosition).getTipoCobroT_fra());
                    if(tftList.get(idPosition).getTipoCobroCopia_print()>0) {
                        for (int i = 0; i < tftList.get(idPosition).getTipoCobroCopia_print(); i++) {

                            PrintTicket printticket = new PrintTicket(ActividadPrincipal.this, factura_ftp, serie_ftp);
                            printticket.iniciarTicket();

                        }
                    }
                    control_articulos_cobro();
                }
                if (ArticuloBuffet){
                    if(tftList.get(idPosition).getTipoCobroCopia_print()>0) {
                        for (int i = 0; i < tftList.get(idPosition).getTipoCobroCopia_print(); i++) {

                            PrintTicket printticket = new PrintTicket(ActividadPrincipal.this, Filtro.getFactura(), Filtro.getSerieBuffet());
                            printticket.iniciarTicket();

                        }
                    }
                    control_articulos_buffet();
                }
                Toast.makeText(ActividadPrincipal.this, getPalabras("Crear") + " " + getPalabras("Linea"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Crear")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }

    public void dialog_buffet(){

        final LinearLayout layout = new LinearLayout(this);
        layout.setId(R.id.layoutbuffet);
        layout.setOrientation(LinearLayout.VERTICAL);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("OK", null);
        alert.setNegativeButton(getPalabras("Cancelar"), null);

/*
        //Para recyclerView in dialog
        final RecyclerView rv_ejemplo;
        final Button btn_obtener;
        final TextView tv_marcados;
        final AdaptadorObjetoSimple adaptador;
        final ObjetoSimple objectsimple;
        /////////////////////////////////

        LayoutInflater inflater = LayoutInflater.from(this);
        View content = inflater.inflate(R.layout.buffet_main_layout, null);
        rv_ejemplo = (RecyclerView) content.findViewById(R.id.rv_ejemplo);
        btn_obtener = (Button) content.findViewById(R.id.btn_obtener);
        tv_marcados = (TextView) content.findViewById(R.id.tv_marcados);
        //Cargamos una lista con 8 objetos de ejemplo
        final LinkedList objetosSimples = new LinkedList();
        for (int i = 0 ; i < 8 ; i++)
            objetosSimples.add(new ObjetoSimple(String.valueOf(i + 1)));

        //Asignamos un LayoutManager y un adaptador al RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_ejemplo.setLayoutManager(layoutManager);
        adaptador = new AdaptadorObjetoSimple(this, objetosSimples);
        rv_ejemplo.setAdapter(adaptador);

        layout.addView(content);
        //Asignamos una función al botón
        btn_obtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList marcados = adaptador.obtenerSeleccionados();
                String contenidoMarcados = "Marcados: ";
                for (Object os : marcados) contenidoMarcados += ((ObjetoSimple)os).getTexto() + ", ";
                tv_marcados.setText(contenidoMarcados);
            }
        });
*/
        String[] articulos = new String[larticulobuffet.size()];
        String space01 = new String(new char[01]).replace('\0', ' ');

        articulosList = new ArrayList<Articulos>();

        for (int i = 0; i < larticulobuffet.size(); i++) {
            //////////////////////////////////////////////////////////
            String myTextPreu = String.format("%1$,.2f", larticulobuffet.get(i).getArticuloPrecio());
            myTextPreu = myTextPreu.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextPreu = myTextPreu.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextPreu="";
            for (int ii = 0; ii < (8-myTextPreu.length()); ii++) {
                newTextPreu+=space01;
            }
            newTextPreu +=myTextPreu;
/*            articulos[i] =  String.format("%-80s", Html.fromHtml(newTextPreu.replace(" ", "&nbsp;&nbsp;")).toString()+ " " +
                    larticulo.get(i).getArticuloNombre() + " " + larticulo.get(i).getArticuloNombre_Plato()
            );
*/
            //////////////////////////////////////////////////////////

            String myTextNombreArticulo = String.format("%1$-32s", larticulobuffet.get(i).getArticuloNombre());
            myTextNombreArticulo = myTextNombreArticulo.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextNombreArticulo = myTextNombreArticulo.replaceAll("\\s+$", ""); // Quitamos espacios derecha

            articulos[i] =  StringUtils.rightPad(larticulobuffet.get(i).getArticuloNombre().trim(),32,space01)+" ("+
                    myTextPreu.toString()+" "+Filtro.getSimbolo()+")";

///            Drawables d = LoadImageFromWebOperations(comidas.get(indiceSeccion).get(i).getUrlimagen());
            CheckBox opcion = new CheckBox(this);
            final Button btnIncrease = new Button(this);
            final Button btnDecrement = new Button(this);

            articulosList.add(new Articulos(articulos[i], "BUFFET", opcion, 1, larticulobuffet.get(i).getArticuloPrecio(),0,btnIncrease, btnDecrement,larticulobuffet.get(i).getArticuloUrlimagen() ));

        }
        ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter(this, articulosList);


        mSelectedItems = new ArrayList();  // Where we track the selected items
        mSelectedItemsCant = new ArrayList();

        boolean[] checkedItems = new boolean[articulos.length];
     /*   for (int i = 0; i < articulos.length; i++) {
            if (i==0) {
                checkedItems[i] = true;
                mSelectedItems.add(0);

            } else {
                checkedItems[i] = false;
            }
        }
*/
        /// RELLENAR TOTAL
        final TextView titleBoxTotal = new TextView(this);
        titleBoxTotal.setHint(getPalabras("Total")+" "+getPalabras("Ticket"));
        titleBoxTotal.setTextSize(30);
        titleBoxTotal.setTextColor(Color.RED);
        layout.addView(titleBoxTotal);

        txtBoxTotal = new TextView(this);
        txtBoxTotal.setId(R.id.totalbuffet);
        String myText = String.format("%1$,.2f", Float.parseFloat("0.00"));
        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newText="";
        for (int ii = 0; ii < (10-myText.length()); ii++) {
            newText+=space01;
        }
        newText +=myText;
        txtBoxTotal.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());
        txtBoxTotal.setTextSize(30);
        txtBoxTotal.setTypeface(txtBoxTotal.getTypeface(), Typeface.BOLD);
        txtBoxTotal.setTextColor(Color.RED);
        layout.addView(txtBoxTotal);
        Log.i("buffetsaldo", "buffettextViewID: "+String.valueOf(txtBoxTotal.getId()));

        /// RELLENAR CANTIDAD
/*        final TextView titleBoxCant = new TextView(this);
        titleBoxCant.setHint(getPalabras("Seleccionar")+" "+getPalabras("Cantidad"));
        titleBoxCant.setTextColor(Color.RED);
        layout.addView(titleBoxCant);

        final LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);

        final TextView txtBoxCant = new TextView(this);
        txtBoxCant.setText("1");
        txtBoxCant.setTextSize(30);
        txtBoxCant.setTextColor(Color.RED);
        layout1.addView(txtBoxCant);
        cantbuffet = txtBoxCant.getText().toString();

        final TextView txtBoxSpace = new TextView(this);
        txtBoxSpace.setText("                    ");
        layout1.addView(txtBoxSpace);


        final Button btnIncrease = new Button(this);
        btnIncrease.setText("+");
        btnIncrease.setTextSize(30);
        layout1.addView(btnIncrease);
        btnIncrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                txtBoxCant.setText(String.valueOf(Integer.parseInt(txtBoxCant.getText().toString()) + 1));
                cantbuffet = txtBoxCant.getText().toString();

            }
        });
        final Button btnDecrement = new Button(this);
        btnDecrement.setText("-");
        btnDecrement.setTextSize(30);
        layout1.addView(btnDecrement);
        btnDecrement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                txtBoxCant.setText(String.valueOf(Integer.parseInt(txtBoxCant.getText().toString()) - 1));
                cantbuffet = txtBoxCant.getText().toString();

            }
        });
        layout.addView(layout1);
*/

        /// RELLENAR SPINNER MESAS BUFFET
        final TextView titleBoxMesas = new TextView(this);
        titleBoxMesas.setHint(getPalabras("Seleccionar")+" "+getPalabras("Mesa")+" "+getPalabras("Buffet"));
        titleBoxMesas.setTextColor(Color.RED);
        layout.addView(titleBoxMesas);

        final Spinner cmbToolbarMesas = new Spinner(this);

        List<String> lables_mesas = new ArrayList<String>();

        for (int i = 0; i < mesaListBuffet.size(); i++) {
            lables_mesas.add(mesaListBuffet.get(i).getMesaNombre_Mesas());
            //            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_mesas = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_mesas);

        adapter_mesas.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarMesas.setAdapter(adapter_mesas);
        if (mesaListBuffet.size()>0) {
            Filtro.setMimesa(mesaListBuffet.get(0).getMesaMesa());
        }

        layout.addView(cmbToolbarMesas);

        cmbToolbarMesas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setMimesa(mesaListBuffet.get(cmbToolbarMesas.getSelectedItemPosition()).getMesaMesa());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        /// RELLENAR SPINNER SERIE FACTURACION
        final TextView titleBoxFra = new TextView(this);
        titleBoxFra.setHint(getPalabras("Seleccionar")+" "+getPalabras("Serie")+" "+getPalabras("Ticket"));
        titleBoxFra.setTextColor(Color.RED);
        layout.addView(titleBoxFra);

        final Spinner cmbToolbarFra = new Spinner(this);

        List<String> lables_fra = new ArrayList<String>();
        int posfra = 0;
        for (int i = 0; i < fraList.size(); i++) {
            if(lparam.get(0).getDEFAULT_SERIE_BUFFET().equals(fraList.get(i).getFraSerie())){
                posfra=i;
            }
            lables_fra.add(fraList.get(i).getFraSerie());
            //            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_fra = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_fra);

        adapter_fra.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarFra.setAdapter(adapter_fra);
        if (fraList.size()>0) {
            cmbToolbarFra.setSelection(posfra);
            Filtro.setSerieBuffet(fraList.get(posfra).getFraSerie());
        }

        layout.addView(cmbToolbarFra);

        cmbToolbarFra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setSerieBuffet(fraList.get(cmbToolbarFra.getSelectedItemPosition()).getFraSerie());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /// RELLENAR SPINNER TIPOS COBRO
        final TextView titleBoxTft = new TextView(this);
        titleBoxTft.setHint(getPalabras("Seleccionar")+" "+getPalabras("Tipo")+" "+getPalabras("Cobro"));
        titleBoxTft.setTextColor(Color.RED);
        layout.addView(titleBoxTft);

        final Spinner cmbToolbarTft = new Spinner(this);

        List<String> lables_tft = new ArrayList<String>();
        int postft=0;
        for (int i = 0; i < tftbuffetList.size(); i++) {
            if(lparam.get(0).getDEFAULT_TIPO_COBRO_BUFFET().equals(tftbuffetList.get(i).getTipoCobroT_fra())){
                postft=i;
            }
            lables_tft.add(tftbuffetList.get(i).getTipoCobroNombre_tft());
            //            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_tft = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_tft);

        adapter_tft.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarTft.setAdapter(adapter_tft);
        if (tftbuffetList.size()>0) {
            cmbToolbarTft.setSelection(postft);
            Filtro.setT_fra(tftbuffetList.get(postft).getTipoCobroT_fra());
        }

        layout.addView(cmbToolbarTft);

        cmbToolbarTft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setT_fra(tftbuffetList.get(cmbToolbarTft.getSelectedItemPosition()).getTipoCobroT_fra());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        alert.setTitle(getPalabras("Articulo")+" "+ArticuloNombreGrupo);
        Drawable d = LoadImageFromWebOperations(lcategoria.get(indiceCategoria).getCategoriaUrlimagen());
        alert.setIcon(d);
        alert.setView(layout);
        alert.setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // a choice has been made!
                ////              String selectedVal = _animals[which].getVal();
                ////                Log.d(TAG, "chosen " + selectedVal );
                ////                dialog.dismiss();
            }
        });

/*
                alert.setMultiChoiceItems(articulos, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                        Log.i("which",String.valueOf(which));
                        if (mSelectedItems.size()>0) {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);

                        }else{
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);

                        }

                    }
                });
*/
/*        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                     ArticuloBuffet = true;
                     nArticuloPositionSelected=0;
                     int i = 0;
                     for (Articulos articulo : articulosList) { //iterate element by element in a list
///                        Log.i("Articulo: ", articulo.getName()+" "+articulo.getCant());
                        if (articulo.getChecked() && articulo.getCant()!=0)  {
                            mSelectedItems.add(i);
                            mSelectedItemsCant.add(articulo.getCant());
///                            Log.i("Articulo OK: ", articulo.getName()+" "+articulo.getCant());
                        }
                        i++;
                    }

                if (mSelectedItems.size()>0) {
                    new CreaDocumentoFactura().execute();
                }else{
                    Toast.makeText(getApplicationContext(), getPalabras("Debe seleccionar un articulo"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ArticuloBuffet = false;
                dialog.cancel();
            }
        });
*/
 ////       alert.show();
        final AlertDialog dialogo = alert.create();
        dialogo.getWindow().setType(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = dialogo.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        ArticuloBuffet = true;
                        nArticuloPositionSelected=0;
                        mSelectedItems.clear();  // Where we track the selected items
                        mSelectedItemsCant.clear();
                        int i = 0;
                        for (Articulos articulo : articulosList) { //iterate element by element in a list
///                        Log.i("Articulo: ", articulo.getName()+" "+articulo.getCant());
                            if (articulo.getChecked() && articulo.getCant()!=0)  {
                                mSelectedItems.add(i);
                                mSelectedItemsCant.add(articulo.getCant());
///                            Log.i("Articulo OK: ", articulo.getName()+" "+articulo.getCant());
                            }
                            i++;
                        }

                        if (mSelectedItems.size()>0) {
                            new CreaDocumentoFactura().execute();
                        }else{
                            Toast.makeText(getApplicationContext(), getPalabras("Debe seleccionar un articulo"), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                Button bc = dialogo.getButton(AlertDialog.BUTTON_NEGATIVE);
                bc.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        ArticuloBuffet = false;
                        dialogo.cancel();

                    }
                });

            }
        });

        dialogo.show();
        Window window = dialogo.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogo.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialogo.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
// Initially disable the button
/*        if (mSelectedItems.size()>0) {
            ((AlertDialog) dialogo).getButton(AlertDialog.BUTTON_POSITIVE)
                    .setEnabled(true);

        }else{
            ((AlertDialog) dialogo).getButton(AlertDialog.BUTTON_POSITIVE)
                    .setEnabled(false);

        }
        */
// Now set the textchange listener for edittext
/**        txtBoxCant.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().trim().equals("0")) {
                    // Disable ok button
                    ((AlertDialog) dialogo).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) dialogo).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if edittext is empty
**//*                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    ((AlertDialog) dialogo).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) dialogo).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
*/
/**            }
        });**/
    }
    class CreaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Crear")+" "+ActividadPrincipal.getPalabras("Factura")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */



        @Override
        protected Integer doInBackground(String... args) {

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            long maxDate = currentDate.getTime().getTime(); // Twice!

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("grupo", Filtro.getGrupo());
            values.put("empresa", Filtro.getEmpresa());
            values.put("local", Filtro.getLocal());
            values.put("seccion", Filtro.getSeccion());
            values.put("caja", Filtro.getCaja());
            values.put("cod_turno", Filtro.getTurno());
            values.put("serie", Filtro.getSerieBuffet());
            values.put("factura", Long.toString(maxDate));
//            values.put("factura", Integer.toString(Filtro.getFactura()));
            values.put("mesa", Filtro.getMimesa());
            values.put("estado", lparam.get(0).getDEFAULT_ESTADO_COBRO_FACTURA());
            values.put("fecha", Filtro.getFechaapertura());
            values.put("empleado", Filtro.getEmpleado());
            values.put("t_fra", Filtro.getT_fra());
            values.put("tabla", lparam.get(0).getDEFAULT_TABLA_OPEN_FACTURA());
            values.put("obs", "");
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            jsonParserNew = new JSONParserNew();
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_ftp,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            int success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
                Filtro.setFactura(json.getInt(TAG_FACTURA));
                Filtro.setId(json.getInt(TAG_ID));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (success == 1) {
                ArticuloBuffet = true;
                new CreaLineaDocumentoFacturaCobro().execute(
                        Filtro.getSerieBuffet(),
                        String.valueOf(Filtro.getFactura()),
                        Filtro.getT_fra(),
                        txtBoxTotal.getText().toString(),
                        "0"
                );

       //         control_articulos_buffet();
            } else {
                Toast.makeText(getApplicationContext(), "ERROR NO "+ActividadPrincipal.getPalabras("Crear")+" "+ActividadPrincipal.getPalabras("Factura"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }
    private void control_articulos_buffet(){
        Log.i("CONTROL ARTICULOS",Integer.toString(nArticuloPositionSelected)+" "+Integer.toString(mSelectedItems.size()));
        if (nArticuloPositionSelected < mSelectedItems.size()) {
            // Conversion precio a formato para base de datos
             String cValor = String.format("%1$,.2f", Double.valueOf(larticulobuffet.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloPrecio()));
             TaskHelper.execute(new CreaLineaDocumentoFactura(),
                    larticulobuffet.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloArticulo(),
                    larticulobuffet.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloNombre(),
                    cValor,
                    Integer.toString(larticulobuffet.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloTiva_id()),
                    String.valueOf(mSelectedItemsCant.get(nArticuloPositionSelected)));
            nArticuloPositionSelected++;
        }else{
            new CalculaCabecera().execute("ftp", "lft", "1");
        }
    }

    public void dialog_factura(){

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText inputSerie = new EditText(this);
                final EditText inputNif = new EditText(this);
                final EditText inputRazon = new EditText(this);
                final EditText inputFactura = new EditText(this);
                final EditText inputFecha = new EditText(this);
                final EditText inputTotal = new EditText(this);


                int maxLengthSerie = 10;
                int maxLengthNif = 25;
                int maxLengthRazon = 128;
                int maxLengthFactura = 25;
                int maxLengthFecha = 20;
                int maxLengthTotal = 25;

                inputSerie.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthSerie)});
                inputSerie.setText(getPalabras("Serie")+": "+lfac.get(0).getFacSerie());
                inputSerie.setInputType(InputType.TYPE_NULL);

                inputFactura.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthFactura)});
                inputFactura.setText(getPalabras("Factura")+": "+String.format("%08d",lfac.get(0).getFacFactura()));
                inputFactura.setInputType(InputType.TYPE_NULL);

                inputNif.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthNif)});
                inputNif.setText(getPalabras("NIF")+": "+lfac.get(0).getFacNif());
                inputNif.setInputType(InputType.TYPE_NULL);

                inputRazon.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthRazon)});
                inputRazon.setText(getPalabras("Razon")+": "+lfac.get(0).getFacRazon());
                inputRazon.setInputType(InputType.TYPE_NULL);

                String myText="";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    String StringRecogido = lfac.get(0).getFacFecha();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input ")+": "+datehora);
                    myText = sdf2.format(datehora);

                } catch (Exception e) {
                    e.getMessage();
                }
                inputFecha.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthFecha)});
                inputFecha.setText(getPalabras("Fecha")+": "+myText);
                inputFecha.setInputType(InputType.TYPE_NULL);

                inputTotal.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthTotal)});
                inputTotal.setText(getPalabras("Total")+": "+String.format("%1$,.2f", Float.parseFloat(lfac.get(0).getFacTotal()))+" "+Filtro.getSimbolo());
                inputTotal.setInputType(InputType.TYPE_NULL);



                inputSerie.setTextColor(Color.RED);
                inputNif.setTextColor(Color.RED);
                inputRazon.setTextColor(Color.RED);
                inputFactura.setTextColor(Color.RED);
                inputFecha.setTextColor(Color.RED);
                inputTotal.setTextColor(Color.RED);

                layout.addView(inputSerie);
                layout.addView(inputFactura);
                layout.addView(inputNif);
                layout.addView(inputRazon);
                layout.addView(inputFecha);
                layout.addView(inputTotal);


                alert.setTitle(getPalabras("Datos")+" "+getPalabras("Factura"));
                alert.setView(layout);

                alert.setPositiveButton(getPalabras("Imprimir"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // IMPRIMIR MEDIANTE WEBSERVICE SOAP
                        new WSFactura().execute();

                    }
                });

                alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();

    }
    public void dialog_cliente(){

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText inputCodigo = new EditText(this);
        final EditText inputNif = new EditText(this);
        final EditText inputRazon = new EditText(this);
        final EditText inputNombre = new EditText(this);
        final EditText inputDireccion = new EditText(this);
        final EditText inputPoblacion = new EditText(this);
        final EditText inputProvincia = new EditText(this);
        final EditText inputPais = new EditText(this);
        final EditText inputTelefono = new EditText(this);
        final EditText inputEmail = new EditText(this);

        int pos = 0;

        int maxLengthCodigo = 20;
        int maxLengthNif = 20;
        int maxLengthRazon = 128;
        int maxLengthNombre = 128;
        int maxLengthDireccion = 128;
        int maxLengthPoblacion = 128;
        int maxLengthProvincia = 128;
        int maxLengthPais = 128;
        int maxLengthTelefono = 15;
        int maxLengthEmail = 128;

        inputCodigo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthCodigo)});
        inputCodigo.setText("");

        inputNif.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthNif)});
        inputNif.setText("");

        inputRazon.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthRazon)});
        inputRazon.setText("");

        inputNombre.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthNombre)});
        inputNombre.setText("");

        inputDireccion.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthDireccion)});
        inputDireccion.setText("");

        inputPoblacion.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthPoblacion)});
        inputPoblacion.setText("");

        inputProvincia.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthProvincia)});
        inputProvincia.setText("");

        inputPais.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthPais)});
        inputPais.setText("");

        inputTelefono.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthTelefono)});
        inputTelefono.setText("");

        inputEmail.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthEmail)});
        inputEmail.setText("");

        // Ponerse al final del edittext
        pos = inputCodigo.getText().length();
        inputCodigo.setSelection(pos);

        pos = inputNif.getText().length();
        inputNif.setSelection(pos);

        pos = inputRazon.getText().length();
        inputRazon.setSelection(pos);

        pos = inputNombre.getText().length();
        inputNombre.setSelection(pos);

        pos = inputDireccion.getText().length();
        inputDireccion.setSelection(pos);

        pos = inputPoblacion.getText().length();
        inputPoblacion.setSelection(pos);

        pos = inputProvincia.getText().length();
        inputProvincia.setSelection(pos);

        pos = inputPais.getText().length();
        inputPais.setSelection(pos);

        pos = inputTelefono.getText().length();
        inputTelefono.setSelection(pos);

        pos = inputEmail.getText().length();
        inputEmail.setSelection(pos);

        inputCodigo.setTextColor(Color.RED);
        inputNif.setTextColor(Color.RED);
        inputRazon.setTextColor(Color.RED);
        inputNombre.setTextColor(Color.RED);
        inputDireccion.setTextColor(Color.RED);
        inputPoblacion.setTextColor(Color.RED);
        inputProvincia.setTextColor(Color.RED);
        inputPais.setTextColor(Color.RED);
        inputTelefono.setTextColor(Color.RED);
        inputEmail.setTextColor(Color.RED);

        inputCodigo.setHint(getPalabras("Codigo"));
        layout.addView(inputCodigo);

        inputNif.setHint(getPalabras("Nif"));
        layout.addView(inputNif);

        inputRazon.setHint(getPalabras("Razon"));
        layout.addView(inputRazon);

        inputNombre.setHint(getPalabras("Nombre"));
        layout.addView(inputNombre);

        inputDireccion.setHint(getPalabras("Direccion"));
        layout.addView(inputDireccion);

        inputPoblacion.setHint(getPalabras("Poblacion"));
        layout.addView(inputPoblacion);

        inputProvincia.setHint(getPalabras("Provincia"));
        layout.addView(inputProvincia);

        inputPais.setHint(getPalabras("Pais"));
        layout.addView(inputPais);

        inputTelefono.setHint(getPalabras("Telefono"));
        layout.addView(inputTelefono);

        inputEmail.setHint(getPalabras("Email"));
        layout.addView(inputEmail);

        /// RELLENAR SPINNER SERIE FACTURACION
        final TextView titleBox = new TextView(this);
        titleBox.setHint(getPalabras("Seleccionar")+" "+getPalabras("Serie")+" "+getPalabras("Factura"));
        titleBox.setTextColor(Color.RED);
        layout.addView(titleBox);

        final Spinner cmbToolbarFra = new Spinner(this);

        List<String> lables_fra = new ArrayList<String>();

        for (int i = 0; i < frafacturaList.size(); i++) {
            lables_fra.add(frafacturaList.get(i).getFraSerie());
            //            Log.i("zona ",zonasList.get(i).getDescripcion());
        }
        ArrayAdapter<String> adapter_fra = new ArrayAdapter<>(
                this,
                R.layout.appbar_filter_title,lables_fra);

        adapter_fra.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarFra.setAdapter(adapter_fra);
        if (frafacturaList.size()>0) {
            Filtro.setSerieFac(frafacturaList.get(0).getFraSerie());
        }

        layout.addView(cmbToolbarFra);

        cmbToolbarFra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Filtro.setSerieFac(frafacturaList.get(cmbToolbarFra.getSelectedItemPosition()).getFraSerie());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });




        alert.setTitle(getPalabras("Datos")+" "+getPalabras("Cliente"));
        alert.setView(layout);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                new InsertarCliente().execute(
                        inputCodigo.getText().toString().trim(),
                        inputNif.getText().toString().trim(),
                        inputRazon.getText().toString().trim(),
                        inputNombre.getText().toString().trim(),
                        inputDireccion.getText().toString().trim(),
                        inputPoblacion.getText().toString().trim(),
                        inputProvincia.getText().toString().trim(),
                        inputPais.getText().toString().trim(),
                        inputTelefono.getText().toString().trim(),
                        inputEmail.getText().toString().trim()

                );

            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }

    public void onFacturarDocumentoFacturaSelected(int id, String estado, String serie, String factura) {
        if (!getCruge("action_ftp_create")){ //HAY QUE CAMBIAR POR action_fac_create
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            serie_ftp=serie;
            factura_ftp=Integer.parseInt(factura);
            new CompruebaDocumentoFactura().execute(url_fac,serie,factura);
        }
    }
    public void onDeleteMessageSelected(int id, int activo) {
/*        if (!getCruge("action_message_delete")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
*/            if (activo==0) {
                Toast.makeText(this, getPalabras("Mensaje")+" " + Integer.toString(activo) + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getPalabras("Borrar")+" "+ getPalabras("Mensaje")+" "+Integer.toString(activo)+" "+ id, Toast.LENGTH_SHORT).show();
                new DeleteMessage().execute(Integer.toString(id),"message");
            }
//        }
    }
    public void onUpdateMessageSelected(int id, int activo, int comensales, String mesa, String user) {
/*        if (!getCruge("action_message_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
*/            if (activo==0) {
                Toast.makeText(this, getPalabras("Mensaje")+" " + Integer.toString(activo) + " " + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getPalabras("Activar")+" "+ getPalabras("Mensaje")+" "+Integer.toString(activo)+" "+ id, Toast.LENGTH_SHORT).show();
                new UpdateMessage().execute(Integer.toString(id), Integer.toString(comensales), mesa, "message", user);
            }
//        }
    }

    public void onUpdateDcjSelected(int id, String valor, String campo) {
        if (!getCruge("action_dcj_update")){
            Toast.makeText(this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getPalabras("Modificar")+" " + getPalabras("Diario Caja") + " " + id, Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            switch (campo) {
                case "Saldo_inicio":
                    final int ID = id;
                    final EditText input = new EditText(this);
                    valor = valor.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
                    valor = valor.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
                    valor = valor.replace(".", "");
                    valor = valor.replace(",", ".");

                    double xValor = Double.valueOf(valor);
                    input.setText(String.format("%1$,.2f", xValor));

                    // Ponerse al final del edittext
                    int pos = input.getText().length();
                    input.setSelection(pos);

                    input.setTextColor(Color.RED);
                    input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Saldo Inicio"));
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (value.matches("")) {
                                Toast.makeText(getApplicationContext(), getPalabras("Valor")+" " + getPalabras("Vacio") + " " + getPalabras("Saldo Inicio"), Toast.LENGTH_SHORT).show();
                                //            this.btnGuardar.setEnabled(false);
                            } else {

                                value = value.replace(".", "");
                                value = value.replace(",", ".");

                                input.setText(String.format("%1$,.2f", Double.valueOf(value)));

                                new UpdateSaldoInicioDcj().execute(Integer.toString(ID), value);

                                Toast.makeText(getApplicationContext(), input.getText().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                    break;
            }
        }
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        //TextView responseText = (TextView) findViewById(R.id.responseText);
        EditText myEditText = (EditText) view;
        // Ponerse al final del edittext
        int pos = myEditText.getText().length();
        myEditText.setSelection(pos);
        /////////////////////////////////////////
        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                int id = view.getId();
                if (id == R.id.Fecha_apertura) {
                }
                return true;
            }
        }
        return false; // pass on to other listeners.

    }

    @Override
    public void onBackPressed() {
        int count=0;
        if (null!=this.getSupportFragmentManager()) {
            // to prevent irritating accidental logouts
            FragmentManager fragManager = this.getSupportFragmentManager();
            count = this.getSupportFragmentManager().getBackStackEntryCount();
            Log.i("Count Fragments: ", Integer.toString(count) + " " + Filtro.getTag_fragment());
        }
        if (count==0) {
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) {    // 2 secs
                backPressedTime = t;
                Toast.makeText(this, getPalabras("Pulse otra vez BACK para cerrar"),
                        Toast.LENGTH_SHORT).show();
            } else {    // this guy is serious
                if (Filtro.getOplog()) {
                    try {
                        // delete the original file
                        Log.e("Delete Log", Filtro.getFilelog());
/////                        new File(Filtro.getFilelog()).delete();
                    } catch (Exception e) {
                        Log.e("Delete Log", e.getMessage());
                    }
                }
                // clean up
                super.onBackPressed();       // bye
             }
        }else{
            getSupportFragmentManager().popBackStack();
            Log.i("Fragment backpressed: ",Filtro.getTag_fragment());
            boolean idfragmentpages = false;
            CargaFragment cargafragment = null;
            switch (Filtro.getTag_fragment()) {
                case "FragmentoOpenDocumentoFactura":
                    cargafragment = new CargaFragment(FragmentoFactura.newInstance(0),getSupportFragmentManager());
                    break;
                case "FragmentoCloseDocumentoFactura":
                    cargafragment = new CargaFragment(FragmentoFactura.newInstance(1),getSupportFragmentManager());
                    break;
                case "FragmentoLineaDocumentoFactura":
                    cargafragment = new CargaFragment(FragmentoFactura.newInstance(0),getSupportFragmentManager());
                    idfragmentpages = true;
                    break;
                case "FragmentoOpenDocumentoPedido":
                    cargafragment = new CargaFragment(FragmentoPedido.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoCloseDocumentoPedido":
                    cargafragment = new CargaFragment(FragmentoPedido.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoLineaDocumentoPedido":
                    cargafragment = new CargaFragment(FragmentoPedido.newInstance(),getSupportFragmentManager());
                    idfragmentpages = true;
                    break;
                case "FragmentoOpenMessage":
                    cargafragment = new CargaFragment(FragmentoMessage.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoCloseMessage":
                    cargafragment = new CargaFragment(FragmentoMessage.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoOpenDcj":
                    cargafragment = new CargaFragment(FragmentoDcj.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoCloseDcj":
                    cargafragment = new CargaFragment(FragmentoDcj.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoPrintDcj":
                    cargafragment = new CargaFragment(FragmentoDcj.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoOpenCaja":
                    cargafragment = new CargaFragment(FragmentoCaja.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoCloseCaja":
                    cargafragment = new CargaFragment(FragmentoCaja.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoOpenSeccion":
                    cargafragment = new CargaFragment(FragmentoSeccion.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoCloseSeccion":
                    cargafragment = new CargaFragment(FragmentoSeccion.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoOpenTurno":
                    cargafragment = new CargaFragment(FragmentoTurno.newInstance(),getSupportFragmentManager());
                    break;
                case "FragmentoCloseTurno":
                    cargafragment = new CargaFragment(FragmentoTurno.newInstance(),getSupportFragmentManager());
                    break;
                case "EditOpenSeccionFragment":
                    cargafragment = new CargaFragment(FragmentoSeccion.newInstance(),getSupportFragmentManager());
                    break;
                case "EditCloseSeccionFragment":
                    cargafragment = new CargaFragment(FragmentoSeccion.newInstance(),getSupportFragmentManager());
                    break;
                default:
                    cargafragment = new CargaFragment(FragmentoInicio.newInstance(),getSupportFragmentManager());
                    break;
            }
            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
            if (cargafragment.getFragment() != null){
                if (idfragmentpages) {
                    cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                }else{
                    cargafragment.setTransaction(R.id.contenedor_principal);
                }
            }
        }
    }


    /**
     * Background Async Task to Create new product
     * */
    class CreaLineaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Crear")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            String cPreu = args[2];
            cPreu = cPreu.replace(Filtro.getSimbolo(),"");
            cPreu = cPreu.replace(".","");
            cPreu = cPreu.replace(",",".");

            String cant = args[4];
            String preu = cPreu;
            String importe = Double.toString(Double.parseDouble(cant)*Double.parseDouble(preu));

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("grupo", Filtro.getGrupo());
            values.put("empresa", Filtro.getEmpresa());
            values.put("local", Filtro.getLocal());
            values.put("seccion", Filtro.getSeccion());
            values.put("caja", Filtro.getCaja());
            values.put("serie", Filtro.getSerie());
            values.put("factura", Filtro.getFactura());
            values.put("articulo", args[0]);
            values.put("nombre", args[1]);
            values.put("cant", cant);
            values.put("preu", preu);
            values.put("importe",importe);
            values.put("tiva_id", args[3]);
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParserNew.makeHttpRequest(url_create_lft,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            Integer success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (success == 1) {
                if (ArticuloBuffet){
                    control_articulos_buffet();
                } else {

                    Toast.makeText(ActividadPrincipal.this, getPalabras("Crear") + " " + getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                    // find your fragment
                    ///              FragmentoLineaDocumentoPedido.getInstance().onResume();
                    new CalculaCabecera().execute("ftp", "lft", "1");
                }
            } else {
                Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Crear")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }

     /**
     * Background Async Task to Create new product
     * */
    class DeleteLineaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Borrar")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla",tabla);
                        values.put("filtro", "");
                        values.put("lintabla","");

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_delete_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Linea")+" "+pid, Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            new CalculaCabecera().execute("ftp","lft","1");
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class MinusCantLineaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Borrar")+" "+getPalabras("Cantidad")+" 1 "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla", tabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_minuscant_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
////            new CalculaCabecera().execute("ftp","lft","1");
            TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "1");
        }

    }
    /**
ge     * */
    class AddCantLineaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Insertar")+" "+getPalabras("Cantidad")+" 1 "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla", tabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_addcant_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Crear")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Crear")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
////            new CalculaCabecera().execute("ftp","lft","1");
            TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "1");

        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateMessage extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Mensaje")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            comensales = args[1];
            valuemesa = args[2];
            tabla = args[3];
            valueusermessage = args[4];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla", tabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_update_message,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Mensaje")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoMessage.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                            pDialog.dismiss();
//                            new UpdateMesaAperturaOnMessage().execute("1",comensales,valuemesa,valueusermessage);
                            new UpdateMesaAperturaOn().execute("1",comensales,valuemesa);
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Mensaje")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            if (pDialog!=null) {
                pDialog.dismiss();
            }
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdatePreuLineaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Precio")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            preu = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("preu",preu);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatepreu_lft,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Precio")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Precio")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
           new CalculaCabecera().execute("ftp","lft","1");
        }

    }
    class UpdateNombreLineaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Nombre")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            nombre = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("nombre",nombre);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatenombre_lft,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Nombre")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Nombre")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            new CalculaCabecera().execute("ftp","lft","1");

        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateAperturaDcj extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Apertura")+" "+getPalabras("Diario Caja")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("apertura","0");
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updateapertura_dcj,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
////                            FragmentoOpenDcj.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoDcj.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Apertura")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateSaldoInicioDcj extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Saldo Inicio")+" "+getPalabras("Caja")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            saldo_inicio = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("saldo_inicio",saldo_inicio);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatesaldo_inicio_dcj,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
////                            FragmentoOpenDcj.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoDcj.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Precio")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

        }

    }

    /**
     * Background Async Task to Create new product
     * */
    class CreaLineaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Crear")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            String cPreu = args[2];
            String cTiva = args[3];
            cPreu = cPreu.replace(Filtro.getSimbolo(),"");
            cPreu = cPreu.replace(".","");
            cPreu = cPreu.replace(",",".");

            String grupo = Filtro.getGrupo();
            String empresa = Filtro.getEmpresa();
            String local = Filtro.getLocal();
            String seccion = Filtro.getSeccion();
            String caja = Filtro.getCaja();
            String pedido = Integer.toString(Filtro.getPedido());
            String articulo = args[0];
            String nombre = args[1];
            String cant = "1";
            String preu = cPreu;
            String importe = Double.toString(Double.parseDouble(cant)*Double.parseDouble(preu));
            String tiva_id = cTiva;
            String obs = args[6];

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("grupo", grupo);
            values.put("empresa", empresa);
            values.put("local", local);
            values.put("seccion", seccion);
            values.put("caja", caja);
            values.put("pedido", pedido);
            values.put("articulo", articulo);
            values.put("nombre", nombre);
            values.put("cant", cant);
            values.put("preu", preu);
            values.put("importe",importe);
            values.put("tiva_id", tiva_id);
            values.put("obs", obs);
            values.put("swpedido", args[4]);
            values.put("swfactura", args[5]);
            values.put("idrelacion", args[7]);
            values.put("tipoplato", args[8]);
            values.put("principal", args[9]);
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_lpd,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            Integer success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
                if (success==1){
                    if ((ArticuloGrupo) && (nArticuloPositionSelected==0)){
                        ArticuloIdGrupo = json.getInt(TAG_ULTIMOID);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (success == 1) {
                Toast.makeText(ActividadPrincipal.this, getPalabras("Crear")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                // find your fragment
  ///              FragmentoLineaDocumentoPedido.getInstance().onResume();
                 new CalculaCabecera().execute("pdd","lpd","1");
            } else {
                Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Crear")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class DeleteLineaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Borrar")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla",tabla);
                        values.put("filtro", "");
                        values.put("lintabla","");

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_delete_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
///                            FragmentoLineaDocumentoPedido.getInstance().onResume();
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            new CalculaCabecera().execute("pdd","lpd","1");
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class AddCantLineaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Insertar")+" "+getPalabras("Cantidad")+" 1 "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
             // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla", tabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_addcant_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Crear")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
///                            FragmentoLineaDocumentoPedido.getInstance().onResume();
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Crear")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            new CalculaCabecera().execute("pdd","lpd","1");
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class MinusCantLineaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Borrar")+" "+getPalabras("Cantidad")+" 1 "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla", tabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_minuscant_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
///                            FragmentoLineaDocumentoPedido.getInstance().onResume();
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Cantidad")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            new CalculaCabecera().execute("pdd","lpd","1");

        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateObsLineaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            obs = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("obs",obs);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updateobs_lpd,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
                            FragmentoLineaDocumentoPedido.getInstance().onResume();
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateComensalesDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Comensales")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            comensales = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("comensales",comensales);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatecomensales_pdd,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Comensales")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoPedido.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoPedido.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Comensales")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    /**
     * Background Async Task to Create new product
     * */
    class UpdateObsDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Observacion")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            obs = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("obs",obs);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updateobs_pdd,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoPedido.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoPedido.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateObsDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Observacion")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            obs = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("obs",obs);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updateobs_ftp,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
 ////                           FragmentoOpenDocumentoFactura.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoFactura.newInstance(0), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Observacion")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateEmpleadoDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Empleado")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            valueempleado = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("empleado",valueempleado);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updateempleado_ftp,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                           Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Empleado")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoFactura.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoFactura.newInstance(0), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Empleado")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    class UpdateEmpleadoDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Empleado")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            valueempleado = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("empleado",valueempleado);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updateempleado_pdd,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Empleado")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoPedido.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoPedido.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Empleado")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    /**
     * Background Async Task to Create new product
     * */
    class UpdateMesaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Mesa")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            valuemesa = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("mesa",valuemesa);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_pdd,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Mesa")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoPedido.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoPedido.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Mesa")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
    /////        new UpdateMesaAperturaOff().execute("0","0",oldvaluemesa);
            new UpdateMesaAperturaOn().execute("1",comensales,newvaluemesa);
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateMesaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Mesa")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            valuemesa = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("mesa",valuemesa);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_ftp,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Modificar")+" "+getPalabras("Mesa")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
 ////                           FragmentoOpenDocumentoFactura.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoFactura.newInstance(0), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Mesa")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
////            new UpdateMesaAperturaOff().execute("0","0",oldvaluemesa);
            new UpdateMesaAperturaOn().execute("1",comensales,newvaluemesa);
        }

    }

    /**
     * Background Async Task to Create new product
     * */
    class UpdateMesaAperturaOff extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage("OFF "+getPalabras("Apertura")+" "+getPalabras("Mesa")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            comensales = args[1];
            valuemesa = args[2];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        String cSql = "";
                        String xWhere = "";

                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if(!(Filtro.getRango().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                            } else {
                                xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                            }
                        }

                        xWhere += " AND mesas.MESA='"+valuemesa+"'";

                        cSql += xWhere;
                        if(cSql.equals("")) {
                            cSql="Todos";
                        }
                        Log.i("Sql Lista",cSql);

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("filtro",cSql);
                        values.put("apertura", pid);
                        values.put("comensales",comensales);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_mesa,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, "OFF "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                            itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())-1));
                            if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                itemmesas.setTextColor(Filtro.getColorItemZero());
                            } else {
                                itemmesas.setTextColor(Filtro.getColorItem());
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO OFF "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            new UpdateMesaAperturaOn().execute("1",comensales,newvaluemesa);
        }

    }
    class UpdateMesaAperturaOnMessage extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage("ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            comensales = args[1];
            valuemesa = args[2];
            valueusermessage = args[3];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
/*                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        String cSql = "";
                        String xWhere = "";

                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if(!(Filtro.getRango().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                            } else {
                                xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                            }
                        }

                        xWhere += " AND mesas.MESA='"+valuemesa+"'";

                        cSql += xWhere;
                        if(cSql.equals("")) {
                            cSql="Todos";
                        }
                        Log.i("Sql Lista",cSql);
*/
                    final AlertDialog.Builder alert = new AlertDialog.Builder(ActividadPrincipal.this);
                    final EditText inputcomensales = new EditText(ActividadPrincipal.this);
                    inputcomensales.setText(String.format("%02d", Integer.parseInt(comensales)));

                    // Ponerse al final del edittext
                    int poscomensales = inputcomensales.getText().length();
                    inputcomensales.setSelection(poscomensales);

                    inputcomensales.setTextColor(Color.RED);
                    inputcomensales.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                    KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                    inputcomensales.setKeyListener(keyListener);
                    alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Comensales"));
                    alert.setView(inputcomensales);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = inputcomensales.getText().toString();
                            if (value.matches("")) {
                                Toast.makeText(getApplicationContext(), getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                //            this.btnGuardar.setEnabled(false);
                            } else {

                                value = value.replace(".", "");
                                value = value.replace(",", "");

                                inputcomensales.setText(String.format("%2d", Integer.valueOf(value)));
                                comensales=inputcomensales.getText().toString();
                                try {
                                    int success;
                                    Calendar currentDate = Calendar.getInstance(); //Get the current date
                                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                                    String dateNow = formatter.format(currentDate.getTime());

                                    String cSql = "";
                                    String xWhere = "";

                                    if(!(Filtro.getGrupo().equals(""))) {
                                        if (xWhere.equals("")) {
                                            xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                                        } else {
                                            xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                                        }
                                    }
                                    if(!(Filtro.getEmpresa().equals(""))) {
                                        if (xWhere.equals("")) {
                                            xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                                        } else {
                                            xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                                        }
                                    }
                                    if(!(Filtro.getLocal().equals(""))) {
                                        if (xWhere.equals("")) {
                                            xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                                        } else {
                                            xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                                        }
                                    }
                                    if(!(Filtro.getSeccion().equals(""))) {
                                        if (xWhere.equals("")) {
                                            xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                                        } else {
                                            xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                                        }
                                    }
                                    if(!(Filtro.getCaja().equals(""))) {
                                        if (xWhere.equals("")) {
                                            xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                                        } else {
                                            xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                                        }
                                    }
                                    if(!(Filtro.getRango().equals(""))) {
                                        if (xWhere.equals("")) {
                                            xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                                        } else {
                                            xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                                        }
                                    }

                                    xWhere += " AND mesas.MESA='"+valuemesa+"'";

                                    cSql += xWhere;
                                    if(cSql.equals("")) {
                                        cSql="Todos";
                                    }
                                    Log.i("Sql Lista",cSql);
                                    // Building Parameters
                                    ContentValues values = new ContentValues();
                                    values.put("filtro",cSql);
                                    values.put("apertura", pid);
                                    values.put("comensales",comensales);
                                    values.put("updated", dateNow);
                                    values.put("usuario", valueusermessage);
                                    values.put("ip",getLocalIpAddress());

                                    // getting JSON Object
                                    // Note that create product url accepts POST method
                                    JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_mesa,
                                            "POST", values);

                                    // check log cat fro response
                                    //            Log.d("Create Response", json.toString());

                                    // check for success tag

                                    success = json.getInt(TAG_SUCCESS);
                                    if (success == 1) {
                                        Toast.makeText(ActividadPrincipal.this, "ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                                        itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())+1));
                                        if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                            itemmesas.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemmesas.setTextColor(Filtro.getColorItem());
                                        }
                                    } else {
                                        Toast.makeText(ActividadPrincipal.this, "ERROR NO ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                                        // failed to create product
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //                                               Snackbar.make(view, "Creando Documento Pedido", Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), inputcomensales.getText().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
/*
                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("filtro",cSql);
                        values.put("apertura", pid);
                        values.put("comensales",comensales);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_mesa,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, "ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                            itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())+1));
                            if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                itemmesas.setTextColor(Filtro.getColorItemZero());
                            } else {
                                itemmesas.setTextColor(Filtro.getColorItem());
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class UpdateMesaAperturaOn extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage("ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            comensales = args[1];
            valuemesa = args[2];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
/*                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        String cSql = "";
                        String xWhere = "";

                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if(!(Filtro.getRango().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                            } else {
                                xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                            }
                        }

                        xWhere += " AND mesas.MESA='"+valuemesa+"'";

                        cSql += xWhere;
                        if(cSql.equals("")) {
                            cSql="Todos";
                        }
                        Log.i("Sql Lista",cSql);
*/
                        final AlertDialog.Builder alert = new AlertDialog.Builder(ActividadPrincipal.this);
                        final EditText inputcomensales = new EditText(ActividadPrincipal.this);
                        inputcomensales.setText(String.format("%02d", Integer.parseInt(comensales)));

                        // Ponerse al final del edittext
                        int poscomensales = inputcomensales.getText().length();
                        inputcomensales.setSelection(poscomensales);

                        inputcomensales.setTextColor(Color.RED);
                        inputcomensales.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                        KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                        inputcomensales.setKeyListener(keyListener);
                        alert.setTitle(getPalabras("Modificar")+" "+getPalabras("Comensales"));
                        alert.setView(inputcomensales);
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value = inputcomensales.getText().toString();
                                if (value.matches("")) {
                                    Toast.makeText(getApplicationContext(), getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                    //            this.btnGuardar.setEnabled(false);
                                } else {

                                    value = value.replace(".", "");
                                    value = value.replace(",", "");

                                    inputcomensales.setText(String.format("%2d", Integer.valueOf(value)));
                                    comensales=inputcomensales.getText().toString();
                                    try {
                                        int success;
                                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                                        String dateNow = formatter.format(currentDate.getTime());

                                        String cSql = "";
                                        String xWhere = "";

                                        if(!(Filtro.getGrupo().equals(""))) {
                                            if (xWhere.equals("")) {
                                                xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                                            } else {
                                                xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                                            }
                                        }
                                        if(!(Filtro.getEmpresa().equals(""))) {
                                            if (xWhere.equals("")) {
                                                xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                                            } else {
                                                xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                                            }
                                        }
                                        if(!(Filtro.getLocal().equals(""))) {
                                            if (xWhere.equals("")) {
                                                xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                                            } else {
                                                xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                                            }
                                        }
                                        if(!(Filtro.getSeccion().equals(""))) {
                                            if (xWhere.equals("")) {
                                                xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                                            } else {
                                                xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                                            }
                                        }
                                        if(!(Filtro.getCaja().equals(""))) {
                                            if (xWhere.equals("")) {
                                                xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                                            } else {
                                                xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                                            }
                                        }
                                        if(!(Filtro.getRango().equals(""))) {
                                            if (xWhere.equals("")) {
                                                xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                                            } else {
                                                xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                                            }
                                        }

                                        xWhere += " AND mesas.MESA='"+valuemesa+"'";

                                        cSql += xWhere;
                                        if(cSql.equals("")) {
                                            cSql="Todos";
                                        }
                                        Log.i("Sql Lista",cSql);
                                        // Building Parameters
                                        ContentValues values = new ContentValues();
                                        values.put("filtro",cSql);
                                        values.put("apertura", pid);
                                        values.put("comensales",comensales);
                                        values.put("updated", dateNow);
                                        values.put("usuario", Filtro.getUsuario());
                                        values.put("ip",getLocalIpAddress());

                                        // getting JSON Object
                                        // Note that create product url accepts POST method
                                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_mesa,
                                                "POST", values);

                                        // check log cat fro response
                                        //            Log.d("Create Response", json.toString());

                                        // check for success tag

                                        success = json.getInt(TAG_SUCCESS);
                                        if (success == 1) {
                                            Toast.makeText(ActividadPrincipal.this, "ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                                            itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())+1));
                                            if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                                itemmesas.setTextColor(Filtro.getColorItemZero());
                                            } else {
                                                itemmesas.setTextColor(Filtro.getColorItem());
                                            }
                                        } else {
                                            Toast.makeText(ActividadPrincipal.this, "ERROR NO ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                                            // failed to create product
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //                                               Snackbar.make(view, "Creando Documento Pedido", Snackbar.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), inputcomensales.getText().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
/*
                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("filtro",cSql);
                        values.put("apertura", pid);
                        values.put("comensales",comensales);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_mesa,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, "ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                            itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())+1));
                            if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                itemmesas.setTextColor(Filtro.getColorItemZero());
                            } else {
                                itemmesas.setTextColor(Filtro.getColorItem());
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO ON "+getPalabras("Apertura")+" "+getPalabras("Mesa")+" "+valuemesa, Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
     /**
     * Background Async Task to Create new product
     * */
    class DeleteMessage extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Borrar")+" "+getPalabras("Mensaje")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[1];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        String filtro = "";
                        String xWhere = "";

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla",tabla);
                        values.put("filtro", filtro);
                        values.put("lintabla", "");

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_delete_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoPedido.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoMessage.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    /**
     * Background Async Task to Create new product
     * */
    class DeleteDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Delete")+" "+getPalabras("Pedido")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[2];
            final int nPedido = Integer.parseInt(args[1]);
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        String filtro = "";
                        String xWhere = "";
                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND CAJA='" + Filtro.getCaja() + "'";
                            }
                        }

                        if(!(nPedido==0)) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE PEDIDO=" + nPedido;
                            } else {
                                xWhere += " AND PEDIDO=" + nPedido;
                            }
                        }
                        filtro+=xWhere;
                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla",tabla);
                        values.put("filtro", filtro);
                        values.put("lintabla", "lpd");

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_delete_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoPedido.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoPedido.newInstance(), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    /**
     * Background Async Task to Create new product
     * */
    class DeleteDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Delete")+" "+getPalabras("Factura")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
            tabla = args[3];
            final String cSerie = args[1];
            final int nFactura = Integer.parseInt(args[2]);
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        String filtro = "";
                        String xWhere = "";
                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if(!(cSerie.equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE SERIE='" + cSerie + "'";
                            } else {
                                xWhere += " AND SERIE='" + cSerie + "'";
                            }
                        }

                        if(!(nFactura==0)) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE FACTURA=" + nFactura;
                            } else {
                                xWhere += " AND FACTURA=" + nFactura;
                            }
                        }
                        filtro+=xWhere;
                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("tabla",tabla);
                        values.put("filtro", filtro);
                        values.put("lintabla", "lft");

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_delete_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(ActividadPrincipal.this, getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // find your fragment
////                            FragmentoOpenDocumentoFactura.getInstance().onResume();
                            CargaFragment cargafragment = null;
                            cargafragment = new CargaFragment(FragmentoFactura.newInstance(0), getSupportFragmentManager());
                            cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                            if (cargafragment.getFragment() != null) {
                                cargafragment.setTransaction(R.id.contenedor_principal);
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Borrar")+" "+getPalabras("Linea"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    /**
     * Background Async Task to Traspaso Pedido a Factura
     * */
    class CalculaCabecera extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Calcula")+" "+getPalabras("Cabecera")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            tabla = args[0];
            lintabla = args[1];
            final String ok_resume = args[2];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        String filtro = "";
                        String xWhere = "";
                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += lintabla+".GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND "+lintabla+".GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += lintabla+".EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND "+lintabla+".EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += lintabla+".LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND "+lintabla+".LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += lintabla+".SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND "+lintabla+".SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += lintabla+".CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND "+lintabla+".CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if (tabla=="pdd") {
                            if(!(Filtro.getPedido()==0)) {
                                if (xWhere.equals("")) {
                                    xWhere += lintabla+".PEDIDO=" + Filtro.getPedido();
                                } else {
                                    xWhere += " AND "+lintabla+".PEDIDO=" + Filtro.getPedido();
                                }
                            }
                        } else {
                            if(!(Filtro.getSerie().equals(""))) {
                                if (xWhere.equals("")) {
                                    xWhere += lintabla+".SERIE='" + Filtro.getSerie() + "'";
                                } else {
                                    xWhere += " AND "+lintabla+".SERIE='" + Filtro.getSerie() + "'";
                                }
                            }

                            if(!(Filtro.getFactura()==0)) {
                                if (xWhere.equals("")) {
                                    xWhere += lintabla+".FACTURA=" + Filtro.getFactura();
                                } else {
                                    xWhere += " AND "+lintabla+".FACTURA=" + Filtro.getFactura();
                                }
                            }

                        }

                        filtro+=xWhere;

                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("filtro",filtro);
                        values.put("tabla",tabla);
                        values.put("lintabla",lintabla);
                        values.put("ivaincluido",Filtro.getIvaIncluido());
                        values.put("updated", dateNow);
                        values.put("creado", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_update_cabecera,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        double saldo = Double.valueOf(json.getString(TAG_SALDO));
                        Log.i("Saldo",Integer.toString(success)+" "+Double.toString(saldo)+" "+tabla+" "+lintabla+" "+ok_resume);

///                        int layoutID = getResources().getIdentifier("cabecera_drawer", "layout", getPackageName());
///                        int textviewID = getResources().getIdentifier("etiqueta_carrito", "id", getPackageName());

                        if (success == 1) {
                            // Datos en menu drawer
                            int textViewID = getResources().getIdentifier("texto_total_carrito", "id", getPackageName());
                            TextView textSaldo = (TextView) findViewById(textViewID);
                            if (textSaldo != null) {
                                textSaldo.setText(String.format("%1$,.2f", saldo) + " " + Filtro.getSimbolo());
                            }
                            // Datos en appbar
   //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                            int txtViewID = getResources().getIdentifier("total_carrito", "id", getPackageName());
                            TextView txtSaldo = (TextView) findViewById(txtViewID);
                            txtSaldo.setText(String.format("%1$,.2f", saldo)+" "+Filtro.getSimbolo());
                            txtSaldo.setTextSize(30);
   ///                         Log.i("Saldo Dentro ","txtViewID:"+Integer.toString(txtViewID)+" Total Drawer: "+textSaldo.getText().toString()+" Total Appbar: "+txtSaldo.getText().toString());


                            if (ok_resume=="1"){
                                switch (tabla) {
                                    case "pdd":
                                        Utils.setBadgeCount(ActividadPrincipal.this, iconCarrito, Filtro.getPedido());
                                        FragmentoLineaDocumentoPedido.getInstance().onResume();
                                        break;
                                    case "ftp":
                                        Utils.setBadgeCount(ActividadPrincipal.this, iconCarrito, Filtro.getFactura());
                                        FragmentoLineaDocumentoFactura.getInstance().onResume();
                                        FragmentoDivisionLineaDocumentoFactura.getInstance().onResume();
//                                        Log.i("Saldo Dentro Tabla "," Factura"+Filtro.getFactura()+" txtViewID:"+Integer.toString(txtViewID)+" Total Drawer: "+textSaldo.getText().toString()+" Total Appbar: "+txtSaldo.getText().toString());
                                        break;
                                }

                            }else{
                                switch (tabla) {
                                    case "pdd":
                                        Utils.setBadgeCount(ActividadPrincipal.this, iconCarrito, Filtro.getPedido());
                                        break;
                                    case "ftp":
                                        Utils.setBadgeCount(ActividadPrincipal.this, iconCarrito, Filtro.getFactura());
//                                        Log.i("Saldo Dentro Tabla "," Factura"+Filtro.getFactura()+" txtViewID:"+Integer.toString(txtViewID)+" Total Drawer: "+textSaldo.getText().toString()+" Total Appbar: "+txtSaldo.getText().toString());
                                        break;
                                }
                            }
                            if (ArticuloGrupo){
                                pDialog.dismiss();
                                control_articulos();
                            }
                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Cabecera"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (ArticuloBuffet){
                ArticuloBuffet = false;
                PrintTicket printticket = new PrintTicket(ActividadPrincipal.this,Filtro.getFactura(),Filtro.getSerie());
                printticket.iniciarTicket();
            }
        }

    }
    class InsertarCliente extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Crear")+" "+getPalabras("Cliente")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // updating UI from Background Thread
            int success=0;
            try {
                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                Filtro.setCod_cliente(args[0]);
                // Building Parameters
                ContentValues values = new ContentValues();
                values.put("grupo", Filtro.getGrupo());
                values.put("empresa",Filtro.getEmpresa());
                values.put("cliente", args[0]);
                values.put("nif",args[1]);
                values.put("razon", args[2]);
                values.put("nombre",args[3]);
                values.put("direccion", args[4]);
                values.put("poblacion",args[5]);
                values.put("provincia", args[6]);
                values.put("pais",args[7]);
                values.put("telefono", args[8]);
                values.put("email",args[9]);
                values.put("creado", dateNow);
                values.put("updated", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());

                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParserNew.makeHttpRequest(url_create_cliente,
                        "POST", values);

                // check log cat fro response
                //            Log.d("Create Response", json.toString());

                // check for success tag

                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (success == 1) {
                new TraspasoTicketFactura().execute();
            } else {
                Log.e(TAG_FAC, "Failed to fetch data!");
            }

        }

    }

    public class CompruebaDocumentoFactura extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFac = new ProgressDialog(ActividadPrincipal.this);
            pDialogFac.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+getPalabras("Facturas")+"...");
            pDialogFac.setIndeterminate(false);
            pDialogFac.setCancelable(true);
            pDialogFac.show();
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE frf.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND frf.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE frf.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND frf.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE frf.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND frf.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE frf.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND frf.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE frf.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND frf.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            xWhere += " AND frf.SERIE_FTP='"+params[1]+"'";
            xWhere += " AND frf.FACTURA_FTP="+params[2];

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Fac> it = lfac.iterator(); it.hasNext();){
                        Fac fac = it.next();
                        it.remove();
                    }

                    parseResultFac(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogFac.dismiss();
            if (result == 1) {
                Log.e(TAG_FAC, "OK FAC");
                if(lfac.size()>0){
                    Toast.makeText(
                        getApplicationContext(), "FACTURA EXISTE " + lfac.get(0).getFacSerie() + " " + lfac.get(0).getFacFactura(),
                        Toast.LENGTH_LONG).show();
                    dialog_factura();
                }else{
                    Toast.makeText(getApplicationContext(), "FACTURA NO EXISTE", Toast.LENGTH_LONG).show();

                    URL_FRA = Filtro.getUrl() + "/get_fras.php";
                    new GetFraFactura().execute(URL_FRA);

                 ////*****   new TraspasoTicketFactura().execute();

                }
            } else {
                Log.e(TAG_FAC, "Failed to fetch data!");
            }
        }
    }
    private void parseResultFac(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Log.i("POST: ",post.optString("SERIE")+ post.optInt("FACTURA"));
                Fac fac = new Fac(
                        post.optInt("ID"),
                        post.optString("SERIE"),
                        post.optInt("FACTURA"),
                        post.optString("NIF"),
                        post.optString("RAZON"),
                        post.optString("TOTAL"),
                        post.optString("FECHA"));
                lfac.add(fac);
                Log.i("POST Longitud: ",Integer.toString(lfac.size()));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class TraspasoTicketFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Traspaso")+" "+getPalabras("Ticket")+" "+getPalabras("Factura")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // updating UI from Background Thread
            int success=0;
            try {
                String filtro = "";
                String xWhere = "";
                if(!(Filtro.getGrupo().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += "GRUPO='" + Filtro.getGrupo() + "'";
                    } else {
                        xWhere += " AND GRUPO='" + Filtro.getGrupo() + "'";
                    }
                }
                if(!(Filtro.getEmpresa().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += "EMPRESA='" + Filtro.getEmpresa() + "'";
                    } else {
                        xWhere += " AND EMPRESA='" + Filtro.getEmpresa() + "'";
                    }
                }
                if(!(Filtro.getLocal().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += "LOCAL='" + Filtro.getLocal() + "'";
                    } else {
                        xWhere += " AND LOCAL='" + Filtro.getLocal() + "'";
                    }
                }
                if(!(Filtro.getSeccion().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += "SECCION='" + Filtro.getSeccion() + "'";
                    } else {
                        xWhere += " AND SECCION='" + Filtro.getSeccion() + "'";
                    }
                }
                if(!(Filtro.getCaja().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += "CAJA='" + Filtro.getCaja() + "'";
                    } else {
                        xWhere += " AND CAJA='" + Filtro.getCaja() + "'";
                    }
                }
                filtro+=xWhere;

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                long maxDate = currentDate.getTime().getTime(); // Twice!

                // Building Parameters
                ContentValues values = new ContentValues();
                values.put("serie_ftp", serie_ftp);
                values.put("factura_ftp", factura_ftp);
                values.put("filtro",filtro);
                values.put("cliente",Filtro.getCod_cliente());
                values.put("tabla",lparam.get(0).getDEFAULT_TABLA_OPEN_FACTURA_CLIENTES());
                values.put("serie",Filtro.getSerieFac());
                values.put("factura",Long.toString(maxDate));
                values.put("updated", dateNow);
                values.put("creado", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());

                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParserNew.makeHttpRequest(url_ticket_a_factura,
                        "POST", values);

                // check log cat fro response
                //            Log.d("Create Response", json.toString());

                // check for success tag

                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (success == 1) {
               new CompruebaDocumentoFactura().execute(url_fac,serie_ftp,String.valueOf(factura_ftp));
            } else {
                Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Traspaso")+" "+getPalabras("Ticket")+" "+getPalabras("Factura"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }

        }

    }
    public class GetFraFactura extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogFra = new ProgressDialog(ActividadPrincipal.this);
            pDialogFra.setMessage(getPalabras("Cargando")+" "+getPalabras("Serie")+" "+getPalabras("Factura")+". "+getPalabras("Espere por favor")+"...");
            pDialogFra.setIndeterminate(false);
            pDialogFra.setCancelable(true);
            pDialogFra.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND fra.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND fra.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND fra.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND fra.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND fra.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            xWhere += " AND fra.T_SERIE='"+lparam.get(0).getDEFAULT_TIPO_SERIE_FACTURA_CLIENTE()+"'";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Fra> it = frafacturaList.iterator(); it.hasNext();){
                        Fra fra = it.next();
                        it.remove();
                    }

                    parseResultFraFactura(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogFra.dismiss();
            if (result == 1) {
                Log.e(TAG_SERIE, "OK SERIE");
                // Introducir datos clientes
                dialog_cliente();
            } else {
                Log.e(TAG_SERIE, "Failed to fetch data!");
            }
        }
    }
    private void parseResultFraFactura(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Fra cat = new Fra(post.optString("SERIE"),
                        post.optInt("FACTURA"));
                frafacturaList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Tarea Asíncrona para llamar al WS de consulta en segundo plano
    private class WSFactura extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean resul = true;
            final String NAMESPACE = Filtro.getNamespace();
            final String URL=Filtro.getNamespace()+"MyServiceCRLinux.asmx?WSDL";
            final String METHOD_NAME = "exportToPdf";
            final String SOAP_ACTION = Filtro.getNamespace()+METHOD_NAME;
            //           final String SOAP_ACTION = "http://83.56.29.190:8082/"+METHOD_NAME;
            Log.i("SOAP","NAMESPACE: "+NAMESPACE+" URL: "+URL+" METHOD_NAME: "+METHOD_NAME+" SOAP_ACTION: "+SOAP_ACTION);
            Log.i("SOAP","DRIVER: "+Filtro.getDriver()+" HOST: "+Filtro.getHost()+" DBNAME: "+Filtro.getDbname()+" USERNAME: "+Filtro.getUsername()+" PWDNAME: "+Filtro.getPwdname());

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("reportName", Filtro.getDirreportname()+"FACTURA.rpt");
            request.addProperty("pdfName", Filtro.getDirpdfname()+"FACTURA"+String.format("%06d",lfac.get(0).getFacFactura())+".pdf");
            request.addProperty("wfId", "");
            request.addProperty("cpId", "");
            request.addProperty("dbViewName", "{fac.ID}="+lfac.get(0).getFacId());
            request.addProperty("viewType", "view");
            request.addProperty("driver", Filtro.getDriver());
            request.addProperty("host", Filtro.getHost());
            request.addProperty("dbName", Filtro.getDbname());
            request.addProperty("userName", Filtro.getUsername());
            request.addProperty("pwdName", Filtro.getPwdname());
            request.addProperty("p01", "");
            request.addProperty("p02", "");
            request.addProperty("p03", "");

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try
            {
                transporte.call(SOAP_ACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                String res = String.valueOf(response.toString());
                Log.i("SOAP: ",res);
                //          String foo = "This,that,other";
                //        EQUIVALENTE EXPLODE PHP
                String[] split = res.split("~");
                ////////////////////////////////////////////
/*              EQUIVALENTE IMPLODE DE PHP
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    sb.append(split[i]);
                    if (i != split.length - 1) {
                        sb.append(" ");
                    }
                }
                String joined = sb.toString();
      /////////////////////////////////////////////
*/              int ok_existe=0;
                for (int i = -1; (i = split[0].indexOf("OK", i + 1)) != -1; ) {
                    ok_existe = i;
                }
                if (ok_existe==0){
                    resul = false;
                }
            }
            catch (Exception e)
            {
                Log.e("SOAP", "Error WEBSERVICE [" + e.getMessage()+"] ");
                resul = false;
            }

            return resul;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
            {
                new DownloadFile().execute(Filtro.getNamespace()+Filtro.getDirpdfname()+"FACTURA"+String.format("%06d",lfac.get(0).getFacFactura())+".pdf", "FACTURA"+String.format("%06d",lfac.get(0).getFacFactura())+".pdf");
            }
            else
            {
                Log.e(TAG, "NOT FILE PDF GENERATED!");

            }
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();
            Log.i("FOLDER",folder.getPath());
            File pdfFile = new File(folder, fileName);

            boolean resul = true;

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                resul = false;
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return resul;
        }
        protected void onPostExecute(Boolean result) {

            if (result)
            {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "FACTURA"+String.format("%06d",lfac.get(0).getFacFactura())+".pdf");  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Log.e(TAG, "NOT FILE PDF VIEW!");

            }
        }
    }

    public class GetPalabras extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogPalabras = new ProgressDialog(ActividadPrincipal.this);
            pDialogPalabras.setMessage("Cargando Palabras. Espere por favor...");
            pDialogPalabras.setIndeterminate(false);
            pDialogPalabras.setCancelable(true);
            pDialogPalabras.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getIdioma().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE palabras.IDIOMA='" + Filtro.getIdioma() + "'";
                } else {
                    xWhere += " AND palabras.IDIOMA='" + Filtro.getIdioma() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Palabras> it = lpalabras.iterator(); it.hasNext();){
                        Palabras palabras = it.next();
                        it.remove();
                    }

                    parseResultpalabras(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogPalabras.dismiss();
            if (result == 1) {
                Log.i(TAG_PALABRAS, Integer.toString(lpalabras.size()));
            } else {
                Log.e(TAG_PALABRAS, "Failed to fetch data!");
            }
        }
    }

    private void parseResultpalabras(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Palabras cat = new Palabras(post.optInt("ID"),
                        post.optString("IDIOMA"),
                        post.optString("CLAVESTRING").trim(),
                        post.optString("PALABRA").trim());
                Log.i(TAG_PALABRAS,Integer.toString(post.optInt("ID"))+","+post.optString("IDIOMA")+","+post.optString("CLAVESTRING")+","+post.optString("PALABRA"));
                lpalabras.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Background Async Task to Traspaso Pedido a Factura
     * */
    class TraspasoPedidoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(getPalabras("Facturar")+" "+getPalabras("Pedido")+" "+getPalabras("Linea")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
//            oksucces = 0;
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
/*                        String filtro = "GRUPO='" + Filtro.getGrupo() + "'";
                        filtro += " AND EMPRESA='" + Filtro.getEmpresa() + "'";
                        filtro += " AND LOCAL='" + Filtro.getLocal() + "'";
                        filtro += " AND SECCION='" + Filtro.getSeccion() + "'";
                        filtro += " AND CAJA='" + Filtro.getCaja() + "'";
*/
                        String filtro = "";
                        String xWhere = "";
                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        filtro+=xWhere;

                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        long maxDate = currentDate.getTime().getTime(); // Twice!

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("filtro",filtro);
                        values.put("estado",lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA());
                        values.put("serie",Filtro.getSerie());
                        values.put("factura",Long.toString(maxDate));
                        values.put("t_fra",lparam.get(0).getDEFAULT_TIPO_COBRO_OPEN_FACTURA());
                        values.put("empleado",Filtro.getEmpleado());
                        values.put("updated", dateNow);
                        values.put("creado", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        JSONObject json = jsonParserNew.makeHttpRequest(url_pedido_a_factura_id,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Filtro.setId(json.getInt(TAG_ID));
                            Filtro.setFactura(json.getInt(TAG_FACTURA));

                            String total = json.getString("total");
                            String obs = json.getString("obs");

                            String space01 = new String(new char[01]).replace('\0', ' ');
                            String myText= String.format("%1$,.2f", Float.parseFloat(total));
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            String newText="";
                            for (int ii = 0; ii < (10-myText.length()); ii++) {
                                newText+=space01;
                            }
                            newText +=myText;
                            total=Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo();

                            if (!getCruge("action_ftp_update")) {
                                Toast.makeText(ActividadPrincipal.this, getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                            }else {
                                CargaFragment cargafragment = null;
                                cargafragment = new CargaFragment(FragmentoPagesFactura.newInstance(Filtro.getId(), "OPEN", json.getString(TAG_MESA_FTP) ,Filtro.getSerie(), Integer.toString(Filtro.getFactura()),total,obs), getSupportFragmentManager());
                                cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                                if (cargafragment.getFragment() != null) {
                                    cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
//                                    TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "0");
                                }
                            }
//                            FragmentoOpenDocumentoPedido.getInstance().onResume();

                            //Calcular Items FTP
                            mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());

                            CountTable="ftp";
                            url_count = Filtro.getUrl()+"/CountFtpOpen.php";
                            mSerialExecutorActivity.execute(null);

                            CountTable="pdd";
                            url_count = Filtro.getUrl()+"/CountPddOpen.php";
                            mSerialExecutorActivity.execute(null);

                        } else {
                            Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Traspaso")+" "+getPalabras("Pedido")+" "+getPalabras("Factura"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();

        }

    }
    public class GetCategorias extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogTipoare = new ProgressDialog(ActividadPrincipal.this);
            pDialogTipoare.setMessage(getPalabras("Cargando")+" "+getPalabras("Cartas")+". "+getPalabras("Espere por favor")+"...");
            pDialogTipoare.setIndeterminate(false);
            pDialogTipoare.setCancelable(true);
            pDialogTipoare.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
/*            String xWhere = " WHERE tipoare.GRUPO='" + Filtro.getGrupo() + "'";
            xWhere += " AND tipoare.EMPRESA='" + Filtro.getEmpresa() + "'";
            xWhere += " AND tipoare.LOCAL='" + Filtro.getLocal() + "'";
            xWhere += " AND tipoare.SECCION='" + Filtro.getSeccion() + "'";
            xWhere += " AND tipoare.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
*/
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tipoare.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND tipoare.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tipoare.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND tipoare.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tipoare.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND tipoare.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tipoare.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND tipoare.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            xWhere += " AND tipoare.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Categoria> it = lcategoria.iterator(); it.hasNext();){
                        Categoria categoria = it.next();
                        it.remove();
                    }

                    parseResulttipoare(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogTipoare.dismiss();
            if (result == 1) {
                Log.i("Resultado Categoria", Integer.toString(result));
                populate_articulos_categoria();
            } else {
                Log.e(TAG_CATEGORIA, "Failed to fetch data!");
            }
        }
    }
    private void populate_articulos_categoria(){
        comidas = new ArrayList<ArrayList<Comida>>();
        for(int x=0;x<lcategoria.size();x++) {
            Filtro.setTipo_are(lcategoria.get(x).getCategoriaTipo_are());
///            IndiceSeccion = x;
            lcomida = new ArrayList<Comida>();

            //Calcular Items
            mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());

            CountTable="are";
            url_count = Filtro.getUrl()+"/RellenaListaArticulosSerial.php";
            mSerialExecutorActivity.execute(null);

            Log.i("Poblando: ",Integer.toString(x)+" "+lcategoria.get(x).getCategoriaTipo_are());
        }
    }
    public static String getQuery(ContentValues values) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : values.valueSet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));
        }
        Log.i("Result QUERY", result.toString());

        return result.toString();
    }

    private void parseResulttipoare(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                Categoria categoriaItem = new Categoria();
                categoriaItem.setCategoriaOrden(post.optInt("ORDEN"));
                categoriaItem.setCategoriaTipo_are(post.optString("TIPO_ARE"));
                categoriaItem.setCategoriaNombre_tipoare(getPalabras(post.optString("NOMBRE_TIPOARE").trim()));
                categoriaItem.setCategoriaUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                lcategoria.add(categoriaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GetUserrel extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogUserrel = new ProgressDialog(ActividadPrincipal.this);
            pDialogUserrel.setMessage(getPalabras("Cargando")+" "+getPalabras("Usuario")+" "+getPalabras("Relacion")+". "+getPalabras("Espere por favor")+"...");
            pDialogUserrel.setIndeterminate(false);
            pDialogUserrel.setCancelable(true);
            pDialogUserrel.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getUsuario().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE userrel.username='" + Filtro.getUsuario() + "'";
                } else {
                    xWhere += " AND userrel.username='" + Filtro.getUsuario() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Userrel> it = luserrel.iterator(); it.hasNext();){
                        Userrel userrel = it.next();
                        it.remove();
                    }

                    parseResultuserrel(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogUserrel.dismiss();
            if (result == 1) {
                Log.i("Resultado userrel", Integer.toString(result));
                new GetGrupos().execute(URL_GRUPOS);
  ///              new GetParams().execute(URL_PARAMS);
            } else {
                Log.e(TAG_USERREL, "Failed to fetch data!");
            }
        }
    }

    private void parseResultuserrel(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                Userrel userrelItem = new Userrel();
                userrelItem.setUserrelUsername(post.optString("username"));
                userrelItem.setUserrelGrupo(post.optString("GRUPO"));
                userrelItem.setUserrelEmpresa(post.optString("EMPRESA"));
                userrelItem.setUserrelLocal(post.optString("LOCAL"));
                userrelItem.setUserrelSeccion(post.optString("SECCION"));
                userrelItem.setUserrelCaja(post.optString("CAJA"));
                userrelItem.setUserrelCod_turno(post.optString("COD_TURNO"));
                userrelItem.setUserrelSerie(post.optString("SERIE"));
                Log.i("SERIE","USERREL "+post.optString("SERIE"));
                luserrel.add(userrelItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class GetPlatos extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogPlato = new ProgressDialog(ActividadPrincipal.this);
            pDialogPlato.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+getPalabras("Platos")+"...");
            pDialogPlato.setIndeterminate(false);
            pDialogPlato.setCancelable(true);
            pDialogPlato.show();
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE plato.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND plato.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE plato.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND plato.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE plato.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND plato.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE plato.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND plato.SECCION='" + Filtro.getSeccion() + "'";
                }
            }


            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Plato> it = lplato.iterator(); it.hasNext();){
                        Plato plato = it.next();
                        it.remove();
                    }

                    parseResultTipoPlato(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogPlato.dismiss();
            if (result == 1) {
                Log.e(TAG_PLATO, "OK PLATOS");
            } else {
                Log.e(TAG_PLATO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultTipoPlato(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Log.i("POST: ",post.optString("TIPOPLATO")+ post.optString("NOMBRE"));
                Plato cat = new Plato(post.optString("TIPOPLATO"),
                        getPalabras(post.optString("NOMBRE").trim()));
                lplato.add(cat);
                Log.i("POST Longitud: ",Integer.toString(lplato.size()));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


 /*   public class GetCruge extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogCruge = new ProgressDialog(ActividadPrincipal.this);
            pDialogCruge.setMessage(getPalabras("Cargando")+" Cruge. "+getPalabras("Espere por favor")+"...");
            pDialogCruge.setIndeterminate(false);
            pDialogCruge.setCancelable(true);
            pDialogCruge.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getUsuario().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE cruge_user.username='" + Filtro.getUsuario() + "'";
                } else {
                    xWhere += " AND cruge_user.username='" + Filtro.getUsuario() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Cruge> it = lcruge.iterator(); it.hasNext();){
                        Cruge cruge = it.next();
                        it.remove();
                    }

                    parseResultcruge(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            pDialogCruge.dismiss();
            if (result == 1) {
                Log.i(TAG_CRUGE, Integer.toString(lcruge.size()));
                comprobarToolbar();
            } else {
                Log.e(TAG_CRUGE, "Failed to fetch data!");
            }
        }
    }
    */

/*    private void parseResultcruge(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Cruge cat = new Cruge(post.optString("ACTION"));
//                Log.i(TAG_CRUGE,post.optString("ACTION"));
                lcruge.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/
    public static String getLocalIpAddress() {
        String ip ="127.0.0.1";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG_IP, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG_IP, ex.toString());
        }
        return ip;
    }
    /**
     * Async task to get all food categories
     */
    public class GetGrupos extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogGrup = new ProgressDialog(ActividadPrincipal.this);
            pDialogGrup.setMessage(getPalabras("Cargando")+" "+getPalabras("Grupo")+". "+getPalabras("Espere por favor")+"...");
            pDialogGrup.setIndeterminate(false);
            pDialogGrup.setCancelable(true);
            pDialogGrup.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            xWhere += " WHERE grup.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Grupo> it = grupList.iterator(); it.hasNext();){
                        Grupo grupo = it.next();
                        it.remove();
                    }

                    parseResultGrup(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogGrup.dismiss();
            if (result == 1) {
                Log.e(TAG_GRUPO, "OK GRUPO");
                populateSpinnerGrup();
            } else {
                Log.e(TAG_GRUPO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultGrup(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelGrupo().equals(post.optString("GRUPO"))) {
                        int found=0;
                        for (int y = 0; y < grupList.size(); y++) {
                            if (grupList.get(y).getGrupoGrupo().equals(post.optString("GRUPO"))) {
                               found=1;
                            }
                        }
                        if (found==0) {
                            Grupo cat = new Grupo(post.optString("GRUPO"),
                                    getPalabras(post.optString("NOMBRE").trim()));
                            grupList.add(cat);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all Empresas
     */
    public class GetEmpresas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpr = new ProgressDialog(ActividadPrincipal.this);
            pDialogEmpr.setMessage(getPalabras("Cargando")+" "+getPalabras("Empresa")+". "+getPalabras("Espere por favor")+"...");
            pDialogEmpr.setIndeterminate(false);
            pDialogEmpr.setCancelable(true);
            pDialogEmpr.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
//            String xWhere = " WHERE empr.GRUPO='" + Filtro.getGrupo() + "'";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empr.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND empr.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }

            xWhere += " AND empr.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Empresa> it = emprList.iterator(); it.hasNext();){
                        Empresa empresa = it.next();
                        it.remove();
                    }

                    parseResultEmpr(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogEmpr.dismiss();
            if (result == 1) {
                Log.e(TAG_EMPRESA, "OK EMPRESA");
                populateSpinnerEmpr();
                urlCabeceraEmpr = Filtro.getUrl() + "/CabeceraEMPR.php";
                new LeerCabeceraEmpr().execute(urlCabeceraEmpr);

            } else {
                Log.e(TAG_EMPRESA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultEmpr(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelGrupo().equals(post.optString("GRUPO")) &&
                            luserrel.get(z).getUserrelEmpresa().equals(post.optString("EMPRESA"))   ) {
                        int found=0;
                        for (int y = 0; y < emprList.size(); y++) {
                            if (emprList.get(y).getEmpresaEmpresa().equals(post.optString("EMPRESA"))) {
                                found=1;
                            }
                        }
                        if (found==0) {
                            Simbolo sim = new Simbolo(post.optString("SIMBOLO"));
                            simboloList.add(sim);

                            Empresa cat = new Empresa(post.optString("EMPRESA"),
                                    getPalabras(post.optString("NOMBRE").trim()));
                            emprList.add(cat);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class LeerCabeceraEmpr extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpr = new ProgressDialog(ActividadPrincipal.this);
            pDialogEmpr.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Cabecera")+" "+ActividadPrincipal.getPalabras("Empresa")+"..");
            pDialogEmpr.setIndeterminate(false);
            pDialogEmpr.setCancelable(true);
            pDialogEmpr.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empr.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND empr.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empr.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND empr.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    Log.i("Longitud Antes: ",Integer.toString(lcabeceraempr.size()));
                    for (Iterator<CabeceraEmpr> it = lcabeceraempr.iterator(); it.hasNext();){
                        CabeceraEmpr cabeceraEmpr = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(lcabeceraempr.size()));

                    parseResultCabeceraEmpr(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (pDialogEmpr.isShowing()) {
                pDialogEmpr.dismiss();

                if (result == 1) {
                    Log.i("Cabecera Empr", Integer.toString(lcabeceraempr.size()));
                } else {
                    Log.e("Cabecera Empr", "Failed to fetch data!");
                }
            }
        }
    }

    private void parseResultCabeceraEmpr(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                CabeceraEmpr cabeceraEmprItem = new CabeceraEmpr();
                cabeceraEmprItem.setCabeceraRazon(post.optString("RAZON").trim());
                cabeceraEmprItem.setCabeceraCif(post.optString("CIF").trim());
                cabeceraEmprItem.setCabeceraNumero(post.optString("NUMERO").trim());
                cabeceraEmprItem.setCabeceraBloque(post.optString("BLOQUE").trim());
                cabeceraEmprItem.setCabeceraEscalera(post.optString("ESCALERA").trim());
                cabeceraEmprItem.setCabeceraPiso(post.optString("PISO").trim());
                cabeceraEmprItem.setCabeceraPuerta(post.optString("PUERTA").trim());
                cabeceraEmprItem.setCabeceraAmpliacion(post.optString("AMPLIACION").trim());
                cabeceraEmprItem.setCabeceraNombre_calle(post.optString("NOMBRE_CALLE"));
                cabeceraEmprItem.setCabeceraCod_poblacion(post.optString("COD_POBLACION").trim());
                cabeceraEmprItem.setCabeceraNombre_poblacion(post.optString("NOMBRE_POBLACION").trim());
                cabeceraEmprItem.setCabeceraNombre_provincia(post.optString("NOMBRE_PROVINCIA").trim());
                cabeceraEmprItem.setCabeceraNombre_pais(post.optString("NOMBRE_PAIS").trim());
                lcabeceraempr.add(cabeceraEmprItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food locales
     */
    public class GetLocales extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogLocal = new ProgressDialog(ActividadPrincipal.this);
            pDialogLocal.setMessage(getPalabras("Cargando")+" "+getPalabras("Local")+". "+getPalabras("Espere por favor")+"...");
            pDialogLocal.setIndeterminate(false);
            pDialogLocal.setCancelable(true);
            pDialogLocal.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
/*            String xWhere = " WHERE local.GRUPO='" + Filtro.getGrupo() + "'";
            xWhere += " AND local.EMPRESA='" + Filtro.getEmpresa() + "'";
*/
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE local.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND local.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE local.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND local.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }

            xWhere += " AND local.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Local> it = localList.iterator(); it.hasNext();){
                        Local local = it.next();
                        it.remove();
                    }

                    parseResultLocal(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogLocal.dismiss();
            if (result == 1) {
                Log.e(TAG_LOCAL, "OK LOCAL");
                populateSpinnerLocal();
            } else {
                Log.e(TAG_LOCAL, "Failed to fetch data!");
            }
        }
    }
    private void parseResultLocal(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelGrupo().equals(post.optString("GRUPO")) &&
                        luserrel.get(z).getUserrelEmpresa().equals(post.optString("EMPRESA")) &&
                        luserrel.get(z).getUserrelLocal().equals(post.optString("LOCAL"))   ) {
                        int found=0;
                        for (int y = 0; y < localList.size(); y++) {
                            if (localList.get(y).getLocalLocal().equals(post.optString("LOCAL"))) {
                                found=1;
                            }
                        }
                        if (found==0) {
                            Local cat = new Local(post.optString("LOCAL"),
                                                  getPalabras(post.optString("NOMBRE").trim()),
                                                  Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim(),
                                                  post.optInt("RESIZE_LOGO_SCREEN_WIDTH") ,
                                                  post.optInt("RESIZE_LOGO_SCREEN_HEIGHT"),
                                                  post.optInt("RESIZE_LOGO_PRINT_WIDTH"),
                                                  post.optInt("RESIZE_LOGO_PRINT_HEIGHT"),
                                                  post.optString("URL_ADMIN").trim(),
                                                  post.optString("URL_REDSYS").trim(),
                                                  post.optString("POBLACION").trim(),
                                                  post.optString("EMAIL").trim()
                            );
                            Filtro.setUrl_admin(cat.getLocalUrl_admin());
                            Filtro.setUrl_redsys(cat.getLocalUrl_redsys());
                            localList.add(cat);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class GetParams extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogParam = new ProgressDialog(ActividadPrincipal.this);
            pDialogParam.setMessage(getPalabras("Cargando")+" "+getPalabras("Parametros")+". "+getPalabras("Espere por favor")+"...");
            pDialogParam.setIndeterminate(false);
            pDialogParam.setCancelable(true);
            pDialogParam.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE param.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND param.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE param.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND param.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE param.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND param.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE param.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND param.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Param> it = lparam.iterator(); it.hasNext();){
                        Param param = it.next();
                        it.remove();
                    }

                    parseResultParam(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogParam.dismiss();
            if (result == 1) {
                Log.e(TAG_PARAM, "OK PARAM");
///                new GetGrupos().execute(URL_GRUPOS);
            } else {
                Log.e(TAG_PARAM, "Failed to fetch data!");
            }
        }
    }
    private void parseResultParam(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos Param: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Param cat = new Param(
                        post.optString("DEFAULT_ESTADO_TODOS_GRUPO"),
                        post.optString("DEFAULT_ESTADO_TODOS_EMPRESA"),
                        post.optString("DEFAULT_ESTADO_TODOS_LOCAL"),
                        post.optString("DEFAULT_ESTADO_TODOS_SECCION"),
                        post.optString("DEFAULT_ESTADO_TODOS_CAJA"),
                        post.optString("DEFAULT_ESTADO_TODOS_TURNO"),
                        post.optString("DEFAULT_ESTADO_TODOS_MESA"),
                        post.optString("DEFAULT_ESTADO_TODOS_EMPLEADO"),
                        post.optString("DEFAULT_ESTADO_TODOS_TIPOPLATO"),
                        post.optString("DEFAULT_ESTADO_OPEN_PEDIDO"),
                        post.optString("DEFAULT_ESTADO_OPEN_FACTURA"),
                        post.optString("DEFAULT_ESTADO_CLOSE_PEDIDO"),
                        post.optString("DEFAULT_ESTADO_CLOSE_FACTURA"),
                        post.optString("DEFAULT_ESTADO_COBRO_FACTURA"),
                        post.optString("DEFAULT_TIPO_COBRO_OPEN_FACTURA"),
                        post.optString("DEFAULT_TABLA_OPEN_PEDIDO"),
                        post.optString("DEFAULT_TABLA_OPEN_FACTURA"),
                        post.optString("DEFAULT_TABLA_OPEN_FACTURA_CLIENTES"),
                        post.optString("DEFAULT_TIPO_ARTICULO_BUFFET"),
                        post.optString("DEFAULT_TIPO_MESA_BUFFET"),
                        post.optString("DEFAULT_TIPO_COBRO_BUFFET"),
                        post.optString("DEFAULT_SERIE_BUFFET"),
                        post.optString("DEFAULT_TIPO_SERIE_FACTURA_CLIENTE"),
                        post.optInt("DEFAULT_VALOR_ON_APERTURA"),
                        post.optInt("DEFAULT_VALOR_ON_ACTIVO"),
                        post.optInt("DEFAULT_VALOR_OFF_APERTURA"),
                        post.optInt("DEFAULT_VALOR_OFF_ACTIVO"));
    
                Log.i("Params: ",
                        cat.getDEFAULT_ESTADO_TODOS_GRUPO()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_EMPRESA()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_LOCAL()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_SECCION()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_CAJA()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_TURNO()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_MESA()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_EMPLEADO()+"\n" +
                                cat.getDEFAULT_ESTADO_TODOS_TIPOPLATO()+"\n" +
                                cat.getDEFAULT_ESTADO_OPEN_PEDIDO()+"\n" +
                                cat.getDEFAULT_ESTADO_OPEN_FACTURA()+"\n" +
                                cat.getDEFAULT_ESTADO_CLOSE_PEDIDO()+"\n" +
                                cat.getDEFAULT_ESTADO_CLOSE_FACTURA()+"\n" +
                                cat.getDEFAULT_ESTADO_COBRO_FACTURA()+"\n" +
                                cat.getDEFAULT_TIPO_COBRO_OPEN_FACTURA()+"\n" +
                                cat.getDEFAULT_TABLA_OPEN_PEDIDO()+"\n" +
                                cat.getDEFAULT_TABLA_OPEN_FACTURA()+"\n" +
                                cat.getDEFAULT_TABLA_OPEN_FACTURA_CLIENTES()+"\n" +
                                cat.getDEFAULT_TIPO_ARTICULO_BUFFET()+"\n" +
                                cat.getDEFAULT_TIPO_MESA_BUFFET()+"\n" +
                                cat.getDEFAULT_TIPO_COBRO_BUFFET()+"\n" +
                                cat.getDEFAULT_SERIE_BUFFET()+"\n" +
                                cat.getDEFAULT_TIPO_SERIE_FACTURA_CLIENTE()+"\n" +
                                cat.getDEFAULT_VALOR_ON_APERTURA()+"\n" +
                                cat.getDEFAULT_VALOR_ON_ACTIVO()+"\n" +
                                cat.getDEFAULT_VALOR_OFF_APERTURA()+"\n" +
                                cat.getDEFAULT_VALOR_OFF_ACTIVO());
    
                lparam.add(cat);
                Filtro.setT_fra(lparam.get(0).getDEFAULT_TIPO_COBRO_OPEN_FACTURA());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food secciones
     */
    public class GetSecciones extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogSec = new ProgressDialog(ActividadPrincipal.this);
            pDialogSec.setMessage(getPalabras("Cargando")+" "+getPalabras("Seccion")+". "+getPalabras("Espere por favor")+"...");
            pDialogSec.setIndeterminate(false);
            pDialogSec.setCancelable(true);
            pDialogSec.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
/*            String xWhere = " WHERE sec.GRUPO='" + Filtro.getGrupo() + "'";
            xWhere += " AND sec.EMPRESA='" + Filtro.getEmpresa() + "'";
            xWhere += " AND sec.LOCAL='" + Filtro.getLocal() + "'";
            xWhere += " AND sec.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
*/
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND sec.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND sec.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND sec.LOCAL='" + Filtro.getLocal() + "'";
                }
            }

            xWhere += " AND sec.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Seccion> it = secList.iterator(); it.hasNext();){
                        Seccion seccion = it.next();
                        it.remove();
                    }

                    parseResultSec(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogSec.dismiss();
            if (result == 1) {
                Log.e(TAG_SECCION, "OK SECCION");
                populateSpinnerSec();
            } else {
                Log.e(TAG_SECCION, "Failed to fetch data!");
            }
        }
    }
    private void parseResultSec(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelGrupo().equals(post.optString("GRUPO")) &&
                            luserrel.get(z).getUserrelEmpresa().equals(post.optString("EMPRESA")) &&
                            luserrel.get(z).getUserrelLocal().equals(post.optString("LOCAL")) &&
                            luserrel.get(z).getUserrelSeccion().equals(post.optString("SECCION"))   ) {
                        int found=0;
                        for (int y = 0; y < secList.size(); y++) {
                            if (secList.get(y).getSeccionSeccion().equals(post.optString("SECCION"))) {
                                found=1;
                            }
                        }
                        if (found==0) {
                            Seccion cat = new Seccion(post.optString("SECCION"),
                                    getPalabras(post.optString("NOMBRE").trim()),
                                    post.optInt("IVAINCLUIDO"));
                            secList.add(cat);
                        }
                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class GetSeccionFechas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogSecFechas = new ProgressDialog(ActividadPrincipal.this);
            pDialogSecFechas.setMessage(getPalabras("Cargando")+" "+getPalabras("Seccion")+" "+getPalabras("Fecha")+". "+getPalabras("Espere por favor")+"...");
            pDialogSecFechas.setIndeterminate(false);
            pDialogSecFechas.setCancelable(true);
            pDialogSecFechas.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND sec.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND sec.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND sec.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND sec.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            xWhere += " AND sec.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());


                    parseResultSecFechas(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogSecFechas.dismiss();
            if (result == 1) {
                Log.e(TAG_SECCION, "OK SECCION");
            } else {
                Log.e(TAG_SECCION, "Failed to fetch data!");
            }
        }
    }
    private void parseResultSecFechas(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Filtro.setFechaInicio(post.optString("FECHAINICIO"));
                Filtro.setFechaFinal(post.getString("FECHAFINAL"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Async task to get all food cajas
     */
    public class GetCajas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogCaja = new ProgressDialog(ActividadPrincipal.this);
            pDialogCaja.setMessage(getPalabras("Cargando")+" "+getPalabras("Caja")+". "+getPalabras("Espere por favor")+"...");
            pDialogCaja.setIndeterminate(false);
            pDialogCaja.setCancelable(true);
            pDialogCaja.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
/*            String xWhere = " WHERE caja.GRUPO='" + Filtro.getGrupo() + "'";
            xWhere += " AND caja.EMPRESA='" + Filtro.getEmpresa() + "'";
            xWhere += " AND caja.LOCAL='" + Filtro.getLocal() + "'";
            xWhere += " AND caja.SECCION='" + Filtro.getSeccion() + "'";
            xWhere += " AND caja.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
*/
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND caja.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND caja.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND caja.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND caja.SECCION='" + Filtro.getSeccion() + "'";
                }
            }


            xWhere += " AND caja.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Caja> it = cajaList.iterator(); it.hasNext();){
                        Caja caja = it.next();
                        it.remove();
                    }

                    parseResultCaja(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogCaja.dismiss();
            if (result == 1) {
                Log.e(TAG_CAJA, "OK CAJA");
                populateSpinnerCaja();
            } else {
                Log.e(TAG_CAJA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultCaja(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelGrupo().equals(post.optString("GRUPO")) &&
                        luserrel.get(z).getUserrelEmpresa().equals(post.optString("EMPRESA")) &&
                        luserrel.get(z).getUserrelLocal().equals(post.optString("LOCAL")) &&
                        luserrel.get(z).getUserrelSeccion().equals(post.optString("SECCION")) &&
                        luserrel.get(z).getUserrelCaja().equals(post.optString("CAJA"))   ) {
                        int found=0;
                        for (int y = 0; y < cajaList.size(); y++) {
                            if (cajaList.get(y).getCajaCaja().equals(post.optString("CAJA"))) {
                                found=1;
                            }
                        }
                        if (found==0) {
                            Caja cat = new Caja(post.optString("CAJA"),
                                    getPalabras(post.optString("NOMBRE").trim()));
                            cajaList.add(cat);
                        }
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     /**
     * Async task to get all food rangos
     */
    public class GetRangos extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogRango = new ProgressDialog(ActividadPrincipal.this);
            pDialogRango.setMessage(getPalabras("Cargando")+" "+getPalabras("Rangos")+". "+getPalabras("Espere por favor")+"...");
            pDialogRango.setIndeterminate(false);
            pDialogRango.setCancelable(true);
            pDialogRango.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE rangos.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND rangos.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE rangos.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND rangos.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE rangos.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND rangos.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE rangos.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND rangos.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE rangos.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND rangos.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            xWhere += " AND rangos.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Rango> it = rangosList.iterator(); it.hasNext();){
                        Rango rango = it.next();
                        it.remove();
                    }

                    parseResultRangos(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogRango.dismiss();
            if (result == 1) {
                Log.e(TAG_RANGO, "OK RANGO");
                populateSpinnerRangos();
            } else {
                Log.e(TAG_RANGO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultRangos(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Rango cat = new Rango(post.optString("RANGO"),
                        getPalabras(post.optString("NOMBRE").trim()));
                rangosList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food turnos
     */
    public class GetTurnos extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogTurno = new ProgressDialog(ActividadPrincipal.this);
            pDialogTurno.setMessage(getPalabras("Cargando")+" "+getPalabras("Turnos")+". "+getPalabras("Espere por favor")+"...");
            pDialogTurno.setIndeterminate(false);
            pDialogTurno.setCancelable(true);
            pDialogTurno.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND turno.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND turno.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND turno.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND turno.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND turno.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            xWhere += " AND turno.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Turno> it = turnoList.iterator(); it.hasNext();){
                        Turno turno = it.next();
                        it.remove();
                    }

                    parseResultTurno(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogTurno.dismiss();
            if (result == 1) {
                Log.e(TAG_TURNO, "OK TURNO");
                populateSpinnerTurno();
            } else {
                Log.e(TAG_TURNO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultTurno(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelGrupo().equals(post.optString("GRUPO")) &&
                        luserrel.get(z).getUserrelEmpresa().equals(post.optString("EMPRESA")) &&
                        luserrel.get(z).getUserrelLocal().equals(post.optString("LOCAL")) &&
                        luserrel.get(z).getUserrelSeccion().equals(post.optString("SECCION")) &&
                        luserrel.get(z).getUserrelCaja().equals(post.optString("CAJA")) &&
                        luserrel.get(z).getUserrelCod_turno().equals(post.optString("COD_TURNO"))   ) {
                        int found=0;
                        for (int y = 0; y < turnoList.size(); y++) {
                            if (turnoList.get(y).getTurnoCod_turno().equals(post.optString("COD_TURNO"))) {
                                found=1;
                            }
                        }
                        if (found==0) {
                            Turno cat = new Turno(post.optString("COD_TURNO"),
                                    getPalabras(post.optString("NOMBRE").trim()));
                            turnoList.add(cat);
                        }
                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food fra
     */
    public class GetFras extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogFra = new ProgressDialog(ActividadPrincipal.this);
            pDialogFra.setMessage(getPalabras("Cargando")+" "+getPalabras("Serie")+". "+getPalabras("Espere por favor")+"...");
            pDialogFra.setIndeterminate(false);
            pDialogFra.setCancelable(true);
            pDialogFra.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND fra.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND fra.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND fra.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND fra.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE fra.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND fra.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Fra> it = fraList.iterator(); it.hasNext();){
                        Fra fra = it.next();
                        it.remove();
                    }

                    parseResultFra(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogFra.dismiss();
            if (result == 1) {
                Log.e(TAG_SERIE, "OK FRA");
                populateSpinnerFra();
            } else {
                Log.e(TAG_SERIE, "Failed to fetch data!");
            }
        }
    }
    private void parseResultFra(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                for (int z = 0; z < luserrel.size(); z++) {
                    if (luserrel.get(z).getUserrelSerie().equals(post.optString("SERIE"))   ) {
                        int found=0;
                        for (int y = 0; y < fraList.size(); y++) {
                            if (fraList.get(y).getFraSerie().equals(post.optString("SERIE"))) {
                                found=1;
                            }
                        }
                        if (found==0) {
                            Fra cat = new Fra(post.optString("SERIE"),
                                    post.optInt("FACTURA"));
                            fraList.add(cat);
                            Log.i("SERIE LIST", fraList.get(0).getFraSerie()+" "+Integer.toString(fraList.get(0).getFraFactura()));
                        }
                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food mesas
     */
    public class GetZonas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogZonas = new ProgressDialog(ActividadPrincipal.this);
            pDialogZonas.setMessage(getPalabras("Cargando")+" "+getPalabras("Zonas")+". "+getPalabras("Espere por favor")+"...");
            pDialogZonas.setIndeterminate(false);
            pDialogZonas.setCancelable(true);
            pDialogZonas.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE zonas.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND zonas.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE zonas.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND zonas.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE zonas.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND zonas.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE zonas.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND zonas.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            xWhere += " AND zonas.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Zonas> it = lzonas.iterator(); it.hasNext();){
                        Zonas zona = it.next();
                        it.remove();
                    }

                    parseResultZonas(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogZonas.dismiss();
            if (result == 1) {
                Log.e(TAG_ZONAS, "OK ZONAS");
            } else {
                Log.e(TAG_ZONAS, "Failed to fetch data!");
            }
        }
    }
    private void parseResultZonas(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Zonas cat = new Zonas(post.optString("ZONA").trim(),
                        getPalabras(post.optString("NOMBRE").trim()),
                        Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                lzonas.add(cat);
//                Glide.with(this).load(cat.getZonasUrlimagen()).thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).preload();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food mesas
     */
    public class GetMesas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogMesa = new ProgressDialog(ActividadPrincipal.this);
            pDialogMesa.setMessage(getPalabras("Cargando")+" "+getPalabras("Mesas")+". "+getPalabras("Espere por favor")+"...");
            pDialogMesa.setIndeterminate(false);
            pDialogMesa.setCancelable(true);
            pDialogMesa.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getRango().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                } else {
                    xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                }
            }

            xWhere += " AND mesas.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON MESAS-->", response.toString());

                    for (Iterator<Mesa> it = mesasList.iterator(); it.hasNext();){
                        Mesa mesa = it.next();
                        it.remove();
                    }

                    parseResultMesas(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogMesa.dismiss();
            if (result == 1) {
                Log.e(TAG_MESA, "OK MESAS");
                populateSpinnerMesas();
            } else {
                Log.e(TAG_MESA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultMesas(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Mesa cat = new Mesa(
                        post.optString("MESA"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optString("T_MESA"),
                        post.optString("CALENDARIO").trim());
                mesasList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food mesa
     */
    public class GetMesaPedido extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogMesa = new ProgressDialog(ActividadPrincipal.this);
            pDialogMesa.setMessage(getPalabras("Cargando")+" "+getPalabras("Mesa")+" "+getPalabras("Pedido")+". "+getPalabras("Espere por favor")+"...");
            pDialogMesa.setIndeterminate(false);
            pDialogMesa.setCancelable(true);
            pDialogMesa.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getRango().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                } else {
                    xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                }
            }

            xWhere += " AND mesas.ACTIVO=1";
            xWhere += " AND mesas.APERTURA=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Mesa> it = mesaList.iterator(); it.hasNext();){
                        Mesa mesa = it.next();
                        it.remove();
                    }

                    parseResultMesaPedido(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogMesa.dismiss();
            if (result == 1) {
                Log.e(TAG_MESA, "OK MESA");
                populateSpinnerMesaPedido();
            } else {
                Log.e(TAG_MESA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultMesaPedido(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Mesa cat = new Mesa(post.optString("MESA"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optInt("COMENSALES"),
                        post.optString("CALENDARIO").trim());
                mesaList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food mesa
     */
    public class GetMesaBuffet extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogMesa = new ProgressDialog(ActividadPrincipal.this);
            pDialogMesa.setMessage(getPalabras("Cargando")+" "+getPalabras("Mesa")+" "+getPalabras("Buffet")+". "+getPalabras("Espere por favor")+"...");
            pDialogMesa.setIndeterminate(false);
            pDialogMesa.setCancelable(true);
            pDialogMesa.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            xWhere += " AND mesas.T_MESA='"+lparam.get(0).getDEFAULT_TIPO_MESA_BUFFET() +"'";
            xWhere += " AND mesas.APERTURA=1";
            xWhere += " AND mesas.ACTIVO=1";
            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista Buffet",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Mesa> it = mesaListBuffet.iterator(); it.hasNext();){
                        Mesa mesa = it.next();
                        it.remove();
                    }

                    parseResultMesaBuffet(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogMesa.dismiss();
            if (result == 1) {
                Log.e(TAG_MESA, "OK MESA");
                if(mesaListBuffet.size()>0) {
                    // Leer Articulo buffet y que este activo
                    larticulobuffet = new ArrayList<Articulo>();
                    URL_ARTICULOS = Filtro.getUrl() + "/RellenaListaARE.php";
                    new GetAreAreBuffet().execute(URL_ARTICULOS);

                }else{
                    Toast.makeText(getApplicationContext(), getPalabras("BUFFET")+" "+getPalabras("NO")+" "+getPalabras("OPEN"),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG_MESA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultMesaBuffet(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Mesa cat = new Mesa(post.optString("MESA"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optInt("COMENSALES"),
                        post.optString("MENSAJE"),
                        post.optString("CALENDARIO").trim());
                mesaListBuffet.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GetMesaFactura extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogMesa = new ProgressDialog(ActividadPrincipal.this);
            pDialogMesa.setMessage(getPalabras("Cargando")+" "+getPalabras("Mesa")+" "+getPalabras("Factura")+". "+getPalabras("Espere por favor")+"...");
            pDialogMesa.setIndeterminate(false);
            pDialogMesa.setCancelable(true);
            pDialogMesa.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getRango().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                } else {
                    xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                }
            }

            xWhere += " AND mesas.ACTIVO=1";
            xWhere += " AND mesas.APERTURA=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Mesa> it = mesaList.iterator(); it.hasNext();){
                        Mesa mesa = it.next();
                        it.remove();
                    }

                    parseResultMesaFactura(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogMesa.dismiss();
            if (result == 1) {
                Log.e(TAG_MESA, "OK MESA");
                populateSpinnerMesaFactura();
            } else {
                Log.e(TAG_MESA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultMesaFactura(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Mesa cat = new Mesa(post.optString("MESA"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optInt("COMENSALES"),
                        post.optString("CALENDARIO").trim());
                mesaList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food mesa
     */
    public class GetEmpleadoFactura extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpleado = new ProgressDialog(ActividadPrincipal.this);
            pDialogEmpleado.setMessage(getPalabras("Cargando")+" "+getPalabras("Empleado")+" "+getPalabras("Factura")+". "+getPalabras("Espere por favor")+"...");
            pDialogEmpleado.setIndeterminate(false);
            pDialogEmpleado.setCancelable(true);
            pDialogEmpleado.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND empleados.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND empleados.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND empleados.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            xWhere += " AND empleados.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Empleado> it = empleadoList.iterator(); it.hasNext();){
                        Empleado empleado = it.next();
                        it.remove();
                    }

                    parseResultEmpleadoFactura(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogEmpleado.dismiss();
            if (result == 1) {
                Log.e(TAG_EMPLEADO, "OK EMPLEADO");
                populateSpinnerEmpleadoFactura();
            } else {
                Log.e(TAG_EMPLEADO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultEmpleadoFactura(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Empleado cat = new Empleado(post.optString("EMPLEADO"),
                        post.optString("NOMBRE"),
                        post.optString("username"));
                empleadoList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food mesa
     */
    public class GetEmpleadoPedido extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpleado = new ProgressDialog(ActividadPrincipal.this);
            pDialogEmpleado.setMessage(getPalabras("Cargando")+" "+getPalabras("Empleado")+" "+getPalabras("Pedido")+". "+getPalabras("Espere por favor")+"...");
            pDialogEmpleado.setIndeterminate(false);
            pDialogEmpleado.setCancelable(true);
            pDialogEmpleado.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND empleados.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND empleados.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND empleados.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            xWhere += " AND empleados.ACTIVO=1";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Empleado> it = empleadoList.iterator(); it.hasNext();){
                        Empleado empleado = it.next();
                        it.remove();
                    }

                    parseResultEmpleadoPedido(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogEmpleado.dismiss();
            if (result == 1) {
                Log.e(TAG_EMPLEADO, "OK EMPLEADO");
                populateSpinnerEmpleadoPedido();
            } else {
                Log.e(TAG_EMPLEADO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultEmpleadoPedido(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Empleado cat = new Empleado(post.optString("EMPLEADO"),
                        post.optString("NOMBRE"),
                        post.optString("username"));
                empleadoList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food cajas
     */
    public class GetEmpleados extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpleado = new ProgressDialog(ActividadPrincipal.this);
            pDialogEmpleado.setMessage(getPalabras("Cargando")+" "+getPalabras("Empleados")+". "+getPalabras("Espere por favor")+"...");
            pDialogEmpleado.setIndeterminate(false);
            pDialogEmpleado.setCancelable(true);
            pDialogEmpleado.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND empleados.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND empleados.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND empleados.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE empleados.username='" + Filtro.getUsuario() + "'";
            }else{
                xWhere += " AND empleados.username='" + Filtro.getUsuario() + "'";
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE empleados.ACTIVO=1";
            }else{
                xWhere += " AND empleados.ACTIVO=1";
            }
            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista Empleados",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON EMPLEADOS-->", response.toString());

                    for (Iterator<Empleado> it = empleadosList.iterator(); it.hasNext();){
                        Empleado empleado = it.next();
                        it.remove();
                    }

                    parseResultEmpleados(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogEmpleado.dismiss();
            if (result == 1) {
                Log.e(TAG_EMPLEADO, "OK EMPLEADO");
                TaskHelper.execute(new GetEmpleadosRolInferior(), URL_EMPLEADOS);
            } else {
                Log.e(TAG_EMPLEADO, "Failed to fetch data!");
            }
        }
    }
    public class GetEmpleadosRolInferior extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpleado = new ProgressDialog(ActividadPrincipal.this);
            pDialogEmpleado.setMessage(getPalabras("Cargando")+" "+getPalabras("Empleados")+". "+getPalabras("Espere por favor")+"...");
            pDialogEmpleado.setIndeterminate(false);
            pDialogEmpleado.setCancelable(true);
            pDialogEmpleado.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND empleados.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND empleados.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND empleados.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empleados.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND empleados.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE (empleados.username<>'" + Filtro.getUsuario() + "'";
                xWhere += " AND roles.NIVEL<" + Integer.toString(Filtro.getNivelroles()) + ")";
            }else{
                xWhere += " AND (empleados.username<>'" + Filtro.getUsuario() + "'";
                xWhere += " AND roles.NIVEL<" + Integer.toString(Filtro.getNivelroles()) + ")";
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE empleados.ACTIVO=1";
            }else{
                xWhere += " AND empleados.ACTIVO=1";
            }
            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista Empleados",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON EMPLEADOS-->", response.toString());

/*                    for (Iterator<Empleado> it = empleadosList.iterator(); it.hasNext();){
                        Empleado empleado = it.next();
                        it.remove();
                    }
*/
                    parseResultEmpleados(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogEmpleado.dismiss();
            if (result == 1) {
                Log.e(TAG_EMPLEADO, "OK EMPLEADO");
                populateSpinnerEmpleados();
            } else {
                Log.e(TAG_EMPLEADO, "Failed to fetch data!");
            }
        }
    }
    private void parseResultEmpleados(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Empleado cat = new Empleado(post.optString("EMPLEADO"),
                                            getPalabras(post.optString("NOMBRE").trim()),
                                            post.optString("username"));
                empleadosList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food monedas
     */
    public class GetMonedas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogMoneda = new ProgressDialog(ActividadPrincipal.this);
            pDialogMoneda.setMessage(getPalabras("Cargando")+" "+getPalabras("Moneda")+". "+getPalabras("Espere por favor")+"...");
            pDialogMoneda.setIndeterminate(false);
            pDialogMoneda.setCancelable(true);
            pDialogMoneda.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getMoneda().equals(""))) {
                xWhere += " WHERE moneda.MONEDA='" + Filtro.getMoneda() + "'";
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Moneda> it = monedaList.iterator(); it.hasNext();){
                        Moneda moneda = it.next();
                        it.remove();
                    }

                    parseResultMoneda(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogMoneda.dismiss();
            if (result == 1) {
                Log.e(TAG_MONEDA, "OK MONEDA");
                populateSpinnerMoneda();
            } else {
                Log.e(TAG_MONEDA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultMoneda(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Moneda cat = new Moneda(post.optString("MONEDA"),
                        getPalabras(post.optString("NOMBRE").trim()));
                monedaList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food monedas
     */
    public class GetTerminales extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogTerminal = new ProgressDialog(ActividadPrincipal.this);
            pDialogTerminal.setMessage(getPalabras("Cargando")+" "+getPalabras("Terminal")+". "+getPalabras("Espere por favor")+"...");
            pDialogTerminal.setIndeterminate(false);
            pDialogTerminal.setCancelable(true);
            pDialogTerminal.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getUsuario().equals(""))) {
                xWhere += " WHERE usuarioterminal.username='" + Filtro.getUsuario() + "'";
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    for (Iterator<Terminal> it = terminalList.iterator(); it.hasNext();){
                        Terminal terminal = it.next();
                        it.remove();
                    }

                    parseResultTerminal(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogTerminal.dismiss();
            if (result == 1) {
                Log.e(TAG_TERMINAL, "OK TERMINAL");
                populateSpinnerTerminal();
            } else {
                Log.e(TAG_TERMINAL, "Failed to fetch data!");
            }
        }
    }
    private void parseResultTerminal(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Terminal cat = new Terminal(post.optString("PRINTER"),
                                            post.optString("TERMINAL"),
                                            post.optString("DEVICETYPE"),
                                            post.optString("PRINTIP"),
                                            post.optInt("PRINTLANGUAGE"),
                                            post.optInt("PRINTINTERVAL"));
                terminalList.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Background Async Task to  Save Caja Details
     * */
    class OpenCloseCaja extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogCaja = new ProgressDialog(ActividadPrincipal.this);
            pDialogCaja.setMessage(getPalabras("Cargando")+" "+getPalabras("Cajas")+" "+getPalabras("Cerrada")+". "+getPalabras("Espere por favor")+"...");
            pDialogCaja.setIndeterminate(false);
            pDialogCaja.setCancelable(true);
            pDialogCaja.show();
        }

        /**
         * Saving Seccion
         * */
        @Override
        protected Integer doInBackground(String... args) {
            int result=0;
            // getting updated data from EditTexts
            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());
            //           System.out.println("Now the date is :=>  " + dateNow);

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("pid", args[0]);
            values.put("fechaapertura",Filtro.getFechaapertura());
            values.put("apertura",args[1]);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // sending modified data through http request
            // Notice that update Seccion url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_caja,
                    "POST", values);

            // check json success tag
            try {
                result = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer result) {
            // dismiss the dialog once Seccion uupdated
            pDialogCaja.dismiss();
            if (result==1) {
                CargaFragment cargafragment = null;
                cargafragment = new CargaFragment(FragmentoCaja.newInstance(), getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                if (cargafragment.getFragment() != null) {
                    cargafragment.setTransaction(R.id.contenedor_principal);
                }
       //         FragmentoOpenCaja.getInstance().onResume();
       //         FragmentoCloseCaja.getInstance().onResume();
            }
        }
    }
    /**
     * Background Async Task to  Save Turno Details
     * */
    class OpenCloseTurno extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogTurno = new ProgressDialog(ActividadPrincipal.this);
            pDialogTurno.setMessage(getPalabras("Cargando")+" "+getPalabras("Turnos")+" "+getPalabras("Cerrado")+". "+getPalabras("Espere por favor")+"...");
            pDialogTurno.setIndeterminate(false);
            pDialogTurno.setCancelable(true);
            pDialogTurno.show();

        }

        /**
         * Saving Seccion
         * */
        @Override
        protected Integer doInBackground(String... args) {
            int result=0;
            // getting updated data from EditTexts
            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());
            //           System.out.println("Now the date is :=>  " + dateNow);

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("pid", args[0]);
            values.put("fechaapertura",Filtro.getFechaapertura());
            values.put("apertura",args[1]);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // sending modified data through http request
            // Notice that update Seccion url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_turno,
                    "POST", values);

            // check json success tag
            try {
                result = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer result) {
            // dismiss the dialog once Seccion uupdated
            pDialogTurno.dismiss();
            if (result==1) {
                CargaFragment cargafragment = null;
                cargafragment = new CargaFragment(FragmentoTurno.newInstance(), getSupportFragmentManager());
                cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                if (cargafragment.getFragment() != null) {
                    cargafragment.setTransaction(R.id.contenedor_principal);
                }
//                FragmentoOpenTurno.getInstance().onResume();
//                FragmentoCloseTurno.getInstance().onResume();
            }
        }
    }
    public class GetTiposCobro extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogtft = new ProgressDialog(ActividadPrincipal.this);
            pDialogtft.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Tipo Cobro")+"...");
            pDialogtft.setIndeterminate(false);
            pDialogtft.setCancelable(true);
            pDialogtft.show();
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tft.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND tft.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tft.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND tft.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tft.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND tft.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tft.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND tft.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE tft.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND tft.CAJA='" + Filtro.getCaja() + "'";
                }
            }


            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<TipoCobro> it = tftbuffetList.iterator(); it.hasNext();){
                        TipoCobro tipoCobro = it.next();
                        it.remove();
                    }
                    for (Iterator<TipoCobro> itbis = tftList.iterator(); itbis.hasNext();){
                        TipoCobro tipoCobro = itbis.next();
                        itbis.remove();
                    }

                    parseResultTiposCobro(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogtft.dismiss();
            if (result == 1) {
                Log.e(TAG_TFT, "OK TIPOS COBRO");
            } else {
                Log.e(TAG_TFT, "Failed to fetch data!");
            }
        }
    }
    private void parseResultTiposCobro(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Log.i("POST: ",post.optString("T_FRA")+ post.optString("NOMBRE"));
                TipoCobro cat = new TipoCobro(post.optString("T_FRA"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optInt("COPIA_PRINT"),
                        0);
                tftbuffetList.add(cat);
                tftList.add(cat);
                Log.i("POST Longitud: ",Integer.toString(tftbuffetList.size()));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Background Async Task to  Save Factura Details
     * */
    class SaveCobroFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Guardar")+" "+ActividadPrincipal.getPalabras("Cobro")+" ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving Factura
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // getting updated data from EditTexts
            Integer result = 0;
            // Building Parameters
            ContentValues values = new ContentValues();

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            values.put(TAG_PID, args[0]);
            values.put(TAG_TFRA,args[1]);
            values.put(TAG_ESTADO,args[2]);
            values.put(TAG_IMPCOBRO,args[3]);
            values.put(TAG_IMPDIFERENCIA,args[4]);
            values.put(TAG_DTO,args[5]);
            values.put(TAG_OBS,args[6]);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // sending modified data through http request
            // Notice that update Factura url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_Factura,
                    "POST", values);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    result = 1;
                } else {
                    result = 0;
                    // failed to update Factura
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer result) {
            // dismiss the dialog once Factura updated
            pDialog.dismiss();
            if (result==1){
                if (ArticuloCobro){
                    control_articulos_cobro();
                } else {
                    CargaFragment cargafragment = null;
                    cargafragment = new CargaFragment(EditCobroFacturaFragment.newInstance(pid,serie_ftp,String.valueOf(factura_ftp),"lista"),getSupportFragmentManager());
                    cargafragment.getFragmentManager().addOnBackStackChangedListener(ActividadPrincipal.this);
                    if (cargafragment.getFragment() != null){
                        cargafragment.setTransactionToBackStack(R.id.contenedor_principal);
                        Toast.makeText(ActividadPrincipal.this, getPalabras("Cobro")+" " + getPalabras("Factura") + " " + factura_ftp, Toast.LENGTH_SHORT).show();
                    }
                }

            }else{

                Toast.makeText(ActividadPrincipal.this, "ERROR NO "+getPalabras("Guardar") + " " + getPalabras("Cobro"), Toast.LENGTH_SHORT).show();

            }
        }
    }
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position,
                               long id) {
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.CmbToolbarGrup)
        {
            if (grupList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), grupList.get(position).getGrupoGrupo() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setGrupo(grupList.get(position).getGrupoGrupo());
                new GetEmpresas().execute(URL_EMPRESAS);

            }
        }
        if(spinner.getId() == R.id.CmbToolbarEmpr)
        {
            if (emprList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), emprList.get(position).getEmpresaEmpresa() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setEmpresa(emprList.get(position).getEmpresaEmpresa());
                Filtro.setSimbolo(simboloList.get(position).getMonedaSimbolo());
                TaskHelper.execute(new GetLocales(), URL_LOCALES);
                TaskHelper.execute(new GetMonedas(), URL_MONEDAS);
//                new GetLocales().execute(URL_LOCALES);

            }
        }

        if(spinner.getId() == R.id.CmbToolbarLocal)
        {
            if (localList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), localList.get(position).getLocalLocal() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setLocal(localList.get(position).getLocalLocal());

                /// Leemos i cargamos la imagen para LOGO SCREEN
                Drawable d = LoadImageFromWebOperations(localList.get(position).getLocalUrlimagen());
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                Bitmap new_iconscreen = null;
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        new_iconscreen = getResizedBitmap(bitmap,localList.get(position).getLocalResize_logo_screen_width(),localList.get(position).getLocalResize_logo_screen_height());
                        break;
                    case 1:
                        new_iconscreen = getResizedBitmap(bitmap,localList.get(position).getLocalResize_logo_screen_width(),localList.get(position).getLocalResize_logo_screen_height());
                        break;
                    case 2:
                        new_iconscreen = resizeBitmapImageFn(bitmap, 50); //resizing the bitmap 500: ricoparico 75 Marivent
                        break;
                }
                Drawable d1screen = new BitmapDrawable(getResources(),new_iconscreen); //Converting bitmap into drawable
                getSupportActionBar().setIcon(d1screen); //also displays wide logo_ricoparico
                imagelogo = new_iconscreen; // GUARDAMOS EL ICONO MODIFICADO .
/*
                /// Leemos i cargamos la imagen para LOGO SCREEN
                Drawables d = LoadImageFromWebOperations(localList.get(position).getLocalUrlimagen());
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
//*                Bitmap new_iconscreen = resizeBitmapImageFn(bitmap, localList.get(position).getLocalResize_logo_screen()); //resizing the bitmap 500: ricoparico 75 Marivent
                Bitmap new_iconscreen = getResizedBitmap(bitmap,localList.get(position).getLocalResize_logo_screen_width(),localList.get(position).getLocalResize_logo_screen_height());
                Drawables d1screen = new BitmapDrawable(getResources(),new_iconscreen); //Converting bitmap into drawable
                getSupportActionBar().setIcon(d1screen); //also displays wide logo_ricoparico
                imagelogo = new_iconscreen; // GUARDAMOS EL ICONO MODIFICADO .
*/


                /// Leemos i cargamos la imagen para LOGO PRINT
                Drawable dprint = LoadImageFromWebOperations(localList.get(position).getLocalUrlimagen());
                Bitmap bitmapprint = ((BitmapDrawable)dprint).getBitmap();
                Bitmap bitmaplogo = getResizedBitmap(bitmapprint,localList.get(position).getLocalResize_logo_print_width(),localList.get(position).getLocalResize_logo_print_height());

                Bitmap scaled = Bitmap.createScaledBitmap(bitmaplogo,localList.get(position).getLocalResize_logo_print_width(),localList.get(position).getLocalResize_logo_print_height(),false);
                if (bitmaplogo != scaled) {
                    bitmaplogo.recycle();
                }

                imagelogoprint = codec(scaled, Bitmap.CompressFormat.JPEG, 0);// PASAMOS EL ICONO LEIDO AL BITMAP PARA IMPRIMIR.


                /// Modificar logo_ricoparico menudrawer
                int imageViewID = getResources().getIdentifier("imageLogoDrawer", "id", getPackageName());
                ImageView imgLogo = (ImageView) findViewById(imageViewID);
                imgLogo.setImageBitmap(imagelogo);

//                TaskHelper.execute(new DownloadImageTask(imagelogo), localList.get(position).getLocalUrlimagen());
                Log.i("image",localList.get(position).getLocalUrlimagen());

                new GetSecciones().execute(URL_SECCIONES);
            }
        }
        if(spinner.getId() == R.id.CmbToolbarSec)
        {
            if (secList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), secList.get(position).getSeccionSeccion() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setSeccion(secList.get(position).getSeccionSeccion());
                Filtro.setIvaIncluido(secList.get(position).getSeccionIvaIncluido());

                new GetParams().execute(URL_PARAMS);
                /// almacenar platos en arraylist
                TaskHelper.execute(new GetPlatos(), url_platos);
                /// almacenar categorias en arraylist
                TaskHelper.execute(new GetCategorias(), url_tipoare);
                /// almacenar tipos cobros en arrayList
                TaskHelper.execute(new GetTiposCobro(),URL_TFTBUFFET);
                /// almacenar mesas buffet en arrayList
                TaskHelper.execute(new GetMesaBuffet(),URL_MESA_BUFFET);
                /// almacenar zonas en arraylist
                TaskHelper.execute(new GetZonas(), URL_ZONAS);
                /// almacenar artticulonotificacionterminal en arraylist
                TaskHelper.execute(new GetAreNotifTerminal(), URL_ARTICULONOTIFICACIONTERMINAL);

                ///////////////////////////////////////////////////////////////
                TaskHelper.execute(new GetSeccionFechas(), URL_SECCIONES_FECHAS);
                TaskHelper.execute(new GetCajas(), URL_CAJAS);
                TaskHelper.execute(new GetEmpleados(), URL_EMPLEADOS);
            }
        }
        if(spinner.getId() == R.id.CmbToolbarCaja)
        {
            if (cajaList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), cajaList.get(position).getCajaCaja() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setCaja(cajaList.get(position).getCajaCaja());
                TaskHelper.execute(new GetTurnos(), URL_TURNOS);
                TaskHelper.execute(new GetFras(), URL_FRAS);
                TaskHelper.execute(new GetRangos(), URL_RANGOS);
                TaskHelper.execute(new GetMesas(), URL_MESAS);
            }
        }
        if(spinner.getId() == R.id.CmbToolbarTurno)
        {
            if (turnoList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), turnoList.get(position).getTurnoCod_turno() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setTurno(turnoList.get(position).getTurnoCod_turno());
            }
        }
        if(spinner.getId() == R.id.CmbToolbarFra)
        {
            if (fraList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), fraList.get(position).getFraSerie() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setSerie(fraList.get(position).getFraSerie());
            }
        }
        if(spinner.getId() == R.id.CmbToolbarRangos)
        {
            if (rangosList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), rangosList.get(position).getRangoRango() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setRango(rangosList.get(position).getRangoRango());
                TaskHelper.execute(new GetMesas(), URL_MESAS);
            }

        }


        if(spinner.getId() == R.id.CmbToolbarMesas)
        {
            if (mesasList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), mesasList.get(position).getMesaMesa() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setMesa(mesasList.get(position).getMesaMesa());
            }
        }

        if(spinner.getId() == R.id.CmbToolbarEmpleados)
        {
            if (empleadosList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), empleadosList.get(position).getEmpleadoEmpleado() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                if (position==0){
                    usuario_identificado = true;
                    position_usuario = 0;
                    Filtro.setEmpleado(empleadosList.get(position).getEmpleadoEmpleado());
                }else{
                    usuario_identificado = false;
                    position_usuario = position;
                }
            }
        }
        if(spinner.getId() == R.id.CmbToolbarMoneda)
        {
            if (monedaList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), monedaList.get(position).getMonedaMoneda() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setMoneda(monedaList.get(position).getMonedaMoneda());
                TaskHelper.execute(new GetTerminales(), URL_TERMINALES);
            }
        }
        
        if(spinner.getId() == R.id.CmbToolbarTerminal)
        {
            if (terminalList.size()>0) {
/*                Toast.makeText(
                        getApplicationContext(), terminalList.get(position).getTerminalTerminal() + " " +
                                parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
*/
                Filtro.setPrintPrinterName(terminalList.get(position).getTerminalPrinter());
                Filtro.setPrintIp(terminalList.get(position).getTerminalPrintIp());
                Filtro.setPrintLanguage(terminalList.get(position).getTerminalPrintLanguage());
                Filtro.setPrintInterval(terminalList.get(position).getTerminalPrintInterval());
                switch (terminalList.get(position).getTerminalDeviceType()) {
                    case "TCP":
                        Filtro.setPrintDeviceType(Print.DEVTYPE_TCP);
                        break;
                    case "BLUETOOTH":
                        Filtro.setPrintDeviceType(Print.DEVTYPE_BLUETOOTH);
                        break;
                    case "USB":
                        Filtro.setPrintDeviceType(Print.DEVTYPE_USB);
                        break;
                }
                Log.i("PRINT ","DeviceType: "+Filtro.getPrintdeviceType()+
                        " PrinterName: "+Filtro.getPrintPrinterName()+
                        " PrinterIP: "+Filtro.getPrintIp()+
                        " PrintLanguage: "+Integer.toString(Filtro.getPrintLanguage())+
                        " PrintInterval: "+Integer.toString(Filtro.getPrintInterval())
                );

            }
        }

        /// CONTROLAR QUE FRAGMENT ESTA ACTIVO
        if(!Filtro.getInicio()) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.contenedor_principal);
            if (currentFragment != null) {
                if (currentFragment instanceof FragmentoFactura) {
                    if ((FragmentoOpenDocumentoFactura.getInstance() != null)) {
                        FragmentoOpenDocumentoFactura.getInstance().onResume();
                        Log.i("instancia OpenFactura", "ok");
                    }
                    if ((FragmentoCloseDocumentoFactura.getInstance() != null)) {
                        FragmentoCloseDocumentoFactura.getInstance().onResume();
                        Log.i("instancia CloseFactura", "ok");
                    }
                } else {
                    Log.i("instancia", currentFragment.getClass().getName());
                }
                if (currentFragment instanceof FragmentoPedido) {
                    if ((FragmentoOpenDocumentoPedido.getInstance() != null)) {
                        FragmentoOpenDocumentoPedido.getInstance().onResume();
                        Log.i("instancia OpenPedido", "ok");
                    }
                    if ((FragmentoCloseDocumentoPedido.getInstance() != null)) {
                        FragmentoCloseDocumentoPedido.getInstance().onResume();
                        Log.i("instancia ClosePedido", "ok");
                    }
                } else {
                    Log.i("instancia", currentFragment.getClass().getName());
                }
                if (currentFragment instanceof FragmentoDcj) {
                    if ((FragmentoOpenDcj.getInstance() != null)) {
                        FragmentoOpenDcj.getInstance().onResume();
                        Log.i("instancia OpenDcj", "ok");
                    }
                    if ((FragmentoCloseDcj.getInstance() != null)) {
                        FragmentoCloseDcj.getInstance().onResume();
                        Log.i("instancia CloseDcj", "ok");
                    }
                } else {
                    Log.i("instancia", currentFragment.getClass().getName());
                }
                if (currentFragment instanceof FragmentoTurno) {
                    if ((FragmentoOpenTurno.getInstance() != null)) {
                        FragmentoOpenTurno.getInstance().onResume();
                        Log.i("instancia OpenTurno", "ok");
                    }
                    if ((FragmentoCloseTurno.getInstance() != null)) {
                        FragmentoCloseTurno.getInstance().onResume();
                        Log.i("instancia CloseTurno", "ok");
                    }
                } else {
                    Log.i("instancia", currentFragment.getClass().getName());
                }
                if (currentFragment instanceof FragmentoCaja) {
                    if ((FragmentoOpenCaja.getInstance() != null)) {
                        FragmentoOpenCaja.getInstance().onResume();
                        Log.i("instancia OpenCaja", "ok");
                    }
                    if ((FragmentoCloseCaja.getInstance() != null)) {
                        FragmentoCloseCaja.getInstance().onResume();
                        Log.i("instancia CloseCaja", "ok");
                    }
                } else {
                    Log.i("instancia", currentFragment.getClass().getName());
                }
                if (currentFragment instanceof FragmentoSeccion) {
                    if ((FragmentoOpenSeccion.getInstance() != null)) {
                        FragmentoOpenSeccion.getInstance().onResume();
                        Log.i("instancia OpenSeccion", "ok");
                    }
                    if ((FragmentoCloseSeccion.getInstance() != null)) {
                        FragmentoCloseSeccion.getInstance().onResume();
                        Log.i("instancia CloseSeccion", "ok");
                    }
                } else {
                    Log.i("instancia", currentFragment.getClass().getName());
                }

            }
            //Calcular Items
            mSerialExecutorActivity = new MySerialExecutor(getApplicationContext());

            CountTable="sec";
            url_count = Filtro.getUrl()+"/CountSecOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable="caja";
            url_count = Filtro.getUrl()+"/CountCajaOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable="turno";
            url_count = Filtro.getUrl()+"/CountTurnoOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable="dcj";
            url_count = Filtro.getUrl()+"/CountDcjOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable="pdd";
            url_count = Filtro.getUrl()+"/CountPddOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable="ftp";
            url_count = Filtro.getUrl()+"/CountFtpOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable = "mesas";
            url_count = Filtro.getUrl() + "/CountMesasOpen.php";
            mSerialExecutorActivity.execute(null);


/*        url_count = Filtro.getUrl()+"/CountSecOpen.php";
        new CountOpen().execute(url_count,"sec");
        url_count = Filtro.getUrl()+"/CountCajaOpen.php";
        new CountOpen().execute(url_count,"caja");
        url_count = Filtro.getUrl()+"/CountTurnoOpen.php";
        new CountOpen().execute(url_count,"turno");
        url_count = Filtro.getUrl()+"/CountPddOpen.php";
        new CountOpen().execute(url_count,"pdd");
        url_count = Filtro.getUrl()+"/CountFtpOpen.php";
        new CountOpen().execute(url_count,"ftp");
*/

        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (Filtro.getTag_fragment().equals("FragmentoOpenDocumentoPedido")){
            final List<DocumentoPedido> filteredModelListDocumentoPedido = filterdocumentopedido(FragmentoOpenDocumentoPedido.getInstance().ldocumentopedido, newText);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    FragmentoOpenDocumentoPedido.getInstance().adaptadordocumentopedidoasus.setFilter(filteredModelListDocumentoPedido);
                    break;
                case 1:
                    FragmentoOpenDocumentoPedido.getInstance().adaptadordocumentopedidosony.setFilter(filteredModelListDocumentoPedido);
                    break;
                case 2:
                    FragmentoOpenDocumentoPedido.getInstance().adaptadordocumentopedidosony.setFilter(filteredModelListDocumentoPedido);
                    break;
            }
        }
        if (Filtro.getTag_fragment().equals("FragmentoCloseDocumentoPedido")){
            final List<DocumentoPedido> filteredModelListDocumentoPedido = filterdocumentopedido(FragmentoCloseDocumentoPedido.getInstance().ldocumentopedido, newText);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    FragmentoCloseDocumentoPedido.getInstance().adaptadordocumentopedidoasus.setFilter(filteredModelListDocumentoPedido);
                    break;
                case 1:
                    FragmentoCloseDocumentoPedido.getInstance().adaptadordocumentopedidosony.setFilter(filteredModelListDocumentoPedido);
                    break;
                case 2:
                    FragmentoCloseDocumentoPedido.getInstance().adaptadordocumentopedidosony.setFilter(filteredModelListDocumentoPedido);
                    break;
            }
        }
        if (Filtro.getTag_fragment().equals("FragmentoLineaDocumentoPedido")){
            final List<LineaDocumentoPedido> filteredModelListLineaDocumentoPedido = filterlineadocumentopedido(FragmentoLineaDocumentoPedido.getInstance().llineadocumentopedido, newText);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    FragmentoLineaDocumentoPedido.getInstance().adaptadorlineadocumentopedidoasus.setFilter(filteredModelListLineaDocumentoPedido);
                    break;
                case 1:
                    FragmentoLineaDocumentoPedido.getInstance().adaptadorlineadocumentopedidosony.setFilter(filteredModelListLineaDocumentoPedido);
                    break;
                case 2:
                    FragmentoLineaDocumentoPedido.getInstance().adaptadorlineadocumentopedidosony.setFilter(filteredModelListLineaDocumentoPedido);
                    break;
            }
        }
        if (Filtro.getTag_fragment().equals("FragmentoLineaDocumentoFactura")){
            final List<LineaDocumentoFactura> filteredModelListLineaDocumentoFactura = filterlineadocumentofactura(FragmentoLineaDocumentoFactura.getInstance().llineadocumentofactura, newText);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    FragmentoLineaDocumentoFactura.getInstance().adaptadorlineadocumentofacturaasus.setFilter(filteredModelListLineaDocumentoFactura);
                    break;
                case 1:
                    FragmentoLineaDocumentoFactura.getInstance().adaptadorlineadocumentofacturasony.setFilter(filteredModelListLineaDocumentoFactura);
                    break;
                case 2:
                    FragmentoLineaDocumentoFactura.getInstance().adaptadorlineadocumentofacturasony.setFilter(filteredModelListLineaDocumentoFactura);
                    break;
            }
        }
        if (Filtro.getTag_fragment().equals("FragmentoOpenSeccion")){
            final List<Seccion> filteredModelListSeccion = filterseccion(FragmentoOpenSeccion.getInstance().lseccion, newText);
            FragmentoOpenSeccion.getInstance().adaptadorseccion.setFilter(filteredModelListSeccion);
        }
        if (Filtro.getTag_fragment().equals("FragmentoCloseSeccion")){
            final List<Seccion> filteredModelListSeccion = filterseccion(FragmentoCloseSeccion.getInstance().lseccion, newText);
            FragmentoCloseSeccion.getInstance().adaptadorseccion.setFilter(filteredModelListSeccion);
        }
        if (Filtro.getTag_fragment().equals("FragmentoOpenCaja")){
            final List<Caja> filteredModelListCaja = filtercaja(FragmentoOpenCaja.getInstance().lcaja, newText);
            FragmentoOpenCaja.getInstance().adaptadorcaja.setFilter(filteredModelListCaja);
        }
        if (Filtro.getTag_fragment().equals("FragmentoCloseCaja")){
            final List<Caja> filteredModelListCaja = filtercaja(FragmentoCloseCaja.getInstance().lcaja, newText);
            FragmentoCloseCaja.getInstance().adaptadorcaja.setFilter(filteredModelListCaja);
        }
        if (Filtro.getTag_fragment().equals("FragmentoOpenTurno")){
            final List<Turno> filteredModelListTurno = filterturno(FragmentoOpenTurno.getInstance().lturno, newText);
            FragmentoOpenTurno.getInstance().adaptadorturno.setFilter(filteredModelListTurno);
        }
        if (Filtro.getTag_fragment().equals("FragmentoCloseTurno")){
            final List<Turno> filteredModelListTurno = filterturno(FragmentoCloseTurno.getInstance().lturno, newText);
            FragmentoCloseTurno.getInstance().adaptadorturno.setFilter(filteredModelListTurno);
        }
        if (Filtro.getTag_fragment().equals("FragmentoOpenDcj")){
            final List<Dcj> filteredModelListDcj = filterdcj(FragmentoOpenDcj.getInstance().ldcj, newText);
            FragmentoOpenDcj.getInstance().adaptadordcj.setFilter(filteredModelListDcj);
        }
        if (Filtro.getTag_fragment().equals("FragmentoCloseDcj")){
            final List<Dcj> filteredModelListDcj = filterdcj(FragmentoCloseDcj.getInstance().ldcj, newText);
            FragmentoCloseDcj.getInstance().adaptadordcj.setFilter(filteredModelListDcj);
        }
        if (Filtro.getTag_fragment().equals("FragmentoOpenDocumentoFactura")){
            final List<DocumentoFactura> filteredModelListDocumentoFactura = filterdocumentofactura(FragmentoOpenDocumentoFactura.getInstance().ldocumentofactura, newText);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    FragmentoOpenDocumentoFactura.getInstance().adaptadordocumentofacturaasus.setFilter(filteredModelListDocumentoFactura);
                    break;
                case 1:
                    FragmentoOpenDocumentoFactura.getInstance().adaptadordocumentofacturasony.setFilter(filteredModelListDocumentoFactura);
                    break;
                case 2:
                    FragmentoOpenDocumentoFactura.getInstance().adaptadordocumentofacturasony.setFilter(filteredModelListDocumentoFactura);
                    break;
            }
        }
        if (Filtro.getTag_fragment().equals("FragmentoCloseDocumentoFactura")){
            final List<DocumentoFactura> filteredModelListDocumentoFactura = filterdocumentofactura(FragmentoCloseDocumentoFactura.getInstance().ldocumentofactura, newText);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    FragmentoCloseDocumentoFactura.getInstance().adaptadordocumentofacturaasus.setFilter(filteredModelListDocumentoFactura);
                    break;
                case 1:
                    FragmentoCloseDocumentoFactura.getInstance().adaptadordocumentofacturasony.setFilter(filteredModelListDocumentoFactura);
                    break;
                case 2:
                    FragmentoCloseDocumentoFactura.getInstance().adaptadordocumentofacturasony.setFilter(filteredModelListDocumentoFactura);
                    break;
            }
        }
        if (Filtro.getTag_fragment().equals("FragmentoInicio")){
            final List<Popular> filteredModelListPopular = filterpopular(lpopular, newText);
            FragmentoInicio.getInstance().adaptadorpopulares.setFilter(filteredModelListPopular);
        }

        Log.i("TAGFRAGMENT",Filtro.getTag_fragment());
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Comida> filtercomida(List<Comida> models, String query) {
        query = query.toLowerCase();

        final List<Comida> filteredModelListcomida = new ArrayList<>();
        for (Comida model : models) {
            final String text = model.getNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelListcomida.add(model);
            }
        }
        return filteredModelListcomida;
    }
    private List<Popular> filterpopular(List<Popular> models, String query) {
        query = query.toLowerCase();

        final List<Popular> filteredModelListpopular = new ArrayList<>();
        for (Popular model : models) {
            final String text = model.getNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelListpopular.add(model);
            }
        }
        return filteredModelListpopular;
    }

    private List<DocumentoFactura> filterdocumentofactura(List<DocumentoFactura> models, String query) {
        query = query.toLowerCase();

        final List<DocumentoFactura> filteredModelListdocumentofactura = new ArrayList<>();
        for (DocumentoFactura model : models) {
            final String text = model.getDocumentoFacturaEstado().toLowerCase();
            if (text.contains(query)) {
                filteredModelListdocumentofactura.add(model);
            }
        }
        return filteredModelListdocumentofactura;
    }
    private List<LineaDocumentoFactura> filterlineadocumentofactura(List<LineaDocumentoFactura> models, String query) {
        query = query.toLowerCase();

        final List<LineaDocumentoFactura> filteredModelListlineadocumentofactura = new ArrayList<>();
        for (LineaDocumentoFactura model : models) {
            final String text = model.getLineaDocumentoFacturaNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelListlineadocumentofactura.add(model);
            }
        }
        return filteredModelListlineadocumentofactura;
    }

    private List<DocumentoPedido> filterdocumentopedido(List<DocumentoPedido> models, String query) {
        query = query.toLowerCase();

        final List<DocumentoPedido> filteredModelListdocumentopedido = new ArrayList<>();
        for (DocumentoPedido model : models) {
            final String text = model.getDocumentoPedidoEstado().toLowerCase();
            if (text.contains(query)) {
                filteredModelListdocumentopedido.add(model);
            }
        }
        return filteredModelListdocumentopedido;
    }
    private List<LineaDocumentoPedido> filterlineadocumentopedido(List<LineaDocumentoPedido> models, String query) {
        query = query.toLowerCase();

        final List<LineaDocumentoPedido> filteredModelListlineadocumentopedido = new ArrayList<>();
        for (LineaDocumentoPedido model : models) {
            final String text = model.getLineaDocumentoPedidoNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelListlineadocumentopedido.add(model);
            }
        }
        return filteredModelListlineadocumentopedido;
    }
    private List<Seccion> filterseccion(List<Seccion> models, String query) {
        query = query.toLowerCase();

        final List<Seccion> filteredModelListseccion = new ArrayList<>();
        for (Seccion model : models) {
            final String text = model.getSeccionNombre_Sec().toLowerCase();
            if (text.contains(query)) {
                filteredModelListseccion.add(model);
            }
        }
        return filteredModelListseccion;
    }
    private List<Caja> filtercaja(List<Caja> models, String query) {
        query = query.toLowerCase();

        final List<Caja> filteredModelListcaja = new ArrayList<>();
        for (Caja model : models) {
            final String text = model.getCajaNombre_Caja().toLowerCase();
            if (text.contains(query)) {
                filteredModelListcaja.add(model);
            }
        }
        return filteredModelListcaja;
    }
    private List<Turno> filterturno(List<Turno> models, String query) {
        query = query.toLowerCase();

        final List<Turno> filteredModelListturno = new ArrayList<>();
        for (Turno model : models) {
            final String text = model.getTurnoNombre_Turno().toLowerCase();
            if (text.contains(query)) {
                filteredModelListturno.add(model);
            }
        }
        return filteredModelListturno;
    }
    private List<Dcj> filterdcj(List<Dcj> models, String query) {
        query = query.toLowerCase();

        final List<Dcj> filteredModelListdcj = new ArrayList<>();
        for (Dcj model : models) {
            final String text = model.getDcjFecha_Apertura().toLowerCase();
            if (text.contains(query)) {
                filteredModelListdcj.add(model);
            }
        }
        return filteredModelListdcj;
    }

    public class CountOpen extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            String xTabla= params[1];
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND "+xTabla+".GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND "+xTabla+".EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND "+xTabla+".LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND "+xTabla+".SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            switch (xTabla){
                case "sec":
                    xWhere += " AND sec.ACTIVO=1";
                    xWhere += " AND sec.APERTURA=1";

                    break;
                case "zonas":
                    xWhere += " AND zonas.ACTIVO=1";

                    break;
                case "caja":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE caja.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND caja.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE caja.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                        } else {
                            xWhere += " AND caja.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                        }
                    }

                    xWhere += " AND caja.ACTIVO=1";
                    xWhere += " AND caja.APERTURA=1";

                    break;
                case "turno":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE turno.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND turno.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE turno.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                        } else {
                            xWhere += " AND turno.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE turno.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND turno.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
                    xWhere += " AND turno.ACTIVO=1";
                    xWhere += " AND turno.APERTURA=1";

                    break;
                case "pdd":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND pdd.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
                    if(!(Filtro.getEmpleado().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        } else {
                            xWhere += " AND pdd.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                        } else {
                            xWhere += " AND pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                        }
                    }

                    if (xWhere.equals("")) {
                        xWhere += " WHERE pdd.ESTADO<'13'";
                    } else {
                        xWhere += " AND pdd.ESTADO<'13'";
                    }
                    break;
                case "ftp":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND ftp.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
                    if(!(Filtro.getEmpleado().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        } else {
                            xWhere += " AND ftp.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                        } else {
                            xWhere += " AND ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                        }
                    }

                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.ESTADO<'13'";
                    } else {
                        xWhere += " AND ftp.ESTADO<'13'";
                    }
                    break;
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Count Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    parseResultCount(response.toString(),params[1]);
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result == 1) {
                Log.i("COUNT", "OK");
            } else {
                Log.e("COUNT", "Failed to fetch data!");
            }
        }
    }
    private void parseResultCount(String result, String table) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                switch (table){
                    case "sec":
                        itemseccion.setText(Integer.toString(post.optInt("COUNT")));
                        itemseccion.setEnabled(true);
                        Filtro.setFechaapertura(post.optString("FECHA_APERTURA"));
                        Filtro.setFecha(post.optString("FECHA_APERTURA"));
                        Log.i("SECCION",itemseccion.getText().toString()+" "+Filtro.getFechaapertura());
                        break;
                    case "caja":
                        itemcaja.setText(Integer.toString(post.optInt("COUNT")));
                        itemcaja.setEnabled(true);
                        break;
                    case "turno":
                        itemturno.setText(Integer.toString(post.optInt("COUNT")));
                        itemturno.setEnabled(true);
                        break;
                    case "pdd":
                        itempedido.setText(Integer.toString(post.optInt("COUNT")));
                        itempedido.setEnabled(true);
                        break;
                    case "ftp":
                        itemfactura.setText(Integer.toString(post.optInt("COUNT")));
                        itemfactura.setEnabled(true);
                        break;
                    case "mesas":
                        itemmesas.setText(Integer.toString(post.optInt("COUNT")));
                        itemmesas.setEnabled(true);
                        break;
                    case "message":
                        itemmessage.setText(Integer.toString(post.optInt("COUNT")));
                        itemmessage.setEnabled(true);
                        break;

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static class MySerialExecutor extends SerialExecutor {
        private final Context mContext;

        public MySerialExecutor(Context context){
            super();
            this.mContext = context;
        }

        @Override
        public void execute(TaskParams params) {
            MyParams myParams = (MyParams) params;
            // do something...
            // Check for success tag
            int success;
            String cSql = "";
            String xWhere = "";
            String xTabla= CountTable;
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND "+xTabla+".GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND "+xTabla+".EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND "+xTabla+".LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE "+xTabla+".SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND "+xTabla+".SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            switch (xTabla){

                case "are":
                    if(!(Filtro.getTipo_are().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE "+xTabla+".TIPO_ARE='" + Filtro.getTipo_are() + "'";
                        } else {
                            xWhere += " AND "+xTabla+".TIPO_ARE='" + Filtro.getTipo_are() + "'";
                        }
                    }
                    break;
                case "sec":
                    xWhere += " AND sec.ACTIVO=1";
                    xWhere += " AND sec.APERTURA=1";
                    break;
                case "caja":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE caja.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND caja.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE caja.FECHA_APERTURA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND caja.FECHA_APERTURA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE caja.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND caja.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }

                    xWhere += " AND caja.ACTIVO=1";
                    xWhere += " AND caja.APERTURA=1";
                    break;
                case "message":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE message.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND message.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE message.creado=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND message.creado=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE DATE(message.creado)='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND DATE(message.creado)='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }

                    xWhere += " AND message.ACTIVO=1";
                    break;

                case "turno":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE turno.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND turno.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE turno.FECHA_APERTURA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND turno.FECHA_APERTURA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE turno.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND turno.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE turno.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND turno.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
                    xWhere += " AND turno.ACTIVO=1";
                    xWhere += " AND turno.APERTURA=1";
                    break;
                case "dcj":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE dcj.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND dcj.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE dcj.FECHA_APERTURA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND dcj.FECHA_APERTURA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE dcj.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND dcj.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE dcj.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND dcj.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
                    xWhere += " AND dcj.APERTURA=1";
                    break;
                case "pdd":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND pdd.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
/*                    if(!(Filtro.getEmpleado().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        } else {
                            xWhere += " AND pdd.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        }
                    }
*/
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE pdd.FECHA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND pdd.FECHA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }

                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.ESTADO<'13'";
                        } else {
                            xWhere += " AND pdd.ESTADO<'13'";
                        }
                    break;
                case "ftp":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND ftp.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if(!(Filtro.getTurno().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                        } else {
                            xWhere += " AND ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                        }
                    }
/*                    if(!(Filtro.getEmpleado().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE ftp.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        } else {
                            xWhere += " AND ftp.EMPLEADO='" + Filtro.getEmpleado() + "'";
                        }
                    }
*/
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE ftp.FECHA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND ftp.FECHA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }

                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.ESTADO<'13'";
                    } else {
                        xWhere += " AND ftp.ESTADO<'13'";
                    }
                    break;
                case "mesas":
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }

                    xWhere += " AND mesas.ACTIVO=1";
                    xWhere += " AND mesas.APERTURA=1";
                    break;

                case "lft":
                    cSql = "";
                    xWhere = "";

                    break;
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Count Lista",cSql);

            try {
                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                ContentValues values = new ContentValues();
                switch (xTabla){
                    case "obs":
                        values.put("filtro", cSql);
                        break;
                    case "popular":
                        values.put("filtro", cSql);
                        break;
                    case "are":
                        values.put("filtro", cSql);
                        values.put("tipoare", Filtro.getTipo_are());
                        break;
                    case "lft":
                        values.put("pid", pid);
                        values.put("tabla", xTabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());
                        break;
                    case "lpd":
                        values.put("pid", pid);
                        values.put("tabla", xTabla);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());
                        break;

                    default:
                        values.put("filtro", cSql);
                        break;
                }

                jsonparsernew = new JSONParserNew();
                JSONObject json = jsonparsernew.makeHttpRequest(
                        url_count, "POST", values);


                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray posts = json.getJSONArray("posts"); // JSON Array
                    switch (xTabla) {
                        case "obs":
                            String oldArticulo="";
                            boolean primer = true;
                            boolean pendiente = false;
                            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
                            for (int ii = 0; ii < posts.length(); ii++) {
                                JSONObject post = posts.optJSONObject(ii);
                                if (primer){
                                    oldArticulo=post.optString("ARTICULO");
                                    primer = false;
                                    pendiente = true;
                                }
                                if(!oldArticulo.equals(post.optString("ARTICULO"))){
                                    Observacion observacionItem = new Observacion();
                                    observacionItem.setNombre_obs(getPalabras("OTROS"));
                                    observacionItem.setArticulo(oldArticulo);
                                    observacionItem.setCodigo_obs("999");
                                    observacionItem.setUrlimagen(Filtro.getUrl() + "/image/placeholder.png");
                                    lobservacion.add(observacionItem);
//                                    Log.i("Obs canvi: ", observacionItem.getArticulo() + " - " + observacionItem.getCodigo_obs() + " - " +observacionItem.getNombre_obs()+ " - " + lobservacion.size());
                                    oldArticulo=post.optString("ARTICULO");
                                    pendiente=true;
                                }
                                Observacion observacionItem = new Observacion();
                                observacionItem.setNombre_obs(getPalabras(post.optString("NOMBRE_OBS").trim()));
                                observacionItem.setArticulo(post.optString("ARTICULO"));
                                observacionItem.setCodigo_obs(post.optString("CODIGO_OBS"));
                                observacionItem.setUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                                lobservacion.add(observacionItem);
//                                Log.i("Obs normal: ", observacionItem.getArticulo() + " - " + observacionItem.getCodigo_obs() + " - " +observacionItem.getNombre_obs()+ " - " + lobservacion.size());
                            }
                            if(pendiente){
                                Observacion observacionItem = new Observacion();
                                observacionItem.setNombre_obs(getPalabras("OTROS"));
                                observacionItem.setArticulo(oldArticulo);
                                observacionItem.setCodigo_obs("999");
                                observacionItem.setUrlimagen(Filtro.getUrl() + "/image/placeholder.png");
                                lobservacion.add(observacionItem);
//                                Log.i("Obs pendent: ", observacionItem.getArticulo() + " - " + observacionItem.getCodigo_obs() + " - " +observacionItem.getNombre_obs()+ " - " + lobservacion.size());
                            }

                            // AÑADIMOS OBSERVACION AL ARRAY DE OBSERVACIONES
                            observaciones.add(lobservacion);
                            break;
                        case "popular":
                            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
                            for (int ii = 0; ii < posts.length(); ii++) {
                                JSONObject post = posts.optJSONObject(ii);

                                Popular popularItem = new Popular();
                                popularItem.setPrecio(Float.parseFloat(String.valueOf(post.optDouble("PREU_VTA1"))));
                                popularItem.setNombre(getPalabras(post.optString("NOMBRE_ARE").trim()));
                                popularItem.setArticulo(post.optString("ARTICULO"));
                                popularItem.setTipo_are(post.optString("TIPO_ARE"));
                                popularItem.setUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                                lpopular.add(popularItem);
                                Glide.with(mContext).load(popularItem.getUrlimagen()).thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).preload();
                                Log.i("Popular: ", popularItem.getTipo_are() + " - " + popularItem.getNombre()+ " - " + lpopular.size());

                            }
                            // AÑADIMOS POPULAR AL ARRAY DE POPULARES
                            populares.add(lpopular);
                            break;
                        case "are":
                            Log.i("Longitud Datos: ", Integer.toString(posts.length()));
                            for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                                Comida comida = it.next();
                                it.remove();
                            }
                            for (int ii = 0; ii < posts.length(); ii++) {
                                JSONObject post = posts.optJSONObject(ii);
                                Comida comidaItem = new Comida();
                                comidaItem.setPrecio(Float.parseFloat(String.valueOf(post.optDouble("PREU_VTA1"))));
                                comidaItem.setNombre(getPalabras(post.optString("NOMBRE_ARE").trim()));
                                comidaItem.setArticulo(post.optString("ARTICULO"));
                                comidaItem.setTipo_are(post.optString("TIPO_ARE"));
                                comidaItem.setTiva_id(post.optInt("TIVA_ID"));
                                comidaItem.setUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                                comidaItem.setIndividual(post.optInt("INDIVIDUAL"));
                                lcomida.add(comidaItem);
                                Log.i("ImagenUrl", comidaItem.getUrlimagen());
                                Glide.with(mContext).load(comidaItem.getUrlimagen()).thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.SOURCE).preload();
                            }

                            // AÑADIMOS LA CATEGORIA DE COMIDA AL ARRAY DE COMIDAS
                            comidas.add(lcomida);
/*                            Log.i("SERIALIZABLE: ","comidas size "+Integer.toString(comidas.size())+" lcomida.size: "+lcomida.size());
                            for (ArrayList<Comida> list : comidas) { // iterate -list by list
                                    for (Comida comida : list) { //iterate element by element in a list
                                        Log.i("COMIDA: ", comida.getTipo_are()+" "+comida.getNombre());
                                    }
                            }
*/


                                break;
                        case "lft":
     //                       new CalculaCabecera().execute("ftp","lft","1");
                            break;
                        case "lpd":
     //                       new CalculaCabecera().execute("pdd","lpd","1");
                            break;
                        default:
                            // successfully received LineaDocumentoFactura details

                            Log.i("Longitud Datos: ", Integer.toString(posts.length()));

                            for (int ii = 0; ii < posts.length(); ii++) {
                                JSONObject post = posts.optJSONObject(ii);
                                switch (CountTable) {
                                    case "sec":
                                        itemseccion.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemseccion.getText().toString()) == 0) {
                                            itemseccion.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemseccion.setTextColor(Filtro.getColorItem());
                                        }

                                        Filtro.setFechaapertura(post.optString("FECHA_APERTURA"));
                                        Filtro.setFecha(post.optString("FECHA_APERTURA"));
                                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                                        try {
                                            String StringRecogido = String.valueOf(Filtro.getFechaapertura());
                                            Date fecha = sdf1.parse(StringRecogido);
                                            SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
                                            StringBuilder nowYYYYMMDD = new StringBuilder(dateformatYYYYMMDD.format(fecha));
                                            SimpleDateFormat dateformatDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
                                            StringBuilder nowDDMMYYYY = new StringBuilder(dateformatDDMMYYYY.format(fecha));
                                            txtFecha_apertura.setText(nowDDMMYYYY);
                                            Log.i("CONVERT IN", StringRecogido + " " + nowDDMMYYYY + " " + nowYYYYMMDD);

                                        } catch (Exception e) {
                                            e.getMessage();
                                        }
                                        Log.i("CONVERT OUT", Filtro.getFechaapertura() + " " + txtFecha_apertura.getText().toString());
                                        Log.i("SECCION", itemseccion.getText().toString() + " " + Filtro.getFechaapertura() + " " + txtFecha_apertura.getText().toString());
                                        break;
                                    case "caja":
                                        itemcaja.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemcaja.getText().toString()) == 0) {
                                            itemcaja.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemcaja.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                    case "turno":
                                        itemturno.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemturno.getText().toString()) == 0) {
                                            itemturno.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemturno.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                    case "dcj":
                                        itemdcj.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemdcj.getText().toString()) == 0) {
                                            itemdcj.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemdcj.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                    case "pdd":
                                        itempedido.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itempedido.getText().toString()) == 0) {
                                            itempedido.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itempedido.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                    case "ftp":
                                        itemfactura.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemfactura.getText().toString()) == 0) {
                                            itemfactura.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemfactura.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                    case "mesas":
                                        itemmesas.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                            itemmesas.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemmesas.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                    case "message":
                                        itemmessage.setText(Integer.toString(post.optInt("COUNT")));
                                        if (Integer.parseInt(itemmessage.getText().toString()) == 0) {
                                            itemmessage.setTextColor(Filtro.getColorItemZero());
                                        } else {
                                            itemmessage.setTextColor(Filtro.getColorItem());
                                        }
                                        break;
                                }
                            }
                        break;
                    }

                } else {
                    // NO HAY ARTICULOS DE LA CATEGORIA success = 0
                    switch(xTabla) {
                        case "are":
                            for (Iterator<Comida> it = lcomida.iterator(); it.hasNext(); ) {
                                Comida comida = it.next();
                                it.remove();
                            }
                            Comida comidaItem = new Comida();
                            comidaItem.setPrecio(Float.parseFloat(String.valueOf("0.00")));
                            comidaItem.setNombre(getPalabras("No hay")+" "+getPalabras("Articulo"));
                            comidaItem.setArticulo("000");
                            comidaItem.setTipo_are(Filtro.getTipo_are());
                            comidaItem.setTiva_id(2);
                            comidaItem.setUrlimagen(Filtro.getUrl() + "/image/" + "placeholder.png");
                            comidaItem.setIndividual(0);
                            lcomida.add(comidaItem);
                            Log.i("ImagenUrl", comidaItem.getUrlimagen());
                            Glide.with(mContext).load(comidaItem.getUrlimagen()).thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).preload();

                            // AÑADIMOS LA CATEGORIA DE COMIDA AL ARRAY DE COMIDAS
                            comidas.add(lcomida);
                            break;
                        default:
                            break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public static class MyParams extends SerialExecutor.TaskParams {
            // ... params definition

            public MyParams(int param) {
                // ... params init
            }
        }
    }
    public class GetAreNotifTerminal extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogAreNotif = new ProgressDialog(ActividadPrincipal.this);
            pDialogAreNotif.setMessage(getPalabras("Cargando")+" "+getPalabras("Articulos")+" "+getPalabras("Notificacion")+"...");
            pDialogAreNotif.setIndeterminate(false);
            pDialogAreNotif.setCancelable(true);
            pDialogAreNotif.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE arenotifterminal.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND arenotifterminal.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE arenotifterminal.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND arenotifterminal.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE arenotifterminal.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND arenotifterminal.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE arenotifterminal.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND arenotifterminal.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON ARENOTIF-->", response.toString());
                    Log.i("Longitud Antes: ",Integer.toString(larticulonotificacionterminal.size()));
                    for (Iterator<ArticuloNotificacionTerminal> it = larticulonotificacionterminal.iterator(); it.hasNext();){
                        ArticuloNotificacionTerminal articulo = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(larticulonotificacionterminal.size()));

                    parseResultArticuloNotificacionTerminal(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            pDialogAreNotif.dismiss();
            if (result == 1) {
                Log.i(TAG_ARENOTIF, "OK");
     //           dialog_buffet();
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
    private void parseResultArticuloNotificacionTerminal(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                ArticuloNotificacionTerminal articuloItem = new ArticuloNotificacionTerminal(
                        post.optInt("ID"),
                        post.optString("GRUPO"),
                        post.optString("EMPRESA"),
                        post.optString("LOCAL"),
                        post.optString("SECCION"),
                        post.optString("ARTICULO"),
                        post.optString("TERMINAL")
                );
             //   Log.i("AreNotifTerminal: ",articuloItem.getArticuloArticulo());

                larticulonotificacionterminal.add(articuloItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   public class GetAreAreBuffet extends AsyncTask<String, Void, Integer> {

    @Override
    protected void onPreExecute() {
        //setProgressBarIndeterminateVisibility(true);
        super.onPreExecute();
        pDialog = new ProgressDialog(ActividadPrincipal.this);
        pDialog.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Articulos")+" BUFFET...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
//            Integer result = 0;
        String cSql = "";
        String xWhere = "";
        if(!(Filtro.getGrupo().equals(""))) {
            if (xWhere.equals("")) {
                xWhere += " WHERE are.GRUPO='" + Filtro.getGrupo() + "'";
            } else {
                xWhere += " AND are.GRUPO='" + Filtro.getGrupo() + "'";
            }
        }
        if(!(Filtro.getEmpresa().equals(""))) {
            if (xWhere.equals("")) {
                xWhere += " WHERE are.EMPRESA='" + Filtro.getEmpresa() + "'";
            } else {
                xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
            }
        }
        if(!(Filtro.getLocal().equals(""))) {
            if (xWhere.equals("")) {
                xWhere += " WHERE are.LOCAL='" + Filtro.getLocal() + "'";
            } else {
                xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
            }
        }
        if(!(Filtro.getSeccion().equals(""))) {
            if (xWhere.equals("")) {
                xWhere += " WHERE are.SECCION='" + Filtro.getSeccion() + "'";
            } else {
                xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
            }
        }
        if (xWhere.equals("")) {
            xWhere += " WHERE are.ACTIVOBUFFET=1";
        } else {
            xWhere += " AND are.ACTIVOBUFFET=1";
        }

        cSql += xWhere;
        if(cSql.equals("")) {
            cSql="Todos";
        }
        Log.i("Sql Lista",cSql);
        InputStream inputStream = null;
        Integer result = 0;
        HttpURLConnection urlConnection = null;

        try {
            // forming th java.net.URL object
            URL url = new URL(params[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            // for Get request
            ///           urlConnection.setRequestMethod("GET");

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            ContentValues values = new ContentValues();
            values.put("filtro", cSql);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
            writer.write(getQuery(values));
            writer.flush();
            writer.close();
            os.close();
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
            // 200 represents HTTP OK
            if (statusCode ==  200) {

                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                Log.i("JSON-->", response.toString());
                Log.i("Longitud Antes: ",Integer.toString(larticulobuffet.size()));
                for (Iterator<Articulo> it = larticulobuffet.iterator(); it.hasNext();){
                    Articulo articulo = it.next();
                    it.remove();
                }

                Log.i("Longitud Despues: ",Integer.toString(larticulobuffet.size()));

                parseResultArticulosBuffet(response.toString());
                result = 1; // Successful
            }else{
                result = 0; //"Failed to fetch data!";
            }

        } catch (Exception e) {
            e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
        }

        return result; //"Failed to fetch data!";
    }

    @Override
    protected void onPostExecute(Integer result) {
        pDialog.dismiss();
        if (result == 1) {
            Log.i("AREAREBUFFET", "OK");
        } else {
            Log.e("AREAREBUFFET", "Failed to fetch data!");
        }
    }
}
   private void parseResultArticulosBuffet(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Articulo articuloItem = new Articulo(post.optInt("ID"),
                        post.optString("ARTICULO"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optString("TIPOPLATO"),
                        getPalabras(post.optString("NOMBRE_PLATO").trim()),
                        Float.parseFloat(post.optString("PRECIO")),
                        post.optString("CANTIDAD"),
                        Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim(),
                        post.optString("TIPO_ARE"),
                        post.optString("TIPO_IVA"),
                        post.optInt("TIVA_ID"),
                        post.optInt("DEPENDIENTETIPOPLATOMAESTRO"),
                        post.optInt("EXCLUYENTEBUFFET"),
                        post.optInt("ACTIVOBUFFET"),
                        post.optInt("SW_TIPO_ARE"),
                        post.optInt("SW_SUMA_PRECIO_INDIVIDUAL"),
                        post.optInt("PRINCIPAL")
                );
                Log.i("ImagenUrl",articuloItem.getArticuloUrlimagen());

                larticulobuffet.add(articuloItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GetAreAre extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Articulos")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND are.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if (xWhere.equals("")) {
                xWhere += " WHERE are.ARTICULO='" + params[1] + "'";
            } else {
                xWhere += " AND are.ARTICULO='" + params[1] + "'";
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    Log.i("Longitud Antes: ",Integer.toString(larticulo.size()));
                    for (Iterator<Articulo> it = larticulo.iterator(); it.hasNext();){
                        Articulo articulo = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(larticulo.size()));

                    parseResultArticulos(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            pDialog.dismiss();
            if (result == 1) {
                Log.i("ARE", "OK");
                if (!ArticuloPrecioGrupo.equals("")){
//**                    populate_dialog();
                    populate_dialog_are_are();
                }else {
                    populate_dialog_are();
                }
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
    private void parseResultArticulos(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
             Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Articulo articuloItem = new Articulo(post.optInt("ID"),
                                                     post.optString("ARTICULO"),
                                                     getPalabras(post.optString("NOMBRE").trim()),
                                                     post.optString("TIPOPLATO"),
                                                     getPalabras(post.optString("NOMBRE_PLATO").trim()),
                                                     Float.parseFloat(post.optString("PRECIO")),
                                                     post.optString("CANTIDAD"),
                                                     Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim(),
                                                     post.optString("TIPO_ARE"),
                                                     post.optString("TIPO_IVA"),
                                                     post.optInt("TIVA_ID"),
                                                     post.optInt("DEPENDIENTETIPOPLATOMAESTRO"),
                                                     post.optInt("EXCLUYENTEBUFFET"),
                                                     post.optInt("ACTIVOBUFFET"),
                                                     post.optInt("SW_TIPO_ARE"),
                                                     post.optInt("SW_SUMA_PRECIO_INDIVIDUAL"),
                                                     post.optInt("PRINCIPAL")
                );
                Log.i("ImagenUrl",articuloItem.getArticuloUrlimagen());

                larticulo.add(articuloItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populate_dialog_are_are() {

        String[] articulos = new String[larticulo.size()];
        String space01 = new String(new char[01]).replace('\0', ' ');
 /*       Picasso.with(this)
                .load(ArticuloUrlImagen)
                .resize(60, 60)
                .centerCrop()
                .into(ArticuloImagenGrupo);*/
/*        Glide.with(this)
                .load(ArticuloUrlImagen)
                .centerCrop()
                .override(60,60)
                .into(ArticuloImagenGrupo);*/
        /// Leemos i cargamos la imagen para LOGO SCREEN
        Drawable d = LoadImageFromWebOperations(ArticuloUrlImagen);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
//*                Bitmap new_iconscreen = resizeBitmapImageFn(bitmap, localList.get(position).getLocalResize_logo_screen()); //resizing the bitmap 500: ricoparico 75 Marivent
        Bitmap new_iconscreen = getResizedBitmap(bitmap,60,60);
        Drawable d1screen = new BitmapDrawable(getResources(),new_iconscreen); //Converting bitmap into drawable


        articulosListNew = new ArrayList<ArticulosNew>();

        for (int i = 0; i < larticulo.size(); i++) {
            //////////////////////////////////////////////////////////
            String myTextPreu = String.format("%1$,.2f", larticulo.get(i).getArticuloPrecio());
            myTextPreu = myTextPreu.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextPreu = myTextPreu.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            //////////////////////////////////////////////////////////
            String myTextCantidad = String.format("%1$,.2f", Float.parseFloat(larticulo.get(i).getArticuloCantidad()));
            myTextCantidad = myTextCantidad.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextCantidad = myTextCantidad.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            //////////////////////////////////////////////////////////

            String myTextNombreArticulo = String.format("%1$-32s", larticulo.get(i).getArticuloNombre());
            myTextNombreArticulo = myTextNombreArticulo.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextNombreArticulo = myTextNombreArticulo.replaceAll("\\s+$", ""); // Quitamos espacios derecha

            articulos[i] =  StringUtils.rightPad(myTextNombreArticulo,32,space01)+" ("+
                    myTextPreu.toString()+" "+Filtro.getSimbolo()+")";

///            Drawables d = LoadImageFromWebOperations(larticulo.get(i).getArticuloUrlimagen());
            CheckBox opcion = new CheckBox(this);

            Spinner cmbToolbarPlato = new Spinner(this);
            List<String> lables_plato = new ArrayList<String>();
            int positionplato = 0;
            for (int j = 0; j < lplato.size(); j++) {
                lables_plato.add(lplato.get(j).getPlatoNombre_Plato());
                // SI DEPENDIENTETIPOPLATOMAESTRO = 0 TIENE QUE EMPLEAR EL TIPO DE PLATO QUE TENGA ACTIVADO EN EL ARTICULO
                // SI DEPENDIENTETIPOPLATOMAESTRO = 1 TIENE QUE EMPLEAR EL TIPO DE PLATO QUE TENGA EL PEDIDO EN ESE MOMENTO
                if (larticulo.get(i).getArticuloDependientetipoplatomaestro()==0) {
                    if (lplato.get(j).getPlatoTipoPlato().equals(larticulo.get(i).getArticuloTipoPlato())) {
                        positionplato = j;
                    }
                }else{
                    if (lplato.get(j).getPlatoTipoPlato().equals(Filtro.getTipoPlato())) {
                        positionplato = j;
                    }
                }
                Log.i("PLATO ",lplato.get(j).getPlatoTipoPlato());
            }
            ArrayAdapter<String> adapter_plato = new ArrayAdapter<>(
                    this,
                    R.layout.appbar_filter_title,lables_plato);
            adapter_plato.setDropDownViewResource(R.layout.appbar_filter_list);


            articulosListNew.add(new ArticulosNew(
                    larticulo.get(i).getArticuloArticulo(),
                    articulos[i],
                    "GRUPO",
                    opcion,
                    cmbToolbarPlato,
                    adapter_plato,
                    positionplato,
                    larticulo.get(i).getArticuloUrlimagen(),
                    larticulo.get(i).getArticuloDependientetipoplatomaestro(),
                    larticulo.get(i).getArticuloPrecio(),
                    larticulo.get(i).getArticuloSw_tipo_are(),
                    larticulo.get(i).getArticuloSw_suma_precio_individual(),
                    larticulo.get(i).getArticuloPrincipal()
            ));
        }


        ArticulosListArrayAdapterNew adapter;


        mSelectedItems = new ArrayList();  // Where we track the selected items
        mSelectedItemsTipoPlato = new ArrayList();  // Tipo plato del Item seleccionado

        AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);
        View convertView = LayoutInflater.from(this).inflate(R.layout.lista_articulo_compuesto, null);
        builder.setView(convertView);
//        builder.setIcon(ArticuloImagenGrupo.getDrawable());
        builder.setIcon(d1screen);
        builder.setTitle(ActividadPrincipal.getPalabras("Articulo")+" "+ActividadPrincipal.getPalabras("Compuesto")+" "+ArticuloNombreGrupo )
 /*               .setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // a choice has been made!
////              String selectedVal = _animals[which].getVal();
////                Log.d(TAG, "chosen " + selectedVal );
////                dialog.dismiss();
                    }
                })
*/
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int i = 0;
                        String cValor = ArticuloPrecioGrupo;
                        cValor = cValor.replace(Filtro.getSimbolo(),"");
                        cValor = cValor.replace(Html.fromHtml("&nbsp;"),"");
                        cValor = cValor.replace(".","");
                        cValor = cValor.replace(",",".");

                        Float precioGrupo = Float.parseFloat(cValor);
                        for (ArticulosNew articulo : articulosListNew) { //iterate element by element in a list
///                        Log.i("Articulo: ", articulo.getName()+" "+articulo.getCant());
                            if (articulo.getChecked())  {
                                mSelectedItems.add(i);
                                mSelectedItemsTipoPlato.add(articulo.getPosicionplato());
                                if(articulo.getSw_suma_precio_individual()==1){
                                    precioGrupo += articulo.getPreu();
                                }
                                Log.i("Articulo OK OK: ", articulo.getName()+" "+articulo.getPosicionplato());
                            }
                            i++;
                        }
                        ArticuloPrecioGrupo = String.format("%1$,.2f", precioGrupo)+" "+Filtro.getSimbolo();
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        if (mSelectedItems.size()>0) {
                            ArticuloGrupo = true;
                            new CreaLineaDocumentoPedido().execute(
                                    ArticuloCodigoGrupo,
                                    ArticuloNombreGrupo,
                                    ArticuloPrecioGrupo,
                                    ArticuloTivaGrupo,
                                    "0",
                                    "1",
                                    "",//ArticuloNombreGrupo,
                                    Integer.toString(ArticuloIdGrupo),
                                    Filtro.getTipoPlato(),
                                    "0"
                            );
                        }
                    }
                })
                .setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.RecViewArticuloCompuesto);
        // 2. set layoutManger
        rv.setLayoutManager(new WrapContentLinearLayoutManager(this));

        // 3. create an adapter
        adapter = new ArticulosListArrayAdapterNew(this, (ArrayList) articulosListNew);

        // 4. set adapter
        rv.setAdapter(adapter);

//        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        dialog.show();

/*        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View convertView = LayoutInflater.from(this).inflate(R.layout.lista, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Prueba Articulo");
        Dialog dialog = alertDialog.create();
        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.RecView);
        // 2. set layoutManger
        rv.setLayoutManager(new WrapContentLinearLayoutManager(this));

        // 3. create an adapter
        adapter = new ArticulosListArrayAdapterNew(this, (ArrayList) articulosListNew);

        // 4. set adapter
        rv.setAdapter(adapter);

//        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        dialog.show();
*/
    }
     private void populate_dialog_are() {
        String[] articulos = new String[larticulo.size()];
        String space01 = new String(new char[01]).replace('\0', ' ');

        articulosList = new ArrayList<Articulos>();

        for (int i = 0; i < larticulo.size(); i++) {
            //////////////////////////////////////////////////////////
/*            String myTextCantidad = String.format("%1$,.2f", Float.parseFloat(larticulo.get(i).getArticuloCantidad()));
            myTextCantidad = myTextCantidad.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextCantidad = myTextCantidad.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextCantidad="";
            for (int ii = 0; ii < (8-myTextCantidad.length()); ii++) {
                newTextCantidad+=space01;
            }
            newTextCantidad +=myTextCantidad;
            articulos[i] =  String.format("%-80s", Html.fromHtml(newTextCantidad.replace(" ", "&nbsp;&nbsp;")).toString()+ " " +
                    larticulo.get(i).getArticuloNombre() + " " + larticulo.get(i).getArticuloNombre_Plato()
            );
*/

            articulos[i] =  larticulo.get(i).getArticuloNombre();
///            Drawables d = LoadImageFromWebOperations(larticulo.get(i).getArticuloUrlimagen());

            articulosList.add(new Articulos(articulos[i], "ARTICULO" ,larticulo.get(i).getArticuloUrlimagen()));

        }
        ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter(this, articulosList);


        AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);

        builder.setIcon(ArticuloImagenGrupo.getDrawable());
        builder.setTitle(ActividadPrincipal.getPalabras("Articulo")+" "+ActividadPrincipal.getPalabras("Compuesto")+" "+ArticuloNombreGrupo )
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected

                .setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // a choice has been made!
                        ////              String selectedVal = _animals[which].getVal();
                        ////                Log.d(TAG, "chosen " + selectedVal );
                        ////                dialog.dismiss();
                    }
                })

                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        dialog.cancel();
                    }
                })
                .setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog1 = builder.create();
        dialog1.show();

    }

    private void populate_dialog() {
        String[] articulos = new String[larticulo.size()];
        String space01 = new String(new char[01]).replace('\0', ' ');

        articulosList = new ArrayList<Articulos>();

        for (int i = 0; i < larticulo.size(); i++) {
            //////////////////////////////////////////////////////////
            String myTextCantidad = String.format("%1$,.2f", Float.parseFloat(larticulo.get(i).getArticuloCantidad()));
            myTextCantidad = myTextCantidad.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextCantidad = myTextCantidad.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextCantidad="";
            for (int ii = 0; ii < (8-myTextCantidad.length()); ii++) {
                newTextCantidad+=space01;
            }
            newTextCantidad +=myTextCantidad;

            String myTextNombreArticulo = String.format("%1$-32s", larticulo.get(i).getArticuloNombre());
            myTextNombreArticulo = myTextNombreArticulo.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextNombreArticulo = myTextNombreArticulo.replaceAll("\\s+$", ""); // Quitamos espacios derecha
//            myTextNombreArticulo+= StringUtils.repeat(space01, (32-myTextNombreArticulo.length()));
            String indent = StringUtils.repeat(" ", 32);
            String output = larticulo.get(i).getArticuloNombre().trim();
            output += indent.substring(0, indent.length() - output.length());
/*            articulos[i] =  String.format("%-80s", Html.fromHtml(newTextCantidad.replace(" ", "&nbsp;"))+ " " +
                                                   output+" "+
                                                   larticulo.get(i).getArticuloNombre_Plato()
                            );
*/          String format = "%-40s%s%n";

/*            articulos[i] =  newTextCantidad+ " " + StringUtils.rightPad(larticulo.get(i).getArticuloNombre().trim(),32,space01)
                    + larticulo.get(i).getArticuloNombre_Plato().trim();
*/
            articulos[i] =  StringUtils.rightPad(larticulo.get(i).getArticuloNombre().trim(),32,space01);

///            Drawables d = LoadImageFromWebOperations(larticulo.get(i).getArticuloUrlimagen());
            CheckBox opcion = new CheckBox(this);

            Spinner cmbToolbarPlato = new Spinner(this);
            List<String> lables_plato = new ArrayList<String>();
            int position = 0;
            for (int j = 0; j < lplato.size(); j++) {
                lables_plato.add(lplato.get(j).getPlatoNombre_Plato());
                if(lplato.get(j).getPlatoTipoPlato().equals(larticulo.get(i).getArticuloTipoPlato())){
                    position=j;
                }
                Log.i("PLATO ",lplato.get(j).getPlatoTipoPlato());
            }
            ArrayAdapter<String> adapter_plato = new ArrayAdapter<>(
                    this,
                    R.layout.appbar_filter_title,lables_plato);
            adapter_plato.setDropDownViewResource(R.layout.appbar_filter_list);


            articulosList.add(new Articulos(articulos[i], "GRUPO", opcion, cmbToolbarPlato, adapter_plato, position, larticulo.get(i).getArticuloUrlimagen() ));

        }
        ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter(this, articulosList);


        mSelectedItems = new ArrayList();  // Where we track the selected items
        mSelectedItemsTipoPlato = new ArrayList();  // Tipo plato del Item seleccionado

        AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);

        builder.setIcon(ArticuloImagenGrupo.getDrawable());
        builder.setTitle(ActividadPrincipal.getPalabras("Articulo")+" "+ActividadPrincipal.getPalabras("Compuesto")+" "+ArticuloNombreGrupo )
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
/*                .setMultiChoiceItems(articulos, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
*/
        .setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // a choice has been made!
////              String selectedVal = _animals[which].getVal();
////                Log.d(TAG, "chosen " + selectedVal );
////                dialog.dismiss();
            }
        })

        // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int i = 0;
                        for (Articulos articulo : articulosList) { //iterate element by element in a list
///                        Log.i("Articulo: ", articulo.getName()+" "+articulo.getCant());
                            if (articulo.getChecked())  {
                                mSelectedItems.add(i);
                                mSelectedItemsTipoPlato.add(articulo.getPosicionplato());
///                                Log.i("Articulo OK: ", articulo.getName()+" "+articulo.getCant());
                            }
                            i++;
                        }

                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        if (mSelectedItems.size()>0) {
                            ArticuloGrupo = true;
                            new CreaLineaDocumentoPedido().execute(ArticuloCodigoGrupo,
                                                                   ArticuloNombreGrupo,
                                                                   ArticuloPrecioGrupo,
                                                                   ArticuloTivaGrupo,
                                                                   "0",
                                                                   "1",
                                                                   "",
                                                                   Integer.toString(ArticuloIdGrupo),
                                                                   Filtro.getTipoPlato(),
                                                                   "0");


/*                            String[] idarticulos = new String[mSelectedItems.size()];
                            pid = "";
                            for (int i = 0; i < mSelectedItems.size(); i++) {
                                idarticulos[i] = Integer.toString(larticulo.get(mSelectedItems.get(i)).getArticuloId());
                                Log.i("Id Pedidos", idarticulos[i] + " " + pid);
                                pid += idarticulos[i] + ",";
                                TaskHelper.execute(new CreaLineaDocumentoPedido(),larticulo.get(mSelectedItems.get(i)).getArticuloArticulo(),
                                        larticulo.get(mSelectedItems.get(i)).getArticuloNombre(),
                                        "0.00",
                                        Integer.toString(larticulo.get(mSelectedItems.get(i)).getArticuloTiva_id()),
                                        "1","0");

                            }
                            pid = pid.substring(0, pid.length() - 1);
                            Log.i("Id Articulos Final", pid);
///                            new TraspasoPedidoFactura().execute(pid);
*/                        }
                    }
                })
                .setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog1 = builder.create();
        dialog1.show();

    }
    private void control_articulos(){
        Log.i("CONTROL ARTICULOS",Integer.toString(nArticuloPositionSelected)+" "+Integer.toString(mSelectedItems.size()));
        if (nArticuloPositionSelected < mSelectedItems.size()) {
            TaskHelper.execute(new CreaLineaDocumentoPedido(),
                    larticulo.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloArticulo(),
                    larticulo.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloNombre(),
                    "0.00",
                    Integer.toString(larticulo.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloTiva_id()),
                    "1",
                    "0",
                    "",//ArticuloNombreGrupo,
                    Integer.toString(ArticuloIdGrupo),
                    lplato.get(mSelectedItemsTipoPlato.get(nArticuloPositionSelected)).getPlatoTipoPlato(),
                    Integer.toString(larticulo.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloPrincipal())
                    );
//                    larticulo.get(mSelectedItems.get(nArticuloPositionSelected)).getArticuloTipoPlato());
             nArticuloPositionSelected++;
        }else{
            ArticuloGrupo = false;
            ArticuloIdGrupo = 0;
        }
    }
    public static String getPalabras (String search){
//        Log.i("Palabra Valor: ",search+","+Integer.toString(lpalabras.size()));
        for(Palabras d : lpalabras){
//            Log.i("Palabra Clave: ",d.getPalabrasClavestring());
            if(d.getPalabrasClavestring().equals(search)){
//                Log.i("get1PalabrasId",d.getPalabrasPalabra());
                return d.getPalabrasPalabra();
            }
        }
        return search;
    }
    public String getNameResource(int id, Activity activity, String view) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) viewGroup.findViewById(id);
            restext = text.getText().toString();
//            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("MenuItem")){
            Menu menudrawer = navigationView.getMenu();
            MenuItem text = (MenuItem) menudrawer.findItem(id);
            restext = text.getTitle().toString();
//            Log.i("get1NameResource",text.getTitle().toString());
        }
        return restext;
    }

    public String ValorCampo (int ID, String viewclass){
       String name = getNameResource(ID, ActividadPrincipal.this,viewclass);
       if (!name.equals("")){
           String valorcampo = getPalabras(name);
           if(!valorcampo.equals("")){
               return valorcampo;
           }else{
               return name;
           }
       }
       return "**";
    }

}
