package tpv.cirer.com.marivent.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.StringUtil;
import tpv.cirer.com.marivent.modelo.CabeceraEmpr;
import tpv.cirer.com.marivent.modelo.CabeceraFtp;
import tpv.cirer.com.marivent.modelo.Dcj;
import tpv.cirer.com.marivent.modelo.DocumentoFacturaIva;
import tpv.cirer.com.marivent.print.ShowMsg;

//import static com.google.android.gms.internal.zzir.runOnUiThread;



/**
 * Created by JUAN on 21/11/2016.
 */

public class PrintDcjFragment extends Fragment {
    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    private StringBuilder nowYYYYMMDD;
    private StringBuilder nowDDMMYYYY;
    private SimpleDateFormat dateformatYYYYMMDD;
    private SimpleDateFormat dateformatDDMMYYYY;
    public static List<Dcj> dcjlist;
    public static final String TAG = "Print Dcj";

    static final int SEND_TIMEOUT = 10 * 1000;
    static Print printer = null;
    public static List<CabeceraEmpr> lcabeceraempr;
    public static List<CabeceraFtp> lcabeceraftp;
    public static List<DocumentoFacturaIva> lcabeceraftpiva;
    ProgressDialog pDialogEmpr,pDialogFtp,pDialogLft,pDialogFtpiva;
    private String urlPrint;

    CheckBox chkApertura;
    CheckBox chkTurno;
    CheckBox chkCaja;
    CheckBox chkSeccion;

    CheckBox chkPrint;
    CheckBox chkPantalla;

    TextView txtDcj;
    EditText txtFecha_apertura;
    TextView tvticket;
    TextView lblSeccion;
    TextView lblFecha;

    String ticket;
    String separador = "- - - - - - - - - - - - - - - - - - - - - - - - " + "\n";
    String space01 = new String(new char[01]).replace('\0', ' ');
    float ntEfectivo;
    float ntNoEfectivo;
    float ntfraTotal;
    float ntfraCobro;
    float ntfraDif;
    float nlocalTotal;
    float nlocalCobro;
    float nlocalDif;
    float nsecTotal;
    float nsecCobro;
    float nsecDif;
    float ncajaTotal;
    float ncajaCobro;
    float ncajaDif;
    float nturnoTotal;
    float nturnoCobro;
    float nturnoDif;
    String OldTfra;
    String OldTurno;
    String OldCaja;
    String OldSec;
    boolean pdtetotaltfra;
    boolean pdtetotalturno;
    boolean pdtetotalcaja;
    boolean pdtetotalsec;
    
    
    Button btnImprimir;
    Button btnSalir;
    private String pid;
    private int apertura;
    LinearLayout rootView;
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParserNew jsonParserNew = new JSONParserNew();

    // single Dcj url
    private static final String url_dcj_details = Filtro.getUrl()+"/lee_dcj_detalle_print.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DCJ = "dcj";
    private static final String TAG_FECHAAPERTURA = "fechaapertura";
    private static final String TAG_GRUPO = "grupo";
    private static final String TAG_EMPRESA = "empresa";
    private static final String TAG_LOCAL = "local";
    private static final String TAG_SECCION = "seccion";
    private static final String TAG_CAJA = "caja";
    private static final String TAG_TURNO = "cod_turno";
    private static final String TAG_APERTURA = "apertura";
    private static final String TAG_SALDOINICIO = "saldo_inicio";

    private static PrintDcjFragment PrintDcj = null;

    public static PrintDcjFragment newInstance(String id, int apertura) {
        PrintDcjFragment PrintDcj = new PrintDcjFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putInt("apertura", apertura);
        PrintDcj.setArguments(args);
        return PrintDcj;
    }

