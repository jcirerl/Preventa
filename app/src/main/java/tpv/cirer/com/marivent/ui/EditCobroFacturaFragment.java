package tpv.cirer.com.marivent.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

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

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.GetAlImages;
import tpv.cirer.com.marivent.modelo.DocumentoFactura;
import tpv.cirer.com.marivent.modelo.TipoCobro;
import tpv.cirer.com.marivent.print.PrintTicket;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.tftList;

//import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by JUAN on 10/11/2016.
 */

public class EditCobroFacturaFragment  extends Fragment implements View.OnKeyListener,
                                                                   AdapterView.OnItemSelectedListener {
    public static final String TAG = "Modifica Cobro";
    public static List<DocumentoFactura> documentofacturalist;
    public static final String GET_IMAGE_URL=Filtro.getUrl()+"/getImageFirma.php";

    public GetAlImages getAlImages;

    public static final String BITMAP_ID = "BITMAP_ID";

    String TAG_TFT = "TFT: ";

    CheckBox chkCobro;
    EditText txtTotalCobro;
    EditText txtTipoDto;
    EditText txtObs;
    TextView txtFactura;
    TextView txtTotalFactura;
    TextView txtTotalDiferencia;
    TextView lbltft;
    TextView lblfactura;
    TextView lblfirma;
    TextView lblTotalFactura;
    TextView lblTotalCobro;
    TextView lblTotalDiferencia;
    TextView lblTipoDto;
    TextView lblObs;

    public static String URL_TFT;
    ///*** public static ArrayList<TipoCobro> tftList;
    Spinner cmbToolbarTft;
    LinearLayout rootView;
    ToggleButton btnGuardarCobro;
    ImageView imagefirma;
    Button btnSalir;

    //  Button btnGuardar;

    private String pid;
    private String serie;
    private String factura;
    private String origen;
    private int positiontft;

    // Progress Dialog
    private ProgressDialog pDialogftp,pDialogtft;

    // JSON parser class
    JSONParserNew jsonParserNew = new JSONParserNew();

    // single Factura url
    private static final String url_Factura_details = Filtro.getUrl()+"/lee_factura_detalle.php";

    // url to update Factura
    private static final String url_update_Factura = Filtro.getUrl()+"/modifica_cobro_factura.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_FACTURA = "factura";
    private static final String TAG_ESTADO = "estado";
    private static final String TAG_TFRA = "t_fra";
    private static final String TAG_IMPTOTAL = "imp_total";
    private static final String TAG_IMPCOBRO = "imp_cobro";
    private static final String TAG_IMPDIFERENCIA = "imp_diferencia";
    private static final String TAG_IMAGENFIRMA = "imagen_firma";
    private static final String TAG_DTO = "dto";
    private static final String TAG_OBS = "obs";


    private static EditCobroFacturaFragment EditCobroFactura = null;

    public static EditCobroFacturaFragment newInstance(String id, String serie, String factura, String origen) {
        EditCobroFacturaFragment EditCobroFactura = new EditCobroFacturaFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("serie", serie);
        args.putString("factura", factura);
        args.putString("origen", origen);
        EditCobroFactura.setArguments(args);
        return EditCobroFactura;
    }

    public EditCobroFacturaFragment() {
        // Required empty public constructor
    }
    public static EditCobroFacturaFragment getInstance(){
        if(EditCobroFactura == null){
            EditCobroFactura = new EditCobroFacturaFragment();
        }
        return EditCobroFactura;
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
        serie = getArguments() != null ? getArguments().getString("serie") : "0";
        factura = getArguments() != null ? getArguments().getString("factura") : "0";
        origen = getArguments() != null ? getArguments().getString("origen") : "lista";

        /// Poner Titulo CABECERA
////        ((ActividadPrincipal) getActivity()).setTitle("Cobro Factura");
        ((ActividadPrincipal) getActivity()).setTitle(((ActividadPrincipal)getActivity()).getPalabras("Cobro")+" "+((ActividadPrincipal)getActivity()).getPalabras("Factura"));

        documentofacturalist = new ArrayList<DocumentoFactura>();
        // Rellenar string toolbar_tft
////***        tftList = new ArrayList<TipoCobro>();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   View rootView = inflater.inflate(R.layout.add_lectura, container, false);
        rootView = (LinearLayout) inflater.inflate(R.layout.edit_cobro,container, false);

        lblObs = (TextView) rootView.findViewById(R.id.lblObs);
        lblObs.setText(ValorCampo(R.id.lblObs, lblObs.getClass().getName(),0));
        txtObs = (EditText) rootView.findViewById(R.id.Obs);

        lblTipoDto = (TextView) rootView.findViewById(R.id.lblDto);
        lblTipoDto.setText(ValorCampo(R.id.lblDto, lblTipoDto.getClass().getName(),0));
        txtTipoDto = (EditText) rootView.findViewById(R.id.TipoDto);

        lblTotalCobro = (TextView) rootView.findViewById(R.id.lblTotalCobro);
        lblTotalCobro.setText(ValorCampo(R.id.lblTotalCobro, lblTotalCobro.getClass().getName(),0));
        txtTotalCobro = (EditText) rootView.findViewById(R.id.TotalCobro);

        txtTotalFactura = (TextView) rootView.findViewById(R.id.TotalFactura);
        lblTotalFactura = (TextView) rootView.findViewById(R.id.lblTotalFactura);
        lblTotalFactura.setText(ValorCampo(R.id.lblTotalFactura, lblTotalFactura.getClass().getName(),0));

        txtTotalDiferencia = (TextView) rootView.findViewById(R.id.TotalDiferencia);
        lblTotalDiferencia = (TextView) rootView.findViewById(R.id.lblTotalDiferencia);
        lblTotalDiferencia.setText(ValorCampo(R.id.lblTotalDiferencia, lblTotalDiferencia.getClass().getName(),0));

        lblfactura = (TextView) rootView.findViewById(R.id.lblFactura);
        lblfactura.setText(ValorCampo(R.id.lblFactura, lblfactura.getClass().getName(),0));

        txtFactura = (TextView) rootView.findViewById(R.id.Factura);

        chkCobro = (CheckBox) rootView.findViewById(R.id.chkCobro);
//        chkCobro.setText(getResources().getString(R.string.ON_cobro));
        chkCobro.setText(ValorCampo(R.id.chkCobro, chkCobro.getClass().getName(),0));

        lblfirma = (TextView) rootView.findViewById(R.id.lblfirma);
        lblfirma.setText(ValorCampo(R.id.lblfirma, lblfirma.getClass().getName(),0));

        imagefirma = (ImageView) rootView.findViewById(R.id.imagefirma);

        lbltft = (TextView) rootView.findViewById(R.id.Lbltft);
        lbltft.setText(ValorCampo(R.id.Lbltft, lbltft.getClass().getName(),0));
        cmbToolbarTft = (Spinner) rootView.findViewById(R.id.CmbToolbarTft);

        cmbToolbarTft.setOnItemSelectedListener(this);
        // Appbar page filter grupos
        URL_TFT = Filtro.getUrl() + "/get_tiposcobro.php";
////***        new GetTiposCobro().execute(URL_TFT);  AHORA SE CARGA EN LA ACTIVIDAD PRINCIPAL
        populateSpinnerTft();

 //       TaskHelper.execute(new GetTiposCobro(), URL_TFT);
 //       new GetTiposCobro().execute(URL_TFT);

        txtTotalCobro.setOnKeyListener(this);
        txtTipoDto.setOnKeyListener(this);

        // button click event
        btnSalir = (Button)rootView.findViewById(R.id.BtnSalir);
        btnSalir.setText(ValorCampo(R.id.BtnSalir, btnSalir.getClass().getName(),0));

        btnSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (origen.equals("lista")) {
                    getActivity().onBackPressed();
                }
                if (origen.equals("factura")) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            }
        });


        btnGuardarCobro = (ToggleButton)rootView.findViewById(R.id.BtnGuardarCobro);
        btnGuardarCobro.setText(ValorCampo(R.id.BtnGuardarCobro, btnGuardarCobro.getClass().getName(),0));
        btnGuardarCobro.setText(ValorCampo(R.id.BtnGuardarCobro, btnGuardarCobro.getClass().getName(),1));
        btnGuardarCobro.setEnabled(false);
        btnGuardarCobro.setChecked(false);
        btnGuardarCobro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String cMaximo = txtTotalCobro.getText().toString();
                cMaximo = cMaximo.replace(".","");
                cMaximo = cMaximo.replace(",",".");
                txtTotalCobro.setText(cMaximo);

                cMaximo = txtTotalDiferencia.getText().toString();
                cMaximo = cMaximo.replace(".","");
                cMaximo = cMaximo.replace(",",".");
                cMaximo = cMaximo.replace('\u00A0',' ').trim();
                Log.i("cMaximo",cMaximo);

                txtTotalDiferencia.setText(cMaximo);

                // Eliminar Teclado Virtual
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);

                for (int i = 0; i < documentofacturalist.size(); i++) {
                    documentofacturalist.get(i).setDocumentoFacturaImp_cobro(txtTotalCobro.getText().toString());
                    documentofacturalist.get(i).setDocumentoFacturaImp_diferencia(txtTotalDiferencia.getText().toString());
                    documentofacturalist.get(i).setDocumentoFacturaObs(txtObs.getText().toString());
                    documentofacturalist.get(i).setDocumentoFacturaT_fra(Filtro.getT_fra());
                    documentofacturalist.get(i).setDocumentoFacturaEstado(lparam.get(0).getDEFAULT_ESTADO_COBRO_FACTURA());

                }
                // starting background task to update Factura
                new SaveCobroFactura().execute();
            }
        });
        // save button click event
        chkCobro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if(chkCobro.isChecked()) {
                    btnGuardarCobro.setEnabled(true);
                    btnGuardarCobro.setChecked(true);
                }else {
                    btnGuardarCobro.setEnabled(false);
                    btnGuardarCobro.setChecked(false);
                }
            }
        });

        Log.i("Fragment id ", " # " + pid );
        //       new AsyncHttpTask().execute(url);

        return rootView;
    }

     /**
     * Adding spinner data grupo
     */
    private void populateSpinnerTft() {
        List<String> lables_tft = new ArrayList<String>();
        for (int i = 0; i < tftList.size(); i++) {
            lables_tft.add(tftList.get(i).getTipoCobroNombre_tft());
            Log.i("TFT ",tftList.get(i).getTipoCobroNombre_tft());
        }
        ArrayAdapter<String> adapter_tft = new ArrayAdapter<>(
                this.getActivity(),
                R.layout.appbar_filter_title,lables_tft);

        adapter_tft.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbarTft.setAdapter(adapter_tft);
        cmbToolbarTft.setSelection(0);
        if (tftList.size()>0) {
            Filtro.setT_fra(tftList.get(0).getTipoCobroT_fra());
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spinner = (Spinner) parent;
        String cMaximo;
        if(spinner.getId() == R.id.CmbToolbarTft)
        {
            if (tftList.size()>0) {
                Filtro.setT_fra(tftList.get(position).getTipoCobroT_fra());
                // GUARDAMOS POSICION SPINNER PARA SABER SI TENEMOS QUE HACER COPIA DE TICKET
                positiontft = position;
                /////////////////////////////////////////////////////////////////////////////
                switch (Filtro.getT_fra()) {
                    case "CR":
//                        if (imagefirma.getVisibility()==View.GONE && origen.equals("lista")) {
                        if (imagefirma.getVisibility()==View.GONE) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());

                            dialog.setTitle(((ActividadPrincipal) getActivity()).getPalabras("Factura")+": " + documentofacturalist.get(0).getDocumentoFacturaFactura());
                            dialog.setMessage(((ActividadPrincipal) getActivity()).getPalabras("Tipo Cobro")+": " + Filtro.getT_fra() +
                                    "\n"+((ActividadPrincipal) getActivity()).getPalabras("Total Cobro")+": " + txtTotalCobro.getText().toString()
                            );
                            dialog.setIcon(R.drawable.mark_as_read);
                            dialog.setPositiveButton(((ActividadPrincipal) getActivity()).getPalabras("Registra Firma"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        Fragment firmafragment = null;
                                        firmafragment = FirmaFragment.newInstance(pid,serie,factura,origen);
                                        FragmentTransaction ft = null;
                                        if (origen.equals("lista")) {
                                            ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.contenedor_principal, firmafragment, firmafragment.getClass().getName());
                                        }
                                        if (origen.equals("factura")) {
                                            ft = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.left_pane, firmafragment, firmafragment.getClass().getName());
                                        }
                                        ft.addToBackStack(null);
                                        ft.commit();

//                                    startActivity(new Intent(getActivity(), SignatureActivity.class));
                                    } catch (ClassCastException exception) {
                                        // do something
                                    }
                                    dialog.cancel();
                                }
                            });
                            dialog.setNegativeButton(((ActividadPrincipal) getActivity()).getPalabras("Salir"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }else{
                            Snackbar.make(view, ActividadPrincipal.getPalabras("No") + " " + ActividadPrincipal.getPalabras("Registra")+ " " + ActividadPrincipal.getPalabras("Firma"), Snackbar.LENGTH_LONG).show();
                        }
                        break;
                    case "CC":
                        cMaximo = "0,00";
                        cMaximo = cMaximo.replace(".","");
                        cMaximo = cMaximo.replace(",",".");
                        txtTotalCobro.setText(cMaximo);
                        break;
                    case "CO":
                        cMaximo = txtTotalFactura.getText().toString();
                        cMaximo = cMaximo.replace(Html.fromHtml("&nbsp;"),""); // quitamos espacios
                        cMaximo = cMaximo.replace(Filtro.getSimbolo(),""); // quitamos moneda
//                        cMaximo = cMaximo.replace(".","");
//                        cMaximo = cMaximo.replace(",",".");
                        txtTotalCobro.setText(cMaximo);
                        break;

                }
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
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

    /**
     * Async task to get all food rangos
     */
    public class GetTiposCobro extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogtft = new ProgressDialog(getActivity());
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
                    for (Iterator<TipoCobro> it = tftList.iterator(); it.hasNext();){
                        TipoCobro tipoCobro = it.next();
                        it.remove();
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
                populateSpinnerTft();
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
                        post.optString("NOMBRE"),
                        post.optInt("COPIA_PRINT"));
                tftList.add(cat);
                Log.i("POST Longitud: ",Integer.toString(tftList.size()));

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


    /**
     * Background Async Task to Get complete Factura details
     * */
    class GetCobroFactura extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogftp = new ProgressDialog(getActivity());
            pDialogftp.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Factura")+". "+ActividadPrincipal.getPalabras("Espere por favor")+"...");
            pDialogftp.setIndeterminate(false);
            pDialogftp.setCancelable(true);
            pDialogftp.show();
        }

        /**
         * Getting Factura details in background thread
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
                                url_Factura_details, "GET", values);


                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received Factura details
                            JSONArray FacturaObj = json
                                    .getJSONArray(TAG_FACTURA); // JSON Array

                            // get first Factura object from JSON Array
                            JSONObject DocumentoFactura = FacturaObj.getJSONObject(0);

                            chkCobro.setChecked(false);
                            String space01 = new String(new char[01]).replace('\0', ' ');
                            String myText;
                            String newText;

                            double xMaximo = Double.valueOf(DocumentoFactura.getString(TAG_IMPTOTAL));
                            txtTotalCobro.setText(String.format("%1$,.2f", xMaximo));

                            xMaximo = Double.valueOf(DocumentoFactura.getString(TAG_IMPTOTAL));
///                            txtTotalFactura.setText(String.format("%1$,.2f", xMaximo));
                            myText = String.format("%1$,.2f", xMaximo);
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            newText="";
                            for (int ii = 0; ii < (11-myText.length()); ii++) {
                                newText+=space01;
                            }
                            newText +=myText;
                            txtTotalFactura.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                            txtTipoDto.setText(String.format("%1$,.2f", Double.valueOf("0.00")));

                            xMaximo = Double.valueOf("0.00");
                            myText = String.format("%1$,.2f", xMaximo);
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            newText="";
                            for (int ii = 0; ii < (11-myText.length()); ii++) {
                                newText+=space01;
                            }
                            newText +=myText;
                            txtTotalDiferencia.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

//                            txtObs.setText(DocumentoFactura.getString(TAG_OBS));
                            myText =String.format("%1$-50s",DocumentoFactura.getString(TAG_OBS));
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha

                            newText=myText;
                            for (int ii = 0; ii < (50-myText.length()); ii++) {
                                newText+=space01;
                            }

                            txtObs.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                            txtFactura.setText(String.format("%07d", Integer.parseInt(String.valueOf(DocumentoFactura.getString(TAG_FACTURA)))));

                            DocumentoFactura facturaItem = new DocumentoFactura();

                            facturaItem.setDocumentoFacturaT_fra(DocumentoFactura.getString(TAG_TFRA));
                            facturaItem.setDocumentoFacturaEstado(DocumentoFactura.getString(TAG_ESTADO));
                            facturaItem.setDocumentoFacturaImp_cobro(DocumentoFactura.getString(TAG_IMPTOTAL));
                            facturaItem.setDocumentoFacturaImp_total(DocumentoFactura.getString(TAG_IMPTOTAL));
                            facturaItem.setDocumentoFacturaImp_diferencia(DocumentoFactura.getString(TAG_IMPDIFERENCIA));
                            facturaItem.setDocumentoFacturaFactura(Integer.parseInt(DocumentoFactura.getString(TAG_FACTURA)));
                            facturaItem.setDocumentoFacturaImagen_firma(DocumentoFactura.getString(TAG_IMAGENFIRMA));
                            facturaItem.setDocumentoFacturaDto(DocumentoFactura.getString(TAG_DTO));
                            facturaItem.setDocumentoFacturaObs(DocumentoFactura.getString(TAG_OBS));

                            documentofacturalist.add(facturaItem);

                            for (int i = 0; i < tftList.size(); i++) {
                                for (int z = 0; z < documentofacturalist.size(); z++) {
                                    if (tftList.get(i).getTipoCobroT_fra().equals(documentofacturalist.get(z).getDocumentoFacturaT_fra())) {
                                        cmbToolbarTft.setSelection(i);
                                        Filtro.setT_fra(tftList.get(i).getTipoCobroT_fra());
                                    }
                                }
                            }
                            ((ActividadPrincipal) getActivity()).setTitle("Cobro Factura "+documentofacturalist.get(0).getDocumentoFacturaFactura());


                        }else{
                            // Factura with pid not found
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
            pDialogftp.dismiss();
            if (Filtro.getT_fra().equals("CR")){
                lblfirma.setVisibility(View.VISIBLE);
                imagefirma.setVisibility(View.VISIBLE);
//                .load(documentofacturalist.get(0).getDocumentoFacturaImagen_firma())
                Picasso.with(getActivity())
                        .load(Filtro.getUrlFirma())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .resize(200, 200)
                        .centerCrop()
                        .into(imagefirma);

            }
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
            pDialogftp = new ProgressDialog(getActivity());
            pDialogftp.setMessage(ActividadPrincipal.getPalabras("Guardar")+" "+ActividadPrincipal.getPalabras("Cobro")+" ...");
            pDialogftp.setIndeterminate(false);
            pDialogftp.setCancelable(true);
            pDialogftp.show();
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
            for (int i = 0; i < documentofacturalist.size(); i++) {

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());


                values.put(TAG_PID, pid);
                values.put(TAG_TFRA,String.valueOf(documentofacturalist.get(i).getDocumentoFacturaT_fra()));
                values.put(TAG_ESTADO,String.valueOf(documentofacturalist.get(i).getDocumentoFacturaEstado()));
                values.put(TAG_IMPCOBRO,String.valueOf(documentofacturalist.get(i).getDocumentoFacturaImp_cobro()));
                values.put(TAG_IMPDIFERENCIA,String.valueOf(documentofacturalist.get(i).getDocumentoFacturaImp_diferencia()));
                values.put(TAG_DTO,String.valueOf(documentofacturalist.get(i).getDocumentoFacturaDto()));
                values.put(TAG_OBS,String.valueOf(documentofacturalist.get(i).getDocumentoFacturaObs()));
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
            pDialogftp.dismiss();
            if (result==1){
                if(tftList.get(positiontft).getTipoCobroCopia_print()>0) {
                    for (int i = 0; i < tftList.get(positiontft).getTipoCobroCopia_print(); i++) {

                        PrintTicket printticket = new PrintTicket(getActivity(), Integer.parseInt(factura), serie);
                        printticket.iniciarTicket();

                    }
                }
                if (origen.equals("lista")) {
                    getActivity().onBackPressed();
                }
                if (origen.equals("factura")) {
                    Filtro.setCobroDesdeFactura(1);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }

        }
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        //TextView responseText = (TextView) findViewById(R.id.responseText);
        EditText myEditText = (EditText) view;
        String value = myEditText.getText().toString();
        int pos = 0;
        /////////////////////////////////////////
        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                int id = view.getId();
                switch (view.getId()) {
                    case R.id.TotalCobro:
                        value = value.replace(".", "");
                        value = value.replace(",", ".");

                        myEditText.setText(String.format("%1$,.2f", Double.valueOf(value)));

                        // Ponerse al final del edittext
                        pos = myEditText.getText().length();
                        myEditText.setSelection(pos);

                        String cTotalcobro = myEditText.getText().toString();
                        if (cTotalcobro.matches("")) {
                            Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("Valor")+" "+ActividadPrincipal.getPalabras("Vacio"), Toast.LENGTH_SHORT).show();
                            //            this.btnGuardar.setEnabled(false);
                            this.btnGuardarCobro.setEnabled(false);
                            this.btnGuardarCobro.setChecked(false);
                            return false;
                        }

                        cTotalcobro = cTotalcobro.replace(".","");
                        cTotalcobro = cTotalcobro.replace(",",".");

                        Log.v("EnterKey TCobro PARSE",cTotalcobro);

                        double xTotalcobro = Double.valueOf(cTotalcobro);
                        txtTotalCobro.setText(String.format("%1$,.2f", xTotalcobro));

                        Log.v("EnterKey TCobro",txtTotalCobro.getText().toString());

                        String cTotalfactura = txtTotalFactura.getText().toString();
                        cTotalfactura = cTotalfactura.replace(Html.fromHtml("&nbsp;"),"");
                        cTotalfactura = cTotalfactura.replace(Filtro.getSimbolo(),"");
                        cTotalfactura = cTotalfactura.replace(".","");
                        cTotalfactura = cTotalfactura.replace(",",".");

                        Log.v("EnterKey TFactura PARSE",cTotalfactura);

                        double xTotalfactura = Double.valueOf(cTotalfactura);

                        txtTotalDiferencia.setText(String.format("%1$,.2f", (xTotalcobro-xTotalfactura)));
                        Log.v("EnterKey TDif",txtTotalDiferencia.getText().toString());

                        break;
                    case R.id.TipoDto:
                        value = value.replace(".", "");
                        value = value.replace(",", ".");

                        myEditText.setText(String.format("%1$,.2f", Double.valueOf(value)));

                        // Ponerse al final del edittext
                        pos = myEditText.getText().length();
                        myEditText.setSelection(pos);
                        String cTipoDto = myEditText.getText().toString();
                        if (cTipoDto.matches("")) {
                            Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("Valor")+" "+ActividadPrincipal.getPalabras("Vacio"), Toast.LENGTH_SHORT).show();
                            //            this.btnGuardar.setEnabled(false);
                            this.btnGuardarCobro.setEnabled(false);
                            this.btnGuardarCobro.setChecked(false);
                            return false;
                        }
                        // Formateamos TIPO DTO
                        cTipoDto = cTipoDto.replace(".","");
                        cTipoDto = cTipoDto.replace(",",".");

                        Log.v("EnterKey Dto PARSE",cTipoDto);

                        double xTipoDto = Double.valueOf(cTipoDto);
                        txtTipoDto.setText(String.format("%1$,.2f", xTipoDto));
                        Log.v("EnterKey Dto",txtTipoDto.getText().toString());


                        // Formateamos TOTAL FACTURA
                        cTotalfactura = txtTotalFactura.getText().toString();
                        cTotalfactura = cTotalfactura.replace(Html.fromHtml("&nbsp;"),"");
                        cTotalfactura = cTotalfactura.replace(Filtro.getSimbolo(),"");
                        cTotalfactura = cTotalfactura.replace(".","");
                        cTotalfactura = cTotalfactura.replace(",",".");

                        Log.v("EnterKey TFactura PARSE", cTotalfactura);

                        // CALCULAMOS EL DESCUENTO Y LO RESTAMOS DE LA FACTURA
                        xTotalfactura = Double.valueOf(cTotalfactura);
                        double xTotaldto = Double.valueOf(((xTotalfactura * xTipoDto) / 100));

                        Log.v("EnterKey TDto",Double.toString(xTotaldto));

                        txtTotalCobro.setText(String.format("%1$,.2f", (xTotalfactura-xTotaldto)));

                        Log.v("EnterKey TCobro",txtTotalCobro.getText().toString());

                        // Formateamos TOTAL COBRO
                        cTotalcobro = txtTotalCobro.getText().toString();
                        cTotalcobro = cTotalcobro.replace(".","");
                        cTotalcobro = cTotalcobro.replace(",",".");

                        Log.v("EnterKey TCobro PARSE",cTotalcobro);


                        xTotalcobro = Double.valueOf(cTotalcobro);

                        // CALCULAMOS DIFERENCIA ENTRE FACTURA Y COBRO
                        txtTotalDiferencia.setText(String.format("%1$,.2f", (xTotalcobro-xTotalfactura)));

                        Log.v("EnterKey TDiferencia",txtTotalDiferencia.getText().toString());


                        break;
                    case R.id.Obs:
                        Log.i("OBS",Integer.toString(value.length()));
                        String cValor;
                        cValor = value;
                        cValor = cValor.replace(Html.fromHtml("&nbsp;&nbsp;"), "");
                        myEditText.setText(cValor.trim());

                        // Ponerse al final del edittext
                        pos = myEditText.getText().length();
                        myEditText.setSelection(pos);
                        Log.i("OBS",Integer.toString(value.length()));

                        break;
                }
                return true;
            }
        }
        return false; // pass on to other listeners.

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
 //       TaskHelper.execute(new GetCobroFactura());
        ((ActividadPrincipal) getActivity()).setTitle(((ActividadPrincipal)getActivity()).getPalabras("Cobro")+" "+((ActividadPrincipal)getActivity()).getPalabras("Factura"));
        Spannable text = new SpannableString(((ActividadPrincipal) getActivity()).getTitle());
        text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(((ActividadPrincipal) getActivity()), R.color.light_blue_500)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ((ActividadPrincipal) getActivity()).setTitle(text);
        new GetCobroFactura().execute();
    }
}