package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.print.PrintManager;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.ImageAndTextContainer;
import tpv.cirer.com.marivent.herramientas.StringUtil;
import tpv.cirer.com.marivent.modelo.CabeceraEmpr;
import tpv.cirer.com.marivent.modelo.CabeceraFtp;
import tpv.cirer.com.marivent.modelo.DocumentoFacturaIva;
import tpv.cirer.com.marivent.modelo.LineaDocumentoFactura;

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

/**
 * Created by JUAN on 06/12/2016.
 */

public class PrintTicketFragment extends Fragment implements ImageAndTextContainer {
    public static List<CabeceraEmpr> lcabeceraempr;
    public static List<CabeceraFtp> lcabeceraftp;
    public static List<LineaDocumentoFactura> llineadocumentofactura;
    public static List<DocumentoFacturaIva> ldocumentofacturaiva;
    public String url;
    ProgressDialog pDialogEmpr,pDialogFtp,pDialogLft,pDialogFtpiva;
    int nFactura;
    int nId;
    String sSerie;

    TextView tvticket;
    LinearLayout rootViewPrintTicket;

    private static PrintTicketFragment PrintTicket = null;

    public static PrintTicketFragment newInstance(int id, String serie, int factura) {
        PrintTicketFragment PrintTicket = new PrintTicketFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("ID", id);
        args.putString("SERIE", serie);
        args.putInt("FACTURA", factura);
        PrintTicket.setArguments(args);
        return PrintTicket;
    }

    public static PrintTicketFragment getInstance(){
        if(PrintTicket == null){
            PrintTicket = new PrintTicketFragment();
        }
        return PrintTicket;
    }

    public PrintTicketFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lcabeceraempr = new ArrayList<CabeceraEmpr>();
        lcabeceraftp = new ArrayList<CabeceraFtp>();
        llineadocumentofactura = new ArrayList<LineaDocumentoFactura>();
        ldocumentofacturaiva = new ArrayList<DocumentoFacturaIva>();

        nFactura = getArguments().getInt("FACTURA", 0);
        nId = getArguments().getInt("ID", 0);
        sSerie = getArguments().getString("SERIE", "");

        url = Filtro.getUrl()+"/CabeceraEMPR.php";
        new LeerCabeceraEmpr().execute(url);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootViewPrintTicket = (LinearLayout) inflater.inflate(R.layout.fragment_print_ticket,container, false);

//        final View rootView = inflater.inflate(R.layout.fragment_print_ticket, container, false);

        tvticket = (TextView) rootViewPrintTicket.findViewById(R.id.tvticket);