    public PrintDcjFragment() {
        // Required empty public constructor
    }
    public static PrintDcjFragment getInstance(){
        if(PrintDcj == null){
            PrintDcj = new PrintDcjFragment();
        }
        return PrintDcj;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        pid = getArguments() != null ? getArguments().getString("id") : "0";
        apertura = getArguments() != null ? getArguments().getInt("apertura") : 0;
        Filtro.setTag_fragment("PrintDcjFragment");

        dcjlist = new ArrayList<Dcj>();

        lcabeceraempr = new ArrayList<CabeceraEmpr>();
        lcabeceraftp = new ArrayList<CabeceraFtp>();
        lcabeceraftpiva = new ArrayList<DocumentoFacturaIva>();

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   View rootView = inflater.inflate(R.layout.add_lectura, container, false);
        rootView = (LinearLayout) inflater.inflate(R.layout.print_dcj,container, false);

        lblSeccion = (TextView) rootView.findViewById(R.id.lblSeccion);
        lblSeccion.setText(ValorCampo(R.id.lblSeccion, lblSeccion.getClass().getName(),0));
        txtDcj = (TextView) rootView.findViewById(R.id.Dcj);

        // Edit Text
        lblFecha = (TextView) rootView.findViewById(R.id.lblFecha);
        lblFecha.setText(ValorCampo(R.id.lblFecha, lblFecha.getClass().getName(),0));

        txtFecha_apertura = (EditText) rootView.findViewById(R.id.Fecha_apertura);
        txtFecha_apertura.setEnabled(false);

        chkApertura = (CheckBox) rootView.findViewById(R.id.chkApertura);
        chkApertura.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.ON_apertura)));
        chkApertura.setEnabled(false);

        chkTurno = (CheckBox) rootView.findViewById(R.id.chkTurno);
        chkTurno.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.listaturno)));
        chkCaja = (CheckBox) rootView.findViewById(R.id.chkCaja);
        chkCaja.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.listacaja)));
        chkSeccion = (CheckBox) rootView.findViewById(R.id.chkSeccion);
        chkSeccion.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.listaseccion)));

        chkPrint = (CheckBox) rootView.findViewById(R.id.chkPrint);
        chkPrint.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.impresora)));
        chkPantalla = (CheckBox) rootView.findViewById(R.id.chkPantalla);
        chkPantalla.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.pantalla)));

        tvticket = (TextView) rootView.findViewById(R.id.tvticket);
        tvticket.setText(ValorCampo(R.id.tvticket, tvticket.getClass().getName(),0));


        // button click event
        btnSalir = (Button)rootView.findViewById(R.id.BtnSalir);
        btnSalir.setText(ValorCampo(R.id.BtnSalir, btnSalir.getClass().getName(),0));
        btnSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btnImprimir = (Button)rootView.findViewById(R.id.BtnImprimir);
        btnImprimir.setText(ValorCampo(R.id.BtnImprimir, btnImprimir.getClass().getName(),0));
        btnImprimir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                urlPrint = Filtro.getUrl()+"/CabeceraEMPR.php";
                new LeerCabeceraEmpr().execute(urlPrint);

            }
        });

        chkTurno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!((ActividadPrincipal)getActivity()).getCruge("action_turno_printg")) {
                    chkTurno.setChecked(false);
                    Toast.makeText(getActivity(), "No puede realizar esta accion", Toast.LENGTH_SHORT).show();
                } else {
                    if (chkTurno.isChecked()) {
                        btnImprimir.setEnabled(true);
                        chkCaja.setChecked(false);
                        chkSeccion.setChecked(false);
                    } else {
                        chkCaja.setChecked(true);
                        chkSeccion.setChecked(false);
                    }
                }
            }
        });
        chkCaja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if (!((ActividadPrincipal)getActivity()).getCruge("action_caja_printg")) {
                    chkCaja.setChecked(false);
                    Toast.makeText(getActivity(), "No puede realizar esta accion", Toast.LENGTH_SHORT).show();
                } else {
                    if (chkCaja.isChecked()) {
                        btnImprimir.setEnabled(true);
                        chkTurno.setChecked(false);
                        chkSeccion.setChecked(false);
                    } else {
                        chkTurno.setChecked(false);
                        chkSeccion.setChecked(true);
                    }
                }
            }
        });
        chkSeccion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if (!((ActividadPrincipal)getActivity()).getCruge("action_sec_printg")) {
                    chkSeccion.setChecked(false);
                    Toast.makeText(getActivity(), "No puede realizar esta accion", Toast.LENGTH_SHORT).show();
                } else {
                    if (chkSeccion.isChecked()) {
                        btnImprimir.setEnabled(true);
                        chkCaja.setChecked(false);
                        chkTurno.setChecked(false);
                    } else {
                        chkTurno.setChecked(true);
                        chkCaja.setChecked(false);
                    }
                }
            }
        });
        chkPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (chkPrint.isChecked()) {
                        chkPantalla.setChecked(false);
                } else {
                        chkPantalla.setChecked(true);
                }
            }
        });
        chkPantalla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
            if (chkPantalla.isChecked()) {
                chkPrint.setChecked(false);
            } else {
                chkPrint.setChecked(true);
            }
            }
        });

        return rootView;
    }
    public String getNameResource(int id, String view, int num) {
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) rootView.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("CheckBox")){
            CheckBox text = (CheckBox) rootView.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("Button")){
            Button text = (Button) rootView.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("ToggleButton")){
            if (num==0) {
                ToggleButton textOn = (ToggleButton) rootView.findViewById(id);
                restext = textOn.getText().toString();
                Log.i("get1NameResource",textOn.getText().toString());
            }else{
                ToggleButton textOff = (ToggleButton) rootView.findViewById(id);
                restext = textOff.getText().toString();
                Log.i("get1NameResource",textOff.getText().toString());
            }
        }
        return restext;
    }

    public String ValorCampo (int ID, String viewclass, int num ){
        String name = getNameResource(ID, viewclass, num);
        if (!name.equals("")){
            String valorcampo =((ActividadPrincipal)getActivity()).getPalabras(name);
            if(!valorcampo.equals("")){
                return valorcampo;
            }else{
                return name;
            }
        }
        return "**";
    }

    public void CabEmprListDcj() {
        // Datos EMPRESA
        for (int i = 0; i < lcabeceraempr.size(); i++) {
            //razon
            ticket = ticket + String.format("%-48s", String.valueOf(lcabeceraempr.get(i).getCabeceraRazon())) + "\n";

            //cif
            ticket = ticket + String.format("%-48s", String.valueOf(lcabeceraempr.get(i).getCabeceraCif())) + "\n";

            //domicilio
            String domicilio = String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_calle());
            domicilio += ",";
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraNumero()).length() > 0) {
                domicilio += String.valueOf(lcabeceraempr.get(i).getCabeceraNumero());
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraBloque()).length() > 0) {
                domicilio += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraBloque()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraEscalera()).length() > 0) {
                domicilio += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraEscalera()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraPiso()).length() > 0) {
                domicilio += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraPiso()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraPuerta()).length() > 0) {
                domicilio += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraPuerta()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraAmpliacion()).length() > 0) {
                domicilio += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraAmpliacion()));
            }
            ticket = ticket + String.format("%-48s", domicilio) + "\n";

            //poblacion
            String poblacion = String.valueOf(lcabeceraempr.get(i).getCabeceraCod_poblacion());
            poblacion += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_poblacion()));
            ticket = ticket + String.format("%-48s", poblacion) + "\n";

            //provincia
            ticket = ticket + String.format("%-48s", String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_provincia())) + "\n";
            //pais
            ticket = ticket + String.format("%-48s", String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_pais())) + "\n";

        }

        ticket = ticket + separador;
    }

    public void CabSecDcj(int position){
        /// Ponemos Valores seccion a cero
        nsecTotal = 0;
        nsecCobro = 0;
        nsecDif = 0;

        //Seccion
        ticket += String.format("%-48s","Seccion: " + String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_seccion())) + "\n";
        ticket += "\n";

        if (chkSeccion.isChecked()) {
            OldSec = String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_seccion());
            pdtetotalsec = true;
        }
    }
    public void TotSecDcj() {
        /// Pasamos Valores sec a totales sec
        nlocalTotal += nsecTotal;
        nlocalCobro += nsecCobro;
        nlocalDif += nsecDif;

        /// Imprimimos Totales Caja
        ticket += String.format("%-48s", "         --------- -------- --------") + "\n";
        String myTextTotal = String.format("%1$,.2f", nsecTotal);
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextTotal = "";
        for (int ii = 0; ii < (8 - myTextTotal.length()); ii++) {
            newTextTotal += space01;
        }
        newTextTotal += myTextTotal;
        ///////////////////////////////////////////////////////////
        String myTextCobro = String.format("%1$,.2f", nsecCobro);
        myTextCobro = myTextCobro.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextCobro = myTextCobro.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextCobro = "";
        for (int ii = 0; ii < (8 - myTextCobro.length()); ii++) {
            newTextCobro += space01;
        }
        newTextCobro += myTextCobro;
        ///////////////////////////////////////////////////////////
        String myTextDiferencia = String.format("%1$,.2f", nsecDif);
        myTextDiferencia = myTextDiferencia.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextDiferencia = myTextDiferencia.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextDiferencia = "";
        for (int ii = 0; ii < (8 - myTextDiferencia.length()); ii++) {
            newTextDiferencia += space01;
        }
        newTextDiferencia += myTextDiferencia;
        ///////////////////////////////////////////////////////////
        ticket += String.format("%-48s", "T.Seccion." + newTextTotal + " " + newTextCobro + " " + newTextDiferencia) + "\n";
        ticket += "\n";
    }
    
    public void CabCajaDcj(int position){
        /// Ponemos Valores caja a cero
        ncajaTotal = 0;
        ncajaCobro = 0;
        ncajaDif = 0;

        //Caja
        ticket += String.format("%-48s","Caja: " + String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_caja())) + "\n";
        ticket += "\n";
        if (chkSeccion.isChecked() || chkCaja.isChecked()) {
            OldCaja = String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_caja());
            pdtetotalcaja = true;
        }
    }
    public void TotCajaDcj() {
        /// Pasamos Valores caja a totales caja
        nsecTotal += ncajaTotal;
        nsecCobro += ncajaCobro;
        nsecDif += ncajaDif;

        /// Imprimimos Totales Caja
        ticket += String.format("%-48s", "         --------- -------- --------") + "\n";
        String myTextTotal = String.format("%1$,.2f", ncajaTotal);
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextTotal = "";
        for (int ii = 0; ii < (8 - myTextTotal.length()); ii++) {
            newTextTotal += space01;
        }
        newTextTotal += myTextTotal;
        ///////////////////////////////////////////////////////////
        String myTextCobro = String.format("%1$,.2f", ncajaCobro);
        myTextCobro = myTextCobro.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextCobro = myTextCobro.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextCobro = "";
        for (int ii = 0; ii < (8 - myTextCobro.length()); ii++) {
            newTextCobro += space01;
        }
        newTextCobro += myTextCobro;
        ///////////////////////////////////////////////////////////
        String myTextDiferencia = String.format("%1$,.2f", ncajaDif);
        myTextDiferencia = myTextDiferencia.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextDiferencia = myTextDiferencia.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextDiferencia = "";
        for (int ii = 0; ii < (8 - myTextDiferencia.length()); ii++) {
            newTextDiferencia += space01;
        }
        newTextDiferencia += myTextDiferencia;
        ///////////////////////////////////////////////////////////
        ticket += String.format("%-48s", "T.Caja...." + newTextTotal + " " + newTextCobro + " " + newTextDiferencia) + "\n";
        ticket += "\n";
    }

    public void CabTurnoDcj(int position){
        /// Ponemos Valores turno a cero
        nturnoTotal = 0;
        nturnoCobro = 0;
        nturnoDif = 0;

        //Turno
        ticket += String.format("%-48s","Turno: " + String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_turno())) + "\n";
        ticket += "\n";

        OldTurno=String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_turno());
        pdtetotalturno = true;

    }
    public void TotTurnoDcj() {
        /// Pasamos Valores turno a totales caja
        ncajaTotal += nturnoTotal;
        ncajaCobro += nturnoCobro;
        ncajaDif += nturnoDif;

        /// Imprimimos Totales Turno
        ticket += String.format("%-48s", "         --------- -------- --------") + "\n";
        String myTextTotal = String.format("%1$,.2f", nturnoTotal);
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextTotal = "";
        for (int ii = 0; ii < (8 - myTextTotal.length()); ii++) {
            newTextTotal += space01;
        }
        newTextTotal += myTextTotal;
        ///////////////////////////////////////////////////////////
        String myTextCobro = String.format("%1$,.2f", nturnoCobro);
        myTextCobro = myTextCobro.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextCobro = myTextCobro.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextCobro = "";
        for (int ii = 0; ii < (8 - myTextCobro.length()); ii++) {
            newTextCobro += space01;
        }
        newTextCobro += myTextCobro;
        ///////////////////////////////////////////////////////////
        String myTextDiferencia = String.format("%1$,.2f", nturnoDif);
        myTextDiferencia = myTextDiferencia.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextDiferencia = myTextDiferencia.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextDiferencia = "";
        for (int ii = 0; ii < (8 - myTextDiferencia.length()); ii++) {
            newTextDiferencia += space01;
        }
        newTextDiferencia += myTextDiferencia;
        ///////////////////////////////////////////////////////////
        ticket += String.format("%-48s", "T.Turno..." + newTextTotal + " " + newTextCobro + " " + newTextDiferencia) + "\n";
        ticket += "\n";
    }

    public void CabTfraDcj(int position){
        /// Ponemos Valores tfra a cero
        ntfraTotal = 0;
        ntfraCobro = 0;
        ntfraDif = 0;

        //TipoCobro
        ticket += String.format("%-48s","Tipo Cobro: " + String.valueOf(lcabeceraftp.get(position).getCabeceraNombre_tft())) + "\n";
        ticket += "\n";

        OldTfra = lcabeceraftp.get(position).getCabeceraNombre_tft();
        pdtetotaltfra = true;
    }
    public void TotTfraDcj() {
        /// Pasamos Valores tfra a totales turno
        nturnoTotal += ntfraTotal;
        nturnoCobro += ntfraCobro;
        nturnoDif += ntfraDif;

        /// Imprimimos Totales Cobro
        ticket += String.format("%-48s", "         --------- -------- --------") + "\n";
        String myTextTotal = String.format("%1$,.2f", ntfraTotal);
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextTotal = "";
        for (int ii = 0; ii < (8 - myTextTotal.length()); ii++) {
            newTextTotal += space01;
        }
        newTextTotal += myTextTotal;
        ///////////////////////////////////////////////////////////
        String myTextCobro = String.format("%1$,.2f", ntfraCobro);
        myTextCobro = myTextCobro.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextCobro = myTextCobro.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextCobro = "";
        for (int ii = 0; ii < (8 - myTextCobro.length()); ii++) {
            newTextCobro += space01;
        }
        newTextCobro += myTextCobro;
        ///////////////////////////////////////////////////////////
        String myTextDiferencia = String.format("%1$,.2f", ntfraDif);
        myTextDiferencia = myTextDiferencia.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextDiferencia = myTextDiferencia.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextDiferencia = "";
        for (int ii = 0; ii < (8 - myTextDiferencia.length()); ii++) {
            newTextDiferencia += space01;
        }
        newTextDiferencia += myTextDiferencia;
        ///////////////////////////////////////////////////////////
        ticket += String.format("%-48s", "T.Cobro..." + newTextTotal + " " + newTextCobro + " " + newTextDiferencia) + "\n";
        ticket += "\n";
    }
    public void TotDesgloseDcj() {
        // DESGLOSAMOS TOTALES CAJA
        ticket += String.format("%-48s","DESGLOSE CAJA") + "\n";
        ticket += String.format("%-48s","-------------") + "\n";
        String myTextTotal =  String.format("%1$,.2f", Float.parseFloat(dcjlist.get(0).getDcjSaldo_inicio()));
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextTotal="";
        for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
            newTextTotal+=space01;
        }
        newTextTotal +=myTextTotal;
        ticket += String.format("%-48s","SALDO INICIO EFECTIVO "+newTextTotal) + "\n";
        ///////////////////////////////////////////////////////////
        myTextTotal =  String.format("%1$,.2f", ntEfectivo);
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        newTextTotal="";
        for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
            newTextTotal+=space01;
        }
        newTextTotal +=myTextTotal;
        ticket += String.format("%-48s","COBROS EFECTIVO...... "+newTextTotal) + "\n";
        ///////////////////////////////////////////////////////////
        myTextTotal =  String.format("%1$,.2f", (ntEfectivo + Float.parseFloat(dcjlist.get(0).getDcjSaldo_inicio())));
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        newTextTotal="";
        for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
            newTextTotal+=space01;
        }
        newTextTotal +=myTextTotal;
        ticket += String.format("%-48s","TOTAL EFECTIVO....... "+newTextTotal) + "\n";
        ticket += String.format("%-48s","------------------------------") + "\n";
        ///////////////////////////////////////////////////////////
        ticket += "\n";
        ///////////////////////////////////////////////////////////
        myTextTotal =  String.format("%1$,.2f", ntNoEfectivo);
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        newTextTotal="";
        for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
            newTextTotal+=space01;
        }
        newTextTotal +=myTextTotal;
        ticket += String.format("%-48s","TOTAL NO EFECTIVO.... "+newTextTotal) + "\n";
        ticket += String.format("%-48s","------------------------------") + "\n";
        ///////////////////////////////////////////////////////////
        ticket += "\n";
        ///////////////////////////////////////////////////////////
        myTextTotal =  String.format("%1$,.2f", (ntEfectivo + ntNoEfectivo + Float.parseFloat(dcjlist.get(0).getDcjSaldo_inicio())));
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        newTextTotal="";
        for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
            newTextTotal+=space01;
        }
        newTextTotal +=myTextTotal;
        ticket += String.format("%-48s","TOTAL CAJA........... "+newTextTotal) + "\n";
        ticket += String.format("%-48s","------------------------------") + "\n";
        ///////////////////////////////////////////////////////////
        ticket += "\n";

    }
    public void CabFtpDcj(){

        //Cabecera FTP
        ticket += String.format("%-48s","S FACTURA  TOTAL    COBRO     DIF    ST EM MESA") + "\n";
        ticket += separador;
    }

    public void ListaLineaFtp(int position){
        ///////////////////////////////////////////////////////////
        String myTextSerie = String.format("%1$-1s", String.valueOf(lcabeceraftp.get(position).getCabeceraSerie().substring(0,1)));
        myTextSerie = myTextSerie.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextSerie = myTextSerie.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextSerie=myTextSerie;
        for (int ii = 0; ii < (1-myTextSerie.length()); ii++) {
            newTextSerie+=space01;
        }
        ///////////////////////////////////////////////////////////
        String newTextFactura=String.format("%07d", Integer.parseInt(String.valueOf(lcabeceraftp.get(position).getCabeceraFactura())));
        ///////////////////////////////////////////////////////////
        String myTextTotal = String.format("%1$,.2f", Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_total()));
        myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextTotal="";
        for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
            newTextTotal+=space01;
        }
        newTextTotal +=myTextTotal;
        ///////////////////////////////////////////////////////////
        String myTextCobro = String.format("%1$,.2f", Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_cobro()));
        myTextCobro = myTextCobro.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextCobro = myTextCobro.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextCobro="";
        for (int ii = 0; ii < (8-myTextCobro.length()); ii++) {
            newTextCobro+=space01;
        }
        newTextCobro +=myTextCobro;
        ///////////////////////////////////////////////////////////
        String myTextDiferencia = String.format("%1$,.2f", Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_diferencia()));
        myTextDiferencia = myTextDiferencia.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextDiferencia = myTextDiferencia.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextDiferencia="";
        for (int ii = 0; ii < (8-myTextDiferencia.length()); ii++) {
            newTextDiferencia+=space01;
        }
        newTextDiferencia +=myTextDiferencia;
        ///////////////////////////////////////////////////////////
        String myTextEstado = String.format("%1$-2s", String.valueOf(lcabeceraftp.get(position).getCabeceraEstado().substring(0,2)));
        myTextEstado = myTextEstado.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextEstado = myTextEstado.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextEstado=myTextEstado;
        for (int ii = 0; ii < (2-myTextEstado.length()); ii++) {
            newTextEstado+=space01;
        }
        ///////////////////////////////////////////////////////////
        String myTextEmpleado = String.format("%1$-2s", String.valueOf(lcabeceraftp.get(position).getCabeceraEmpleado().substring(0,2)));
        myTextEmpleado = myTextEmpleado.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextEmpleado = myTextEmpleado.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextEmpleado=myTextEmpleado;
        for (int ii = 0; ii < (2-myTextEmpleado.length()); ii++) {
            newTextEmpleado+=space01;
        }
        ///////////////////////////////////////////////////////////
        String myTextMesa = String.format("%1$-4s", String.valueOf(lcabeceraftp.get(position).getCabeceraMesa()));
        myTextMesa = myTextMesa.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextMesa = myTextMesa.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextMesa=myTextMesa;
        for (int ii = 0; ii < (4-myTextMesa.length()); ii++) {
            newTextMesa+=space01;
        }
        ///////////////////////////////////////////////////////////

        ticket += String.format("%-48s",newTextSerie+" "+
                newTextFactura+" "+
                newTextTotal+" "+
                newTextCobro+" "+
                newTextDiferencia+" "+
                newTextEstado+" "+
                newTextEmpleado+" "+
                newTextMesa
        ) + "\n";

        /// Sumamos importe de cada linea para tipos de cobro
        ntfraTotal += Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_total());
        ntfraCobro += Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_cobro());
        ntfraDif += Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_diferencia());
        /// Sumamos si tipo cobro es efectivo
        if(lcabeceraftp.get(position).getCabeceraEfectivo()==1){
            ntEfectivo += Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_cobro());
        }else{
            ntNoEfectivo += Float.parseFloat(lcabeceraftp.get(position).getCabeceraImp_cobro());
        }
        
    }
    public void crearListDcj() {
        ticket = "";
        //obteniendo el encabezado del documento

        //fecha de emision del documento
        ticket = ticket + String.format("%-48s", getDate() + " " + getTime()) + "\n";

        // Datos EMPRESA
        CabEmprListDcj();

        ntEfectivo = 0;
        ntNoEfectivo = 0;
        
        nlocalTotal = 0;
        nlocalCobro = 0;
        nlocalDif = 0;

        OldTfra="";
        OldTurno="";
        OldCaja="";
        OldSec="";
        
        pdtetotaltfra = false;
        pdtetotalturno = false;
        pdtetotalcaja = false;
        pdtetotalsec = false;

        // DATOS FACTURAS
        for (int i = 0; i < lcabeceraftp.size(); i++) {
            if (i==0) {
                //FECHA DIARIO CAJA,
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                String xfecha = "";
                try {
                    Date datehora = sdf1.parse(String.valueOf(lcabeceraftp.get(i).getCabeceraFecha()));
                    xfecha = sdf2.format(datehora);

                } catch (Exception e) {
                    e.getMessage();
                }
                ticket += String.format("%-48s",(0 != apertura ? "A B I E R T O":"C E R R A D O")) + "\n";

                if (chkSeccion.isChecked()) {
                    ticket += String.format("%-48s","D I A R I O  C A J A  P O R  S E C C I O N") + "\n";
                }
                if (chkCaja.isChecked()) {
                    ticket += String.format("%-48s","D I A R I O  C A J A  P O R  C A J A") + "\n";
                }
                if (chkTurno.isChecked()) {
                    ticket += String.format("%-48s","D I A R I O  C A J A  P O R  T U R N O") + "\n";
                }

                ticket += separador;
                ticket += "\n";

                //FECHA
                ticket += String.format("%-48s","Fecha: " + xfecha) + "\n";
                ticket += "\n";
                
                //LOCAL
                ticket += String.format("%-48s", "Local: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_local())) + "\n";

                //Seccion
                CabSecDcj(i);

                //Caja
                CabCajaDcj(i);

                //Turno
                CabTurnoDcj(i);

                //TipoCobro
                CabTfraDcj(i);

                //Cabecera Lineas
                CabFtpDcj();
            }
            if (chkSeccion.isChecked()) {
                if (!lcabeceraftp.get(i).getCabeceraNombre_seccion().equals(OldSec)) {
                    TotSecDcj();
                    CabSecDcj(i);
                }
                if (!lcabeceraftp.get(i).getCabeceraNombre_caja().equals(OldCaja)) {
                    TotCajaDcj();
                    CabCajaDcj(i);
                }
                if (!lcabeceraftp.get(i).getCabeceraNombre_turno().equals(OldTurno)) {
                    TotTurnoDcj();
                    CabTurnoDcj(i);
                }
            }
            if (chkCaja.isChecked()) {
                if (!lcabeceraftp.get(i).getCabeceraNombre_caja().equals(OldCaja)) {
                    TotCajaDcj();
                    CabCajaDcj(i);
                }
                if (!lcabeceraftp.get(i).getCabeceraNombre_turno().equals(OldTurno)) {
                    TotTurnoDcj();
                    CabTurnoDcj(i);
                }
            }
            if (chkTurno.isChecked()) {
                if (!lcabeceraftp.get(i).getCabeceraNombre_turno().equals(OldTurno)) {
                    TotTurnoDcj();
                    CabTurnoDcj(i);
                }
            }

            if(!lcabeceraftp.get(i).getCabeceraNombre_tft().equals(OldTfra)){
                TotTfraDcj();
                CabTfraDcj(i);
            }
            //// Lista lineas tickets
            ListaLineaFtp(i);
        }
        if(pdtetotaltfra){
            /// IMPRIMIMOS TOTALES TIPO COBRO
            TotTfraDcj();
        }
        if(pdtetotalturno){
            /// IMPRIMIMOS TOTALES TURNO
            TotTurnoDcj();
        }
        if(pdtetotalcaja){
            /// IMPRIMIMOS TOTALES CAJA
            TotCajaDcj();
        }
        if(pdtetotalsec){
            /// IMPRIMIMOS TOTALES SECCION
            TotSecDcj();
        }

        TotDesgloseDcj();

        ticket += separador;
        ticket += StringUtil.align("Importes expresados en "+Filtro.getSimbolo()+" "+Filtro.getMoneda(), 48, ' ', 0) + "\n";

        ticket += separador + "\n\n\n\n\n\n";
        if (chkPrint.isChecked()){
            ImprimirDCJ();
        }
        if (chkPantalla.isChecked()) {
            tvticket.setText(ticket);
        }
    }
    public void ImprimirDCJ(){

        //open
        printer = new Print(getActivity());
        try{
            printer.openPrinter(Filtro.getPrintdeviceType(), Filtro.getPrintIp(), 1, Filtro.getPrintInterval());
            Log.e("PRINTER", "PRINT OPEN!");
        }catch(Exception e){
            printer = null;
            ShowMsg.showException(e, "openPrinter" , getActivity());
            return;
        }
        Builder builder = null;
        String method = "";
        try{
            //create builder
            method = "Builder";
            builder = new Builder(Filtro.getPrintPrinterName(),Filtro.getPrintLanguage(),getActivity());
//                                intent.getStringExtra("printername"), intent.getIntExtra("language", 0), getApplicationContext());

            //add command
/*                        method = "addTextFont";
                        builder.addTextFont(getBuilderFont());

                        method = "addTextAlign";
                        builder.addTextAlign(getBuilderAlign());

                        method = "addTextLineSpace";
                        builder.addTextLineSpace(getBuilderLineSpace());

                        method = "addTextLang";
                        builder.addTextLang(getBuilderLanguage());

                        method = "addTextSize";
                        builder.addTextSize(getBuilderSizeW(), getBuilderSizeH());

                        method = "addTextStyle";
                        builder.addTextStyle(Builder.FALSE, getBuilderStyleUnderline(), getBuilderStyleBold(), Builder.COLOR_1);

                        method = "addTextPosition";
                        builder.addTextPosition(getBuilderXPosition());

                        method = "addFeedUnit";
                        builder.addFeedUnit(getBuilderFeedUnit());

*/
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
            builder.addImage(bmp, 0, 0, 48, 48, Builder.PARAM_DEFAULT);
            method = "addText";
            builder.addText(getBuilderText());
            builder.addCut(Builder.CUT_FEED);

            //send builder data
            int[] status = new int[1];
            int[] battery = new int[1];
            try{
//                            Print printer = EPOSPrintSampleActivity.getPrinter();
                printer.sendData(builder, SEND_TIMEOUT, status, battery);
                ShowMsg.showStatus(EposException.SUCCESS, status[0], battery[0], getActivity());
            }catch(EposException e){
                ShowMsg.showStatus(e.getErrorStatus(), e.getPrinterStatus(), e.getBatteryStatus(), getActivity());
            }
        }catch(Exception e){
            ShowMsg.showException(e, method, getActivity());
        }

        //remove builder
        if(builder != null){
            try{
                builder.clearCommandBuffer();
                builder = null;
            }catch(Exception e){
                builder = null;
            }
        }

        closePrinter();
        getActivity().onBackPressed();

    }

    public static void closePrinter(){
        try{
            printer.closePrinter();
            printer = null;
        }catch(Exception e){
            printer = null;
        }
    }

    private String getBuilderText() {
        return ticket;
    }

    private String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(cal.getTime());
    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        return dateFormat.format(cal.getTime());
    }

    /**
     * Background Async Task to Get complete Dcj details
     * */
    class GetDcjDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(ActividadPrincipal.getPalabras("Cargando")+" Detalles Dcj. "+ActividadPrincipal.getPalabras("Espere por favor")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting Dcj details in background thread
         * */
        @Override
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            ((ActividadPrincipal) getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);

                         JSONObject json = jsonParserNew.makeHttpRequest(
                                url_dcj_details, "GET", values);


                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received Dcj details
                            JSONArray DcjObj = json
                                    .getJSONArray(TAG_DCJ); // JSON Array

                            // get first Dcj object from JSON Array
                            JSONObject Dcj = DcjObj.getJSONObject(0);
                            String xfecha="";
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                            try{
                                String StringRecogido = Dcj.getString(TAG_FECHAAPERTURA);
                                Date datehora = sdf1.parse(StringRecogido);

                                Calendar c = Calendar.getInstance();
                                c.setTime(datehora);
 //                               c.add(Calendar.DATE, 1);  // number of days to add
                                xfecha = sdf2.format(c.getTime());  // dt is now the new date


                            } catch (Exception e) {
                                e.getMessage();
                            }


                            // display Dcj data in EditText
                            txtFecha_apertura.setText(xfecha);
                            txtDcj.setText(Dcj.getString(TAG_DCJ));
                            chkApertura.setChecked((0 != Integer.parseInt(Dcj.getString(TAG_APERTURA)) ? true : false));


                            Dcj dcjItem = new Dcj();
                            dcjItem.setDcjFecha_Apertura(Dcj.getString(TAG_FECHAAPERTURA));
                            dcjItem.setDcjApertura(Integer.parseInt(Dcj.getString(TAG_APERTURA)));
                            dcjItem.setDcjGrupo(Dcj.getString(TAG_GRUPO));
                            dcjItem.setDcjEmpresa(Dcj.getString(TAG_EMPRESA));
                            dcjItem.setDcjLocal(Dcj.getString(TAG_LOCAL));
                            dcjItem.setDcjSeccion(Dcj.getString(TAG_SECCION));
                            dcjItem.setDcjCaja(Dcj.getString(TAG_CAJA));
                            dcjItem.setDcjCod_turno(Dcj.getString(TAG_TURNO));
                            dcjItem.setDcjSaldo_inicio(Dcj.getString(TAG_SALDOINICIO));

                            dcjlist.add(dcjItem);


                        }else{
                            // Dcj with pid not found
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
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
    public class LeerCabeceraEmpr extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpr = new ProgressDialog(getActivity());
            pDialogEmpr.setMessage("Leyendo Cabecera Empresa..");
            pDialogEmpr.setIndeterminate(false);
            pDialogEmpr.setCancelable(true);
            pDialogEmpr.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(dcjlist.get(0).getDcjGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empr.GRUPO='" + dcjlist.get(0).getDcjGrupo() + "'";
                } else {
                    xWhere += " AND empr.GRUPO='" + dcjlist.get(0).getDcjGrupo() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE empr.EMPRESA='" + dcjlist.get(0).getDcjEmpresa() + "'";
                } else {
                    xWhere += " AND empr.EMPRESA='" + dcjlist.get(0).getDcjEmpresa() + "'";
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

            //    setProgressBarIndeterminateVisibility(false);
            pDialogEmpr.dismiss();

            if (result == 1) {
                Log.i("Cabecera Empr", Integer.toString(lcabeceraempr.size()));

                urlPrint = Filtro.getUrl()+"/ListaDCJ.php";
                new LeerCabeceraFtp().execute(urlPrint);

            } else {
                Log.e("Cabecera Empr", "Failed to fetch data!");
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
    public class LeerCabeceraFtp extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogFtp = new ProgressDialog(getActivity());
            pDialogFtp.setMessage("Leyendo Cabecera Factura..");
            pDialogFtp.setIndeterminate(false);
            pDialogFtp.setCancelable(true);
            pDialogFtp.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(dcjlist.get(0).getDcjGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.GRUPO='" + dcjlist.get(0).getDcjGrupo() + "'";
                } else {
                    xWhere += " AND ftp.GRUPO='" + dcjlist.get(0).getDcjGrupo() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.EMPRESA='" + dcjlist.get(0).getDcjEmpresa() + "'";
                } else {
                    xWhere += " AND ftp.EMPRESA='" + dcjlist.get(0).getDcjEmpresa() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.LOCAL='" + dcjlist.get(0).getDcjLocal() + "'";
                } else {
                    xWhere += " AND ftp.LOCAL='" + dcjlist.get(0).getDcjLocal() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.SECCION='" + dcjlist.get(0).getDcjSeccion() + "'";
                } else {
                    xWhere += " AND ftp.SECCION='" + dcjlist.get(0).getDcjSeccion() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.CAJA='" + dcjlist.get(0).getDcjCaja() + "'";
                } else {
                    xWhere += " AND ftp.CAJA='" + dcjlist.get(0).getDcjCaja() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjCod_turno().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.COD_TURNO='" + dcjlist.get(0).getDcjCod_turno() + "'";
                } else {
                    xWhere += " AND ftp.COD_TURNO='" + dcjlist.get(0).getDcjCod_turno() + "'";
                }
            }
            if(!(dcjlist.get(0).getDcjFecha_Apertura().equals(""))) {
                if(Filtro.getUrl().contains("sqlsrv")) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.FECHA=CONVERT(DATETIME, '" + dcjlist.get(0).getDcjFecha_Apertura() + "', 120)";
                    } else {
                        xWhere += " AND ftp.FECHA=CONVERT(DATETIME, '" + dcjlist.get(0).getDcjFecha_Apertura() + "', 120)";
                    }
                }else{
                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.FECHA='" + dcjlist.get(0).getDcjFecha_Apertura() + "'";
                    } else {
                        xWhere += " AND ftp.FECHA='" + dcjlist.get(0).getDcjFecha_Apertura() + "'";
                    }

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
                    Log.i("Longitud Antes: ",Integer.toString(lcabeceraftp.size()));
                    for (Iterator<CabeceraFtp> it = lcabeceraftp.iterator(); it.hasNext();){
                        CabeceraFtp cabeceraFtp = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(lcabeceraftp.size()));

                    parseResultCabeceraFtp(response.toString());
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

            //    setProgressBarIndeterminateVisibility(false);
            pDialogFtp.dismiss();

            if (result == 1) {
                Log.i("Cabecera Ftp", Integer.toString(lcabeceraftp.size()));

                crearListDcj();
                
            } else {
                Log.e("Cabecera Ftp", "Failed to fetch data!");
            }
        }
    }
    private void parseResultCabeceraFtp(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                CabeceraFtp cabeceraFtpItem = new CabeceraFtp();
                cabeceraFtpItem.setCabeceraSerie(post.optString("SERIE").trim());
                cabeceraFtpItem.setCabeceraFactura(post.optInt("FACTURA"));
                cabeceraFtpItem.setCabeceraFecha(post.optString("FECHA").trim());
                cabeceraFtpItem.setCabeceraMesa(post.optString("MESA").trim());
                cabeceraFtpItem.setCabeceraEstado(post.optString("ESTADO").trim());
                cabeceraFtpItem.setCabeceraEmpleado(post.optString("EMPLEADO").trim());
                cabeceraFtpItem.setCabeceraImp_total(post.optString("TOTAL").trim());
                cabeceraFtpItem.setCabeceraImp_cobro(post.optString("COBRO").trim());
                cabeceraFtpItem.setCabeceraImp_diferencia(post.optString("DIFERENCIA").trim());
                cabeceraFtpItem.setCabeceraNombre_caja(post.optString("NOMBRE_CAJA").trim());
                cabeceraFtpItem.setCabeceraNombre_turno(post.optString("NOMBRE_TURNO").trim());
                cabeceraFtpItem.setCabeceraNombre_seccion(post.optString("NOMBRE_SECCION"));
                cabeceraFtpItem.setCabeceraNombre_local(post.optString("NOMBRE_LOCAL").trim());
                cabeceraFtpItem.setCabeceraNombre_tft(post.optString("NOMBRE_TFT").trim());
                cabeceraFtpItem.setCabeceraEfectivo(post.optInt("EFECTIVO"));
                lcabeceraftp.add(cabeceraFtpItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getQuery(ContentValues values) throws UnsupportedEncodingException
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



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState !=null){
            //    new AsyncHttpTask().execute(url);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        new GetDcjDetails().execute();
    }

}

