package tpv.cirer.com.marivent.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.DatePickerFragment;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.modelo.Dcj;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getPalabras;

/**
 * Created by JUAN on 28/01/2017.
 */

public class EditCloseDcjFragment extends Fragment implements View.OnKeyListener {
    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    private StringBuilder nowYYYYMMDD;
    private StringBuilder nowDDMMYYYY;
    private SimpleDateFormat dateformatYYYYMMDD;
    private SimpleDateFormat dateformatDDMMYYYY;
    public static List<Dcj> dcjlist;
    public boolean ok_delete_lectura = false;
    public static final String TAG = "Modifica Dcj";

    LinearLayout rootView;
    CheckBox chkApertura;
    EditText txtSaldoInicio;
    TextView txtDcj;
    EditText txtFecha_apertura;
    TextView lblSaldoInicio;
    TextView lblSeccion;
    TextView lblFecha;

    ToggleButton btnGuardar1;
    //  Button btnGuardar;
    Button btnFecha;
    Button btnSalir;
    private String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParserNew jsonParserNew = new JSONParserNew();

    // single Dcj url
    private static final String url_dcj_details = Filtro.getUrl()+"/lee_dcj_detalle_1.php";

    // url to update Dcj
    private static final String url_update_dcj = Filtro.getUrl()+"/modifica_dcj_1.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_DCJ = "dcj";
    private static final String TAG_FECHAAPERTURA = "fechaapertura";
    private static final String TAG_SALDOINICIO = "saldoinicio";
    private static final String TAG_APERTURA = "apertura";

    private static EditCloseDcjFragment EditDcj = null;

    public static EditCloseDcjFragment newInstance(String id) {
        EditCloseDcjFragment EditDcj = new EditCloseDcjFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        EditDcj.setArguments(args);
        return EditDcj;
    }

    public EditCloseDcjFragment() {
        // Required empty public constructor
    }
    public static EditCloseDcjFragment getInstance(){
        if(EditDcj == null){
            EditDcj = new EditCloseDcjFragment();
        }
        return EditDcj;
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
        dcjlist = new ArrayList<Dcj>();

        /// Poner Titulo CABECERA
        ((ActividadPrincipal) getActivity()).setTitle(getPalabras("CLOSE")+" "+getPalabras("Diario Caja"));

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   View rootView = inflater.inflate(R.layout.add_lectura, container, false);
        rootView = (LinearLayout) inflater.inflate(R.layout.edit_dcj,container, false);

        // Edit Text

        lblSaldoInicio = (TextView) rootView.findViewById(R.id.lblSaldoInicio);
        lblSaldoInicio.setText(ValorCampo(R.id.lblSaldoInicio, lblSaldoInicio.getClass().getName(),0));
        txtSaldoInicio = (EditText) rootView.findViewById(R.id.SaldoInicio);

        lblFecha = (TextView) rootView.findViewById(R.id.lblFecha);
        lblFecha.setText(ValorCampo(R.id.lblFecha, lblFecha.getClass().getName(),0));
        txtFecha_apertura = (EditText) rootView.findViewById(R.id.Fecha_apertura);
        txtFecha_apertura.setEnabled(false);

        chkApertura = (CheckBox) rootView.findViewById(R.id.chkApertura);
        chkApertura.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.OFF_apertura)));

        txtDcj = (TextView) rootView.findViewById(R.id.Dcj);

        txtSaldoInicio.setOnKeyListener(this);

        btnFecha = (Button)rootView.findViewById(R.id.date);
        btnFecha.setText(ValorCampo(R.id.date, btnFecha.getClass().getName(),0));
        // button click event
        btnSalir = (Button)rootView.findViewById(R.id.BtnSalir);
        btnSalir.setText(ValorCampo(R.id.BtnSalir, btnSalir.getClass().getName(),0));
        btnSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Create button
