package tpv.cirer.com.restaurante.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import tpv.cirer.com.restaurante.R;

/**
 * Created by JUAN on 30/09/2017.
 */

public class AnotherActivity extends AppCompatActivity {

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_main_error);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("ERROR APPLICATION");

        error = (TextView) findViewById(R.id.error);

        error.setText(getIntent().getStringExtra("error"));
    }
}
