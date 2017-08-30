package tpv.cirer.com.marivent.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.modelo.LineaDocumentoFactura;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;

//import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by JUAN on 27/09/2016.
 */

public class EditLineaDocumentoFacturaFragment extends Fragment implements View.OnKeyListener {
    static final int DATE_DIALOG_ID = 0;
    public static List<LineaDocumentoFactura> lineadocumentofacturalist;
    public static final String TAG = "Modifica LFT";

    EditText txtPreu;
    EditText txtCant;
    TextView txtNombre;
    EditText txtImporte;

    ToggleButton btnGuardar1;
    //  Button btnGuardar;

    private String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParserNew jsonParserNew = new JSONParserNew();

    // single LineaDocumentoFactura url
    private static final String url_LineaDocumentoFactura_details = Filtro.getUrl()+"/lee_lft_detalle.php";

    // url to update LineaDocumentoFactura
    private static final String url_update_LineaDocumentoFactura = Filtro.getUrl()+"/modifica_lft.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_LINEAFACTURATICKET = "lineadocumentofactura";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_PREU = "preu";
    private static final String TAG_CANT = "cant";
    private static final String TAG_IMPORTE = "importe";

    private static EditLineaDocumentoFacturaFragment EditLineaDocumentoFactura = null;

    public static EditLineaDocumentoFacturaFragment newInstance(String id) {
        EditLineaDocumentoFacturaFragment EditLineaDocumentoFactura = new EditLineaDocumentoFacturaFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        EditLineaDocumentoFactura.setArguments(args);
        return EditLineaDocumentoFactura;
    }

    public EditLineaDocumentoFacturaFragment() {
        // Required empty public constructor
    }
    public static EditLineaDocumentoFacturaFragment getInstance(){
        if(EditLineaDocumentoFactura == null){
            EditLineaDocumentoFactura = new EditLineaDocumentoFacturaFragment();
        }
        return EditLineaDocumentoFactura;
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
        lineadocumentofacturalist = new ArrayList<LineaDocumentoFactura>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   View rootView = inflater.inflate(R.layout.add_lectura, container, false);
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.edit_lineadocumentofactura,container, false);

        // Edit Text

        txtCant = (EditText) rootView.findViewById(R.id.Cant);
        txtPreu = (EditText) rootView.findViewById(R.id.Preu);
        txtImporte = (EditText) rootView.findViewById(R.id.Importe);

        txtNombre = (TextView) rootView.findViewById(R.id.Nombre);

        txtCant.setOnKeyListener(this);
        txtPreu.setOnKeyListener(this);
        txtImporte.setOnKeyListener(this);


        btnGuardar1 = (ToggleButton)rootView.findViewById(R.id.BtnGuardar1);
        btnGuardar1.setEnabled(true);
        btnGuardar1.setChecked(true);
        btnGuardar1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String cValor = txtCant.getText().toString();
                cValor = cValor.replace(".","");
                cValor = cValor.replace(",",".");
                txtCant.setText(cValor);
                
                cValor = txtPreu.getText().toString();
                cValor = cValor.replace(".","");
                cValor = cValor.replace(",",".");
                txtPreu.setText(cValor);

                cValor = txtImporte.getText().toString();
                cValor = cValor.replace(".","");
                cValor = cValor.replace(",",".");
                txtImporte.setText(cValor);

                // Eliminar Teclado Virtual
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);

                for (int i = 0; i < lineadocumentofacturalist.size(); i++) {
                    lineadocumentofacturalist.get(i).setLineaDocumentoFacturaNombre(txtNombre.getText().toString());
                    lineadocumentofacturalist.get(i).setLineaDocumentoFacturaCant(txtCant.getText().toString());
                    lineadocumentofacturalist.get(i).setLineaDocumentoFacturaPreu(txtPreu.getText().toString());
                    lineadocumentofacturalist.get(i).setLineaDocumentoFacturaImporte(txtImporte.getText().toString());

                }
                // starting background task to update LineaDocumentoFactura
                new SaveLineaDocumentoFacturaDetails().execute();
            }
        });
        Log.i("Fragment id ", " # " + pid );
        //       new AsyncHttpTask().execute(url);

        return rootView;
    }


    /**
     * Background Async Task to Get complete LineaDocumentoFactura details
     * */
    class GetLineaDocumentoFacturaDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Linea")+" "+ActividadPrincipal.getPalabras("Documento")+" "+ActividadPrincipal.getPalabras("Factura")+". "+ActividadPrincipal.getPalabras("Espere por favor")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting LineaDocumentoFactura details in background thread
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
                        // Building Parameters
