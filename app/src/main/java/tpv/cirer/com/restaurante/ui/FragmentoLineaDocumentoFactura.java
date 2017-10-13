package tpv.cirer.com.restaurante.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;

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
import java.io.File;
import java.io.IOException;
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

import tpv.cirer.com.restaurante.BuildConfig;
import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.conexion_http_post.FileDownloader;
import tpv.cirer.com.restaurante.conexion_http_post.JSONParserNew;
import tpv.cirer.com.restaurante.herramientas.DividerItemDecoration;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.StringUtil;
import tpv.cirer.com.restaurante.herramientas.TaskHelper;
import tpv.cirer.com.restaurante.herramientas.Utils;
import tpv.cirer.com.restaurante.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.restaurante.modelo.CabeceraEmpr;
import tpv.cirer.com.restaurante.modelo.CabeceraFtp;
import tpv.cirer.com.restaurante.modelo.DocumentoFacturaIva;
import tpv.cirer.com.restaurante.modelo.LineaDocumentoFactura;
import tpv.cirer.com.restaurante.print.PrintTicket;
import tpv.cirer.com.restaurante.print.ShowMsg;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.LoadImageFromWebOperations;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.codec;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.getPalabras;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.iconCarrito;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.imagelogoprint;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lcabeceraempr;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 09/11/2016.
 */

public class FragmentoLineaDocumentoFactura extends Fragment {
    public static final String TAG = "Lista LFT";
    private String cadenaZero;
    static final int SEND_TIMEOUT = 10 * 1000;
    static Print printer = null;
    static Builder builder = null;
//***    public static List<CabeceraEmpr> lcabeceraempr; // LEEMOS DESDE ACTIVIDAD PRINCIPAL
    public static List<CabeceraFtp> lcabeceraftp;
    public static List<LineaDocumentoFactura> llineadocumentofacturaprint;
    public static List<DocumentoFacturaIva> ldocumentofacturaiva;
    ProgressDialog pDialogEmpr,pDialogFtp,pDialogLft,pDialogFtpiva;
    String bug = "\u0027[1;"; // negrita
    static final int REQUEST_CODE = 12345;
    static final int IMAGE_WIDTH_MAX = 512;
    private static String url_update_cabecera;
    boolean mIsRestoredFromBackstack;

    // JSON parser class
    JSONParserNew jsonParserNew = new JSONParserNew();
    // url to update Factura
    private static final String url_update_Factura = Filtro.getUrl()+"/modifica_estado_factura.php";

    private String pid;
    ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_ESTADO = "estado";
    String TAG_SALDO = "saldo";

    //   public List<LineaDocumentoFactura> lineadocumentofacturas = new ArrayList<LineaDocumentoFactura>();
    public static List<LineaDocumentoFactura> llineadocumentofactura;
    private String url = Filtro.getUrl()+"/RellenaListaLFT.php";
    private String urlPrint;
    private static final String KEY_COLOR = "color";
    //    ProgressDialog pDialog;
    //public FragmentManager fragment;
    String cGrupo;
    String cEmpresa;
    String cLocal;
    String cSeccion;
    String cCaja;
    String ticket;
    String ticketTotal;
    String ticketMensaje;
    int nFactura;
    int nId;
    String sSerie;
    String cEstado;
    String tabla;
    String lintabla;
    String cTotal;
    String cObs;
    boolean fragmentAlreadyLoaded = false;
    public static RecyclerView recViewlineadocumentofactura;
//    public static AdaptadorLineaDocumentoFacturaHeader adaptadorlineadocumentofactura;
    public static AdaptadorLineaDocumentoFacturaHeaderAsus adaptadorlineadocumentofacturaasus;
    public static AdaptadorLineaDocumentoFacturaHeaderSony adaptadorlineadocumentofacturasony;
    public static AdaptadorLineaDocumentoFacturaHeaderOchoPulgadas adaptadorlineadocumentofacturaochopulgadas;
    View rootViewlineadocumentofactura;
    FloatingActionButton btnPrint,btnCobro;

    private static FragmentoLineaDocumentoFactura LineaDocumentoFactura = null;

    public static FragmentoLineaDocumentoFactura newInstance(int id, String estado, String serie, int factura, String total, String obs) {
        FragmentoLineaDocumentoFactura LineaDocumentoFactura = new FragmentoLineaDocumentoFactura();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("ID", id);
        args.putString("ESTADO", estado);
        args.putString("SERIE", serie);
        args.putString("TOTAL", total);
        args.putString("OBS", obs);
        args.putInt("FACTURA", factura);
        LineaDocumentoFactura.setArguments(args);
        return LineaDocumentoFactura;
    }

    public static FragmentoLineaDocumentoFactura getInstance(){
        if(LineaDocumentoFactura == null){
            LineaDocumentoFactura = new FragmentoLineaDocumentoFactura();
        }
        return LineaDocumentoFactura;
    }

    public FragmentoLineaDocumentoFactura() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       RuntimeInlineAnnotationReader.cachePackageAnnotation(AppConfigImpl.class.getPackage(), new XmlSchemaMine(""));
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mIsRestoredFromBackstack = false;
        cadenaZero="";
        Filtro.setTag_fragment("FragmentoLineaDocumentoFactura");
        llineadocumentofactura = new ArrayList<LineaDocumentoFactura>();

//***        lcabeceraempr = new ArrayList<CabeceraEmpr>(); // Leemos desde ACTIVIDAD PRINCIPAL
        lcabeceraftp = new ArrayList<CabeceraFtp>();
        llineadocumentofacturaprint = new ArrayList<LineaDocumentoFactura>();
        ldocumentofacturaiva = new ArrayList<DocumentoFacturaIva>();

        url_update_cabecera = Filtro.getUrl()+"/update_cabecera.php";