//        btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);

        // Getting complete Dcj details in background thread
        //    new GetDcjDetails().execute();
        btnGuardar1 = (ToggleButton)rootView.findViewById(R.id.BtnGuardar1);
        btnGuardar1.setText(ValorCampo(R.id.BtnGuardar1, btnGuardar1.getClass().getName(),0));
        btnGuardar1.setText(ValorCampo(R.id.BtnGuardar1, btnGuardar1.getClass().getName(),1));
        btnGuardar1.setEnabled(false);
        btnGuardar1.setChecked(false);
        btnGuardar1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String cMaximo = txtSaldoInicio.getText().toString();
                cMaximo = cMaximo.replace(".","");
                cMaximo = cMaximo.replace(",",".");
                txtSaldoInicio.setText(cMaximo);

                // Eliminar Teclado Virtual
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);

                for (int i = 0; i < dcjlist.size(); i++) {
                    dcjlist.get(i).setDcjSaldo_inicio(txtSaldoInicio.getText().toString());
                    dcjlist.get(i).setDcjApertura((chkApertura.isChecked()?0:1));
                    dcjlist.get(i).setDcjFecha_Apertura(txtFecha_apertura.getText().toString());

                }
                // starting background task to update Dcj
                new SaveDcjDetails().execute();
            }
        });
        // save button click event
        chkApertura.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if(chkApertura.isChecked()) {
                    //                btnGuardar.setEnabled(true);
                    btnGuardar1.setEnabled(true);
                    btnGuardar1.setChecked(true);
                }else {
                    //                btnGuardar.setEnabled(false);
                    btnGuardar1.setEnabled(false);
                    btnGuardar1.setChecked(false);
                }
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        Log.i("Fragment id ", " # " + pid );
        //       new AsyncHttpTask().execute(url);

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


    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Bundle args = new Bundle();

        // Poner ultima fecha registrada en la dcj.
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        String strFecha = txtFecha_apertura.getText().toString();
        Date fecha = null;
        try {

            fecha = formatoDelTexto.parse(strFecha);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }

        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
//        System.out.println("Son las: "+formatoHora.format(fechaActual)+" de fecha: "+formatoFecha.format(fechaActual));

        //Fecha actual desglosada:
        Calendar fecha_cal = Calendar.getInstance();
        fecha_cal.setTime(fecha);
///        fecha_cal.add(Calendar.DATE, 1); //Añadir 1 dia al ultimo dia cerrado
        int año = fecha_cal.get(Calendar.YEAR);
        int mes = fecha_cal.get(Calendar.MONTH) ;
        int dia = fecha_cal.get(Calendar.DAY_OF_MONTH);
        int hora = fecha_cal.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha_cal.get(Calendar.MINUTE);
        int segundo = fecha_cal.get(Calendar.SECOND);

        long minDate = fecha_cal.getTime().getTime(); // Twice!


/*        StringBuilder xfdia = new StringBuilder(xf1.substring(0,2));
        StringBuilder xfmes =  new StringBuilder(xf2.substring(3,2));
        StringBuilder xfanyo =  new StringBuilder(xf3.substring(6,4));
*/
        Log.i("FECHA APERTURA",txtFecha_apertura.getText().toString()+"_"+TAG_FECHAAPERTURA+" _ ");
        args.putInt("year", año);
        args.putInt("month",mes);
        args.putInt("day", dia);
        args.putLong("minDate",minDate);
/*        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
*/


//        pickerDialog.getDatePicker().setMaxDate(maxDate);
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
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
            Toast.makeText(
                    getActivity(),
                    String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth),
                    Toast.LENGTH_LONG).show();
        }
    };

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
            pDialog.setMessage(getPalabras("Cargando")+" "+getPalabras("Diario Caja")+". "+ getPalabras("Espere por favor")+"...");
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
                        // Building Parameters
//                        List<NameValuePair> params = new ArrayList<NameValuePair>();
//                        params.add(new BasicNameValuePair("pid", pid));
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put(TAG_APERTURA, "1" );

                        // getting Dcj details by making HTTP request
                        // Note that Dcj details url will use GET request
//                        JSONObject json = jsonParser.makeHttpRequest(
//                                url_dcj_details, "GET", values);
                        JSONObject json = jsonParserNew.makeHttpRequest(
                                url_dcj_details, "GET", values);

                        // check your log for json response
///                        Log.d("Single Dcj Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received Dcj details
                            JSONArray DcjObj = json
                                    .getJSONArray(TAG_DCJ); // JSON Array

                            // get first Dcj object from JSON Array
                            JSONObject Dcj = DcjObj.getJSONObject(0);

                            // Dcj with this pid found
                            // Edit Text
