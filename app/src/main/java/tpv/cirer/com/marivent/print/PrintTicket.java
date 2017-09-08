package tpv.cirer.com.marivent.print;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.StringUtil;
import tpv.cirer.com.marivent.modelo.CabeceraEmpr;
import tpv.cirer.com.marivent.modelo.CabeceraFtp;
import tpv.cirer.com.marivent.modelo.DocumentoFacturaIva;
import tpv.cirer.com.marivent.modelo.LineaDocumentoFactura;
import tpv.cirer.com.marivent.ui.ActividadPrincipal;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.LoadImageFromWebOperations;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.codec;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getQuery;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.imagelogoprint;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lcabeceraempr;

/**
 * Created by JUAN on 08/08/2017.
 */

public class PrintTicket {
    private static String urlPrint;
    private Context context;
    private int nFacturaTicket;
    private String sSerieTicket;
    
    String ticket;
    String ticketTotal;
    String ticketMensaje;

    // JSON parser class
    JSONParserNew jsonParserNew = new JSONParserNew();
    // url to update Factura
    private static final String url_update_Factura = Filtro.getUrl()+"/modifica_estado_factura_filtro.php";

    private String pid;
    ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_ESTADO = "estado";

    static final int IMAGE_WIDTH_MAX = 512;
    static final int SEND_TIMEOUT = 10 * 1000;
    static Print printer = null;
    static Builder builder = null;
//**    public static List<CabeceraEmpr> lcabeceraempr;
    public static List<CabeceraFtp> lcabeceraftp;
    public static List<LineaDocumentoFactura> llineadocumentofacturaprint;
    public static List<DocumentoFacturaIva> ldocumentofacturaiva;
    ProgressDialog pDialogEmpr,pDialogFtp,pDialogLft,pDialogFtpiva;

    public PrintTicket(Context contexto, int factura, String serie) {
        this.context = contexto;
        this.nFacturaTicket = factura;
        this.sSerieTicket = serie;
    }