        //Floating Action Button
        nFactura = getArguments().getInt("FACTURA", 0);
        nId = getArguments().getInt("ID", 0);
        sSerie = getArguments().getString("SERIE", "");
        cEstado = getArguments().getString("ESTADO", "");
        cTotal = getArguments().getString("TOTAL", "0");
        cObs = getArguments().getString("OBS", "");

        pid = Integer.toBinaryString(nId);
        Filtro.setCobroDesdeFactura(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewlineadocumentofactura == null) {
/*             setHasOptionsMenu(true);*/
            rootViewlineadocumentofactura = inflater.inflate(R.layout.lista_print_factura, container, false);
            recViewlineadocumentofactura = (RecyclerView) rootViewlineadocumentofactura.findViewById(R.id.RecView);
            //     recViewlineadocumentofacturas.setHasFixedSize(true);

            // 2. set layoutManger
            recViewlineadocumentofactura.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
/*
            // 3. create an adapter
            ///           adaptadorlineadocumentofactura = new AdaptadorLineaDocumentoFactura(getActivity(),llineadocumentofactura);
            adaptadorlineadocumentofactura = new AdaptadorLineaDocumentoFacturaHeader(getActivity(),llineadocumentofactura,cEstado);

            // 4. set adapter
            recViewlineadocumentofactura.setAdapter(adaptadorlineadocumentofactura);
            */
            
            // 3. create an adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    adaptadorlineadocumentofacturaasus = new AdaptadorLineaDocumentoFacturaHeaderAsus(getActivity(),llineadocumentofactura,cEstado);
                    break;
                case 1:
                    adaptadorlineadocumentofacturasony = new AdaptadorLineaDocumentoFacturaHeaderSony(getActivity(),llineadocumentofactura,cEstado);
                    break;
                case 2:
                    adaptadorlineadocumentofacturaochopulgadas = new AdaptadorLineaDocumentoFacturaHeaderOchoPulgadas(getActivity(),llineadocumentofactura,cEstado);
                    break;
            }
            // 4. set adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    recViewlineadocumentofactura.setAdapter(adaptadorlineadocumentofacturaasus);
                    break;
                case 1:
                    recViewlineadocumentofactura.setAdapter(adaptadorlineadocumentofacturasony);
                    break;
                case 2:
                    recViewlineadocumentofactura.setAdapter(adaptadorlineadocumentofacturaochopulgadas);
                    break;
            }

            // 5. set item animator to DefaultAnimator
            recViewlineadocumentofactura.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            recViewlineadocumentofactura.addItemDecoration(itemDecoration);

            btnCobro = (FloatingActionButton) rootViewlineadocumentofactura.findViewById(R.id.btnCobro);
            btnCobro.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.visacurved));
            if (!cEstado.contains("CLOSE") && Filtro.getCobroDesdeFactura()==0) {
                btnCobro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!((ActividadPrincipal) getActivity()).getCruge("action_ftp_update")) {
                            Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                        } else {
                          if ( Filtro.getCobroDesdeFactura()==0) {
                                View rootView = ((ActividadPrincipal)getActivity()).getWindow().getDecorView().findViewById(android.R.id.content);
                                int txtViewID = getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                                TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                                String cMaximo = txtSaldo.getText().toString();
                                cMaximo = cMaximo.replace(Html.fromHtml("&nbsp;"),""); // quitamos espacios
                                cMaximo = cMaximo.replace(Filtro.getSimbolo(),""); // quitamos moneda
                                cMaximo = cMaximo.replace(".","");
                                cMaximo = cMaximo.replace(",",".");

                                String space01 = new String(new char[01]).replace('\0', ' ');
                                String myText= String.format("%1$,.2f", Float.parseFloat(cMaximo));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                String newText="";
                                for (int ii = 0; ii < (10-myText.length()); ii++) {
                                    newText+=space01;
                                }
                                newText +=myText;
                                cTotal=Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo();

                                ((ActividadPrincipal)getActivity()).dialog_cobro(nId,sSerie,String.valueOf(nFactura),cTotal,cObs,"factura");
                            }else{
                                View rootView = ((ActividadPrincipal)getActivity()).getWindow().getDecorView().findViewById(android.R.id.content);
                                int txtViewID = getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                                TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                                String cMaximo = txtSaldo.getText().toString();
                                cMaximo = cMaximo.replace(Html.fromHtml("&nbsp;"),""); // quitamos espacios
                                cMaximo = cMaximo.replace(Filtro.getSimbolo(),""); // quitamos moneda
                                cMaximo = cMaximo.replace(".","");
                                cMaximo = cMaximo.replace(",",".");

                                ((ActividadPrincipal) getActivity()).setCabecera(((ActividadPrincipal) getActivity()).getPalabras("Factura")+": "+Integer.toString(nFactura)+" "+((ActividadPrincipal) getActivity()).getPalabras("CLOSE COBRO")+" "+((ActividadPrincipal) getActivity()).getPalabras("Mesa")+": "+Filtro.getMesa(),Double.valueOf(cMaximo),nFactura);
                                btnCobro.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }else{
                btnCobro.setAlpha(0.3f); // COLOR APAGADO
//                btnCobro.setVisibility(View.GONE);
            }
            btnPrint = (FloatingActionButton)rootViewlineadocumentofactura.findViewById(R.id.btnPrint);
            btnPrint.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.print48));
            btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_ftp_printg")){
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {

                        Snackbar.make(view, getPalabras("Imprimir")+" "+ getPalabras("Ticket"), Snackbar.LENGTH_SHORT).show();

/***   LEEMOS DESDE ACTIVIDAD PRINCIPAL
                        urlPrint = Filtro.getUrl() + "/CabeceraEMPR.php";
                        new LeerCabeceraEmpr().execute(urlPrint);
*/
                        PrintTicket printticket = new PrintTicket(getActivity(),nFactura,sSerie);
                        printticket.iniciarTicket();
                    }

                }
            });

        }else{
            if (Filtro.getCobroDesdeFactura()==1){
                btnCobro.setVisibility(View.GONE);
            }
        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        return rootViewlineadocumentofactura;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        cadenaZero="";
        new AsyncHttpTaskLineaDocumentoFactura().execute(url);
        ///      TaskHelper.execute(new AsyncHttpTaskLineaDocumentoFactura(),url);
    }

    public class AsyncHttpTaskLineaDocumentoFactura extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSqlLft = "";
            String xWhereLft = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhereLft += " AND lft.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhereLft += " AND lft.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhereLft += " AND lft.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhereLft += " AND lft.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhereLft += " AND lft.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getSerie().equals(""))) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.SERIE='" + Filtro.getSerie() + "'";
                } else {
                    xWhereLft += " AND lft.SERIE='" + Filtro.getSerie() + "'";
                }
            }

            if(!(Filtro.getFactura()==0)) {
                if (xWhereLft.equals("")) {
                    xWhereLft += " WHERE lft.FACTURA=" + Filtro.getFactura();
                } else {
                    xWhereLft += " AND lft.FACTURA=" + Filtro.getFactura();
                }
            }
 
            cSqlLft += xWhereLft;
            if(cSqlLft.equals("")) {
                cSqlLft="Todos";
            }
            Log.i("Sql Lista",cSqlLft);
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
                values.put("filtro", cSqlLft);

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
                    Log.i("Longitud Antes: ",Integer.toString(llineadocumentofactura.size()));
