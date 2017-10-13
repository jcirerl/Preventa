package tpv.cirer.com.restaurante.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tpv.cirer.com.restaurante.BuildConfig;
import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.conexion_http_post.JSONParserNew;
import tpv.cirer.com.restaurante.herramientas.DividerItemDecoration;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.TaskHelper;
import tpv.cirer.com.restaurante.herramientas.Utils;
import tpv.cirer.com.restaurante.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.restaurante.modelo.LineaDocumentoFactura;
import tpv.cirer.com.restaurante.print.PrintTicket;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.getPalabras;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.iconCarrito;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 31/08/2017.
 */

public class FragmentoDivisionLineaDocumentoFactura extends Fragment {
    public static final String TAG = "Lista LFT";
    private static String url_update_cabecera;
    boolean mIsRestoredFromBackstack;
    boolean traspasoticket;
    // JSON parser class
    JSONParserNew jsonParserNew = new JSONParserNew();
    // url to update Factura
    private static final String url_ticket_a_ticket = Filtro.getUrl()+"/invoice_ftp_ftp.php";

    private String pid;
    ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_FACTURA = "factura";
    String TAG_SALDO = "saldo";

    public static List<LineaDocumentoFactura> llineadocumentofactura;
    private String url = Filtro.getUrl()+"/RellenaListaLFT.php";

    int nFactura;
    int nId;
    int nFacturaImprimir;
    String sSerie;
    String cEstado;
    String tabla;
    String lintabla;
    String cTotal;
    String cObs;

    public static RecyclerView recViewdivisionlineadocumentofactura;
    public static AdaptadorLineaDocumentoFacturaHeaderAsus adaptadordivisionlineadocumentofacturaasus;
    public static AdaptadorDivisionLineaDocumentoFacturaHeaderSony adaptadordivisionlineadocumentofacturasony;
    View rootViewdivisionlineadocumentofactura;
    FloatingActionButton btnFacturar,btnCobro;

    private static FragmentoDivisionLineaDocumentoFactura DivisionLineaDocumentoFactura = null;

    public static FragmentoDivisionLineaDocumentoFactura newInstance(int id, String estado, String serie, int factura, String total, String obs) {
        FragmentoDivisionLineaDocumentoFactura LineaDocumentoFactura = new FragmentoDivisionLineaDocumentoFactura();
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

    public static FragmentoDivisionLineaDocumentoFactura getInstance(){
        if(DivisionLineaDocumentoFactura == null){
            DivisionLineaDocumentoFactura = new FragmentoDivisionLineaDocumentoFactura();
        }
        return DivisionLineaDocumentoFactura;
    }

    public FragmentoDivisionLineaDocumentoFactura() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mIsRestoredFromBackstack = false;
        traspasoticket = false;
        nFacturaImprimir = 0;

        Filtro.setTag_fragment("FragmentoLineaDocumentoFactura");
        llineadocumentofactura = new ArrayList<LineaDocumentoFactura>();

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
        if (rootViewdivisionlineadocumentofactura == null) {
/*             setHasOptionsMenu(true);*/
            rootViewdivisionlineadocumentofactura = inflater.inflate(R.layout.lista_print_factura_division, container, false);
            recViewdivisionlineadocumentofactura = (RecyclerView) rootViewdivisionlineadocumentofactura.findViewById(R.id.RecView);
            //     recViewdivisionlineadocumentofacturas.setHasFixedSize(true);

            // 2. set layoutManger
            recViewdivisionlineadocumentofactura.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    adaptadordivisionlineadocumentofacturaasus = new AdaptadorLineaDocumentoFacturaHeaderAsus(getActivity(),llineadocumentofactura,cEstado);
                    break;
                case 1:
                    adaptadordivisionlineadocumentofacturasony = new AdaptadorDivisionLineaDocumentoFacturaHeaderSony(getActivity(),llineadocumentofactura,cEstado);
                    break;
                case 2:
                    adaptadordivisionlineadocumentofacturasony = new AdaptadorDivisionLineaDocumentoFacturaHeaderSony(getActivity(),llineadocumentofactura,cEstado);
                    break;
            }
            // 4. set adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    recViewdivisionlineadocumentofactura.setAdapter(adaptadordivisionlineadocumentofacturaasus);
                    break;
                case 1:
                    recViewdivisionlineadocumentofactura.setAdapter(adaptadordivisionlineadocumentofacturasony);
                    break;
                case 2:
                    recViewdivisionlineadocumentofactura.setAdapter(adaptadordivisionlineadocumentofacturasony);
                    break;
            }

