package tpv.cirer.com.restaurante.ui;

/**
 * Created by JUAN on 27/11/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;
import com.epson.epsonio.DevType;
import com.epson.epsonio.Finder;
import com.epson.epsonio.IoStatus;
import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.ShowMsg;
import tpv.cirer.com.restaurante.herramientas.StringUtil;
import tpv.cirer.com.restaurante.modelo.CabeceraEmpr;
import tpv.cirer.com.restaurante.modelo.CabeceraFtp;
import tpv.cirer.com.restaurante.modelo.DocumentoFacturaIva;
import tpv.cirer.com.restaurante.modelo.LineaDocumentoFactura;

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

public class ActivityImprimirVentas extends Activity implements View.OnClickListener {
    public static List<CabeceraEmpr> lcabeceraempr;
    public static List<CabeceraFtp> lcabeceraftp;
    public static List<LineaDocumentoFactura> llineadocumentofactura;
    public static List<DocumentoFacturaIva> ldocumentofacturaiva;
    public String url;
    ProgressDialog pDialogEmpr,pDialogFtp,pDialogLft,pDialogFtpiva;
    int nFactura;
    int nId;
    String sSerie;

    EditText etimpresora;
    Button btbuscarimp;
    TextView tvticket;
    Button btimprimirventa;
    Print impresora;
//    Model dbmanager;
    Integer factura;
    String razonsocial;
    AlertDialog.Builder builder;
    String ruccliente;
    String condicion;
    String fec_vto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_imprimir_ventas);
////        getActionBar().setDisplayHomeAsUpEnabled(true);
////        getActionBar().setHomeButtonEnabled(true);
//        dbmanager = new Model(this);
/*
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        printManager.print(jobName, new PrintDocumentAdapter(...), printAttributes);
*/
        lcabeceraempr = new ArrayList<CabeceraEmpr>();
        lcabeceraftp = new ArrayList<CabeceraFtp>();
        llineadocumentofactura = new ArrayList<LineaDocumentoFactura>();
        ldocumentofacturaiva = new ArrayList<DocumentoFacturaIva>();

        etimpresora = (EditText) findViewById(R.id.etimpresora);
        btbuscarimp = (Button) findViewById(R.id.btbuscarimp);
        tvticket = (TextView) findViewById(R.id.tvticket);
        btimprimirventa = (Button) findViewById(R.id.btimprimirventa);
        btbuscarimp.setOnClickListener(this);
        btimprimirventa.setOnClickListener(this);
        obtenerimpresora();
        impresora = new Print(getApplicationContext());
////        pruebaimpresora();
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            nId = extras.getInt("id");
            sSerie = extras.getString("serie");
            nFactura = extras.getInt("factura");

/*            String value = extras.getString("numero");
            factura = Integer.parseInt(value);
            razonsocial = extras.getString("razonsocial");
            ruccliente =  extras.getString("ruccliente");
            condicion = extras.getString("condicion");
            fec_vto = extras.getString("fec_vto");
*/        }

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje");
        builder.setCancelable(true);
        builder.setNeutralButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Leemos Cabecera Empresa, Factura, Lineas e Iva Facturas.
        url = Filtro.getUrl()+"/CabeceraEMPR.php";
        new LeerCabeceraEmpr().execute(url);

//        crearTicket();
    }