/*                    for (int i = 0; i < llineadocumentofactura.size(); i++) {
                        llineadocumentofactura.remove(llineadocumentofactura.get(i));
                    }
*/
                    for (Iterator<LineaDocumentoFactura> it = llineadocumentofactura.iterator(); it.hasNext();){
                        LineaDocumentoFactura lineadocumentofactura = it.next();
//                        if (student.equals(studentToCompare)){
                        it.remove();
//                            return true;
//                        }
                    }

/*                Iterator<LineaDocumentoFactura> itr = lineadocumentofactura.iterator();

// remove all even numbers
                    while (itr.hasNext()) {
                        LineaDocumentoFactura number = itr.next();
                           itr.remove();
                    }
*/
                    Log.i("Longitud Despues: ",Integer.toString(llineadocumentofactura.size()));

                    parseResult(response.toString());
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

            /* Download complete. Lets update UI */
            if (result == 1) {
/*                Log.i("ADAPTADOR LFT", Integer.toString(adaptadorlineadocumentofactura.getItemCount()));
                adaptadorlineadocumentofactura.notifyDataSetChanged();
*/
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        Log.i("ADAPTADOR LFT", Integer.toString(adaptadorlineadocumentofacturaasus.getItemCount()));
                        adaptadorlineadocumentofacturaasus.notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("ADAPTADOR LFT", Integer.toString(adaptadorlineadocumentofacturasony.getItemCount()));
                        adaptadorlineadocumentofacturasony.notifyDataSetChanged();
                        break;
                    case 2:
                        Log.i("ADAPTADOR LFT", Integer.toString(adaptadorlineadocumentofacturaochopulgadas.getItemCount()));
                        adaptadorlineadocumentofacturaochopulgadas.notifyDataSetChanged();
                        break;
                }
                if (null!=getActivity()) {
                    Toast.makeText(getActivity(), cadenaZero, Toast.LENGTH_SHORT).show();
                }
                if (!Filtro.getCabecera()) {
                    TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "0");
                    Filtro.setCabecera(true);
                }

            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
    //   private String getQuery(List<NameValuePair> params1) throws UnsupportedEncodingException
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

