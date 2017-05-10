package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import tpv.cirer.com.marivent.BuildConfig;
import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.FileDownloader;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.SerialExecutor;
import tpv.cirer.com.marivent.herramientas.TaskHelper;
import tpv.cirer.com.marivent.herramientas.Utils;
import tpv.cirer.com.marivent.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.marivent.modelo.CabeceraEmpr;
import tpv.cirer.com.marivent.modelo.CabeceraPdd;
import tpv.cirer.com.marivent.modelo.DocumentoPedidoIva;
import tpv.cirer.com.marivent.modelo.LineaDocumentoPedido;
import tpv.cirer.com.marivent.modelo.Plato;
import tpv.cirer.com.marivent.print.ShowMsg;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.iconCarrito;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lplato;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.terminalList;

/**
 * Created by JUAN on 26/09/2016.
 */

public class FragmentoLineaDocumentoPedido extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "Lista LPD";
    String TAG_SALDO = "saldo";
    private static String url_update_cabecera;

    static final int SEND_TIMEOUT = 10 * 1000;
    // JSON parser class

    private static JSONParserNew jsonParserNew = null;

    String TAG_PLATO = "PLATO: ";

    static Print printer = null;
    public static List<CabeceraEmpr> lcabeceraempr;
    public static List<CabeceraPdd> lcabecerapdd;
    public static List<LineaDocumentoPedido> llineadocumentopedidoprint;
    public static List<DocumentoPedidoIva> ldocumentopedidoiva;
    ProgressDialog pDialogEmpr,pDialogPdd,pDialogLpd,pDialogPddiva,pDialogplato;
    //   public List<LineaDocumentoPedido> lineadocumentopedidos = new ArrayList<LineaDocumentoPedido>();
    public static List<LineaDocumentoPedido> llineadocumentopedido;
    private String url = Filtro.getUrl()+"/RellenaListaLPD.php";
    private static String urlPrint;
    private static String PrintTable;
    private static final String KEY_COLOR = "color";
    //    ProgressDialog pDialog;
    //public FragmentManager fragment;
    String cGrupo;
    String cEmpresa;
    String cLocal;
    String cSeccion;
    String cCaja;
    String pedidocabecera;
    String pedido;
    String pedidofinal;
    ProgressDialog pDialog;
    String tabla;
    String lintabla;


    public static Toolbar toolbarPlato;

    private TextView lblPlato;
    private static String URL_PLATOS;
    private ArrayList<Plato> platoList;
    private Spinner cmbToolbarPlato;

    boolean optionprint;

    static int nPedido;
    static int nId;
    static String cEstado;
    static int nPosition;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    public static RecyclerView recViewlineadocumentopedido;
    public static AdaptadorLineaDocumentoPedidoHeader adaptadorlineadocumentopedido;
    View rootViewlineadocumentopedido;
    FloatingActionButton btnSend,btnPrint;
    
    private MySerialExecutor mSerialExecutorActivity;

    private static FragmentoLineaDocumentoPedido LineaDocumentoPedido = null;

    public static FragmentoLineaDocumentoPedido newInstance(int id, String estado, int pedido) {
        FragmentoLineaDocumentoPedido LineaDocumentoPedido = new FragmentoLineaDocumentoPedido();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("ID", id);
        args.putString("ESTADO", estado);
        args.putInt("PEDIDO", pedido);
        LineaDocumentoPedido.setArguments(args);
        return LineaDocumentoPedido;
    }

    public static FragmentoLineaDocumentoPedido getInstance(){
        if(LineaDocumentoPedido == null){
            LineaDocumentoPedido = new FragmentoLineaDocumentoPedido();
        }
        return LineaDocumentoPedido;
    }

    public FragmentoLineaDocumentoPedido() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Filtro.setTag_fragment("FragmentoLineaDocumentoPedido");
        llineadocumentopedido = new ArrayList<LineaDocumentoPedido>();
        url_update_cabecera = Filtro.getUrl()+"/update_cabecera.php";

        lcabeceraempr = new ArrayList<CabeceraEmpr>();
        lcabecerapdd = new ArrayList<CabeceraPdd>();
        llineadocumentopedidoprint = new ArrayList<LineaDocumentoPedido>();
        ldocumentopedidoiva = new ArrayList<DocumentoPedidoIva>();

        // Rellenar string toolbar_plato
        platoList = new ArrayList<Plato>();

        //Floating Action Button
        nPedido = getArguments().getInt("PEDIDO", 0);
        nId = getArguments().getInt("ID", 0);
        cEstado = getArguments().getString("ESTADO", "");
        optionprint = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewlineadocumentopedido == null) {
/*             setHasOptionsMenu(true);*/
            rootViewlineadocumentopedido = inflater.inflate(R.layout.lista_print_pedido, container, false);
            recViewlineadocumentopedido = (RecyclerView) rootViewlineadocumentopedido.findViewById(R.id.RecView);
            //     recViewlineadocumentopedidos.setHasFixedSize(true);

            // 2. set layoutManger
            recViewlineadocumentopedido.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
            // 3. create an adapter
 ///           adaptadorlineadocumentopedido = new AdaptadorLineaDocumentoPedido(getActivity(),llineadocumentopedido);
            adaptadorlineadocumentopedido = new AdaptadorLineaDocumentoPedidoHeader(getActivity(),llineadocumentopedido,cEstado);

            // 4. set adapter
            recViewlineadocumentopedido.setAdapter(adaptadorlineadocumentopedido);

            // 5. set item animator to DefaultAnimator
            recViewlineadocumentopedido.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            recViewlineadocumentopedido.addItemDecoration(itemDecoration);

            // Appbar page filter platos
            toolbarPlato = (Toolbar) rootViewlineadocumentopedido.findViewById(R.id.appbarplato);
            lblPlato = (TextView) toolbarPlato.findViewById(R.id.LblPlato);
            lblPlato.setText(ValorCampo(R.id.LblPlato, lblPlato.getClass().getName()));
            cmbToolbarPlato = (Spinner) toolbarPlato.findViewById(R.id.CmbToolbarPlato);

            cmbToolbarPlato.setOnItemSelectedListener(this);
////            URL_PLATOS = Filtro.getUrl() + "/get_platos.php";
////            new GetPlatos().execute(URL_PLATOS);
            populateSpinnerPlatobis();



            btnSend = (FloatingActionButton)rootViewlineadocumentopedido.findViewById(R.id.btnSend);
            btnSend.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.send48));
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_pdd_printg")){
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(view, "Enviar Pedido Secciones", Snackbar.LENGTH_LONG).show();
                        optionprint = false;
