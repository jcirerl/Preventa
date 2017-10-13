package tpv.cirer.com.restaurante.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.net.URISyntaxException;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.Filtro;

/**
 * Created by JUAN on 12/10/2017.
 */

public class WebeasyredsysActivity extends AppCompatActivity {
    Button b1;
    EditText ed1;
    private String pid;
    private String serie;
    private String factura;
    private String importe;
    private String TAG = "RESPONSE EASYREDSYS";
    private WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easyredsys_main);
        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get ID, SERIE, FACTURA, IMPORTE
        pid = intent.getStringExtra("Id");
        serie = intent.getStringExtra("Serie");
        factura = intent.getStringExtra("Factura");
        importe = intent.getStringExtra("Importe");

/*        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);
        ed1.setText("https://easyredsys.miguelangeljulvez.com/easyredsys/");
*/        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new WebeasyredsysActivity.CustomWebViewClient());
        String url = Filtro.getUrl_redsys()+"?"+
                "id="+pid.trim()+
                "&serie="+serie.trim()+
                "&factura="+factura.trim()+
                "&importe="+importe.trim()+
                "&card=4548812049400004"+
                "&expiration-month=12"+
                "&expiration-year=20"+
                "&ccv=123";
        Log.i("urljsp",url);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setVerticalScrollBarEnabled(true);
        wv1.setHorizontalScrollBarEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);

/*        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ed1.getText().toString();
                String url = "https://easyredsys.miguelangeljulvez.com/easyredsys/";

                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });
        */
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (url.startsWith("http")) return false;//open web links as usual
            //try to find browse activity to handle uri
            Uri parsedUri = Uri.parse(url);
            PackageManager packageManager = WebeasyredsysActivity.this.getPackageManager();
            Intent browseIntent = new Intent(Intent.ACTION_VIEW).setData(parsedUri);
            if (browseIntent.resolveActivity(packageManager) != null) {
                WebeasyredsysActivity.this.startActivity(browseIntent);
                return true;
            }
            //if not activity found, try to parse intent://
            if (url.startsWith("intent:")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent.resolveActivity(WebeasyredsysActivity.this.getPackageManager()) != null) {
                        WebeasyredsysActivity.this.startActivity(intent);
                        return true;
                    }
                    //try to find fallback url
                    String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                    if (fallbackUrl != null) {
                        webView.loadUrl(fallbackUrl);
                        return true;
                    }
                    //invite to install
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(
                            Uri.parse("market://details?id=" + intent.getPackage()));
                    if (marketIntent.resolveActivity(packageManager) != null) {
                        WebeasyredsysActivity.this.startActivity(marketIntent);
                        return true;
                    }
                } catch (URISyntaxException e) {
                    //not an intent uri
                }
            }
            return true;//do nothing in other cases
        }

    }

}