            // 5. set item animator to DefaultAnimator
            recViewdivisionlineadocumentofactura.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            recViewdivisionlineadocumentofactura.addItemDecoration(itemDecoration);

            btnCobro = (FloatingActionButton) rootViewdivisionlineadocumentofactura.findViewById(R.id.btnCobro);
            btnCobro.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.visacurved));
            btnCobro.setAlpha(0.3f); // COLOR APAGADO BOTON COBRO
            btnCobro.setEnabled(false);

            btnCobro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    if (!((ActividadPrincipal) getActivity()).getCruge("action_ftp_update")) {
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    } else {
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

                        ((ActividadPrincipal)getActivity()).dialog_cobro(Integer.parseInt(pid),sSerie,String.valueOf(Filtro.getFactura()),cTotal,cObs,"division");
/*                        Fragment cobrofragment = null;
                        cobrofragment = EditCobroFacturaFragment.newInstance(pid, sSerie, Integer.toString(Filtro.getFactura()), "lista");
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.lista_coordinator, cobrofragment, cobrofragment.getClass().getName());
                        ft.addToBackStack(null);
                        ft.commit();
*/                    }
                }
                          });
            btnFacturar = (FloatingActionButton)rootViewdivisionlineadocumentofactura.findViewById(R.id.btnFacturar);
            btnFacturar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.invoice48));
            btnFacturar.setAlpha(1.0f); // COLOR BRILLANTE BOTON FACTURAR
            btnFacturar.setEnabled(true);
            btnFacturar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_ftp_create")){
                        Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                    }else {
                        String cValor = Filtro.getTotaldivision();
                        cValor = cValor.replace(Html.fromHtml("&nbsp;"),"");
                        cValor = cValor.replace(".","");
                        cValor = cValor.replace(",",".");
                        final double totaldivision = Double.parseDouble(cValor.toString().trim());
                        if (totaldivision>0) {
                            new TraspasoTicketTicket().execute();
                            Toast.makeText(getActivity(), getPalabras("Facturar") + " " + getPalabras("Ticket"), Toast.LENGTH_SHORT).show();
//                        Snackbar.make(view, getPalabras("Facturar")+" "+ getPalabras("Ticket"), Snackbar.LENGTH_SHORT).show();
                        }else{
                           Snackbar.make(view, "ERROR "+getPalabras("Total")+" "+ getPalabras("Cero"), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }

        return rootViewdivisionlineadocumentofactura;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Filtro.getCobroDesdeFactura()==1) {
///            cEstado = "CLOSE"+" "+getPalabras("Cobro");
            Filtro.setFactura(nFactura);
            Filtro.setCabecera(false);
        }
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

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

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
                    for (Iterator<LineaDocumentoFactura> it = llineadocumentofactura.iterator(); it.hasNext();){
                        LineaDocumentoFactura lineadocumentofactura = it.next();
                        it.remove();
                    }
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
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        Log.i("ADAPTADOR LFT", Integer.toString(adaptadordivisionlineadocumentofacturaasus.getItemCount()));
                        adaptadordivisionlineadocumentofacturaasus.notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("ADAPTADOR LFT", Integer.toString(adaptadordivisionlineadocumentofacturasony.getItemCount()));
                        adaptadordivisionlineadocumentofacturasony.notifyDataSetChanged();
                        break;
                    case 2:
                        Log.i("ADAPTADOR LFT", Integer.toString(adaptadordivisionlineadocumentofacturasony.getItemCount()));
                        adaptadordivisionlineadocumentofacturasony.notifyDataSetChanged();
                        break;
                }



                if (!Filtro.getCabecera()) {
//                    ((ActividadPrincipal)getActivity()).setCabecera(getPalabras("Factura")+": "+Integer.toString(Filtro.getFactura())+" "+getPalabras(cEstado),Float.parseFloat("0.00"),Filtro.getFactura());

                    TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "0");
                    Filtro.setCabecera(true);
                }

            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
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
            Filtro.setTotaldivision("0.00");
            float total=0;
            float importe;
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                LineaDocumentoFactura lineadocumentofacturaItem = new LineaDocumentoFactura();
                lineadocumentofacturaItem.setLineaDocumentoFacturaId(post.optInt("ID"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaNombre(post.optString("NOMBRE"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaArticulo(post.optString("ARTICULO"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaTiva_id(post.optInt("TIVA_ID"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaCant(post.optString("CANT"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaCantRestar(post.optString("CANTRESTAR"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaPreu(post.optString("PREU"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaImporte(post.optString("IMPORTE"));
                lineadocumentofacturaItem.setLineaDocumentoFacturaUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                llineadocumentofactura.add(lineadocumentofacturaItem);
                importe = Float.parseFloat(post.optString("CANTRESTAR"))*Float.parseFloat(post.optString("PREU"));
                total = total + importe;
            }
            Filtro.setTotaldivision(String.format("%1$,.2f", total));

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
/*            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getPalabras("Calcula")+" "+ getPalabras("Cabecera")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
*/        }

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
//            pDialog.dismiss();
            if(traspasoticket) {
                PrintTicket printticket = new PrintTicket(getActivity(), Filtro.getFactura(), sSerie);
                printticket.iniciarTicket();

                btnFacturar.setAlpha(0.3f); // COLOR APAGADO BOTON FACTURAR
                btnFacturar.setEnabled(false);
                btnCobro.setAlpha(1.0f); // COLOR BRILLANTE BOTON COBRO
                btnCobro.setEnabled(true);

                cEstado = "OPEN PRINT";
                Filtro.setCabecera(false);
                traspasoticket=false;
                ((ActividadPrincipal)getActivity()).setCabecera(getPalabras("Factura")+": "+Integer.toString(Filtro.getFactura())+" "+getPalabras(cEstado),Float.parseFloat("0.00"),Filtro.getFactura());

                new AsyncHttpTaskLineaDocumentoFactura().execute(url);

                traspasoticket=false;
            }
        }

    }
    class TraspasoTicketTicket extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getPalabras("Traspaso")+" "+getPalabras("Ticket")+" "+getPalabras("Ticket")+"..");
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

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                long maxDate = currentDate.getTime().getTime(); // Twice!

                // Building Parameters
                ContentValues values = new ContentValues();
                values.put("pid",nId);
                values.put("grupo", Filtro.getGrupo());
                values.put("empresa", Filtro.getEmpresa());
                values.put("local",Filtro.getLocal());
                values.put("seccion",Filtro.getSeccion());
                values.put("caja",Filtro.getCaja());
                values.put("serie",Filtro.getSerie());
                values.put("factura",Long.toString(maxDate));
                values.put("oldserie",sSerie);
                values.put("oldfactura",nFactura);
                values.put("estado",lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA());
                values.put("t_fra",lparam.get(0).getDEFAULT_TIPO_COBRO_OPEN_FACTURA());
                values.put("updated", dateNow);
                values.put("creado", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());

                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParserNew.makeHttpRequest(url_ticket_a_ticket,
                        "POST", values);

                // check log cat fro response
                //            Log.d("Create Response", json.toString());

                // check for success tag

                success = json.getInt(TAG_SUCCESS);
                if (success==1) {
                    Filtro.setFactura(json.getInt(TAG_FACTURA));
                    pid = json.getString("id");
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
                traspasoticket=true;
                TaskHelper.execute(new CalculaCabecera(), "ftp", "lft", "0");
           } else {
                Toast.makeText(getActivity(), "ERROR NO "+getPalabras("Traspaso")+" "+getPalabras("Ticket")+" "+getPalabras("Ticket"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }

        }

    }

}