        // Wire up some button handlers
        rootViewPrintTicket.findViewById(R.id.print_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintHelper printHelper = new PrintHelper(getActivity());
                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                // Get the image
                Bitmap image = getImage();
                if (image != null) {
                    // Send it to the print helper
                    printHelper.printBitmap("PrintTicket", image);
                }

            }
        });

        final ImageAndTextContainer imageAndTextContainer = this;

        rootViewPrintTicket.findViewById(R.id.print_page_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a PrintDocumentAdapter
                PrintTicketPrintDocumentAdapter adapter = new PrintTicketPrintDocumentAdapter(imageAndTextContainer, getActivity());
                // Get the print manager from the context
                PrintManager printManager = (PrintManager)getActivity().getSystemService(Context.PRINT_SERVICE);
                // And print the document
                printManager.print("PrintTicket", adapter, null);
            }
        });

        return rootViewPrintTicket;
    }

    @Override
    public String getText() {
        TextView textView = (TextView) getView().findViewById(R.id.tvticket);
        return textView.getText().toString();
    }

    @Override
    public Bitmap getImage() {
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
        Bitmap image = null;
        // Get the image
        if ((imageView.getDrawable()) != null) {
            // Send it to the print helper
            image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
        return image;
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
                url = Filtro.getUrl()+"/CabeceraFTP.php";
                new LeerCabeceraFtp().execute(url);
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
                url = Filtro.getUrl()+"/RellenaListaLFT.php";
                new LeerLineasLft().execute(url);
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
                    Log.i("Longitud Antes: ",Integer.toString(llineadocumentofactura.size()));
                    for (Iterator<LineaDocumentoFactura> it = llineadocumentofactura.iterator(); it.hasNext();){
                        LineaDocumentoFactura lineadocumentofactura = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(llineadocumentofactura.size()));

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
                Log.i("Lineas LFT", Integer.toString(llineadocumentofactura.size()));
                url = Filtro.getUrl()+"/RellenaListaFTPIVA.php";
                new LeerLineasFtpiva().execute(url);
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
                llineadocumentofactura.add(lineadocumentofacturaItem);
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
    public void crearTicket(){
        String ticket =  "";
        String separador = "- - - - - - - - - -- - - - - - - - - - -" + "\n";
        //obteniendo el encabezado del documento

        //fecha de emision del documento
        ticket = ticket + String.format("%-40s", getDate() + " " + getTime()) + "\n";

        // Datos EMPRESA
        for (int i = 0; i < lcabeceraempr.size(); i++) {
            //razon
            ticket = ticket + StringUtil.align(String.valueOf(lcabeceraempr.get(i).getCabeceraRazon()), 40, ' ',0) + "\n";

            //cif
            ticket = ticket + StringUtil.align(String.valueOf(lcabeceraempr.get(i).getCabeceraCif()), 40, ' ',0) + "\n";

            //domicilio
            String domicilio = String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_calle());
            domicilio += ",";
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraNumero()).length()>0) {
                domicilio += String.valueOf(lcabeceraempr.get(i).getCabeceraNumero());
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraBloque()).length()>0) {
                domicilio += (" "+String.valueOf(lcabeceraempr.get(i).getCabeceraBloque()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraEscalera()).length()>0) {
                domicilio += (" "+String.valueOf(lcabeceraempr.get(i).getCabeceraEscalera()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraPiso()).length()>0) {
                domicilio += (" "+String.valueOf(lcabeceraempr.get(i).getCabeceraPiso()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraPuerta()).length()>0) {
                domicilio += (" "+String.valueOf(lcabeceraempr.get(i).getCabeceraPuerta()));
            }
            if (String.valueOf(lcabeceraempr.get(i).getCabeceraAmpliacion()).length()>0) {
                domicilio += (" "+String.valueOf(lcabeceraempr.get(i).getCabeceraAmpliacion()));
            }
            ticket = ticket + StringUtil.align(domicilio, 40, ' ',0) + "\n";

            //poblacion
            String poblacion = String.valueOf(lcabeceraempr.get(i).getCabeceraCod_poblacion());
            poblacion += (" "+String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_poblacion()));
            ticket = ticket + StringUtil.align(poblacion, 40, ' ',0) + "\n";

            //provincia
            ticket = ticket + StringUtil.align(String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_provincia()), 40, ' ',0) + "\n";
            //pais
            ticket = ticket + StringUtil.align(String.valueOf(lcabeceraempr.get(i).getCabeceraNombre_pais()), 40, ' ',0) + "\n";

        }

        ticket = ticket + separador;

        // DATOS FACTURA
        for (int i = 0; i < lcabeceraftp.size(); i++) {
            //LOCAL
            ticket = ticket + StringUtil.align("Local: "+String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_local()), 40, ' ',0) + "\n";
            //Seccion
            ticket = ticket + StringUtil.align("Seccion: "+String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_seccion()), 40, ' ',0) + "\n";
            //Caja
            ticket = ticket + StringUtil.align("Caja: "+String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_caja()), 40, ' ',0) + "\n";
            //Turno
            ticket = ticket + StringUtil.align("Turno: "+String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_turno()), 40, ' ',0) + "\n";
            //Mesa
            ticket = ticket + StringUtil.align("Mesa: "+String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_mesa()), 40, ' ',0) + "\n";
            //Empleado
            ticket = ticket + StringUtil.align("Empleado: "+String.valueOf(lcabeceraftp.get(i).getCabeceraNombre_empleado()), 40, ' ',0) + "\n";

            ticket = ticket + separador;

            //Serie,factura,fecha e iva,
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String xfecha="";
            try{
                Date datehora = sdf1.parse(String.valueOf(lcabeceraftp.get(i).getCabeceraFecha()));
                xfecha = sdf2.format(datehora);

            } catch (Exception e) {
                e.getMessage();
            }
            String ivaincluido = (0 != lcabeceraftp.get(i).getCabeceraIvaincluido() ? "SI" : "NO");
            ticket =  ticket + String.format("%-40s", "SERIE  FACTURA    FECHA   IVA INCLUIDO") + "\n";
            ticket =  ticket + String.format("%-40s", "  " +String.valueOf(lcabeceraftp.get(i).getCabeceraSerie())
                    + "    " + String.format("%07d", Integer.parseInt(String.valueOf(lcabeceraftp.get(i).getCabeceraFactura())))
                    + "    " + xfecha + "    " + ivaincluido) + "\n";

        }

        //Cabecera Lineas
        ticket = ticket + StringUtil.align("CANT  NOMBRE          PREU  IMPORTE  IVA", 40, ' ',0) + "\n";

        // DATOS LINEAS FACTURA
        for (int i = 0; i < llineadocumentofactura.size(); i++) {
            ticket =  ticket + String.format("%-40s",
                    String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(llineadocumentofactura.get(i).getLineaDocumentoFacturaCant())))
                            + " "+String.format("%1$-16s",String.valueOf(llineadocumentofactura.get(i).getLineaDocumentoFacturaNombre()))
                            +String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(llineadocumentofactura.get(i).getLineaDocumentoFacturaPreu())))
                            +String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(llineadocumentofactura.get(i).getLineaDocumentoFacturaImporte())))
                            +String.format("%1$-5s",String.format("%1$,.2f", Float.parseFloat(llineadocumentofactura.get(i).getLineaDocumentoFacturaTipo_iva())))
            ) + "\n";

        }
        ticket = ticket + separador;
        //Cabecera Lineas IVA
        ticket = ticket + StringUtil.align("          BASE  TIPO    CUOTA     TOTAL", 40, ' ',0) + "\n";
        float nbase=0;
        float ncuota=0;
        float ntotal=0;
        // DATOS  FACTURA IVA
        for (int i = 0; i < ldocumentofacturaiva.size(); i++) {
            ticket =  ticket + String.format("%-40s","          "
                    +String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_base())))
                    +String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaTipo_iva())))
                    +String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_iva())))
                    +String.format("%1$-6s",String.format("%1$,.2f", Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_total())))
            ) + "\n";
            nbase +=Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_base());
            ncuota +=Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_iva());
            ntotal +=Float.parseFloat(ldocumentofacturaiva.get(i).getDocumentoFacturaIvaImp_total());

        }
        ticket =  ticket + String.format("%-40s","Total...  "
                +String.format("%1$-6s",String.format("%1$,.2f", nbase))
                +"      "
                +String.format("%1$-6s",String.format("%1$,.2f", ncuota)))
                +String.format("%1$-6s",String.format("%1$,.2f", ntotal)
        ) + "\n";
        ticket = ticket + separador;

        ticket = ticket + StringUtil.align("GRACIAS POR SU PREFERENCIA!", 40, ' ',0) + "\n";

        ticket = ticket + separador+"\n\n\n\n\n\n";

        tvticket.setText(ticket);
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

}
