package tpv.cirer.com.restaurante.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.net.URISyntaxException;

import tpv.cirer.com.restaurante.R;

/**
 * Created by JUAN on 09/10/2017.
 */

public class EasyredsysActivity extends AppCompatActivity {
    Button b1;
    EditText ed1;

    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easyredsys_main);

/*        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);
        ed1.setText("https://easyredsys.miguelangeljulvez.com/easyredsys/");
*/        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new CustomWebViewClient());
        String url = "https://easyredsys.miguelangeljulvez.com/easyredsys/";

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
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
            PackageManager packageManager = EasyredsysActivity.this.getPackageManager();
            Intent browseIntent = new Intent(Intent.ACTION_VIEW).setData(parsedUri);
            if (browseIntent.resolveActivity(packageManager) != null) {
                EasyredsysActivity.this.startActivity(browseIntent);
                return true;
            }
            //if not activity found, try to parse intent://
            if (url.startsWith("intent:")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent.resolveActivity(EasyredsysActivity.this.getPackageManager()) != null) {
                        EasyredsysActivity.this.startActivity(intent);
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
                        EasyredsysActivity.this.startActivity(marketIntent);
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