//                        List<NameValuePair> params = new ArrayList<NameValuePair>();
//                        params.add(new BasicNameValuePair("pid", pid));
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);

                        // getting LineaDocumentoFactura details by making HTTP request
                        // Note that LineaDocumentoFactura details url will use GET request
//                        JSONObject json = jsonParser.makeHttpRequest(
//                                url_LineaDocumentoFactura_details, "GET", values);
                        JSONObject json = jsonParserNew.makeHttpRequest(
                                url_LineaDocumentoFactura_details, "GET", values);

                        // check your log for json response
///                        Log.d("Single LineaDocumentoFactura Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received LineaDocumentoFactura details
                            JSONArray LineaDocumentoFacturaObj = json
                                    .getJSONArray(TAG_LINEAFACTURATICKET); // JSON Array

                            // get first LineaDocumentoFactura object from JSON Array
                            JSONObject LineaDocumentoFactura = LineaDocumentoFacturaObj.getJSONObject(0);
                            txtNombre.setText(LineaDocumentoFactura.getString(TAG_NOMBRE));

                            double xValor = Double.valueOf(LineaDocumentoFactura.getString(TAG_CANT));
                            txtCant.setText(String.format("%1$,.2f", xValor));

                            xValor = Double.valueOf(LineaDocumentoFactura.getString(TAG_PREU));
                            txtPreu.setText(String.format("%1$,.2f", xValor));

                            xValor = Double.valueOf(LineaDocumentoFactura.getString(TAG_IMPORTE));
                            txtImporte.setText(String.format("%1$,.2f", xValor));

                            LineaDocumentoFactura lineadocumentofacturaItem = new LineaDocumentoFactura();
                            lineadocumentofacturaItem.setLineaDocumentoFacturaNombre(LineaDocumentoFactura.getString(TAG_NOMBRE));
                            lineadocumentofacturaItem.setLineaDocumentoFacturaPreu(LineaDocumentoFactura.getString(TAG_PREU));
                            lineadocumentofacturaItem.setLineaDocumentoFacturaCant(LineaDocumentoFactura.getString(TAG_CANT));
                            lineadocumentofacturaItem.setLineaDocumentoFacturaImporte(LineaDocumentoFactura.getString(TAG_IMPORTE));

                            lineadocumentofacturalist.add(lineadocumentofacturaItem);


                        }else{
                            // LineaDocumentoFactura with pid not found
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


    /**
     * Background Async Task to  Save LineaDocumentoFactura Details
     * */
    class SaveLineaDocumentoFacturaDetails extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(ActividadPrincipal.getPalabras("Guardar")+" "+ActividadPrincipal.getPalabras("Linea")+" "+ActividadPrincipal.getPalabras("Documento")+" "+ActividadPrincipal.getPalabras("Factura")+" ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving LineaDocumentoFactura
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // getting updated data from EditTexts
            String importe = "";
            String cant = "";
            String preu = "";
            String nombre = "";
            Integer result = 0;

            for (int i = 0; i < lineadocumentofacturalist.size(); i++) {
                nombre = String.valueOf(lineadocumentofacturalist.get(i).getLineaDocumentoFacturaNombre());
                cant = String.valueOf(lineadocumentofacturalist.get(i).getLineaDocumentoFacturaCant());
                preu = String.valueOf(lineadocumentofacturalist.get(i).getLineaDocumentoFacturaPreu());
                importe = String.valueOf(lineadocumentofacturalist.get(i).getLineaDocumentoFacturaImporte());
            }

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put(TAG_PID, pid);
            values.put(TAG_NOMBRE,nombre);
            values.put(TAG_PREU,preu);
            values.put(TAG_CANT,cant);
            values.put(TAG_IMPORTE,importe);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            Log.i("Datos Recogidos", pid + " ... " + preu + " ..." + nombre + " - " + cant + " - " + importe +  " - " + dateNow + " - "+Filtro.getUsuario());


            // sending modified data through http request
            // Notice that update LineaDocumentoFactura url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_LineaDocumentoFactura,
                    "POST", values);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    result = 1;
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    result = 0;
                    // failed to update LineaDocumentoFactura
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
            // dismiss the dialog once LineaDocumentoFactura uupdated
            pDialog.dismiss();
        }
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        //TextView responseText = (TextView) findViewById(R.id.responseText);
        EditText myEditText = (EditText) view;
        String cValor;
        Double nValor;
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
                if (id == R.id.Cant) {
                    cValor = myEditText.getText().toString();
                    if (cValor.matches("")) {
                        Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("Valor")+" "+ActividadPrincipal.getPalabras("Vacio")+" "+ActividadPrincipal.getPalabras("Cantidad"), Toast.LENGTH_SHORT).show();
                        //            this.btnGuardar.setEnabled(false);
                        this.btnGuardar1.setEnabled(false);
                        this.btnGuardar1.setChecked(false);
                        return false;
                    }
                    btnGuardar1.setEnabled(true);
                    btnGuardar1.setChecked(true);

                    cValor = cValor.replace(".","");
                    cValor = cValor.replace(",",".");

                    nValor = Double.valueOf(cValor);
                    txtCant.setText(String.format("%1$,.2f", nValor));
                    txtImporte.setText(calculaImporte(txtCant.getText().toString(),txtPreu.getText().toString()));
                }else if (id == R.id.Preu) {
                    cValor = myEditText.getText().toString();
                    if (cValor.matches("")) {
                        Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("Valor")+" "+ActividadPrincipal.getPalabras("Vacio")+" "+ActividadPrincipal.getPalabras("Precio"), Toast.LENGTH_SHORT).show();
                        //            this.btnGuardar.setEnabled(false);
                        this.btnGuardar1.setEnabled(false);
                        this.btnGuardar1.setChecked(false);
                        return false;
                    }
                    btnGuardar1.setEnabled(true);
                    btnGuardar1.setChecked(true);

                    cValor = cValor.replace(".","");
                    cValor = cValor.replace(",",".");

                    nValor = Double.valueOf(cValor);
                    txtPreu.setText(String.format("%1$,.2f", nValor));
                    txtImporte.setText(calculaImporte(txtCant.getText().toString(),txtPreu.getText().toString()));

                }else if (id == R.id.Importe) {
                    cValor = myEditText.getText().toString();
                    if (cValor.matches("")) {
                        Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("Valor")+" "+ActividadPrincipal.getPalabras("Vacio")+" "+ActividadPrincipal.getPalabras("Importe"), Toast.LENGTH_SHORT).show();
                        //            this.btnGuardar.setEnabled(false);
                        this.btnGuardar1.setEnabled(false);
                        this.btnGuardar1.setChecked(false);
                        return false;
                    }
                    btnGuardar1.setEnabled(true);
                    btnGuardar1.setChecked(true);

                    cValor = cValor.replace(".","");
                    cValor = cValor.replace(",",".");

                    nValor = Double.valueOf(cValor);
                    txtImporte.setText(String.format("%1$,.2f", nValor));

                }
                return true;
            }
        }
        return false; // pass on to other listeners.

    }
    public String calculaImporte(String cCant, String cPreu) {
        double nCant = 0;
        double nPreu = 0;

        if (cCant.matches("")) {
            nCant = 0;
        }else{
            cCant = cCant.replace(".","");
            cCant = cCant.replace(",",".");
            nCant = Double.valueOf(cCant);
        }

        if (cPreu.matches("")) {
            nPreu = 0;
        }else{
            cPreu = cPreu.replace(".","");
            cPreu = cPreu.replace(",",".");
            nPreu = Double.valueOf(cPreu);
        }

        return (String.format("%1$,.2f", nCant*nPreu));
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
        new GetLineaDocumentoFacturaDetails().execute();
    }

}