/*
        for (NameValuePair pair : params1)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
*/
        return result.toString();
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            //Initialize array if null
/*            if (null == lineadocumentofactura) {
                lineadocumentofactura = new ArrayList<LineaDocumentoFactura>();
                Log.i("Inicializar lineadocumentofactura: ",Integer.toString(lineadocumentofactura.size()));
            }
*/             Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                LineaDocumentoFactura lineadocumentofacturaItem = new LineaDocumentoFactura();
                lineadocumentofacturaItem.setLineaDocumentoFacturaId(post.optInt("ID"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaNombre(post.optString("NOMBRE"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaArticulo(post.optString("ARTICULO"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaTiva_id(post.optInt("TIVA_ID"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaCant(post.optString("CANT"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaPreu(post.optString("PREU"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaImporte(post.optString("IMPORTE"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                llineadocumentofactura.add(lineadocumentofacturaItem);
                if(Float.parseFloat(lineadocumentofacturaItem.getLineaDocumentoFacturaPreu())==0.00){
                    cadenaZero += getPalabras("Precio")+" "+getPalabras("Cero")+"\n"+
                            getPalabras("Articulo")+": "+lineadocumentofacturaItem.getLineaDocumentoFacturaArticulo()+"\n"+
                            getPalabras("Nombre")+": "+lineadocumentofacturaItem.getLineaDocumentoFacturaNombre()+"\n"+
                            getPalabras("Cantidad")+": "+lineadocumentofacturaItem.getLineaDocumentoFacturaCant()+"\n"+
                            "\n" ;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class CalculaCabecera extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getPalabras("Calcula")+" "+ getPalabras("Cabecera")+"..");
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
            ((ActividadPrincipal) getActivity()).runOnUiThread(new Runnable() {
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
                            int textViewID = getResources().getIdentifier("texto_total_carrito", "id",BuildConfig.APPLICATION_ID);
                            View rootView = ((ActividadPrincipal)getActivity()).getWindow().getDecorView().findViewById(android.R.id.content);

                            TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                            if (textSaldo!=null) {
                                textSaldo.setText(String.format("%1$,.2f", saldo) + " " + Filtro.getSimbolo());
                            }
                            // Datos en appbar
                            //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                            int txtViewID = getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                            TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                            txtSaldo.setText(String.format("%1$,.2f", saldo)+" "+Filtro.getSimbolo());
                            txtSaldo.setTextSize(30);

//                            Log.i("Saldo Dentro ","txtViewID:"+Integer.toString(txtViewID)+" Total Drawer: "+textSaldo.getText().toString()+" Total Appbar: "+txtSaldo.getText().toString());


                            Utils.setBadgeCount(getActivity(), iconCarrito, Filtro.getFactura());
                        } else {
                            Toast.makeText(getActivity(), "ERROR NO "+ getPalabras("Modificar")+" "+ getPalabras("Cabecera"), Toast.LENGTH_SHORT).show();
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
    private class WSCobro extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean resul = true;

            final String NAMESPACE = "https://easyredsys.miguelangeljulvez.com/";
            final String URL="https://easyredsys.miguelangeljulvez.com/easyredsys/SerClsWSEntradaService?WSDL";
            final String METHOD_NAME = "EasyRedsysService";
            final String SOAP_ACTION = "https://easyredsys.miguelangeljulvez.com/"+METHOD_NAME;
            //           final String SOAP_ACTION = "http://83.56.29.190:8082/"+METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("reportName", Filtro.getDirreportname()+"FACTURA.rpt");
            request.addProperty("pdfName", Filtro.getDirpdfname()+"FACTURA"+String.format("%06d",nFactura)+".pdf");
            request.addProperty("wfId", "");
            request.addProperty("cpId", "");
            request.addProperty("dbViewName", "{ftp.ID}="+nId);
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
                new DownloadFile().execute(Filtro.getNamespace()+Filtro.getDirpdfname()+"FACTURA"+String.format("%06d",nFactura)+".pdf", "FACTURA"+String.format("%06d",nFactura)+".pdf");
            }
            else
            {
                Log.e(TAG, "NOT FILE PDF GENERATED!");

            }
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

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("reportName", Filtro.getDirreportname()+"FACTURA.rpt");
            request.addProperty("pdfName", Filtro.getDirpdfname()+"FACTURA"+String.format("%06d",nFactura)+".pdf");
            request.addProperty("wfId", "");
            request.addProperty("cpId", "");
            request.addProperty("dbViewName", "{ftp.ID}="+nId);
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
                new DownloadFile().execute(Filtro.getNamespace()+Filtro.getDirpdfname()+"FACTURA"+String.format("%06d",nFactura)+".pdf", "FACTURA"+String.format("%06d",nFactura)+".pdf");
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
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "FACTURA"+String.format("%06d",nFactura)+".pdf");  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Log.e(TAG, "NOT FILE PDF VIEW!");

            }
        }
    }
    public void crearTicket() {
        String space01 = new String(new char[01]).replace('\0', ' ');
        ticket = "";
        ticketTotal = "";
        ticketMensaje = "";
        String separador = "- - - - - - - - - - - - - - - - - - - - - - - - " + "\n";
        //obteniendo el encabezado del documento
        ticket = ticket + "\n";

        //fecha de emision del documento
        ticket = ticket + String.format("%-48s", getDate() + " " + getTime()) + "\n";
        ticket = ticket + separador;

        // Datos EMPRESA
        for (int i = 0; i < lcabeceraempr.size(); i++) {
            //razon
            ticket = ticket + String.format("%-48s",String.valueOf(lcabeceraempr.get(i).getCabeceraRazon())) + "\n";

            //cif
            ticket = ticket + String.format("%-48s",String.valueOf(lcabeceraempr.get(i).getCabeceraCif())) + "\n";

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
///            ticket = ticket + String.format("%-48s",domicilio) + "\n";

            //poblacion
            String poblacion = String.valueOf(lcabeceraempr.get(i).getCabeceraCod_poblacion());
            poblacion += (" " + String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_poblacion()));
///            ticket = ticket + String.format("%-48s",poblacion) + "\n";

            //provincia
///            ticket = ticket + String.format("%-48s",String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_provincia())) + "\n";
            //pais
///            ticket = ticket + String.format("%-48s",String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_pais())) + "\n";

        }

        ticket = ticket + separador;

        // DATOS FACTURA
        for (int i = 0; i < lcabeceraftp.size(); i++) {
            //LOCAL
///            ticket = ticket + String.format("%-48s","Local: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_local())) + "\n";
            //Seccion
            ticket = ticket + String.format("%-48s","Seccion: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_seccion())) + "\n";
            //Telefono
            ticket = ticket + String.format("%-48s","Telefono: " + String.valueOf(lcabeceraftp.get(i).getCabeceraTelefono())) + "\n";
            //Email
            ticket = ticket + String.format("%-48s","Email: " + String.valueOf(lcabeceraftp.get(i).getCabeceraEMail())) + "\n";
            //Web
            ticket = ticket + String.format("%-48s","Web: " + String.valueOf(lcabeceraftp.get(i).getCabeceraWeb())) + "\n";
            //Caja
///            ticket = ticket + String.format("%-48s","Caja: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_caja())) + "\n";
            //Turno
///            ticket = ticket + String.format("%-48s","Turno: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_turno())) + "\n";
            //Mesa
            ticket = ticket + String.format("%-48s","Mesa: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_mesa())) + "\n";
            //Empleado
///            ticket = ticket + String.format("%-48s","Empleado: " + String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_empleado())) + "\n";
            // Terminal
///            ticket = ticket + String.format("%-48s","Terminal: " + terminalList.get(ActividadPrincipal.cmbToolbarTerminal.getSelectedItemPosition()).getTerminalTerminal()) + "\n";
            ticket = ticket + separador;
            //Observaciones
            ///////////////////////////////////////////////////////////
            if (lcabeceraftp.get(i).getCabeceraObs().trim().length()>0) {
                String myTextFtpObs = String.format("%1$-48s", String.valueOf(lcabeceraftp.get(i).getCabeceraObs().substring(0, lcabeceraftp.get(i).getCabeceraObs().trim().length())));
                myTextFtpObs = myTextFtpObs.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextFtpObs = myTextFtpObs.replaceAll("\\s+$", ""); // Quitamos espacios derecha

                ticket = ticket + String.format("%-48s", "Observaciones: ") + "\n";
                ticket = ticket + String.format("%-48s", myTextFtpObs) + "\n";
                ticket = ticket + separador;
            }
            //////////////////////////////////////////////////////////
            //ESTADO TICKET
            ///////////////////////////////////////////////////////////
            if (!lcabeceraftp.get(i).getCabeceraEstado().equals(lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA())) {

                ticket = ticket + String.format("%-48s", "C O P I A") + "\n";
                ticket = ticket + separador;
            }
            //////////////////////////////////////////////////////////

            //Serie,factura,fecha e iva,
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String xfecha = "";
            try {
                Date datehora = sdf1.parse(String.valueOf(lcabeceraftp.get(i).getCabeceraFecha()));
                xfecha = sdf2.format(datehora);

            } catch (Exception e) {
                e.getMessage();
            }
            String ivaincluido = (0 != lcabeceraftp.get(i).getCabeceraIvaincluido() ? "SI" : "NO");
            ticket = ticket + String.format("%-48s", "SERIE  FACTURA      FECHA     IVA INCLUIDO") + "\n";
            ticket = ticket + String.format("%-48s", "  " + String.valueOf(lcabeceraftp.get(i).getCabeceraSerie())
                    + "    " + String.format("%07d", Integer.parseInt(String.valueOf(lcabeceraftp.get(i).getCabeceraFactura())))
                    + "    " + xfecha + "    " + ivaincluido) + "\n";

        }
        ticket = ticket + separador;

        //Cabecera Lineas
        ticket = ticket + String.format("%-48s","CANT   NOMBRE                     PREU   IMPORTE") + "\n";

        // DATOS LINEAS FACTURA
        for (int i = 0; i < llineadocumentofacturaprint.size(); i++) {
            String myTextCant = String.format("%1$,.2f", Float.parseFloat(llineadocumentofacturaprint.get(i).getLineaDocumentoFacturaCant()));
            myTextCant = myTextCant.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextCant = myTextCant.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextCant="";
            for (int ii = 0; ii < (6-myTextCant.length()); ii++) {
                newTextCant+=space01;
            }
            newTextCant +=myTextCant;
            ///////////////////////////////////////////////////////////
            int lennombre = llineadocumentofacturaprint.get(i).getLineaDocumentoFacturaNombre().trim().length();
            String myTextNombre = String.format("%1$-25s", String.valueOf(llineadocumentofacturaprint.get(i).getLineaDocumentoFacturaNombre().substring(0,(lennombre>25 ? 25 : lennombre))));
            myTextNombre = myTextNombre.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextNombre = myTextNombre.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextNombre=myTextNombre;
            for (int ii = 0; ii < (25-myTextNombre.length()); ii++) {
                newTextNombre+=space01;
            }
            ///////////////////////////////////////////////////////////
            String myTextPreu = String.format("%1$,.2f", Float.parseFloat(llineadocumentofacturaprint.get(i).getLineaDocumentoFacturaPreu()));
            myTextPreu = myTextPreu.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextPreu = myTextPreu.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextPreu="";
            for (int ii = 0; ii < (6-myTextPreu.length()); ii++) {
                newTextPreu+=space01;
            }
            newTextPreu +=myTextPreu;
            ///////////////////////////////////////////////////////////
            String myTextImporte = String.format("%1$,.2f", Float.parseFloat(llineadocumentofacturaprint.get(i).getLineaDocumentoFacturaImporte()));
            myTextImporte = myTextImporte.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextImporte = myTextImporte.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextImporte="";
            for (int ii = 0; ii < (8-myTextImporte.length()); ii++) {
                newTextImporte+=space01;
            }
            newTextImporte +=myTextImporte;
/*
            ///////////////////////////////////////////////////////////
            String myTextTipo_iva = String.format("%1$,.2f", Float.parseFloat(llineadocumentofacturaprint.get(i).getLineaDocumentoFacturaTipo_iva()));
            myTextTipo_iva = myTextTipo_iva.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextTipo_iva = myTextTipo_iva.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextTipo_iva="";
            for (int ii = 0; ii < (5-myTextTipo_iva.length()); ii++) {
                newTextTipo_iva+=space01;
            }
            newTextTipo_iva +=myTextTipo_iva;
            ///////////////////////////////////////////////////////////
*/
            ticket = ticket + String.format("%-48s",newTextCant+" "+newTextNombre+" "+newTextPreu+" "+newTextImporte
            ) + "\n";

        }
        ticket = ticket + separador;
        //Cabecera Lineas IVA
        ticket = ticket + String.format("%-48s","         BASE      TIPO     CUOTA       TOTAL   ") + "\n";
        ticket = ticket + String.format("%-48s","      ----------- ------- ---------- -----------") + "\n";
        float nbase = 0;
        float ncuota = 0;
        float ntotal = 0;
        // DATOS  FACTURA IVA
        for (int i = 0; i < ldocumentofacturaiva.size(); i++) {
            if (!ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_base().equals("0.00")){
                String myTextBase = String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_base())) + " " + Filtro.getSimbolo();
                myTextBase = myTextBase.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextBase = myTextBase.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newTextBase = "";
                for (int ii = 0; ii < (11 - myTextBase.length()); ii++) {
                    newTextBase += space01;
                }
                newTextBase += myTextBase;
                ///////////////////////////////////////////////////////////
                String myTextTipo_iva = String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaTipo_iva())) + " %";
                myTextTipo_iva = myTextTipo_iva.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextTipo_iva = myTextTipo_iva.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newTextTipo_iva = "";
                for (int ii = 0; ii < (7 - myTextTipo_iva.length()); ii++) {
                    newTextTipo_iva += space01;
                }
                newTextTipo_iva += myTextTipo_iva;
                ///////////////////////////////////////////////////////////
                String myTextImp_iva = String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_iva())) + " " + Filtro.getSimbolo();
                myTextImp_iva = myTextImp_iva.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextImp_iva = myTextImp_iva.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newTextImp_iva = "";
                for (int ii = 0; ii < (10 - myTextImp_iva.length()); ii++) {
                    newTextImp_iva += space01;
                }
                newTextImp_iva += myTextImp_iva;
                ///////////////////////////////////////////////////////////
                String myTextImp_total = String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_total())) + " " + Filtro.getSimbolo();
                myTextImp_total = myTextImp_total.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextImp_total = myTextImp_total.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newTextImp_total = "";
                for (int ii = 0; ii < (11 - myTextImp_total.length()); ii++) {
                    newTextImp_total += space01;
                }
                newTextImp_total += myTextImp_total;
                ///////////////////////////////////////////////////////////

                ticket = ticket + String.format("%-48s", "      " + newTextBase + " " + newTextTipo_iva + " " + newTextImp_iva + " " + newTextImp_total
                ) + "\n";
                nbase += Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_base());
                ncuota += Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_iva());
                ntotal += Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_total());
            }
        }
        ticket = ticket + String.format("%-48s","      ----------- ------- ---------- -----------") + "\n";
        ticket = ticket + "\n";

        String myTextBase =  String.format("%1$,.2f", nbase)+" "+Filtro.getSimbolo();
        myTextBase = myTextBase.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextBase = myTextBase.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextBase="";
        for (int ii = 0; ii < (11-myTextBase.length()); ii++) {
            newTextBase+=space01;
        }
        newTextBase +=myTextBase;
        ///////////////////////////////////////////////////////////
        String myTextImp_iva =  String.format("%1$,.2f", ncuota)+" "+Filtro.getSimbolo();
        myTextImp_iva = myTextImp_iva.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextImp_iva = myTextImp_iva.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextImp_iva="";
        for (int ii = 0; ii < (10-myTextImp_iva.length()); ii++) {
            newTextImp_iva+=space01;
        }
        newTextImp_iva +=myTextImp_iva;
        ///////////////////////////////////////////////////////////
        String myTextImp_total =  String.format("%1$,.2f", ntotal)+" "+Filtro.getSimbolo();
        myTextImp_total = myTextImp_total.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
        myTextImp_total = myTextImp_total.replaceAll("\\s+$", ""); // Quitamos espacios derecha
        String newTextImp_total="";
        for (int ii = 0; ii < (11-myTextImp_total.length()); ii++) {
            newTextImp_total+=space01;
        }
        newTextImp_total +=myTextImp_total;
        ///////////////////////////////////////////////////////////
//        ticket = ticket + String.format("%-48s", "Total "+newTextBase+" "+"       "+" "+newTextImp_iva+" "+newTextImp_total) + "\n";
//        ticket = ticket + separador;
        ticketTotal = StringUtil.align("Total "+newTextImp_total, 24, ' ', 0) + "\n\n";

        if (lcabeceraftp.get(0).getCabeceraMensaje().trim().length()>0) {

            ticketMensaje = ticketMensaje + separador;
            ticketMensaje = StringUtil.align(lcabeceraftp.get(0).getCabeceraMensaje().trim(), 48, ' ', 0) + "\n";
            ticketMensaje = ticketMensaje + separador;
        }

//        ticket = ticket + StringUtil.align("GRACIAS POR SU PREFERENCIA!", 48, ' ', 0) + "\n";

        ticketMensaje = ticketMensaje + "\n\n";
        /// EMPEZAMOS A IMPRIMIR
        openPrinter();
        if (printer != null) {
            openBuilder();
            if(builder != null ){
               if(ImprimirTicketLogo()){
                   if(ImprimirTicketCuerpo()){
                      ImprimirTicketTotal();
                      ImprimirTicketMensaje();
                      if(lcabeceraftp.get(0).getCabeceraImagen_firma().trim().length()>0){
                          ImprimirTicketFirma();
                      }
                      ImprimirTicketFinal();
                      Imprimir();
                   }
               }
            }
            closeBuilder();
        }
        closePrinter();
     }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public void openPrinter() {

        //open
        printer = new Print(getActivity());
        try {
            printer.openPrinter(Filtro.getPrintdeviceType(), Filtro.getPrintIp(), 1, Filtro.getPrintInterval());
            printer.beginTransaction();
            Log.e("PRINTER", "PRINT OPEN!");
        } catch (Exception e) {
            printer = null;
            ShowMsg.showException(e, "openPrinter", getActivity());
            return;
        }
    }
    public void openBuilder() {

        builder = null;
        String method = "";
        try {
            //create builder
            method = "Builder";
            builder = new Builder(Filtro.getPrintPrinterName(), Filtro.getPrintLanguage(), getActivity());

        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
    }
    public boolean ImprimirTicketLogo() {
        boolean ok_image=false;
        String method = "";
        try {

            method = "addImage";
            builder.addImage(imagelogoprint, 0, 0, Math.min(IMAGE_WIDTH_MAX, imagelogoprint.getWidth()), imagelogoprint.getHeight(), Builder.COLOR_1,
                    getBuilderMode(), getBuilderHalftone(), getBuilderBrightness());
              ok_image=true;
        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
        return ok_image;
    }
    public boolean ImprimirTicketFirma() {
        boolean ok_image=false;
        String method = "";
        try {
            /// Leemos i cargamos la imagen de la firma
            Drawable d = LoadImageFromWebOperations(lcabeceraftp.get(0).getCabeceraImagen_firma());
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

            Bitmap bitmapfirma = codec(bitmap, Bitmap.CompressFormat.JPEG, 0);// PASAMOS EL ICONO LEIDO AL BITMAP PARA IMPRIMIR.
            bitmapfirma = getResizedBitmap(bitmapfirma,512,250);

            method = "addImage";
            builder.addImage(bitmapfirma, 0, 0, Math.min(IMAGE_WIDTH_MAX, bitmapfirma.getWidth()), bitmapfirma.getHeight(), Builder.COLOR_1,
                    getBuilderMode(), getBuilderHalftone(), getBuilderBrightness());
            ok_image=true;
        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
        return ok_image;
    }
    public boolean ImprimirTicketCuerpo() {
        boolean ok_cuerpo=false;
        String method = "";
        try {

            method = "addText";
            builder.addText(getBuilderText());
            ok_cuerpo=true;
        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
        return ok_cuerpo;
    }
    public boolean ImprimirTicketMensaje() {
        boolean ok_cuerpo=false;
        String method = "";
        try {

            method = "addText";
            builder.addTextSize(1,1);
            builder.addText(ticketMensaje);
            ok_cuerpo=true;
        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
        return ok_cuerpo;
    }

    public boolean ImprimirTicketTotal() {
        boolean ok_cuerpo=false;
        String method = "";
        try {

            method = "addText";
            builder.addTextSize(2,2);
            builder.addText(ticketTotal);
            ok_cuerpo=true;
        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
        return ok_cuerpo;
    }
    public boolean ImprimirTicketFinal() {
        boolean ok_cuerpo=false;
        String method = "";
        try {

            method = "addText";
            builder.addText("\n\n\n\n");
            builder.addCut(Builder.CUT_FEED);
            ok_cuerpo=true;
        } catch (Exception e) {
            ShowMsg.showException(e, method, getActivity());
        }
        return ok_cuerpo;
    }

    public void Imprimir(){
        //send builder data
        int[] status = new int[1];
        int[] battery = new int[1];
        try{
//                            Print printer = EPOSPrintSampleActivity.getPrinter();
            printer.sendData(builder, SEND_TIMEOUT, status, battery);
            ShowMsg.showStatus(EposException.SUCCESS, status[0], battery[0], getActivity());
            // Comprobar estado ticket si = "01" modificar "02"
            if (lcabeceraftp.get(0).getCabeceraEstado().contains(lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA())){
                new SaveEstadoFactura().execute(url_update_Factura);
            }

        }catch(EposException e){
            ShowMsg.showStatus(e.getErrorStatus(), e.getPrinterStatus(), e.getBatteryStatus(), getActivity());
        }
    }
    public void closeBuilder(){
        //remove builder
        if(builder != null){
            try{
                builder.clearCommandBuffer();
                builder = null;
            }catch(Exception e){
                builder = null;
            }
        }
    }

    public void ImprimirTicket(){

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
///            Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.logo_ricoparico);

            //     Bitmap bJPGcompress = codec(bmp, Bitmap.CompressFormat.JPEG, 3);

            //           builder.addImage(ActividadPrincipal.imagelogoprint, 0, 0, 412, 76, Builder.PARAM_DEFAULT);

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
                // Comprobar estado ticket si = "01" modificar "02"
                if (lcabeceraftp.get(0).getCabeceraEstado().contains(lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA())){
                    new SaveEstadoFactura().execute(url_update_Factura);
                }

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

    }

    public static void closePrinter(){
        try{
            printer.endTransaction();
            printer.closePrinter();
            printer = null;
        }catch(Exception e){
            printer = null;
        }
    }
    private int getBuilderMode() {
/*        Spinner spinner = (Spinner)findViewById(R.id.spinner_colormode);
        switch(spinner.getSelectedItemPosition()){
            case 1:
                return Builder.MODE_GRAY16;
            case 0:
            default:
*/                return Builder.MODE_MONO;
 //       }
    }

    private int getBuilderHalftone() {
/*        Spinner spinner = (Spinner)findViewById(R.id.spinner_halftonemethod);
        switch(spinner.getSelectedItemPosition()){
            case 1:
*/                return Builder.HALFTONE_ERROR_DIFFUSION;
/*            case 2:
                return Builder.HALFTONE_THRESHOLD;
            case 0:
            default:
                return Builder.HALFTONE_DITHER;
        }
*/    }

    private double getBuilderBrightness() {
/*        TextView text = (TextView)findViewById(R.id.editText_brightness);
        try{
            return Double.parseDouble(text.getText().toString());
        }catch(Exception e){
*/            return 1.0;
//        }
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

            //    setProgressBarIndeterminateVisibility(false);
            pDialogEmpr.dismiss();

            if (result == 1) {
                Log.i("Cabecera Empr", Integer.toString(lcabeceraempr.size()));

                urlPrint = Filtro.getUrl()+"/CabeceraFTP.php";
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
            if (xWhere.equals("")) {
                xWhere += " WHERE ftp.ID=" + Integer.toString(nId);
            } else {
                xWhere += " AND ftp.ID=" + Integer.toString(nId);
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

                urlPrint = Filtro.getUrl()+"/RellenaListaLFT.php";
                new LeerLineasLft().execute(urlPrint);

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
                cabeceraFtpItem.setCabeceraNombre_mesa(post.optString("NOMBRE_MESA").trim());
                cabeceraFtpItem.setCabeceraNombre_sta(post.optString("NOMBRE_STA").trim());
                cabeceraFtpItem.setCabeceraNombre_empleado(post.optString("NOMBRE_EMPLEADO").trim());
                cabeceraFtpItem.setCabeceraNombre_caja(post.optString("NOMBRE_CAJA").trim());
                cabeceraFtpItem.setCabeceraNombre_turno(post.optString("NOMBRE_TURNO").trim());
                cabeceraFtpItem.setCabeceraNombre_seccion(post.optString("NOMBRE_SECCION"));
                cabeceraFtpItem.setCabeceraNombre_local(post.optString("NOMBRE_LOCAL").trim());
                cabeceraFtpItem.setCabeceraImagen_local(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_LOCAL").trim());
                cabeceraFtpItem.setCabeceraObs(post.optString("OBS").trim());
                cabeceraFtpItem.setCabeceraNombre_tft(post.optString("NOMBRE_TFT").trim());
                cabeceraFtpItem.setCabeceraImagen_firma(post.optString("IMAGEN_FIRMA").trim());
                cabeceraFtpItem.setCabeceraIvaincluido(post.optInt("IVAINCLUIDO"));
                cabeceraFtpItem.setCabeceraEstado(post.optString("ESTADO"));
                cabeceraFtpItem.setCabeceraMensaje(post.optString("MENSAJE"));
                cabeceraFtpItem.setCabeceraTelefono(post.optString("TELEFONO"));
                cabeceraFtpItem.setCabeceraEMail(post.optString("EMAIL"));
                cabeceraFtpItem.setCabeceraWeb(post.optString("WEB"));
                lcabeceraftp.add(cabeceraFtpItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class LeerLineasLft extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogLft = new ProgressDialog(getActivity());
            pDialogLft.setMessage("Leyendo Lineas Factura..");
            pDialogLft.setIndeterminate(false);
            pDialogLft.setCancelable(true);
            pDialogLft.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND lft.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND lft.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND lft.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND lft.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND lft.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getSerie().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.SERIE='" + sSerie + "'";
                } else {
                    xWhere += " AND lft.SERIE='" + sSerie + "'";
                }
            }

            if(!(Filtro.getFactura()==0)) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lft.FACTURA=" + nFactura;
                } else {
                    xWhere += " AND lft.FACTURA=" + nFactura;
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
                    Log.i("Longitud Antes: ",Integer.toString(llineadocumentofacturaprint.size()));
                    for (Iterator<LineaDocumentoFactura> it = llineadocumentofacturaprint.iterator(); it.hasNext();){
                        LineaDocumentoFactura lineadocumentofactura = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(llineadocumentofacturaprint.size()));

                    parseResultLft(response.toString());
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
            pDialogLft.dismiss();
            /* Download complete. Lets update UI */
            if (result == 1) {
                Log.i("Lineas LFT", Integer.toString(llineadocumentofacturaprint.size()));

                urlPrint = Filtro.getUrl()+"/RellenaListaFTPIVA.php";
                new LeerLineasFtpiva().execute(urlPrint);

            } else {
                Log.e("Lineas LFT", "Failed to fetch data!");
            }
        }
    }

    private void parseResultLft(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                LineaDocumentoFactura lineadocumentofacturaItem = new LineaDocumentoFactura();
                lineadocumentofacturaItem.setLineaDocumentoFacturaId(post.optInt("ID"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaNombre(post.optString("NOMBRE"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaArticulo(post.optString("ARTICULO"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaTiva_id(post.optInt("TIVA_ID"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaCant(post.optString("CANT"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaPreu(post.optString("PREU"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaImporte(post.optString("IMPORTE"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                lineadocumentofacturaItem.setLineaDocumentoFacturaTipo_iva(post.optString("TIPO_IVA"));
                llineadocumentofacturaprint.add(lineadocumentofacturaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class LeerLineasFtpiva extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogFtpiva = new ProgressDialog(getActivity());
            pDialogFtpiva.setMessage("Leyendo Lineas Iva..");
            pDialogFtpiva.setIndeterminate(false);
            pDialogFtpiva.setCancelable(true);
            pDialogFtpiva.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND ftpiva.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND ftpiva.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND ftpiva.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND ftpiva.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND ftpiva.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getSerie().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.SERIE='" + sSerie + "'";
                } else {
                    xWhere += " AND ftpiva.SERIE='" + sSerie + "'";
                }
            }

            if(!(Filtro.getFactura()==0)) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftpiva.FACTURA=" + nFactura;
                } else {
                    xWhere += " AND ftpiva.FACTURA=" + nFactura;
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
                    Log.i("Longitud Antes: ",Integer.toString(ldocumentofacturaiva.size()));
                    for (Iterator<DocumentoFacturaIva> it = ldocumentofacturaiva.iterator(); it.hasNext();){
                        DocumentoFacturaIva ldocumentofacturaiva = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(ldocumentofacturaiva.size()));

                    parseResultFtpiva(response.toString());
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
            pDialogFtpiva.dismiss();
            /* Download complete. Lets update UI */
            if (result == 1) {
                Log.i("Lineas FTPIVA", Integer.toString(ldocumentofacturaiva.size()));
                crearTicket();
            } else {
                Log.e("Lineas FTPIVA", "Failed to fetch data!");
            }
        }
    }

    private void parseResultFtpiva(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                DocumentoFacturaIva documentofacturaivaItem = new DocumentoFacturaIva();
                documentofacturaivaItem.setDocumentoFacturaIvaImp_base(post.optString("IMP_BASE"));
                documentofacturaivaItem.setDocumentoFacturaIvaImp_iva(post.optString("IMP_IVA"));
                documentofacturaivaItem.setDocumentoFacturaIvaImp_total(post.optString("IMP_TOTAL"));
                documentofacturaivaItem.setDocumentoFacturaIvaTipo_iva(post.optString("TIPO_IVA"));
                ldocumentofacturaiva.add(documentofacturaivaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Background Async Task to  Save Factura Details
     * */
    class SaveEstadoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFtp = new ProgressDialog(getActivity());
            pDialogFtp.setMessage("Guardando Estado ...");
            pDialogFtp.setIndeterminate(false);
            pDialogFtp.setCancelable(true);
            pDialogFtp.show();
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

            for (int i = 0; i < lcabeceraftp.size(); i++) {
                lcabeceraftp.get(i).setCabeceraEstado("02");

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                values.put(TAG_PID, pid);
                values.put(TAG_ESTADO,String.valueOf(lcabeceraftp.get(i).getCabeceraEstado()));
                values.put("updated", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());
            }

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
            pDialogFtp.dismiss();

        }
    }



}