/*

                            // LEER MODELOS SECUENCIALMENTE
                            mSerialExecutorActivity = new MySerialExecutor(getActivity());

                            // Lineas PEDIDO
                            for (Iterator<LineaDocumentoPedido> it = llineadocumentopedidoprint.iterator(); it.hasNext();){
                                LineaDocumentoPedido lineadocumentopedido = it.next();
                                it.remove();
                            }
                            PrintTable="lpd";
                            urlPrint = Filtro.getUrl()+"/RellenaListaLPD.php";
                            mSerialExecutorActivity.execute(null);

                            // Cabecera EMPRESA
                            for (Iterator<CabeceraEmpr> it = lcabeceraempr.iterator(); it.hasNext(); ) {
                                CabeceraEmpr cabeceraEmpr = it.next();
                                it.remove();
                            }
                            PrintTable = "empr";
                            urlPrint = Filtro.getUrl() + "/CabeceraEMPR.php";
                            mSerialExecutorActivity.execute(null);

                            // CABECERA PEDIDO
                            for (Iterator<CabeceraPdd> it = lcabecerapdd.iterator(); it.hasNext();){
                                CabeceraPdd cabeceraPdd = it.next();
                                it.remove();
                            }
                            PrintTable = "pdd";
                            urlPrint = Filtro.getUrl() + "/CabeceraPDD.php";
                            mSerialExecutorActivity.execute(null);

                            // LINEAS IVA PEDIDO
                            for (Iterator<DocumentoPedidoIva> it = ldocumentopedidoiva.iterator(); it.hasNext();){
                                DocumentoPedidoIva ldocumentopedidoiva = it.next();
                                it.remove();
                            }
                            PrintTable = "pddiva";
                            urlPrint = Filtro.getUrl() + "/RellenaListaPDDIVA.php";
                            mSerialExecutorActivity.execute(null);
*/
                        nPosition = 0;
                        setImpresora(nPosition);
 ///                       urlPrint = Filtro.getUrl() + "/RellenaListaLPDprintsec.php"; // ORDENADO POR TIPO ARTICULO
                        urlPrint = Filtro.getUrl() + "/RellenaListaLPDprintsecplato.php";    // ORDENADO POR TIPO PLATO
                        new LeerLineasLpd().execute(urlPrint,Integer.toString(nPosition));
                   }
                }
            });
            btnPrint = (FloatingActionButton)rootViewlineadocumentopedido.findViewById(R.id.btnPrint);
            btnPrint.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.print48));
            btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_pdd_printg")){
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(view, "Imprimir Pedido", Snackbar.LENGTH_LONG).show();
//                    new WSPedido().execute();
                        optionprint = true;
                        urlPrint = Filtro.getUrl() + "/CabeceraEMPR.php";
                        new LeerCabeceraEmpr().execute(urlPrint);
                    }
                }
            });

        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        return rootViewlineadocumentopedido;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
  //      adaptadorlineadocumentopedido.setItemViewType(1);
        new AsyncHttpTaskLineaDocumentoPedido().execute(url);
  ///      TaskHelper.execute(new AsyncHttpTaskLineaDocumentoPedido(),url);
    }
    /**
     * Adding spinner data grupo
     */
     private void populateSpinnerPlato() {
        List<String> lables_plato = new ArrayList<String>();
        for (int i = 0; i < platoList.size(); i++) {
            lables_plato.add(platoList.get(i).getPlatoNombre_Plato());
            Log.i("PLATO ",platoList.get(i).getPlatoTipoPlato());
        }
        ArrayAdapter<String> adapter_plato = new ArrayAdapter<>(
                this.getActivity(),
                R.layout.appbar_filter_title,lables_plato);

        adapter_plato.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarPlato.setAdapter(adapter_plato);
        cmbToolbarPlato.setSelection(0);
        if (platoList.size()>0) {
            Filtro.setTipoPlato(platoList.get(0).getPlatoTipoPlato());
        }else{
            Filtro.setTipoPlato("00");
        }

    }
    private void populateSpinnerPlatobis() {
        List<String> lables_plato = new ArrayList<String>();
        for (int i = 0; i < lplato.size(); i++) {
            lables_plato.add(lplato.get(i).getPlatoNombre_Plato());
            Log.i("PLATO ",lplato.get(i).getPlatoTipoPlato());
        }
        ArrayAdapter<String> adapter_plato = new ArrayAdapter<>(
                this.getActivity(),
                R.layout.appbar_filter_title,lables_plato);

        adapter_plato.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarPlato.setAdapter(adapter_plato);
        cmbToolbarPlato.setSelection(0);
        if (lplato.size()>0) {
            Filtro.setTipoPlato(lplato.get(0).getPlatoTipoPlato());
        }else{
            Filtro.setTipoPlato("00");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.CmbToolbarPlato)
        {
            Filtro.setTipoPlato(lplato.get(position).getPlatoTipoPlato());
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
    public String getNameResource(int id, String view) {
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) toolbarPlato.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        return restext;
    }

    public String ValorCampo (int ID, String viewclass ){
        String name = getNameResource(ID, viewclass);
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

    public class GetPlatos extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogplato = new ProgressDialog(getActivity());
            pDialogplato.setMessage(ActividadPrincipal.getPalabras("Cargando")+" Platos...");
            pDialogplato.setIndeterminate(false);
            pDialogplato.setCancelable(true);
            pDialogplato.show();
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
                    for (Iterator<Plato> it = platoList.iterator(); it.hasNext();){
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
            pDialogplato.dismiss();
            if (result == 1) {
                Log.e(TAG_PLATO, "OK PLATOS");
                populateSpinnerPlato();
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
                        post.optString("NOMBRE"));
                platoList.add(cat);
                Log.i("POST Longitud: ",Integer.toString(platoList.size()));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class AsyncHttpTaskLineaDocumentoPedido extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSqlLpd = "";
            String xWhereLpd = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhereLpd.equals("")) {
                    xWhereLpd += " WHERE lpd.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhereLpd += " AND lpd.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhereLpd.equals("")) {
                    xWhereLpd += " WHERE lpd.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhereLpd += " AND lpd.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhereLpd.equals("")) {
                    xWhereLpd += " WHERE lpd.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhereLpd += " AND lpd.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhereLpd.equals("")) {
                    xWhereLpd += " WHERE lpd.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhereLpd += " AND lpd.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhereLpd.equals("")) {
                    xWhereLpd += " WHERE lpd.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhereLpd += " AND lpd.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            if(!(Filtro.getPedido()==0)) {
                if (xWhereLpd.equals("")) {
                    xWhereLpd += " WHERE lpd.PEDIDO=" + Filtro.getPedido();
                } else {
                    xWhereLpd += " AND lpd.PEDIDO=" + Filtro.getPedido();
                }
            }
/*            if (xWhereLpd.equals("")) {
                xWhereLpd += " WHERE lpd.SWPEDIDO=1";
            } else {
                xWhereLpd += " AND lpd.SWPEDIDO=1";
            }
*/
            cSqlLpd += xWhereLpd;
            if(cSqlLpd.equals("")) {
                cSqlLpd="Todos";
            }
            Log.i("Sql Lista",cSqlLpd);
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
                values.put("filtro", cSqlLpd);

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
                    Log.i("Longitud Antes: ",Integer.toString(llineadocumentopedido.size()));
                    for (Iterator<LineaDocumentoPedido> it = llineadocumentopedido.iterator(); it.hasNext();){
                        LineaDocumentoPedido lineadocumentopedido = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(llineadocumentopedido.size()));

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
                Log.i("ADAPTADOR LPD", Integer.toString(adaptadorlineadocumentopedido.getItemCount()));
                adaptadorlineadocumentopedido.notifyDataSetChanged();
                if (!Filtro.getCabecera()) {
                    TaskHelper.execute(new CalculaCabecera(), "pdd", "lpd", "0");
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

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                LineaDocumentoPedido lineadocumentopedidoItem = new LineaDocumentoPedido();
                lineadocumentopedidoItem.setLineaDocumentoPedidoId(post.optInt("ID"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoNombre(post.optString("NOMBRE"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoArticulo(post.optString("ARTICULO"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoCant(post.optString("CANT"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoObs(post.optString("OBS"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoUrlimagen(Filtro.getUrl() + "/image/" +post.optString("IMAGEN").trim());
                lineadocumentopedidoItem.setLineaDocumentoPedidoSwPedido(post.optInt("SWPEDIDO"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoSwFactura(post.optInt("SWFACTURA"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoIndividual(post.optInt("INDIVIDUAL"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoTipoPlato(post.optString("TIPOPLATO"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoNombre_plato(post.optString("NOMBRE_PLATO"));
                llineadocumentopedido.add(lineadocumentopedidoItem);
                Log.i("ImagenUrl",lineadocumentopedidoItem.getLineaDocumentoPedidoUrlimagen());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setImpresora(int pos){
        Filtro.setPrintPrinterName(terminalList.get(pos).getTerminalPrinter());
        Filtro.setPrintIp(terminalList.get(pos).getTerminalPrintIp());
        Filtro.setPrintLanguage(terminalList.get(pos).getTerminalPrintLanguage());
        Filtro.setPrintInterval(terminalList.get(pos).getTerminalPrintInterval());
        switch (terminalList.get(pos).getTerminalDeviceType()) {
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

    }
    class CalculaCabecera extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Calcula Cabecera..");
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
                        jsonParserNew = new JSONParserNew();
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
//                Toast.makeText(ActividadPrincipal.this, "Creada linea ", Toast.LENGTH_SHORT).show();
                            // Datos en menu drawer
///                            int layoutID = getResources().getIdentifier("actividad_principal", "id", BuildConfig.APPLICATION_ID);
                            int textViewID = getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                            View rootView = ((ActividadPrincipal) getActivity()).getWindow().getDecorView().findViewById(android.R.id.content);

                            TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                            if (textSaldo != null){
                                textSaldo.setText(String.format("%1$,.2f", saldo) + " " + Filtro.getSimbolo());
                            }
                        // Datos en appbar
                            //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                            int txtViewID = getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                            TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                            txtSaldo.setText(String.format("%1$,.2f", saldo)+" "+Filtro.getSimbolo());
                            txtSaldo.setTextSize(16);
//                            Log.i("Saldo Dentro ","txtViewID:"+Integer.toString(txtViewID)+" Total Drawer: "+textSaldo.getText().toString()+" Total Appbar: "+txtSaldo.getText().toString());


                            Utils.setBadgeCount(getActivity(), iconCarrito, Filtro.getPedido());
                        } else {
                            Toast.makeText(getActivity(), "ERROR NO UPDATE CABECERA ", Toast.LENGTH_SHORT).show();
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

    //Tarea Asíncrona para llamar al WS de consulta en segundo plano
    private class WSPedido extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean resul = true;

            final String NAMESPACE = Filtro.getNamespace();
            final String URL=Filtro.getNamespace()+"MyServiceCRLinux.asmx?WSDL";
            final String METHOD_NAME = "exportToPdf";
            final String SOAP_ACTION = Filtro.getNamespace()+METHOD_NAME;
 //           final String SOAP_ACTION = "http://83.56.29.190:8082/"+METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("reportName", Filtro.getDirreportname()+"PEDIDO.rpt");
            request.addProperty("pdfName", Filtro.getDirpdfname()+"PEDIDO"+String.format("%06d",nPedido)+".pdf");
            request.addProperty("wfId", "");
            request.addProperty("cpId", "");
            request.addProperty("dbViewName", "{pdd.ID}="+nId);
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
                new DownloadFile().execute(Filtro.getNamespace()+Filtro.getDirpdfname()+"PEDIDO"+String.format("%06d",nPedido)+".pdf", "PEDIDO"+String.format("%06d",nPedido)+".pdf");
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
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "PEDIDO"+String.format("%06d",nPedido)+".pdf");  // -> filename = maven.pdf
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
    public void crearPedido() {
        String space01 = new String(new char[01]).replace('\0', ' ');
        pedido = "";
        String separador = "- - - - - - - - - - - - - - - - - - - - - - - - " + "\n";
        //obteniendo el encabezado del documento

        //fecha de emision del documento
        pedido = pedido + String.format("%-48s", getDate() + " " + getTime()) + "\n";

        // Datos EMPRESA
        for (int i = 0; i < lcabeceraempr.size(); i++) {
            //razon
            pedido = pedido + String.format("%-48s",String.valueOf(lcabeceraempr.get(i).getCabeceraRazon())) + "\n";

        }

        pedido = pedido + separador;

        // DATOS PEDIDO
        for (int i = 0; i < lcabecerapdd.size(); i++) {
            //LOCAL
            pedido = pedido + String.format("%-48s","Local: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_local())) + "\n";
            //Seccion
 //           pedido = pedido + String.format("%-48s","Seccion: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_seccion())) + "\n";
            //Caja
//            pedido = pedido + String.format("%-48s","Caja: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_caja())) + "\n";
            //Turno
//            pedido = pedido + String.format("%-48s","Turno: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_turno())) + "\n";
            //Mesa
            pedido = pedido + String.format("%-48s","Mesa: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_mesa())) + "\n";
            //Comensales
            pedido = pedido + String.format("%-48s","Comensales: " + String.valueOf(lcabecerapdd.get(i).getCabeceraComensales())) + "\n";
            //Empleado
//            pedido = pedido + String.format("%-48s","Empleado: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_empleado())) + "\n";
            // Terminal
//            pedido = pedido + String.format("%-48s","Terminal: " + terminalList.get(nPosition).getTerminalTerminal()) + "\n";

            pedido = pedido + separador;
            //Observaciones
            ///////////////////////////////////////////////////////////
            if (lcabecerapdd.get(i).getCabeceraObs().trim().length()>0) {
                String myTextPddObs = String.format("%1$-48s", String.valueOf(lcabecerapdd.get(i).getCabeceraObs().substring(0, lcabecerapdd.get(i).getCabeceraObs().trim().length())));
                myTextPddObs = myTextPddObs.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextPddObs = myTextPddObs.replaceAll("\\s+$", ""); // Quitamos espacios derecha

                pedido = pedido + String.format("%-48s", "Observaciones: ") + "\n";
                pedido = pedido + String.format("%-48s", myTextPddObs) + "\n";
                pedido = pedido + separador;
            }
            //////////////////////////////////////////////////////////
            //pedido,fecha,
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String xfecha = "";
            try {
                Date datehora = sdf1.parse(String.valueOf(lcabecerapdd.get(i).getCabeceraFecha()));
                xfecha = sdf2.format(datehora);

            } catch (Exception e) {
                e.getMessage();
            }
            pedido = pedido + String.format("%-48s", "PEDIDO      FECHA     ") + "\n";
            pedido = pedido + String.format("%-48s", String.format("%07d", Integer.parseInt(String.valueOf(lcabecerapdd.get(i).getCabeceraPedido())))
                    + "    " + xfecha ) + "\n";

        }
        pedido = pedido + separador;

        //Cabecera Lineas
        pedido = pedido + String.format("%-48s","CANT   NOMBRE              OBSERVACIONES       ") + "\n";

        pedido = pedido + separador;
        boolean primer = true;
        String oldtipo_are="";
        // DATOS LINEAS PEDIDO
        for (int i = 0; i < llineadocumentopedidoprint.size(); i++) {
            if ((primer) || (!oldtipo_are.equals(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoTipo_are()))){
                primer = false;
                oldtipo_are = llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoTipo_are();
                pedido = pedido +  "\n";
                pedido = pedido + String.format("%-48s",llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoNombre_tipoare()) + "\n";
                pedido = pedido +  "\n";

            }

            String myTextCant = String.format("%1$,.2f", Float.parseFloat(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoCant()));
            myTextCant = myTextCant.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextCant = myTextCant.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextCant="";
            for (int ii = 0; ii < (6-myTextCant.length()); ii++) {
                newTextCant+=space01;
            }
            newTextCant +=myTextCant;
            ///////////////////////////////////////////////////////////
            int lennombre = llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoNombre().trim().length();
            String myTextNombre = String.format("%1$-19s", String.valueOf(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoNombre().substring(0,(lennombre>19 ? 19 : lennombre))));
            myTextNombre = myTextNombre.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextNombre = myTextNombre.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextNombre=myTextNombre;
            for (int ii = 0; ii < (19-myTextNombre.length()); ii++) {
                newTextNombre+=space01;
            }
            ///////////////////////////////////////////////////////////
            String myTextObs;
            int lenobs = llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoObs().trim().length();
            if (llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoObs().trim().length()>0) {
                myTextObs = String.format("%1$-23s", String.valueOf(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoObs().substring(0,(lenobs>23 ? 23 : lennombre))));
            }else{
                myTextObs = String.format("%1$-23s", "");
            }
            myTextObs = myTextObs.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextObs = myTextObs.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextObs = myTextObs;
            for (int ii = 0; ii < (23 - myTextObs.length()); ii++) {
                newTextObs += space01;
            }
            pedido = pedido + String.format("%-48s",newTextCant+" "+newTextNombre+" "+newTextObs) + "\n";

        }

        pedido = pedido + separador + "\n\n\n\n\n\n";
        ImprimirPedido();
        Toast.makeText(getActivity(), "ERROR NO UPDATE CABECERA ", Toast.LENGTH_SHORT).show();
        if (!optionprint) {
            comprobar_terminales();
/*            nPosition += 1;
            if (nPosition < terminalList.size()) {
                setImpresora(nPosition);
                urlPrint = Filtro.getUrl() + "/RellenaListaLPD.php";
                new LeerLineasLpd().execute(urlPrint, Integer.toString(nPosition));
            }else{
                setImpresora(ActividadPrincipal.cmbToolbarTerminal.getSelectedItemPosition());
            }
*/        }
    }
    public void crearPedidoPlato() {
        String space01 = new String(new char[01]).replace('\0', ' ');
        pedido = "";
        pedidocabecera="";
        String separador = "- - - - - - - - - - - - - - - - - - - - - - - - " + "\n";
        //obteniendo el encabezado del documento

        //fecha de emision del documento
        pedidocabecera = pedidocabecera + String.format("%-48s", getDate() + " " + getTime()) + "\n";

        // Datos EMPRESA
        for (int i = 0; i < lcabeceraempr.size(); i++) {
            //razon
            pedidocabecera = pedidocabecera + String.format("%-48s",String.valueOf(lcabeceraempr.get(i).getCabeceraRazon())) + "\n";

        }

        pedidocabecera = pedidocabecera + separador;

        // DATOS PEDIDO
        for (int i = 0; i < lcabecerapdd.size(); i++) {
            //LOCAL
            pedidocabecera = pedidocabecera + String.format("%-48s","Local: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_local())) + "\n";
            //Seccion
            //           pedidocabecera = pedidocabecera + String.format("%-48s","Seccion: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_seccion())) + "\n";
            //Caja
//            pedidocabecera = pedidocabecera + String.format("%-48s","Caja: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_caja())) + "\n";
            //Turno
//            pedidocabecera = pedidocabecera + String.format("%-48s","Turno: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_turno())) + "\n";
            //Mesa
            pedidocabecera = pedidocabecera + String.format("%-48s","Mesa: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_mesa())) + "\n";
            //Comensales
            pedidocabecera = pedidocabecera + String.format("%-48s","Comensales: " + String.valueOf(lcabecerapdd.get(i).getCabeceraComensales())) + "\n";
            //Empleado
//            pedidocabecera = pedidocabecera + String.format("%-48s","Empleado: " + String.valueOf(lcabecerapdd.get(i).getCabeceraNombre_empleado())) + "\n";
            // Terminal
//            pedidocabecera = pedidocabecera + String.format("%-48s","Terminal: " + terminalList.get(nPosition).getTerminalTerminal()) + "\n";

            pedidocabecera = pedidocabecera + separador;
            //Observaciones
            ///////////////////////////////////////////////////////////
            if (lcabecerapdd.get(i).getCabeceraObs().trim().length()>0) {
                String myTextPddObs = String.format("%1$-48s", String.valueOf(lcabecerapdd.get(i).getCabeceraObs().substring(0, lcabecerapdd.get(i).getCabeceraObs().trim().length())));
                myTextPddObs = myTextPddObs.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myTextPddObs = myTextPddObs.replaceAll("\\s+$", ""); // Quitamos espacios derecha

                pedidocabecera = pedidocabecera + String.format("%-48s", "Observaciones: ") + "\n";
                pedidocabecera = pedidocabecera + String.format("%-48s", myTextPddObs) + "\n";
                pedidocabecera = pedidocabecera + separador;
            }
            //////////////////////////////////////////////////////////
            //pedidocabecera,fecha,
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String xfecha = "";
            try {
                Date datehora = sdf1.parse(String.valueOf(lcabecerapdd.get(i).getCabeceraFecha()));
                xfecha = sdf2.format(datehora);

            } catch (Exception e) {
                e.getMessage();
            }
            pedidocabecera = pedidocabecera + String.format("%-48s", "PEDIDO      FECHA     ") + "\n";
            pedidocabecera = pedidocabecera + String.format("%-48s", String.format("%07d", Integer.parseInt(String.valueOf(lcabecerapdd.get(i).getCabeceraPedido())))
                    + "    " + xfecha ) + "\n";

        }
        pedidocabecera = pedidocabecera + separador;

        //Cabecera Lineas
        pedidocabecera = pedidocabecera + String.format("%-48s","CANT   NOMBRE              OBSERVACIONES       ") + "\n";

        pedidocabecera = pedidocabecera + separador;
        boolean primer = true;
        String oldtipoplato="";
        // DATOS LINEAS PEDIDO
        for (int i = 0; i < llineadocumentopedidoprint.size(); i++) {
            if ((primer) || (!oldtipoplato.equals(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoTipoPlato()))){
                primer = false;
                oldtipoplato = llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoTipoPlato();
                pedido = pedido +  "\n";
                pedido = pedido + String.format("%-24s",llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoNombre_plato()) + "\n";
                pedido = pedido +  "\n";

            }

            String myTextCant = String.format("%1$,.2f", Float.parseFloat(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoCant()));
            myTextCant = myTextCant.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextCant = myTextCant.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextCant="";
            for (int ii = 0; ii < (6-myTextCant.length()); ii++) {
                newTextCant+=space01;
            }
            newTextCant +=myTextCant;
            ///////////////////////////////////////////////////////////
            int lennombre = llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoNombre().trim().length();
            String myTextNombre = String.format("%1$-19s", String.valueOf(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoNombre().substring(0,(lennombre>19 ? 19 : lennombre))));
            myTextNombre = myTextNombre.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextNombre = myTextNombre.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextNombre=myTextNombre;
            for (int ii = 0; ii < (19-myTextNombre.length()); ii++) {
                newTextNombre+=space01;
            }
            ///////////////////////////////////////////////////////////
            String myTextObs;
            int lenobs = llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoObs().trim().length();
            if (llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoObs().trim().length()>0) {
                myTextObs = String.format("%1$-23s", String.valueOf(llineadocumentopedidoprint.get(i).getLineaDocumentoPedidoObs().substring(0,(lenobs>23 ? 23 : lenobs))));
            }else{
                myTextObs = String.format("%1$-23s", "");
            }
            myTextObs = myTextObs.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextObs = myTextObs.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextObs = myTextObs;
            for (int ii = 0; ii < (23 - myTextObs.length()); ii++) {
                newTextObs += space01;
            }
            pedido = pedido + String.format("%-48s",newTextCant+" "+newTextNombre+" "+newTextObs) + "\n";

        }

        pedidofinal = separador + "\n\n\n\n\n\n";
        ImprimirPedido();
///***        Toast.makeText(getActivity(), pedido, Toast.LENGTH_SHORT).show();
        if (!optionprint) {
            comprobar_terminales();
/*            nPosition += 1;
            if (nPosition < terminalList.size()) {
                setImpresora(nPosition);
                urlPrint = Filtro.getUrl() + "/RellenaListaLPD.php";
                new LeerLineasLpd().execute(urlPrint, Integer.toString(nPosition));
            }else{
                setImpresora(ActividadPrincipal.cmbToolbarTerminal.getSelectedItemPosition());
            }
*/        }
    }

    public void ImprimirPedido(){

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
            builder.addTextDouble(0,0);
            builder.addText(pedidocabecera);
            builder.addTextDouble(1,1);
            builder.addText(getBuilderText());
            builder.addTextDouble(0,0);
            builder.addText(pedidofinal);
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
        return pedido;
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
            if (pDialogEmpr.isShowing()) {
                pDialogEmpr.dismiss();

                if (result == 1) {
                    Log.i("Cabecera Empr", Integer.toString(lcabeceraempr.size()));

                    urlPrint = Filtro.getUrl() + "/CabeceraPDD.php";
                    new LeerCabeceraPdd().execute(urlPrint);

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
    public class LeerCabeceraPdd extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogPdd = new ProgressDialog(getActivity());
            pDialogPdd.setMessage("Leyendo Cabecera Pedido..");
            pDialogPdd.setIndeterminate(false);
            pDialogPdd.setCancelable(true);
            pDialogPdd.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if (xWhere.equals("")) {
                xWhere += " WHERE pdd.ID=" + Integer.toString(nId);
            } else {
                xWhere += " AND pdd.ID=" + Integer.toString(nId);
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
                    Log.i("Longitud Antes: ",Integer.toString(lcabecerapdd.size()));
                    for (Iterator<CabeceraPdd> it = lcabecerapdd.iterator(); it.hasNext();){
                        CabeceraPdd cabeceraPdd = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(lcabecerapdd.size()));

                    parseResultCabeceraPdd(response.toString());
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
            if (pDialogPdd.isShowing()) {
                pDialogPdd.dismiss();
                if (result == 1) {
                    Log.i("Cabecera Pdd", Integer.toString(lcabecerapdd.size()));
                    if (optionprint) {
                        urlPrint = Filtro.getUrl() + "/RellenaListaLPD.php";
                        new LeerLineasLpd().execute(urlPrint);
                    }else{
                        urlPrint = Filtro.getUrl()+"/RellenaListaPDDIVA.php";
                        new LeerLineasPddiva().execute(urlPrint);
                    }
                } else {
                    Log.e("Cabecera Pdd", "Failed to fetch data!");
                }
            }

        }
    }
    private void parseResultCabeceraPdd(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                CabeceraPdd cabeceraPddItem = new CabeceraPdd();
                cabeceraPddItem.setCabeceraPedido(post.optInt("PEDIDO"));
                cabeceraPddItem.setCabeceraFecha(post.optString("FECHA").trim());
                cabeceraPddItem.setCabeceraNombre_mesa(post.optString("NOMBRE_MESA").trim());
                cabeceraPddItem.setCabeceraNombre_sta(post.optString("NOMBRE_STA").trim());
                cabeceraPddItem.setCabeceraNombre_empleado(post.optString("NOMBRE_EMPLEADO").trim());
                cabeceraPddItem.setCabeceraNombre_caja(post.optString("NOMBRE_CAJA").trim());
                cabeceraPddItem.setCabeceraNombre_turno(post.optString("NOMBRE_TURNO").trim());
                cabeceraPddItem.setCabeceraNombre_seccion(post.optString("NOMBRE_SECCION"));
                cabeceraPddItem.setCabeceraNombre_local(post.optString("NOMBRE_LOCAL").trim());
                cabeceraPddItem.setCabeceraImagen_local(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_LOCAL").trim());
                cabeceraPddItem.setCabeceraObs(post.optString("OBS").trim());
                cabeceraPddItem.setCabeceraIvaincluido(post.optInt("IVAINCLUIDO"));
                cabeceraPddItem.setCabeceraComensales(post.optInt("COMENSALES"));
                lcabecerapdd.add(cabeceraPddItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class LeerLineasLpd extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogLpd = new ProgressDialog(getActivity());
            pDialogLpd.setMessage("Leyendo Lineas Pedido..");
            pDialogLpd.setIndeterminate(false);
            pDialogLpd.setCancelable(true);
            pDialogLpd.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lpd.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND lpd.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lpd.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND lpd.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lpd.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND lpd.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lpd.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND lpd.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lpd.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND lpd.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            if(!(Filtro.getPedido()==0)) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE lpd.PEDIDO=" + nPedido;
                } else {
                    xWhere += " AND lpd.PEDIDO=" + nPedido;
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE lpd.SWPEDIDO=1";
            } else {
                xWhere += " AND lpd.SWPEDIDO=1";
            }


            if (!optionprint){
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.TERMINAL='" + terminalList.get(Integer.parseInt(params[1])).getTerminalTerminal() + "'";
                } else {
                    xWhere += " AND are.TERMINAL='" + terminalList.get(Integer.parseInt(params[1])).getTerminalTerminal() + "'";
                }
                Log.i("Print LPD", terminalList.get(Integer.parseInt(params[1])).getTerminalTerminal());

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
                    Log.i("Longitud Antes: ",Integer.toString(llineadocumentopedidoprint.size()));
                    for (Iterator<LineaDocumentoPedido> it = llineadocumentopedidoprint.iterator(); it.hasNext();){
                        LineaDocumentoPedido lineadocumentopedido = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(llineadocumentopedidoprint.size()));

                    parseResultLpd(response.toString());
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
            if (pDialogLpd.isShowing()) {
                pDialogLpd.dismiss();

                if (result == 1) {
                    Log.i("Lineas LPD", Integer.toString(llineadocumentopedidoprint.size()));
                    if (optionprint) {
                        urlPrint = Filtro.getUrl() + "/RellenaListaPDDIVA.php";
                        new LeerLineasPddiva().execute(urlPrint);
                    }else{

                        if (llineadocumentopedidoprint.size()>0){
                            urlPrint = Filtro.getUrl() + "/CabeceraEMPR.php";
                            new LeerCabeceraEmpr().execute(urlPrint);
                        }else{
                            comprobar_terminales();
                        }
                    }
                } else {
                    Log.e("Lineas LPD", "Failed to fetch data!");
                }
            }
        }
    }

    private void comprobar_terminales() {
        nPosition += 1;
        if (nPosition < terminalList.size()) {
            setImpresora(nPosition);
            urlPrint = Filtro.getUrl() + "/RellenaListaLPDprintsecplato.php";
            new LeerLineasLpd().execute(urlPrint, Integer.toString(nPosition));
        }else{
            setImpresora(ActividadPrincipal.cmbToolbarTerminal.getSelectedItemPosition());
        }

    }


    private void parseResultLpd(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                LineaDocumentoPedido lineadocumentopedidoItem = new LineaDocumentoPedido();
                lineadocumentopedidoItem.setLineaDocumentoPedidoId(post.optInt("ID"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoNombre(post.optString("NOMBRE"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoArticulo(post.optString("ARTICULO"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoTiva_id(post.optInt("TIVA_ID"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoCant(post.optString("CANT"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoPreu(post.optString("PREU"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoImporte(post.optString("IMPORTE"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                lineadocumentopedidoItem.setLineaDocumentoPedidoTipo_iva(post.optString("TIPO_IVA"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoObs(post.optString("OBS"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoTipo_are(post.optString("TIPO_ARE"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoNombre_tipoare(post.optString("NOMBRE_TIPOARE"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoTipoPlato(post.optString("TIPOPLATO"));
                lineadocumentopedidoItem.setLineaDocumentoPedidoNombre_plato(post.optString("NOMBRE_PLATO"));
                llineadocumentopedidoprint.add(lineadocumentopedidoItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class LeerLineasPddiva extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogPddiva = new ProgressDialog(getActivity());
            pDialogPddiva.setMessage("Leyendo Lineas Iva..");
            pDialogPddiva.setIndeterminate(false);
            pDialogPddiva.setCancelable(true);
            pDialogPddiva.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pddiva.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND pddiva.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pddiva.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND pddiva.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pddiva.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND pddiva.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pddiva.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND pddiva.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pddiva.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND pddiva.CAJA='" + Filtro.getCaja() + "'";
                }
            }

            if(!(Filtro.getPedido()==0)) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pddiva.PEDIDO=" + nPedido;
                } else {
                    xWhere += " AND pddiva.PEDIDO=" + nPedido;
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
                    Log.i("Longitud Antes: ",Integer.toString(ldocumentopedidoiva.size()));
                    for (Iterator<DocumentoPedidoIva> it = ldocumentopedidoiva.iterator(); it.hasNext();){
                        DocumentoPedidoIva ldocumentopedidoiva = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(ldocumentopedidoiva.size()));

                    parseResultPddiva(response.toString());
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
            if (pDialogPddiva.isShowing()) {
                pDialogPddiva.dismiss();
                if (result == 1) {
                    Log.i("Lineas PDDIVA", Integer.toString(ldocumentopedidoiva.size()));
                    crearPedidoPlato();
                } else {
                    Log.e("Lineas PDDIVA", "Failed to fetch data!");
                }
            }
        }
    }

    private void parseResultPddiva(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                DocumentoPedidoIva documentopedidoivaItem = new DocumentoPedidoIva();
                documentopedidoivaItem.setDocumentoPedidoIvaImp_base(post.optString("IMP_BASE"));
                documentopedidoivaItem.setDocumentoPedidoIvaImp_iva(post.optString("IMP_IVA"));
                documentopedidoivaItem.setDocumentoPedidoIvaImp_total(post.optString("IMP_TOTAL"));
                documentopedidoivaItem.setDocumentoPedidoIvaTipo_iva(post.optString("TIPO_IVA"));
                ldocumentopedidoiva.add(documentopedidoivaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private static class MySerialExecutor extends SerialExecutor {
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
            String xTabla= PrintTable;

            switch (xTabla){
                case "empr":
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
                    break;
                case "pddiva":
                    if(!(Filtro.getGrupo().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pddiva.GRUPO='" + Filtro.getGrupo() + "'";
                        } else {
                            xWhere += " AND pddiva.GRUPO='" + Filtro.getGrupo() + "'";
                        }
                    }
                    if(!(Filtro.getEmpresa().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pddiva.EMPRESA='" + Filtro.getEmpresa() + "'";
                        } else {
                            xWhere += " AND pddiva.EMPRESA='" + Filtro.getEmpresa() + "'";
                        }
                    }
                    if(!(Filtro.getLocal().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pddiva.LOCAL='" + Filtro.getLocal() + "'";
                        } else {
                            xWhere += " AND pddiva.LOCAL='" + Filtro.getLocal() + "'";
                        }
                    }
                    if(!(Filtro.getSeccion().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pddiva.SECCION='" + Filtro.getSeccion() + "'";
                        } else {
                            xWhere += " AND pddiva.SECCION='" + Filtro.getSeccion() + "'";
                        }
                    }
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pddiva.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND pddiva.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }

                    if (xWhere.equals("")) {
                        xWhere += " WHERE pddiva.PEDIDO=" + nPedido;
                    } else {
                        xWhere += " AND pddiva.PEDIDO=" + nPedido;
                    }

                    break;
                case "pdd":
                    if (xWhere.equals("")) {
                        xWhere += " WHERE pdd.ID=" + Integer.toString(nId);
                    } else {
                        xWhere += " AND pdd.ID=" + Integer.toString(nId);
                    }
                    break;
                case "lpd":
                    if(!(Filtro.getGrupo().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE lpd.GRUPO='" + Filtro.getGrupo() + "'";
                        } else {
                            xWhere += " AND lpd.GRUPO='" + Filtro.getGrupo() + "'";
                        }
                    }
                    if(!(Filtro.getEmpresa().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE lpd.EMPRESA='" + Filtro.getEmpresa() + "'";
                        } else {
                            xWhere += " AND lpd.EMPRESA='" + Filtro.getEmpresa() + "'";
                        }
                    }
                    if(!(Filtro.getLocal().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE lpd.LOCAL='" + Filtro.getLocal() + "'";
                        } else {
                            xWhere += " AND lpd.LOCAL='" + Filtro.getLocal() + "'";
                        }
                    }
                    if(!(Filtro.getSeccion().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE lpd.SECCION='" + Filtro.getSeccion() + "'";
                        } else {
                            xWhere += " AND lpd.SECCION='" + Filtro.getSeccion() + "'";
                        }
                    }
                    if(!(Filtro.getCaja().equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE lpd.CAJA='" + Filtro.getCaja() + "'";
                        } else {
                            xWhere += " AND lpd.CAJA='" + Filtro.getCaja() + "'";
                        }
                    }
                    if (xWhere.equals("")) {
                        xWhere += " WHERE lpd.PEDIDO=" + nPedido;
                    } else {
                        xWhere += " AND lpd.PEDIDO=" + nPedido;
                    }

                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.TERMINAL='" + terminalList.get(nPosition).getTerminalTerminal() + "'";
                    } else {
                        xWhere += " AND are.TERMINAL='" + terminalList.get(nPosition).getTerminalTerminal() + "'";
                    }
                    Log.i("Print LPD", terminalList.get(nPosition).getTerminalTerminal());
                    break;
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Count Lista",cSql);

            try {
                ContentValues values = new ContentValues();
                values.put("filtro", cSql);
                jsonParserNew = new JSONParserNew();

                JSONObject json = jsonParserNew.makeHttpRequest(
                        urlPrint, "POST", values);


                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received LineaDocumentoFactura details
                    JSONArray posts = json.getJSONArray("posts"); // JSON Array

                    Log.i("Longitud Datos: ", Integer.toString(posts.length()));

                    for (int ii = 0; ii < posts.length(); ii++) {
                        JSONObject post = posts.optJSONObject(ii);
                        switch (PrintTable){
                            case "lpd":
                                LineaDocumentoPedido lineadocumentopedidoItem = new LineaDocumentoPedido();
                                lineadocumentopedidoItem.setLineaDocumentoPedidoId(post.optInt("ID"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoNombre(post.optString("NOMBRE"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoArticulo(post.optString("ARTICULO"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoTiva_id(post.optInt("TIVA_ID"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoCant(post.optString("CANT"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoPreu(post.optString("PREU"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoImporte(post.optString("IMPORTE"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                                lineadocumentopedidoItem.setLineaDocumentoPedidoTipo_iva(post.optString("TIPO_IVA"));
                                lineadocumentopedidoItem.setLineaDocumentoPedidoObs(post.optString("OBS"));
                                llineadocumentopedidoprint.add(lineadocumentopedidoItem);
                                break;
                            case "empr":
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
                                break;
                            case "pdd":
                                CabeceraPdd cabeceraPddItem = new CabeceraPdd();
                                cabeceraPddItem.setCabeceraPedido(post.optInt("PEDIDO"));
                                cabeceraPddItem.setCabeceraFecha(post.optString("FECHA").trim());
                                cabeceraPddItem.setCabeceraNombre_mesa(post.optString("NOMBRE_MESA").trim());
                                cabeceraPddItem.setCabeceraNombre_sta(post.optString("NOMBRE_STA").trim());
                                cabeceraPddItem.setCabeceraNombre_empleado(post.optString("NOMBRE_EMPLEADO").trim());
                                cabeceraPddItem.setCabeceraNombre_caja(post.optString("NOMBRE_CAJA").trim());
                                cabeceraPddItem.setCabeceraNombre_turno(post.optString("NOMBRE_TURNO").trim());
                                cabeceraPddItem.setCabeceraNombre_seccion(post.optString("NOMBRE_SECCION"));
                                cabeceraPddItem.setCabeceraNombre_local(post.optString("NOMBRE_LOCAL").trim());
                                cabeceraPddItem.setCabeceraImagen_local(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_LOCAL").trim());
                                cabeceraPddItem.setCabeceraObs(post.optString("OBS").trim());
                                cabeceraPddItem.setCabeceraIvaincluido(post.optInt("IVAINCLUIDO"));
                                lcabecerapdd.add(cabeceraPddItem);
                                break;
                            case "pddiva":
                                DocumentoPedidoIva documentopedidoivaItem = new DocumentoPedidoIva();
                                documentopedidoivaItem.setDocumentoPedidoIvaImp_base(post.optString("IMP_BASE"));
                                documentopedidoivaItem.setDocumentoPedidoIvaImp_iva(post.optString("IMP_IVA"));
                                documentopedidoivaItem.setDocumentoPedidoIvaImp_total(post.optString("IMP_TOTAL"));
                                documentopedidoivaItem.setDocumentoPedidoIvaTipo_iva(post.optString("TIPO_IVA"));
                                ldocumentopedidoiva.add(documentopedidoivaItem);
                                break;
                        }
                    }


                } else {
                    // LineaDocumentoFactura with pid not found
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



}