/*    @Override
    public void onStartPrinterStateTracking(PrinterId printerId) {
        // check for info we found when printer was discovered
        PrinterInfo printerInfo = findPrinterInfo(printerId);

        if (printerInfo != null) {
            PrinterCapabilitiesInfo capabilities = new PrinterCapabilitiesInfo.Builder(printerId)
                    .setMinMargins(new PrintAttributes.Margins(200, 200, 200, 200))
                    .addMediaSize(PrintAttributes.MediaSize.ISO_A4, true)
                    .addMediaSize(PrintAttributes.MediaSize.ISO_A5, false)
                    .addResolution(new PrintAttributes.Resolution("R1", "200x200", 200, 200), false)
                    .addResolution(new PrintAttributes.Resolution("R2", "200x300", 200, 300), true)
                    .setColorModes(PrintAttributes.COLOR_MODE_COLOR |
                            PrintAttributes.COLOR_MODE_MONOCHROME, PrintAttributes.COLOR_MODE_COLOR)
                    .build();

            printerInfo = new PrinterInfo.Builder(printerInfo)
                    .setCapabilities(capabilities).build();

            // We add printer info to system service
            List<PrinterInfo> printers = new ArrayList();
            printers.add(printerInfo);
            addPrinters(printers);
        }
    }
    public void handleQueuedPrintJob(PrintJobId printJobId, PrinterId printerId) {
        final PrintJob printJob = mProcessedPrintJobs.get(printJobId);
        if (printJob == null) {
            return;
        }

        if (printJob.isQueued()) {
            printJob.start();
        }

        OurPrinter ourPrinter = ourDiscoverySession.getPrinter(printerId);

        if (ourPrinter != null) {
            AsyncTask <Void, Void, Void> printTask =
                    new PrintTask(printJob, ourPrinter);
            printTask.execute();
        }
    }
*/    public void pruebaimpresora() {
        Print printer = new Print();
        int[] status = new int[1];
        status[0] = 0;
        try {
            Builder builder = new Builder("TM-T20II", Builder.MODEL_ANK);
            builder.addText("ABCDE");
            printer.openPrinter(Print.DEVTYPE_TCP, "192.168.1.100");
            printer.sendData(builder, 10000, status);
            printer.closePrinter();
        } catch (EposException e) {
            int errStatus = e.getErrorStatus();
            status[0] = e.getPrinterStatus();
            Log.i("print",Integer.toString(errStatus)+" "+e.getPrinterStatus());
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btbuscarimp){
            obtenerimpresora();
        }else if (view.getId() == R.id.btimprimirventa){
            iniciarImpresion();
            try {
                impresora.closePrinter();
            } catch (EposException e) {
                e.printStackTrace();
            }
            setResult(25);
//            dbmanager.close();
            this.finish();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_imprimir_ventas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                try {
                    Finder.stop();
                }catch(Exception e){

                }

                try {
                    impresora.closePrinter();
                } catch (EposException e) {
                    e.printStackTrace();
                }
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            Finder.stop();
        }catch(Exception e){
            return;
        }

        try {
            impresora.closePrinter();
        } catch (EposException e) {
            e.printStackTrace();
        }
    }
    public void obtenerimpresora(){

        int errStatus = IoStatus.SUCCESS;
        int intentos = 1;
        String[] mList = null;
        //Start search
        while(etimpresora.getText().length() < 1) {
            System.out.print("Intentando conexion");
            try {
                Finder.stop();
            } catch (Exception e) {

            }

            try {
//                Finder.start(this, DevType.USB, null);
                Finder.start(this, DevType.TCP, "192.168.1.100");

                //Exception handling
            } catch (Exception e) {
                ShowMsg.showException(e, "start", this);
                return;
            }

            try {
                mList = Finder.getResult();
                //Exception handling
            } catch (Exception e) {
                ShowMsg.showException(e, "finder", this);
                return;
            }
            try {
                //etimpresora.setText(mList[0].toString());
                for (int i = 0; i < mList.length; i++) {
                    etimpresora.setText(mList[i]);

                }
            } catch (Exception e) {
                ShowMsg.showException(e, "Result", this);
                return;
            }

            if (intentos == 100){
                etimpresora.setText("Imp. no conectado");
            }
            intentos = intentos + 1;
        }

        if (etimpresora.getText().toString() == "Imp. no conectado"){
            AlertDialog msg;
            msg = builder.create();
            msg.setMessage("La impresora no responde.");
            msg.show();
        }

    }

    public void conectarImpresora() {
        //open
        try{
            impresora.openPrinter(Print.DEVTYPE_USB, etimpresora.getText().toString(), Print.FALSE, 1000);
//            impresora.openPrinter(Print.DEVTYPE_TCP, "TCP:192.168.1.33(EPSON TMU210)");
        }catch(EposException e){
            //impresora.closePrinter();
            impresora = null;
            ShowMsg.showException(e, "openPrinter" , this);
            return;
        }
    }

    public void iniciarImpresion(){
        int[] status = new int[1];
        status[0] = 0;
        int[] battery = new int[1];
        battery[0] = 0;
        try {
            Builder builder = new Builder("TM-T20II", Builder.MODEL_ANK);

            builder.addTextLang(Builder.LANG_EN);
            //builder.addTextSmooth(Builder.TRUE);
            //builder.addTextFont(Builder.FONT_A);
            //builder.addTextSize(1, 1);
            //builder.addTextStyle(Builder.FALSE, Builder.FALSE, Builder.TRUE, Builder.PARAM_UNSPECIFIED);


            builder.addText(tvticket.getText().toString());
            conectarImpresora();


            impresora.sendData(builder, 1000, status);
            //<End communication with the printer>
////            ShowMsg.showStatus(EposException.SUCCESS, status[0], battery[0], this);
            try {
                Finder.stop();
            }catch(Exception e){
                return;
            }
            impresora.closePrinter();

        } catch (Exception e) {
            ShowMsg.showException(e, "Printer" , this);
            return;
        }
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
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
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

    public void crearTicketOLD(){
        //obteniendo el encabezado del documento
/*        String ticket =  "";
        String empresa;
        String est;
        String puntoexp;
        String act_eco = "DISTRIBUIDORA";
        String ruc;
        String telefono;
        String timbrado;
        String vencimiento;
        Integer total = 0;
        Integer ex = 0;
        Integer g5 = 0;
        Integer g10 = 0;
        String separador = "- - - - - - - - - -- - - - - - - - - - -" + "\n";
        ArrayList<String> pex = dbmanager.getAllPex();
        empresa = pex.get(4).toString().toUpperCase();
        est = pex.get(5);
        puntoexp = pex.get(6);
        ruc = pex.get(7).toString();
        telefono = pex.get(8).toString();
        timbrado = pex.get(0).toString();
        vencimiento = pex.get(2).toString();
        //empresa
        ticket = ticket + StringUtil.align(empresa, 40, ' ',0) + "\n";
        //actividad económica
        ticket = ticket + StringUtil.align(act_eco, 40, ' ',0) + "\n";
        //ruc
        ticket = ticket + StringUtil.align("RUC: " + "XXXXXXX", 40, ' ',0) + "\n";
        //telefono
        ticket = ticket + StringUtil.align(telefono, 40, ' ',0) + "\n";
        //direccion
        ticket = ticket + StringUtil.align("DIRECCION ESTABLECIMIENTO", 40, ' ',0) + "\n";
        //timbrado
        ticket = ticket + StringUtil.align("TIMBRADO NRO.: " + "XXXXXXX", 40, ' ',0) + "\n";
        //validez
        ticket = ticket + StringUtil.align("VALIDEZ " + "XX/XX/XXXX", 40, ' ',0) + "\n";
        //ciudad
        ticket = ticket + StringUtil.align("DR. J. M. FRUTOS-PARAGUAY", 40, ' ',0) + "\n";
        //iva incluido
        ticket = ticket + StringUtil.align("IVA INCLUIDO", 40, ' ',0) + "\n";
        ticket = ticket + separador;

        //numero de factura
        ticket =  ticket + String.format("%-40s", "FACTURA NRO.: " + String.format("%03d", Integer.parseInt(est))
                + "-" + String.format("%03d", Integer.parseInt(puntoexp)) + "-" + String.format("%07d", factura) ) + "\n";

        //fecha de emision del documento
        ticket = ticket + String.format("%-40s", "FECHA: " + getDate() + " " + getTime()) + "\n";
        //condicion de venta   #########OBTENER VALOR DEL SPINNER DE SELECCION############
        ticket = ticket + String.format("%-40s", "CONDICION DE VENTA: "+ " CONTADO ") + "\n";

        //vendedor ##########OBTENER DATOS DEL VENDEDOR ACTUAL################
        ticket = ticket + String.format("%-40s", "VENDEDOR: " + "PRUEBA") + "\n";
        ticket = ticket + separador;

        //cliente
        ticket = ticket + String.format("%-40s", "RAZON SOCIAL: " + razonsocial) + "\n";
        //ruc o numero de documento de indentidad
        ticket = ticket + String.format("%-40s", "RUC O CI NRO.: " + ruccliente) + "\n";
        ticket = ticket + separador;

        Cursor res = dbmanager.getDetalleFac(factura.toString(), timbrado);
        res.moveToFirst();
        String row = "";
        while(res.isAfterLast() == false){

            row = row + String.format("%07d", Integer.parseInt(res.getString(0))) + " ";
            if (res.getString(1).length() > 28){
                row = row + String.format("%-28s", res.getString(1).substring(0, 27)) + " ";
            }else{
                row = row + String.format("%-28s", res.getString(1)) + " ";
            }
            row = row + String.format("%-2s", res.getString(2)) + "%" + "\n";

            row = row + String.format("%-6s", res.getString(3)) + " ";
            row = row + String.format("%-7s", res.getString(4) + "/" + res.getString(5)) + " ";
            row = row + String.format("%-12s", res.getInt(6));
            row = row + String.format("%13s", res.getInt(7)) + "\n";

            //sumando los totales
            //total = total + Integer.parseInt(res.getString(7));
            total = total + res.getInt(7);
            if (Integer.parseInt(res.getString(2)) == 0){
                //ex = ex + Integer.parseInt(res.getString(7));
                ex = ex + res.getInt(7);
            }
            if (Integer.parseInt(res.getString(2)) == 5){
                //g5 = g5 + Integer.parseInt(res.getString(7));
                g5 = g5 + res.getInt(7);
            }
            if (Integer.parseInt(res.getString(2)) == 10){
                //g10 = g10 + Integer.parseInt(res.getString(7));
                g10 = g10 + res.getInt(7);
            }
            res.moveToNext();
        }
        ticket = ticket + row;
        ticket = ticket + separador;
        ticket = ticket + String.format("%-20s", "IMPORTE TOTAL");
        ticket = ticket + String.format("%20s", total.toString()) + "\n";
        ticket = ticket + separador;

        ticket = ticket +  String.format("%-40s", "TOTALES") + "\n";

        ticket = ticket + String.format("%-20s", "EXENTAS");
        ticket = ticket + String.format("%20s", ex.toString()) + "\n";

        ticket = ticket + String.format("%-20s", "GRAVADAS 5% ");
        ticket = ticket + String.format("%20s", g5.toString()) + "\n";

        ticket = ticket + String.format("%-20s", "GRAVADAS 10% ");
        ticket = ticket + String.format("%20s", g10.toString()) + "\n";

        ticket = ticket +  String.format("%-40s", "LIQUIDACION DEL IVA") + "\n";

        ticket = ticket + String.format("%-20s", "IVA 5% ");
        ticket = ticket + String.format("%20s", Math.round(g5/21)) + "\n";

        ticket = ticket + String.format("%-20s", "IVA 10% ");
        ticket = ticket + String.format("%20s", Math.round(g10/11)) + "\n";

        ticket = ticket +  String.format("%40s", "- - - - - - - - - -") + "\n";

        ticket = ticket + String.format("%-20s", "TOTAL IVA ");
        ticket = ticket + String.format("%20s",Math.round(g5/21) + Math.round(g10/11)) + "\n";

        ticket = ticket + separador;

        ticket = ticket + StringUtil.align("GRACIAS POR SU PREFERENCIA!", 40, ' ',0) + "\n";

        ticket = ticket + separador+"\n\n\n\n\n\n";

        tvticket.setText(ticket);
*/
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
            pDialogEmpr = new ProgressDialog(ActivityImprimirVentas.this);
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
            pDialogFtp = new ProgressDialog(ActivityImprimirVentas.this);
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
            pDialogLft = new ProgressDialog(ActivityImprimirVentas.this);
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
            pDialogFtpiva = new ProgressDialog(ActivityImprimirVentas.this);
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

}