/*                            txtDcjActual = (EditText) findViewById(R.id.DcjActual);
                            txtFecha_apertura = (EditText) findViewById(R.id.Fecha_apertura);
                            txtDcjAnterior = (TextView) findViewById(R.id.DcjAnterior);
                            txtConsumo = (TextView) findViewById(R.id.Consumo);
                            txtDcj = (TextView) findViewById(R.id.Dcj);
                            
*/                          String xfecha="";
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                            try{
                                String StringRecogido = Dcj.getString(TAG_FECHAAPERTURA);
                                Date datehora = sdf1.parse(StringRecogido);

                                Calendar c = Calendar.getInstance();
                                c.setTime(datehora);
                                c.add(Calendar.DATE, 1);  // number of days to add
                                xfecha = sdf2.format(c.getTime());  // dt is now the new date

                                //System.out.println("Fecha input : "+datehora);
                                ///*              xfecha = sdf2.format(datehora);

                            } catch (Exception e) {
                                e.getMessage();
                            }


                            // display Dcj data in EditText
                            txtFecha_apertura.setText(xfecha);
                            txtDcj.setText(Dcj.getString(TAG_DCJ));
                            chkApertura.setChecked((0 != Integer.parseInt(Dcj.getString(TAG_APERTURA)) ? true : false));

//                            txtSaldoInicio.setText(Dcj.getString(TAG_SALDOINICIO));
                            double xMaximo = Double.valueOf(Dcj.getString(TAG_SALDOINICIO));
                            txtSaldoInicio.setText(String.format("%1$,.2f", xMaximo));

                            Dcj dcjItem = new Dcj();
                            dcjItem.setDcjFecha_Apertura(Dcj.getString(TAG_FECHAAPERTURA));
                            dcjItem.setDcjSaldo_inicio(Dcj.getString(TAG_SALDOINICIO));
                            dcjItem.setDcjApertura(Integer.parseInt(Dcj.getString(TAG_APERTURA)));

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


    /**
     * Background Async Task to  Save Dcj Details
     * */
    class SaveDcjDetails extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getPalabras("Guardar")+" "+getPalabras("Diario Caja")+" ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving Dcj
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // getting updated data from EditTexts
            String dcj = "";
            String xfecha="";
            String StringRecogido="";
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            String apertura = "";
            String saldoinicio = "";
            Integer result = 0;
            for (int i = 0; i < dcjlist.size(); i++) {
                try{
                    StringRecogido = String.valueOf(dcjlist.get(i).getDcjFecha_Apertura());
                    Date datehora = sdf1.parse(StringRecogido);
                    //System.out.println("Fecha input : "+datehora);
                    xfecha = sdf2.format(datehora);

                }
                catch(Exception e){
                    e.getMessage();
                }
                apertura = String.valueOf(dcjlist.get(i).getDcjApertura());
                saldoinicio = String.valueOf(dcjlist.get(i).getDcjSaldo_inicio());
            }

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());
            //           System.out.println("Now the date is :=>  " + dateNow);

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put(TAG_PID, pid);
            values.put(TAG_FECHAAPERTURA,xfecha);
            values.put(TAG_SALDOINICIO,saldoinicio);
            values.put(TAG_APERTURA,apertura);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            Log.i("fecha", xfecha + " ... " + StringRecogido + " ..." + pid + " - " + dcj + " - " + saldoinicio + " - " + dateNow + " - "+Filtro.getUsuario());


            // sending modified data through http request
            // Notice that update Dcj url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_dcj,
                    "POST", values);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    result = 1;
                    // PONEMOS EN FILTRO FECHA APERTURA LA FECHA ABIERTA CON FORMATO YYYY-MM-DD
///                    Filtro.setFechaapertura(xfecha);
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    result = 0;
                    // failed to update Dcj
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
            // dismiss the dialog once Dcj uupdated
            pDialog.dismiss();
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
                if (id == R.id.SaldoInicio) {
                    String cMaximo = myEditText.getText().toString();
                    if (cMaximo.matches("")) {
                        Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("Valor")+" "+ActividadPrincipal.getPalabras("Vacio"), Toast.LENGTH_SHORT).show();
                        //            this.btnGuardar.setEnabled(false);
                        this.btnGuardar1.setEnabled(false);
                        this.btnGuardar1.setChecked(false);
                        return false;
                    }

                    cMaximo = cMaximo.replace(".","");
                    cMaximo = cMaximo.replace(",",".");

                    double xMaximo = Double.valueOf(cMaximo);
                    txtSaldoInicio.setText(String.format("%1$,.2f", xMaximo));
                }else if (id == R.id.chkApertura) {
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
        new EditCloseDcjFragment.GetDcjDetails().execute();
    }

}