    public void iniciarTicket(){
  //**      lcabeceraempr = new ArrayList<CabeceraEmpr>();
        lcabeceraftp = new ArrayList<CabeceraFtp>();
        llineadocumentofacturaprint = new ArrayList<LineaDocumentoFactura>();
        ldocumentofacturaiva = new ArrayList<DocumentoFacturaIva>();

        urlPrint = Filtro.getUrl()+"/CabeceraFTP.php";
        new LeerCabeceraFtp().execute(urlPrint);
/*        urlPrint = Filtro.getUrl() + "/CabeceraEMPR.php";
        new LeerCabeceraEmpr().execute(urlPrint);
*/
    }
    public class LeerCabeceraEmpr extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogEmpr = new ProgressDialog(context);
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
            Log.i("Sql Print",cSql);
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
            if (null==context) {
            }else{
                pDialogFtp = new ProgressDialog(context);
                pDialogFtp.setMessage(ActividadPrincipal.getPalabras("Leyendo") + " " + ActividadPrincipal.getPalabras("Cabecera") + " " + ActividadPrincipal.getPalabras("Factura") + "..");
                pDialogFtp.setIndeterminate(false);
                pDialogFtp.setCancelable(true);
                pDialogFtp.show();
            }
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND ftp.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND ftp.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND ftp.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND ftp.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            xWhere += " AND ftp.SERIE='" + sSerieTicket + "'";
            xWhere += " AND ftp.FACTURA=" + nFacturaTicket;

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Print",cSql);
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
            if(null==context){
            }else {
                pDialogFtp.dismiss();
            }

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
            if (null==context) {
            }else {
                pDialogLft = new ProgressDialog(context);
                pDialogLft.setMessage(ActividadPrincipal.getPalabras("Leyendo") + " " + ActividadPrincipal.getPalabras("Lineas") + " " + ActividadPrincipal.getPalabras("Factura") + "..");
                pDialogLft.setIndeterminate(false);
                pDialogLft.setCancelable(true);
                pDialogLft.show();
            }
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
            xWhere += " AND lft.SERIE='" + sSerieTicket + "'";
            xWhere += " AND lft.FACTURA=" + nFacturaTicket;

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Print",cSql);
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
            if (null==context) {
            }else {
                pDialogLft.dismiss();
            }
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
            if (null==context) {
            }else {
                pDialogFtpiva = new ProgressDialog(context);
                pDialogFtpiva.setMessage(ActividadPrincipal.getPalabras("Leyendo") + " " + ActividadPrincipal.getPalabras("Lineas") + " " + ActividadPrincipal.getPalabras("Iva") + "..");
                pDialogFtpiva.setIndeterminate(false);
                pDialogFtpiva.setCancelable(true);
                pDialogFtpiva.show();
            }
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
            xWhere += " AND ftpiva.SERIE='" + sSerieTicket + "'";
            xWhere += " AND ftpiva.FACTURA=" + nFacturaTicket;

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Print",cSql);
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
            if (null==context) {
            }else {
                pDialogFtpiva.dismiss();
            }
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
    class SaveEstadoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (null==context) {
            }else {
                pDialogFtp = new ProgressDialog(context);
                pDialogFtp.setMessage(ActividadPrincipal.getPalabras("Guardar") + " " + ActividadPrincipal.getPalabras("Estado") + " ...");
                pDialogFtp.setIndeterminate(false);
                pDialogFtp.setCancelable(true);
                pDialogFtp.show();
            }
        }

        /**
         * Saving Factura
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // getting updated data from EditTexts
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND ftp.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND ftp.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND ftp.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND ftp.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getSerie().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.SERIE='" + sSerieTicket + "'";
                } else {
                    xWhere += " AND ftp.SERIE='" + sSerieTicket + "'";
                }
            }

            if(!(Filtro.getFactura()==0)) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.FACTURA=" + nFacturaTicket;
                } else {
                    xWhere += " AND ftp.FACTURA=" + nFacturaTicket;
                }
            }
            cSql += xWhere;

            Integer result = 0;
            // Building Parameters
            ContentValues values = new ContentValues();

            for (int i = 0; i < lcabeceraftp.size(); i++) {
                lcabeceraftp.get(i).setCabeceraEstado("02");

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                values.put("filtro", cSql);
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
            if (null==context) {
            }else {
                pDialogFtp.dismiss();
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
            if (!lcabeceraftp.get(i).getCabeceraEstado().equals("01")) {

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
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
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
        printer = new Print(context);
        try {
            printer.openPrinter(Filtro.getPrintdeviceType(), Filtro.getPrintIp(), 1, Filtro.getPrintInterval());
            printer.beginTransaction();
            Log.e("PRINTER", "PRINT OPEN!");
        } catch (Exception e) {
            printer = null;
            ShowMsg.showException(e, "openPrinter", context);
            return;
        }
    }
    public void openBuilder() {

        builder = null;
        String method = "";
        try {
            //create builder
            method = "Builder";
            builder = new Builder(Filtro.getPrintPrinterName(), Filtro.getPrintLanguage(), context);

        } catch (Exception e) {
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showException(e, method, context);
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
            ShowMsg.showStatus(EposException.SUCCESS, status[0], battery[0], context);
            // Comprobar estado ticket si = "01" modificar "02"
            if (lcabeceraftp.get(0).getCabeceraEstado().contains("01")){
                new SaveEstadoFactura().execute(url_update_Factura);
            }

        }catch(EposException e){
            ShowMsg.showStatus(e.getErrorStatus(), e.getPrinterStatus(), e.getBatteryStatus(), context);
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
    public static void closePrinter(){
        try{
            printer.endTransaction();
            printer.closePrinter();
            printer = null;
        }catch(Exception e){
            printer = null;
        }
    }

}
